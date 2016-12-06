package com.nixsolutions.bondarenko.study.entity;

public enum UserLibraryRole {
    USER(2L, "User"),
    ADMIN(1L, "Admin"),
    ANONYMOUS(3L, "Anonimus");

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
