package reactor.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Timestamp occurredAt;
    private String device;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String data;
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private JsonNode json;


    public Event() {
    }

    public Event(Timestamp occurredAt, String device) {
        this.occurredAt = occurredAt;
        this.device = device;
        this.data = null;
    }

    public Event(Timestamp occurredAt, String device, String data) {
        this.occurredAt = occurredAt;
        this.device = device;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Timestamp occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public JsonNode getJson() {
        if(json == null && data != null){
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.readTree(this.data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
