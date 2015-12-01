package pageextractor.start;

import com.deb.lib.image.IRGBImage;
import com.deb.lib.program.ProgramFs;

import pageextractor.fiducials.Fiducial;
import pageextractor.fiducials.FiducialLocator;
import pageextractor.singlepage.SinglePageLocator;

public class Start {

	public static void main(String[] args) {
		IRGBImage i = new IRGBImage(ProgramFs.getProgramFile("testing/blank.png"));
		Fiducial[] f = FiducialLocator.locateFiducials(i);
		SinglePageLocator.locateSinglePage(i, f);
	}
}
