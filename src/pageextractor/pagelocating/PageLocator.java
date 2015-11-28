package pageextractor.pagelocating;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IRGBImage;
import com.deb.lib.image.Image;

public class PageLocator {
	
	private float[] pC;
	
	public PageLocator(){ this.pC = new float[]{150/255f,170/255f,180/255f};}
	public PageLocator(float[] pageColor) { this.pC = pageColor; }
	
	public FloatingImage locatePages(Image i) {
		//return EdgeDetector.detectEdges(fIByPageColor(new IRGBImage(i)));
		return fIByPageColor(new IRGBImage(i));
	}
	
	private FloatingImage fIByPageColor(IRGBImage i) {
		FloatingImage out = new FloatingImage(i.getWidth(), i.getHeight(), 1, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				Color pxC = i.getPixel(x, y);
				float[] px = new float[]{pxC.getRed()/255f, pxC.getGreen()/255f, pxC.getBlue()/255f};
				float nPx = 1-(evalPageRatioSim(px)+evalPageLumSim(px))/2;
				out.setPixel(x, y, new float[]{nPx});
				//System.out.println("outval");
				//System.out.println(nPx);
			}
		}
		
		return out;
	}
	
	private float evalPageRatioSim(float[] px) {
		float pCAvg = (px[0]+px[1]+px[2])/3f;
		float pXAvg = (this.pC[0]+this.pC[1]+this.pC[2])/3f;
		
		float pCR = this.pC[0]/pCAvg;
		float pXR = px[0]/pXAvg;
		
		float pCG = this.pC[1]/pCAvg;
		float pXG = px[1]/pXAvg;
		
		float pCB = this.pC[2]/pCAvg;
		float pXB = px[2]/pXAvg;

		float rDif = Math.abs(pCR - pXR);
		float gDif = Math.abs(pCG - pXG);
		float bDif = Math.abs(pCB - pXB);

		//System.out.println("Ratio");
		float out = ((rDif+gDif+bDif)/3);
		//System.out.println(out);
		if(out > 1) out = 1;//NOTE: BAD!
		//System.out.println(out);
		return out;
	}
	
	private float evalPageLumSim(float[] c) {
		float rDif = Math.abs(this.pC[0] - c[0]);
		float gDif = Math.abs(this.pC[1] - c[1]);
		float bDif = Math.abs(this.pC[2] - c[2]);
		
		//System.out.println("Lum");
		//System.out.println(1-((rDif+gDif+bDif)/3));
		return 1-((rDif+gDif+bDif)/3);
	}
}
