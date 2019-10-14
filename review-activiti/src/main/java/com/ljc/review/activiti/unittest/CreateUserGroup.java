package com.ljc.review.activiti.unittest;

import com.ljc.review.activiti.ReviewActivitiApplication;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReviewActivitiApplication.class)
public class CreateUserGroup {

    @Autowired
    IdentityService identityService;

    @Test
    public void addUser() {
        User user = identityService.newUser("tom");
        user.setFirstName("Tutu");
        user.setLastName("Li");
        user.setPassword("111111");
        user.setEmail("tom@qq.com");
        identityService.saveUser(user);
    }

    @Test
    public void queryUser() {
        User user = identityService.createUserQuery().userId("nick").singleResult();
        Assert.assertNotNull(user);
    }

    @Test
    public void deleteUser() {
        identityService.deleteUser("nick");
    }

    @Test
    public void addGroup() {
        Group group = identityService.newGroup("manager");
        group.setName("managerGroup");
        group.setType("aaa");
        identityService.saveGroup(group);
    }

    @Test
    public void queryGroup() {
        Group group = identityService.createGroupQuery().groupId("manager").singleResult();
        Assert.assertNotNull(group);
    }

    @Test
    public void deleteGroup() {
        identityService.deleteGroup("manager");
    }

    @Test
    public void addMembership() {
        identityService.createMembership("tom", "manager");
    }

    @Test
    public void deleteMembership() {
        identityService.deleteMembership("nick", "manager");
    }

    @Test
    public void queryMembership() {
        //查询属于组manager_group的用户
        List<User> usersInGroup = identityService.createUserQuery().memberOfGroup("manager").list();
        Assert.assertNotEquals(0, usersInGroup.size());
        //查询nick所属于的组
        List<Group> groupsForUser = identityService.createGroupQuery().groupMember("nick").list();
        Assert.assertNotEquals(0, groupsForUser.size());
    }

}
