
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SampleFormController {

	@FXML private Button submitBtn;
	@FXML private Button quitBtn;
	@FXML private TextField usernameTxf;
	@FXML private TextField passwordTxf;
	@FXML private Label errorMessageLbl;

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
		if(checkAuth(usernameTxf.getText(), passwordTxf.getText())) {
			Parent root = FXMLLoader.load(getClass().getResource("WelcomeForm.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) submitBtn.getScene().getWindow();
			
			stage.setTitle("FXML Welcome");
			stage.setScene(scene);
			stage.show();
		}
		else {
			errorMessageLbl.setText("Invalid Username/Passsword. Try again.");
		}
	}
	
	@FXML
	protected void handleQuitButtonAction(ActionEvent event) {
		Stage stage = (Stage) quitBtn.getScene().getWindow();
		stage.close();
	}
	
	public boolean checkAuth(String uname, String pword) throws Exception {
		String sDriverName = "org.sqlite.JDBC";
        Class.forName(sDriverName);
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:logindb.db";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select * from users");
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                if(uname.equals(username) && pword.equals(password)) return true;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }
}
