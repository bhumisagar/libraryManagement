
package library.system;

import com.jfoenix.controls.JFXButton;
import static com.mysql.cj.MysqlType.SET;
import static com.mysql.cj.xdevapi.UpdateType.SET;
import dbutils.ConnectDB;
import static java.awt.event.PaintEvent.UPDATE;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class LibraryController implements Initializable {

    @FXML
    private TextField BookInputID;
    @FXML
    private Label bookName;
    @FXML
    private Label bookAuthor;
    
    private ConnectDB connectDB;
    @FXML
    private TextField MemberInputID;
    @FXML
    private Label memberName;
    @FXML
    private Label memberInfo;
    @FXML
    private JFXButton issueBook;
    @FXML
    private TextField bookID;
    @FXML
    private ListView<String> listView;
    @FXML
    private JFXButton renew;
    @FXML
    private JFXButton submission;
    ;
    private String MemberID;

    
    

     private static class IOEception extends Exception {

        public IOEception() {
        }
    }
    
    
    public void loadWindow(String location, String title, String iconPath) throws IOEception, IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Stage stage = new Stage (StageStyle.DECORATED);
        stage.setTitle(title);
        Image icon = new Image (getClass().getResourceAsStream(iconPath));
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root));
        stage.show();        
    }

    
    private void loadwindow(String addBookAddBookfxml, String add_Book, String imgaddBookpng) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   
    
    public class Test {
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        connectDB = new ConnectDB();
    }
    
    
    
    
    
    
    //ADD_BOOK Controller
    @FXML
    private void addBook(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/AddBook/AddBook.fxml", "Add Book", "/img/addBook.png" );
    }

   
    
    //ADD_MEMBER Controller
    @FXML
    private void addMember(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/AddMember/AddMember.fxml", "Add Member", "/img/adduser.png"); 
    }

    
    //VIEW_BOOKS Controller
    @FXML
    private void listBook(ActionEvent event) throws IOEception, IOException  {
        
        loadWindow("/BookList/BookList.fxml", "Book List", "/img/viewbooks.png");        
    }

    
    
    //VIEW_LIST Controller
    @FXML
    private void listMember(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/MemberList/MemberList.fxml", "Member List", "/img/viewmember.png");     
    }
    
    
    //ISSUED_BOOKS Controller
    @FXML
    private void issuedBooks(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/IssuedBooks/IssuedBooks.fxml", "Issued Books List", "/img/bookissued.png"); 
    }
    
    
    
    

    
    //BOOK_ID_ENTER controller
    @FXML
    private void bookIDEnter(ActionEvent event) {
        
        String id = BookInputID.getText();
        String sql ="SELECT * FROM tbl_addbook WHERE id = '" + id + "'";
        Connection conn;
        
        try {
            conn = ConnectDB.getConnections();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            
            boolean flag = false;
            
            while(rst.next()){
                String bName = rst.getString("title");
                String bAuthor = rst.getString("author");
                
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                flag = true;    
            }
            
            if(!flag) {
                bookName.setText("No Such Book Is Available");
                bookAuthor.setText("No Such Author Is Available");
            }  
            
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    //MEMBER_ID_ENTER Controller
    @FXML
    private void memberInputAction(ActionEvent event) {
         String id = MemberInputID.getText();
        String sql ="SELECT * FROM tbl_addmember WHERE id = '" + id + "'";
        Connection conn;
        
        try {
            conn = ConnectDB.getConnections();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            
            boolean flag = false;
            
            while(rst.next()){
                String bMember = rst.getString("name");
                String bInfo = rst.getString("mobile");
                
                memberName.setText(bMember);
                memberInfo.setText(bInfo);
                flag = true;    
            }
            
            if(!flag) {
                memberName.setText("No Such Member Is Available");
                memberInfo.setText("No Such Info Is Available");
            }  
            
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    //ISSUE_BOOK_ID Controller
    @FXML
    private void IssueBook(ActionEvent event) {
        String memberID = MemberInputID.getText();
        String bookID = BookInputID.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText( " Are You Sure To Issue The Book" + bookName.getText() +  "\n  To" + memberName.getText() );
        Optional<ButtonType> response = alert.showAndWait();
            
        if(response.get() == ButtonType.OK){
            
            String sql = "INSERT INTO tbl_issue(bookID, memberID) VALUES(?,?)";
            Connection conn;
            try {
                conn = ConnectDB.getConnections();
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, bookID);
                pst.setString(2, memberID);
                
                 pst.execute();
                 
                Alert alert1 =new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issued Sucessfully To" + memberName.getText());
                alert1.showAndWait();
                return;
                
            } catch (SQLException ex) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
    }
    
    
    
    
    //LOAD_INFO_REISSUE CONTROLLER
    @FXML
    private void loadInfo(ActionEvent event) {
        
        ObservableList<String> issueData = FXCollections.observableArrayList();
        
        String id = bookID.getText();
        String sql ="SELECT * FROM tbl_issue WHERE bookID = '" + id + "'";
        try {
            
            Connection conn = ConnectDB.getConnections();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            
            while(rst.next()){
                String BookID = id;
                String memberID = rst.getString("memberID");
                String issueTime = rst.getString("issueTime");
                
                int renew_count = rst.getInt("renew_count");
                
                issueData.add("Issued Information: ");
                issueData.add("Issued Time And Date: " + issueTime);
                issueData.add("Renew Count : " + renew_count);
                issueData.add("Book Information: ");
                
                
                String sql2 ="SELECT * FROM tbl_addBook WHERE id = '" + BookID + "'";
                Connection con2 = ConnectDB.getConnections();
                PreparedStatement pst2 = con2.prepareStatement(sql2);
                ResultSet rst2 = pst2.executeQuery();
                
                while(rst2.next()){
                    issueData.add("Book Name: " + rst2.getString("title"));
                    issueData.add("Book ID: " + rst2.getString("id"));
                    issueData.add("Book Author: " + rst2.getString("author"));
                    issueData.add("Book Publisher: " + rst2.getString("publisher"));
                }
                
                
                String sql3 ="SELECT * FROM tbl_addMember WHERE id = '" + MemberID + "'";
                Connection con3 = ConnectDB.getConnections();
                PreparedStatement pst3 = con3.prepareStatement(sql3);
                ResultSet rst3 = pst3.executeQuery();
                
                while(rst3.next()){
                    issueData.add("Member Name: " + rst3.getString("name"));
                    issueData.add("Member ID: " + rst3.getString("id"));
                    issueData.add("Member Mobile: " + rst3.getString("mobile"));
                    issueData.add("Member Email: " + rst3.getString("email"));
                }
                
            }    
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      listView.getItems().setAll(issueData);
        
    }

    
    //RENEWBTN
    @FXML
    private void renewbtn(ActionEvent event) {
        String bookIDS = bookID.getText();
        
       
       
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText( " Are You Sure To Renew The Book" );
        Optional<ButtonType> response = alert.showAndWait();
        
        if(response.get() == ButtonType.OK){
            
            
//            UPDATE tbl_issue; 
//            DateTime issueTime =CURRENT_TIMESTAMP();

//                            "UPDATE tbl_issue SET issueTime = NOW() where bookID = ? "


            String sql = "UPDATE tbl_issue SET issueTime = NOW() where bookID = (?)";
//            String sql = "UPDATE * FROM tbl_issue WHERE issueTime='" + issueTime + "'";

            Connection conn;
            try {
                conn = ConnectDB.getConnections();
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, bookIDS);

                pst.execute();
                
                Alert alert1 =new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Book Renew Sucessfully To" + memberName.getText());
                alert1.showAndWait();
                return;
                
            } catch (SQLException ex) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
    }

    
    
    //SUBMISSIONBTN
    @FXML
    private void submissionbtn(ActionEvent event) {
        
        String memberID = MemberInputID.getText();
        String bookIDS = bookID.getText();
        
       
       
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText( " Are You Sure To Submit The Book" );
        Optional<ButtonType> response = alert.showAndWait();
        
        if(response.get() == ButtonType.OK){
            String sql = "DELETE FROM tbl_issue WHERE bookID = (?)";
            
            Connection conn;
            try {
                conn = ConnectDB.getConnections();
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, bookIDS);

                pst.execute();
                
                Alert alert1 =new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Book Submitted Sucessfully!!" );
                alert1.showAndWait();
                return;
                
            } catch (SQLException ex) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


