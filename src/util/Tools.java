/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Claudinea de Almeida
 */
public class Tools {
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public String checkActualDate(){
        // data/hora atual
        LocalDateTime data1 = LocalDateTime.now();

        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String dateFormated = formatterData.format(data1);

     //   System.out.println("Data "+ dataFormatada);
        return dateFormated;
    }
    
    
    public void copiar(File fonte, File destino) {
        try {           
            //instance of FileInputStream origin
            FileChannel in = new FileInputStream(fonte).getChannel();
            //instance of FileInputStream destiny
            FileChannel out = new FileOutputStream(destino).getChannel();  
            //transfer file
            out.transferFrom(in, 0, in.size());

            in.close();
            out.close();

        } catch (IOException ex) {
           JOptionPane.showMessageDialog(null, "Error. It was not copied sucessfuly"+ex);       
            }
    }
    
    
    //mehtod to validate integer values
    public boolean validateInt(float number){
        //if the number is integer return true
        if(number == (int)number)
           return true;
        else
           //if the number is not integer return false 
           return false;
    }
    
    //method to validate password with regular expression
    public boolean validatePassword(String pass){
        //regular expression will validate letters and numbers, starting with letters
        //if the pass variable match with the expression it will return true
        if (pass.matches("[a-zA-Z_].*[a-zA-Z0-9_]")){
            return true;
        }else{
             //if the pass variable not match with the expression it will return false
            return false;
        }
    }
    
    //method to validate email with regular expression
    public boolean validateEmail(String email){
        //creating the boolean variable isEmailIdValid and initialize it with false
        boolean isEmailIdValid = false;
        //if the email is not null and it length is bigger than zero
        if (email != null && email.length() > 0) {
            //regular expression
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            //method pattern to check the regular expression
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            //instance of Matcher method
            Matcher matcher = pattern.matcher(email);
            //if the value passed by parameter matche with the regular expression the variable isEmailIdValid receives true
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        //return the value in the variable isEmailIdValid
        return isEmailIdValid;
    }
    
    //method to validate text with regular expression
    public boolean validateText(String text){
        //creating the boolean variable isTextValid and initialize it with false
        boolean isTextValid = false;
        //if the text is not null and it length is bigger than zero
        if (text != null && text.length() > 3) {
            isTextValid = true;
            
        }
        //return the value in the variable isTextValid
        return isTextValid;
    }
    
    //method to validate text with regular expression
    public boolean validatePPS(String text){
        //creating the boolean variable isTextValid and initialize it with false
        boolean isTextValid = false;
        //if the text is not null and it length is bigger than zero
        if (text != null && text.length() > 0) {
            //regular expression
            String expression = "\\d{7}[A-Z]{1,2}";
            
           //method pattern to check the regular expression
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            //instance of Matcher method
            Matcher matcher = pattern.matcher(text);
            //if the value passed by parameter matche with the regular expression the variable isTextValid receives true
            if (matcher.matches()) {
                isTextValid = true;
            }
        }
        //return the value in the variable isTextValid
        return isTextValid;
    }
      
   
    public String checkPassword(String pass){
        //creation of variables
        String formatedpass=null;
        //creation and initialization of variable type of MessageDigest
        MessageDigest md=null;
        try {
             //instance of MessageDigest in the format "SHA-256"
             md = MessageDigest.getInstance("SHA-256");

            //getting the bytes of the password parameter 
            byte[] messagedigest = md.digest(pass.getBytes("UTF-8"));
            
            //instance of StringBuilder
            StringBuilder sb = new StringBuilder();
            
            //for loop to get each letter of String in the format "%02X", 0xFF & b)
            for(byte b:messagedigest){
                sb.append(String.format("%02X", 0xFF & b));
            }
            
            //variable receive the result of the string
            formatedpass = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return the password encrypted
        return formatedpass;
    }

    
    public String convertDatetoString(Date date){
       // java.util.Date date1 = date;
        
        String dateformated = formatter.format(date);
      
        
        return dateformated;
    }
    
    
    public Date convertStringtoDate(String datestr){
         java.util.Date date=null;
         if(!datestr.isEmpty()){
                        
             try {
                 date = formatter.parse(datestr);
                 
             } catch (ParseException ex) {
                 Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
             }
                
          }
        return date;  
         
    }
}
