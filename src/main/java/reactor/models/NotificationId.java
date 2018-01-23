package reactor.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NotificationId {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String notificationAddress;

    public NotificationId() {
    }

    public NotificationId(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotificationAddress() {
        return notificationAddress;
    }

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }
}
