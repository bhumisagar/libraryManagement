
package library.system;

import com.jfoenix.controls.JFXButton;
import dbutils.ConnectDB;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
    private void listBook(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/BookList/BookList.fxml", "Book List", "/img/viewbooks.png");        
    }

    
    
    //VIEW_LIST Controller
    @FXML
    private void listMember(ActionEvent event) throws IOEception, IOException {
        
        loadWindow("/MemberList/MemberList.fxml", "Member List", "/img/viewmember.png");     
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
                memberName.setText("No Such Book Is Available");
                memberInfo.setText("No Such Author Is Available");
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
        alert.setContentText(memberName.getText() + "Are You Sure To Issue The Book" + bookName.getText() + "\n To" );
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

    

    
    
    
   

  
}

