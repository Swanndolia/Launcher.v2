package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class Controller 
{
	@FXML
	private void appExit() 
	{
		System.exit(0);
	}
	
	@FXML
	private void appMinimize() 
	{
        Main.stage.setIconified(true);
	}
	
	@FXML
	private void openSettings() 
	{
		System.exit(0);
	}
	
    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;

	
    @FXML
	 protected void login(ActionEvent event) {
	        Window owner = loginButton.getScene().getWindow();
	        if(nameField.getText().isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, owner, "Invalid Fields", "Enter your mojang email or an username for normal auth");
	            return;
	        }
	        
	        if(passField.getText().isEmpty())
	        	System.out.print("not premium");
	        
	        showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Succes",  "Welcome " + nameField.getText());
	    }
    
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	}
