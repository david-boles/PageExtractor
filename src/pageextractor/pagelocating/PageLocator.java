package pageextractor.pagelocating;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IRGBImage;
import com.deb.lib.image.Image;

public class PageLocator {
	
	private Color pC;
	
	public PageLocator(){ this.pC = new Color(1f, 1f, 1f);}
	public PageLocator(Color pageColor) { this.pC = pageColor; }
	
	public FloatingImage locatePages(Image i) {
		return EdgeDetector.detectEdges(fIByPageColor(new IRGBImage(i)));
		
	}
	
	private FloatingImage fIByPageColor(IRGBImage i) {
		FloatingImage out = new FloatingImage(i.getWidth(), i.getHeight(), 3, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				Color px = i.getPixel(x, y);
			}
		}
		
		return out;
	}
	
	private float evalPageRatioSim(Color c) {
		
	}
	
	private float evalPageLumSim(Color c) {
		float rDif = Math.abs(this.pC.getRed() - c.getRed());
		float gDif = Math.abs(this.pC.getGreen() - c.getGreen());
		float bDif = Math.abs(this.pC.getBlue() - c.getBlue());
		
		return 1-((rDif+gDif+bDif)/3);
	}
}
