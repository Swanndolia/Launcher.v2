package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import Launcher.LaunchException;
import Launcher.minecraft.AuthInfos;
import Launcher.minecraft.GameInfos;
import Launcher.minecraft.GameTweak;
import Launcher.minecraft.GameType;
import Launcher.minecraft.GameVersion;
import Launcher.minecraft.util.ConnectToServer;
import Launcher.minecraft.util.MinecraftPing;
import Launcher.minecraft.util.MinecraftPing.MinecraftPingReply;
import Launcher.minecraft.util.Presets;
import Launcher.util.Saver;
import application.console.ConsoleFrame;
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
	private boolean inUpdate = true;
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	private AtomicBoolean tryToRun = new AtomicBoolean(false);
	private AtomicInteger is = new AtomicInteger(0);
	   
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
	
	private void setInfoPaneVisible() {
		if (!infoPane.isVisible()) {
			infoPane.setVisible(true);
			settingsPane.setVisible(false);
		}
	}

	@FXML
	 private void login(ActionEvent event) {
		noSpamButton();
	    if (!nameField.getText().matches("^[A-Za-z0-9]{3,20}$") && passField.getText().isEmpty()) {
     		String[] tableauChaine = {"Login Error,", "Invalid username", "you should use only alphanumeric", "between 3 and 20 characters lenght"};
     		setInfoPaneVisible();
     		setInfoText(tableauChaine, 4, 750);
        	System.out.print("Login Error, Invalid username, you should use only alphanumeric characters between 3 and 20 lenght" + '\n');
	    }
	    else if (!nameField.getText().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$") && !passField.getText().isEmpty()) {
     		String[] tableauChaine = {"Login Error,", "Invalids forms,", "type only a username to login as crack,", "or use your mojang email and password."};
     		setInfoPaneVisible();
     		setInfoText(tableauChaine, 4, 750);
        	System.out.print("Login Error," + "Invalids forms, type only a username to login as crack, or use your mojang email and password." + '\n');
	    }
	    else {
	    	try {
	      		Login.tryLogin(nameField.getText(), passField.getText());
	        	System.out.print("Login Succes, " + "Welcome " + Login.name + '\n');
				loadSkin(Login.name);
				if (keepLoginCheck.isVisible())
					tweaks.set("username", Login.authInfos.getUsername());
	     		String[] tableauChaine = {"Login Success,", "Welcome " + Login.name};
				switchElementsState(true);
	     		setInfoPaneVisible();
	     		setInfoText(tableauChaine, 2, 750);
	     	} catch (AuthenticationException e) {
	     		String[] tableauChaine = {"Login Error,", "Incorrect mail or password,", "if you don't have mojang account,", "just type a username without pass."};
	     		setInfoPaneVisible();
	     		setInfoText(tableauChaine, 4, 750);
	     		System.out.print("Login Error, Incorrect mail or password if you don't have mojang account just type a username without pass." + '\n');
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
					if (settingsPane.isVisible())
						openSettings();
					playInfo();
					logoutButton.setDisable(true);
					Launch.launch();
				} catch (LaunchException | InterruptedException e) {
		     		String[] tableauChaine = {"Launch Error,", "Please report this error,", "press escape to open the console", "then send a screenshot of it"};
		     		setInfoPaneVisible();
		     		setInfoText(tableauChaine, 4, 750);
		        	System.out.print("Launch Error, Please report this error, send a screenshot of this" + '\n' + e + '\n');
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

   private void loadSkin(String name) {
		new Thread(() -> {
			try {
				skin = new Image(new URL("https://mc-heads.net/head/" + name + "/120").openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			Platform.runLater(()-> 	imageView.setImage(skin));
		}).start();
    }
   
    private void switchElementsState(boolean loged) {
    	playButton.setVisible(loged);
    	logoutButton.setVisible(loged);
    	loginButton.setVisible(!loged);
    	nameField.setDisable(loged);
    	passField.setVisible(!loged);
    	if(keepLoginCheck.isVisible())
    		keepLoginCheck.setVisible(!loged);
    	keepLogin.setVisible(!loged);
    	ipField.setVisible(loged);
    	portField.setVisible(loged);
    }
    
    public void initialize() {
    	ConsoleFrame console = new ConsoleFrame();
    	System.out.print("Azurpixel / Saber LLC Launcher console, send a full capture of this to report any bugs" + '\n');
    	System.out.print("java version: " + System.getProperty("java.version") + '\n');
    	System.out.print("os arch: " + System.getProperty("os.arch") + '\n');
    	
    	if (!System.getProperty("os.arch").contains("64"))
    		memorySlider.setMax(1024);
    	defSettings();
    	showPing();
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
    		else if (newValue.intValue() > 0 && newValue.intValue() < 15) 
    			graphicsLabel.setText("Graphics: Very Low");
    		else if (newValue.intValue() >= 15 && newValue.intValue() < 30) 
    			graphicsLabel.setText("Graphics: Low");
    		else if (newValue.intValue() >= 30 && newValue.intValue() < 50) 
    			graphicsLabel.setText("Graphics: Medium");
    		else if (newValue.intValue() >= 50 && newValue.intValue() < 65) 
    			graphicsLabel.setText("Graphics: High");
    		else if (newValue.intValue() >= 65 && newValue.intValue() < 80)
    			graphicsLabel.setText("Graphics: Very High");
    		else if (newValue.intValue() >= 80 && newValue.intValue() < 95)
    			graphicsLabel.setText("Graphics: Ultra");
    		else
    			graphicsLabel.setText("Graphics: Custom");
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
	     		String[] tableauChaine = {"Login Error,", "Please report this error,", "press escape to open the console", "then send a screenshot of it"};
	     		setInfoPaneVisible();
	     		setInfoText(tableauChaine, 4, 750);
	        	System.out.print("Login Error, Please report this error, send a screenshot of this" + '\n' + e + '\n');
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
        		     		String[] tableauChaine = {"Launch Error,", "Please report this error,", "press escape to open the console", "then send a screenshot of it"};
        		     		setInfoPaneVisible();
        		     		setInfoText(tableauChaine, 4, 750);
        		        	System.out.print("Launch Error, Please report this error, send a screenshot of this" + '\n' + e + '\n');
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
    		graphicsSlider.valueProperty().set(7.5);
    	else if (tweaks.get("graphics").equals("Low"))
    		graphicsSlider.valueProperty().set(22.5);
    	else if (tweaks.get("graphics").equals("Medium"))
    		graphicsSlider.valueProperty().set(40);
    	else if (tweaks.get("graphics").equals("High"))
    		graphicsSlider.valueProperty().set(57.5);
    	else if (tweaks.get("graphics").equals("Very High"))
    		graphicsSlider.valueProperty().set(72.5);
    	else if (tweaks.get("graphics").equals("Ultra"))
    		graphicsSlider.valueProperty().set(87.5);
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
    private void logout() throws AuthenticationException {
 		String[] tableauChaine = {"Logout Success", "See you later !", "", ""};
		setInfoText(tableauChaine, 3, 750);
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
	
	private void playInfo() throws InterruptedException {
		new Thread(() -> {
			try {
				Update.update();
				inUpdate = false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
		}).start();
		try {
			Presets.presetSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
			while(inUpdate) {
				setInfoLoading("Download and file check in progress, please wait", "Download and file check finished", 300);
			}
		setInfoLoading("Starting the game, please wait", "Here you go, Have fun !", 300);
	}

	private void setInfoText(String[] stringList, int size, int time) {
		AtomicInteger sizeTS = new AtomicInteger(size);
		if (isRunning.compareAndSet(true, true)) {
			tryToRun.compareAndSet(false, true);
			is.set(0);
			Platform.runLater(()-> 	infoLabel.setText(""));
		}
			Thread t = new Thread(() -> {
				isRunning.set(true);
				try {
					while (true) {
						Platform.runLater(()-> 	infoLabel.setText(stringList[is.get()]));
						Thread.sleep(time);
						if (stringList[is.get()] == "" && stringList[is.get() - 1 ] == "") {
							is.set(0);
							Platform.runLater(()-> 	infoLabel.setText(""));
							break;
						}
						if (tryToRun.get() == true)
							break;
						is.set(is.get() + 1);
						if (is.get() == sizeTS.get())
							is.set(0);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				is.set(0);
				tryToRun.compareAndSet(true, false);
			});
		t.start();
	}
	

	private void setInfoLoading(String string, String end, int time) throws InterruptedException {
		if (isRunning.compareAndSet(true, true)) {
			tryToRun.compareAndSet(false, true);
			is.set(0);
			Platform.runLater(()-> 	infoLabel.setText(""));
		}
		Platform.runLater(()-> 	infoLabel.setText(string));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + "."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + ".."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(string + "..."));
		Thread.sleep(time);
		Platform.runLater(()-> 	infoLabel.setText(end));
	}
	private void noSpamButton() {
		new Thread(() -> {
			loginButton.setDisable(true);
			logoutButton.setDisable(true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			loginButton.setDisable(false);
			logoutButton.setDisable(false);
		}).start();
	}
	
	private void showPing()
	{
		new Thread("Ping Updater"){
			@Override
			public void run() {
				while (true) {
					MinecraftPingReply data;
					try {
						data = new MinecraftPing().getPing(ipField.getText(), Integer.valueOf(portField.getText()));
						Platform.runLater(()-> 	pingLabel.setText(data.getLatency() + "ms " + data.getPlayers().getOnline() + "/" + data.getPlayers().getMax() + " joueurs en ligne"));
						Thread.sleep(100);
					} catch (IOException e1) {} catch (InterruptedException e) {}
				}
			}
		}.start();
	}
}
