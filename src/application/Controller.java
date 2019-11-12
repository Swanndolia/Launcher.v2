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
import application.console.Debug;
import fr.theshark34.supdate.BarAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import openauth.AuthenticationException;

public class Controller

{
	public static ConsoleFrame console = new ConsoleFrame();
	public static final String url = "https://azurpixel.net";
	public static GameVersion version = new GameVersion("1.8", null);
	public static GameInfos infos = new GameInfos("AzurPixel v4", version, null);
	public static final File dir = infos.getGameDir();
	public static Saver tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
	private boolean inUpdate;
	public boolean launched = false;
	AtomicInteger sizeTS = new AtomicInteger();
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	private AtomicBoolean tryToRun = new AtomicBoolean(false);
	public static Boolean isLaunched = false;
	private AtomicInteger is = new AtomicInteger(0);
	long progress = 0;
	private File profileFolder = new File(dir + "/profiles");
	private String[] profileList = profileFolder.list();
	private String[] IUsername;
	private String[] IForms;
	private String[] loginE;
	private String[] loginI;
	private String[] loginS;
	private String[] loginRS;
	private String[] logoutS;
	private String[] IMail;
	private String[] ELaunch;
	private String[] langS;
	private String[] EFile;
	private String versionBase;
	private String memoryBase;
	private String graphicsBase;
	private String themeBase;
	private String checkE;
	private String checkS;
	private String started;
	private String starting;
	private String download;
	private String connected;
	private String onlineText;

	@FXML
	private ProgressBar pI;
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
	private Button settingsButton;
	@FXML
	private Button newProfile;
	@FXML
	private TextField portField;
	@FXML
	private Button loginButton;
	@FXML
	private Button keepLogin;
	@FXML
	private Button keepLoginCheck;
	@FXML
	private Button optifine;
	@FXML
	private Button langButton;
	@FXML
	private Button optifineCheck;
	@FXML
	private Button autoConnect;
	@FXML
	private Button autoConnectCheck;
	@FXML
	private Button logoutButton;
	@FXML
	private Button deleteProfile;
	@FXML
	private Button switchAccountButton;
	@FXML
	private Button playButton;
	@FXML
	private Image skin;
	@FXML
	private ImageView skinView;
	@FXML
	private ImageView profileView1;
	@FXML
	private Image profile1;
	@FXML
	private ImageView profileView2;
	@FXML
	private Image profile2;
	@FXML
	private ImageView profileView3;
	@FXML
	private Image profile3;
	@FXML
	private ImageView profileView4;
	@FXML
	private Image profile4;
	@FXML
	private ImageView profileView5;
	@FXML
	private Image profile5;
	@FXML
	private ImageView profileView6;
	@FXML
	private Image profile6;
	@FXML
	public AnchorPane mainPane;
	@FXML
	private AnchorPane settingsPane;
	@FXML
	private AnchorPane userPane;
	@FXML
	private AnchorPane newsPane;

	@FXML
	private void login(ActionEvent event) {
		noSpamButton();
		if (!nameField.getText().matches("^[A-Za-z0-9]{3,20}$") && passField.getText().isEmpty()) {

			setInfoText(IUsername, 4, 1200);
			System.out.println(
					"Login Error, Invalid username, you should use only alphanumeric characters between 3 and 20 lenght");
		} else if (!nameField.getText().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")
				&& !passField.getText().isEmpty()) {
			setInfoText(IForms, 4, 1200);
			System.out.println("Login Error,"
					+ "Invalids forms, type only a username to Login as crack, or use your mojang email and password.");
		} else {
			new Thread(() -> {
				try {
					switchFieldState(true);
					Login.tryLogin(nameField.getText(), passField.getText());
					nameField.setText(connected + Login.name);
					System.out.println("Login Succes, " + "Welcome " + Login.name);
					loadSkin(Login.name);
					if (keepLoginCheck.isVisible())
						tweaks.set("username", Login.authInfos.getUsername());
					switchElementsState(true);
					setInfoText(loginS, 2, 1200);
				} catch (AuthenticationException e) {
					setInfoText(IMail, 4, 1200);
					System.out.println(
							"Login Error, Incorrect mail or password if you don't have mojang account just type a username without pass.");
				}
				switchFieldState(false);
				if (Login.name != null)
					nameField.setDisable(true);
			}).start();
		}
	}

