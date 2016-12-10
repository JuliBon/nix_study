package com.nixsolutions.bondarenko.study.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * @author Yuliya Bondarenko
 */

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table( name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name="name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy="role", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<User> users;

    public Role(){

    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}