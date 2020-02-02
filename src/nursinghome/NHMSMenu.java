/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nursinghome;

import DAO.EmployeeDAO;
import DAO.ItemDAO;
import DAO.ResidentDAO;
import DAO.RosterDAO;
import DAO.RoutineDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import util.Tools;

/**
 *
 * @author Claudinea de Almeida
 */
public class NHMSMenu extends javax.swing.JFrame {

    /**
     * Creates new form NHMS
     */
    
    public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String caminho;
    String username=null;
    int idResident=0;
    int leveluser=0;
    int idEmployee= 0;
    Date actualDate = new Date(System.currentTimeMillis());
    
    public NHMSMenu(int userid,String name,int levelaccess) {
        //initialization of the Swing components
        initComponents();
        //initialization of the variable idEmployee
        idEmployee=userid;
         //initialization of the variable username
        username=name;
         //initialization of the variable leveluser
        leveluser=levelaccess;
         //centralization of the windows
        setLocationRelativeTo(null);
        //changing the image icone of the window
        URL url = this.getClass().getResource("/images/hospital.png"); 
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url); 
        this.setIconImage(imagemTitulo);
        
        //calling the method checklevel with the parameter leveluser
        checklevel(leveluser);
        //calling the method readTableRoutine()
        readTableRoutine();
        //calling the method readTableResidentRoutine()
        readTableResidentRoutine();
        //calling the method listItem with the combobox Category items
        listItems(jComboCategoryItemRoutine.getSelectedItem().toString());
        //inserting the username into a label
        jLabUserName.setText("Welcome "+username);
        
    }
    
    public NHMSMenu() {
        initComponents();
        
        setLocationRelativeTo(null);
        URL url = this.getClass().getResource("/images/hospital.png"); 
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url); 
        this.setIconImage(imagemTitulo);
        readTableRoutine();
        readTableResidentRoutine();
        listItems(jComboCategoryItemRoutine.getSelectedItem().toString());

    }

    public void clearText(){
        employeename.setText("");
        employeedateofbirth.cleanup();
        employeepps.setText("");
        employeephone.setText("");
        employeeemail.setText("");
        employeeaddress.setText("");
        employeecertificate.setText("");
        employeespecialist.setText("");
        employeecountry.setSelectedIndex(0);
        employeepassport.setText("");
        employeestartdate.cleanup();
        employeelocation.setText("");
        employeetypeoftime.setSelectedIndex(0);
        employeejobtitle.setText("");
        employeepassword.setText("");
    }
    
    public void clearTextRoster(){
       jComboFloorRoster.setSelectedIndex(0);
       jTextDateRoster.cleanup();
       jComboEmployeeRoster.setSelectedIndex(0);
       jComboStartRoster.setSelectedIndex(0);
       jComboFinishRoster.setSelectedIndex(0);

       
    }
    
       public void clearTextItem(){
        jTextIdItem.setText("");
        jTextItemDescription.setText("");
        jComboBoxItemCategory.setSelectedIndex(0);
        foto.setIcon(null);
       
    }
       
    public void clearTextResident(){
        jTextResidentName.setText("");
        jTextResidentDateofBirth.cleanup();
        jTextResidentPPS.setText("");
        jTextResidentFloor.setText("");
        jTextResidentRoom.setText("");
        jComboResidentDiet.setSelectedIndex(0);
        jComboResidentFluid.setSelectedIndex(0);
        jComboResidentAssistance.setSelectedIndex(0);
        jComboResidentMobility.setSelectedIndex(0);
        jTextResidentAction.setText("");
        jTextResidentHistory.setText("");
        jTextResidentPresent.setText("");
        jTextResidentKindName.setText("");
        jTextResidentKindEmail.setText("");
        jTextResidentKindPhone.setText("");
        jTextResidentKindAddress.setText("");

    }
    
    public void clearTextRoutine(){
       jTextResidentNameRoutine.setText("");
       jTextDateRoutine.setText("");
       jComboMealRoutine.setSelectedIndex(0);
       jComboItemsRoutine.setSelectedIndex(0);
       jComboQuantityRoutine.setSelectedIndex(0);
       jTextCommentsRoutine.setText("");

       
    }
    
    public void readComboEmpployee(){
        
        jComboEmployeeRoster.removeAllItems();
        RosterDAO dao = new RosterDAO();
        Employee employee = new Employee();
        List<Employee> listemployee = new ArrayList<>();
        
        listemployee=dao.readEmployee();       
                
        for(Employee e:listemployee){
            jComboEmployeeRoster.addItem(e.getEmployeeId()+" - "+ e.getName());
         
        }
        
         
    }
    
    public void checklevel(int level){
        
        switch(level){
        case 0:{
            employeebtn.setEnabled(true);
            rosterbtn.setEnabled(true);
            residentbtn.setEnabled(true);
            dailyroutinebtn.setEnabled(true);
            itemsbtn2.setEnabled(true);
            jLabTitle.setText("Routine");
            break;
        }
        case 1:{
            employeebtn.setEnabled(true);
            rosterbtn.setEnabled(true);
            jTabPanel.setSelectedIndex(2);
            jLabTitle.setText("Employee");
            readTable();
            residentbtn.setEnabled(false);
            dailyroutinebtn.setEnabled(false);
            itemsbtn2.setEnabled(false);
            break;
        }
        case 2:{
            residentbtn.setEnabled(true);
            jTabPanel.setSelectedIndex(1);
            jLabTitle.setText("Resident");
            readTableResident();
            employeebtn.setEnabled(false);
            rosterbtn.setEnabled(false);          
            dailyroutinebtn.setEnabled(false);
            itemsbtn2.setEnabled(false);
            break;
        }
         case 3:{
            dailyroutinebtn.setEnabled(true);
            jLabTitle.setText("Routine");
            employeebtn.setEnabled(false);
            rosterbtn.setEnabled(false);
            residentbtn.setEnabled(false);
            itemsbtn2.setEnabled(false);
            break;
        }
         default :
            JOptionPane.showMessageDialog(null, "Error. Invalid user level "+level);
            
        }
    }
    
    public void readTable(){
        DefaultTableModel model = (DefaultTableModel) jTabEmployee.getModel();
       
        
        model.setNumRows(0);

        EmployeeDAO dao = new EmployeeDAO();
        
        for(Employee e:dao.read()){
            model.addRow(new Object[]{
                  e.getEmployeeId(),
                  e.getName()
            });
        }
        
        
    }
    
     public void readTableItem(){
        DefaultTableModel model = (DefaultTableModel) jTabItem.getModel();
       
        
        model.setNumRows(0);
        caminho=null;

        ItemDAO dao = new ItemDAO();
        
        for(Item e:dao.readItem()){
            model.addRow(new Object[]{
                  e.getItemId(),
                  e.getCategory(),
                  e.getDescription(),
                 // e.getPicture()
            
                    
            });
            caminho=e.getPicture();
        }
       
        
    }
     
     public void readTableRoster(){
        DefaultTableModel modelRo = (DefaultTableModel) jTabRoster.getModel();
       
        
        modelRo.setNumRows(0);

        RosterDAO daoR = new RosterDAO();
        
        for(Roster e:daoR.readRoster()){
            modelRo.addRow(new Object[]{
                  e.getId(),
                  e.getFloor(),
                  e.getDate(),
                  e.getStartTime(),
                  e.getFinishTime(),
                  e.getEmployeeId(),
                    
            });
           
        }
       
        
    }
     
    public void readTableResident(){
        DefaultTableModel modelR = null;
        modelR = (DefaultTableModel) jTabResident.getModel();
        
       
       
        
        modelR.setNumRows(0);

        ResidentDAO daoR = new ResidentDAO();
        
        for(Resident e:daoR.readResident()){
            modelR.addRow(new Object[]{
                  e.getResidentId(),
                  e.getName()
             
                    
            });
           
        }
       
        
    }
    
    public void readTableRoutine(){
        DefaultTableModel model = (DefaultTableModel) jTabRoutine.getModel();
       
        
        model.setNumRows(0);

        RoutineDAO dao = new RoutineDAO();
        
        for(DailyRoutine e:dao.readroutine()){
            model.addRow(new Object[]{
                  e.getId(),
                  e.getResident().getResidentId()+ " - "+e.getResident().getName(),
                  e.getDate(),
                  e.getMeal(),
                  e.getItem().getItemId()+ " - "+e.getItem().getDescription(),
                  e.getQuantity(),
                  e.getEmployee().getEmployeeId(),
                  
                
            });
            jTextCommentsRoutine.setText(e.getComments());
        }
        
        
    }
    
        
    public void readTableResidentRoutine(){
        DefaultTableModel modelR =  (DefaultTableModel) jTabResidentRoutine.getModel();
       
          
        
        modelR.setNumRows(0);

        ResidentDAO daoR = new ResidentDAO();
        
        for(Resident e:daoR.readResident()){
            modelR.addRow(new Object[]{
                  e.getResidentId(),
                  e.getName()
             
                    
            });
            caminho=e.getPicture();
           
        }
       
        
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dailyroutinebtn = new javax.swing.JButton();
        residentbtn = new javax.swing.JButton();
        employeebtn = new javax.swing.JButton();
        rosterbtn = new javax.swing.JButton();
        jLabUserName = new javax.swing.JLabel();
        itemsbtn2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabTitle = new javax.swing.JLabel();
        jTabPanel = new javax.swing.JTabbedPane();
        jPanelRoutine = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTabResidentRoutine = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jTextResidentNameRoutine = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextDateRoutine = new javax.swing.JFormattedTextField();
        jLabel61 = new javax.swing.JLabel();
        jComboMealRoutine = new javax.swing.JComboBox<>();
        jPanelResidentPicRoutine = new javax.swing.JPanel();
        jLResidentPicRoutine = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jComboQuantityRoutine = new javax.swing.JComboBox<>();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel64 = new javax.swing.JLabel();
        jComboItemsRoutine = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        jTextCommentsRoutine = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabRoutine = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btndeleteRoutine = new javax.swing.JButton();
        jbtnupdateRoutine = new javax.swing.JButton();
        jBtnSaveRoutine = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jComboCategoryItemRoutine = new javax.swing.JComboBox<>();
        jPanelResident = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTabResident = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jTextResidentName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextResidentDateofBirth = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jTextResidentPPS = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextResidentFloor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextResidentRoom = new javax.swing.JTextField();
        jPaneResidentImage = new javax.swing.JPanel();
        jResidentPhoto = new javax.swing.JLabel();
        jBtnResidentPic = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jComboResidentDiet = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jComboResidentFluid = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jComboResidentAssistance = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jComboResidentMobility = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jTextResidentAction = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextResidentHistory = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextResidentPresent = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextResidentKindName = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextResidentKindEmail = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextResidentKindPhone = new javax.swing.JFormattedTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextResidentKindAddress = new javax.swing.JFormattedTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jBtnSaveResident = new javax.swing.JButton();
        jbtnupdateResident = new javax.swing.JButton();
        btndeleteResident = new javax.swing.JButton();
        jPanelEmployee = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTabEmployee = new javax.swing.JTable();
        employeename = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        employeedateofbirth = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        employeepps = new javax.swing.JFormattedTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        employeephone = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        employeeemail = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        employeeaddress = new javax.swing.JFormattedTextField();
        jLabel52 = new javax.swing.JLabel();
        employeecertificate = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        employeespecialist = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        employeecountry = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        employeepassport = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        employeestartdate = new com.toedter.calendar.JDateChooser();
        jLabel45 = new javax.swing.JLabel();
        employeelocation = new javax.swing.JFormattedTextField();
        jLabel46 = new javax.swing.JLabel();
        employeetypeoftime = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        employeejobtitle = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        employeepassword = new javax.swing.JPasswordField();
        jPanel8 = new javax.swing.JPanel();
        btnsaveemployee = new javax.swing.JButton();
        btnupdateemployee = new javax.swing.JButton();
        btndeleteemployee = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        employeelevel = new javax.swing.JComboBox<>();
        jPanelItems = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextIdItem = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jComboBoxItemCategory = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        jTextItemDescription = new javax.swing.JTextField();
        jPanelItemImage = new javax.swing.JPanel();
        foto = new javax.swing.JLabel();
        jBtnOpenFile = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jBtnSaveItem = new javax.swing.JButton();
        jbtnupdateItem = new javax.swing.JButton();
        btndeleteItem = new javax.swing.JButton();
        jPanelTabItemContainer = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTabItem = new javax.swing.JTable();
        jPanelRoster = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jComboEmployeeRoster = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jComboFloorRoster = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        jTextDateRoster = new com.toedter.calendar.JDateChooser();
        jLabel53 = new javax.swing.JLabel();
        jComboStartRoster = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jComboFinishRoster = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTabRoster = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btndeleteRoster = new javax.swing.JButton();
        btnupdateroster = new javax.swing.JButton();
        btnsaveroster = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nursing Home Managment System");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMenu.setBackground(new java.awt.Color(189, 210, 178));

        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 0, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hospital.png"))); // NOI18N
        jLabel1.setText("NHMS");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/copyright.png"))); // NOI18N
        jLabel2.setText("DesignerClau CIA");

        dailyroutinebtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        dailyroutinebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Routine.png"))); // NOI18N
        dailyroutinebtn.setText("Routine  ");
        dailyroutinebtn.setBorder(null);
        dailyroutinebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyroutinebtnActionPerformed(evt);
            }
        });

        residentbtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        residentbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/elderly.png"))); // NOI18N
        residentbtn.setText("Resident ");
        residentbtn.setBorder(null);
        residentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                residentbtnActionPerformed(evt);
            }
        });

        employeebtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        employeebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nurse.png"))); // NOI18N
        employeebtn.setText("Employee");
        employeebtn.setBorder(null);
        employeebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeebtnActionPerformed(evt);
            }
        });

        rosterbtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rosterbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Roster.png"))); // NOI18N
        rosterbtn.setText("Roster     ");
        rosterbtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rosterbtn.setBorderPainted(false);
        rosterbtn.setIconTextGap(8);
        rosterbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rosterbtnActionPerformed(evt);
            }
        });

        jLabUserName.setFont(new java.awt.Font("Lucida Calligraphy", 0, 12)); // NOI18N
        jLabUserName.setText("Wellcome");

        itemsbtn2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        itemsbtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chicken.png"))); // NOI18N
        itemsbtn2.setText("Items      ");
        itemsbtn2.setToolTipText("");
        itemsbtn2.setBorder(null);
        itemsbtn2.setIconTextGap(8);
        itemsbtn2.setMaximumSize(new java.awt.Dimension(123, 33));
        itemsbtn2.setMinimumSize(new java.awt.Dimension(123, 33));
        itemsbtn2.setPreferredSize(new java.awt.Dimension(123, 33));
        itemsbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsbtn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(residentbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeebtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rosterbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dailyroutinebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(itemsbtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabUserName)
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(183, 183, 183)
                .addComponent(dailyroutinebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(residentbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(itemsbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rosterbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(344, 344, 344))
        );

        jPanel2.add(jPanelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 1280));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabTitle.setFont(new java.awt.Font("Lucida Calligraphy", 0, 18)); // NOI18N
        jLabTitle.setText("Routine");
        jLabTitle.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(425, 425, 425)
                .addComponent(jLabTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1304, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabTitle)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1890, -1));

        jTabPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanelRoutine.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Resident:");

        jTabResidentRoutine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Resident"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabResidentRoutine.setGridColor(new java.awt.Color(255, 255, 255));
        jTabResidentRoutine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabResidentRoutineMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTabResidentRoutineMouseReleased(evt);
            }
        });
        jTabResidentRoutine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabResidentRoutineKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(jTabResidentRoutine);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Resident:");

        jTextResidentNameRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentNameRoutineActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Date:");

        jTextDateRoutine.setEditable(false);
        try {
            jTextDateRoutine.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTextDateRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDateRoutineActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setText("Meal:");

        jComboMealRoutine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BreakFast", "Soup service", "Lunch", "Tea service afternoon", "Dinner", "Tea service evening", "Other", " " }));

        jLResidentPicRoutine.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLResidentPicRoutine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLResidentPicRoutine.setText("No Picture");
        jLResidentPicRoutine.setOpaque(true);

        javax.swing.GroupLayout jPanelResidentPicRoutineLayout = new javax.swing.GroupLayout(jPanelResidentPicRoutine);
        jPanelResidentPicRoutine.setLayout(jPanelResidentPicRoutineLayout);
        jPanelResidentPicRoutineLayout.setHorizontalGroup(
            jPanelResidentPicRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResidentPicRoutineLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLResidentPicRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelResidentPicRoutineLayout.setVerticalGroup(
            jPanelResidentPicRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResidentPicRoutineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLResidentPicRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("Quantity:");

        jComboQuantityRoutine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Full", "Half", "None" }));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setText("Item:");

        jComboItemsRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboItemsRoutineActionPerformed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel63.setText("Comments:");

        jTextCommentsRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCommentsRoutineActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTabRoutine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Resident", "Date", "Meal", "Item", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabRoutine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabRoutineMouseClicked(evt);
            }
        });
        jTabRoutine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabRoutineKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTabRoutine);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                .addGap(209, 209, 209))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btndeleteRoutine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndeleteRoutine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btndeleteRoutine.setText("Delete");
        btndeleteRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteRoutineActionPerformed(evt);
            }
        });

        jbtnupdateRoutine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnupdateRoutine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rename - Edit.png"))); // NOI18N
        jbtnupdateRoutine.setText("Update");
        jbtnupdateRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnupdateRoutineActionPerformed(evt);
            }
        });

        jBtnSaveRoutine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnSaveRoutine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        jBtnSaveRoutine.setText("Save");
        jBtnSaveRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveRoutineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(821, Short.MAX_VALUE)
                .addComponent(jBtnSaveRoutine)
                .addGap(18, 18, 18)
                .addComponent(jbtnupdateRoutine)
                .addGap(18, 18, 18)
                .addComponent(btndeleteRoutine)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndeleteRoutine)
                    .addComponent(jbtnupdateRoutine)
                    .addComponent(jBtnSaveRoutine))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel65.setText("Category:");

        jComboCategoryItemRoutine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Hygiene", "Room Tidy" }));
        jComboCategoryItemRoutine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoryItemRoutineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRoutineLayout = new javax.swing.GroupLayout(jPanelRoutine);
        jPanelRoutine.setLayout(jPanelRoutineLayout);
        jPanelRoutineLayout.setHorizontalGroup(
            jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel63)
                                            .addComponent(jLabel64)
                                            .addComponent(jLabel65))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextCommentsRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                                                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jComboCategoryItemRoutine, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboItemsRoutine, javax.swing.GroupLayout.Alignment.LEADING, 0, 190, Short.MAX_VALUE))
                                                .addGap(47, 47, 47)
                                                .addComponent(jLabel56)
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboQuantityRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextResidentNameRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDateRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboMealRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95)
                        .addComponent(jPanelResidentPicRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanelRoutineLayout.setVerticalGroup(
            jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRoutineLayout.createSequentialGroup()
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRoutineLayout.createSequentialGroup()
                                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextResidentNameRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(jTextDateRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboMealRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel61)))
                            .addComponent(jPanelResidentPicRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(jComboCategoryItemRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(jComboItemsRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56)
                            .addComponent(jComboQuantityRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanelRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(jTextCommentsRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        jTabPanel.addTab("tab1", jPanelRoutine);

        jPanelResident.setBackground(new java.awt.Color(255, 255, 255));

        jTabResident.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Resident"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabResident.setGridColor(new java.awt.Color(255, 255, 255));
        jTabResident.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabResidentMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTabResidentMouseReleased(evt);
            }
        });
        jTabResident.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabResidentKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(jTabResident);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Name:");

        jTextResidentName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentNameFocusLost(evt);
            }
        });
        jTextResidentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentNameActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Date of birth:");

        jTextResidentDateofBirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentDateofBirthFocusLost(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("PPS Number:");

        jTextResidentPPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentPPSFocusLost(evt);
            }
        });
        jTextResidentPPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentPPSActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Floor:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Room:");

        jTextResidentRoom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentRoomFocusLost(evt);
            }
        });
        jTextResidentRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentRoomActionPerformed(evt);
            }
        });

        jResidentPhoto.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jResidentPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jResidentPhoto.setText("No Picture");
        jResidentPhoto.setOpaque(true);

        javax.swing.GroupLayout jPaneResidentImageLayout = new javax.swing.GroupLayout(jPaneResidentImage);
        jPaneResidentImage.setLayout(jPaneResidentImageLayout);
        jPaneResidentImageLayout.setHorizontalGroup(
            jPaneResidentImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
            .addGroup(jPaneResidentImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPaneResidentImageLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jResidentPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPaneResidentImageLayout.setVerticalGroup(
            jPaneResidentImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
            .addGroup(jPaneResidentImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPaneResidentImageLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jResidentPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jBtnResidentPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo.png"))); // NOI18N
        jBtnResidentPic.setText("Photo");
        jBtnResidentPic.setFocusTraversalPolicyProvider(true);
        jBtnResidentPic.setIconTextGap(10);
        jBtnResidentPic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnResidentPicActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Diet:");

        jComboResidentDiet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Soft", "Purée", "Tube feeding" }));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Fluid:");

        jComboResidentFluid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Free", "Grade 1", "Grade 2", "Grade 3" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Assistance of:");

        jComboResidentAssistance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Mobility:");

        jComboResidentMobility.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Walking Stick", "Rollator", "Zimmer Frame", "Wheelchair", "Standing Hoist", "Full body hoist" }));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Action:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("History:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Present Condition: ");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Next Kind information:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Name:");

        jTextResidentKindName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentKindNameFocusLost(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Email:");

        jTextResidentKindEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextResidentKindEmailFocusLost(evt);
            }
        });
        jTextResidentKindEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentKindEmailActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Phone:");

        jTextResidentKindPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentKindPhoneActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Address:");

        jTextResidentKindAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextResidentKindAddressActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jBtnSaveResident.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnSaveResident.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        jBtnSaveResident.setText("Save");
        jBtnSaveResident.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveResidentActionPerformed(evt);
            }
        });

        jbtnupdateResident.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnupdateResident.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rename - Edit.png"))); // NOI18N
        jbtnupdateResident.setText("Update");
        jbtnupdateResident.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnupdateResidentActionPerformed(evt);
            }
        });

        btndeleteResident.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndeleteResident.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btndeleteResident.setText("Delete");
        btndeleteResident.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteResidentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(942, Short.MAX_VALUE)
                .addComponent(jBtnSaveResident)
                .addGap(18, 18, 18)
                .addComponent(jbtnupdateResident)
                .addGap(18, 18, 18)
                .addComponent(btndeleteResident)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSaveResident)
                    .addComponent(jbtnupdateResident)
                    .addComponent(btndeleteResident))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanelResidentLayout = new javax.swing.GroupLayout(jPanelResident);
        jPanelResident.setLayout(jPanelResidentLayout);
        jPanelResidentLayout.setHorizontalGroup(
            jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResidentLayout.createSequentialGroup()
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextResidentName, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextResidentDateofBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextResidentPPS)
                                    .addComponent(jTextResidentFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jTextResidentRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(91, 91, 91)
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPaneResidentImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jBtnResidentPic, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanelResidentLayout.createSequentialGroup()
                                            .addComponent(jLabel30)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jComboResidentFluid, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelResidentLayout.createSequentialGroup()
                                            .addComponent(jLabel31)
                                            .addGap(37, 37, 37)
                                            .addComponent(jComboResidentAssistance, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(43, 43, 43)
                                            .addComponent(jLabel32)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jComboResidentMobility, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextResidentPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel34)
                                                    .addComponent(jLabel27))
                                                .addGap(37, 37, 37)
                                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextResidentHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextResidentAction, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addComponent(jLabel28)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jLabel29)
                                .addGap(40, 40, 40)
                                .addComponent(jComboResidentDiet, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addGap(23, 23, 23)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextResidentKindPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextResidentKindName, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextResidentKindAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addGap(27, 27, 27)
                                        .addComponent(jTextResidentKindEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 909, Short.MAX_VALUE)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanelResidentLayout.setVerticalGroup(
            jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResidentLayout.createSequentialGroup()
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelResidentLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jTextResidentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextResidentDateofBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextResidentPPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(jTextResidentRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextResidentFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelResidentLayout.createSequentialGroup()
                                .addComponent(jPaneResidentImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnResidentPic)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResidentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResidentLayout.createSequentialGroup()
                        .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboResidentDiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jComboResidentFluid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addGap(18, 18, 18)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jComboResidentAssistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jComboResidentMobility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextResidentAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextResidentHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextResidentPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addGap(33, 33, 33)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33)
                        .addComponent(jTextResidentKindEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTextResidentKindName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jTextResidentKindPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel35)
                        .addComponent(jTextResidentKindAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );

        jTabPanel.addTab("tab2", jPanelResident);

        jPanelEmployee.setBackground(new java.awt.Color(255, 255, 255));

        jTabEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Employee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabEmployee.setGridColor(new java.awt.Color(255, 255, 255));
        jTabEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabEmployeeMouseClicked(evt);
            }
        });
        jTabEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabEmployeeKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(jTabEmployee);

        employeename.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeenameFocusLost(evt);
            }
        });
        employeename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeenameActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Name:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setText("Date of birth:");

        employeedateofbirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeedateofbirthFocusLost(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("PPS Number:");

        employeepps.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                employeeppsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeppsFocusLost(evt);
            }
        });
        employeepps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeppsActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Phone:");

        employeephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeephoneActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("Email:");

        employeeemail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeemailFocusLost(evt);
            }
        });
        employeeemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeemailActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setText("Address:");

        employeeaddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeaddressActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setText("Certificate:");

        employeecertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeecertificateActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Specialist:");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setText("Country:");

        employeecountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ireland", "Brazil", "Spain", "Portugal", "France", "Bolivia", "EUA" }));
        employeecountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeecountryActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel48.setText("Passport:");

        employeepassport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeepassportActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("Start date:");

        employeestartdate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeestartdateFocusLost(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setText("Location:");

        employeelocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeelocationActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Type of time:");

        employeetypeoftime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Part Time", "Full Time" }));
        employeetypeoftime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeetypeoftimeActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("Job Title:");

        employeejobtitle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeejobtitleFocusLost(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setText("Password:");

        employeepassword.setToolTipText("");
        employeepassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeepasswordFocusLost(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnsaveemployee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsaveemployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btnsaveemployee.setText("Save");
        btnsaveemployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveemployeeActionPerformed(evt);
            }
        });

        btnupdateemployee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnupdateemployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rename - Edit.png"))); // NOI18N
        btnupdateemployee.setText("Update");
        btnupdateemployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateemployeeActionPerformed(evt);
            }
        });

        btndeleteemployee.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndeleteemployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btndeleteemployee.setText("Delete");
        btndeleteemployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteemployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(730, Short.MAX_VALUE)
                .addComponent(btnsaveemployee)
                .addGap(18, 18, 18)
                .addComponent(btnupdateemployee)
                .addGap(18, 18, 18)
                .addComponent(btndeleteemployee)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsaveemployee)
                    .addComponent(btnupdateemployee)
                    .addComponent(btndeleteemployee))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel54.setText("Level:");

        employeelevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrator", "HR", "Receptionist", "Nurse/Care" }));
        employeelevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeelevelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEmployeeLayout = new javax.swing.GroupLayout(jPanelEmployee);
        jPanelEmployee.setLayout(jPanelEmployeeLayout);
        jPanelEmployeeLayout.setHorizontalGroup(
            jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(29, 29, 29)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeelocation, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeestartdate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeetypeoftime, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeejobtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                .addComponent(employeepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel54)
                                .addGap(18, 18, 18)
                                .addComponent(employeelevel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel50)
                                            .addComponent(jLabel51))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(employeedateofbirth, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(employeename, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(employeepps, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator4)))
                            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel44)
                                            .addComponent(jLabel40))
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                                .addComponent(employeecountry, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(97, 97, 97)
                                                .addComponent(jLabel48)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(employeepassport, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(employeespecialist, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel49)
                                            .addComponent(jLabel52)
                                            .addComponent(jLabel38))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                                                .addComponent(employeephone, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel39)
                                                .addGap(18, 18, 18)
                                                .addComponent(employeeemail, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(employeecertificate, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(employeeaddress, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        jPanelEmployeeLayout.setVerticalGroup(
            jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEmployeeLayout.createSequentialGroup()
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(employeedateofbirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeepps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51))
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(employeeemail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(employeephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(employeeaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeecertificate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(employeespecialist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(employeecountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(employeepassport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(employeestartdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(employeelocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(employeetypeoftime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(employeejobtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(employeepassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(employeelevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(430, Short.MAX_VALUE))
        );

        jTabPanel.addTab("tab3", jPanelEmployee);

        jPanelItems.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Item id:");

        jTextIdItem.setEditable(false);
        jTextIdItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdItemActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setText("Category:");

        jComboBoxItemCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Hygiene", "Room Tidy" }));
        jComboBoxItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxItemCategoryActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("Description:");

        jTextItemDescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextItemDescriptionFocusLost(evt);
            }
        });
        jTextItemDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextItemDescriptionActionPerformed(evt);
            }
        });

        foto.setOpaque(true);

        javax.swing.GroupLayout jPanelItemImageLayout = new javax.swing.GroupLayout(jPanelItemImage);
        jPanelItemImage.setLayout(jPanelItemImageLayout);
        jPanelItemImageLayout.setHorizontalGroup(
            jPanelItemImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelItemImageLayout.setVerticalGroup(
            jPanelItemImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jBtnOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_open.png"))); // NOI18N
        jBtnOpenFile.setText("Open Image");
        jBtnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOpenFileActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jBtnSaveItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnSaveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        jBtnSaveItem.setText("Save");
        jBtnSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveItemActionPerformed(evt);
            }
        });

        jbtnupdateItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnupdateItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rename - Edit.png"))); // NOI18N
        jbtnupdateItem.setText("Update");
        jbtnupdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnupdateItemActionPerformed(evt);
            }
        });

        btndeleteItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btndeleteItem.setText("Delete");
        btndeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(757, Short.MAX_VALUE)
                .addComponent(jBtnSaveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnupdateItem)
                .addGap(18, 18, 18)
                .addComponent(btndeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSaveItem)
                    .addComponent(jbtnupdateItem)
                    .addComponent(btndeleteItem))
                .addContainerGap())
        );

        jTabItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Category", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabItem.setGridColor(new java.awt.Color(255, 255, 255));
        jTabItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabItemMouseClicked(evt);
            }
        });
        jTabItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabItemKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTabItem);

        javax.swing.GroupLayout jPanelTabItemContainerLayout = new javax.swing.GroupLayout(jPanelTabItemContainer);
        jPanelTabItemContainer.setLayout(jPanelTabItemContainerLayout);
        jPanelTabItemContainerLayout.setHorizontalGroup(
            jPanelTabItemContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabItemContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelTabItemContainerLayout.setVerticalGroup(
            jPanelTabItemContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabItemContainerLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelItemsLayout = new javax.swing.GroupLayout(jPanelItems);
        jPanelItems.setLayout(jPanelItemsLayout);
        jPanelItemsLayout.setHorizontalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemsLayout.createSequentialGroup()
                .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItemsLayout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelTabItemContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelItemsLayout.createSequentialGroup()
                                .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel55))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextItemDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextIdItem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(269, 269, 269)
                                .addComponent(jPanelItemImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelItemsLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 427, Short.MAX_VALUE))
            .addGroup(jPanelItemsLayout.createSequentialGroup()
                .addGap(1002, 1002, 1002)
                .addComponent(jBtnOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelItemsLayout.setVerticalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemsLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItemsLayout.createSequentialGroup()
                        .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextIdItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(jTextItemDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelItemImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jBtnOpenFile)
                .addGap(55, 55, 55)
                .addComponent(jPanelTabItemContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(336, Short.MAX_VALUE))
        );

        jTabPanel.addTab("tab4", jPanelItems);

        jPanelRoster.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Employee:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Floor:");

        jComboFloorRoster.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ground Floor", "First Floor", "Second Floor", "Thrid Floor" }));
        jComboFloorRoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboFloorRosterActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setText("Date:");

        jTextDateRoster.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextDateRosterFocusLost(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setText("Start time:");

        jComboStartRoster.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7 am", "8 am", "9 am", "7 pm", "8 pm", "9 pm" }));
        jComboStartRoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboStartRosterActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setText("hours");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setText("Finish time:");

        jComboFinishRoster.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6 am", "7 am", "8 am", "9 am", "6 pm", "7 pm", "8 pm", "9 pm", " " }));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setText("hours");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jTabRoster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Floor", "Date", "Start time", "Finish time", "Employee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabRoster.setGridColor(new java.awt.Color(255, 255, 255));
        jTabRoster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabRosterMouseClicked(evt);
            }
        });
        jTabRoster.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabRosterKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(jTabRoster);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 903, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btndeleteRoster.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btndeleteRoster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btndeleteRoster.setText("Delete");
        btndeleteRoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteRosterActionPerformed(evt);
            }
        });

        btnupdateroster.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnupdateroster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rename - Edit.png"))); // NOI18N
        btnupdateroster.setText("Update");
        btnupdateroster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdaterosterActionPerformed(evt);
            }
        });

        btnsaveroster.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsaveroster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btnsaveroster.setText("Save");
        btnsaveroster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaverosterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(670, Short.MAX_VALUE)
                .addComponent(btnsaveroster)
                .addGap(18, 18, 18)
                .addComponent(btnupdateroster)
                .addGap(18, 18, 18)
                .addComponent(btndeleteRoster)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndeleteRoster)
                    .addComponent(btnupdateroster)
                    .addComponent(btnsaveroster))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelRosterLayout = new javax.swing.GroupLayout(jPanelRoster);
        jPanelRoster.setLayout(jPanelRosterLayout);
        jPanelRosterLayout.setHorizontalGroup(
            jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRosterLayout.createSequentialGroup()
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRosterLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRosterLayout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRosterLayout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelRosterLayout.createSequentialGroup()
                                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel58))
                                .addGap(45, 45, 45)
                                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanelRosterLayout.createSequentialGroup()
                                        .addComponent(jComboFinishRoster, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel60))
                                    .addGroup(jPanelRosterLayout.createSequentialGroup()
                                        .addComponent(jComboStartRoster, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel59))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelRosterLayout.createSequentialGroup()
                                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23))
                                .addGap(51, 51, 51)
                                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboFloorRoster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboEmployeeRoster, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextDateRoster, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(383, Short.MAX_VALUE))
        );
        jPanelRosterLayout.setVerticalGroup(
            jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRosterLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jComboEmployeeRoster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboFloorRoster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47)
                    .addComponent(jTextDateRoster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jComboStartRoster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanelRosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jComboFinishRoster, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(377, 377, 377))
        );

        jTabPanel.addTab("tab5", jPanelRoster);

        jPanel2.add(jTabPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1640, 1280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1699, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dailyroutinebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyroutinebtnActionPerformed
        jTabPanel.setSelectedIndex(0);
        readTableRoutine();
        readTableResidentRoutine();
        jComboCategoryItemRoutine.setSelectedIndex(0);
        listItems(jComboCategoryItemRoutine.getSelectedItem().toString());
        jLabTitle.setText("Routine");

     
        
    }//GEN-LAST:event_dailyroutinebtnActionPerformed

    private void residentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_residentbtnActionPerformed
        jTabPanel.setSelectedIndex(1);
        readTableResident();
        jLabTitle.setText("Resident");

    }//GEN-LAST:event_residentbtnActionPerformed

    private void employeebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeebtnActionPerformed
        jTabPanel.setSelectedIndex(2);
        readTable();
        jLabTitle.setText("Employee");

    }//GEN-LAST:event_employeebtnActionPerformed

    private void rosterbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rosterbtnActionPerformed
        // TODO add your handling code here:
        jTabPanel.setSelectedIndex(4);
        readComboEmpployee();
        readTableRoster();
        jLabTitle.setText("Roster");

    }//GEN-LAST:event_rosterbtnActionPerformed

    private void jComboFloorRosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboFloorRosterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboFloorRosterActionPerformed

    private void jTextDateRosterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextDateRosterFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDateRosterFocusLost

    private void jTabRosterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabRosterMouseClicked
        // TODO add your handling code here:

        if (jTabRoster.getSelectedRow() != -1){
            clearTextRoster();
            DefaultTableModel model = (DefaultTableModel) jTabRoster.getModel();

            RosterDAO dao = new RosterDAO();

            int id = (int)jTabRoster.getValueAt(jTabRoster.getSelectedRow(), 0);
            List<Roster> array1 = new ArrayList<>();
            array1= dao.search(id);
            int empid =0;
            for (Roster c : array1) {

                jComboFloorRoster.setSelectedItem(c.getFloor());

                if(!c.getDate().isEmpty()){
                    try {
                        jTextDateRoster.setDate(formatter.parse(c.getDate()));
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Error. The date is empty: "+ex);
                    }
                }

                jComboStartRoster.setSelectedItem(c.getStartTime());
                jComboFinishRoster.setSelectedItem(c.getFinishTime());
                jComboEmployeeRoster.setSelectedItem(c.getEmployeeId());
                empid=c.getEmployeeId();

            }

            if (empid!=0){

                String name=dao.readEmployeeId(empid);
                jComboEmployeeRoster.setSelectedItem(name);

            }
        }
    }//GEN-LAST:event_jTabRosterMouseClicked

    private void jTabRosterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabRosterKeyReleased
        // TODO add your handling code here:

        if (jTabRoster.getSelectedRow() != -1){
            clearTextRoster();
            DefaultTableModel model = (DefaultTableModel) jTabRoster.getModel();

            RosterDAO dao = new RosterDAO();

            int id = (int)jTabRoster.getValueAt(jTabRoster.getSelectedRow(), 0);
            List<Roster> array1 = new ArrayList<>();
            array1= dao.search(id);

            for (Roster c : array1) {

                jComboFloorRoster.setSelectedItem(c.getFloor());

                if(!c.getDate().isEmpty()){
                    try {
                        jTextDateRoster.setDate(formatter.parse(c.getDate()));
                    } catch (ParseException ex) {
                         JOptionPane.showMessageDialog(null, "Error. The date is empty: "+ex);
                    }
                }

                jComboStartRoster.setSelectedItem(c.getStartTime());
                jComboFinishRoster.setSelectedItem(c.getFinishTime());
                jComboEmployeeRoster.setSelectedItem(c.getEmployeeId());

            }

        }

    }//GEN-LAST:event_jTabRosterKeyReleased

    private void btndeleteRosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteRosterActionPerformed
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_OPTION);

	// 0=yes, 1=no, 2=cancel
        if (input == 0){

            if (jTabRoster.getSelectedRow() != -1){
                Roster roster = new Roster();
                RosterDAO daoR = new RosterDAO();

                roster.setId((int)jTabRoster.getValueAt(jTabRoster.getSelectedRow(), 0));

                daoR.delete(roster);
                readTableRoster();
                clearTextRoster();
            }
        }
    }//GEN-LAST:event_btndeleteRosterActionPerformed

    private void btnupdaterosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdaterosterActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTabRoster.getModel();
        if (jTabRoster.getSelectedRow() != -1){
            Roster roster = new Roster();
            RosterDAO daoRo = new RosterDAO();

            roster.setId((int)jTabRoster.getValueAt(jTabRoster.getSelectedRow(), 0));
            roster.setFloor(jComboFloorRoster.getSelectedItem().toString());

            Tools tools = new Tools();
            String datestr=tools.convertDatetoString(jTextDateRoster.getDate());
            roster.setDate(datestr);

            roster.setStartTime(jComboStartRoster.getSelectedItem().toString());
            roster.setFinishTime(jComboFinishRoster.getSelectedItem().toString());

            daoRo.update(roster);

            readTableRoster();
            clearTextRoster();

        }

    }//GEN-LAST:event_btnupdaterosterActionPerformed

    private void btnsaverosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaverosterActionPerformed
        // TODO add your handling code here:
        Roster roster = new Roster();
        RosterDAO daoRo = new RosterDAO();

        String employeestr=String.valueOf(jComboEmployeeRoster.getSelectedItem());

        String array[] = new String[2];

        array = employeestr.split(" - ");

        roster.setFloor(jComboFloorRoster.getSelectedItem().toString());

        Tools tools = new Tools();
        String datestr=tools.convertDatetoString(jTextDateRoster.getDate());
        roster.setDate(datestr);

        roster.setFloor(jComboFloorRoster.getSelectedItem().toString());
        roster.setStartTime(jComboStartRoster.getSelectedItem().toString());
        roster.setFinishTime(jComboFinishRoster.getSelectedItem().toString());
        roster.setEmployeeId(Integer.parseInt(array[0]));

        daoRo.Save(roster);
        readTableRoster();

        clearTextRoster();

    }//GEN-LAST:event_btnsaverosterActionPerformed

    private void jTabEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabEmployeeMouseClicked

        if (jTabEmployee.getSelectedRow() != -1){
            clearText();
            DefaultTableModel model = (DefaultTableModel) jTabEmployee.getModel();

            EmployeeDAO dao = new EmployeeDAO();

            int id = (int)jTabEmployee.getValueAt(jTabEmployee.getSelectedRow(), 0);
            List<Employee> array1 = new ArrayList<>();
            array1= dao.search(id);

            for (Employee c : array1) {
                try {
                    employeename.setText(c.getName());

                    if(!c.getDateofbirth().isEmpty()){
                        employeedateofbirth.setDate(formatter.parse(c.getDateofbirth()));
                    }

                    employeepps.setText(c.getPpsnumber());
                    employeephone.setText(c.getPhone());
                    employeeemail.setText(c.getEmail());
                    employeeaddress.setText(c.getAddress());
                    employeecertificate.setText(c.getCertificate());
                    employeespecialist.setText(c.getSpecialist());
                    employeecountry.setSelectedItem(c.getNationality());
                    employeepassport.setText(c.getPassport());

                    if(!c.getStartdate().isEmpty()){
                        employeestartdate.setDate(formatter.parse(c.getStartdate()));
                    }

                    employeelocation.setText(c.getLocation());
                    employeetypeoftime.setSelectedItem(c.getTypeoftime());
                    employeejobtitle.setText(c.getJobtitle());
                    employeepassword.setText("");
                    employeelevel.setSelectedIndex(c.getLevel());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error. There is a problem with the table: "+ex);
                }
            }

        }

    }//GEN-LAST:event_jTabEmployeeMouseClicked

    private void jTabEmployeeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabEmployeeKeyReleased
        if (jTabEmployee.getSelectedRow() != -1){
            clearText();
            DefaultTableModel model = (DefaultTableModel) jTabEmployee.getModel();

            EmployeeDAO dao = new EmployeeDAO();

            int id = (int)jTabEmployee.getValueAt(jTabEmployee.getSelectedRow(), 0);
            List<Employee> array1 = new ArrayList<>();
            array1= dao.search(id);

            for (Employee c : array1) {
                try {
                    employeename.setText(c.getName());

                    if(!c.getDateofbirth().isEmpty()){
                        employeedateofbirth.setDate(formatter.parse(c.getDateofbirth()));
                    }

                    employeepps.setText(c.getPpsnumber());
                    employeephone.setText(c.getPhone());
                    employeeemail.setText(c.getEmail());
                    employeeaddress.setText(c.getAddress());
                    employeecertificate.setText(c.getCertificate());
                    employeespecialist.setText(c.getSpecialist());
                    employeecountry.setSelectedItem(c.getNationality());
                    employeepassport.setText(c.getPassport());

                    if(!c.getStartdate().isEmpty()){
                        employeestartdate.setDate(formatter.parse(c.getStartdate()));
                    }

                    employeelocation.setText(c.getLocation());
                    employeetypeoftime.setSelectedItem(c.getTypeoftime());
                    employeejobtitle.setText(c.getJobtitle());
                    employeepassword.setText("");
                    employeelevel.setSelectedIndex(c.getLevel());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error. There is a problem with the table: "+ex);
                }
            }

        }

    }//GEN-LAST:event_jTabEmployeeKeyReleased

    private void employeenameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeenameFocusLost
        // TODO add your handling code here:
        String texto=employeename.getText();
        
        Tools tools = new Tools();
        boolean result = tools.validateText(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Name of Employee not valid. Type it again");
            employeename.requestFocus();
        }
    }//GEN-LAST:event_employeenameFocusLost

    private void employeenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeenameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeenameActionPerformed

    private void employeedateofbirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeedateofbirthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_employeedateofbirthFocusLost

    private void employeeppsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeppsFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_employeeppsFocusGained

    private void employeeppsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeppsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeppsActionPerformed

    private void employeephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeephoneActionPerformed

    private void employeeemailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeemailFocusLost
        // TODO add your handling code here:
        String texto=employeeemail.getText();

        Tools tools = new Tools();
        boolean result = tools.validateEmail(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Email not valid. Type it again");
            employeeemail.requestFocus();
        }
    }//GEN-LAST:event_employeeemailFocusLost

    private void employeeemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeemailActionPerformed

    private void employeeaddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeaddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeaddressActionPerformed

    private void employeecertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeecertificateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeecertificateActionPerformed

    private void employeecountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeecountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeecountryActionPerformed

    private void employeepassportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeepassportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeepassportActionPerformed

    private void employeestartdateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeestartdateFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_employeestartdateFocusLost

    private void employeelocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeelocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeelocationActionPerformed

    private void employeetypeoftimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeetypeoftimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeetypeoftimeActionPerformed

    private void employeejobtitleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeejobtitleFocusLost
        // TODO add your handling code here:
        String texto=employeejobtitle.getText();

        Tools tools = new Tools();
        boolean result = tools.validateText(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Job Title not valid. Type it again");
            employeejobtitle.requestFocus();
        }
    }//GEN-LAST:event_employeejobtitleFocusLost

    private void employeepasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeepasswordFocusLost
        // TODO add your handling code here:

        String passw=employeepassword.getText();

        Tools tools = new Tools();
        boolean result = tools.validatePassword(passw);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Password not valid. Type it again");
            employeepassword.requestFocus();
        }
    }//GEN-LAST:event_employeepasswordFocusLost

    private void btnsaveemployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveemployeeActionPerformed
        // Button save employee

        Employee employee = new Employee();
        EmployeeDAO dao = new EmployeeDAO();

        Tools tools = new Tools();
        String datestr=tools.convertDatetoString(employeedateofbirth.getDate());

        employee.setName(employeename.getText());
        employee.setDateofbirth(datestr);
        employee.setPpsnumber(employeepps.getText());
        employee.setPhone(employeephone.getText());
        employee.setEmail(employeeemail.getText());
        employee.setAddress(employeeaddress.getText());
        employee.setCertificate(employeecertificate.getText());
        employee.setSpecialist(employeespecialist.getText());
        employee.setNationality(employeecountry.getSelectedItem().toString());
        employee.setPassport(employeepassport.getText());

        String datestr2=tools.convertDatetoString(employeestartdate.getDate());
        employee.setStartdate(datestr2);
        employee.setLocation(employeelocation.getText());
        employee.setTypeoftime(employeetypeoftime.getSelectedItem().toString());
        employee.setJobtitle(employeejobtitle.getText());
        employee.setPassword(String.valueOf(employeepassword.getPassword()));
        employee.setLevel(employeelevel.getSelectedIndex());

        dao.Save(employee);
        readTable();

        clearText();

    }//GEN-LAST:event_btnsaveemployeeActionPerformed

    private void btnupdateemployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateemployeeActionPerformed
        // TODO add your handling code here:
        if (employeepassword.getText()==null || employeepassword.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Please type the password again ");   
           //employeepassword.requestFocus();
           
        }else {
            DefaultTableModel model = (DefaultTableModel) jTabEmployee.getModel();
            if (jTabEmployee.getSelectedRow() != -1){
                Employee employee = new Employee();
                EmployeeDAO dao = new EmployeeDAO();

                Tools tools = new Tools();
                String datestr=tools.convertDatetoString(employeedateofbirth.getDate());

                employee.setName(employeename.getText());
                employee.setDateofbirth(datestr);
                employee.setPpsnumber(employeepps.getText());
                employee.setPhone(employeephone.getText());
                employee.setEmail(employeeemail.getText());
                employee.setAddress(employeeaddress.getText());
                employee.setCertificate(employeecertificate.getText());
                employee.setSpecialist(employeespecialist.getText());
                employee.setNationality(employeecountry.getSelectedItem().toString());
                employee.setPassport(employeepassport.getText());

                String datestr2=tools.convertDatetoString(employeestartdate.getDate());
                employee.setStartdate(datestr2);
                employee.setLocation(employeelocation.getText());
                employee.setTypeoftime(employeetypeoftime.getSelectedItem().toString());
                employee.setJobtitle(employeejobtitle.getText());
                employee.setPassword(String.valueOf(employeepassword.getPassword()));
                employee.setLevel(employeelevel.getSelectedIndex());
                employee.setEmployeeId( (int)jTabEmployee.getValueAt(jTabEmployee.getSelectedRow(), 0));

                dao.update(employee);
                readTable();

                clearText();
            }
        }
    }//GEN-LAST:event_btnupdateemployeeActionPerformed

    private void btndeleteemployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteemployeeActionPerformed
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_OPTION);

	// 0=yes, 1=no, 2=cancel
        if (input == 0){
            if (jTabEmployee.getSelectedRow() != -1){
                Employee employee = new Employee();
                EmployeeDAO daoE = new EmployeeDAO();

                employee.setEmployeeId( (int)jTabEmployee.getValueAt(jTabEmployee.getSelectedRow(), 0));

                daoE.delete(employee);
                readTable();
                clearText();
            }
        }
    }//GEN-LAST:event_btndeleteemployeeActionPerformed

    private void jTextIdItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdItemActionPerformed

    private void jComboBoxItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxItemCategoryActionPerformed
        // TODO add your handling code here:
        // System.out.println("I am in");
        //   jTextItemDescription.setEnabled(true);

    }//GEN-LAST:event_jComboBoxItemCategoryActionPerformed

    private void jTextItemDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextItemDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextItemDescriptionActionPerformed

    private void jBtnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOpenFileActionPerformed
        // TODO add your handling code here:
          
        if (jTextItemDescription.getText() != null && jTextItemDescription.getText().length() > 3) {
                // Cria o objeto Janela de Sele��o de Arquivos
                javax.swing.JFileChooser seletor =
                new javax.swing.JFileChooser();
                // seletor.setCurrentDirectory(new File());

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image JPG", "jpg");
                seletor.addChoosableFileFilter(filter);
                seletor.setAcceptAllFileFilterUsed(false);
                seletor.setCurrentDirectory(new File("D:\\CCT projects\\imagem"));
                // Exibir a janela de sele��o
                int acao = seletor.showOpenDialog(this);

                // Captura o arquivo selecionado na janela
                java.io.File f = seletor.getSelectedFile();

                // Essa vari�vel deve ser declara no in�cio do
                // c�digo, logo ap�s o nome da classe
                // ex.  public class Cadastro{
                    //          String caminho;

                    caminho = f.getPath();
                    Image img = new ImageIcon(caminho).getImage().getScaledInstance(foto.getWidth(),foto.getHeight(), Image.SCALE_DEFAULT );
                    foto.setIcon(new ImageIcon(img));
                    String nome = jTextItemDescription.getText().trim();
                    //alterar aqui
                    File copia = new File("./src/images/"+nome+".jpg");
                    Tools tools = new Tools();
                    tools.copiar(f, copia);
                    caminho = "./src/images/"+nome+".jpg";
        }else{
               JOptionPane.showMessageDialog(null, "Error. The description can not be empty. Type it again ");
        }

    }//GEN-LAST:event_jBtnOpenFileActionPerformed

    private void jTabItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabItemMouseClicked
        // TODO add your handling code here:

        Image img=null;
        if (jTabItem.getSelectedRow() != -1){
            Item item = new Item();
            ItemDAO dao = new ItemDAO();

            jTextIdItem.setText(jTabItem.getValueAt(jTabItem.getSelectedRow(), 0).toString());
            String combo=jTabItem.getValueAt(jTabItem.getSelectedRow(), 1).toString();
            jComboBoxItemCategory.setSelectedItem(combo);
            jTextItemDescription.setText(jTabItem.getValueAt(jTabItem.getSelectedRow(), 2).toString());

            List<Item> array1 = new ArrayList<>();
            array1= dao.searchItem(Integer.parseInt(jTextIdItem.getText()));

            for (Item c : array1) {
                caminho=(c.getPicture());
            }

            img = new ImageIcon(caminho).getImage().getScaledInstance(foto.getWidth(),foto.getHeight(), Image.SCALE_DEFAULT );
            foto.setIcon(new ImageIcon(img));

        }
    }//GEN-LAST:event_jTabItemMouseClicked

    private void jTabItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabItemKeyReleased
        // TODO add your handling code here:

        Image img=null;
        if (jTabItem.getSelectedRow() != -1){
            Item item = new Item();
            ItemDAO dao = new ItemDAO();

            jTextIdItem.setText(jTabItem.getValueAt(jTabItem.getSelectedRow(), 0).toString());
            String combo=jTabItem.getValueAt(jTabItem.getSelectedRow(), 1).toString();
            jComboBoxItemCategory.setSelectedItem(combo);
            jTextItemDescription.setText(jTabItem.getValueAt(jTabItem.getSelectedRow(), 2).toString());

            List<Item> array1 = new ArrayList<>();
            array1= dao.searchItem(Integer.parseInt(jTextIdItem.getText()));

            for (Item c : array1) {
                caminho=(c.getPicture());
            }

            img = new ImageIcon(caminho).getImage().getScaledInstance(foto.getWidth(),foto.getHeight(), Image.SCALE_DEFAULT );
            foto.setIcon(new ImageIcon(img));
        }

    }//GEN-LAST:event_jTabItemKeyReleased

    private void jBtnSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveItemActionPerformed
        // TODO add your handling code here:
        // Button save employee

        Item item = new Item();
        ItemDAO daoI = new ItemDAO();

        item.setCategory(jComboBoxItemCategory.getSelectedItem().toString());
        item.setDescription(jTextItemDescription.getText());
        item.setPicture(caminho);

        daoI.Save(item);
        readTableItem();

        clearTextItem();

    }//GEN-LAST:event_jBtnSaveItemActionPerformed

    private void jbtnupdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnupdateItemActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTabItem.getModel();
        if (jTabItem.getSelectedRow() != -1){
            Item item = new Item();
            ItemDAO daoI = new ItemDAO();

            item.setItemId( (int)jTabItem.getValueAt(jTabItem.getSelectedRow(), 0));
            item.setCategory(jComboBoxItemCategory.getSelectedItem().toString());
            item.setDescription(jTextItemDescription.getText());
            item.setPicture(caminho);
//            if (itemImage != null){
//                //  item.setPicture(getImage());
//            }

            daoI.update(item);

            readTableItem();
            clearTextItem();

        }
    }//GEN-LAST:event_jbtnupdateItemActionPerformed

    private void btndeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteItemActionPerformed
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_OPTION);

	// 0=yes, 1=no, 2=cancel
        if (input == 0){
            if (jTabItem.getSelectedRow() != -1){
                Item item = new Item();
                ItemDAO daoI = new ItemDAO();

                item.setItemId( (int)jTabItem.getValueAt(jTabItem.getSelectedRow(), 0));

                daoI.delete(item);
                readTableItem();
                clearTextItem();
            }
        }
    }//GEN-LAST:event_btndeleteItemActionPerformed

    private void jTabResidentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabResidentMouseClicked
        // TODO add your handling code here:

        clearTextResident();
        DefaultTableModel model = (DefaultTableModel) jTabResident.getModel();
        ResidentHistory resident = new ResidentHistory();
        NextKind nextkind = new NextKind();
        ResidentDAO daoR = new ResidentDAO();
        List<ResidentHistory> residents = new ArrayList();
        List<NextKind> nextkinds = new ArrayList();

        int id = (int)jTabResident.getValueAt(jTabResident.getSelectedRow(), 0);

        residents=daoR.searchResident(id);

        for (ResidentHistory c : residents) {
            jTextResidentName.setText(c.getName());

            if(!c.getDateofbirth().isEmpty()){
                try {
                    jTextResidentDateofBirth.setDate(formatter.parse(c.getDateofbirth()));
                } catch (ParseException ex) {
                     JOptionPane.showMessageDialog(null, "Error. The date can not be empty. Type it again "+ex);
                }
            }

            jTextResidentPPS.setText(c.getPpsnumber());
            jTextResidentFloor.setText(c.getFloor());
            jTextResidentRoom.setText(String.valueOf(c.getRoom()));
            jComboResidentDiet.setSelectedItem(c.getDiet());
            jComboResidentFluid.setSelectedItem(c.getFluid());
            jComboResidentAssistance.setSelectedItem(String.valueOf(c.getAssistanceof()));
            jComboResidentMobility.setSelectedItem(c.getMobility());
            jTextResidentAction.setText(c.getAction());
            jTextResidentHistory.setText(c.getHistory());
            jTextResidentPresent.setText(c.getPresentCondition());
            caminho=(c.getPicture());
        }

        Image img = new ImageIcon(caminho).getImage().getScaledInstance(jResidentPhoto.getWidth(),jResidentPhoto.getHeight(), Image.SCALE_DEFAULT );
        jResidentPhoto.setIcon(new ImageIcon(img));

        nextkinds=daoR.searchResidentNext(id);

        for (NextKind c : nextkinds) {
            jTextResidentKindName.setText(c.getNextName());
            jTextResidentKindEmail.setText(c.getNextEmail());
            jTextResidentKindPhone.setText(c.getNextPhone());
            jTextResidentKindAddress.setText(c.getNextAddress());
        }

    }//GEN-LAST:event_jTabResidentMouseClicked

    private void jTabResidentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabResidentMouseReleased

    }//GEN-LAST:event_jTabResidentMouseReleased

    private void jTabResidentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabResidentKeyReleased
        // TODO add your handling code here:

        clearTextResident();
        DefaultTableModel model = (DefaultTableModel) jTabResident.getModel();
        ResidentHistory resident = new ResidentHistory();
        NextKind nextkind = new NextKind();
        ResidentDAO daoR = new ResidentDAO();
        List<ResidentHistory> residents = new ArrayList();
        List<NextKind> nextkinds = new ArrayList();

        int id = (int)jTabResident.getValueAt(jTabResident.getSelectedRow(), 0);

        residents=daoR.searchResident(id);

        for (ResidentHistory c : residents) {
            jTextResidentName.setText(c.getName());

            if(!c.getDateofbirth().isEmpty()){
                try {
                    jTextResidentDateofBirth.setDate(formatter.parse(c.getDateofbirth()));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error. The date can not be empty. Type it again "+ex);
                }
            }

            jTextResidentPPS.setText(c.getPpsnumber());
            jTextResidentFloor.setText(c.getFloor());
            jTextResidentRoom.setText(String.valueOf(c.getRoom()));
            jComboResidentDiet.setSelectedItem(c.getDiet());
            jComboResidentFluid.setSelectedItem(c.getFluid());
            jComboResidentAssistance.setSelectedItem(String.valueOf(c.getAssistanceof()));
            jComboResidentMobility.setSelectedItem(c.getMobility());
            jTextResidentAction.setText(c.getAction());
            jTextResidentHistory.setText(c.getHistory());
            jTextResidentPresent.setText(c.getPresentCondition());
            caminho=(c.getPicture());
        }

        Image img = new ImageIcon(caminho).getImage().getScaledInstance(jResidentPhoto.getWidth(),jResidentPhoto.getHeight(), Image.SCALE_DEFAULT );
        jResidentPhoto.setIcon(new ImageIcon(img));

        nextkinds=daoR.searchResidentNext(id);

        for (NextKind c : nextkinds) {
            jTextResidentKindName.setText(c.getNextName());
            jTextResidentKindEmail.setText(c.getNextEmail());
            jTextResidentKindPhone.setText(c.getNextPhone());
            jTextResidentKindAddress.setText(c.getNextAddress());
        }

    }//GEN-LAST:event_jTabResidentKeyReleased

    private void jTextResidentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentNameActionPerformed

    private void jTextResidentDateofBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentDateofBirthFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentDateofBirthFocusLost

    private void jTextResidentPPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentPPSFocusLost
        // TODO add your handling code here:
        Tools tools = new Tools();
        boolean result = tools.validatePPS(jTextResidentPPS.getText());
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. PPS is not valid number. Type it again");
            jTextResidentRoom.requestFocus();
        }

    }//GEN-LAST:event_jTextResidentPPSFocusLost

    private void jTextResidentPPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentPPSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentPPSActionPerformed

    private void jTextResidentRoomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentRoomFocusLost
        // TODO add your handling code here:
        int texto= Integer.parseInt(jTextResidentRoom.getText());

        Tools tools = new Tools();
        boolean result = tools.validateInt(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Room is not valid number. Type it again");
            jTextResidentRoom.requestFocus();
        }
    }//GEN-LAST:event_jTextResidentRoomFocusLost

    private void jTextResidentRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentRoomActionPerformed

    private void jBtnResidentPicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnResidentPicActionPerformed
         
        if (jTextResidentName.getText() != null && jTextResidentName.getText().length() > 3) {
             // criation of the object window to select files
            javax.swing.JFileChooser seletor =
            new javax.swing.JFileChooser();
          
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image JPG", "jpg");
            seletor.addChoosableFileFilter(filter);
            seletor.setAcceptAllFileFilterUsed(false);
            seletor.setCurrentDirectory(new File("D:\\CCT projects\\imagem"));
            // Show the window of selection
            int acao = seletor.showOpenDialog(this);

            // capture the file selected on the window
            java.io.File f = seletor.getSelectedFile();

            //using a public variable to get the path of the file
            caminho = f.getPath();
            //setting the image with the path selected
            Image img = new ImageIcon(caminho).getImage().getScaledInstance(jResidentPhoto.getWidth(),jResidentPhoto.getHeight(), Image.SCALE_DEFAULT );
            //setting the resident photo with the image
            jResidentPhoto.setIcon(new ImageIcon(img));
            //getting the name of the resident
            String nome = jTextResidentName.getText().trim();
            //creating a File changing the name of it
            File copia = new File("./src/images/"+nome+".jpg");
            //instance of tools class
            Tools tools = new Tools();
            //coping the file to the package images
            tools.copiar(f, copia);
            //setting the new address and name
            caminho = "./src/images/"+nome+".jpg";
        }else{
            JOptionPane.showMessageDialog(null, "Error. The resident Name can not be empty. Type it again");
        }
    }//GEN-LAST:event_jBtnResidentPicActionPerformed

    private void jTextResidentKindNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentKindNameFocusLost
        // TODO add your handling code here:
        String texto=jTextResidentKindName.getText();
        
        Tools tools = new Tools();
        boolean result = tools.validateText(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Name of Kind not valid. Type it again");
            jTextResidentKindName.requestFocus();
        }
        

    }//GEN-LAST:event_jTextResidentKindNameFocusLost

    private void jTextResidentKindEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentKindEmailFocusLost
        // TODO add your handling code here:
        String texto=jTextResidentKindEmail.getText();

        Tools tools = new Tools();
        boolean result = tools.validateEmail(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Email not valid. Type it again");
            jTextResidentKindEmail.requestFocus();
        }
    }//GEN-LAST:event_jTextResidentKindEmailFocusLost

    private void jTextResidentKindEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentKindEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentKindEmailActionPerformed

    private void jTextResidentKindPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentKindPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentKindPhoneActionPerformed

    private void jTextResidentKindAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentKindAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentKindAddressActionPerformed

    private void jBtnSaveResidentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveResidentActionPerformed
        // TODO add your handling code here:

        Resident resident = new Resident();
        ResidentHistory residenth = new ResidentHistory();
        NextKind nextkind = new NextKind();
        ResidentDAO daoR = new ResidentDAO();

        resident.setName(jTextResidentName.getText());

        Tools tools = new Tools();
        String datestr=tools.convertDatetoString(jTextResidentDateofBirth.getDate());
        resident.setDateofbirth(datestr);

        resident.setPpsnumber(jTextResidentPPS.getText());
        resident.setFloor(jTextResidentFloor.getText());
        resident.setRoom(Integer.valueOf(jTextResidentRoom.getText()));
        resident.setPicture(caminho);

        int id = daoR.Save(resident);

        residenth.setResidentId(id);
        residenth.setDiet(jComboResidentDiet.getSelectedItem().toString());
        residenth.setFluid(jComboResidentFluid.getSelectedItem().toString());
        residenth.setAssistanceof(Integer.parseInt(jComboResidentAssistance.getSelectedItem().toString()));
        residenth.setMobility(jComboResidentMobility.getSelectedItem().toString());
        residenth.setAction(jTextResidentAction.getText());
        residenth.setHistory(jTextResidentHistory.getText());
        residenth.setPresentCondition(jTextResidentPresent.getText());

        daoR.SaveHistory(residenth);

        nextkind.setNextName(jTextResidentKindName.getText());
        nextkind.setNextEmail(jTextResidentKindEmail.getText());
        nextkind.setNextPhone(jTextResidentKindPhone.getText());
        nextkind.setNextAddress(jTextResidentKindAddress.getText());
        nextkind.setResidentId(id);

        daoR.SaveKind(nextkind);

        readTableResident();

        clearTextResident();
    }//GEN-LAST:event_jBtnSaveResidentActionPerformed

    private void jbtnupdateResidentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnupdateResidentActionPerformed
        // TODO add your handling code here:
        Resident resident = new Resident();
        ResidentHistory residenth = new ResidentHistory();
        NextKind nextkind = new NextKind();
        ResidentDAO daoR = new ResidentDAO();

        int id = (int)jTabResident.getValueAt(jTabResident.getSelectedRow(), 0);

        residenth.setResidentId(id);
        residenth.setName(jTextResidentName.getText());

        Tools tools = new Tools();
        String datestr=tools.convertDatetoString(jTextResidentDateofBirth.getDate());
        residenth.setDateofbirth(datestr);

        residenth.setPpsnumber(jTextResidentPPS.getText());
        residenth.setFloor(jTextResidentFloor.getText());
        residenth.setRoom(Integer.valueOf(jTextResidentRoom.getText()));
        residenth.setPicture(caminho);

        residenth.setDiet(jComboResidentDiet.getSelectedItem().toString());
        residenth.setFluid(jComboResidentFluid.getSelectedItem().toString());
        residenth.setAssistanceof(Integer.parseInt(jComboResidentAssistance.getSelectedItem().toString()));
        residenth.setMobility(jComboResidentMobility.getSelectedItem().toString());
        residenth.setAction(jTextResidentAction.getText());
        residenth.setHistory(jTextResidentHistory.getText());
        residenth.setPresentCondition(jTextResidentPresent.getText());

        nextkind.setNextName(jTextResidentKindName.getText());
        nextkind.setNextEmail(jTextResidentKindEmail.getText());
        nextkind.setNextPhone(jTextResidentKindPhone.getText());
        nextkind.setNextAddress(jTextResidentKindAddress.getText());
        nextkind.setResidentId(id);

        daoR.update(residenth,nextkind);

        readTableResident();

        clearTextResident();
    }//GEN-LAST:event_jbtnupdateResidentActionPerformed

    private void btndeleteResidentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteResidentActionPerformed
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_OPTION);

	// 0=yes, 1=no, 2=cancel
        if (input == 0){
            if (jTabResident.getSelectedRow() != -1){
                NextKind nextkind = new NextKind();
                ResidentDAO daoR = new ResidentDAO();

                nextkind.setResidentId((int)jTabResident.getValueAt(jTabResident.getSelectedRow(), 0));

                daoR.deleteNext(nextkind);
                readTableResident();
                clearTextResident();

            }
        }
    }//GEN-LAST:event_btndeleteResidentActionPerformed

    private void jTabResidentRoutineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabResidentRoutineMouseClicked
        // TODO add your handling code here:
        clearTextRoutine();
        DefaultTableModel model = (DefaultTableModel) jTabResidentRoutine.getModel();
        ResidentHistory resident = new ResidentHistory();
        ResidentDAO daoR = new ResidentDAO();
        List<ResidentHistory> residents = new ArrayList();

        idResident = (int)jTabResidentRoutine.getValueAt(jTabResidentRoutine.getSelectedRow(), 0);

        residents=daoR.searchResident(idResident);

        for (ResidentHistory c : residents) {
            jTextResidentNameRoutine.setText(c.getName());
            caminho=(c.getPicture());
        }

        Image img = new ImageIcon(caminho).getImage().getScaledInstance(jLResidentPicRoutine.getWidth(),jLResidentPicRoutine.getHeight(), Image.SCALE_DEFAULT );
        jLResidentPicRoutine.setIcon(new ImageIcon(img));

    }//GEN-LAST:event_jTabResidentRoutineMouseClicked

    private void jTabResidentRoutineMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabResidentRoutineMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabResidentRoutineMouseReleased

    private void jTabResidentRoutineKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabResidentRoutineKeyReleased
        // TODO add your handling code here:
        clearTextRoutine();
        DefaultTableModel model = (DefaultTableModel) jTabResidentRoutine.getModel();
        ResidentHistory resident = new ResidentHistory();
        ResidentDAO daoR = new ResidentDAO();
        List<ResidentHistory> residents = new ArrayList();

        idResident = (int)jTabResidentRoutine.getValueAt(jTabResidentRoutine.getSelectedRow(), 0);

        residents=daoR.searchResident(idResident);

        for (ResidentHistory c : residents) {
            jTextResidentNameRoutine.setText(c.getName());
            caminho=(c.getPicture());
        }

        Image img = new ImageIcon(caminho).getImage().getScaledInstance(jLResidentPicRoutine.getWidth(),jLResidentPicRoutine.getHeight(), Image.SCALE_DEFAULT );
        jLResidentPicRoutine.setIcon(new ImageIcon(img));
    }//GEN-LAST:event_jTabResidentRoutineKeyReleased

    private void jTextResidentNameRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextResidentNameRoutineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextResidentNameRoutineActionPerformed

    private void jTextDateRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDateRoutineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDateRoutineActionPerformed

    private void jComboItemsRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboItemsRoutineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboItemsRoutineActionPerformed

    private void jTextCommentsRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCommentsRoutineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCommentsRoutineActionPerformed

    private void jTabRoutineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabRoutineMouseClicked
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTabRoutine.getModel();

        RoutineDAO daoR = new RoutineDAO();
        ItemDAO daoI = new ItemDAO();
        ResidentDAO daoRes = new ResidentDAO();
        List<DailyRoutine> routine = new ArrayList();

        int idRoutine = (int)jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 0);

        String residentstr=String.valueOf(jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 1));
        String arrayres[] = new String[2];
        arrayres = residentstr.split(" - ");
        idResident=Integer.parseInt(arrayres[0]);

        List <Resident> listresident = new ArrayList<>();
        listresident=daoRes.searchResidentId(idResident);

        Resident resident=null;
        for(Resident a : listresident) {
            resident = new Resident();
            resident.setResidentId(a.getResidentId());
            resident.setName(a.getName());

        }

        String itemstr=String.valueOf(jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 4));
        String array[] = new String[2];
        array = itemstr.split(" - ");
        int itemid=Integer.parseInt(array[0]);

        List <Item> listItem = new ArrayList<>();
        listItem=daoI.searchItem(itemid);

        String itemname=null;
        Item item = null;
        for(Item b : listItem) {
            if(b.getItemId()==itemid){

                item = new Item();
                item.setItemId(b.getItemId());
                item.setCategory(b.getCategory());
                item.setDescription(b.getDescription());
                itemname=b.getItemId()+" - "+b.getDescription();
            }
        }

        routine=daoR.search(idRoutine);

        for (DailyRoutine c : routine) {
            jTextResidentNameRoutine.setText(resident.getName());
            jTextDateRoutine.setText(c.getDate());
            jComboMealRoutine.setSelectedItem(c.getMeal());
            jComboQuantityRoutine.setSelectedItem(c.getQuantity());
            jTextCommentsRoutine.setText(c.getComments());
        }

        if(itemname!=null){
            jComboItemsRoutine.setSelectedItem(itemname);
        }

    }//GEN-LAST:event_jTabRoutineMouseClicked

    private void jTabRoutineKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabRoutineKeyReleased
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTabRoutine.getModel();

        RoutineDAO daoR = new RoutineDAO();
        ItemDAO daoI = new ItemDAO();
        ResidentDAO daoRes = new ResidentDAO();
        List<DailyRoutine> routine = new ArrayList();

        int idRoutine = (int)jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 0);

        String residentstr=String.valueOf(jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 1));
        String arrayres[] = new String[2];
        arrayres = residentstr.split(" - ");
        idResident=Integer.parseInt(arrayres[0]);

        List <Resident> listresident = new ArrayList<>();
        listresident=daoRes.searchResidentId(idResident);

        Resident resident=null;
        for(Resident a : listresident) {
            resident = new Resident();
            resident.setResidentId(a.getResidentId());
            resident.setName(a.getName());

        }

        String itemstr=String.valueOf(jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 4));
        String array[] = new String[2];
        array = itemstr.split(" - ");
        int itemid=Integer.parseInt(array[0]);

        List <Item> listItem = new ArrayList<>();
        listItem=daoI.searchItem(itemid);

        String itemname=null;
        Item item = null;
        for(Item b : listItem) {
            if(b.getItemId()==itemid){

                item = new Item();
                item.setItemId(b.getItemId());
                item.setCategory(b.getCategory());
                item.setDescription(b.getDescription());
                itemname=b.getItemId()+" - "+b.getDescription();
            }
        }

        routine=daoR.search(idRoutine);

        for (DailyRoutine c : routine) {
            jTextResidentNameRoutine.setText(resident.getName());
            jTextDateRoutine.setText(c.getDate());
            jComboMealRoutine.setSelectedItem(c.getMeal());
            jComboQuantityRoutine.setSelectedItem(c.getQuantity());
            jTextCommentsRoutine.setText(c.getComments());
        }

        if(itemname!=null){
            jComboItemsRoutine.setSelectedItem(itemname);
        }

    }//GEN-LAST:event_jTabRoutineKeyReleased

    private void btndeleteRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteRoutineActionPerformed
        
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_OPTION);

	// 0=yes, 1=no, 2=cancel
        if (input == 0){
            // checking if the routine is selected
            if (jTabRoutine.getSelectedRow() != -1){

                DailyRoutine routine = new DailyRoutine();
                RoutineDAO daoRo = new RoutineDAO();

                //setting the routine id
                routine.setId((int)jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 0));

                //using the method delete
                daoRo.delete(routine);
                //updating the routine table
                readTableRoutine();
                //cleaning the form
                clearTextRoutine();
            }
        }
    }//GEN-LAST:event_btndeleteRoutineActionPerformed

    private void jbtnupdateRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnupdateRoutineActionPerformed
        // checking if a routine was selected
         if (jTabRoutine.getSelectedRow() != -1){
            //checking if the resident name is not empty 
            if(jTextResidentNameRoutine.getText()!= null && jTextResidentNameRoutine.getText().length()>0){
                DailyRoutine routine = new DailyRoutine();
                EmployeeDAO daoE = new EmployeeDAO();
                ResidentDAO daoRe = new ResidentDAO();
                ItemDAO daoItem = new ItemDAO();
                //list of resident
                List <Resident> listresident = new ArrayList<>();
                listresident=daoRe.searchResidentId(idResident);
                Resident resident=null;
                //setting all data from the resident
                for(Resident c : listresident) {
                    if(c.getName().equals(jTextResidentNameRoutine.getText())){
                        resident = new Resident();
                        resident.setResidentId(c.getResidentId());
                        routine.setResident(resident);
                    }
                }
                RoutineDAO daoRou = new RoutineDAO();
                //getting the value from the combo item
                String itemstr=String.valueOf(jComboItemsRoutine.getSelectedItem());
                String array[] = new String[2];
                array = itemstr.split(" - ");
                //getting the item id
                int itemid=Integer.parseInt(array[0]);
                //instance of the item list
                List <Item> listItem = new ArrayList<>();
                //searching item by id
                listItem=daoItem.searchItem(itemid);
                Item item = null;
                for(Item c : listItem) {
                    item = new Item();
                    //setting all data of item
                    item.setItemId(itemid);
                    item.setCategory(c.getCategory());
                    item.setDescription(c.getDescription());
                    routine.setItem(item);
                }
                List <Employee> listemployee = new ArrayList<>();
                listemployee=daoE.search(idEmployee);
                Employee emplo = null;
                for(Employee c : listemployee) {
                    if(c.getEmployeeId()==idEmployee){
                        emplo = new Employee();
                        emplo.setEmployeeId(c.getEmployeeId());
                        emplo.setName(c.getName());
                        emplo.setDateofbirth(c.getDateofbirth());
                        emplo.setPpsnumber(c.getPpsnumber());
                        emplo.setPhone(c.getPhone());
                        emplo.setEmail(c.getEmail());
                        emplo.setAddress(c.getAddress());
                        emplo.setCertificate(c.getCertificate());
                        emplo.setSpecialist(c.getSpecialist());
                        emplo.setNationality(c.getNationality());
                        emplo.setPassport(c.getPassport());
                        emplo.setStartdate(c.getStartdate());
                        emplo.setLocation(c.getLocation());
                        emplo.setTypeoftime(c.getTypeoftime());
                        emplo.setJobtitle(c.getJobtitle());
                        emplo.setPassword(c.getPassword());
                    }
                }
                //setting the routine data
                routine.setId(Integer.parseInt(jTabRoutine.getValueAt(jTabRoutine.getSelectedRow(), 0).toString()));
                routine.setDate(actualDate.toString());
                routine.setMeal(jComboMealRoutine.getSelectedItem().toString());
                routine.setQuantity(jComboQuantityRoutine.getSelectedItem().toString());
                routine.setComments(jTextCommentsRoutine.getText());
                routine.setEmployee(emplo);
                //updating the routine data
                daoRou.update(routine);
                readTableRoutine();
                clearTextRoutine();
            }else{
                JOptionPane.showMessageDialog(null, "Error. Resident is not valid. Select it from the table");
                jTextResidentNameRoutine.requestFocus();
            }
         }
    }//GEN-LAST:event_jbtnupdateRoutineActionPerformed

    private void jBtnSaveRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveRoutineActionPerformed
        // TODO add your handling code here:
        //checking if the resident name is not empty
        if(jTextResidentNameRoutine.getText()!= null && jTextResidentNameRoutine.getText().length()>0){
            DailyRoutine routine = new DailyRoutine();
            EmployeeDAO daoE = new EmployeeDAO();
            ResidentDAO daoRe = new ResidentDAO();
            ItemDAO daoItem = new ItemDAO();

            //list of resident
            List <Resident> listresident = new ArrayList<>();
            //searching the resident using dao to connect to the database
            listresident=daoRe.searchResidentId(idResident);

            Resident resident=null;

            //loop to setting all data from resident
            for(Resident c : listresident) {
                if(c.getName().equals(jTextResidentNameRoutine.getText())){
                    resident = new Resident();
                    resident.setResidentId(c.getResidentId());
                    routine.setResident(resident);

                }
            }

            RoutineDAO daoRo = new RoutineDAO();
            //getting the value selected from the comboitem
            String itemstr=String.valueOf(jComboItemsRoutine.getSelectedItem());
            //setting the array with this values eg 1 - banana
            String array[] = new String[2];
            //spliting the values in two
            array = itemstr.split(" - ");
            //getting the item id
            int itemid=Integer.parseInt(array[0]);
            //list of item
            List <Item> listItem = new ArrayList<>();
            //searching the item using dao to connect to the database
            listItem=daoItem.searchItem(itemid);
            Item item = null;
            //for loop to fill the informations
            for(Item c : listItem) {
                if(c.getItemId()==itemid){
                    item = new Item();
                    item.setItemId(c.getItemId());
                    item.setCategory(c.getCategory());
                    item.setDescription(c.getDescription());
                }
            }
            List <Employee> listemployee = new ArrayList<>();
            //searching the employee using dao to connect to the database
            listemployee=daoE.search(idEmployee);
            Employee emplo = null;
            for(Employee c : listemployee) {
                if(c.getEmployeeId()==idEmployee){
                    //setting the employee data
                    emplo = new Employee();
                    emplo.setEmployeeId(c.getEmployeeId());
                    emplo.setName(c.getName());
                    emplo.setDateofbirth(c.getDateofbirth());
                    emplo.setPpsnumber(c.getPpsnumber());
                    emplo.setPhone(c.getPhone());
                    emplo.setEmail(c.getEmail());
                    emplo.setAddress(c.getAddress());
                    emplo.setCertificate(c.getCertificate());
                    emplo.setSpecialist(c.getSpecialist());
                    emplo.setNationality(c.getNationality());
                    emplo.setPassport(c.getPassport());
                    emplo.setStartdate(c.getStartdate());
                    emplo.setLocation(c.getLocation());
                    emplo.setTypeoftime(c.getTypeoftime());
                    emplo.setJobtitle(c.getJobtitle());
                    emplo.setPassword(c.getPassword());
                }
            }
            //setting routine data
            routine.setDate(actualDate.toString());
            routine.setMeal(jComboMealRoutine.getSelectedItem().toString());
            routine.setItem(item);
            routine.setQuantity(jComboQuantityRoutine.getSelectedItem().toString());
            routine.setComments(jTextCommentsRoutine.getText());
            routine.setEmployee(emplo);
            //saving routine using DAO class
            daoRo.Save(routine);
            //updating the routine table
            readTableRoutine();

           

        }else{
            JOptionPane.showMessageDialog(null, "Error. Resident is not valid. Select it from the table");
            jTextResidentNameRoutine.requestFocus();
        }
       
       
        

    }//GEN-LAST:event_jBtnSaveRoutineActionPerformed

    private void listItems(String selected){
         jComboItemsRoutine.removeAllItems();
        ItemDAO dao = new ItemDAO(); 
        List<Item> listitem = new ArrayList();
        listitem=dao.searchCategory(selected);
       
        int i=0;
        for(Item e:listitem){
           
            jComboItemsRoutine.addItem(e.getItemId()+" - "+ e.getDescription());
            i++;
        }
    }
    
    private void jComboCategoryItemRoutineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoryItemRoutineActionPerformed
        // TODO add your handling code here:
        
        listItems(jComboCategoryItemRoutine.getSelectedItem().toString());

    }//GEN-LAST:event_jComboCategoryItemRoutineActionPerformed

    private void jTextResidentNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextResidentNameFocusLost
        // TODO add your handling code here:
        String texto=jTextResidentName.getText();
        
        Tools tools = new Tools();
        boolean result = tools.validateText(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Name of Resident not valid. Type it again");
            jTextResidentName.requestFocus();
        }
        
    }//GEN-LAST:event_jTextResidentNameFocusLost

    private void employeeppsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeppsFocusLost
        // TODO add your handling code here:
        
        String texto=employeepps.getText();
        
        Tools tools = new Tools();
        boolean result = tools.validatePPS(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. PPS number not valid. Type it again");
            employeepps.requestFocus();
        }
    }//GEN-LAST:event_employeeppsFocusLost

    private void jTextItemDescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextItemDescriptionFocusLost
        // TODO add your handling code here:
        
        String texto=jTextItemDescription.getText();
        
        Tools tools = new Tools();
        boolean result = tools.validateText(texto);
        if (!result){
            JOptionPane.showMessageDialog(null, "Error. Description not valid. Type it again");
            jTextItemDescription.requestFocus();
        }
    }//GEN-LAST:event_jTextItemDescriptionFocusLost

    private void jComboStartRosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboStartRosterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboStartRosterActionPerformed

    private void employeelevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeelevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeelevelActionPerformed

    private void itemsbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsbtn2ActionPerformed
        // TODO add your handling code here:
        jTabPanel.setSelectedIndex(3);
        readTableItem();
        jLabTitle.setText("Item");
    }//GEN-LAST:event_itemsbtn2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NHMSMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NHMSMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NHMSMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NHMSMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NHMSMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndeleteItem;
    private javax.swing.JButton btndeleteResident;
    private javax.swing.JButton btndeleteRoster;
    private javax.swing.JButton btndeleteRoutine;
    private javax.swing.JButton btndeleteemployee;
    private javax.swing.JButton btnsaveemployee;
    private javax.swing.JButton btnsaveroster;
    private javax.swing.JButton btnupdateemployee;
    private javax.swing.JButton btnupdateroster;
    private javax.swing.JButton dailyroutinebtn;
    private javax.swing.JFormattedTextField employeeaddress;
    private javax.swing.JButton employeebtn;
    private javax.swing.JTextField employeecertificate;
    private javax.swing.JComboBox<String> employeecountry;
    private com.toedter.calendar.JDateChooser employeedateofbirth;
    private javax.swing.JTextField employeeemail;
    private javax.swing.JTextField employeejobtitle;
    private javax.swing.JComboBox<String> employeelevel;
    private javax.swing.JFormattedTextField employeelocation;
    private javax.swing.JTextField employeename;
    private javax.swing.JFormattedTextField employeepassport;
    private javax.swing.JPasswordField employeepassword;
    private javax.swing.JFormattedTextField employeephone;
    private javax.swing.JFormattedTextField employeepps;
    private javax.swing.JTextField employeespecialist;
    private com.toedter.calendar.JDateChooser employeestartdate;
    private javax.swing.JComboBox<String> employeetypeoftime;
    private javax.swing.JLabel foto;
    private javax.swing.JButton itemsbtn2;
    private javax.swing.JButton jBtnOpenFile;
    private javax.swing.JButton jBtnResidentPic;
    private javax.swing.JButton jBtnSaveItem;
    private javax.swing.JButton jBtnSaveResident;
    private javax.swing.JButton jBtnSaveRoutine;
    private javax.swing.JComboBox<String> jComboBoxItemCategory;
    private javax.swing.JComboBox<String> jComboCategoryItemRoutine;
    private javax.swing.JComboBox<String> jComboEmployeeRoster;
    private javax.swing.JComboBox<String> jComboFinishRoster;
    private javax.swing.JComboBox<String> jComboFloorRoster;
    private javax.swing.JComboBox<String> jComboItemsRoutine;
    private javax.swing.JComboBox<String> jComboMealRoutine;
    private javax.swing.JComboBox<String> jComboQuantityRoutine;
    private javax.swing.JComboBox<String> jComboResidentAssistance;
    private javax.swing.JComboBox<String> jComboResidentDiet;
    private javax.swing.JComboBox<String> jComboResidentFluid;
    private javax.swing.JComboBox<String> jComboResidentMobility;
    private javax.swing.JComboBox<String> jComboStartRoster;
    private javax.swing.JLabel jLResidentPicRoutine;
    private javax.swing.JLabel jLabTitle;
    private javax.swing.JLabel jLabUserName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JPanel jPaneResidentImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelEmployee;
    private javax.swing.JPanel jPanelItemImage;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelResident;
    private javax.swing.JPanel jPanelResidentPicRoutine;
    private javax.swing.JPanel jPanelRoster;
    private javax.swing.JPanel jPanelRoutine;
    private javax.swing.JPanel jPanelTabItemContainer;
    private javax.swing.JLabel jResidentPhoto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTabEmployee;
    private javax.swing.JTable jTabItem;
    private javax.swing.JTabbedPane jTabPanel;
    private javax.swing.JTable jTabResident;
    private javax.swing.JTable jTabResidentRoutine;
    private javax.swing.JTable jTabRoster;
    private javax.swing.JTable jTabRoutine;
    private javax.swing.JTextField jTextCommentsRoutine;
    private com.toedter.calendar.JDateChooser jTextDateRoster;
    private javax.swing.JFormattedTextField jTextDateRoutine;
    private javax.swing.JTextField jTextIdItem;
    private javax.swing.JTextField jTextItemDescription;
    private javax.swing.JTextField jTextResidentAction;
    private com.toedter.calendar.JDateChooser jTextResidentDateofBirth;
    private javax.swing.JTextField jTextResidentFloor;
    private javax.swing.JTextField jTextResidentHistory;
    private javax.swing.JFormattedTextField jTextResidentKindAddress;
    private javax.swing.JFormattedTextField jTextResidentKindEmail;
    private javax.swing.JTextField jTextResidentKindName;
    private javax.swing.JFormattedTextField jTextResidentKindPhone;
    private javax.swing.JTextField jTextResidentName;
    private javax.swing.JTextField jTextResidentNameRoutine;
    private javax.swing.JFormattedTextField jTextResidentPPS;
    private javax.swing.JTextField jTextResidentPresent;
    private javax.swing.JTextField jTextResidentRoom;
    private javax.swing.JButton jbtnupdateItem;
    private javax.swing.JButton jbtnupdateResident;
    private javax.swing.JButton jbtnupdateRoutine;
    private javax.swing.JButton residentbtn;
    private javax.swing.JButton rosterbtn;
    // End of variables declaration//GEN-END:variables
}
