package nursinghome;


import nursinghome.Employee;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Claudinea de Almeida
 */
public class Roster {
     private int id;   
     private String floor;
     private String date;
     private String startTime;
     private String finishTime;
     private int employeeId;
     
     
     public Roster(){};

    public Roster(int id, String floor, String date, String startTime, String finishTime, int employeeId) {
        this.id = id;
        this.floor = floor;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Roster{" + "id=" + id + ", floor=" + floor + ", date=" + date + ", startTime=" + startTime + ", finishTime=" + finishTime + ", employeeId=" + employeeId + '}';
    }

   
     
    
}
