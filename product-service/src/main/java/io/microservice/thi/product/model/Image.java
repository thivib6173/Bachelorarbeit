package io.microservice.thi.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.enterprise.inject.Model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="images")
@NamedQuery(name = "Image.findByName", query ="SELECT n FROM Image n WHERE n.name = :name")
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_generator")
    @SequenceGenerator(name="images_generator", sequenceName = "generate_id", allocationSize=1)
    private Integer id;

    @JsonIgnore
    private byte[] file;

    private String name;


    private String content;

    public Image() {

    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
