package swe1.twodpubg.engine;

import swe1.twodpubg.util.Constants;

public class Camera {
	private double cameraX;
	private double cameraY;
	public Camera() {
		cameraX = Constants.FRAME_SIZE_X / 2;
		cameraY = Constants.FRAME_SIZE_Y / 2;
	}
	public double getCameraX() {
		return cameraX;
	}
	public void setCameraX(double cameraX) {
		this.cameraX = cameraX;
	}
	public double getCameraY() {
		return cameraY;
	}
	public void setCameraY(double cameraY) {
		this.cameraY = cameraY;
	}
	
	public static double fixPosX(double posX, double cameraX) {
		return posX + Constants.FRAME_SIZE_X/2 - cameraX;
	}
	
	public static double fixPosY(double posY, double cameraY) {
		return posY + Constants.FRAME_SIZE_Y/2 - cameraY;
	}
}
