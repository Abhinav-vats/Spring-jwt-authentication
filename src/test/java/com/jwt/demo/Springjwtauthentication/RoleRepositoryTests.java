package com.jwt.demo.Springjwtauthentication;


import com.jwt.demo.Springjwtauthentication.user.Role;
import com.jwt.demo.Springjwtauthentication.user.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    RoleRepository repository;

    @Test
    public void testCreateRoles(){
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_EDITOR");
        Role customer = new Role("ROLE_CUSTOMER");
        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(editor);
        roles.add(customer);

        repository.saveAll(roles);

        long count  = repository.count();
        System.out.println(count==3);

    }

    @Test
    public void testListAllRoles(){
        List<Role> roles = repository.findAll();
        System.out.println(roles.toString());
    }


}
