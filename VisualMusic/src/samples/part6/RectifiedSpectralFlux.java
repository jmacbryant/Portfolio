package src.com.badlogic.audio.samples.part6;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import src.com.badlogic.audio.analysis.FFT;
import src.com.badlogic.audio.analysis.SpectrumProvider;
import src.com.badlogic.audio.io.MP3Decoder;
import src.com.badlogic.audio.visualization.PlaybackVisualizer;
import src.com.badlogic.audio.visualization.Plot;

/**
 * Calculates the spectral flux of a song and displays the
 * resulting plot.
 * 
 * @author mzechner
 *
 */
public class RectifiedSpectralFlux 
{
	public static final String FILE = "samples/judith.mp3";	
	
	public static void main( String[] argv ) throws Exception
	{
		MP3Decoder decoder = new MP3Decoder( new FileInputStream( FILE  ) );							
		FFT fft = new FFT( 1024, 44100 );
		float[] samples = new float[1024];
		float[] spectrum = new float[1024 / 2 + 1];
		float[] lastSpectrum = new float[1024 / 2 + 1];
		List<Float> spectralFlux = new ArrayList<Float>( );
		
		while( decoder.readSamples( samples ) > 0 )
		{			
			fft.forward( samples );
			System.arraycopy( spectrum, 0, lastSpectrum, 0, spectrum.length ); 
			System.arraycopy( fft.getSpectrum(), 0, spectrum, 0, spectrum.length );
			
			float flux = 0;
			for( int i = 0; i < spectrum.length; i++ )	
			{
				float value = (spectrum[i] - lastSpectrum[i]);
				flux += value < 0? 0: value;
			}
			spectralFlux.add( flux );					
		}		
		
		
		Plot plot = new Plot( "Spectral Flux", 1024, 512 );
		plot.plot( spectralFlux, 1, Color.red );		
		new PlaybackVisualizer( plot, 1024, new MP3Decoder( new FileInputStream( FILE ) ) );
	}
}
