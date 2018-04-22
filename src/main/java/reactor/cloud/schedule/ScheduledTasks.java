package reactor.cloud.schedule;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.cloud.entities.DeviceType;
import reactor.cloud.entities.ScheduleEvent;
import reactor.cloud.feign_clients.DeviceClient;
import reactor.cloud.repositories.ScheduleEventRepository;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ScheduledTasks {

    private final ScheduleEventRepository scheduleEventRepository;

    private final DeviceClient deviceClient;

    private Queue<ScheduleEvent> eventQueue = new ConcurrentLinkedQueue<>();

    private final int refreshRate = 60 * 1000;

    @Autowired
    public ScheduledTasks(ScheduleEventRepository scheduleEventRepository, DeviceClient deviceClient) {
        this.scheduleEventRepository = scheduleEventRepository;
        this.deviceClient = deviceClient;
    }

    @Scheduled(fixedRate = refreshRate)
    public void getDueEvents() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);


        Optional<List<ScheduleEvent>> optional = null;
        switch (dayOfWeek){
            case 1:
                optional = scheduleEventRepository.findBySundayAndHourAndMinute(true, hour, minute);
                break;
            case 2:
                optional = scheduleEventRepository.findByMondayAndHourAndMinute(true, hour, minute);
                break;
            case 3:
                optional = scheduleEventRepository.findByTuesdayAndHourAndMinute(true, hour, minute);
                break;
            case 4:
                optional = scheduleEventRepository.findByWednesdayAndHourAndMinute(true, hour, minute);
                break;
            case 5:
                optional = scheduleEventRepository.findByThursdayAndHourAndMinute(true, hour, minute);
                break;
            case 6:
                optional = scheduleEventRepository.findByFridayAndHourAndMinute(true, hour, minute);
                break;
            case 7:
                optional = scheduleEventRepository.findBySaturdayAndHourAndMinute(true, hour, minute);
                break;
            default:
        }

        optional.ifPresent(scheduleEvents -> eventQueue.addAll(scheduleEvents));
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void executeEvents() {
        while (!eventQueue.isEmpty()){
            ScheduleEvent event = eventQueue.remove();

            ObjectNode device = deviceClient.getDevice(event.getDeviceId());
            device.put(event.getAttribute_name(), event.getAttribute_value());

            if(event.getDeviceType().equals(DeviceType.LIGHT)){
                if(!deviceClient.updateLight(event.getGroupId(), event.getDeviceId()).getStatusCode().equals(HttpStatus.OK)){
                    eventQueue.add(event);
                }
            } else if(event.getDeviceType().equals(DeviceType.OUTLET)){
                if(!deviceClient.updateOutlet(event.getGroupId(), event.getDeviceId()).getStatusCode().equals(HttpStatus.OK)){
                    eventQueue.add(event);
                }
            } else if(event.getDeviceType().equals(DeviceType.THERMOSTAT)){
                if(!deviceClient.updateThermostat(event.getGroupId(), event.getDeviceId()).getStatusCode().equals(HttpStatus.OK)){
                    eventQueue.add(event);
                }
            }

            System.out.println(event.getAttribute_name());
        }
    }
}