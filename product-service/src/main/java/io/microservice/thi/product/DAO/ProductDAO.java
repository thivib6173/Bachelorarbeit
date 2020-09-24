package io.microservice.thi.product.DAO;

import io.microservice.thi.product.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@RequestScoped
public class ProductDAO {

  @PersistenceContext(name = "product_db")
  private EntityManager em;

  public void createProduct(Product product) {
    em.persist(product);
  }

  public Product readProduct(int id) {
    return em.find(Product.class, id);
  }

  public void updateProduct(Product Product) {
    em.merge(Product);
  }

  public void deleteProduct(Product Product) {
    em.remove(Product);
  }

  public List<Product> readAllProducts() {
    return em.createNamedQuery("Product.findAll", Product.class).getResultList();
  }


}
