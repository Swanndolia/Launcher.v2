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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
	
    public static Stage stage;
    
	@Override
	public void start(Stage primaryStage) throws IOException {
	   	URL url = getClass().getResource("/ui/resources/main.fxml");
	   	FXMLLoader fxmlLoader = new FXMLLoader(url);
	   	AnchorPane root = (AnchorPane) fxmlLoader.load();
	    final Scene scene = new Scene(root, 1300, 700);
	    stage = primaryStage;
	    stage.setScene(scene);
	    scene.getStylesheets().add("/ui/resources/application.css");
	    scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("azurpixel v4");
        stage.setScene(scene);
        stage.show();
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
        		stage.setX(event.getScreenX() - xOffset);
        		stage.setY(event.getScreenY() - yOffset);
        	}
        });
	  }

	  public static void main(String[] args) {
	    launch(args);
	  }
	}