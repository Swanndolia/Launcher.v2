package ui.animations;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import javafx.animation.FadeTransition;
import javafx.scene.control.TextField;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class FieldAnim extends TextFieldSkin{
	public FieldAnim(TextField control) {
    	super(control);

    	final FadeTransition fadeIn = new FadeTransition(Duration.millis(250));
    	fadeIn.setNode(control);
    	fadeIn.setToValue(1);
    	control.setOnMouseEntered(e -> fadeIn.playFromStart());

    	final FadeTransition fadeOut = new FadeTransition(Duration.millis(250));
    	fadeOut.setNode(control);
    	fadeOut.setToValue(0.75);
    	control.setOnMouseExited(e -> fadeOut.playFromStart());
    	
    	control.setOpacity(0.75);
    }
}
