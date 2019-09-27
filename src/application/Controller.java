package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import Launcher.LaunchException;
import Launcher.minecraft.AuthInfos;
import Launcher.minecraft.GameInfos;
import Launcher.minecraft.GameTweak;
import Launcher.minecraft.GameType;
import Launcher.minecraft.GameVersion;
import Launcher.minecraft.util.ConnectToServer;
import Launcher.util.CrashReporter;
import Launcher.util.Saver;
import application.console.ConsoleFrame;
import fr.theshark34.supdate.BarAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import openauth.AuthenticationException;

public class Controller 

{
	public static final String url = "https://azurpixel.net";
	public static GameVersion version = new GameVersion("1.8", null);
	public static GameInfos infos = new GameInfos("AzurPixel v4", version, null);
	public static final File dir = infos.getGameDir();
	public static Saver tweaks = new Saver(new File(dir, "AzurPixel.properties"));
	public static final CrashReporter crash = new CrashReporter(infos.getServerName(), new File(dir, "crashs"));
	
	public int is = 0;
	   
    @FXML
    private Slider memorySlider;
    
    @FXML
    private Label memoryLabel;
    
    @FXML
    private Label infoLabel;
    
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
    public AnchorPane mainPane;
    
    @FXML
    private AnchorPane settingsPane;
    
    @FXML
    private AnchorPane infoPane;
    
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
			infoPane.setVisible(false);
		}
		else {
			settingsPane.setVisible(false);
			infoPane.setVisible(true);
		}
	}

	@FXML
	 protected void login(ActionEvent event) {
	    if (!nameField.getText().matches("^[A-Za-z0-9]{3,20}$") && passField.getText().isEmpty())
	    	;
	    else if (!nameField.getText().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$") && !passField.getText().isEmpty()) 
	    	;
	    else {
	    	try {
	      		Login.tryLogin(nameField.getText(), passField.getText());
				// TODO 
	        	System.out.print("Login Succes, " + "Welcome " + Login.name + '\n');
				loadSkin(Login.name);
				if (keepLoginCheck.isVisible())
					tweaks.set("username", Login.authInfos.getUsername());
				switchElementsState(true);
	     	} catch (AuthenticationException e) {
	     		// TODO		
	        	System.out.print("Login Error," + "Incorrect mail or password, just type an username if you don't have mojang account" + '\n');
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
					setInfoText(null);
					infoPane.setVisible(true);
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

    public void loadSkin(String name) {
		new Thread(() -> {
			Platform.runLater(()-> 	 {
				try {
					skin = new Image(new URL("https://mc-heads.net/head/" + name + "/120").openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			});
			Platform.runLater(()-> 	imageView.setImage(skin));
		}).start();
    }
   
    public void switchElementsState(boolean loged) {
    	playButton.setVisible(loged);
    	logoutButton.setVisible(loged);
    	loginButton.setVisible(!loged);
    	nameField.setDisable(loged);
    	passField.setVisible(!loged);
    	settingsPane.setVisible(!loged);
    	if(keepLoginCheck.isVisible())
    		keepLoginCheck.setVisible(!loged);
    	keepLogin.setVisible(!loged);
    	ipField.setVisible(loged);
    	portField.setVisible(loged);
    }
	
    public void initialize() {
    	ConsoleFrame console = new ConsoleFrame();
    	System.out.print("Azurpixel / Saber LLC Launcher console, send a full capture of this to report any bugs" + '\n');
    	defSettings();
    	System.out.print("java version: " + System.getProperty("java.version") + '\n');
    	System.out.print("os arch: " + System.getProperty("os.arch") + '\n');
    	if (!System.getProperty("os.arch").contains("64"))
    		memorySlider.setMax(1024);

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
    	
    	if (tweaks.get("username").equals(""))
    		keepLogin.setVisible(true);
    	else {
    		keepLoginCheck.setVisible(true);
		try {
				Login.refresh();
				nameField.setText("Loged in as " + tweaks.get("username"));
				loadSkin(tweaks.get("username"));
				switchElementsState(true);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
			}
    	}
    	
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                	if (passField.isVisible()) {
                		login(null);
                	} else
                		try {
                			play(null);
                		} catch (LaunchException | InterruptedException e) {
                			// TODO Auto-generated catch block
                		}
               	}
                else if (ke.getCode() == KeyCode.ESCAPE) 
                	console.setVisible(true);
            }
        });
    }
    
    private void defSettings() {
    	
    	if (tweaks.get("username") == null)
    		tweaks.set("username", "");
    	versionLabel.setText("Version: " + tweaks.get("version", "1.8"));
    	memoryLabel.setText("Memory: " + tweaks.get("memory", "1024") + " mb");
    	graphicsLabel.setText("Graphics: " + tweaks.get("graphics", "Medium"));
    	themeLabel.setText("Theme: " + tweaks.get("theme", "ClassyDark"));
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
		// TODO 
    	System.out.print("Logout Succes, " + "See you later" + '\n');
		Login.authInfos = new AuthInfos("", "", "");	
		switchElementsState(false);
		skin = new Image("/ui/resources/skin.png");
		imageView.setImage(skin);
		tweaks.set("username", "");
		tweaks.set("access-token", "");
		nameField.setText("");
		passField.setText("");
    }

	public void setInfoText(String stringList[]) {
		new Thread(() -> {
			try {
				if (BarAPI.getNumberOfTotalBytesToDownload() != 0) {
					while(Launch.inUpdate) {
						setInfoLoading("Download in progress, please wait", "Download finished", 300);
					}
				}
				setInfoLoading("Starting the game, please wait", "Here you go ! Have fun !", 300);
				while (stringList[is] != null) {
					Platform.runLater(()-> 	infoLabel.setText(stringList[is]));
					is++;
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}).start();
	}

	private void setInfoLoading(String string, String end, int time) throws InterruptedException {
		Platform.runLater(()-> 	infoLabel.setText(string));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + "."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + ".."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + "..."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(""));
	}
}
