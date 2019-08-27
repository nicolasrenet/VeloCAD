import java.awt.geom.*;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

public class Cote extends Measure {

	static enum Position { NORTH, SOUTH, WEST, EAST }

	// Parameters
	private Geometry data;
	private Point2D start ;
	private Point2D end ;
	private Position position ;
	private double arrowHeadLength;

	// Computed values
	private Point2D arrowStart;
	private Point2D arrowEnd;
	private Point2D arrowHead1 ;
	private Point2D arrowHead2 ;
	private Point2D arrowHead3 ;
	private Point2D arrowHead4 ;
	private DecimalFormat df ;

	private static final double ARROW_HEAD_ANGLE = Math.PI /6.0D;

	public static class Builder {
		
		// Required parameters
		private Point2D start;
		private Point2D end ;
		private Position position ;
		private Geometry data ;

		// Optional parameters
		private boolean isVisible=true;
		private double offset = 50D;
		private double arrowHeadLength = 10D;
		private int minZoomLevel = ViewPane.ZOOM_MIN;
		
		Builder(Geometry jd, Geometry.W s, Geometry.W e, Position pos){
			data = jd;
			start = data.getPoint( s );
			end = data.getPoint( e );
			position = pos ;
		}

		public Builder isVisible( boolean b){ isVisible = b ; return this; } 

		public Builder offset( double o){ offset = o; return this; }

		public Builder arrowHeadLength( double l){ arrowHeadLength = l ; return this; }

		public Builder minZoomLevel( int z){ minZoomLevel = z ; return this ;}

		public Cote build(){
			return new Cote(this);
		}
	}

	Cote( Builder builder ){

		start = builder.start;
		end = builder.end;
		data = builder.data;
		position = builder.position;
		offset = builder.offset;
		isVisible = builder.isVisible;
		arrowHeadLength = builder.arrowHeadLength ;
		minZoomLevel = builder.minZoomLevel;

		df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		df.applyPattern("#.0");

		update();
	
	}

	public void update(){
		System.out.println("Cote.udpate()");

		value = start.distance( end );
		double deltaX =  (offset / value) * Math.abs(end.getY() - start.getY());
		double deltaY =  (offset / value) * Math.abs(end.getX() - start.getX());
		double sign = Math.signum( end.getY() - start.getY());
		double labelOffset = 20D;
		if (position == Position.NORTH){
			arrowStart = new Point2D.Double ( start.getX() - sign * deltaX, start.getY() + deltaY );
			System.out.println( start.getX() + " - " + sign + " * " + deltaX );
			arrowEnd = new Point2D.Double ( end.getX() - sign * deltaX, end.getY() + deltaY );
		} else {
			arrowStart = new Point2D.Double ( start.getX() + sign * deltaX, start.getY() - deltaY );
			System.out.println( start.getX() + " + " + sign + " * " + deltaX );
			arrowEnd = new Point2D.Double ( end.getX() + sign * deltaX, end.getY() - deltaY );
		}

		double arrowAngle = Math.atan( Math.abs( (end.getY()-start.getY())/(end.getX()-start.getX()) ) );		
		double deltaX1 = Math.cos( arrowAngle + ARROW_HEAD_ANGLE ) * arrowHeadLength;
		double deltaY1 =  Math.sin( arrowAngle + ARROW_HEAD_ANGLE ) * arrowHeadLength;
		double deltaX2 = Math.cos( arrowAngle - ARROW_HEAD_ANGLE ) * arrowHeadLength;
		double deltaY2 = Math.sin( arrowAngle - ARROW_HEAD_ANGLE ) * arrowHeadLength;

		if (sign > 0){
			arrowHead1 = new Point2D.Double( arrowStart.getX() + deltaX1, arrowStart.getY() + deltaY1 );
			arrowHead2 = new Point2D.Double( arrowStart.getX() + deltaX2, arrowStart.getY() + deltaY2 );
			arrowHead3 = new Point2D.Double( arrowEnd.getX() - deltaX2, arrowEnd.getY() - deltaY2 );
			arrowHead4 = new Point2D.Double( arrowEnd.getX() - deltaX1, arrowEnd.getY() - deltaY1 );
			switch( position ){
				case NORTH:
					labelLocation = new Point2D.Double( 
					(arrowEnd.getX()+arrowStart.getX())/2.0D - Math.sin( arrowAngle ) * labelOffset,
					(arrowEnd.getY()+arrowStart.getY())/2.0D + Math.cos( arrowAngle ) * labelOffset);
					break;
				case SOUTH:
					labelLocation = new Point2D.Double( 
					(arrowEnd.getX()+arrowStart.getX())/2.0D + Math.sin( arrowAngle ) * labelOffset,
					(arrowEnd.getY()+arrowStart.getY())/2.0D - Math.cos( arrowAngle ) * labelOffset);
					break;
				default: ;
			}
		} else {
			arrowHead1 = new Point2D.Double( arrowStart.getX() + deltaX1, arrowStart.getY() - deltaY1 );
			arrowHead2 = new Point2D.Double( arrowStart.getX() + deltaX2, arrowStart.getY() - deltaY2 );
			arrowHead3 = new Point2D.Double( arrowEnd.getX() - deltaX2, arrowEnd.getY() + deltaY2 );
			arrowHead4 = new Point2D.Double( arrowEnd.getX() - deltaX1, arrowEnd.getY() + deltaY1 );
			switch( position ){
				case NORTH:
					labelLocation = new Point2D.Double( 
					(arrowEnd.getX()+arrowStart.getX())/2.0D + Math.sin( arrowAngle ) * labelOffset,
					(arrowEnd.getY()+arrowStart.getY())/2.0D + Math.cos( arrowAngle ) * labelOffset);
					break;
				case SOUTH:
					labelLocation = new Point2D.Double( 
					(arrowEnd.getX()+arrowStart.getX())/2.0D - Math.sin( arrowAngle ) * labelOffset,
					(arrowEnd.getY()+arrowStart.getY())/2.0D - Math.cos( arrowAngle ) * labelOffset);
					break;
				default: ;
			}
		}

	}
	
	public void draw( Graphics2D g){

		Line2D line = new Line2D.Double( arrowStart.getX(), arrowStart.getY(), arrowEnd.getX(), arrowEnd.getY() );		
		g.draw( line );

		GeneralPath gp = new GeneralPath();
		gp.moveTo(arrowHead1.getX(), arrowHead1.getY());
		gp.lineTo(arrowStart.getX(), arrowStart.getY());
		gp.lineTo(arrowHead2.getX(), arrowHead2.getY());
		g.draw( gp );
		gp.reset();

		gp.moveTo(arrowHead3.getX(), arrowHead3.getY());
		gp.lineTo(arrowEnd.getX(), arrowEnd.getY());
		gp.lineTo(arrowHead4.getX(), arrowHead4.getY());
		g.draw( gp );
		gp.reset();

		double x = labelLocation.getX();
		double y = labelLocation.getY();
		AffineTransform savedAft = g.getTransform();
		g.translate( x, y);
		g.scale(1D, -1D);
		//g.translate( -x, -y);
		g.drawString( df.format(value), 0F, 0F);
		g.setTransform( savedAft );

	}



}
