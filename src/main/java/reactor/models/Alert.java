package reactor.models;

import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer types;
    private String data;
    private String faceData;
    @Transient
    private JSONObject dataJson;

    public Alert() {
    }

    public Alert(Integer types, String data, String faceData, JSONObject dataJson) {
        this.types = types;
        this.data = data;
        this.faceData = faceData;
        this.dataJson = dataJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public JSONObject getDataJson() {
        return dataJson;
    }

    public void setDataJson(JSONObject dataJson) {
        this.dataJson = dataJson;
    }
}
