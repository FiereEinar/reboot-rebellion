package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip clip;
	private URL soundURL[] = new URL[30];
	public static final int NONE = 29;
	public static final int PISTOL_SHOT = 0;
	public static final int SHOTGUN_SHOT = 1;
	public static final int AK47_SHOT = 2;
	public static final int M249_SHOT = 3;
	public static final int SNIPER_SHOT = 4;
	public static final int ROBOT_SHOT = 5;
	public static final int ROBOT_CHARGING = 6;
	public static final int ROBOT_ATTACK_2 = 7;
	public static final int GUN_CLOCK = 8;
	public static final int GUN_RELOAD = 9;
	public static final int ROBOT_HIT = 10;
	public static final int PLAYER_HIT = 11;
	public static final int PLAYER_FOOTSTEPS = 12;
	public static final int BOSS_FOOTSTEPS = 13;
	public static final int ROBOT_SLASH = 14;
	public static final int MUSIC_BOSS = 15;
	public static final int MUSIC_LEVEL = 16;
	public static final int ROBOT_EXPLOSION = 17;
	
	public Sound() {
		String[] urls = {
				"/sounds/pistol-shot.wav",
				"/sounds/shotgun-firing.wav",
				"/sounds/ak47-sound.wav",
				"/sounds/m249-sound2.wav",
				"/sounds/sniper-rifle-firing.wav",
				"/sounds/sci-fi-weapon-shoot.wav",
				"/sounds/sci-fi-weapon-charging.wav",
				"/sounds/enemy-attack-2.wav",
				"/sounds/gun-clock (2).wav",
				"/sounds/reload.wav",
				"/sounds/robot-hit.wav",
				"/sounds/player-hit.wav",
				"/sounds/player-footsteps.wav",
				"/sounds/boss-footsteps.wav",
				"/sounds/robot-slash.wav",
				"/sounds/apocalypse-254123.wav",
				"/sounds/danger-and-resolution-183864.wav",
				"/sounds/explosion-sound-effect-1-free-on-gamesfxpackscom-241821.wav",
		};
		
		for (int i = 0; i < urls.length; i++) {
			soundURL[i] = getClass().getResource(urls[i]);
		}
	}
	
	public void play(int i) {
		if (i == NONE) return;
		setFile(i);
		play();
	}
	
	public void setFile(int i) {
		if (i == NONE) return;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
//		if (clip == null) return;
//		clip.start();
	}
	
	public void loop() {
//		if (clip == null) return;
//		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		if (clip == null) return;
		clip.stop();
	}

}
