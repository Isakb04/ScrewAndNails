package org.nailsandscrews;

import javax.persistence.*;

@Entity
public class Stock {
    private int id;
    private String type;
    private String product_type;
    private String material;
    private String length;
    private float buying_price;
    private float selling_price;
    private int quantity;
    private String supplier;
    private String date_added;
    private String last_updated;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public float getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(float buying_price) {
        this.buying_price = buying_price;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }


        /* public Stock() {}

    public Stock(int id, String product_type, String material , String length, int buying_price, int selling_price, int quantity, String supplier, String date_added, String last_updated) {
        this.id = id;
        this.product_type = product_type;
        this.material = material;
        this.length = length;
        this.buying_price = buying_price;
        this.selling_price = selling_price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.date_added = date_added;
        this.last_updated = last_updated;
    } */
}

