package reactor.cloud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.cloud.entities.ScheduleEvent;

import java.util.List;
import java.util.Optional;

public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Integer> {

    Optional<List<ScheduleEvent>> findByMondayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findByTuesdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findByWednesdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findByThursdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findByFridayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findBySaturdayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findBySundayAndHourAndMinute(
            boolean day, Integer hour, Integer minute_after);

    Optional<List<ScheduleEvent>> findByDeviceId(Integer deviceId);

    Optional<List<ScheduleEvent>> findByMinuteAfter(Integer minute);

    Optional<List<ScheduleEvent>> findByGroupId(Integer groupId);
}
