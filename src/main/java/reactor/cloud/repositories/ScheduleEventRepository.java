package reactor.cloud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.cloud.entities.ScheduleEvent;

import java.util.List;
import java.util.Optional;

public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Integer> {

    Optional<List<ScheduleEvent>> findScheduleEventsByMondayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsByTuesdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsByWednesdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsByThursdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsByFridayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsBySaturdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsBySundayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findScheduleEventsByDeviceId(Integer deviceId);

    Optional<List<ScheduleEvent>> findScheduleEventsByMinuteAfter(Integer minute);
}
