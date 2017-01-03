package com.nixsolutions.bondarenko.study.entity;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public static List<String> getRoleNames() {
        List<String> roleList = new ArrayList<>();
        for (UserLibraryRole role : UserLibraryRole.values()) {
            roleList.add(role.toString());
        }
        return roleList;
    }
}
