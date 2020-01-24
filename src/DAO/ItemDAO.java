/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import nursinghome.Item;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
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
public class ItemDAO {
    
    
    private Connection con = null;
    

//    private int itemId;
//    private String category;
//    private String description;
//    private Byte picture;
    
    public boolean Save(Item item){

        con = ConnectionFactory.getConnection();
             
     
        String sql="INSERT INTO item (category,description,picture) values (?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, item.getCategory());
            stmt.setString(2, item.getDescription());
            stmt.setString(3, item.getPicture());
           
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
    
    
    public List<Item> readItem(){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from item";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> items = new ArrayList();
      
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                Item item = new Item();
                
               
                item.setItemId(rs.getInt("id"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setPicture(rs.getString("picture"));
              
      
                
                items.add(item);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  items;
        
        
    }
    
     
    
    
    
    public boolean update(Item item){

        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE item SET category = ?, description = ?, picture =? WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, item.getCategory());
            stmt.setString(2, item.getDescription());
            stmt.setString(3, item.getPicture());
            stmt.setInt(4, item.getItemId());
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
    
     public boolean delete(Item item){

        con = ConnectionFactory.getConnection();
     
        String sql="DELETE FROM item WHERE id=?";
        PreparedStatement stmt = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, item.getItemId());
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
    
    
     public List<Item> searchItem(int id){
        con = ConnectionFactory.getConnection();
     
       // String sql="Select * from resident WHERE id=?";
         String sql="SELECT * FROM item "
                   +"WHERE item.id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> items = new ArrayList();
        Item item1 = new Item();
       
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                item1.setItemId(rs.getInt("id"));
                item1.setCategory(rs.getString("category"));
                item1.setDescription(rs.getString("description"));
                item1.setPicture(rs.getString("picture"));
                
                
                items.add(item1);
                
                
                
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  items;
        
        
     }
     
     
      public List<Item> searchCategory(String category){
        con = ConnectionFactory.getConnection();
     
       // String sql="Select * from resident WHERE id=?";
         String sql="SELECT * FROM item "
                   +"WHERE category like ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> items = new ArrayList();
        Item item1 = new Item();
       
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, category);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                item1.setItemId(rs.getInt("id"));
                item1.setCategory(rs.getString("category"));
                item1.setDescription(rs.getString("description"));
                item1.setPicture(rs.getString("picture"));
                
                
                items.add(item1);
                
                
                
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  items;
        
        
     }
    
}
