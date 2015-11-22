package pageextractor.start;

import com.deb.lib.image.BGRAYImage;
import com.deb.lib.image.FloatingImage;
import com.deb.lib.image.IARGBImage;
import com.deb.lib.program.ProgramFs;

import pageextractor.locatepages.DetectEdges;

public class Start {

	public static void main(String[] args) {
		System.out.println("0");
		BGRAYImage bI = new BGRAYImage(ProgramFs.getProgramFile("testing/test3.png"));
		System.out.println("1");
		System.out.println("4");
		
		FloatingImage fI = DetectEdges.detectEdges(bI);
		System.out.println("5");
		fI.getToBGRAY().writeImage("png", ProgramFs.getProgramFile("testing/testfloatinggreyblur2-1.png"));
	}

}
