package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class Bush extends Drawable {

	BufferedImage bush;
	public int width = 0;
	public int height = 0;
	int r = 0;
	private boolean pIsInBush = false;

	public Bush() {
		bush = Resources.getInstance().getImage("bush_small");
		width = bush.getWidth();
		height = bush.getHeight();
		r = width / 2 - 10;
		r *= r;
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int drawX = (int) Camera.fixPosX(getX(), curX);
		int drawY = (int) Camera.fixPosY(getY(), curY);
		int dX = (drawX + width / 2) - Constants.FRAME_SIZE_X / 2;
		int dY = (drawY + height / 2) - Constants.FRAME_SIZE_Y / 2;
		Composite c = graphics.getComposite();
		// 만약 부쉬가 카메라에 잡혀있으면 부쉬를 밝힌다.
		boolean isinBush = dX * dX + dY * dY < r;
		if (isinBush && !GamePanel.isScopeMode) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			graphics.setComposite(ac);
			if (!pIsInBush)
				Resources.getInstance().playAudio("bush_rustle.wav");
		}
		graphics.drawImage(bush, null, drawX, drawY);
		// composite 복구
		if (isinBush) {
			graphics.setComposite(c);
		}
		pIsInBush = isinBush;
	}

}
