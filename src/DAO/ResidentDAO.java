/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConnectionFactory;
import nursinghome.Employee;
import nursinghome.NextKind;
import nursinghome.Resident;
import nursinghome.ResidentHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Claudinea de Almeida
 */
public class ResidentDAO {
    
    private Connection con = null;
    

    
    
    public int Save(Resident resident){

        con = ConnectionFactory.getConnection();
        boolean returning = false;  
        int lastId=0;
     
        String sql="INSERT INTO resident (name,dateofbirth,ppsnumber,floor,room,picture) values (?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            //stmt=con.prepareStatement(sql);
            stmt= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, resident.getName());
            stmt.setString(2, resident.getDateofbirth());
            stmt.setString(3, resident.getPpsnumber());
            stmt.setString(4, resident.getFloor());
            stmt.setString(5, String.valueOf(resident.getRoom()));
            stmt.setString(6, resident.getPicture());
           
            stmt.executeUpdate();
            
          //  JOptionPane.showMessageDialog(null, "Saved sucessfuly");
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
            returning= true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. Resident was not saved sucessfuly"+ex);
            returning= false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
           
        }
     
        return lastId;
       
    }
    
    public boolean SaveHistory(ResidentHistory residenth){

        con = ConnectionFactory.getConnection();
        boolean returning = false;       
        
        
     
        String sql="INSERT INTO residenthistory (id,diet,fluid,assistanceof,mobility,action,history,presentcondition) values (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            
      //      ResidentHistory residenth = new ResidentHistory();
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, residenth.getResidentId());
            stmt.setString(2, residenth.getDiet());
            stmt.setString(3, residenth.getFluid());
            stmt.setInt(4, residenth.getAssistanceof());
            stmt.setString(5, residenth.getMobility());
            stmt.setString(6, residenth.getAction());
            stmt.setString(7, residenth.getHistory());
            stmt.setString(8, residenth.getPresentCondition());
           
            stmt.executeUpdate();
        //    JOptionPane.showMessageDialog(null, "Saved sucessfuly");
            returning= true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. Resident history was not saved sucessfuly"+ex);
            returning= false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
           
        }
        
       return returning; 
    }
    
    
        public boolean SaveKind(NextKind nextk){

           
        con = ConnectionFactory.getConnection();
        boolean returning = false;        
     
        String sql="INSERT INTO nextkind (kindname,kindemail,kindphone,kindaddress,residentid) values (?,?,?,?,?)";
        PreparedStatement stmt = null;
        
        try {
            stmt=con.prepareStatement(sql);
            
            stmt.setString(1, nextk.getNextName());
            stmt.setString(2, nextk.getNextEmail());
            stmt.setString(3, nextk.getNextPhone());
            stmt.setString(4, nextk.getNextAddress());
            stmt.setInt(5, nextk.getResidentId());
           
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Saved sucessfuly");
            returning= true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. Next Kind was not saved sucessfuly"+ex);
            returning= false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
           
        }
        
       return returning; 
    }
    
    public List<ResidentHistory> readResident(){
        con = ConnectionFactory.getConnection();
     
       String sql="SELECT resident.*,residenthistory.* FROM resident "
                 +"INNER JOIN residenthistory  ON residenthistory.id = resident.id";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ResidentHistory> residenths = new ArrayList();
        
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                ResidentHistory residenth = new ResidentHistory();
               
                residenth.setResidentId(rs.getInt("id"));
                residenth.setName(rs.getString("name"));
                residenth.setDateofbirth(rs.getString("dateofbirth"));
                residenth.setPpsnumber(rs.getString("ppsnumber"));
                residenth.setRoom(rs.getInt("room"));
                residenth.setFloor(rs.getString("floor"));
                residenth.setPicture(rs.getString("picture"));
                residenth.setDiet(rs.getString("diet"));
                residenth.setFluid(rs.getString("fluid"));
                residenth.setAssistanceof(rs.getInt("assistanceof"));
                residenth.setMobility(rs.getString("mobility"));
                residenth.setAction(rs.getString("action"));
                residenth.setHistory(rs.getString("history"));
                residenth.setPresentCondition(rs.getString("presentcondition"));
                
                
                residenths.add(residenth);
               
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
           
        }
        
         return  residenths;
        
        
    }
    
     public List<NextKind> readResidentKind(int id){
        con = ConnectionFactory.getConnection();
     
       String sql="SELECT * FROM nextkind WHERE residentid = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        List<NextKind> nextkinds = new ArrayList();
        try {
            stmt=con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                NextKind nextkind = new NextKind();
                
                nextkind.setNextId(rs.getInt("id"));
                nextkind.setNextName(rs.getString("kindname"));
                nextkind.setNextEmail(rs.getString("kindemail"));
                nextkind.setNextPhone(rs.getString("kindphone"));
                nextkind.setNextAddress(rs.getString("kindaddress"));
                nextkind.setResidentId(rs.getInt("residentid"));
                
                nextkinds.add(nextkind);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  nextkinds;
        
        
    }
    
    
    
    public boolean update(ResidentHistory residenth, NextKind nextkind){

        con = ConnectionFactory.getConnection();
     
        String sql="UPDATE resident SET name = ?, dateofbirth = ?, ppsnumber =?,floor =?, room =?, picture=? WHERE id=?";
        String sql2="UPDATE residenthistory SET diet = ?, fluid = ?, assistanceof =?,mobility =?, action =?, history =?, presentcondition =? WHERE id=?";
        String sql3="UPDATE nextkind SET kindname = ?, kindemail = ?, kindphone =?,kindaddress =? WHERE residentid=?";
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null; 
        PreparedStatement stmt3 = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setString(1, residenth.getName());
            stmt.setString(2, residenth.getDateofbirth());
            stmt.setString(3, residenth.getPpsnumber());
            stmt.setString(4, residenth.getFloor());
            stmt.setInt(5, residenth.getRoom());
            stmt.setString(6, residenth.getPicture());
            stmt.setInt(7, residenth.getResidentId());
            stmt.executeUpdate();
            
            stmt2=con.prepareStatement(sql2);
            stmt2.setString(1, residenth.getDiet());
            stmt2.setString(2, residenth.getFluid());
            stmt2.setInt(3, residenth.getAssistanceof());
            stmt2.setString(4, residenth.getMobility());
            stmt2.setString(5, residenth.getAction());
            stmt2.setString(6, residenth.getHistory());
            stmt2.setString(7, residenth.getPresentCondition());
            stmt2.setInt(8, residenth.getResidentId());
            stmt2.executeUpdate();
            
            stmt3=con.prepareStatement(sql3);
            stmt3.setString(1, nextkind.getNextName());
            stmt3.setString(2, nextkind.getNextEmail());
            stmt3.setString(3, nextkind.getNextPhone());
            stmt3.setString(4, nextkind.getNextAddress());
            stmt3.setInt(5,residenth.getResidentId() );
            stmt3.executeUpdate();
           
            
            JOptionPane.showMessageDialog(null, "Updated sucessfuly");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not updated sucessfuly"+ex);
            return false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
            
        }
        
        
    }
    
       
        
    
        
     public boolean deleteNext(NextKind nextkind){

        con = ConnectionFactory.getConnection();
        boolean returning=false;
        String sql="DELETE FROM nextkind WHERE residentid=?";
        String sql2="DELETE FROM residenthistory WHERE id=?";
        String sql3="DELETE FROM resident WHERE id=?";
      
        PreparedStatement stmt = null; 
        PreparedStatement stmt2 = null; 
        PreparedStatement stmt3 = null; 
        
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, nextkind.getResidentId());
            stmt.executeUpdate();
            
            stmt2=con.prepareStatement(sql2);
            stmt2.setInt(1, nextkind.getResidentId());
            stmt2.executeUpdate();
            
            
            stmt3=con.prepareStatement(sql3);
            stmt3.setInt(1, nextkind.getResidentId());
            stmt3.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deleted sucessfuly");
            returning= true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error. It was not deleted sucessfuly"+ex);
            returning= false;
        } finally{
            ConnectionFactory.CloseConnection(con, stmt);
            
        }
        
        return returning;
    }
    
        

    
    public List<ResidentHistory> searchResident(int id){
        con = ConnectionFactory.getConnection();
     
       // String sql="Select * from resident WHERE id=?";
         String sql="SELECT resident.*,residenthistory.*,nextkind.* FROM resident "
                   +"INNER JOIN residenthistory  ON residenthistory.id = resident.id "
                   +"INNER JOIN nextkind  ON nextkind.residentid = resident.id "
                   +"WHERE resident.id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ResidentHistory> residents = new ArrayList();
        ResidentHistory resident1 = new ResidentHistory();
        NextKind nextk = new NextKind();
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                resident1.setResidentId(rs.getInt("id"));
                resident1.setName(rs.getString("name"));
                resident1.setDateofbirth(rs.getString("dateofbirth"));
                resident1.setPpsnumber(rs.getString("ppsnumber"));
                resident1.setFloor(rs.getString("floor"));
                resident1.setRoom(rs.getInt("room"));
                resident1.setPicture(rs.getString("picture"));
                resident1.setDiet(rs.getString("diet"));
                resident1.setFluid(rs.getString("fluid"));
                resident1.setAssistanceof(rs.getInt("assistanceof"));
                resident1.setMobility(rs.getString("mobility"));
                resident1.setAction(rs.getString("action"));
                resident1.setHistory(rs.getString("history"));
                resident1.setPresentCondition(rs.getString("presentcondition"));
                
                
                residents.add(resident1);
                
                
                
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  residents;
        
        
    }
    
    public List<NextKind> searchResidentNext(int id){
        con = ConnectionFactory.getConnection();
     
       // String sql="Select * from resident WHERE id=?";
         String sql="SELECT nextkind.* FROM nextkind "
                   +"WHERE residentid=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<NextKind> nextkinds = new ArrayList();
        NextKind nextk = new NextKind();
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                nextk.setNextName(rs.getString("kindname"));
                nextk.setNextEmail(rs.getString("kindemail"));
                nextk.setNextPhone(rs.getString("kindphone"));
                nextk.setNextAddress(rs.getString("kindaddress"));
                
                nextkinds.add(nextk);
                
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  nextkinds;
        
        
    }
    
    
    
     public List<Resident> searchResidentId(int id){
        con = ConnectionFactory.getConnection();
     
        String sql="Select * from resident WHERE id=?";
//         String sql="SELECT resident.*,residenthistory.*,nextkind.* FROM resident "
//                   +"INNER JOIN residenthistory  ON residenthistory.id = resident.id "
//                   +"INNER JOIN nextkind  ON nextkind.residentid = resident.id "
//                   +"WHERE resident.id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Resident> residents = new ArrayList();
        Resident resident1 = new Resident();
      
        try {
            stmt=con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()){
               
                
                resident1.setResidentId(rs.getInt("id"));
                resident1.setName(rs.getString("name"));
                resident1.setDateofbirth(rs.getString("dateofbirth"));
                resident1.setPpsnumber(rs.getString("ppsnumber"));
                resident1.setFloor(rs.getString("floor"));
                resident1.setRoom(rs.getInt("room"));
                resident1.setPicture(rs.getString("picture"));
                
                
                
                residents.add(resident1);
                
                
                
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error. It was not found sucessfuly"+ex);
        } finally{
            ConnectionFactory.CloseConnection(con, stmt, rs);
        }
        
         return  residents;
        
        
    }
    
    
}
