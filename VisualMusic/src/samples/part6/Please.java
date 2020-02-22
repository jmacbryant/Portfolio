package src.com.badlogic.audio.samples.part6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import src.com.badlogic.audio.analysis.FFT;
import src.com.badlogic.audio.io.AudioDevice;
import src.com.badlogic.audio.io.MP3Decoder;
import src.com.badlogic.audio.visualization.MyPlot;

/**	The main function in this file uses the following files that are unique to this project:
	-	FFT.java			[Which uses --> FourierTransform.java]
	-	AudioDevice.java
	-	MP3Decoder.java		[Which uses --> Decoder.java]
	*	MyPlot.java			[Heavily edited from --> Plot.java]
*/

public class Please {
	//public static final String FILE = "samples/T.R.A.M. - Seven Ways Till Sunday.mp3";	
	
	public static void main( String[] argv ) throws Exception
	{
		//Browse file system for the desired .mp3 file to be visualized
		//SELECTED FILE MUST BE A .mp3 FILE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		JFileChooser jfc = new JFileChooser();
		jfc.showDialog(null,"Please Select the desired .mp3 file. \n ***MUST BE A .mp3 FILE***");
		jfc.setVisible(true);
		final String FILE = jfc.getSelectedFile().getPath();
		System.out.println("File name "+FILE);
		
		
		MP3Decoder decoder = new MP3Decoder( new FileInputStream( FILE  ) );
		AudioDevice device = new AudioDevice( );
		
		
		//Opens a color chooser so that the user can pick their desired colors for the animation
		Color bgcolor = JColorChooser.showDialog(null, "Choose the background color for the animation:", Color.BLACK);
		Color color1 = JColorChooser.showDialog(null, "Choose the first animation color:", Color.BLUE);
		Color color2 = JColorChooser.showDialog(null, "Choose the second animation color:", Color.WHITE);
		
		//Get Screen Dimensions
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Convert Screen Dimensions to length and width
		//final int width = (int)screenSize.getWidth();
		//final int height = (int)screenSize.getHeight();
		
		
		MyPlot plot = new MyPlot( "Animation", 1024, 512 );
		FFT fft = new FFT( 1024, 44100 );
		float[] samples = new float[1024];
		float[] spectrum = new float[1024 / 2 + 1];
		float[] lastSpectrum = new float[1024 / 2 + 1];
		
		long time = System.currentTimeMillis();
		long lastTime = time;
		int count = 0;
		int bands = 64;
		int currentBands = bands;
		int cycle = 4;
		int c = cycle;
		while( decoder.readSamples( samples ) > 0 )
		{		
			fft.forward( samples );
			System.arraycopy( spectrum, 0, lastSpectrum, 0, spectrum.length );
			System.arraycopy( fft.getSpectrum(), 0, spectrum, 0, spectrum.length );
			if((time - lastTime) > 100) {
				plot.animate( spectrum, lastSpectrum, 1, bgcolor, color1, color2 , currentBands, count);
				lastTime = time;
				count++;
				if(count%currentBands == 0){
					if(currentBands <= c || currentBands == bands) { 
						cycle *= -1;
					}
					currentBands += cycle;
				}
				System.out.println(currentBands);
			}
			device.writeSamples(samples);

			time = System.currentTimeMillis();
			Thread.sleep(15);
		}		
		
			
	}
}
