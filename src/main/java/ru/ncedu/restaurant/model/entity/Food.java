package ru.ncedu.restaurant.model.entity;

import ru.ncedu.restaurant.model.dao.Identified;

import java.io.Serializable;

public class Food implements Identified<Long>, Serializable {
    private Long id;
    private String name;
    private Integer cost;
    private Category category;

    public Food() {
    }

    public Food(String name, Integer cost) {
        this.name = name;
        this.cost = cost;
    }

    public Food(String name, Integer cost, Category category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer price) {
        this.cost = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Food)) {
            return false;
        }
        Food other = (Food) obj;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", category=" + (category == null ?
                    "null" :
                    category.getId() + "('" + category.getName()) + "')" +
                '}';
    }
}
