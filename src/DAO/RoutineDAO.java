/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConnectionFactory;
import nursinghome.DailyRoutine;
import nursinghome.Employee;
import nursinghome.Item;
import nursinghome.Resident;
import nursinghome.ResidentHistory;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.Tools;

/**
 *
 * @author Claudinea de Almeida
 */
public class RoutineDAO {
     private Connection con = null;

    public boolean Save(DailyRoutine routine){

        Tools tool = new Tools();
        String actualdate =tool.checkActualDate();
        
        con = ConnectionFactory.getConnection();
             


        String sql="INSERT INTO routine (residentid,date,meal,itemid, quantity,comments,employeeid) values (?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            stmt=con.prepareStatement(sql);
            System.out.println(routine.getResident().getResidentId());
            stmt.setInt(1, routine.getResident().getResidentId());
            stmt.setString(2, actualdate);
            stmt.setString(3, routine.getMeal());
            
            stmt.setInt(4, routine.getItem().getItemId());
            stmt.setString(5, routine.getQuantity());
            stmt.setString(6, routine.getComments());
            stmt.setInt(7, routine.getEmployee().getEmployeeId());

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
    

    

    public boolean update(DailyRoutine routine){
        
        Tools tool = new Tools();
        String actualdate =tool.checkActualDate();
        
        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE routine SET date = ?, meal = ?, itemid =?, quantity =?,comments =? WHERE id=?";
       
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, actualdate);
            stmt.setString(2, routine.getMeal());
            stmt.setInt(3, routine.getItem().getItemId());
            stmt.setString(4, routine.getQuantity());
            stmt.setString(5, routine.getComments());
            System.out.println(routine.getId());
            stmt.setInt(6, routine.getId());

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
    
    
        //private int      id;
//    private Resident resident;
//    private String   date;
//    private String   meal;
//    private String   category;
//    private Item     item;
//    private String   quantity;
//    private String   comments;
//    private Employee employee;
    
    
    public List<DailyRoutine> readroutine(){
        con = ConnectionFactory.getConnection();
     
        String sql=" Select a.*,b.*,c.*,d.* from routine a " 
                  +" INNER JOIN resident b ON b.id = a.residentid "
                  +" INNER JOIN item c ON c.id = a.itemid "
                  +" INNER JOIN employee d ON d.id = a.employeeid ";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DailyRoutine> routines = new ArrayList();
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                DailyRoutine routine = new DailyRoutine();
                
                
                
       
                routine.setId(rs.getInt("id"));
                
                Resident resident = new Resident();
                
                resident.setResidentId(rs.getInt("residentid"));
                resident.setName(rs.getString("name"));
                resident.setDateofbirth(rs.getString("dateofbirth"));
                resident.setPpsnumber(rs.getString("ppsnumber"));
                resident.setFloor(rs.getString("floor"));
                resident.setRoom(rs.getInt("room"));

                routine.setResident(resident);
                
                routine.setDate(rs.getString("date"));
                routine.setMeal(rs.getString("meal"));
                
                
                Item item = new Item();
                
               
                item.setItemId(rs.getInt("itemid"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPicture(rs.getString("picture"));
                
                routine.setItem(item);
                
                
                routine.setQuantity(rs.getString("quantity"));
                routine.setComments(rs.getString("comments"));
                
                Employee employee = new Employee();
                
                employee.setEmployeeId(rs.getInt("employeeid"));
                employee.setName(rs.getString("name"));
                employee.setDateofbirth(rs.getString("dateofbirth"));
                employee.setDateofbirth(rs.getString("ppsnumber"));
                employee.setDateofbirth(rs.getString("phone"));
                employee.setDateofbirth(rs.getString("email"));
                employee.setDateofbirth(rs.getString("address"));
                employee.setDateofbirth(rs.getString("certificate"));
                employee.setDateofbirth(rs.getString("specialist"));
                employee.setDateofbirth(rs.getString("country"));
                employee.setDateofbirth(rs.getString("passport"));
                employee.setDateofbirth(rs.getString("startdate"));
                employee.setDateofbirth(rs.getString("location"));
                employee.setDateofbirth(rs.getString("typeoftime"));
                employee.setDateofbirth(rs.getString("jobtitle"));
                employee.setDateofbirth(rs.getString("password"));
                
                routine.setEmployee(employee);
               // String name= rs.getString("name");
                
                
                routines.add(routine);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  routines;
        
        
    }
    
    
    
//    public boolean update(DailyRoutine routine){
//
//        con = ConnectionFactory.getConnection();
//     
//        String sql="UPDATE routine SET floor = ?, date =?, starttime =?,finishtime =? WHERE id=?";
//        PreparedStatement stmt = null; 
//        
//        try {
//            stmt=con.prepareStatement(sql);
//           
//            stmt.setString(1, routine.getFloor());
//            stmt.setString(2, routine.getDate());
//            stmt.setString(3, routine.getStartTime());
//            stmt.setString(4, routine.getFinishTime());
//            stmt.setObject(5, routine.getId());
//            
//            stmt.executeUpdate();
//            
//            JOptionPane.showMessageDialog(null, "Updated sucessfuly");
//            return true;
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error. It was not updated sucessfuly"+ex);
//            return false;
//        } finally{
//            ConnectionFactory.CloseConnection(con, stmt);
//        }
//        
//        
//    }
    
    
    public List<DailyRoutine> search(int id){
        con = ConnectionFactory.getConnection();
     
        String sql=" Select a.*,b.* from routine a"
                  +" INNER JOIN item b ON b.id = a.itemid "
                  +" WHERE a.id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DailyRoutine> routines = new ArrayList();
        DailyRoutine routine1 = new DailyRoutine();
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                
                routine1.setDate(rs.getString("date"));
                routine1.setMeal(rs.getString("meal"));
                
                
                Item item = new Item();
                
               
                item.setItemId(rs.getInt("itemid"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPicture(rs.getString("picture"));
                
                routine1.setItem(item);
                
                
                
                routine1.setQuantity(rs.getString("quantity"));
                routine1.setComments(rs.getString("comments"));
//                routine1.setEmployeeId(rs.getInt("employeeid"));
//      
                
                routines.add(routine1);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  routines;
        
        
    }
    
    public boolean delete(DailyRoutine routine){

        con = ConnectionFactory.getConnection();
     
        String sql="DELETE FROM routine WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, routine.getId());
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
