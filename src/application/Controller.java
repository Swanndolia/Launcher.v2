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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
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
	public static GameVersion version = new GameVersion("1.8", null);
	public static GameInfos infos = new GameInfos("AzurPixel v4", version, null);
	public static final File dir = infos.getGameDir();
	public static Saver tweaks = new Saver(new File(dir, "AzurPixel.properties"));
	public static final CrashReporter crash = new CrashReporter(infos.getServerName(), new File(dir, "crashs"));
	private Window owner;
	   
    @FXML
    private Slider memorySlider;
    
    @FXML
    private Label memoryLabel;
    
    @FXML
    private Slider themeSlider;
    
    @FXML
    private Label themeLabel;
    
    @FXML
    private Slider versionSlider;
    
    @FXML
    private Label versionLabel;
    
    @FXML
    private Slider graphicsSlider;
    
    @FXML
    private Label graphicsLabel;
    
    @FXML
	private Label pingLabel;

    @FXML
    private Label playerLabel;
	
    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;
    
    @FXML
    private TextField ipField;
    
    @FXML
    private TextField portField;

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
    private AnchorPane mainPane;
    
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
		if (!settingsPane.isVisible()) {
			settingsPane.setVisible(true);
			profilPane.setVisible(false);
		}
		else {
			settingsPane.setVisible(false);
			profilPane.setVisible(true);
		}
	}

	@FXML
	 protected void login(ActionEvent event) {

	    if (!nameField.getText().matches("^[A-Za-z0-9]{3,20}$") && passField.getText().isEmpty())
	        Alerts.showAlert(Alert.AlertType.ERROR, owner, "Invalid Fields", "Your username should contain between 3 to 20 alphanumeric chars");
	    else if (!nameField.getText().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$") && !passField.getText().isEmpty()) 
	        Alerts.showAlert(Alert.AlertType.ERROR, owner, "Invalid Mail", "Use you mojang account email and password or just type an username without password");
	    else {
	    	try {
	      		Login.tryLogin(nameField.getText(), passField.getText());
				Alerts.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Succes",  "Welcome " + Login.getName());
				switchElementsState(true);
				loadSkin(Login.getName());

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
		new Thread("Launch procedure") {
			@Override
			public void run() {
				try {
					if (tweaks.get("version").equals("1.7"))
						version = new GameVersion(tweaks.get("version"), GameType.V1_7_10);
					else 
						version = new GameVersion(tweaks.get("version"), GameType.V1_8_HIGHER);
					if (tweaks.get("version").contains("Forge"))
						infos = new GameInfos("AzurPixel v4", version, new GameTweak[] {GameTweak.FORGE});
					else 
						infos = new GameInfos("AzurPixel v4", version, new GameTweak[] {GameTweak.OPTIFINE});
					new ConnectToServer(ipField.getText(), portField.getText());
					Launch.launch();
				} catch (LaunchException | InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}.start();
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
    	settingsPane.setVisible(!loged);
    	profilPane.setVisible(loged);
    	if(keepLoginCheck.isVisible())
    		keepLoginCheck.setVisible(loged);
    	keepLogin.setVisible(loged);
    	ipField.setVisible(!loged);
    	portField.setVisible(!loged);
    }
	
    public void initialize() {
    	defSettings();
		
    	versionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

    		versionLabel.setText("Version: 1." + Double.toString(newValue.intValue()).replaceAll(".0$", ""));
    		tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", ""));
        });
    	
    	memorySlider.valueProperty().addListener((observable, oldValue, newValue) -> {

    		memoryLabel.setText("Memory: " + Double.toString(newValue.intValue()).replaceAll(".0$", "") + " mb");
    		tweaks.set("memory", memoryLabel.getText().replaceAll("[^0-9.]?", ""));
        });

    	graphicsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.intValue() == 0) 
        		graphicsLabel.setText("Graphics: Potatoe");
    		else if (newValue.intValue() > 0 && newValue.intValue() < 20) 
    			graphicsLabel.setText("Graphics: Very Low");
    		else if (newValue.intValue() >= 20 && newValue.intValue() < 40) 
    			graphicsLabel.setText("Graphics: Low");
    		else if (newValue.intValue() >= 40 && newValue.intValue() < 60) 
    			graphicsLabel.setText("Graphics: Medium");
    		else if (newValue.intValue() >= 60 && newValue.intValue() < 80) 
    			graphicsLabel.setText("Graphics: High");
    		else if (newValue.intValue() >= 80 && newValue.intValue() < 99)
    			graphicsLabel.setText("Graphics: Very High");
    		else
    			graphicsLabel.setText("Graphics: Ultra");
    		tweaks.set("graphics", graphicsLabel.getText().replaceAll("Graphics\\: ", ""));
        });
    	
    	themeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		mainPane.getStylesheets().clear();
        	if (newValue.intValue() >= 0 && newValue.intValue() < 20)
        		tweaks.set("theme", "ClassyDark");
        	else if (newValue.intValue() >= 20 && newValue.intValue() < 40)
        		tweaks.set("theme", "HappyColor");
        	else if (newValue.intValue() >= 40 && newValue.intValue() < 60)
        		tweaks.set("theme", "BlueWaffle");
        	else if (newValue.intValue() >= 60 && newValue.intValue() < 80)
        		tweaks.set("theme", "PinkyPromise");
        	else 
        		tweaks.set("theme", "SweetWhite");
        	themeLabel.setText("Theme: " + tweaks.get("theme"));
			mainPane.getStylesheets().add("/ui/resources/" + tweaks.get("theme") + ".css");
        });

    }
    
    private void defSettings() {
    	versionLabel.setText("Version: " + tweaks.get("version", "1.8"));
    	memoryLabel.setText("Memory: " + tweaks.get("memory", "2048") + " mb");
    	graphicsLabel.setText("Graphics: " + tweaks.get("graphics", "Medium"));
    	themeLabel.setText("Theme: " + tweaks.get("theme", "Classydark"));
		tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", ""));
		tweaks.set("memory", memoryLabel.getText().replaceAll("[^0-9.]?", ""));
		tweaks.set("graphics", graphicsLabel.getText().replaceAll("Graphics\\: ", ""));
		tweaks.set("theme", themeLabel.getText().replaceAll("Theme\\: ", ""));
		mainPane.getStylesheets().add("/ui/resources/" + tweaks.get("theme") + ".css");

		versionSlider.valueProperty().set(Integer.valueOf(tweaks.get("version").replaceFirst("1.", "")));
		memorySlider.valueProperty().set(Integer.valueOf(tweaks.get("memory")));
		
    	if (tweaks.get("graphics").equals("Potatoe"))
    		graphicsSlider.valueProperty().set(0);
    	else if (tweaks.get("graphics").equals("Very Low"))
    		graphicsSlider.valueProperty().set(15);
    	else if (tweaks.get("graphics").equals("Low"))
    		graphicsSlider.valueProperty().set(30);
    	else if (tweaks.get("graphics").equals("Medium"))
    		graphicsSlider.valueProperty().set(50);
    	else if (tweaks.get("graphics").equals("High"))
    		graphicsSlider.valueProperty().set(70);
    	else if (tweaks.get("graphics").equals("Very High"))
    		graphicsSlider.valueProperty().set(85);
    	else
    		graphicsSlider.valueProperty().set(100);
    	
    	if (tweaks.get("theme").equals("ClassyDark"))
    		themeSlider.valueProperty().set(0);
    	else if (tweaks.get("theme").equals("HappyColor"))
    		themeSlider.valueProperty().set(30);
    	else if (tweaks.get("theme").equals("BlueWaffle"))
    		themeSlider.valueProperty().set(50);
    	else if (tweaks.get("theme").equals("PinkyPromise"))
    		themeSlider.valueProperty().set(70);
    	else
    		themeSlider.valueProperty().set(100);

    }
    
	@FXML
    protected void logout() throws AuthenticationException {
		Alerts.showAlert(Alert.AlertType.INFORMATION, owner, "Logout Succes",  "See you later " + Login.getName());
		Login.tryLogin("", "Disconnect me");
		switchElementsState(false);
		skin = new Image("/ui/resources/skin.png");
		imageView.setImage(skin);
    }
}