	@FXML
	private void play(ActionEvent event) throws LaunchException, InterruptedException {
		new Thread("Launch procedure") {
			@Override
			public void run() {
				try {
					switchFieldState(true);
					if (tweaks.get("version").equals("1.7"))
						version = new GameVersion(tweaks.get("version"), GameType.V1_7_10);
					else
						version = new GameVersion(tweaks.get("version"), GameType.V1_8_HIGHER);
					if (tweaks.get("version").contains("Forge"))
						infos = new GameInfos("AzurPixel v4", version, new GameTweak[] { GameTweak.FORGE });
					else if (optifine.isVisible())
						infos = new GameInfos("AzurPixel v4", version, new GameTweak[] {});
					else
						infos = new GameInfos("AzurPixel v4", version, new GameTweak[] { GameTweak.OPTIFINE });
					if (autoConnectCheck.isVisible())
						new ConnectToServer(ipField.getText(), portField.getText());
					if (settingsPane.isVisible())
						settingsState();
					try {
						playInfo();
					} catch (IOException e) {
						setInfoText(EFile, 4, 1200);
						System.out.println("File Error, Please report this error, send a screenshot of this\n" + e);
					}
				} catch (InterruptedException e) {
					switchFieldState(false);
					setInfoText(ELaunch, 4, 1200);
					System.out.println("Launch Error, Please report this error, send a screenshot of this\n" + e);
				}
			}
		}.start();
	}

	@FXML
	private void createNewProfile() {
		tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
		int i;
		File profileFolder = new File(infos.getGameDir() + "/profiles");
		for (i = 1; i < profileList.length; i++)
			;
		if (i != 7 && Login.name != null) {
			String name = tweaks.get("username");
			String atoken = tweaks.get("access-token");
			String ctoken = tweaks.get("client-token");
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			new File(dir, "/profiles/" + Login.name).mkdir();
			tweaks.set("lastProfile", Login.name);
			tweaks.set("username", "");
			tweaks.set("access-token", "");
			tweaks.set("client-token", "");
			String newList[] = profileFolder.list();
			profileList = newList;
			tweaks = new Saver(new File(dir, "/profiles/" + name + "/AzurPixel.properties"));
			defSettings();
			tweaks.set("username", name);
			tweaks.set("access-token", atoken);
			tweaks.set("client-token", ctoken);
			loadProfiles(newList);
		}
	}

