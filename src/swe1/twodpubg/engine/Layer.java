package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

//Drawable���� ��Ƴ��� ����Ʈ
public class Layer extends ArrayList<Drawable> {
	// �ش� Layer�� �ִ� ��� drawable���� draw�� ȣ���Ѵ�.
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
