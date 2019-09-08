package application;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;public final class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
	
	  @Override
	public void start(final Stage primaryStage) {
		try {
	    	URL url = getClass().getResource("/ui/main.fxml");
	    	FXMLLoader fxmlLoader = new FXMLLoader(url);
	    	AnchorPane root = (AnchorPane) fxmlLoader.load();
	        final Scene scene = new Scene(root, 1300, 700);
	        final Rectangle rect = new Rectangle(1300,700);
	        primaryStage.setScene(scene);
	        scene.getStylesheets().add("/ui/application.css");
		    rect.setArcHeight(60.0);
	        rect.setArcWidth(60.0);
	        root.setClip(rect);
	        scene.setFill(Color.TRANSPARENT);

	        primaryStage.initStyle(StageStyle.TRANSPARENT);
	        primaryStage.setTitle("azurpixel v4");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        //mover
	        root.setOnMousePressed(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent event) {
	        		xOffset = event.getSceneX();
	        		yOffset = event.getSceneY();
	        	}
	        });
	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent event) {
	        		primaryStage.setX(event.getScreenX() - xOffset);
	        		primaryStage.setY(event.getScreenY() - yOffset);
	        	}
	        });
	    } catch (IOException ex) {}
	    

	  }

	  public static void main(String[] args) {
	    launch(args);
	  }
	}