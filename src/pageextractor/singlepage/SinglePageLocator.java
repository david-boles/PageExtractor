package pageextractor.singlepage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.deb.lib.image.IRGBImage;
import com.deb.lib.program.ProgramFs;

import pageextractor.fiducials.Fiducial;

public class SinglePageLocator {
	
	public static void locateSinglePage(IRGBImage i, Fiducial[] f) {
		Fiducial[] cFs = confirmFiducials(f);
		BufferedImage bi = i.getBufImageCopy();
		Graphics g = bi.getGraphics();
		g.setColor(Color.CYAN);
		for(int it = 0; it < 4; it++) {
			g.fillOval((int)cFs[it].getX()-10, (int)cFs[it].getY()-10, 20, 20);
		}
		g.dispose();
		IRGBImage iout = new IRGBImage(bi);
		iout.writeImage("png", ProgramFs.getProgramFile("testing/locateddemo.png"));
	}

	static Fiducial[] confirmFiducials(Fiducial[] f) {
		if(f.length < 4) return null;
		Fiducial[] out = new Fiducial[4];
		ArrayList<Fiducial> red = new ArrayList<Fiducial>();
		ArrayList<Fiducial> green = new ArrayList<Fiducial>();
		ArrayList<Fiducial> blue = new ArrayList<Fiducial>();
		
		for(int i = 0; i < f.length; i++) {
			if(f[i].getType() == Fiducial.TYPE_RED) red.add(f[i]);
			if(f[i].getType() == Fiducial.TYPE_GREEN) green.add(f[i]);
			if(f[i].getType() == Fiducial.TYPE_BLUE) blue.add(f[i]);
		}
		
		if(red.size() < 1) return null;
		else {
			Fiducial highest = new Fiducial(-1, -1, -1, -1);
			for(int i = 0; i < red.size(); i++) {
				if(red.get(i).getColorRatio() > highest.getColorRatio()) highest = red.get(i);
			}
			out[0] = highest;
		}
		
		if(blue.size() < 2) return null;
		else {
			Fiducial highest = new Fiducial(-1, -1, -1, -1);
			for(int i = 0; i < blue.size(); i++) {
				if(blue.get(i).getColorRatio() > highest.getColorRatio()) highest = blue.get(i);
			}
			blue.remove(highest);
			out[1] = highest;
		}
		
		if(blue.size() < 1) return null;
		else {
			Fiducial highest = new Fiducial(-1, -1, -1, -1);
			for(int i = 0; i < blue.size(); i++) {
				if(blue.get(i).getColorRatio() > highest.getColorRatio()) highest = blue.get(i);
			}
			out[2] = highest;
		}
		
		if(green.size() < 1) return null;
		else {
			Fiducial highest = new Fiducial(-1, -1, -1, -1);
			for(int i = 0; i < green.size(); i++) {
				if(green.get(i).getColorRatio() > highest.getColorRatio()) highest = green.get(i);
			}
			out[3] = highest;
		}
		
		return out;
	}
	
	
	
}
