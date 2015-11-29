package pageextractor.start;

import com.deb.lib.image.IRGBImage;
import com.deb.lib.program.ProgramFs;

import pageextractor.fiducials.FiducialLocator;

public class Start {

	public static void main(String[] args) {
		FiducialLocator.locateFiducials(new IRGBImage(ProgramFs.getProgramFile("testing/test4.png")));
	}
}
