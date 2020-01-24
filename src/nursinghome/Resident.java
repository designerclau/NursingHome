package nursinghome;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Claudinea de Almeida
 */
public class Resident extends Person{
    
    private int residentId;
    private String dateofregister;
    private String floor;
    private int room;
    private String picture;
    
    
    public Resident(){}
    
    public Resident(String picture,String dateofregister, String floor, int room){
        this.picture=picture;
        this.dateofregister=dateofregister;
        this.floor=floor;
        this.room=room;
     
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDateofregister() {
        return dateofregister;
    }

    public void setDateofregister(String dateofregister) {
        this.dateofregister = dateofregister;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Resident{" + "residentId=" + residentId + ", picture=" + picture + ", dateofregister=" + dateofregister + ", floor=" + floor + ", room=" + room + '}';
    }
    
    
    
}
