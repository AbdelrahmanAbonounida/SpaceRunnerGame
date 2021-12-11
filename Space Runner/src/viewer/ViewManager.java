package viewer;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

public class ViewManager {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 750;
	public static int LastClick = -1;
	
	private Scene mainScene;
	private AnchorPane mainPane;
	private static Stage mainStage;
	private  ArrayList<SpaceRunnerButton> mainListButtons;
	public static ArrayList<SpaceRunnerSubScene> mainListSubScens;
	
	
	public ViewManager() {
		
		this.mainPane = new AnchorPane();
		this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);	
		
		this.mainListButtons = new ArrayList<SpaceRunnerButton>();
		ViewManager.mainListSubScens = new ArrayList<SpaceRunnerSubScene>();
		
		
		this.initiateSubScenes();
		this.createBackground();
		this.createLogo();
		this.initiateStartMenu();
		this.mainScene.getStylesheets().add("/model/StyleSheet.css");
		
	}
	


	public static Stage getStage() {
			
		return ViewManager.mainStage;

	}

	public SpaceRunnerButton createButton(String text,int positionX,int positionY) {
		SpaceRunnerButton button = new SpaceRunnerButton(text,positionX,positionY);
		this.mainPane.getChildren().add(button);
		return button;
	}
	
	private void createBackground() {
		this.mainPane.setId("pane");
		
	    /*
		Image back = new Image("model/assets/spaceshooter/Backgrounds/blue.png");
		BackgroundImage background_image = new BackgroundImage(back,BackgroundRepeat.NO_REPEAT,
									 BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		this.mainPane.setBackground(new Background(background_image));
		*/

		// I did it with CSS
		
	}
	
	private void createLogo() {
		
		Text logo = new Text("SPACE RUNNER");
		logo.setX(450);
		logo.setY(100);
		
		logo.setFont(Font.font("fantasy",FontWeight.BOLD,55));
		logo.setFill(Color.YELLOW);
		logo.setStroke(Color.BLACK);
		logo.setStrokeWidth(5);
		
		this.mainPane.getChildren().add(logo);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());		
			}			
		});		
		logo.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);				
			}
			
		});
	}
	
	public void initiateStartMenu() {
		
		int x = 110;
		
		mainListButtons.add(this.createButton("PLAY", 0, x));
		mainListButtons.add(this.createButton("SCORES", 0, x+100));
		mainListButtons.add(this.createButton("HELP", 0, x+200));
		mainListButtons.add(this.createButton("CREDITS", 0, x+300));
		mainListButtons.add(this.createButton("EXIT", 0, x+400));
		
		int i = 0;
		for(SpaceRunnerButton b:mainListButtons) {
			b.initalizeButtonListener(ViewManager.mainListSubScens.get(i),i++);
		}
		
	}
	
	public void createSubScene() {
		
		SpaceRunnerSubScene x = new SpaceRunnerSubScene();
		x.setLayoutX(1020);
		x.setLayoutY(160);
		ViewManager.mainListSubScens.add(x);
		this.mainPane.getChildren().add(x);
		
	}
	
	public void initiateSubScenes() {
		//0>> play, 1>> Scores, 2>> Help, 3>> Credits, 4>> Exit 
		
		for(int i=0;i<5;i++) {
			createSubScene();
		}
		
	}
	
	public void hideStage() {
		mainStage.hide();	
	}
	
	
	
}


