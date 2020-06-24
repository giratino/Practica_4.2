package Viborita.Utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Utils
{
	public static int random(int min, int max)
	{
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static void playSound(Clip clip)
	{
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public static Clip getSound(String file)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds" + System.getProperty("file.separator") + file).getAbsoluteFile());
	        AudioFormat format = audioInputStream.getFormat();
	        DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip sound = (Clip)AudioSystem.getLine(info);
			sound.open(audioInputStream);
			return sound;
		}
		catch(IOException | LineUnavailableException | UnsupportedAudioFileException e)
		{
			return null;
		}
	}
	
	public static Image getImage(String file)
	{
		return Toolkit.getDefaultToolkit().getImage(
				new File("imagenes").getPath() + System.getProperty("file.separator")
						+ file);
	}
}
