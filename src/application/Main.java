package application;

import java.io.IOException;
import java.net.URL;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
	
    public static Stage stage;
	public static DiscordRichPresence presence = new DiscordRichPresence();
    private static DiscordRPC lib = DiscordRPC.INSTANCE;
    
	@Override
	public void start(Stage primaryStage) throws IOException {
	   	URL url = getClass().getResource("/ui/resources/main.fxml");
	   	FXMLLoader fxmlLoader = new FXMLLoader(url);
	   	AnchorPane root = (AnchorPane) fxmlLoader.load();
	    final Scene scene = new Scene(root, 1300, 700);
	    stage = primaryStage;
	    stage.getIcons().add(new Image("/ui/resources/icon.png"));
	    stage.setScene(scene);

	    scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("azurpixel v4");
        stage.setScene(scene);

        stage.show();
        //mover
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		root.requestFocus();
        		xOffset = event.getSceneX();
        		yOffset = event.getSceneY();
        	}
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		stage.setX(event.getScreenX() - xOffset);
        		stage.setY(event.getScreenY() - yOffset);
        	}
        });
	  }
	
	public static void main(String[] args) throws IOException {
		if (!Controller.dir.exists()) 
			  Controller.dir.mkdir();
		String applicationId = "617320590570815498";
		String steamId = "";
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
		presence.details = "Viens de lancer le launcher";
		presence.state = "Azurpixel / Saber LLC";
		presence.largeImageKey = "icone";
		presence.largeImageText = "azurpixel.net";
		updatePresence();
		launch(args);
	  }
	
	
		public static void updatePresence() {
		    lib.Discord_UpdatePresence(presence);
			Thread t = new Thread(() -> {
				while (!Thread.currentThread().isInterrupted()) {
					lib.Discord_RunCallbacks();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						lib.Discord_Shutdown();
						break;
					}
				}
			}, "RPC-Callback-Handler");
			t.start();
		}
	  
	}