package io.microservice.thi.productstock.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product_stock")
@NamedQuery(name = "Product.findAll", query = "SELECT c FROM ProductStock c")
@NamedQuery(name = "Product.findByProductId", query = "SELECT c FROM ProductStock c WHERE c.id = :id")
public class ProductStock implements Serializable {
    private static final long serialVersionUID = 1L;


    public ProductStock(String name, Integer stock){
        this.product = name;
        this.stock = stock;
    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name="product_generator", sequenceName = "generate_id", allocationSize=1)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "product")
    private String product;



    @Column(name = "stock")
    private  Integer stock;

    public ProductStock() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String name) {
        this.product = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
