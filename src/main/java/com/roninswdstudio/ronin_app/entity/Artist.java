package com.roninswdstudio.ronin_app.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    private Date dob;

    private String bio;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<ExhibitImage> exhibitImages;

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<ExhibitImage> getExhibitImages() {
        return exhibitImages;
    }

    public void setExhibitImages(List<ExhibitImage> exhibitImages) {
        this.exhibitImages = exhibitImages;
    }
}