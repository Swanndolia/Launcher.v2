package ui.animations;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class ButtonAnim extends ButtonSkin{
	public ButtonAnim(Button control) {
    	super(control);

    	final FadeTransition fadeIn = new FadeTransition(Duration.millis(200));
    	fadeIn.setNode(control);
    	fadeIn.setToValue(1);
    	control.setOnMouseEntered(e -> fadeIn.playFromStart());
    
    	final FadeTransition fadeOut = new FadeTransition(Duration.millis(200));
    	fadeOut.setNode(control);
    	fadeOut.setToValue(0.75);
    	control.setOnMouseExited(e -> fadeOut.playFromStart());

    	control.setOpacity(0.75);
    }
}
