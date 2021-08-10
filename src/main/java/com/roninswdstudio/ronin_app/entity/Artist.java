package com.roninswdstudio.ronin_app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(	name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String bio;

    private String username;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ExhibitImage> exhibitImages;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Artist() {};

    public Artist(LinkedHashMap<String, Object> artistMap) {
        try {
            this.id = (long) artistMap.get("id");
        } catch (ClassCastException e) {
            this.id = (long) (int) artistMap.get("id");
        }
        this.firstName = (String) artistMap.get("firstName");
        this.lastName = (String) artistMap.get("lastName");
        this.bio = (String) artistMap.get("bio");
        this.username = (String) artistMap.get("username");
        this.exhibitImages = ((ArrayList<LinkedHashMap<String, Object>>) artistMap.get("exhibitImages"))
                .parallelStream()
                .map(ExhibitImage::new)
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ExhibitImage> getExhibitImages() {
        return exhibitImages;
    }

    public void setExhibitImages(List<ExhibitImage> exhibitImages) {
        this.exhibitImages = exhibitImages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}