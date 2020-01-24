/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nursinghome;

/**
 *
 * @author Claudinea de Almeida
 */
public class NursingHome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        NHMSMenu nursing = new NHMSMenu();
        nursing.setVisible(false);
        
        Login login = new Login();
        login.setVisible(true);
    }
    
}
