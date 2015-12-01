package pageextractor.fiducials;

public class Fiducial {
	public static final int TYPE_BLUE = 0;
	public static final int TYPE_GREEN = 1;
	public static final int TYPE_RED = 2;
	
	float xPos;
	float yPos;
	int type;
	float colorRatio = 1f;
	
	public Fiducial(float x, float y, int type) {
		this.xPos = x;
		this.yPos = y;
		this.type = type;
	}
	
	public Fiducial(float x, float y, int type, float coloRatio) {
		this.xPos = x;
		this.yPos = y;
		this.type = type;
		this.colorRatio = coloRatio;
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
	
	public void setColorRatio(float colorRatio) {
		this.colorRatio = colorRatio;
	}
	
	public float getColorRatio() {
		return this.colorRatio;
	}
}
