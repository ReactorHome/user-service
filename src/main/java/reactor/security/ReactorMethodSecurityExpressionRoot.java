package reactor.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.models.Account;
import reactor.models.User;
import reactor.repositories.GroupRepository;


public class ReactorMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    GroupRepository groupRepository;

    public ReactorMethodSecurityExpressionRoot(Authentication authentication, GroupRepository groupRepository) {
        super(authentication);
        this.groupRepository = groupRepository;
    }

    //|| group.getOwner().getId().equals(account.getId())
    public boolean isGroupMember(Integer groupId){
        Account account = ((User)this.getPrincipal()).account;
        return groupRepository.findById(groupId).map(group -> group.getAccountList().stream().anyMatch(account1 -> account1.getId().equals(account.getId()))).get();
        //return account.getGroups().stream().anyMatch((group -> group.getId().equals(groupId)));
    }

    public boolean isGroupOwner(Integer groupId){
        Account account = ((User)this.getPrincipal()).account;
        return groupRepository.findById(groupId).map(group -> group.getOwner().getId().equals(account.getId())).get();
    }

    @Override
    public void setFilterObject(Object o) {
        this.filterObject = o;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object o) {
        this.returnObject = o;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
