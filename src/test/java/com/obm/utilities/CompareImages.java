package com.obm.utilities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.net.URL;

import javax.imageio.ImageIO;

public class CompareImages {

	public boolean compareImages(String webUrl, String workspace_image_name_with_extn) {
		System.out.println("Executing processImage Keyword");

		try {
			String file2 = "target/classes/images/"+workspace_image_name_with_extn;//ac-logo.png
			WebUtils.setProxies();
            
            URL url = new URL(webUrl);
			Image pic1 = ImageIO.read(url);
			Image pic2 = Toolkit.getDefaultToolkit().getImage(file2);

			try {

				PixelGrabber grab11 = new PixelGrabber(pic1, 0, 0, -1, -1, true);
				PixelGrabber grab21 = new PixelGrabber(pic2, 0, 0, -1, -1, true);

				int[] array1 = null;

				if (grab11.grabPixels()) {
//					System.out.println("coming inside here");
					int width = grab11.getWidth();
					int height = grab11.getHeight();
					array1 = new int[width * height];
					array1 = (int[]) grab11.getPixels();
					System.out.println("Number of pixels in Web Image:"+array1.length);
				}

				int[] array2 = null;

				if (grab21.grabPixels()) {
					int width = grab21.getWidth();
					int height = grab21.getHeight();
					array2 = new int[width * height];
					array2 = (int[]) grab21.getPixels();
					System.out.println("Number of pixels in Local Image:"+array2.length);
				}

				System.out.println("Pixels equal: " + java.util.Arrays.equals(array1, array2));

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return true;
		} catch (Exception t) {
			// report error
			t.printStackTrace();
			return false;
		}

	}
	

}
