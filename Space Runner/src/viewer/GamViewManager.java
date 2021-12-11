package viewer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GamViewManager  {
	
	private static final int GAME_WIDTH = 1000;
	private static final int GAME_HEIGHT = 750;
	private final String gameBackground = "/model/assets/spaceshooter/Backgrounds/purple.png";
	private final String starURL = "/model/assets/spaceshooter/PNG/power-ups/star_gold.png";
	private final String label_background = "/model/assets/unipack_fixed/PNG/grey_button14.png";
	private String FONT_PATH = "model/assets/unipack_fixed/Font/kenvector_future.ttf";
	
	ImageView lastCollided;
	ArrayList<ImageView> lifeStars;
	int playerLifes = 3;
	Label PlayerPoints;
	int points = 0;
	
	AnimationTimer gameTimer;
	boolean isLeftKeyPressed;
	boolean isRightKeyPressed;
	int angle;
	
	List<Integer> positions;
	String choosenShipColor;
	
	GridPane gridPane1;
	GridPane gridPane2;
	GridPane gridPane3;
	
	Stage gameStage;
	Scene gameScene;
	Stage menuStage;
	
	AnchorPane gamePane;		
	ImageView playingShip;
	
	String meteor = "/model/assets/spaceshooter/PNG/Meteors/";
	
	String[] gameMeteorsUrls = {meteor+"meteorBrown_med1.png",
								 meteor+"meteorBrown_big3.png",
								 meteor+"meteorBrown_big4.png",
								 meteor+"meteorGrey_med1.png",
								 meteor+"meteorGrey_med2.png",
								 meteor+"meteorGrey_big3.png",
								 meteor+"meteorGrey_big4.png",
								 meteor+"meteorGrey_big2.png",
								 meteor+"meteorGrey_big1.png"};
	
	
	ArrayList<ImageView> gameMeteors;
	ArrayList<ImageView> gameStars;
	String chosenShipURL;
	
	public GamViewManager(ImageView ship,Stage stage,String chosenship) {
		
		this.lastCollided = new ImageView();
		this.positions = new ArrayList<Integer>();
		this.chosenShipURL = chosenship;
		initializeStage();
		createGameBackground();
		createGame(ship,stage);
		initiateMeteors();
		initiateStars();
		initiateLifeShips(this.playerLifes);
		createLabel();
		keyListeners();
		createGameLoop();
		
	}
	
	
	
	private void initializeStage() {
		
		this.gameStage = new Stage();
		this.gamePane = new AnchorPane();
		this.gameScene = new Scene(this.gamePane,GAME_WIDTH,GAME_HEIGHT);
		this.gameStage.setScene(gameScene);	
		
		
	}
	
	private void keyListeners() {
		
		this.gameScene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
				
			}
			
		});
		
		this.gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
				
			}
			
		});
	}
	
	public void createGame(ImageView ship,Stage stage) {
		this.menuStage = stage;
		this.menuStage.hide();
		this.gameStage.show();
		this.playingShip = ship;
		this.gamePane.getChildren().add(this.playingShip);
		this.playingShip.setLayoutX(GAME_WIDTH/2-50);
		this.playingShip.setLayoutY(GAME_HEIGHT-90);
	}
	
	public void createGameLoop() {
		
		this.gameTimer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				
				moveShip();	
				createMovingBackgrounds();
				moveMeteors();
				moveStars();
				updateGameStatus();
				checkExceedingGameHeight(gameMeteors,gameMeteorsUrls.length-5,"meteors");
				checkExceedingGameHeight(gameStars,7,"stars");
				
			}
			
		};
		
		gameTimer.start();
	}
	
	public void moveShip() {
		
		if( isLeftKeyPressed && !isRightKeyPressed ) {
			
			if(this.angle>-30) {
				this.angle-=5;
			}
			this.playingShip.setRotate(this.angle); // rotation
			
			if(this.playingShip.getLayoutX()>5) {   // translation
				this.playingShip.setLayoutX(this.playingShip.getLayoutX()-5);
			}
			
		}
		if( !isLeftKeyPressed && isRightKeyPressed ) {
			
			if(this.angle<30) {
				this.angle+=5;
			}
			this.playingShip.setRotate(this.angle); // rotation
			
			if(this.playingShip.getLayoutX()<GAME_WIDTH-100) {   // translation
				this.playingShip.setLayoutX(this.playingShip.getLayoutX()+5);
			}
			
		}
		if( !isLeftKeyPressed && !isRightKeyPressed ) {
			
			if(this.angle>0) {
				this.angle-=1;
			}
			else {
				this.angle+=1;
			}
			
			this.playingShip.setRotate(this.angle); // rotation

			
		}
		if( isLeftKeyPressed && isRightKeyPressed ) {
			
			if(this.angle>0) {
				this.angle-=5;
			}
			else {
				this.angle+=5;
			}
			
			this.playingShip.setRotate(this.angle); // rotation
			
			
		}
	}
	
	public void createGameBackground() {
		
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
	
		for(int i=0;i<16;i++) {
			ImageView background1 = new ImageView(gameBackground);
			ImageView background2 = new ImageView(gameBackground);
			
			GridPane.setConstraints(background1, i%4,i/4);
			GridPane.setConstraints(background2, i%4,i/4);
			
			gridPane1.getChildren().add(background1);
			gridPane2.getChildren().add(background2);
		}
		
		this.gamePane.getChildren().addAll(gridPane1,gridPane2);
		gridPane2.setLayoutY(-1000);
	}

	
	
	public void createMovingBackgrounds() {
		gridPane1.setLayoutY(gridPane1.getLayoutY()+2);
		gridPane2.setLayoutY(gridPane2.getLayoutY()+2);
		
		if(gridPane1.getLayoutY()>1000) {
			gridPane1.setLayoutY(0);
			gridPane2.setLayoutY(-1000);
		}


	}
	
	public void initiateLifeShips(int k) {
		
		this.lifeStars = new ArrayList<ImageView>();
		
		for(int i=0;i<k;i++) {
			ImageView ship = new ImageView(chosenShipURL);
			this.lifeStars.add(ship);
			ship.setLayoutX(800 + i*50);
			ship.setLayoutY(40);
			this.gamePane.getChildren().add(ship);
		}
		
	}
	
	public void createLabel() {
		
		this.PlayerPoints = new Label();
		this.PlayerPoints.setText(String.format("points: %x",this.points));
		this.PlayerPoints.setLayoutX(GAME_WIDTH-200);
		this.PlayerPoints.setLayoutY(7);
		this.PlayerPoints.setPrefHeight(30);
		this.PlayerPoints.setPrefWidth(230);
		this.PlayerPoints.setTextFill(Color.BLACK);
		//this.PlayerPoints.setPadding(new Insets(80,80,80,80));
		
		try {
			this.PlayerPoints.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 18));
		} 
		catch (FileNotFoundException e) {
			this.PlayerPoints.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		}
		
		
		Image back = new Image(label_background);
		BackgroundImage background_image = new BackgroundImage(back,BackgroundRepeat.NO_REPEAT,
									 BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		
		this.PlayerPoints.setBackground(new Background(background_image));
		this.gamePane.getChildren().add(PlayerPoints);
	}
	
	public void updateLabe() {
		this.PlayerPoints.setText(String.format("points: %x",this.points));
	}
	
	public void updateGameStatus() {
		
		// Stars
		for(int i=0;i<this.gameStars.size();i++) {
			ImageView star = this.gameStars.get(i);
			if(collideDetection(star)) {
				this.points++;
				updateLabe();
				this.gameStars.get(i).setLayoutY(-400);
			}
		}
		
		// Meteors
		for(int i=0;i<this.gameMeteors.size();i++) {
			ImageView meteor = this.gameMeteors.get(i);
			if(collideDetection(meteor)) {
				decreaseLife();
				this.lastCollided = meteor;
				this.gameMeteors.get(i).setLayoutY(-300);
			}
		}		
		
	}
	
	public void decreaseLife() {
		
		this.gamePane.getChildren().removeAll(this.lifeStars);
		--this.playerLifes;
		initiateLifeShips(playerLifes);
		
		if(this.playerLifes<0) {
			
			this.gameTimer.stop();
			this.gameStage.close();		
			this.menuStage.show();	
			
		}		
		    
	}
	
	public boolean collideDetection(ImageView body) {
		
		return body.getBoundsInParent().intersects(this.playingShip.getBoundsInParent()) && this.lastCollided !=body;
		
	}
	
	
	
	public void initiateMeteors() {
		
		this.gameMeteors = new ArrayList<ImageView>();
		
		for(int i=0;i<gameMeteorsUrls.length-5;i++) {
			
			int x = (int)(Math.random() * gameMeteorsUrls.length);
					
			ImageView Meteor = new ImageView(this.gameMeteorsUrls[x]);
			
			Meteor.setLayoutX(GAME_WIDTH *(i/4)+(int)(Math.random()*(GAME_WIDTH)));
			Meteor.setLayoutY(-100 * Math.random());
			
			this.gameMeteors.add(Meteor);
		}
		
		this.gamePane.getChildren().addAll(this.gameMeteors);
	}
	
	public void initiateStars() {
		
		this.gameStars = new ArrayList<ImageView>();
		
		for(int i=0;i<10;i++) {
					
			ImageView star = new ImageView(this.starURL);
			
			star.setLayoutX(GAME_WIDTH *(i/10)+(int)(Math.random()*(GAME_WIDTH)));
			star.setLayoutY(-150 * Math.random());
			
			this.gameStars.add(star);
			
		}
		
		this.gamePane.getChildren().addAll(this.gameStars);
	}
	
	
	public void moveMeteors() {
		
		for(int i=0;i<this.gameMeteorsUrls.length-5;i++) {
			
			double y = this.gameMeteors.get(i).getLayoutY();
			double angle = this.gameMeteors.get(i).getRotate();
			
			this.gameMeteors.get(i).setLayoutY(y + 7);
			this.gameMeteors.get(i).setRotate(angle + 3);
			
		}
		
	}
	
	public void moveStars() {
		
		for(int i=0;i<10;i++) {
			
			double y = this.gameStars.get(i).getLayoutY();
			double angle = this.gameStars.get(i).getRotate();
			
			this.gameStars.get(i).setLayoutY(y + 7);
			this.gameStars.get(i).setRotate(angle + 3);
			
		}
		
	}
	
	public void checkExceedingGameHeight(ArrayList<ImageView> stuff,int length,String type) {
		
		boolean flag = true;

		for(int i=0;i<length;i++) {
			
			if(stuff.get(i).getLayoutY()>1000) {			
				flag = false;
				break;
		}
			
		}
		
		if(!flag) {
			this.gamePane.getChildren().removeAll(stuff);
			if(type == "meteors") {
				initiateMeteors();
			}
			else {
				initiateStars();
			}
		}
	}
}
