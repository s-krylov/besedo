package com.besedo.processing.json;

import java.math.BigDecimal;
import java.util.Objects;


public class Document {

    private Integer id;
    private String title;
    private BigDecimal price;
    private String description;

    Document() {
    }

    public Integer getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    void setPrice(float price) {
        this.price = new BigDecimal(price);
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(title, document.title) &&
                Objects.equals(price, document.price) &&
                Objects.equals(description, document.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, description);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
