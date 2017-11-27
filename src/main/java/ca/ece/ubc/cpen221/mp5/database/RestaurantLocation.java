package ca.ece.ubc.cpen221.mp5.database;

public class RestaurantLocation {

	private double x;
	private double y;
	
	public RestaurantLocation(Double x, Double y) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}
	
	public double distanceBetween(RestaurantLocation r) {
		double newX = this.x - r.getX();
		double newY = this.y - r.getY();
		newX = newX * newX;
		newY = newY * newY;
		return Math.sqrt(newX + newY);
	}
	
	public double getX() {	
		return x;
	}
	
	public double getY() {	
		return y;
	}
	
}
