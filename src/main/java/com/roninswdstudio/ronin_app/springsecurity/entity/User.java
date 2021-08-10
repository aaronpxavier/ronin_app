package com.roninswdstudio.ronin_app.springsecurity.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roninswdstudio.ronin_app.entity.Artist;
import com.roninswdstudio.ronin_app.springsecurity.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String password;

    private boolean locked;

    private boolean enabled;

    private Date dob;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Artist artist;

    public User() {
    }

    public User(LinkedHashMap<String, Object> userHashMap) {
        try {
            this.id = (long) userHashMap.get("id");
        } catch (ClassCastException e) {
            this.id = (long) (int) userHashMap.get("id");
        }

        this.email = (String) userHashMap.get("email");
        this.locked = (boolean) userHashMap.get("locked");
        this.enabled = (boolean) userHashMap.get("enabled");
        this.dob = new Date((long) userHashMap.get("dob"));
        this.roles = ((ArrayList<LinkedHashMap<String, Object>>) userHashMap.get("roles"))
                .parallelStream()
                .map(Role::new)
                .collect(Collectors.toList());
        this.artist = new Artist((LinkedHashMap<String, Object>) userHashMap.get("artist"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public boolean isAdmin() {
        return roles.parallelStream()
                .anyMatch(r -> r.getName() == ERole.ROLE_ADMIN);
    }
}
