package reactor.cloud.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScheduleEvent {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Integer groupId;
    private Integer deviceId;
    private String attribute_name;
    private String attribute_value;
    private Integer minute;
    private Integer hour;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    public ScheduleEvent() {
    }

    public ScheduleEvent(Integer groupId, Integer deviceId, String attribute_name, String attribute_value, Integer minute, Integer hour, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.groupId = groupId;
        this.deviceId = deviceId;
        this.attribute_name = attribute_name;
        this.attribute_value = attribute_value;
        this.minute = minute;
        this.hour = hour;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getAttribute_value() {
        return attribute_value;
    }

    public void setAttribute_value(String attribute_value) {
        this.attribute_value = attribute_value;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }
}
