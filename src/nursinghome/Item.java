package nursinghome;

import java.io.FileInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Claudinea de Almeida
 */
public class Item {
    private int itemId;
    private String category;
    private String description;
    private String picture;
   
    
    
    public Item(){}

    public Item(int itemId, String category, String description, String picture) {
        this.itemId = itemId;
        this.category = category;
        this.description = description;
        this.picture = picture;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Item{" + "itemId=" + itemId + ", category=" + category + ", description=" + description + ", picture=" + picture + '}';
    }

   
}
