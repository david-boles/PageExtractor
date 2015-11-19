package pageextractor.start;

import com.deb.lib.image.BGRAYImage;
import com.deb.lib.image.IARGBImage;
import com.deb.lib.program.ProgramFs;

public class Start {

	public static void main(String[] args) {
		IARGBImage cI = new IARGBImage(ProgramFs.getProgramFile("testing/test.png"));
		BGRAYImage bI = new BGRAYImage(cI);
		bI.writeImage("png", ProgramFs.getProgramFile("testing/testgray.png"));
	}

}
