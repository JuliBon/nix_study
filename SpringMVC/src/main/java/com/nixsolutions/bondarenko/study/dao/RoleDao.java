package com.nixsolutions.bondarenko.study.dao;

import com.nixsolutions.bondarenko.study.entity.Role;

/**
 * @author Yuliya Bondarenko
 */
public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);
}