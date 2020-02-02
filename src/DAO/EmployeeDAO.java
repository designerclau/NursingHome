/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import nursinghome.Employee;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Tools;

/**
 * CRUD
 * @author Claudinea de Almeida
 */
public class EmployeeDAO {
    
    
    private Connection con = null;
    

    
    
    public boolean Save(Employee employee){

        con = ConnectionFactory.getConnection();
        String pass = employee.getPassword();     
        boolean returning=false;
        
        String sql="INSERT INTO employee (name,dateofbirth,ppsnumber,phone,email,address,certificate,specialist,country,passport,startdate,location,typeoftime,jobtitle,password,level) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            Tools tools = new Tools();
            String formatedpass = tools.checkPassword(pass);
            
                      
            stmt=con.prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDateofbirth());
            stmt.setString(3, employee.getPpsnumber());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getCertificate());
            stmt.setString(8, employee.getSpecialist());
            stmt.setString(9, employee.getNationality());
            stmt.setString(10, employee.getPassport());
            stmt.setString(11, employee.getStartdate());
            stmt.setString(12, employee.getLocation());
            stmt.setString(13, employee.getTypeoftime());
            stmt.setString(14, employee.getJobtitle());
            stmt.setString(15, formatedpass);
            stmt.setInt(16,employee.getLevel());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Saved sucessfuly");
            returning= true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not saved sucessfuly"+ex);
            returning= false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
        }
        
        return returning;
    }
    
    
    public List<Employee> read(){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from employee";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList();
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                Employee employee = new Employee();
                
                employee.setEmployeeId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setDateofbirth(rs.getString("dateofbirth"));
                employee.setPpsnumber(rs.getString("ppsnumber"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setAddress(rs.getString("address"));
                employee.setCertificate(rs.getString("certificate"));
                employee.setSpecialist(rs.getString("specialist"));
                employee.setNationality(rs.getString("country"));
                employee.setPassport(rs.getString("passport"));
                employee.setStartdate(rs.getString("startdate"));
                employee.setLocation(rs.getString("location"));
                employee.setTypeoftime(rs.getString("typeoftime"));
                employee.setJobtitle(rs.getString("jobtitle"));
                employee.setPassword(rs.getString("password"));
                employee.setLevel(rs.getInt("level"));
                
      
                
                employees.add(employee);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  employees;
        
        
    }
    
    
    
    public boolean update(Employee employee){

        String pass = employee.getPassword();  
        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE employee SET name = ?, dateofbirth = ?, ppsnumber =?, phone =?,email =?,address =?, certificate =?, specialist =?, country =?, passport =?,"
                   + "startdate =?, location =?,typeoftime =?,jobtitle =?, password =? , level = ? WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            
            
            Tools tools = new Tools();
            String formatedpass = tools.checkPassword(pass);
            
            stmt=con.prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDateofbirth());
            stmt.setString(3, employee.getPpsnumber());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getCertificate());
            stmt.setString(8, employee.getSpecialist());
            stmt.setString(9, employee.getNationality());
            stmt.setString(10, employee.getPassport());
            stmt.setString(11, employee.getStartdate());
            stmt.setString(12, employee.getLocation());
            stmt.setString(13, employee.getTypeoftime());
            stmt.setString(14, employee.getJobtitle());
            stmt.setString(15, formatedpass);
            stmt.setInt(16,employee.getLevel());
            stmt.setInt(17, employee.getEmployeeId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated sucessfuly");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not updated sucessfuly"+ex);
            return false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
        }
        
        
    }
    
    
    public List<Employee> search(int id){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from employee WHERE id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList();
        Employee employee1 = new Employee();
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                employee1.setEmployeeId(rs.getInt("id"));
                employee1.setName(rs.getString("name"));
                employee1.setDateofbirth(rs.getString("dateofbirth"));
                employee1.setPpsnumber(rs.getString("ppsnumber"));
                employee1.setPhone(rs.getString("phone"));
                employee1.setEmail(rs.getString("email"));
                employee1.setAddress(rs.getString("address"));
                employee1.setCertificate(rs.getString("certificate"));
                employee1.setSpecialist(rs.getString("specialist"));
                employee1.setNationality(rs.getString("country"));
                employee1.setPassport(rs.getString("passport"));
                employee1.setStartdate(rs.getString("startdate"));
                employee1.setLocation(rs.getString("location"));
                employee1.setTypeoftime(rs.getString("typeoftime"));
                employee1.setJobtitle(rs.getString("jobtitle"));
                employee1.setPassword(rs.getString("password"));
                employee1.setLevel(rs.getInt("level"));
                
                employees.add(employee1);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  employees;
        
        
    }
    
     public boolean delete(Employee employee){

        con = ConnectionFactory.getConnection();
     
        String sql="DELETE FROM employee WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, employee.getEmployeeId());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deleted sucessfuly");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not deleted sucessfuly"+ex);
            return false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
        }
        
        
    }
     
     
     public String login(String email, String password){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from employee WHERE email=? and password=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Employee employee1 = new Employee();
        boolean isvalid=false;
        String returning=null;
        
        try {
            
            Tools tools = new Tools();
            String formatedpass = tools.checkPassword(password);
            
            stmt=con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, formatedpass);
            rs = stmt.executeQuery();
            
            if (rs.next()){
                
               returning="true"+" - "+rs.getInt("id")+" - "+rs.getString("name")+" - "+rs.getInt("level");
               isvalid=true;  
               
               
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. User was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  returning;
        
        
    }
}
