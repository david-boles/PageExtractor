package pageextractor.pagelocating;

import java.awt.image.BufferedImage;

import com.deb.lib.image.BGRAYImage;
import com.deb.lib.image.FloatingImage;

public final class EdgeDetector {
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
	
	static final float[][] EH1 = {
			{0.25f,		0.5f,		0.25f},
			{0, 		0,			0},
			{-0.25f,	-0.5f,		-0.25f}
	};
	
	static final float[][] EV1 = {
			{-0.125f,	0,		0.125f},
			{-0.25f,		0,		0.25f},
			{-0.125f,	0,		0.125f}
	};
	
	static final float[][][] B = {null, B1, B2};
	static final float[][][] EH = {null, EH1};
	static final float[][][] EV = {null, EV1};
	
	public static FloatingImage detectEdges(FloatingImage i) {
		return detectEdges(i, 1)[0];
	}
	
	static FloatingImage applyBlur(FloatingImage i, int rad) {
		float[][] m = B[rad];
		FloatingImage out = new FloatingImage(i.getWidth(), i.getHeight(), 1, i.getType());
		
		for(int x = m.length-1; x < i.getWidth()-m.length; x++) {
			for(int y = m[0].length-1; y < i.getHeight()-m[0].length; y++) {
				float total = 0;
				
				for(int iX = -rad; iX <= rad; iX++) {
					for(int iY = -rad; iY <= rad; iY++) {
						total += i.getPixel(x+iX, y+iY)[0] * m[iX+rad][iY+rad];
					}
				}
				
				out.setPixel(x, y, new float[]{total});
			}
		}
		
		return out;
	}
	
	static FloatingImage[] detectEdges(FloatingImage i, int rad) {
		float[][] hM = EH[rad];
		float[][] vM = EV[rad];
		FloatingImage tH = new FloatingImage(i.getWidth(), i.getHeight(), 1, i.getType());
		FloatingImage tV = new FloatingImage(i.getWidth(), i.getHeight(), 1, i.getType());
		FloatingImage outE = new FloatingImage(i.getWidth(), i.getHeight(), 1, i.getType());
		FloatingImage outR = new FloatingImage(i.getWidth(), i.getHeight(), 1, i.getType());
		
		for(int x = hM.length-1; x < i.getWidth()-hM.length; x++) {
			for(int y = hM[0].length-1; y < i.getHeight()-hM[0].length; y++) {
				float hTotal = 0;
				float vTotal = 0;
				
				for(int iX = -rad; iX <= rad; iX++) {
					for(int iY = -rad; iY <= rad; iY++) {
						hTotal += i.getPixel(x+iX, y+iY)[0] * hM[iX+rad][iY+rad]*5;
						vTotal += i.getPixel(x+iX, y+iY)[0] * vM[iX+rad][iY+rad]*5;
					}
				}
				
				tH.setPixel(x, y, new float[]{hTotal});
				tV.setPixel(x, y, new float[]{vTotal});
			}
			System.out.println(x);
		}
		
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				outE.setPixel(x, y, new float[]{Math.abs((tH.getPixel(x, y)[0] + tV.getPixel(x, y)[0])/2)});//NOTE: Yikes!
			}
		}
		
		return new FloatingImage[]{outE, outR};
	}
}
