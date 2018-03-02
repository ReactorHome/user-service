package reactor.cloud.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.cloud.entities.ScheduleEvent;
import reactor.cloud.repositories.ScheduleEventRepository;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ScheduledTasks {

    private final ScheduleEventRepository scheduleEventRepository;

    private Queue<ScheduleEvent> eventQueue = new ConcurrentLinkedQueue<>();

    private final int refreshRate = 60 * 1000;

    @Autowired
    public ScheduledTasks(ScheduleEventRepository scheduleEventRepository) {
        this.scheduleEventRepository = scheduleEventRepository;
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
                optional = scheduleEventRepository.findScheduleEventsBySundayAndHourAndMinute(true, hour, minute);
                break;
            case 2:
                optional = scheduleEventRepository.findScheduleEventsByMondayAndHourAndMinute(true, hour, minute);
                break;
            case 3:
                optional = scheduleEventRepository.findScheduleEventsByTuesdayAndHourAndMinute(true, hour, minute);
                break;
            case 4:
                optional = scheduleEventRepository.findScheduleEventsByWednesdayAndHourAndMinute(true, hour, minute);
                break;
            case 5:
                optional = scheduleEventRepository.findScheduleEventsByThursdayAndHourAndMinute(true, hour, minute);
                break;
            case 6:
                optional = scheduleEventRepository.findScheduleEventsByFridayAndHourAndMinute(true, hour, minute);
                break;
            case 7:
                optional = scheduleEventRepository.findScheduleEventsBySaturdayAndHourAndMinute(true, hour, minute);
                break;
            default:
        }

        optional.ifPresent(scheduleEvents -> eventQueue.addAll(scheduleEvents));
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void executeEvents() {
        while (!eventQueue.isEmpty()){
            ScheduleEvent event = eventQueue.remove();

            // execute event
            System.out.println(event.getAttribute_name());
        }
    }
}