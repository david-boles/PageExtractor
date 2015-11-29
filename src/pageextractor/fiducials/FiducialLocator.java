package pageextractor.fiducials;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IRGBImage;

public class FiducialLocator {
	
	public static Fiducial[] locateFiducials(IRGBImage iI) {
		return locateFiducials(iI, new Color(75, 75, 75));
	}
	
	public static Fiducial[] locateFiducials(IRGBImage iI, Color blackRef) {
		FloatingImage i = new FloatingImage(iI);
		i = applyBlur(i, 2);
		ArrayList<CenterPixelGroup> Gs = group(getAreBlack(i, blackRef), i);
		printTest(Gs);
		
		return null;
	}
	
	static ArrayList<CenterPixelGroup> group(boolean[][] b, FloatingImage i) {
		ArrayList<CenterPixelGroup> Gs = new ArrayList<CenterPixelGroup>();
		Pixel[][] p = new Pixel[b.length][b[0].length];
		
		for(int x = 0; x < b.length; x++) {
			for(int y = 0; y < b[0].length; y++) {
				Pixel px = new Pixel();
				p[x][y] = px;
				px.p = new Point(x, y);
				
				px.isBlack = b[x][y];
				if(px.isBlack) {
					try{if(b[x+1][y] && px.g == null && p[x+1][y].g != null) {
						System.out.println("To the right, to the right :)");
						px.g = p[x+1][y].g;
						px.g.addPx(px);
					}}catch (Exception e){}
					
					try{if(b[x-1][y] && px.g == null && p[x-1][y].g != null) {
						px.g = p[x-1][y].g;
						px.g.addPx(px);
					}}catch (Exception e){}
					
					try{if(b[x][y-1] && px.g == null && p[x][y-1].g != null) {
						px.g = p[x][y-1].g;
						px.g.addPx(px);
					}}catch (Exception e){}
					
					try{if(b[x][y-1] && px.g == null && p[x][y-1].g != null) {
						px.g = p[x][y-1].g;
						px.g.addPx(px);
					}}catch (Exception e){}
					
					if(px.g == null) {
						CenterPixelGroup g = new CenterPixelGroup();
						g.addPx(px);
						px.g = g;
						Gs.add(g);
					}
					
					updateSurroundingGroups(b, i, p, px);
				}
				
				try{px.isBound = !b[x+1][y] || !b[x-1][y] || !b[x][y+1] || !b[x][y-1];}catch (Exception e){}
				if(px.isBound) {
					int total = 0;
					float totalR = 0;
					float totalG = 0;
					float totalB = 0;
					
					try{if(!b[x+1][y]) {
						total++;
						totalR += i.getPixel(x+1, y)[0];
						totalG += i.getPixel(x+1, y)[1];
						totalB += i.getPixel(x+1, y)[2];
					}}catch (Exception e){}
					try{if(!b[x-1][y]) {
						total++;
						totalR += i.getPixel(x-1, y)[0];
						totalG += i.getPixel(x-1, y)[1];
						totalB += i.getPixel(x-1, y)[2];
					}}catch (Exception e){}
					try{if(!b[x][y+1]) {
						total++;
						totalR += i.getPixel(x, y+1)[0];
						totalG += i.getPixel(x, y+1)[1];
						totalB += i.getPixel(x, y+1)[2];
					}}catch (Exception e){}
					try{if(!b[x][y-1]) {
						total++;
						totalR += i.getPixel(x, y-1)[0];
						totalG += i.getPixel(x, y-1)[1];
						totalB += i.getPixel(x, y-1)[2];
					}}catch (Exception e){}
					
					px.rB = totalR/total;
					px.gB = totalG/total;
					px.bB = totalB/total;
					
				}
			}
		}
		
		for(int I = 0; I < Gs.size(); I++) {
			if(!Gs.get(I).enabled) {
				Gs.remove(I);
				I--;
			}
		}
		
		return Gs;
	}
	
	static void updateSurroundingGroups(boolean[][] b, FloatingImage i, Pixel[][] p, Pixel px) {
		if(px.isBlack && px.g != null) {
			try{updateGroup(b, i, p, px, p[px.p.x+1][px.p.y]);}catch (Exception e){}
			try{updateGroup(b, i, p, px, p[px.p.x-1][px.p.y]);}catch (Exception e){}
			try{updateGroup(b, i, p, px, p[px.p.x][px.p.y+1]);}catch (Exception e){}
			try{updateGroup(b, i, p, px, p[px.p.x][px.p.y-1]);}catch (Exception e){}
		}
	}
	
