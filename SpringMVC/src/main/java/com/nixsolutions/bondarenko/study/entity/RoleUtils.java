package com.nixsolutions.bondarenko.study.entity;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {
    private RoleUtils(){}

    public static List<String> getRoleNames() {
        List<String> roleList = new ArrayList<>();
        for (UserRole role : UserRole.values()) {
            roleList.add(role.toString());
        }
        return roleList;
    }
}