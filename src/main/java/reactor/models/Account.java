package reactor.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String firstName;

    private String lastName;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<NotificationId> notificationIdList;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "groups_account_list", joinColumns = {@JoinColumn(name = "account_list_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @JsonIgnore
    private List<Group> groupList;

    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    private Group ownerGroups;

    public Account() {
    }

    public Account(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<NotificationId> getNotificationIdList() {
        return notificationIdList;
    }

    public void setNotificationIdList(List<NotificationId> notificationIdList) {
        this.notificationIdList = notificationIdList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        System.out.println("setGroupGroups");
        //this.ownerGroupId = 100;
        this.groupList = groupList;
    }

    public Group getOwnerGroups() {
        return ownerGroups;
    }

    public void setOwnerGroups(Group ownerGroups) {
        System.out.println("setOwnerGroups");
        this.ownerGroups = ownerGroups;
        //this.ownerGroupId = ownerGroups.getId();
    }

    @JsonGetter
    public Integer getOwnerGroupId() {
        if(this.ownerGroups != null){
            return this.ownerGroups.getId();
        }else{
            return null;
        }
    }

    @JsonGetter
    public List<Integer> getGroupsList(){
        if(this.groupList != null){
            return this.groupList.stream().map(Group::getId).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
