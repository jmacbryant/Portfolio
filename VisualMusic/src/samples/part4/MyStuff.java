package src.com.badlogic.audio.samples.part4;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import src.com.badlogic.audio.io.AudioDevice;
import src.com.badlogic.audio.io.MP3Decoder;
import src.com.badlogic.audio.visualization.Plot;

public class MyStuff {

	private static final int SAMPLE_WINDOW_SIZE = 1024;	
	private static final String FILE = "trunk/samples/FlyBy (Cut and Fade).mp3";
	
	public static void main( String[] argv ) throws FileNotFoundException, Exception
	{
		float[] samples = readInAllSamples( FILE );

		Plot plot = new Plot( "Wave Plot", 1024, 512 );
				
		
		MP3Decoder decoder = new MP3Decoder( new FileInputStream( FILE ) );
		AudioDevice device = new AudioDevice( );
		samples = new float[SAMPLE_WINDOW_SIZE];
		
		long startTime = 0;
		while( decoder.readSamples( samples ) > 0 )
		{
			device.writeSamples( samples );
			
			//plot.animate( samples, SAMPLE_WINDOW_SIZE, Color.red, startTime );
			if( startTime == 0 )
				startTime = System.nanoTime();
			Thread.sleep(15); // this is needed or else swing has no chance repainting the plot!
			
		}
	}
	
	public static float[] readInAllSamples( String file ) throws FileNotFoundException, Exception
	{
		MP3Decoder decoder = new MP3Decoder( new FileInputStream( file ) );
		ArrayList<Float> allSamples = new ArrayList<Float>( );
		float[] samples = new float[1024];

		while( decoder.readSamples( samples ) > 0 )
		{
			for( int i = 0; i < samples.length; i++ )
				allSamples.add( samples[i] );
		}

		samples = new float[allSamples.size()];
		for( int i = 0; i < samples.length; i++ )
			samples[i] = allSamples.get(i);

		return samples;
	}	

}

