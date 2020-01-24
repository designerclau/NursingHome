package nursinghome;


import nursinghome.Resident;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Claudinea de Almeida
 */
public class DailyRoutine {
    private int      id;
    private Resident resident;
    private String   date;
    private String   meal;
    private Item     item;
    private String   quantity;
    private String   comments;
    private Employee employee;
   
    
    
    public DailyRoutine(){}

    public DailyRoutine(int id, Resident resident, String date, String meal, Item item, String quantity, String comments, Employee employee) {
        this.id = id;
        this.resident = resident;
        this.date = date;
        this.meal = meal;
        this.item = item;
        this.quantity = quantity;
        this.comments = comments;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "DailyRoutine{" + "id=" + id + ", resident=" + resident + ", date=" + date + ", meal=" + meal + ", item=" + item + ", quantity=" + quantity + ", comments=" + comments + ", employee=" + employee + '}';
    }

   
   

   
    
    
}
