package pageextractor.fiducials;

public class Fiducial {
	public static final int TYPE_BLUE = 0;
	public static final int TYPE_GREEN = 1;
	public static final int TYPE_RED = 2;
	
	float xPos;
	float yPos;
	int type;
	
	public Fiducial(float x, float y, int type) {
		this.xPos = x;
		this.yPos = y;
		this.type = type;
	}
	
	public void setLocation(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public float getX() {
		return this.xPos;
	}
	
	public float getY() {
		return this.yPos;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
