package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import Launcher.LaunchException;
import Launcher.minecraft.GameInfos;
import Launcher.minecraft.GameTweak;
import Launcher.minecraft.GameType;
import Launcher.minecraft.GameVersion;
import Launcher.minecraft.util.ConnectToServer;
import Launcher.util.CrashReporter;
import Launcher.util.Saver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import openauth.AuthenticationException;

public class Controller 

{
	public static final String url = "https://azurpixel.net";
	public static final String img  = url.concat("/app/webroot/img/uploads/");
	public static GameVersion version = new GameVersion("AzurPixel_V1_8", GameType.V1_8_HIGHER);
	public static GameInfos infos = new GameInfos("AzurPixel v4", version, new GameTweak[] {GameTweak.OPTIFINE});
	public static final File dir = infos.getGameDir();
	public static Saver tweaks = new Saver(new File(dir, "AzurPixel.properties"));
	public static final CrashReporter crash = new CrashReporter(infos.getServerName(), new File(dir, "crashs"));
	public static final File logs = new File(dir, "/logs/logs.txt");
	private Window owner;
	private boolean opened = false;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;
    
    @FXML
	public TextField ipField;
    
    @FXML
    public TextField portField;

    @FXML
    private Button loginButton;
    
    @FXML
    private Button keepLogin;
    
    @FXML
    private Button keepLoginCheck;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button playButton;
    
    @FXML
    private Image skin;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private AnchorPane settingsPane;
    
    @FXML
    private AnchorPane profilPane;
    
    @FXML
    private AnchorPane newsPane;
    
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
		if (!opened) {
			opened = true;
			settingsPane.setVisible(true);
			profilPane.setVisible(false);
		}
		else {
			opened = false;
			settingsPane.setVisible(false);
			profilPane.setVisible(true);
		}
	}

	@FXML
	 protected void login(ActionEvent event) {

	    if (nameField.getText().isEmpty()) 
	        Alerts.showAlert(Alert.AlertType.ERROR, owner, "Invalid Fields", "Enter your mojang email or just an username if you don't have mojang account auth");
	    else {
	    	try {
	      		Login.tryLogin(nameField.getText(), passField.getText());
				Alerts.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Succes",  "Welcome " + Login.getName());
				switchElementsState(true);
				loadSkin(Login.getName());
				new ConnectToServer(ipField.getText(), portField.getText());
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
	private void play(ActionEvent event) throws LaunchException, InterruptedException {
		Launch.launch();
	}
   
    @FXML
	private void keepLogin(ActionEvent event) {
    	if (keepLoginCheck.isVisible()) {
    		keepLoginCheck.setVisible(false);
    		keepLogin.setVisible(true);
    	}
    	else {
    		keepLoginCheck.setVisible(true);
			keepLogin.setVisible(false);
    	}
   }
   
    public void loadSkin(String name) throws MalformedURLException, IOException {
	    skin = new Image(new URL("https://mc-heads.net/head/" + name + "/120").openStream());
		imageView.setImage(skin);
    }
   
    public void switchElementsState(boolean loged) {
    	playButton.setVisible(loged);
    	logoutButton.setVisible(loged);
    	loginButton.setVisible(!loged);
    	nameField.setEditable(!loged);
    	passField.setEditable(!loged);
    	if(keepLoginCheck.isVisible())
    		keepLoginCheck.setVisible(loged);
    	keepLogin.setVisible(loged);
    	ipField.setVisible(!loged);
    	portField.setVisible(!loged);
    }
	
	@FXML
    protected void logout() throws AuthenticationException {
		Login.tryLogin("", "Disconnect me");
		Alerts.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Error",  "Successfull Logout");
		switchElementsState(false);
		skin = new Image("/ui/resources/skin.png");
		imageView.setImage(skin);
    }
}
