import java.io.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.awt.PdfGraphics2D;


public final class RearViewPane extends ViewPane {


	/*
	 */
	public RearViewPane(Geometry j){

		super( j );
		
		scale = 1.0D;

	}

	@Override
	public String toString(){
		return "Rear view pane";
	}

	@Override
	public void setDimensions(){
		dimensions.add( new Angle.Builder(
				jd,new Point2D.Double(jd.getPoint(Geometry.W.rJl).getX(), jd.getPoint(Geometry.W.rFl).getY()), 
				jd.getPoint(Geometry.W.rFl),
				jd.getPoint(Geometry.W.rJl) 
				).build()) ;
		//dimensions.add( new Angle.Builder( jd, new Point2D.Double(
		//			-50D, jd.getPoint(Geometry.W.rFl).getY()), 
		//		jd.getPoint(Geometry.W.rFl),
		//		jd.getPoint(Geometry.W.rI)).build()) ;
		//this.dimensions.add( new Cote.Builder( jd, Geometry.W.rH, Geometry.W.rK, Cote.Position.SOUTH ).offset(40).build());
	}

	@Override
	protected void drawSchematics(Graphics2D g2){
		
		g2.setColor(Color.RED);

		GeneralPath gp = new GeneralPath();
		gp.moveTo( jd.getPoint( Geometry.W.rF ).getX(), jd.getPoint( Geometry.W.rF ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rH ).getX(), jd.getPoint( Geometry.W.rG ).getY());
		g2.draw(gp);
		gp.reset();
		// Axle
		gp.moveTo( jd.getPoint( Geometry.W.rH ).getX(), jd.getPoint( Geometry.W.rH ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rH ).getX(), jd.getPoint( Geometry.W.rG ).getY());
		g2.draw(gp);
		gp.reset();
		// Wheel + tire
		gp.moveTo( jd.getPoint( Geometry.W.rH ).getX(), jd.getPoint( Geometry.W.rH ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rK ).getX(), jd.getPoint( Geometry.W.rK ).getY());
		g2.draw(gp);
		gp.reset();

		Arc2D arc = new Arc2D.Double(
				jd.getPoint( Geometry.W.rK ).getX()-jd.getRawParameter( Geometry.Prm.TW )/2.0D,
				jd.getPoint( Geometry.W.rK ).getY()-jd.getRawParameter( Geometry.Prm.TW )/2.0D, 
				jd.getRawParameter( Geometry.Prm.TW ), jd.getRawParameter( Geometry.Prm.TW ),
				0, 360, 0);
		g2.draw( arc );

		g2.setColor(Color.BLACK);
	}
	

	@Override
	protected void drawContours(Graphics2D g2){

		g2.setColor(Color.BLACK);

		GeneralPath gp = new GeneralPath();

		gp.moveTo( jd.getPoint( Geometry.W.rB ).getX(), jd.getPoint( Geometry.W.rB ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rC ).getX(), jd.getPoint( Geometry.W.rC ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rD ).getX(), jd.getPoint( Geometry.W.rD ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rE ).getX(), jd.getPoint( Geometry.W.rE ).getY());
		gp.closePath();	
		g2.draw(gp);
		gp.reset();

		//for (int i=0; i<DASH_PATTERN_COUNT_2; i++) dp1[i]=DASH_PATTERN_2[i]*.5 ;
		//cairo_set_dash(c, dp1, DASH_PATTERN_COUNT_2, 0);
		//g2.draw(gp);gp.reset();

		// Straight chainstays
		if (! jd.hasCurvedChainstay()){
			gp.moveTo( jd.getPoint( Geometry.W.rFr ).getX(), jd.getPoint( Geometry.W.rFr ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rGr ).getX(), jd.getPoint( Geometry.W.rGr ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rGl ).getX(), jd.getPoint( Geometry.W.rGl ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rFl ).getX(), jd.getPoint( Geometry.W.rFl ).getY());
			g2.draw(gp);
			gp.reset();
		//cairo_restore(c);


		//cairo_set_source_rgb(c,1,0,0);

		} else {
			// Curved chainstays
			gp.moveTo( jd.getPoint( Geometry.W.rFl ).getX(), jd.getPoint( Geometry.W.rFl ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.B1 ).getX(), jd.getPoint( Geometry.W.B1 ).getY());
			gp.curveTo(jd.getPoint( Geometry.W.B2 ).getX(), jd.getPoint( Geometry.W.B2 ).getY(),
					jd.getPoint( Geometry.W.B3 ).getX(), jd.getPoint( Geometry.W.B3 ).getY(),
					jd.getPoint( Geometry.W.B4 ).getX(), jd.getPoint( Geometry.W.B4 ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rGl ).getX(), jd.getPoint( Geometry.W.rGl ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rGr ).getX(), jd.getPoint( Geometry.W.rGr ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.C4 ).getX(), jd.getPoint( Geometry.W.C4 ).getY());

			gp.curveTo( jd.getPoint( Geometry.W.C3 ).getX(), jd.getPoint( Geometry.W.C3 ).getY(),
					jd.getPoint( Geometry.W.C2 ).getX(), jd.getPoint( Geometry.W.C2 ).getY(),
					jd.getPoint( Geometry.W.C1 ).getX(), jd.getPoint( Geometry.W.C1 ).getY());
			gp.lineTo( jd.getPoint( Geometry.W.rFr ).getX(), jd.getPoint( Geometry.W.rFr ).getY());

			g2.draw(gp);
			gp.reset();
		}

		//cairo_set_source_rgb(c, 0,0,0);
		// Crank
		gp.moveTo( jd.getPoint( Geometry.W.A ).getX(), jd.getPoint( Geometry.W.A ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rLl ).getX(), jd.getPoint( Geometry.W.rLl ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rLr ).getX(), jd.getPoint( Geometry.W.rLr ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rMr ).getX(), jd.getPoint( Geometry.W.rMr ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rMl ).getX(), jd.getPoint( Geometry.W.rMl ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.rLl ).getX(), jd.getPoint( Geometry.W.rLl ).getY());
		g2.draw(gp);
		gp.reset();
	}

	/*@Override
	protected void drawDimensions(Graphics2D g2){
		;
	}*/

	@Override
	protected void drawPoints(Graphics2D g){
		
		g.setColor(Color.GRAY);
		
		for  (Geometry.W w: EnumSet.complementOf(EnumSet.range(Geometry.W.A1, Geometry.W.G4))){
			if (w.getView() == Geometry.View.REAR) drawPoint( g, w);
		}

		g.setColor(Color.BLACK);
	}



	public void toPdf() throws IOException, DocumentException {
		// System.out.println("Export to PDF");

		com.itextpdf.text.Rectangle rect = PageSize.A4;
		//double widthOffset = .8D;
		//double heightOffset = .8D;

		int pageCount = 1;
		// first page
		double widthOffset = .5;
		double heightOffset = .05;
		double fold = 1; 



		// System.out.println("PDF height: " + rect.getHeight() + " - PDF length: " + rect.getWidth() );

		while (pageCount < 3){

			Document document = new Document( rect );

			if (pageCount == 2){
				widthOffset = .5;
				heightOffset = -.5;
			}

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( "rear_view_" + pageCount + ".pdf" ));

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

			pageCount++;
		}


	}
}

