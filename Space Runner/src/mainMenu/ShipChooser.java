package mainMenu;


import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ShipChooser extends VBox{
	
	private ImageView shipImage;
	private ImageView circleImage;
	private ArrayList<ImageView> ships;
	
	
	public ShipChooser(ImageView ship,String circle ) {
		
		this.shipImage = ship;
		this.shipImage.setScaleX(0.7);
		this.getChildren().add(shipImage);
		
		this.circleImage = new ImageView(circle);	
		this.circleImage.setScaleX(0.7);
		this.circleImage.setScaleY(0.7);
		
		this.setSpacing(7);
		
		this.ships = new ArrayList<ImageView>();
		this.ships.add(shipImage);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(circleImage);
		
	}
	
	
	public ImageView getShip(int i) {
		return this.ships.get(i);
	}
	  
}
