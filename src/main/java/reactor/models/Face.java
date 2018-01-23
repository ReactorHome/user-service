package reactor.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Face {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private boolean safe;
    private String name;
    private String faceData;

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
}
