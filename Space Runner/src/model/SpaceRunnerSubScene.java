package model;


import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainMenu.StartSubScene;
import viewer.ViewManager;


public class SpaceRunnerSubScene extends SubScene{
	

	private AnchorPane root;
	public Stage mainStage;
	
	public SpaceRunnerSubScene() {
		
		super(new AnchorPane(),600,600);
		this.prefHeight(600);
		this.prefWidth(700);
		BackgroundImage background = new BackgroundImage(
			new Image("model/assets/unipack_fixed/PNG/yellow_panel.png",500,500,true,true),
			BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		this.mainStage = ViewManager.getStage();
		this.root = (AnchorPane)this.getRoot();
		this.root.setBackground(new Background(background));
		
	}
	
	public void translateShow() {
		
		TranslateTransition translateTransition = createTransition();
		translateTransition.setToX(-600);
		translateTransition.play(); 
		
	}
		
	
	public void translateHide() {
		TranslateTransition translateTransition = createTransition();
		translateTransition.setToX(700);
		translateTransition.play();
	}
	
	public TranslateTransition createTransition() {
		TranslateTransition translateTransition = new TranslateTransition(); 
		translateTransition.setDuration(Duration.millis(600)); 
		translateTransition.setNode(this); 
		translateTransition.setCycleCount(1); // number of animation actions	
		//translateTransition.setAutoReverse(false);
		return translateTransition;
	}
	
	public void EditSubScene(int i) {
		if(i == 0) {
			this.root.getChildren().add(new StartSubScene().getScenePane());
		}

	}
	
	public Stage getSceneStage() {
		return this.mainStage;
	}
	
	
	
}
