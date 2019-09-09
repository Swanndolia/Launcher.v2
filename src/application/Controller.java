package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import fr.theshark34.openauth.AuthenticationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class Controller 
{
	
    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;
    
    @FXML
    private CheckBox keepLogin;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button playButton;
    
    @FXML
    private Image skin;
    
    @FXML
    private ImageView imageView;
    
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
	 protected void login(ActionEvent event) {
    	Window owner = loginButton.getScene().getWindow();
	    if (nameField.getText().isEmpty()) 
	        Alerts.showAlert(Alert.AlertType.ERROR, owner, "Invalid Fields", "Enter your mojang email or just an username if you don't have mojang account auth");
	    else {
	    	try {
	      		Auth.tryAuth(nameField.getText(), passField.getText());
				Alerts.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Succes",  "Welcome " + Auth.getName());
				switchElementsState(true);
				loadSkin();
	     	} catch (AuthenticationException e) {
	       		Alerts.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Error",  "Incorrect mail or password, just type an username if you don't have mojang account");
	       	} catch (MalformedURLException e) {
	       		// TODO Auto-generated catch block
	        } catch (IOException e) {
				// TODO Auto-generated catch block
	        }
	    }
	}
    
    @FXML
    protected void logout() throws AuthenticationException {
		Auth.tryAuth("", "Disconnect me");
    }
    
    @FXML
	 protected void play(ActionEvent event) {

	}
    
    public void loadSkin() throws MalformedURLException, IOException {
		skin = new Image(new URL("https://mc-heads.net/head/" + Auth.getName() + "/120").openStream());
		imageView.setImage(skin);
    }
    
    public void switchElementsState(boolean loged) {
    	playButton.setVisible(loged);
    	logoutButton.setVisible(loged);
    	loginButton.setVisible(!loged);
    	nameField.setEditable(!loged);
    	passField.setEditable(!loged);
    }
}
