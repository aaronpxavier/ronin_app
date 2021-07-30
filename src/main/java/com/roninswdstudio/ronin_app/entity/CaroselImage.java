package com.roninswdstudio.ronin_app.entity;

import javax.persistence.*;

@Entity
@Table(	name = "carosel_images")
public class CaroselImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "exhibit_id")
    private long exhibitId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(long exhibitId) {
        this.exhibitId = exhibitId;
    }
}
