package com.bookcharm.app.model;


import javax.persistence.*;

@Entity
@Table(name = "Categorys")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "categoryId")
    private Long categoryId;

    @JoinColumn(name = "categoryType")
    private String categoryType;

    // Constructors
    public Category() {
    }

    public Category(String categoryType) {
        this.categoryType = categoryType;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    // Other methods, if needed

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}
