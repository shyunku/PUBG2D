package swe1.twodpubg.util;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

//KeyInputÀ» °ü¸®
public class KeyInput {
	KeyTypedListener listener;
	HashMap<Integer, Integer> keyMap;
	
	public KeyInput() {
		 keyMap = new HashMap<Integer, Integer>();
	}

	public void setKeyTypedListener(KeyTypedListener l) {
		listener = l;
	}

	public static interface KeyTypedListener {
		public abstract void keyTyped(KeyEvent e);
	}

	public Boolean isPressed(int keyCode) {
		try {
			int result = keyMap.get(keyCode);
			return result == 1 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public KeyListener getKeyListener() {
		return new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				keyMap.put(e.getKeyCode(), 1);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyMap.put(e.getKeyCode(), 0);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (listener != null)
					listener.keyTyped(e);
			}
		};
	}
	
	public KeyEventDispatcher getKeyEventDispatcher() {
		return new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				switch(e.getID()) {
				case KeyEvent.KEY_PRESSED:
					keyMap.put(e.getKeyCode(), 1);
					break;
				case KeyEvent.KEY_RELEASED:
					keyMap.put(e.getKeyCode(), 0);
					break;
				case KeyEvent.KEY_TYPED:
					if(listener != null)
						listener.keyTyped(e);
					break;
				}
				return false;
			}
		};
	}
}
