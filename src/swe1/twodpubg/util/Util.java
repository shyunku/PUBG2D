package swe1.twodpubg.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Util {
	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
