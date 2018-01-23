package reactor.models;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private LocalDateTime occurredAt;
    private String device;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Group group;
    private String data;
    @Transient
    private JSONObject dataJson;


    public Event() {
    }

    public Event(LocalDateTime occurredAt, String device, Group group, String data, JSONObject dataJson) {
        this.occurredAt = occurredAt;
        this.device = device;
        this.group = group;
        this.data = data;
        this.dataJson = dataJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getData() {
        dataJson = new JSONObject(data);

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public JSONObject getDataJson() {
        return dataJson;
    }

    public void setDataJson(JSONObject dataJson) {
        this.dataJson = dataJson;
    }
}
