import java.awt.geom.*;
import java.awt.Graphics2D;

public abstract class Measure {

	protected double value;
	protected boolean isVisible;
	protected double offset;
	protected int minZoomLevel;
	protected Point2D labelLocation;
		
	public void setVisible( boolean b){ isVisible = b; }

	public boolean aboveZoomLevel( int zl){ return zl >= minZoomLevel; }

	public abstract void draw( Graphics2D g);

	public abstract void update();
}
