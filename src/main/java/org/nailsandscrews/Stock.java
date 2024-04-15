package org.nailsandscrews;

import javax.persistence.*;

@Entity
public class Stock {
    private int id;
    private String product_type;
    private String material;

    public Stock() {}

    public Stock(int id, String product_type, String material) {
        this.id = id;
        this.product_type = product_type;
        this.material = material;
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}

