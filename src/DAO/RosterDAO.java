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
import nursinghome.Roster;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * CRUD
 * @author Claudinea de Almeida
 */
public class RosterDAO {
    
    
    private Connection con = null;
    

    
    
    public boolean Save(Roster roster){

        con = ConnectionFactory.getConnection();
             
//        private Employee employee;   
//        private String floor;
//        private String date;
//        private String startTime;
//        private String finishTime;

        String sql="INSERT INTO roster (floor,date,starttime,finishtime,employeeid) values (?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, roster.getFloor());
            stmt.setString(2, roster.getDate());
            stmt.setString(3, roster.getStartTime());
            stmt.setString(4, roster.getFinishTime());
            stmt.setObject(5, roster.getEmployeeId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Saved sucessfuly");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not saved sucessfuly"+ex);
            return false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
        }
        
        
    }
    
    public List<Employee> readEmployee(){
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
                
      
                
                employees.add(employee);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  employees;
        
        
    }
    
//        public List<Employee> readEmployeeId(int id){
//        con = ConnectionFactory.getConnection();
//     
//        String sql="Select id,name from employee where id=?";
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        Employee employee = new Employee();
//        List<Employee> employees = new ArrayList();
//        try {
//            stmt=con.prepareStatement(sql);
//            stmt.setObject(1, employee.getEmployeeId());
//            rs = stmt.executeQuery();
//            
//            while (rs.next()){
//               
//                
//                employee.setEmployeeId(rs.getInt("id"));
//                employee.setName(rs.getString("name"));
//                
//      
//                
//                employees.add(employee);
//            }
//        } catch (SQLException ex) {
//             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
//        } finally{
//            ConnectionFactory.CloseConnection(con, stmt, rs);
//        }
//        
//         return  employees;
//        
//        
//    }
        
        public String readEmployeeId(int id){
        con = ConnectionFactory.getConnection();
     
        String sql="Select name from employee where id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String name=null;
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setObject(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                
                name=id + " - "+ rs.getString("name");

            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  name;
        
        
    }
    
    
    public boolean update(Employee employee){

        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE employee SET name = ?, dateofbirth = ?, ppsnumber =?, phone =?,email =?,address =?, certificate =?, specialist =?, country =?, passport =?,"
                   + "startdate =?, location =?,typeoftime =?,jobtitle =?, password =? WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
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
            stmt.setString(15, employee.getPassword());
            stmt.setInt(16, employee.getEmployeeId());
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
    public List<Roster> readRoster(){
        con = ConnectionFactory.getConnection();
     
        String sql=" Select a.*,b.name from roster a " 
                  +" INNER JOIN employee b ON b.id = a.employeeid ";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Roster> rosters = new ArrayList();
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                Roster roster = new Roster();
                
       
                roster.setId(rs.getInt("id"));
                roster.setFloor(rs.getString("floor"));
                roster.setDate(rs.getString("date"));
                roster.setStartTime(rs.getString("starttime"));
                roster.setFinishTime(rs.getString("finishtime"));
                roster.setEmployeeId(rs.getInt("employeeid"));
                String name= rs.getString("name");
                
                
                rosters.add(roster);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  rosters;
        
        
    }
    
    
    
    public boolean update(Roster roster){

        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE roster SET floor = ?, date =?, starttime =?,finishtime =? WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
           
            stmt.setString(1, roster.getFloor());
            stmt.setString(2, roster.getDate());
            stmt.setString(3, roster.getStartTime());
            stmt.setString(4, roster.getFinishTime());
            stmt.setObject(5, roster.getId());
            
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
    
    
    public List<Roster> search(int id){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from roster WHERE id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Roster> rosters = new ArrayList();
        Roster roster1 = new Roster();
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                
                roster1.setFloor(rs.getString("floor"));
                roster1.setDate(rs.getString("date"));
                roster1.setStartTime(rs.getString("starttime"));
                roster1.setFinishTime(rs.getString("finishtime"));
                roster1.setEmployeeId(rs.getInt("employeeid"));
      
                
                rosters.add(roster1);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  rosters;
        
        
    }
    
    public boolean delete(Roster roster){

        con = ConnectionFactory.getConnection();
     
        String sql="DELETE FROM roster WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, roster.getId());
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
    
}
