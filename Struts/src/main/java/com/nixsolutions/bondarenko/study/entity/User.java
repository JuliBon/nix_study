package com.nixsolutions.bondarenko.study.entity;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;


    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;


    public User() {
    }

    public User(Long id, String login, String password, String email, String firstName,
                String lastName, Date birthday, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    @RequiredStringValidator(message = "login required")
    @RegexFieldValidator(regex = "^[a-zA-Z](([._-][a-zA-Z0-9])|[a-zA-Z0-9])*$",
            message = "3-15 characters, beginning with letter. Can include letters, numbers, dashes, and underscores")
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @RequiredStringValidator(message = "pasword required")
    @RegexFieldValidator(regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$",
            message = "at least one number and one uppercase and lowercase letters")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }


    @RequiredStringValidator(message = "email required")
    @RegexFieldValidator(regex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
            message = "not a well-formed email address")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @RequiredStringValidator(message = "first name required")
    @RegexFieldValidator(regex = "[A-Za-z]+",
            message = "one or more letters")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @RequiredStringValidator(message = "last name required")
    @RegexFieldValidator(regex = "[A-Za-z]+",
            message = "one or more letters")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}