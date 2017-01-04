package com.nixsolutions.bondarenko.study.entity;

import javax.persistence.*;

/**
 * @author Yuliya Bondarenko
 */
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public Role() {
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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;

    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Role) {
            Role role = (Role) other;
            result = role.getId().equals(id) && role.getName().equals(name);
        }
        return result;
    }
}