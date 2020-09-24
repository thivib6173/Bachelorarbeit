package io.microservice.thi.product.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product")
@NamedQuery(name = "Product.findAll", query = "SELECT c FROM Product c")
@NamedQuery(name = "Product.findByProductId", query = "SELECT c FROM Product c WHERE c.id = :id")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;


    public Product(String name, String description){
        this.name = name;
        this.description = description;
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name="product_generator", sequenceName = "generate_id", allocationSize=1)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String image;

    private String category;


    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
