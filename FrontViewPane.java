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


public final class FrontViewPane extends ViewPane {


	/*
	 */
	public FrontViewPane(Geometry j){

		super( j );

	}

	@Override
	public String toString(){
		return "Side view pane";
	}

	@Override
	protected void setDimensions(){
		System.out.println("Adding dimensions...");
	}


	@Override
	protected void drawSchematics(Graphics2D g2){
	



	}
	

	@Override
	protected void drawContours(Graphics2D g2){
		
	

	}

	
	private void drawFrontTriangle(Graphics2D g2, Geometry.FrameType frameType){

	}




	@Override
	protected void drawPoints(Graphics2D g){
		g.setColor(Color.GRAY);
		for (Geometry.W w: EnumSet.complementOf(EnumSet.range(Geometry.W.A1, Geometry.W.H6))){
			if (w.getView() == Geometry.View.SIDE) drawPoint( g, w );
		}
		g.setColor(Color.BLACK);
	}

}

