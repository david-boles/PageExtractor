package pageextractor.start;

import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IRGBImage;
import com.deb.lib.program.ProgramFs;

public class Start {

	public static void main(String[] args) {
		IRGBImage irgb = new IRGBImage(ProgramFs.getProgramFile("testing/test.png"));
		System.out.println(irgb);
		System.out.println(irgb.getHeight());
		irgb.writeImage("png", ProgramFs.getProgramFile("testing/testirgb.png"));
		FloatingImage f = new FloatingImage(irgb);
		System.out.println(f);
		f.getToIRGB().writeImage("png", ProgramFs.getProgramFile("testing/testirgbfloating.png"));
	}
}
