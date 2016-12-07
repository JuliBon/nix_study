package com.nixsolutions.bondarenko.study.entity;

public enum UserLibraryRole {
    USER(2L, "USER"),
    ADMIN(1L, "ADMIN"),
    ANONYMOUS(3L, "ANONIMUS");

    private Long id;
    private String name;

    UserLibraryRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
