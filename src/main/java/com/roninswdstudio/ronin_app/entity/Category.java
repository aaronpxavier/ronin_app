package com.roninswdstudio.ronin_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(	name = "categories")
public class Category {

    public Category () {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;


    @OneToMany(mappedBy = "cat", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "category2")
    private List<ExhibitImage> exhibitImages;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "subCat")
    private List<SubCategory> subCategories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExhibitImage> getExhibitImages() {
        return exhibitImages;
    }

    public void setExhibitImages(List<ExhibitImage> exhibitImages) {
        this.exhibitImages = exhibitImages;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    //    public List<SubCategory> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<SubCategory> categories) {
//        this.categories = categories;
//    }
}
