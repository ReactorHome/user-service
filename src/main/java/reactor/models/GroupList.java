package reactor.models;

import java.util.List;

public class GroupList {

    private List<Group> groups;

    public GroupList(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
