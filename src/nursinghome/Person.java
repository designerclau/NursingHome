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
public class Person {
    private String name;
    private String dateofbirth;
    private String ppsnumber;
    
    
    public Person(){
        
    }
    
    public Person(String name, String dateofbirth, String ppsnumber){
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.ppsnumber=ppsnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPpsnumber() {
        return ppsnumber;
    }

    public void setPpsnumber(String ppsnumber) {
        this.ppsnumber = ppsnumber;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", dateofbirth=" + dateofbirth + ", ppsnumber=" + ppsnumber + '}';
    }
    
    
    
    
    
}
