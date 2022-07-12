package swe1.twodpubg.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import swe1.twodpubg.drawable.DropedItem;

public class Resources {
	private static Resources instance;
	private HashMap<String, BufferedImage> imageMap;
	private String imgResourcePath;
	private String audioResourcePath;

	public static Resources getInstance() {
		if (instance == null)
			instance = new Resources();
		return instance;
	}

	public void loadImage(String imageName, String dirPath, String fileName, int width, int height) {
		try {
			BufferedImage loaded = ImageIO.read(new File(dirPath, fileName));
			loaded = resizeImage(loaded, width, height);
			imageMap.put(imageName, loaded);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadImage(String imageName, String dirPath, String fileName) {
		try {
			imageMap.put(imageName, ImageIO.read(new File(dirPath, fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playAudio(String audioName) {
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(new File(audioResourcePath, audioName));
			AudioFormat af = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			clip.start();
		} catch (Exception e) {

		}
	}

	public void playAudio(String audioName, float pan) {
		AudioInputStream ais;
		pan *= 1.2;
		float toCutVol = Math.abs(pan)*5;
		if (Math.abs(pan) >= 1) {
			pan = pan < 0 ? -1 : 1;
		}
		try {
			ais = AudioSystem.getAudioInputStream(new File(audioResourcePath, audioName));
			AudioFormat af = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl panController = (FloatControl) clip.getControl(FloatControl.Type.PAN);
			panController.setValue(pan);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-toCutVol);
			clip.start();
		} catch (Exception e) {

		}
	}

	public BufferedImage getImage(String imageName) {
		return imageMap.get(imageName);
	}

	private Resources() {
		imageMap = new HashMap<>();
		Path currentRelativePath = Paths.get("resources/img");
		String path = currentRelativePath.toAbsolutePath().toString();
		imgResourcePath = path;
		//loadImage("bg", cstpath, "0.png");
		// 스나이퍼, 라이플
		loadImage("rifle_shoot_1", path, "rifle_shoot_1.png", Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
		loadImage("rifle_shoot_2", path, "rifle_shoot_2.png", Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
		loadImage("rifle_shoot_3", path, "rifle_shoot_3.png", Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
		// 무기 없을 때
		for (int i = 1; i <= 14; i++) {
			loadImage("noweapon_hit_" + i, path, "noweapon_hit_" + i + ".png", Constants.PLAYER_SIZE,
					Constants.PLAYER_SIZE);
		}
		// 권총
		for (int i = 1; i <= 3; i++) {
			loadImage("handgun_shoot_" + i, path, "handgun_shoot_" + i + ".png", Constants.PLAYER_SIZE,
					Constants.PLAYER_SIZE);
		}
		// 샷건
		for (int i = 1; i <= 3; i++) {
			loadImage("shotgun_shoot_" + i, path, "shotgun_shoot_" + i + ".png", Constants.PLAYER_SIZE,
					Constants.PLAYER_SIZE);
		}
		// 핏자국
		for (int i = 1; i <= 7; i++) {
			loadImage("bloodsplats_" + i, path, "bloodsplats_000" + i + ".png");
		}
		loadImage("bush_small", path, "bush_small.png", 150, 150);
		loadImage("box_256", path, "box_80.png");
		loadImage("rifle_bullet", path, "rifle_bullet.png");
		loadImage("grenade_red", path, "grenade_red.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("grenade_yellow", path, "grenade_yellow.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("grenade", path, "grenade.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("kar98k_white", path, "kar98k_white.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("p92_white", path, "P92_white.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("s12k_white", path, "s12k_white.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("m416_white", path, "M416_white.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("rip", path, "rip.png", Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
		loadImage("ammo", path, "ammo.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("armor", path, "armor.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("smokebomb", path, "smokebomb.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("scope", path, "scope.png", DropedItem.SIZE, DropedItem.SIZE);
		loadImage("fist", path, "fist.png", 45, 45);
		loadImage("MeOnMap", path, "transparent_memap.png", 50,50);
		loadImage("survive_text", path, "survivetext.png",70,44);
		loadImage("bullet_icon", path, "bullets_trans.png",24,36);
		//BG image
		loadImage("bg", path, "BackGround.bmp");
		loadImage("damageeffect", path, "Damage.png");
		loadImage("loadingImage", path, "battleground2d.jpg",1024,1024);
		//gun icon
		loadImage("kar98k_whiteIcon", path, "kar98k_white.png", (int)(DropedItem.SIZE*1.5), (int)(DropedItem.SIZE*1.5));
		loadImage("s12k_whiteIcon", path, "s12k_white.png", (int)(DropedItem.SIZE*1.5), (int)(DropedItem.SIZE*1.5));
		loadImage("m416_whiteIcon", path, "M416_white.png", (int)(DropedItem.SIZE*1.5), (int)(DropedItem.SIZE*1.5));
		loadImage("p92_whiteIcon", path, "P92_white.png", (int)(DropedItem.SIZE*1), (int)(DropedItem.SIZE*1));
		//object
		loadImage("HealKit", path, "medikit.png",70,70);
		//loadImage("rock67", path, "Rock_round.png",220,220);
		loadImage("rock1", path, "Rocks1.png",366,282);
		loadImage("rock2", path, "Rocks2.png",440,362);
		loadImage("rock3", path, "Rocks3.png",310,254);
		loadImage("rock4", path, "Rocks4.png",354,346);
		//기타
		loadImage("run", path, "run.png", 30,24);
		currentRelativePath = Paths.get("resources/sound");
		path = currentRelativePath.toAbsolutePath().toString();
		audioResourcePath = path;
	}
	static float w;
	static float h;
	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		w = new Float(width);
		h = new Float(height);
		if (w <= 0 && h <= 0) {
			w = image.getWidth();
			h = image.getHeight();
		} else if (w <= 0) {
			w = image.getWidth() * (h / image.getHeight());
		} else if (h <= 0) {
			h = image.getHeight() * (w / image.getWidth());
		}
		int wi = (int) w;
		int he = (int) h;
		BufferedImage resizedImage = new BufferedImage(wi, he, BufferedImage.TYPE_INT_ARGB);
		resizedImage.getGraphics().drawImage(image.getScaledInstance(wi, he, Image.SCALE_AREA_AVERAGING), 0, 0, wi, he,
				null);
		return resizedImage;

	}
}
