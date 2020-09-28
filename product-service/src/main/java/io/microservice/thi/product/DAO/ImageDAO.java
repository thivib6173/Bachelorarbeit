package io.microservice.thi.product.DAO;

import io.microservice.thi.product.model.Image;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@RequestScoped
public class ImageDAO {

    @PersistenceContext(name = "product_db")
    private EntityManager em;


    public void persist(Image object) {
        em.persist(object);
    }

    public void merge(Image object) {
        em.merge(object);
    }


    public Image createImage(byte[] dokument,String content, String name) {
        Image im = new Image();
        im.setFile(dokument);
        im.setName(name);
        im.setContent(content);
        em.persist(im);
        return im;
    }


    public Image getImageById(int id) {
        return em.find(Image.class, id);
    }

    public Integer deleteImage(int id) {
        Image dok = em.find(Image.class, id);
        if(dok == null) {
            return 0;
        }
        em.remove(dok);
        return 1;
    }

}
