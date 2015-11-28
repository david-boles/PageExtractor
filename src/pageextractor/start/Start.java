package pageextractor.start;

import com.deb.lib.image.BGRAYImage;
import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IRGBImage;
import com.deb.lib.program.ProgramFs;

import pageextractor.pagelocating.PageLocator;

public class Start {

	public static void main(String[] args) {
		IRGBImage irgb = new IRGBImage(ProgramFs.getProgramFile("testing/test3.png"));
		new BGRAYImage(irgb).writeImage("png", ProgramFs.getProgramFile("testing/graynorm3.png"));
		PageLocator pL = new PageLocator();
		FloatingImage f = pL.locatePages(irgb);
		f.getToBGRAY().writeImage("png", ProgramFs.getProgramFile("testing/grayrat3.png"));
	}
}
