package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

//Drawable들을 모아놓은 리스트
public class Layer extends ArrayList<Drawable> {
	// 해당 Layer에 있는 모든 drawable에게 draw를 호출한다.
	public void draw(Graphics2D graphics, double curX, double curY) {
		Iterator<Drawable> i = iterator();
		while (i.hasNext()) {
			Drawable d = i.next();
			try {
				d.draw(graphics, curX, curY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		removeIf(e -> (e.getKilled()));
	}
}
