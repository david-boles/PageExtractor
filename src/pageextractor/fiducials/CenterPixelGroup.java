package pageextractor.fiducials;

import java.util.ArrayList;

public class CenterPixelGroup {
	boolean enabled = true;
	ArrayList<Pixel> members = new ArrayList<Pixel>();
	float rB;
	float gB;
	float bB;
	float gPosX;
	float gPosY;
	float edgeDistanceDeviation;
	
	public void merge(CenterPixelGroup c) {
		this.members.addAll(c.members);
		c.members.clear();
		c.enabled = false;
	}
	
	public void addPx(Pixel p) {
		this.members.add(p);
	}
	
	public void updateGroupStats() {
		float rTotal = 0;
		float gTotal = 0;
		float bTotal = 0;
		int cTotal = 0;
		
		float xTotal = 0;
		float yTotal = 0;
		
		float bDMax = 0;
		float bDMin = Float.MAX_VALUE;
		
		for(int i = 0; i < this.members.size(); i++) {
			if(members.get(i).isBound) {
				rTotal += members.get(i).rB;
				gTotal += members.get(i).gB;
				bTotal += members.get(i).bB;
				cTotal++;
				
				float dis = (float) Math.sqrt((members.get(i).p.x^2)+(members.get(i).p.y^2));
				if(dis > bDMax) bDMax = dis;
				if(dis < bDMin) bDMin = dis;
			}
			xTotal += members.get(i).p.x;
			yTotal += members.get(i).p.y;
			
		}
		this.rB = rTotal/cTotal;
		this.gB = gTotal/cTotal;
		this.bB = bTotal/cTotal;
		this.gPosX = xTotal/this.members.size();
		this.gPosY = yTotal/this.members.size();
		this.edgeDistanceDeviation = bDMin/bDMax;
	}
	
}