	private void loadProfiles(String[] profileList) {
		new Thread(() -> {
			try {
				int ip = 0;
				int i = 0;
				while (true) {
					for (i = 0; i < profileList.length; i++)
						;
					if (i == 0)
						break;
					skin = profile1;
					if (++ip == i)
						break;
					profileView1.setVisible(true);
					profile1 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView1.setImage(profile1));
					skin = profile2;
					if (++ip == i)
						break;
					profileView2.setVisible(true);
					profile2 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView2.setImage(profile2));
					;
					skin = profile3;
					if (++ip == i)
						break;
					profileView3.setVisible(true);
					profile3 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView3.setImage(profile3));
					skin = profile4;
					if (++ip == i)
						break;
					profileView4.setVisible(true);
					profile4 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView4.setImage(profile4));
					skin = profile5;
					if (++ip == i)
						break;
					profileView5.setVisible(true);
					profile5 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView5.setImage(profile5));
					skin = profile6;
					if (++ip == i)
						break;
					profileView6.setVisible(true);
					profile6 = new Image(new URL("https://mc-heads.net/head/" + profileList[ip] + "/100").openStream());
					Platform.runLater(() -> profileView6.setImage(profile6));
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}).start();
	}

	public void initialize() {
		if (tweaks.get("lang") != null) {
			if (tweaks.get("lang").equals("en"))
				english();
			else
				french();
		} else
			english();
		Debug.displayDebug();
		if (!System.getProperty("os.arch").contains("64"))
			memorySlider.setMax(1024);
		System.out.println("Last profile: " + tweaks.get("lastProfile", ""));
		tweaks = new Saver(new File(dir, "/profiles/" + tweaks.get("lastProfile", "") + "/AzurPixel.properties"));
		checkRefresh();
		defSettings();
		showPing();
		addListener();
		loadProfiles(profileList);
		if (!tweaks.get("username").equals(""))
			loadSkin(tweaks.get("username"));

	}

	private void refresh(Image image) {
		Platform.runLater(() -> skinView.setImage(image));
		if (tweaks.get("username") == null)
			tweaks.set("username", "");
		if (tweaks.get("username").equals(""))
			keepLogin.setVisible(true);
		else {
			keepLoginCheck.setVisible(true);
			try {
				Login.refresh();
				setInfoText(loginRS, 2, 1200);
				nameField.setText(connected + tweaks.get("username"));
				switchElementsState(true);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	private void checkRefresh() {
		if (tweaks.get("username") == null)
			tweaks.set("username", "");
		if (tweaks.get("username").equals(""))
			keepLogin.setVisible(true);
		else {
			keepLoginCheck.setVisible(true);
			new Thread(() -> {
				try {
					switchFieldState(true);
					setInfoText(loginI, 2, 400);
					Login.refresh();
					nameField.setText(connected + tweaks.get("username"));

					setInfoText(loginRS, 2, 1200);
					System.out.println("Login Success, Welcome back " + tweaks.get("username"));
					switchElementsState(true);
				} catch (AuthenticationException e) {
					setInfoText(loginE, 4, 1200);
					System.out.println("Login Error, Please report this error, send a screenshot of this" + e);
				}
				switchFieldState(false);
				if (Login.name != null)
					nameField.setDisable(true);
			}).start();
		}
	}

	@FXML
	private void logout() {
		setInfoText(logoutS, 2, 1200);
		System.out.println("Logout Succes, " + "See you later");
		Login.authInfos = new AuthInfos("", "", "");
		switchElementsState(false);
		skin = new Image("/ui/resources/skin.png");
		skinView.setImage(skin);
		tweaks.set("username", "");
		tweaks.set("access-token", "");
		nameField.setText("");
		passField.setText("");
		tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
		tweaks.set("lastProfile", "");
	}

	@FXML
	private void switchAccount() {
		setInfoText(logoutS, 2, 1200);
		System.out.println("AccountSwitch" + "Logout Succes, " + "See you later");
		Login.authInfos = new AuthInfos("", "", "");
		switchElementsState(false);
		skin = new Image("/ui/resources/skin.png");
		skinView.setImage(skin);
		nameField.setText("");
		passField.setText("");
		tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
		tweaks.set("lastProfile", "");
	}

	private void switchElementsState(boolean login) {
		playButton.setVisible(login);
		logoutButton.setVisible(login);
		switchAccountButton.setVisible(login);
		loginButton.setVisible(!login);
		nameField.setDisable(login);
		passField.setVisible(!login);
		if (keepLoginCheck.isVisible())
			keepLoginCheck.setVisible(!login);
		keepLogin.setVisible(!login);
		ipField.setVisible(login);
		portField.setVisible(login);
	}

	private void otherAccount() {
		noSpamButton();
		Login.authInfos = new AuthInfos("", "", "");
		switchElementsState(false);
		nameField.setText("");
		passField.setText("");
		defSettings();
	}

	private void playInfo() throws InterruptedException, IOException {
		new Thread(() -> {
			try {
				inUpdate = true;
				Update.update();
				inUpdate = false;
				Launch.launch();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}).start();
		Presets.presetSet();
		setLoading(checkS, checkE, 300, inUpdate);
		setLoading(starting, started, 300, null);
		switchFieldState(false);
		passField.setDisable(true);
		nameField.setDisable(true);
	}

	private void setInfoText(String[] stringList, int size, int time) {
		sizeTS.set(size);
		if (isRunning.get() == true) {
			tryToRun.compareAndSet(false, true);
			is.set(0);
			Platform.runLater(() -> infoLabel.setText(""));
		}
		Thread t = new Thread(() -> {
			int count = 0;
			isRunning.set(true);
			try {
				while (count < 3) {
					Platform.runLater(() -> infoLabel.setText(stringList[is.get()]));
					Thread.sleep(time);
					if (tryToRun.get() == true)
						break;
					is.set(is.get() + 1);
					if (is.get() == sizeTS.get()) {
						is.set(0);
						count++;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			Platform.runLater(() -> infoLabel.setText(""));
			tryToRun.compareAndSet(true, false);
		});
		t.start();
	}

	private void setLoading(String string, String end, int time, Object condition) throws InterruptedException {
		if (isRunning.compareAndSet(true, true)) {
			tryToRun.compareAndSet(false, true);
			is.set(0);
			Platform.runLater(() -> infoLabel.setText(""));
		}
		pI.setVisible(true);
		while (inUpdate) {
			if (BarAPI.getNumberOfTotalBytesToDownload() != 0) {
				progress = 100 - ((BarAPI.getNumberOfFileToDownload() - BarAPI.getNumberOfDownloadedFiles()) * 100
						/ BarAPI.getNumberOfFileToDownload());
				new Thread(() -> {
					Platform.runLater(() -> infoLabel.setText(download + progress + '%'));
					Platform.runLater(() -> pI.setProgress(progress / 100F));
				}).start();
			}
			Platform.runLater(() -> infoLabel.setText(string));
			Thread.sleep(100);
		}
		BarAPI.setNumberOfDownloadedFiles(0);
		progress = 0;
		Platform.runLater(() -> infoLabel.setText(end));
		pI.setVisible(false);
		tryToRun.compareAndSet(true, false);
	}

	private void addListener() {
		versionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

			versionLabel.setText(
					versionBase + Double.toString(newValue.intValue()).replaceFirst(".0$", "").replace("1$", ""));
			tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", ""));
			if (optifine.isVisible())
				tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", "") + "old");
		});

		memorySlider.valueProperty().addListener((observable, oldValue, newValue) -> {

			memoryLabel.setText(memoryBase + Double.toString(newValue.intValue()).replaceAll(".0$", "") + " mb");
			tweaks.set("memory", memoryLabel.getText().replaceAll("[^0-9.]?", ""));
		});

		graphicsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() == 0)
				graphicsLabel.setText(graphicsBase + "Potatoe");
			else if (newValue.intValue() > 0 && newValue.intValue() < 15)
				graphicsLabel.setText(graphicsBase + "Very Low");
			else if (newValue.intValue() >= 15 && newValue.intValue() < 30)
				graphicsLabel.setText(graphicsBase + "Low");
			else if (newValue.intValue() >= 30 && newValue.intValue() < 50)
				graphicsLabel.setText(graphicsBase + "Medium");
			else if (newValue.intValue() >= 50 && newValue.intValue() < 65)
				graphicsLabel.setText(graphicsBase + "High");
			else if (newValue.intValue() >= 65 && newValue.intValue() < 80)
				graphicsLabel.setText(graphicsBase + "Very High");
			else if (newValue.intValue() >= 80 && newValue.intValue() < 95)
				graphicsLabel.setText(graphicsBase + "Ultra");
			else
				graphicsLabel.setText(graphicsBase + "Custom");
			tweaks.set("graphics",
					graphicsLabel.getText().replaceAll("Graphics\\: ", "").replaceAll("Graphiques\\: ", ""));
		});

		themeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			mainPane.getStylesheets().clear();
			if (newValue.intValue() >= 0 && newValue.intValue() < 20)
				tweaks.set("theme", "ClassyDark");
			else if (newValue.intValue() >= 20 && newValue.intValue() < 40)
				tweaks.set("theme", "HappyColor");
			else if (newValue.intValue() >= 40 && newValue.intValue() < 60)
				tweaks.set("theme", "SoftBlue");
			else if (newValue.intValue() >= 60 && newValue.intValue() < 80)
				tweaks.set("theme", "PinkyPromise");
			else
				tweaks.set("theme", "SweetWhite");
			themeLabel.setText(themeBase + tweaks.get("theme"));
			mainPane.getStylesheets().add("/ui/resources/" + tweaks.get("theme") + ".css");
		});

		mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					if (passField.isVisible()) {
						login(null);
					} else
						try {
							play(null);
						} catch (LaunchException | InterruptedException e) {
							setInfoText(ELaunch, 4, 1200);
							System.out.println("Launch Error, Please report this error, send a screenshot of this" + e);
						}
				} else if (ke.getCode() == KeyCode.ESCAPE)
					console.setVisible(true);
			}
		});

		skinView.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			createNewProfile();
		});

		profileView1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[1]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[1]);
			nameField.setText(profileList[1]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[1] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile1);
		});

		profileView2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[2]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[2]);
			nameField.setText(profileList[2]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[2] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile2);
		});

		profileView3.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[3]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[3]);
			nameField.setText(profileList[3]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[3] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile3);
		});

		profileView4.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[4]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[4]);
			nameField.setText(profileList[4]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[4] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile4);
		});

		profileView5.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[5]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[5]);
			nameField.setText(profileList[5]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[5] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile5);
		});

		profileView6.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			System.out.println("Loading profile " + profileList[6]);
			tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
			tweaks.set("lastProfile", profileList[6]);
			nameField.setText(profileList[6]);
			tweaks = new Saver(new File(dir, "/profiles/" + profileList[6] + "/AzurPixel.properties"));
			otherAccount();
			refresh(profile6);
		});
	}

	private void defSettings() {
		versionLabel.setText(versionBase.replaceFirst("1.", "") + tweaks.get("version", "1.8"));
		memoryLabel.setText(memoryBase + tweaks.get("memory", "1024") + " mb");
		graphicsLabel.setText(graphicsBase + tweaks.get("graphics", "Medium"));
		themeLabel.setText(themeBase + tweaks.get("theme", "ClassyDark"));
		tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", ""));
		tweaks.set("memory", memoryLabel.getText().replaceAll("[^0-9.]?", ""));
		tweaks.set("graphics", graphicsLabel.getText().replaceAll("Graphics\\: ", "").replaceAll("Graphiques\\: ", ""));
		tweaks.set("theme", themeLabel.getText().replaceAll("Theme\\: ", "").replaceAll("Thème\\: ", ""));
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
		else if (tweaks.get("theme").equals("SoftBlue"))
			themeSlider.valueProperty().set(50);
		else if (tweaks.get("theme").equals("PinkyPromise"))
			themeSlider.valueProperty().set(70);
		else
			themeSlider.valueProperty().set(100);
	}

	@FXML
	private void appMinimize() {
		Main.stage.setIconified(true);
	}

	@FXML
	private void settingsState() {
		if (!settingsPane.isVisible()) {
			settingsPane.setVisible(true);
			userPane.setVisible(false);
		} else {
			settingsPane.setVisible(false);
			userPane.setVisible(true);
		}
	}

	@FXML
	private void keepLoginState(ActionEvent event) {
		if (keepLoginCheck.isVisible()) {
			keepLoginCheck.setVisible(false);
			keepLogin.setVisible(true);
		} else {
			keepLoginCheck.setVisible(true);
			keepLogin.setVisible(false);
		}
	}

	@FXML
	private void optifineState(ActionEvent event) {
		if (optifineCheck.isVisible()) {
			optifineCheck.setVisible(false);
			optifine.setVisible(true);
			tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", "") + "old");
		} else {
			tweaks.set("version", versionLabel.getText().replaceAll("[^0-9.]?", ""));
			optifineCheck.setVisible(true);
			optifine.setVisible(false);
		}
	}

	@FXML
	private void autoConnectState(ActionEvent event) {
		if (autoConnectCheck.isVisible()) {
			autoConnectCheck.setVisible(false);
			autoConnect.setVisible(true);
		} else {
			autoConnectCheck.setVisible(true);
			autoConnect.setVisible(false);
		}
	}

	private void loadSkin(String name) {
		new Thread(() -> {
			try {
				skin = new Image(new URL("https://mc-heads.net/head/" + name + "/100").openStream());
				Platform.runLater(() -> skinView.setImage(skin));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to load skin");
			}
		}).start();
	}

	private void switchFieldState(boolean disable) {
		langButton.setDisable(disable);
		loginButton.setDisable(disable);
		playButton.setDisable(disable);
		passField.setDisable(disable);
		nameField.setDisable(disable);
		keepLoginCheck.setDisable(disable);
		settingsButton.setDisable(disable);
		logoutButton.setDisable(disable);
		switchAccountButton.setDisable(disable);
		profileView1.setDisable(disable);
		profileView2.setDisable(disable);
		profileView3.setDisable(disable);
		profileView4.setDisable(disable);
		profileView5.setDisable(disable);
		profileView6.setDisable(disable);
	}

	private void noSpamButton() {
		new Thread(() -> {
			langButton.setDisable(true);
			loginButton.setDisable(true);
			logoutButton.setDisable(true);
			profileView1.setDisable(true);
			profileView2.setDisable(true);
			profileView3.setDisable(true);
			profileView4.setDisable(true);
			profileView5.setDisable(true);
			profileView6.setDisable(true);
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			langButton.setDisable(false);
			loginButton.setDisable(false);
			logoutButton.setDisable(false);
			profileView1.setDisable(false);
			profileView2.setDisable(false);
			profileView3.setDisable(false);
			profileView4.setDisable(false);
			profileView5.setDisable(false);
			profileView6.setDisable(false);
		}).start();
	}

	private void showPing() {
		new Thread("Ping Updater") {
			@Override
			public void run() {
				while (true) {
					MinecraftPingReply data;
					try {
						if (isLaunched)
							Thread.sleep(2500);
						else {
							data = new MinecraftPing().getPing(ipField.getText(), Integer.valueOf(portField.getText()));
							Platform.runLater(() -> pingLabel.setText(data.getLatency() + "ms "
									+ data.getPlayers().getOnline() + "/" + data.getPlayers().getMax() + onlineText));
							Thread.sleep(100);
						}
					} catch (IOException e1) {
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}

	@FXML
	private void appExit() {
		System.exit(0);
	}

	@FXML
	public void switchLang() {
		noSpamButton();
		Saver backup = tweaks;
		tweaks = new Saver(new File(dir, "/profiles/AzurPixel.properties"));
		if (tweaks.get("lang").equals("en"))
			french();
		else
			english();
		tweaks = backup;
		setInfoText(langS, 1, 1800);
		versionLabel.setText(versionBase.replaceFirst("1.", "") + tweaks.get("version", "1.8"));
		memoryLabel.setText(memoryBase + tweaks.get("memory", "1024") + " mb");
		graphicsLabel.setText(graphicsBase + tweaks.get("graphics", "Medium"));
		themeLabel.setText(themeBase + tweaks.get("theme", "ClassyDark"));
		mainPane.requestFocus();
	}

	private void french() {
		tweaks.set("lang", "fr");
		loginButton.setText("Se Connecter");
		playButton.setText("Lancer le Jeu");
		keepLogin.setText("Rester Co.");
		keepLoginCheck.setText("Rester Co.");
		versionBase = "Version: 1.";
		graphicsBase = "Graphiques: ";
		memoryBase = "Mémoire: ";
		themeBase = "Thème: ";
		autoConnect.setText("Connection Auto");
		autoConnectCheck.setText("Connection Auto");
		optifine.setText("Activer Optifine");
		optifineCheck.setText("Activer Optifine");
		checkS = "Vérification des fichiers en cours";
		checkE = "Vérification des fichiers terminée";
		starting = "Lancement du jeu en cours";
		started = "Lancement terminé, amuse toi bien !";
		download = "Téléchargemetn en cours: ";
		connected = "Connecté en tant que ";
		onlineText = " joueurs connectés";
		nameField.setText(nameField.getText().replace("Login as", "Connecté en tant que"));

		String[] IUsername = { "Erreur de connexion,", "Nom d'utilisateur invalide",
				"utilisez uniquement entre 3 et 20", "caractères alpha numériques" };
		String[] IForms = { "Erreur de connexion,", "Champs invalides,", "en crack ne remplissez que le premier champ,",
				"en premium, utilisez vos identifiant mojang." };
		String[] IMail = { "Erreur de connexion,", "Email ou mot de passe incorect,",
				"si vous n'avez pas de compte premium", "ne remplissez que le premier champ avec un pseudo." };
		String[] ELaunch = { "Erreur de lancement,", "Merci de rapporter cette erreur,",
				"appuyez sur échap pour ouvrir la console", "puis envoyez nous une capture d'écran" };
		String[] loginE = { "Erreur de connexion,", "Merci de rapporter cette erreur,",
				"appuyez sur échap pour ouvrir la console", "puis envoyez nous une capture d'écran" };
		String[] EFile = { "Erreur de fichiers,", "Merci de rapporter cette erreur,", "appuyez sur échap pour ouvrir la console",
		"puis envoyez nous une capture d'écran" };
		String[] loginI = { "Connexion en cours,", "Veuillez patienter" };
		String[] loginS = { "Connexion réussie", "Bienvenue " };
		String[] loginRS = { "Connexion réussie", "Bienvenue " };
		String[] logoutS = { "Déconnexion réussie", "A bientôt !" };
		String[] langS = { "Langue modifiée en Français" };
		this.IUsername = IUsername;
		this.IForms = IForms;
		this.IMail = IMail;
		this.ELaunch = ELaunch;
		this.loginE = loginE;
		this.loginI = loginI;
		this.loginS = loginS;
		this.logoutS = logoutS;
		this.loginRS = loginRS;
		this.langS = langS;
		this.EFile = EFile;
	}

	private void english() {
		tweaks.set("lang", "en");
		loginButton.setText("Click to Login");
		playButton.setText("Click to Play");
		keepLogin.setText("Stay Login");
		keepLoginCheck.setText("Stay Login");
		versionBase = "Version: 1.";
		graphicsBase = "Graphics: ";
		memoryBase = "Memory: ";
		themeBase = "Theme: ";
		autoConnect.setText("Auto Connect");
		autoConnectCheck.setText("Auto Connect");
		optifine.setText("Enable Optifine");
		optifineCheck.setText("Enable Optifine");
		checkS = "File check in progress";
		checkE = "File check finished";
		starting = "Starting the game";
		started = "Here you go, Have fun !";
		download = "Download in progress: ";
		connected = "Login as ";
		onlineText = " players online";
		nameField.setText(nameField.getText().replace("Connecté en tant que", "Login as"));

		String[] IUsername = { "Login Error,", "Invalid username", "you should use only alphanumeric",
				"between 3 and 20 characters lenght" };
		String[] IForms = { "Login Error,", "Invalids forms,", "type only a username to Login as crack,",
				"or use your mojang email and password." };
		String[] IMail = { "Login Error,", "Incorrect mail or password,", "if you don't have mojang account,",
				"just type a username without pass." };
		String[] ELaunch = { "Launch Error,", "Please report this error,", "press escape to open the console",
				"then send a screenshot of it" };
		String[] loginE = { "Login Error,", "Please report this error,", "press escape to open the console",
				"then send a screenshot of it" };
		String[] EFile = { "File Error,", "Please report this error,", "press escape to open the console",
				"then send a screenshot of it" };
		String[] loginI = { "Loging in,", "Please wait" };
		String[] loginS = { "Login Success,", "Welcome" };
		String[] loginRS = { "Login Success,", "Welcome" };
		String[] logoutS = { "Logout Success", "See you later !" };
		String[] langS = { "Language set to English" };
		this.IUsername = IUsername;
		this.IForms = IForms;
		this.IMail = IMail;
		this.ELaunch = ELaunch;
		this.loginE = loginE;
		this.loginI = loginI;
		this.loginS = loginS;
		this.loginRS = loginRS;
		this.logoutS = logoutS;
		this.langS = langS;
		this.EFile = EFile;
	}
}
