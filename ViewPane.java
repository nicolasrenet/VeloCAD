import java.io.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import java.lang.Math;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.DocumentException;
import com.itextpdf.awt.PdfGraphics2D;



public abstract class ViewPane extends JPanel {

	public static enum Layer { SCHEMATICS, CONTOURS, DIMENSIONS, POINTS }

	private static final float dash1[] = {10f};

	protected static final int ZOOM_INCREMENT = 2;
	public static final int ZOOM_MAX = 10;
	public static final int ZOOM_MIN = -10;

	protected static BasicStroke defaultStroke;
	protected static BasicStroke dashed ;

	protected Geometry jd;
	
	protected double scale ;
	protected static int zoomLevel = 0;
	protected Point2D.Double origin;
	protected static AffineTransform aft;

	protected  List<Measure> dimensions = new ArrayList<Measure>();	

	private static boolean zoom = false;

	private static Set<Layer> toDraw = EnumSet.of( Layer.SCHEMATICS,  Layer.CONTOURS, Layer.POINTS, Layer.DIMENSIONS );

	protected boolean mouseIn = false;
	//===========================================================================


	/*
	 */

	protected ViewPane(){
		// System.out.println("Invoking superclass ViewPane() default constructor");

		scale = 0.5D ;

		MouseListener ml = new MouseHandler(this);
		this.addMouseListener( ml );

		// Key bindings for scrolling actions
		Action leftScroll = new LeftScrollActionHandler( this );
		Action rightScroll = new RightScrollActionHandler( this );
		Action upScroll = new UpScrollActionHandler( this );
		Action downScroll = new DownScrollActionHandler( this );
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "scrollLeft");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "scrollRight");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "scrollUp");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "scrollDown");
		this.getActionMap().put("scrollLeft", leftScroll);
		this.getActionMap().put("scrollRight", rightScroll);
		this.getActionMap().put("scrollUp", upScroll);
		this.getActionMap().put("scrollDown", downScroll);

		this.setRequestFocusEnabled(true);

	}

	public ViewPane(Geometry j){
		
		this();
		// System.out.println("Invoking superclass ViewPane(Geometry j) constructor");

		jd = j;

		setDimensions();

	}

	protected void update(){

		for(Measure m: dimensions){
			// System.out.println("Updating dimension()");	
			m.update(); 
		}

		repaint();
	}

	public static void setLayer(Layer flag, boolean turnOn){
		if (turnOn)
			toDraw.add( flag );
		else
			toDraw.remove( flag );
	}

	public static boolean hasLayer( Layer flag  ){
		return toDraw.contains( flag );
	}

	protected void paintComponent(Graphics g){

		// System.out.println("Invoking ViewPane.paintComponent()");
		
		// Guarantees that the canvas gets erased before drawing
		super.paintComponent(g);

		if (origin == null ) origin = new Point2D.Double( this.getWidth()/2.0D, this.getHeight()*.8D);

		Graphics2D g2 = (Graphics2D) g;
		if ( ! jd.hasValidDataSet()){
			
			drawError( g2 );

		} else {

			aft = new AffineTransform(scale, 0D, 0D, -scale, origin.x, origin.y);
		
			g2.transform(aft);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

			defaultStroke = new BasicStroke((float) (.5F/scale));
			dashed = new BasicStroke( (float) (.5F/scale),
						BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND,
						1F, dash1, 0.0F);
			g2.setStroke(defaultStroke);

			if (toDraw.contains( Layer.CONTOURS )) {
				float alpha = 1F;
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
				drawContours(g2);
			}
			if (toDraw.contains( Layer.SCHEMATICS )) drawSchematics(g2);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
			if (toDraw.contains( Layer.DIMENSIONS )){ drawDimensions(g2); }

			g2.setFont( new Font(g2.getFont().getName(), g2.getFont().getStyle(), g2.getFont().getSize()/2));

			if (toDraw.contains( Layer.POINTS )){ drawPoints(g2 ); }
		}

	}

	public void toPdf(String fileName, String psz) throws IOException, DocumentException {
		// System.out.println("Export to PDF");

		com.itextpdf.text.Rectangle rect = PageSize.A0;
		//double widthOffset = .8D;
		//double heightOffset = .8D;
		double widthOffset = .75D;
		double heightOffset = .6D;
		double fold = 1; 

		if (psz == "A4"){
				rect = PageSize.A4;
				widthOffset = .5;
				heightOffset = .05;
				fold = 1D;

		}



		// System.out.println("PDF height: " + rect.getHeight() + " - PDF length: " + rect.getWidth() );

		Document document = new Document( rect );

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( fileName ));

		document.open();    

		PdfContentByte cb = writer.getDirectContent();

		Graphics2D g2 = new PdfGraphics2D( cb, rect.getWidth(), rect.getHeight());

		double ratio =  72D/25.4 * fold; // mm to pt conversion + page reduction

		// By defaut, the graphic (0,0) point matches the corner of the page: hence the offset
		aft = new AffineTransform(ratio, 0D, 0D, -ratio, rect.getWidth() * widthOffset, rect.getHeight() * heightOffset );
		
		g2.transform(aft);

		g2.rotate( Math.PI/2.0D );
		
		g2.setColor(Color.black);

		if (toDraw.contains( Layer.CONTOURS )) drawContours(g2);
		if (toDraw.contains( Layer.SCHEMATICS )) drawSchematics(g2);
		if (toDraw.contains( Layer.DIMENSIONS )){ drawDimensions(g2); }
		g2.setFont( new Font(g2.getFont().getName(), g2.getFont().getStyle(), g2.getFont().getSize()/2));
		if (toDraw.contains( Layer.POINTS )){ drawPoints(g2); }

		g2.dispose(); 
		document.close();
	}

	public void center(int x, int y){
		// System.out.println("ViewPane.center(" + x + ", " + y + ") - (" + origin.x + "," + origin.y + ")" + "(W:" + this.getWidth() + ", H:"+ this.getHeight() + ")");
		origin.x = this.getWidth()/2.0D - (x - origin.x);
		origin.y = this.getHeight()/2.0D - (y - origin.y);
	}
		

	public void scroll(int dirX, int dirY){
		origin.x += dirX * 40;
		origin.y += dirY * 40;
	}

	public void zoom(int x, int y){
		if (zoomLevel >= ZOOM_MAX) return ;

		zoomLevel++;
	        double ratio = (scale * ZOOM_INCREMENT)/scale;
	       	scale *= ZOOM_INCREMENT;
		origin.x = this.getWidth()/2.0D - ratio * (x - origin.x);
		origin.y = this.getHeight()/2.0D - ratio * (y - origin.y);
		// System.out.println("Zoom(): scale=" + scale + " Zoom level " + zoomLevel );
		zoom = true;
	}

	public void unzoom(int x, int y){
		if (zoomLevel <= ZOOM_MIN) return ;

		zoomLevel--;
	        double ratio = (scale / ZOOM_INCREMENT)/scale;
	        scale /= ZOOM_INCREMENT;
	        origin.x = this.getWidth()/2.0F - ratio * (x - origin.x);
	        origin.y = this.getHeight()/2.0F - ratio * (y - origin.y);
		// System.out.println("Unzoom(): scale=" + scale + " Zoom level " + zoomLevel);
		zoom = true;
	}	

	public void setMouseIn( boolean b ){
		mouseIn = b ;
	}
	
	public boolean isMouseIn(){
		return mouseIn;
	}
	
	protected void drawError( Graphics2D g2 ){
		
		g2.drawString("Invalid data.", this.getWidth()/2, this.getHeight()/2);

	}

	protected abstract void setDimensions();

	protected abstract void drawSchematics(Graphics2D g2);
	
	protected abstract void drawContours(Graphics2D g2);

	protected abstract void drawPoints(Graphics2D g);

	protected void drawDimensions(Graphics2D g2){

		//// System.out.println("Dimensions array contains " + dimensions.size() + " elements.");

		g2.setColor(Color.BLUE);
		for (Measure m: dimensions){
			if ( m.aboveZoomLevel( zoomLevel )){
				m.draw( g2 );
			}
		}
		g2.setColor(Color.BLACK);
	}


	protected void drawPoint(Graphics2D g, Geometry.W pt){
		double x = jd.getPoint( pt ).getX();
		double y = jd.getPoint( pt ).getY();
		AffineTransform savedAft = g.getTransform();
		g.translate( x, y );
		g.scale(1D, -1D);
		g.translate( -x, -y);
		g.fill(new Ellipse2D.Double(x-1D, y-1D,2D,2D));
		g.drawString( pt.getLabel(), (float) x+3F, (float) y+3f);
		g.setTransform( savedAft );
	}

	protected void drawString( Graphics2D g, String s, double x, double y){
		
		AffineTransform savedAft = g.getTransform();
		g.translate( x, y );
		g.scale(1D, -1D);
		g.translate( -x, -y);
		g.drawString( s, (float) x+3F, (float) y+3f);
		g.setTransform( savedAft );
	}


	private class LeftScrollActionHandler extends AbstractAction {

		ViewPane vp ;

		public LeftScrollActionHandler(ViewPane p){
			vp = p;
		}

		public void actionPerformed( ActionEvent e){
			
			// System.out.println("Action:" + e.getActionCommand());
			vp.scroll(-1, 0);
			vp.repaint();
		}
	}

	private class RightScrollActionHandler extends AbstractAction {

		ViewPane vp ;

		public RightScrollActionHandler(ViewPane p){
			vp = p;
		}

		public void actionPerformed( ActionEvent e){
			
			// System.out.println("Action:" + e.getActionCommand());
			vp.scroll(1, 0);
			vp.repaint();
		}
	}

	private class UpScrollActionHandler extends AbstractAction {

		ViewPane vp ;

		public UpScrollActionHandler(ViewPane p){
			vp = p;
		}

		public void actionPerformed( ActionEvent e){
			
			// System.out.println("Action:" + e.getActionCommand());
			vp.scroll(0, -1);
			vp.repaint();
		}
	}
	private class DownScrollActionHandler extends AbstractAction {

		ViewPane vp ;

		public DownScrollActionHandler(ViewPane p){
			vp = p;
		}

		public void actionPerformed( ActionEvent e){
			
			// System.out.println("Action:" + e.getActionCommand());
			vp.scroll(0, 1);
			vp.repaint();
		}
	}

}

