package mainMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;
import viewer.GamViewManager;
import viewer.ViewManager;



public class StartSubScene extends SpaceRunnerSubScene{
	
	private String FONT_PATH = "src/model/assets/unipack_fixed/Font/kenvector_future.ttf";
	private String Label_BackgroundImage = getClass().getResource("Label.png").toExternalForm();
	private String labelName = "Please Choose Your Ship";
	
	private String CircleNotChosen = "/model/assets/spaceshooter/PNG/grey_circle.png";
	private String CircleChosen = "/model/assets/spaceshooter/PNG/CircleChosen.png";
	public boolean[] circles;
	
	private VBox scenePane;
	private SpaceRunnerButton startButton;
	
	private Label label;
	private ArrayList<ImageView> ships;
	
	public String chosenShip;
	public String chosenShipURL;
	public GamViewManager nextScene;
	
	public StartSubScene() {
			
		this.circles = new boolean[SHIP.values().length];	
		this.label = this.createLabel(labelName);
		
		this.scenePane = new VBox();
		this.scenePane.getChildren().add(this.label);
		
		this.scenePane.setSpacing(30);	
		HBox shipChoices = new HBox();
		
		this.ships = new ArrayList<ImageView>();
		
		for(int i=0;i<SHIP.values().length;i++) {	
			ImageView ship = new ImageView(SHIP.values()[i].getUrl());
			ShipChooser x = new ShipChooser(ship,getCircle(this.circles[i]));
			shipActions(ship, i);
			shipChoices.getChildren().add(x);
			this.ships.add(ship);
		}
		
		shipChoices.setAlignment(Pos.CENTER);
		this.scenePane.getChildren().add(shipChoices);
		
		this.addStartButton();	
		this.scenePane.setAlignment(Pos.CENTER);
				
	}
		
	public void updateShips() {
		
		this.scenePane.getChildren().clear();
		
		this.scenePane.getChildren().add(this.label);
		
		this.scenePane.setSpacing(30);	
		HBox shipChoices = new HBox();
		
		for(int i=0;i<SHIP.values().length;i++) {
			ShipChooser x = new ShipChooser(this.ships.get(i),getCircle(this.circles[i]));
			shipChoices.getChildren().add(x);			
		}
		shipChoices.setAlignment(Pos.CENTER);
		this.scenePane.getChildren().add(shipChoices);
		
		this.addStartButton();	
		this.scenePane.setAlignment(Pos.CENTER);	
		
	}
	
	
	public Label createLabel(String label) {
		
		Label x = new Label();
		x.setText(label);
		x.setTextFill(Color.BLACK);
		x.prefHeight(49);
		x.prefWidth(300);
		x.setPadding(new Insets(80,80,80,80));
		x.setAlignment(Pos.CENTER);
		
		try {
			x.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 18));
		} 
		catch (FileNotFoundException e) {
			System.out.println("error");
			x.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		}
		
		BackgroundImage background = new BackgroundImage(
				new Image(Label_BackgroundImage,320,80,false,true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		
		
		x.setBackground(new Background(background));
		//x.setAlignment(Pos.CENTER);
		return x;
		
	}	
	
	public String getCircle(boolean x) {
		
		return x ? CircleChosen : CircleNotChosen;
	}
	
	public void shipActions(ImageView ship,int i) {
				
		ship.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				chosenShip = SHIP.values()[i].getUrl();
				chosenShipURL = SHIP.values()[i].getLife();
				circles = new boolean[circles.length];
				circles[i] = true;
				updateShips();
			}
			
		});
	}
	
	public void addStartButton() {
		
		this.startButton = new SpaceRunnerButton("START",1100,700);
		this.startButton.initalizeButtonListener(this, 5);
		this.scenePane.getChildren().add(this.startButton);
		
		this.startButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(chosenShip != null) {
					ImageView ship = new ImageView(chosenShip);
					nextScene = new GamViewManager(ship, ViewManager.getStage(),chosenShipURL);
				}
				     
			}
			
		});
		
	}
	
	public VBox getScenePane() {
		return this.scenePane;
	}
	
	
}
