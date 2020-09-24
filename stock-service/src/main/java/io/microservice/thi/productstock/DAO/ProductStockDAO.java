package io.microservice.thi.productstock.DAO;

import io.microservice.thi.productstock.model.ProductStock;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class ProductStockDAO {

  @PersistenceContext(name = "productstock_db")
  private EntityManager em;

  public void createProduct(ProductStock product) {
    em.persist(product);
  }

  public ProductStock readProduct(int id) {
    return em.find(ProductStock.class, id);
  }
  public ProductStock readProductName(String name) {return em.find(ProductStock.class, name); }

  public void updateProduct(ProductStock Product) {
    em.merge(Product);
  }

  public void deleteProduct(ProductStock Product) {
    em.remove(Product);
  }

  public List<ProductStock> readAllProducts() {
    return em.createNamedQuery("Product.findAll", ProductStock.class).getResultList();
  }


}