	static void updateGroup(boolean[][] b, FloatingImage i, Pixel[][] p, Pixel px, Pixel temp) {
		if(temp.isBlack && temp.g != null && temp.g != px.g) {
			if(temp.g.enabled) px.g.merge(temp.g);
			temp.g = px.g;
			updateSurroundingGroups(b, i, p, temp);
		}
	}
	
	static boolean[][] getAreBlack(FloatingImage i, Color blackRef) {
		boolean[][] out = new boolean[i.getWidth()][i.getHeight()];
		
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				out[x][y] = getIsBlack(i.getPixel(x, y), blackRef);
			}
		}
		
		return out;
	}
	
	static boolean getIsBlack(float[] px, Color blackRef) {
		boolean out = true;
		if(px[0] > blackRef.getRed()/255f) out = false;
		if(px[1] > blackRef.getGreen()/255f) out = false;
		if(px[2] > blackRef.getBlue()/255f) out = false;
		return out;
	}
	
	static final float[][] B1 = {
			{0.06666666f,	0.13333333f,	0.06666666f},
			{0.13333333f,	0.2f,			0.13333333f},
			{0.06666666f,	0.13333333f,	0.06666666f}
	};
	
	static final float[][] B2 = {
			{1*0.015384615f,2*0.015384615f,3*0.015384615f,2*0.015384615f,1*0.015384615f},
			{2*0.015384615f,3*0.015384615f,4*0.015384615f,3*0.015384615f,2*0.015384615f},
			{3*0.015384615f,4*0.015384615f,5*0.015384615f,4*0.015384615f,3*0.015384615f},
			{2*0.015384615f,3*0.015384615f,4*0.015384615f,3*0.015384615f,2*0.015384615f},
			{1*0.015384615f,2*0.015384615f,3*0.015384615f,2*0.015384615f,1*0.015384615f}
	};
	
	static final float[][][] B = {null, B1, B2};
	
	static FloatingImage applyBlur(FloatingImage i, int rad) {
		float[][] m = B[rad];
		FloatingImage out = new FloatingImage(i.getWidth(), i.getHeight(), 3, i.getType());
		
		for(int x = m.length-1; x < i.getWidth()-m.length; x++) {
			for(int y = m[0].length-1; y < i.getHeight()-m[0].length; y++) {
				float totalR = 0;
				
				for(int iX = -rad; iX <= rad; iX++) {
					for(int iY = -rad; iY <= rad; iY++) {
						totalR += i.getPixel(x+iX, y+iY)[0] * m[iX+rad][iY+rad];
					}
				}
				
				float totalG = 0;
				
				for(int iX = -rad; iX <= rad; iX++) {
					for(int iY = -rad; iY <= rad; iY++) {
						totalG += i.getPixel(x+iX, y+iY)[1] * m[iX+rad][iY+rad];
					}
				}
				
				float totalB = 0;
				
				for(int iX = -rad; iX <= rad; iX++) {
					for(int iY = -rad; iY <= rad; iY++) {
						totalB += i.getPixel(x+iX, y+iY)[2] * m[iX+rad][iY+rad];
					}
				}
				
				out.setPixel(x, y, new float[]{totalR, totalG, totalB});
			}
		}
		
		int radT2 = rad*2;
		return out.newCropped(radT2, radT2, out.getWidth()-(radT2+2), out.getHeight()-(radT2+2));
	}
	
	static void printTest(ArrayList<CenterPixelGroup> Gs) {
		System.out.println("Found " + Gs.size() + " black areas:");
		for(int i = 0; i < Gs.size(); i++) {
			System.out.println("Group " + (i+1));
			System.out.println("Size: " + Gs.get(i).members.size());
			Gs.get(i).updateGroupStats();
			System.out.println("Surrounding red component: " + Gs.get(i).rB);
			System.out.println("Surrounding green component: " + Gs.get(i).gB);
			System.out.println("Surrounding blue component: " + Gs.get(i).bB);
			System.out.println("Average X pos: " + Gs.get(i).gPosX);
			System.out.println("Average Y pos: " + Gs.get(i).gPosY);
			System.out.println("Edge distance deviation: " + Gs.get(i).edgeDistanceDeviation);
			//System.out.println(Gs.get(i).enabled);
			
			System.out.println();
		}
	}
	
}
