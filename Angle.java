import java.awt.geom.*;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

public class Angle extends Measure {

	// Parameters

	private Geometry data;
	private Point2D start;
	private Point2D center;
	private Point2D end;

	// Computed values
	private Point2D arcStart;
	private Point2D arcEnd;
	private DecimalFormat df;
	private double startAngle ;

	public static class Builder {
		
		// Required parameters
		private Point2D start;
		private Point2D center;
		private Point2D end;
		private Geometry data;
		
		// Optional parameters
		private boolean isVisible = true;
		private double offset = 50D;
		private int minZoomLevel = ViewPane.ZOOM_MIN;

		Builder(Geometry jd, Geometry.W s, Geometry.W c, Geometry.W e ){
			data = jd;
			start = data.getPoint( s ); // angle limits in natural order (counterclockwise)
			center = data.getPoint( c );
			end = data.getPoint( e );
		}

		Builder(Geometry jd, Point2D s, Point2D c, Point2D e ){
			data = jd;
			start = s ; // angle limits in natural order (counterclockwise)
			center = c ;
			end = e ;
		}

		public Builder isVisible( boolean b){ isVisible = b ; return this; }

		public Builder offset( double o){ offset = o; return this; }

		public Builder minZoomLevel( int z){ minZoomLevel = z ; return this ;}

		public Angle build(){
			return new Angle(this);
		}
	}

	Angle ( Builder builder ){
		data = builder.data;
		start = builder.start;
		center = builder.center;
		end = builder.end;
		offset = builder.offset;
		isVisible = builder.isVisible;
		minZoomLevel = builder.minZoomLevel;
		
		df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		df.applyPattern("#.00");

		update();
	}


	
	@Override
	public void update(){

		double a = start.distance( end );
		double b = center.distance( start );
		double c = center.distance( end );

		value = 2.0D * Math.atan( Math.sqrt(( a*a - (b - c)*(b - c)) / ((b + c)*(b + c) - a * a )));


		double yDiff = start.getY()-center.getY();
		double xDiff = start.getX()-center.getX();
		startAngle = Math.atan( Math.abs( yDiff / xDiff ));
		// Varies according to the position of the label relative to the arc
		double labelOffset = 10D;

		if (yDiff >= 0){
			// NW quadrant
			if (xDiff < 0){
			 	startAngle = Math.PI - startAngle;
				labelOffset = 30D;
			}
			// No change for NE quadrant
		} else {
			// SW quadrant
			if (xDiff < 0){
				startAngle -= Math.PI;
				labelOffset = 30D;
			} else { // SE quadrant
				startAngle = -startAngle;
			}
		}

		double halfExtent = startAngle + value / 2.0D;
		labelLocation = new Point2D.Double(
			center.getX() +  Math.cos( halfExtent ) * (offset + labelOffset),
			center.getY() +  Math.sin( halfExtent ) * (offset + labelOffset));

		// System.out.println("startAngle = " + degrees( startAngle ));
		
	}

	@Override
	public void draw( Graphics2D g){

			
		// System.out.println("Arc2D.Double(" + center.getX() + ", " + center.getY() + ", " +  offset + ", " + degrees(startAngle) + ", " + degrees(value) );
		Arc2D arc = new Arc2D.Double(	center.getX()-offset, center.getY()-offset,
						offset*2.0D, offset*2.0D,
						degrees(-startAngle), 
						degrees(-value),
						0);
		g.draw( arc );


		double x = labelLocation.getX();
		double y = labelLocation.getY();
		AffineTransform savedAft = g.getTransform();
		g.translate( x, y);
		g.scale(1D, -1D);
		//g.translate( -x, -y);
		g.drawString( df.format(degrees(value)), 0F, 0F);
		g.setTransform( savedAft );
		
	}

	private double degrees(double rad){
		return rad / Math.PI * 180.0D;
	}

}

