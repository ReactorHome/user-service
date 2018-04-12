package reactor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Face {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private boolean safe;
    private String name;
    @Column
    private String faceData;
    @Transient
    private String rawData;

    public Face() {
    }

    public Face(boolean safe, String name, String faceData) {
        this.safe = safe;
        this.name = name;
        this.faceData = faceData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getRawData(){
        return rawData;
    }

    public void setRawData(String rawData){
        this.rawData = rawData;
    }
}
