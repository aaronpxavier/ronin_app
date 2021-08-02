package com.roninswdstudio.ronin_app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(	name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Artist() {};

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