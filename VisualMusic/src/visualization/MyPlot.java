package src.com.badlogic.audio.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
//import java.awt.Panel;
import java.awt.image.BufferedImage;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;

//import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A simple class that allows to plot float[] arrays
 * to a swing window. The first function to plot that
 * is given to this class will set the minimum and 
 * maximum height values. I'm not that good with Swing
 * so i might have done a couple of stupid things in here :)
 * 
 * @author mzechner
 * @editor James Bryant
 *
 */
public class MyPlot 
{
	/** the frame **/
	private JFrame frame;

	/** the scroll pane **/
	//private JScrollPane scrollPane;

	/** the image GUI component **/
	private JPanel panel;	

	/** the image **/
	private BufferedImage image;	

	/** the last scaling factor to normalize samples **/
	//private float scalingFactor = 1;

	/** whether the plot was cleared, if true we have to recalculate the scaling factor **/
	private boolean cleared = true;

	/** current marker position and color **/
	private int markerPosition = 0;
	private Color markerColor = Color.white;

	/**
	 * Creates a new Plot with the given title and dimensions.
	 * 
	 * @param title The title.
	 * @param width The width of the plot in pixels.
	 * @param height The height of the plot in pixels.
	 */
	public MyPlot( final String title, final int width, final int height)
	{
		image = new BufferedImage( 1, 1, BufferedImage.TYPE_4BYTE_ABGR );

		try
		{
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() 
				{
					frame = new JFrame( title );
					frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );					
					//frame.setPreferredSize( new Dimension( width + frame.getInsets().left + frame.getInsets().right, frame.getInsets().top + frame.getInsets().bottom + height ) );
					BufferedImage img = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR );
					Graphics2D g = (Graphics2D)img.getGraphics();
					g.setColor( Color.black );
					g.fillRect( 0, 0, width, height );
					g.dispose();
					image = img;	
					panel = new JPanel( ) {


						@Override
						public void paintComponent( Graphics g )
						{			
							super.paintComponent(g);
							synchronized( image )
							{
								g.drawImage( image, 0, 0, null );
								g.setColor( markerColor );
								g.drawLine( markerPosition, 0, markerPosition, image.getHeight() );
							}

							Thread.yield();


							frame.repaint();
						}

						@Override
						public void update(Graphics g){
							paint(g);
						}

						public Dimension getPreferredSize()
						{
							return new Dimension( image.getWidth(), image.getHeight( ) );
						}
					};
					panel.setPreferredSize( new Dimension( width, height ) );	
					frame.add(panel, BorderLayout.CENTER);
					frame.pack();
					frame.setVisible( true );
					frame.setResizable(false);

				}
			});
		}
		catch( Exception ex )
		{
			// doh...
		}
	}

	public void clear( )
	{
		SwingUtilities.invokeLater( new Runnable( ) {

			@Override
			public void run() {
				Graphics2D g = image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, image.getWidth(), image.getHeight() );
				g.dispose();
				cleared = true;
			}
		});
	}



	public void animate( float[] samples, float[] previousSamples,final float samplesPerPixel, final Color bgcolor, final Color color1, final Color color2, final int bands, final int count)
	{			

		
		
		synchronized( image )
		{		
			Graphics2D g = image.createGraphics();
			
			final int width = image.getWidth();
			final int height = image.getHeight();
			
			final int BGFrameReset = 3;
			
			if( image.getWidth() <  samples.length / samplesPerPixel)
			{
				image = new BufferedImage( (int)(samples.length / samplesPerPixel), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
				g.setColor( bgcolor );
				g.fillRect( 0, 0, image.getWidth(), image.getHeight() );
				g.setColor(color1);
				for( int i = 1; i < samples.length; i+=bands )
				{
					g.drawLine( (int)(i-1)+(image.getWidth()/2), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2);
					g.drawLine( (image.getWidth()/2)-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2);

					//g.fillRoundRect( (int)(i-1), image.getHeight() - ((int)((samples[i-1] + previousSamples[i-1] )/2 * image.getHeight() )/4), (int)(i+bands-2), ((int)((samples[i-1] + previousSamples[i-1] )/2 * image.getHeight() )/4), bands, bands);

				}
				panel.setSize( image.getWidth(), image.getHeight( ));
			}
			else {
				if(count%BGFrameReset == 0) {
					g.setColor( bgcolor );
					g.fillRect( 0, 0, width, height );
					
				}
				//GradientPaint gp4 = new GradientPaint(25, 25, 
				//	    Color.blue, 15, 25, Color.black, true);
				//g.setPaint(gp4);
	
				for( int i = 1; i < samples.length-bands; i+=bands )
				{
					/**
					if(count > 150) {
						if(count%32 <= 7) {
							g.setColor(Color.white);
						}
						else if(count%32 <= 15){
							g.setColor(Color.blue);
	
						}
						else if(count%32 <= 23) {
							g.setColor(Color.red);
	
						}
						else {
							g.setColor(Color.blue);
	
						}
						g.drawLine( (int)(i-1)+(image.getWidth()/2), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						g.drawLine((int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
						if(count%32 <= 7) {
							g.setColor(Color.red);
						}
						else if(count%32 <= 15){
							g.setColor(Color.blue);
	
						}
						else if(count%32 <= 23) {
							g.setColor(Color.white);
	
						}
						else {
							g.setColor(Color.white);
	
						}
						g.drawLine( (image.getWidth()/2)-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						g.drawLine((image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
					
						g.drawLine( (int)(i-1)+(image.getWidth()/2), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						g.drawLine((int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
	
	
						if(count%32 <= 7) {
							g.setColor(Color.white);
						}
						else if(count%32 <= 15){
							g.setColor(Color.blue);
	
						}
						else if(count%32 <= 23) {
							g.setColor(Color.red);
	
						}
						else {
							g.setColor(Color.blue);
	
						}
						g.drawLine( (image.getWidth()/2)-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						g.drawLine((image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
					}
					**/
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
					
					if(count > -1) {
						if(count%32 >= 16) {
							g.setColor(color1);
						}
						else {
							g.setColor(color2);
						}
						g.drawLine( (int)(i-1)+(width/2), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(width/2), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						g.drawLine((int)(i+bands-1)+(width/2), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(width/2), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	//					
	//					g.drawLine( (int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 + image.getHeight()/2, (int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2 + image.getHeight()/2);
	//					g.drawLine((int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2 + image.getHeight()/2, (int)(i+bands-1), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2 + image.getHeight()/2);
						
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
						if(count%32 >= 16) {
							g.setColor(color2);
	
						}
						else {
							g.setColor(color1);
	
						}
						g.drawLine( (width/2)-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (width/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						g.drawLine((width/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (width/2)-(int)(i+bands-1), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
	//					g.drawLine( (image.getWidth())-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 + image.getHeight()/2, (image.getWidth())-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2 + image.getHeight()/2);
	//					g.drawLine((image.getWidth())-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2 + image.getHeight()/2, (image.getWidth())-(int)(i+bands-1), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2 + image.getHeight()/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
	
						g.drawLine( (int)(i-1)+(width/2), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(width/2), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						g.drawLine((int)(i+bands-1)+(width/2), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(width/2), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	//					
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
						
	//					g.drawLine( (int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 - image.getHeight()/2, (int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2 - image.getHeight()/2);
	//					g.drawLine((int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2 - image.getHeight()/2, (int)(i+bands-1), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2 - image.getHeight()/2);
						
	
	
						if(count%32 >= 16) {
							g.setColor(color1);
						}
						else {
							g.setColor(color2);
	
						}
						g.drawLine( (width/2)-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (width/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						g.drawLine((width/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (width/2)-(int)(i+bands-1), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
	//					g.drawLine( (image.getWidth()/2)-(int)(i-1) + image.getWidth()/2, (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 - image.getHeight()/2, (image.getWidth()/2)-(int)(i+bands-1) + image.getWidth()/2, (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2  - image.getHeight()/2);
	//					g.drawLine((image.getWidth()/2)-(int)(i+bands-1) + image.getWidth()/2, (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2 - image.getHeight()/2, (image.getWidth()/2)-(int)(i+bands-1) + image.getWidth()/2, (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2  - image.getHeight()/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
					}
					
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
					else {
						if(count%16 >= 8) {
							g.setColor(Color.cyan);
						}
						else {
							g.setColor(Color.pink);
	
						}
						g.drawLine( (int)(i-1)+(width/2), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(width/2), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						//g.drawLine((int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
						if(count%16 >= 8) {
							g.setColor(Color.pink);
	
						}
						else {
							g.setColor(Color.cyan);
	
						}
						g.drawLine( (width/2)-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (width/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i-1]) * image.getHeight() )))/2);
						//g.drawLine((image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() - ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() - ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
	
						g.drawLine( (int)(i-1)+(width/2), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (int)(i+bands-1)+(width/2), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						//g.drawLine((int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (int)(i+bands-1)+(image.getWidth()/2), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
						//g.fillOval( (int)(i-1)+(image.getWidth()/2)+bands/2, (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
	
	
	
						if(count%16 >= 8) {
							g.setColor(Color.cyan);
						}
						else {
							g.setColor(Color.pink);
	
						}
						g.drawLine( (width/2)-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , (width/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i-1] ) * image.getHeight() )))/2);
						//g.drawLine((image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() + ((int)((previousSamples[i+-1]) * image.getHeight() )))/2, (image.getWidth()/2)-(int)(i+bands-1), (image.getHeight() + ((int)(((samples[i+bands-1] + previousSamples[i+bands-1] )/2) * image.getHeight() )))/2);
	
						//g.fillOval( (image.getWidth()/2)-bands/2-(int)(i-1), (image.getHeight() + ((int)(((samples[i-1] + previousSamples[i-1] )/2) * image.getHeight() )))/2 , bands/2, bands/2);
					}
				}
			}
		g.dispose();

		}		
	}

}
