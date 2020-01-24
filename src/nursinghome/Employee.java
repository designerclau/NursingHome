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
public class Employee extends Person {
    private int employeeId;
    private String phone;
    private String email;
    private String address;
    private String startdate;
    private String certificate;
    private String specialist;
    private String jobtitle;
    private String location;
    private String typeoftime;
    private String nationality;
    private String passport;
    private String password;
    
    public Employee(){}
    
    public Employee(String name, String email, String jobtitle, String typeoftime, String password){
        this.setName(name);
        this.email=email;
        this.jobtitle=jobtitle;
        this.typeoftime=typeoftime;
        this.password=password;
    }

    public Employee(int employeeId, String phone, String email, String address, String startdate, String certificate, String specialist, String jobtitle, String location, String typeoftime, String nationality, String passport, String password) {
        this.employeeId = employeeId;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.startdate = startdate;
        this.certificate = certificate;
        this.specialist = specialist;
        this.jobtitle = jobtitle;
        this.location = location;
        this.typeoftime = typeoftime;
        this.nationality = nationality;
        this.passport = passport;
        this.password = password;
    }

    public Employee(int employeeId, String name, String dateofbirth, String ppsnumber) {
        super(name, dateofbirth, ppsnumber);
        this.employeeId = employeeId;
        
    }
    
    

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeoftime() {
        return typeoftime;
    }

    public void setTypeoftime(String typeoftime) {
        this.typeoftime = typeoftime;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" + "employeeId=" + employeeId + ", phone=" + phone + ", email=" + email + ", startdate=" + startdate + ", certificate=" + certificate + ", specialist=" + specialist + ", jobtitle=" + jobtitle + ", location=" + location + ", typeoftime=" + typeoftime + ", nationality=" + nationality + ", passport=" + passport + ", password=" + password + '}';
    }
    
    
    
}
