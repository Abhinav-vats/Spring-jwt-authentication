package com.jwt.demo.Springjwtauthentication.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    public Role(Integer id){
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Role(String name){
        super();
        this.name = name;
    }



}
