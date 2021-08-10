package com.roninswdstudio.ronin_app.springsecurity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Role() {}

    public Role(LinkedHashMap<String, Object> rolesMap) {
        try {
            this.id = (long) rolesMap.get("id");
        } catch (ClassCastException e) {
            this.id = (long) (int) rolesMap.get("id");
        }
        this.name = ERole.valueOf((String) rolesMap.get("name"));
    }

    public Role(ERole name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
