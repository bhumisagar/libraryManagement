package IssuedBooks;

import dbutils.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class IssuedBooksController implements Initializable {

    @FXML
    private TableView<IssuedBooks> tableview;
    @FXML
    private TableColumn<IssuedBooks, String> bookIDCol;
    @FXML
    private TableColumn<IssuedBooks, String> memberIDCol;
    @FXML
    private TableColumn<IssuedBooks, String> issueTimeCol;
    
    private ConnectDB connectDB;
    
    ObservableList<IssuedBooks> list = FXCollections.observableArrayList();
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initcol();
        connectDB = new ConnectDB();
        loadData();
    }    

    public void initcol() {
        
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        memberIDCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        issueTimeCol.setCellValueFactory(new PropertyValueFactory<>("issueTime"));
        
    }

    
    
    
    public void loadData() {
        
         String sql = "SELECT * FROM tbl_issue";
         Connection conn;
         try {
            conn = ConnectDB.getConnections();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
             while(rs.next()){
                
                String bookID = rs.getString("bookID");
                String memberID = rs.getString("memberID");
                String issueTime = rs.getString("issueTime");
                
                
                list.add(new IssuedBooks(bookID, memberID, issueTime));
                
                
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.getItems().setAll(list);
    }

    

    public static class IssuedBooks {
        
        private final SimpleStringProperty bookID;
        private final SimpleStringProperty memberID;
        private final SimpleStringProperty issueTime;


        public IssuedBooks(String bookID, String memberID, String issueTime){
            
            this.bookID = new SimpleStringProperty(bookID);
            this.memberID = new SimpleStringProperty(memberID);
            this.issueTime = new SimpleStringProperty(issueTime);
        }    
            
        public String getBookID() {
        return bookID.get();
        }

        public String getMemberID() {
            return memberID.get();
        }

        public String getIssueTime() {
            return issueTime.get();
        }
    } 
    
}
