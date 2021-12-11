package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import viewer.ViewManager;

public class SpaceRunnerButton extends Button {
	
	public final static String FONTPATH = "assets/unipack_fixed/Font/kenvector_future.ttf";
	private static final int START_POSITION_X = 90;
	private static final int START_POSITION_Y = 60;
	public boolean Pressed;
	
	
	public SpaceRunnerButton(String text,int positionX,int positionY){
		this.Pressed = false;
		this.setText(text);
		this.setMyFont();
		this.setPrefWidth(190);
		this.setPrefHeight(49);
		this.getStyleClass().add("releasedButton");
		this.setLayoutX(START_POSITION_X+positionX);
		this.setLayoutY(START_POSITION_Y+positionY);
	}
	
	public void setMyFont() {
		FileInputStream fontFile;
		try {
			fontFile = new FileInputStream(FONTPATH);
			this.setFont(Font.loadFont(fontFile, 23));
		} catch (FileNotFoundException e) {

			this.setFont(Font.font("Verdana",23));
		}
	}
	
	public void setReleasedStyle() {
		this.getStyleClass().add("releasedButton");
		this.setPrefHeight(49);
		this.setLayoutY(this.getLayoutY()-4);
	}
	
	public void setPressedStyle() {
		this.getStyleClass().add("pressedButton");
		this.setPrefHeight(45);
		this.setLayoutY(this.getLayoutY()+4);
	}
	
	public void initalizeButtonListener(SpaceRunnerSubScene subScene,int i) {
		
		// Mouse Pressed
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				setPressedStyle();
				subScene.EditSubScene(i);
				
				if(event.getButton().equals(MouseButton.PRIMARY) && i != 5) {
					
					if(ViewManager.LastClick==-1) { // hide 
						subScene.translateShow();
						ViewManager.LastClick = i;
					}
					else if(ViewManager.LastClick != i) { // shown & different button
						ViewManager.mainListSubScens.get(ViewManager.LastClick).translateHide();
						subScene.translateShow();
						ViewManager.LastClick = i;
					}
					else {                             // shown & same button
							subScene.translateHide();
							ViewManager.LastClick = -1;
					}
					
					
				}

			
			}
		});
		
		// Mouse Released
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setReleasedStyle();
				}		
			}
			
		});
		
		//Mouse Entered
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				setEffect(new DropShadow());
			}
			
		});
		
		//Mouse Exit
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				setEffect(null);
			}
			
		});
	}
	
	public String ButtonNames(int i) {
		
		switch(i) {
		
		case 0:return "PLAY";
		case 1:return "CREDITS";
		case 2:return "SCORES";
		case 3:return "HELP";
		case 4:return "Exit";
		case 5:return "Start";
		default:return "no button with this name";
		
		}
	}
	

}
