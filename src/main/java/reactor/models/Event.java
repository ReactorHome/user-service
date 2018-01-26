package reactor.models;

import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private LocalDateTime occurredAt;
    private String device;
    private String data;
    @Transient
    private JSONObject dataJson;


    public Event() {
    }

    public Event(LocalDateTime occurredAt, String device, String data, JSONObject dataJson) {
        this.occurredAt = occurredAt;
        this.device = device;
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


    public JSONObject getDataJson() {
        return dataJson;
    }

    public void setDataJson(JSONObject dataJson) {
        this.dataJson = dataJson;
    }
}
