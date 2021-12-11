package mainMenu;

public enum SHIP {
	
	BLUE("/model/assets/spaceshooter/PNG/playerShip2_blue.png",			
			"/model/assets/spaceshooter/PNG/UI/playerLife1_blue.png"),
	
	GREEN("/model/assets/spaceshooter/PNG/playerShip2_green.png",			
			"/model/assets/spaceshooter/PNG/UI/playerLife1_green.png"),
	ORANGE("/model/assets/spaceshooter/PNG/playerShip2_orange.png",			
			"/model/assets/spaceshooter/PNG/UI/playerLife1_orange.png"),
	RED("/model/assets/spaceshooter/PNG/playerShip2_red.png",			
			"/model/assets/spaceshooter/PNG/UI/playerLife1_red.png");
	
	
	private String urlShip;
	private String life;
	
	private SHIP(String url,String life) {
		this.urlShip = url;
		this.life = life;
	}
	
	public String getUrl() {
		return this.urlShip;
	}
	public String getLife() {
		return this.life;
	}
}
