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


public final class SideViewPane extends ViewPane {


	/*
	 */
	public SideViewPane(Geometry j){

		super( j );

	}

	@Override
	public String toString(){
		return "Side view pane";
	}

	@Override
	protected void setDimensions(){
		System.out.println("Adding dimensions...");
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.A, Geometry.W.D, Cote.Position.SOUTH ).offset(40).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.E, Geometry.W.B, Cote.Position.SOUTH ).offset(30).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.E, Geometry.W.A, Cote.Position.SOUTH ).offset(40D).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.Gp, Geometry.W.Kp, Cote.Position.SOUTH).offset(20D).minZoomLevel(1).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.Aom, Geometry.W.Ai, Cote.Position.SOUTH).offset(20D).minZoomLevel(1).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.Tce, Geometry.W.Tcw, Cote.Position.SOUTH).offset(20D).minZoomLevel(0).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.C, Geometry.W.D, Cote.Position.NORTH).offset(30D).minZoomLevel(0).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.BodySeat, Geometry.W.BodyShoulder, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.BodyShoulder, Geometry.W.Stuf, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.A, Geometry.W.E, Geometry.W.Bs).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.A, Geometry.W.E, Geometry.W.Bs).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.B, Geometry.W.A, Geometry.W.E).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.A, Geometry.W.Bs, Geometry.W.C).build()) ;
		//this.dimensions.add( new Angle.Builder( jd, Geometry.W.J, Geometry.W.C, Geometry.W.D).offset(10D).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.C, Geometry.W.D, Geometry.W.A).offset(10D).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.D, Geometry.W.A, Geometry.W.B).offset(60D).build()) ;
		this.dimensions.add( new Angle.Builder( jd, Geometry.W.A, Geometry.W.J, Geometry.W.C).offset(60D).build()) ;
		//this.dimensions.add( new Cote.Builder( jd, Geometry.W.E, Geometry.W.C, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		//this.dimensions.add( new Cote.Builder( jd, Geometry.W.A, Geometry.W.C, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		//this.dimensions.add( new Cote.Builder( jd, Geometry.W.B, Geometry.W.D, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		//this.dimensions.add( new Cote.Builder( jd, Geometry.W.C, Geometry.W.D, Cote.Position.SOUTH).offset(0D).minZoomLevel(0).build());
		this.dimensions.add( new Cote.Builder( jd, Geometry.W.A, Geometry.W.Bs, Cote.Position.NORTH).offset(2D).minZoomLevel(0).build());
	}


	@Override
	protected void drawSchematics(Graphics2D g2){
	
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		//		RenderingHints.VALUE_ANTIALIAS_ON);
		GeneralPath gp = new GeneralPath();

		g2.setColor(Color.RED);

		/* Seat tube */
		gp.moveTo(jd.getPoint( Geometry.W.A ).getX(),jd.getPoint( Geometry.W.A ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Bs ).getX(),jd.getPoint( Geometry.W.Bs ).getY());

		/* Top tube (actual) */
		gp.lineTo(jd.getPoint( Geometry.W.C ).getX(),jd.getPoint( Geometry.W.C ).getY());

		/* Head Tube */
		gp.lineTo( jd.getPoint( Geometry.W.D ).getX(), jd.getPoint( Geometry.W.D ).getY());

		/* Down tube */
		gp.lineTo( jd.getPoint( Geometry.W.A ).getX(), jd.getPoint( Geometry.W.A ).getY());

		/* Chain Stay */
		gp.lineTo( jd.getPoint( Geometry.W.E ).getX(), jd.getPoint( Geometry.W.E ).getY());

		/* Seat Stay */
		gp.lineTo( jd.getPoint( Geometry.W.Bs ).getX(), jd.getPoint( Geometry.W.Bs ).getY());
		g2.draw(gp);
		gp.reset();
		

		/* Seat stay (virtual) */
		gp.moveTo( jd.getPoint( Geometry.W.E ).getX(), jd.getPoint( Geometry.W.E ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.B ).getX(), jd.getPoint( Geometry.W.B ).getY());
		/* Top tube (virtual) */
		gp.lineTo( jd.getPoint( Geometry.W.C ).getX(), jd.getPoint( Geometry.W.C ).getY());
		gp.moveTo( jd.getPoint( Geometry.W.Bs ).getX(), jd.getPoint( Geometry.W.Bs ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.B ).getX(), jd.getPoint( Geometry.W.B ).getY());

		g2.setStroke(dashed);
		g2.draw(gp);
		gp.reset();
		g2.setStroke(defaultStroke);



		/* Fork blades */
		// center line
		gp.moveTo( jd.getPoint( Geometry.W.F ).getX(), jd.getPoint( Geometry.W.F ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.K ).getX(), jd.getPoint( Geometry.W.K ).getY());
		g2.draw(gp);
		gp.reset();
		QuadCurve2D qc = new QuadCurve2D.Double(jd.getPoint( Geometry.W.K ).getX(), jd.getPoint( Geometry.W.K ).getY(),
								jd.getPoint( Geometry.W.KG ).getX(), jd.getPoint( Geometry.W.KG ).getY(),
								jd.getPoint( Geometry.W.G ).getX(),jd.getPoint( Geometry.W.G ).getY()); 
			
		g2.draw(qc);
		

		/* Wheels */
		Arc2D arc = new Arc2D.Double(jd.getPoint( Geometry.W.E ).getX()- jd.getRawParameter( Geometry.Prm.WR ), jd.getPoint( Geometry.W.E ).getY()- jd.getRawParameter( Geometry.Prm.WR ),
						 jd.getRawParameter( Geometry.Prm.WR )*2.0D, jd.getRawParameter( Geometry.Prm.WR )*2.0D, 0, 360, 0);
		g2.draw(arc);
		arc.setArc(jd.getPoint( Geometry.W.G ).getX()- jd.getRawParameter( Geometry.Prm.WR ), jd.getPoint( Geometry.W.G ).getY()- jd.getRawParameter( Geometry.Prm.WR ),
						 jd.getRawParameter( Geometry.Prm.WR )*2.0D, jd.getRawParameter( Geometry.Prm.WR )*2.0D, 0, 360, 0);
		g2.draw(arc);

		/* Crank arm (bottom) */
		gp.moveTo( jd.getPoint( Geometry.W.A ).getX(), jd.getPoint( Geometry.W.A ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.A ).getX() , jd.getPoint( Geometry.W.A ).getY() - jd.getRawParameter( Geometry.Prm.CL ));
		g2.draw(gp);
		gp.reset();


		//cairo_save(c);

		/* Crank arm (closest to front wheel) */
		gp.moveTo( jd.getPoint( Geometry.W.A ).getX(), jd.getPoint( Geometry.W.A ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.H ).getX(), jd.getPoint( Geometry.W.H ).getY());
		g2.setStroke(dashed);
		g2.draw(gp);
		gp.reset();
		

		/* Fork line (virtual) */
		gp.moveTo( jd.getPoint( Geometry.W.F ).getX(), jd.getPoint( Geometry.W.F ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.G ).getX(), jd.getPoint( Geometry.W.G ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Gp ).getX(), jd.getPoint( Geometry.W.Gp ).getY());
		gp.moveTo( jd.getPoint( Geometry.W.K ).getX(), jd.getPoint( Geometry.W.K ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Kp ).getX(), jd.getPoint( Geometry.W.Kp ).getY());

		/* Cross member */
		gp.moveTo( jd.getPoint( Geometry.W.E ).getX(), jd.getPoint( Geometry.W.E ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.J ).getX(), jd.getPoint( Geometry.W.J ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.C ).getX(), jd.getPoint( Geometry.W.C ).getY());

		gp.moveTo( jd.getPoint( Geometry.W.K ).getX(), jd.getPoint( Geometry.W.K ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.L ).getX(), jd.getPoint( Geometry.W.L ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.G ).getX(), jd.getPoint( Geometry.W.G ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Gm ).getX(), jd.getPoint( Geometry.W.Gm ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.L ).getX(), jd.getPoint( Geometry.W.L ).getY());
		g2.draw(gp);
		gp.reset();

		/* Stem */
		gp.moveTo( jd.getPoint( Geometry.W.C ).getX(), jd.getPoint( Geometry.W.C ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Stmr ).getX(), jd.getPoint( Geometry.W.Stmr ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Stmf ).getX(), jd.getPoint( Geometry.W.Stmf ).getY());
		g2.draw(gp);
		gp.reset();
		

		g2.setStroke(defaultStroke);

		g2.setColor(Color.BLACK);

	}
	

	@Override
	protected void drawContours(Graphics2D g2){
		
	
		Geometry.FrameType frameType = jd.getFrameType();

		drawFrontTriangle( g2, frameType );
		drawHeadset( g2 );
		drawFenders( g2, frameType );
		drawFork( g2 );
		drawStem( g2 );
		drawRearTriangle( g2, frameType );
		drawCrank( g2 );

	}

	
	private void drawFrontTriangle(Graphics2D g2, Geometry.FrameType frameType){

		GeneralPath gp = new GeneralPath();
		g2.setColor(Color.BLACK);
		g2.setStroke(defaultStroke);

		// Bottom bracket
		final double bb_thickness = 2.1D;
		Arc2D arc = new Arc2D.Double(jd.getPoint( Geometry.W.A ).getX()- jd.getRawParameter( Geometry.Prm.BBOD )/2.0D, jd.getPoint( Geometry.W.A ).getY()- jd.getRawParameter( Geometry.Prm.BBOD )/2.0D,
			 jd.getRawParameter( Geometry.Prm.BBOD ), jd.getRawParameter( Geometry.Prm.BBOD ), 0, 360, 0);
		g2.draw(arc);
		arc.setArc(jd.getPoint( Geometry.W.A ).getX()- jd.getRawParameter( Geometry.Prm.BBOD )/2.0D + jd.getRawParameter( Geometry.Prm.BBG ) , jd.getPoint( Geometry.W.A ).getY()- jd.getRawParameter( Geometry.Prm.BBOD )/2.0D + jd.getRawParameter( Geometry.Prm.BBG ),
			 jd.getRawParameter( Geometry.Prm.BBOD ) - jd.getRawParameter( Geometry.Prm.BBG )*2 , jd.getRawParameter( Geometry.Prm.BBOD ) - jd.getRawParameter( Geometry.Prm.BBG )*2, 0, 360, 0);
		g2.draw(arc);

		// Front triangle: inner lines
		
		gp.moveTo(jd.getPoint( Geometry.W.Ai ).getX(), jd.getPoint( Geometry.W.Ai ).getY());
		if (frameType == Geometry.FrameType.MIXTE){
			gp.lineTo(jd.getPoint( Geometry.W.Ji ).getX(), jd.getPoint( Geometry.W.Ji ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Cmi ).getX(), jd.getPoint( Geometry.W.Cmi ).getY());
		} else {
			gp.lineTo(jd.getPoint( Geometry.W.Bi ).getX(), jd.getPoint( Geometry.W.Bi ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Ci ).getX(), jd.getPoint( Geometry.W.Ci ).getY());
		}
		gp.lineTo(jd.getPoint( Geometry.W.Di ).getX(), jd.getPoint( Geometry.W.Di ).getY());
		gp.closePath();
		

		// Front triangle: outer lines
		gp.moveTo(jd.getPoint( Geometry.W.Bol ).getX(), jd.getPoint( Geometry.W.Bol ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Bspl ).getX(), jd.getPoint( Geometry.W.Bspl ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Bspr ).getX(), jd.getPoint( Geometry.W.Bspr ).getY());
		//gp.lineTo(jd.getPoint( Geometry.W.Bsu ).getX(), jd.getPoint( Geometry.W.Bsu ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Bor ).getX(), jd.getPoint( Geometry.W.Bor ).getY());
		if (frameType == Geometry.FrameType.MIXTE){
			gp.lineTo(jd.getPoint( Geometry.W.Jo ).getX(), jd.getPoint( Geometry.W.Jo ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Cmo ).getX(), jd.getPoint( Geometry.W.Cmo ).getY());
		}
		gp.lineTo(jd.getPoint( Geometry.W.Col ).getX(), jd.getPoint( Geometry.W.Col ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.ColPrime ).getX(), jd.getPoint( Geometry.W.ColPrime ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.CorPrime ).getX(), jd.getPoint( Geometry.W.CorPrime ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Sf ).getX(), jd.getPoint( Geometry.W.Sf ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Sb ).getX(), jd.getPoint( Geometry.W.Sb ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Dol ).getX(), jd.getPoint( Geometry.W.Dol ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Aor ).getX(), jd.getPoint( Geometry.W.Aor ).getY());

		// Front triangle: tube junctions
		
		if (frameType == Geometry.FrameType.MIXTE){
			gp.moveTo(jd.getPoint( Geometry.W.Cmi ).getX(), jd.getPoint( Geometry.W.Cmi ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.C ).getX(), jd.getPoint( Geometry.W.C ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Cmo ).getX(), jd.getPoint( Geometry.W.Cmo ).getY());

			gp.moveTo(jd.getPoint( Geometry.W.Ji ).getX(), jd.getPoint( Geometry.W.Ji ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.J ).getX(), jd.getPoint( Geometry.W.J ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Jo ).getX(), jd.getPoint( Geometry.W.Jo ).getY());
		} else {
			gp.moveTo(jd.getPoint( Geometry.W.Ci ).getX(), jd.getPoint( Geometry.W.Ci ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Co ).getX(), jd.getPoint( Geometry.W.Co ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Col ).getX(), jd.getPoint( Geometry.W.Col ).getY());

			gp.moveTo(jd.getPoint( Geometry.W.Bi ).getX(), jd.getPoint( Geometry.W.Bi ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Bo ).getX(), jd.getPoint( Geometry.W.Bo ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Bsor ).getX(), jd.getPoint( Geometry.W.Bsor ).getY());
		}

		gp.moveTo(jd.getPoint( Geometry.W.Di ).getX(), jd.getPoint( Geometry.W.Di ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Do ).getX(), jd.getPoint( Geometry.W.Do ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Dol ).getX(), jd.getPoint( Geometry.W.Dol ).getY());


		gp.moveTo(jd.getPoint( Geometry.W.Ai ).getX(), jd.getPoint( Geometry.W.Ai ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Aom ).getX(), jd.getPoint( Geometry.W.Aom ).getY());

		g2.draw( gp );
	}

	private void drawHeadset(Graphics2D g2){

		GeneralPath gp = new GeneralPath();

		// Headtube edge
		gp.moveTo(jd.getPoint( Geometry.W.Sb ).getX(), jd.getPoint( Geometry.W.Sb ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Sf ).getX(), jd.getPoint( Geometry.W.Sf ).getY());

		// Headset edge
		gp.moveTo(jd.getPoint( Geometry.W.Tb ).getX(), jd.getPoint( Geometry.W.Tb ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Tf ).getX(), jd.getPoint( Geometry.W.Tf ).getY());

		gp.moveTo(jd.getPoint( Geometry.W.Bol ).getX(), jd.getPoint( Geometry.W.Bol ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Aol ).getX(), jd.getPoint( Geometry.W.Aol ).getY());
		g2.draw(gp);
		gp.reset();

		// Headset
		CubicCurve2D crv = new CubicCurve2D.Double(
			jd.getPoint( Geometry.W.Sf ).getX(), jd.getPoint( Geometry.W.Sf ).getY(),
			jd.getPoint( Geometry.W.F1 ).getX(), jd.getPoint( Geometry.W.F1 ).getY(),
			jd.getPoint( Geometry.W.F2 ).getX(), jd.getPoint( Geometry.W.F2 ).getY(),
			jd.getPoint( Geometry.W.Tf ).getX(), jd.getPoint( Geometry.W.Tf ).getY());
		g2.draw(crv);
		crv.setCurve(
			jd.getPoint( Geometry.W.Sb ).getX(), jd.getPoint( Geometry.W.Sb ).getY(),
			jd.getPoint( Geometry.W.F4 ).getX(), jd.getPoint( Geometry.W.F4 ).getY(),
			jd.getPoint( Geometry.W.F3 ).getX(), jd.getPoint( Geometry.W.F3 ).getY(),
			jd.getPoint( Geometry.W.Tb ).getX(), jd.getPoint( Geometry.W.Tb ).getY());
		g2.draw(crv);

	}

	private void drawFork(Graphics2D g2){

		GeneralPath gp = new GeneralPath();

		// Fork crown lug
		gp.moveTo(jd.getPoint( Geometry.W.Tb ).getX(), jd.getPoint( Geometry.W.Tb ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Ub ).getX(), jd.getPoint( Geometry.W.Ub ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Vb ).getX(), jd.getPoint( Geometry.W.Vb ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.G1 ).getX(), jd.getPoint( Geometry.W.G1 ).getY(),
			jd.getPoint( Geometry.W.G2 ).getX(), jd.getPoint( Geometry.W.G2 ).getY(),
			jd.getPoint( Geometry.W.G0 ).getX(), jd.getPoint( Geometry.W.G0 ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.G3 ).getX(), jd.getPoint( Geometry.W.G3 ).getY(),
			jd.getPoint( Geometry.W.G4 ).getX(), jd.getPoint( Geometry.W.G4 ).getY(),
			jd.getPoint( Geometry.W.Vf ).getX(), jd.getPoint( Geometry.W.Vf ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Uf ).getX(), jd.getPoint( Geometry.W.Uf ).getY());
		gp.lineTo( jd.getPoint( Geometry.W.Tf ).getX(), jd.getPoint( Geometry.W.Tf ).getY());
		g2.draw(gp);
		gp.reset();
		
		// Fork bend upper line
		gp.moveTo(jd.getPoint( Geometry.W.Vf ).getX(), jd.getPoint( Geometry.W.Vf ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.M ).getX(), jd.getPoint( Geometry.W.M ).getY());
		gp.curveTo( 
			jd.getPoint( Geometry.W.A1 ).getX(), jd.getPoint( Geometry.W.A1 ).getY(),
			jd.getPoint( Geometry.W.A2 ).getX(), jd.getPoint( Geometry.W.A2 ).getY(),
			jd.getPoint( Geometry.W.Gr ).getX(), jd.getPoint( Geometry.W.Gr ).getY());
		// Fork tip
		gp.lineTo(jd.getPoint( Geometry.W.Gl ).getX(), jd.getPoint( Geometry.W.Gl ).getY());

		// Fork bend lower line
		gp.curveTo( 
			jd.getPoint( Geometry.W.A4 ).getX(), jd.getPoint( Geometry.W.A4 ).getY(),
			jd.getPoint( Geometry.W.A3 ).getX(), jd.getPoint( Geometry.W.A3 ).getY(),
			jd.getPoint( Geometry.W.N ).getX(), jd.getPoint( Geometry.W.N ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Vb ).getX(), jd.getPoint( Geometry.W.Vb ).getY());

		// Fork tip
		gp.moveTo(jd.getPoint( Geometry.W.Gl ).getX(), jd.getPoint( Geometry.W.Gl ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Gr ).getX(), jd.getPoint( Geometry.W.Gr ).getY());
		g2.setColor( Color.WHITE);
		g2.fill( gp );
		g2.setColor( Color.BLACK);
		g2.draw( gp );


		/* hub axles */
		double front_axle_diameter = 9D;
		double rear_axle_diameter = 10D;
		Arc2D arc = new Arc2D.Double(jd.getPoint( Geometry.W.E ).getX()- rear_axle_diameter/2D, jd.getPoint( Geometry.W.E ).getY()- rear_axle_diameter / 2D, rear_axle_diameter, rear_axle_diameter, 0, 360, 0);
		g2.draw(arc);
		arc.setArc(jd.getPoint( Geometry.W.G ).getX() -front_axle_diameter/2D, jd.getPoint( Geometry.W.G ).getY()- front_axle_diameter / 2D, front_axle_diameter, front_axle_diameter, 0, 360, 0);
		g2.draw(arc);
	}

	private void drawFenders(Graphics2D g2, Geometry.FrameType frameType){

		GeneralPath gp = new GeneralPath();

		// Fenders
		double fenderOuterRadius = jd.getRawParameter( Geometry.Prm.WR ) + jd.getRawParameter( Geometry.Prm.FC );
		double fenderInnerRadius = fenderOuterRadius - jd.getRawParameter( Geometry.Prm.FH );
		double frontOuterStartAngle = Math.toDegrees( jd.getRawParameter( Geometry.Prm.FFSA )) ;	
		double frontOuterExtension = Math.toDegrees( jd.getRawParameter( Geometry.Prm.FFE )) ;	
		double taper = 3D;
		double frontInnerStartAngle = frontOuterExtension - frontOuterStartAngle - taper;
		double frontInnerExtension = frontOuterExtension - taper * 2D ;

		double rearOuterStartAngle = Math.toDegrees( jd.getRawParameter( Geometry.Prm.RFSA )) ;	
		double rearOuterExtension = Math.toDegrees( jd.getRawParameter( Geometry.Prm.RFE )) ;	
		double rearInnerStartAngle = rearOuterExtension - rearOuterStartAngle - taper;
		double rearInnerExtension = rearOuterExtension - taper * 2D ;

		// Front
		Arc2D arc1 = new Arc2D.Double(jd.getPoint( Geometry.W.G ).getX() - fenderOuterRadius, jd.getPoint( Geometry.W.G ).getY() - fenderOuterRadius,
				fenderOuterRadius*2D, fenderOuterRadius*2D, -frontOuterStartAngle, frontOuterExtension, 0);
		Arc2D arc2 = new Arc2D.Double(jd.getPoint( Geometry.W.G ).getX() - fenderInnerRadius, jd.getPoint( Geometry.W.G ).getY() - fenderInnerRadius,
				fenderInnerRadius*2D, fenderInnerRadius*2D, frontInnerStartAngle, -frontInnerExtension, 0);

		Path2D path = new Path2D.Double( arc1 );
		path.append( arc2, true );
		path.closePath();
		
		g2.setColor(Color.WHITE);
		g2.fill( path );
		g2.setColor(Color.BLACK);
		g2.draw( path );
		path.reset();

		// Rear
		arc1.setArc(jd.getPoint( Geometry.W.E ).getX() - fenderOuterRadius, jd.getPoint( Geometry.W.E ).getY() - fenderOuterRadius,
				fenderOuterRadius*2D, fenderOuterRadius*2D, -rearOuterStartAngle, rearOuterExtension, 0);
		arc2.setArc(jd.getPoint( Geometry.W.E ).getX() - fenderInnerRadius, jd.getPoint( Geometry.W.E ).getY() - fenderInnerRadius,
				fenderInnerRadius*2D, fenderInnerRadius*2D, rearInnerStartAngle, -rearInnerExtension, 0);


		path.append( arc1, false );
		path.append( arc2, true );
		path.closePath();
		
		g2.setColor(Color.WHITE);
		g2.fill( path );
		g2.setColor(Color.BLACK);
		g2.draw( path );
	}


	private void drawRearTriangle(Graphics2D g2, Geometry.FrameType frameType){

		GeneralPath gp = new GeneralPath();

		// Chainstays
		gp.moveTo(jd.getPoint( Geometry.W.Acu ).getX(), jd.getPoint( Geometry.W.Acu ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Ecu ).getX(), jd.getPoint( Geometry.W.Ecu ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Ecl ).getX(), jd.getPoint( Geometry.W.Ecl ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Acl ).getX(), jd.getPoint( Geometry.W.Acl ).getY());
		g2.draw( gp );
		gp.reset();
		
		// Seatstays
		gp.moveTo(jd.getPoint( Geometry.W.Mal ).getX(), jd.getPoint( Geometry.W.Mal ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Esu ).getX(), jd.getPoint( Geometry.W.Esu ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Esl ).getX(), jd.getPoint( Geometry.W.Esl ).getY());
		gp.lineTo(jd.getPoint( Geometry.W.Mar ).getX(), jd.getPoint( Geometry.W.Mar ).getY());
		g2.setColor(Color.WHITE);
		g2.fill( gp );
		g2.setColor(Color.BLACK);
		g2.draw( gp );
		gp.reset();
		
		
		// Seatstay attachment
		//cairo_set_source_rgb(c, 1.0, 1.0, 1.0);
		gp.moveTo(jd.getPoint( Geometry.W.D1 ).getX(), jd.getPoint( Geometry.W.D1 ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.D2 ).getX(), jd.getPoint( Geometry.W.D2 ).getY(),
			jd.getPoint( Geometry.W.D3 ).getX(), jd.getPoint( Geometry.W.D3 ).getY(),
			jd.getPoint( Geometry.W.D4 ).getX(), jd.getPoint( Geometry.W.D4 ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.D6 ).getX(), jd.getPoint( Geometry.W.D6 ).getY(),
			jd.getPoint( Geometry.W.D5 ).getX(), jd.getPoint( Geometry.W.D5 ).getY(),
			jd.getPoint( Geometry.W.D1 ).getX(), jd.getPoint( Geometry.W.D1 ).getY());
		//cairo_fill(c);
		//cairo_set_source_rgb(c, 0, 0, 0);
		gp.moveTo(jd.getPoint( Geometry.W.D1 ).getX(), jd.getPoint( Geometry.W.D1 ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.D2 ).getX(), jd.getPoint( Geometry.W.D2 ).getY(),
			jd.getPoint( Geometry.W.D3 ).getX(), jd.getPoint( Geometry.W.D3 ).getY(),
			jd.getPoint( Geometry.W.D4 ).getX(), jd.getPoint( Geometry.W.D4 ).getY());
		gp.curveTo(
			jd.getPoint( Geometry.W.D6 ).getX(), jd.getPoint( Geometry.W.D6 ).getY(),
			jd.getPoint( Geometry.W.D5 ).getX(), jd.getPoint( Geometry.W.D5 ).getY(),
			jd.getPoint( Geometry.W.D1 ).getX(), jd.getPoint( Geometry.W.D1 ).getY());
		g2.draw( gp );
		gp.reset();

		if (frameType == Geometry.FrameType.MIXTE){
			
			gp.moveTo(jd.getPoint( Geometry.W.Jal ).getX(), jd.getPoint( Geometry.W.Jal ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Ecmu ).getX(), jd.getPoint( Geometry.W.Ecmu ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Ecml ).getX(), jd.getPoint( Geometry.W.Ecml ).getY());
			gp.lineTo(jd.getPoint( Geometry.W.Jar ).getX(), jd.getPoint( Geometry.W.Jar ).getY());
			g2.setColor(Color.WHITE);
			g2.fill( gp );
			g2.setColor(Color.BLACK);
			g2.draw( gp );
			gp.reset();
			
			gp.moveTo(jd.getPoint( Geometry.W.H1 ).getX(), jd.getPoint( Geometry.W.H1 ).getY());
			gp.curveTo(
				jd.getPoint( Geometry.W.H2 ).getX(), jd.getPoint( Geometry.W.H2 ).getY(),
				jd.getPoint( Geometry.W.H3 ).getX(), jd.getPoint( Geometry.W.H3 ).getY(),
				jd.getPoint( Geometry.W.H4 ).getX(), jd.getPoint( Geometry.W.H4 ).getY());
			gp.curveTo(
				jd.getPoint( Geometry.W.H6 ).getX(), jd.getPoint( Geometry.W.H6 ).getY(),
				jd.getPoint( Geometry.W.H5 ).getX(), jd.getPoint( Geometry.W.H5 ).getY(),
				jd.getPoint( Geometry.W.H1 ).getX(), jd.getPoint( Geometry.W.H1 ).getY());
			//cairo_fill(c);
			//cairo_set_source_rgb(c, 0, 0, 0);
			gp.moveTo(jd.getPoint( Geometry.W.H1 ).getX(), jd.getPoint( Geometry.W.H1 ).getY());
			gp.curveTo(
				jd.getPoint( Geometry.W.H2 ).getX(), jd.getPoint( Geometry.W.H2 ).getY(),
				jd.getPoint( Geometry.W.H3 ).getX(), jd.getPoint( Geometry.W.H3 ).getY(),
				jd.getPoint( Geometry.W.H4 ).getX(), jd.getPoint( Geometry.W.H4 ).getY());
			gp.curveTo(
				jd.getPoint( Geometry.W.H6 ).getX(), jd.getPoint( Geometry.W.H6 ).getY(),
				jd.getPoint( Geometry.W.H5 ).getX(), jd.getPoint( Geometry.W.H5 ).getY(),
				jd.getPoint( Geometry.W.H1 ).getX(), jd.getPoint( Geometry.W.H1 ).getY());
		}
		g2.draw(gp);
		gp.reset();


	}

	private void drawCrank( Graphics2D g2 ){
		final double eyeRadius1 = 6D;
		final double eyeRadius2 = 8D;
		double overlapAngle = Math.atan( jd.getPoint( Geometry.W.G ).getY() / jd.getPoint( Geometry.W.G ).getX());
		System.out.println("Overlap angle: " + overlapAngle );

		// Vertical position
		Arc2D arc = new Arc2D.Double( - jd.getRawParameter( Geometry.Prm.CER ), - jd.getRawParameter( Geometry.Prm.CER ),
								jd.getRawParameter( Geometry.Prm.CER ) * 2D, jd.getRawParameter( Geometry.Prm.CER ) * 2D,
								180D, 180D, 0);
		Path2D path  = new Path2D.Double( arc );

		arc.setArc(
			jd.getPoint( Geometry.W.Cem ).getX() - jd.getRawParameter( Geometry.Prm.CER ),
			jd.getPoint( Geometry.W.Cem ).getY() - jd.getRawParameter( Geometry.Prm.CER ),
			jd.getRawParameter( Geometry.Prm.CER ) * 2D, jd.getRawParameter( Geometry.Prm.CER ) * 2D,
						0D, 180D, 0);
		path.append( arc, true);
		path.closePath();

		g2.draw( path );
		path.reset();

		arc.setArc( jd.getPoint( Geometry.W.Cem ).getX() - eyeRadius1,
			jd.getPoint( Geometry.W.Cem ).getY() - eyeRadius1,
			eyeRadius1 * 2.D, eyeRadius1*2D,
			0D, 360D, 0);
		g2.draw( arc );
		arc.setArc( jd.getPoint( Geometry.W.Cem ).getX() - eyeRadius2, jd.getPoint( Geometry.W.Cem ).getY() - eyeRadius2,
				eyeRadius2 * 2.D, eyeRadius2*2D,
				0D, 360D, 0);
		g2.draw( arc );

		// Front overlap position
		arc.setArc( - jd.getRawParameter( Geometry.Prm.CER ), - jd.getRawParameter( Geometry.Prm.CER ),
			jd.getRawParameter( Geometry.Prm.CER ) * 2D, jd.getRawParameter( Geometry.Prm.CER ) * 2D,
			Math.toDegrees(-Math.PI/2D-overlapAngle), -180D, 0);
		path.append( arc, false );

		arc.setArc( jd.getPoint( Geometry.W.H ).getX() - jd.getRawParameter( Geometry.Prm.CER ),
			jd.getPoint( Geometry.W.H ).getY() - jd.getRawParameter( Geometry.Prm.CER ),
			jd.getRawParameter( Geometry.Prm.CER ) * 2D, jd.getRawParameter( Geometry.Prm.CER ) * 2D,
			Math.toDegrees(Math.PI/2D - overlapAngle), -180D, 0);
		path.append( arc, true);
		path.closePath();

		g2.draw( path );
		path.reset();

		Arc2D eye = new Arc2D.Double(
					jd.getPoint( Geometry.W.H ).getX() - eyeRadius2,
					jd.getPoint( Geometry.W.H ).getY() - eyeRadius2,
					eyeRadius2 * 2.D, eyeRadius2*2D,
					0D, 360D, 0);
		g2.draw( eye );

		// pedal
		double pedalLength = 65D;
		double pedalHeight = jd.getRawParameter( Geometry.Prm.PH ); // 15D;
		double toeclipHeight = jd.getRawParameter( Geometry.Prm.TCH );
		g2.draw( new Rectangle.Double( jd.getPoint( Geometry.W.H).getX() - pedalLength/2.0D,
					jd.getPoint( Geometry.W.H).getY() - pedalHeight/2.0D,
					pedalLength, pedalHeight));

		// toeclip
		path.moveTo( jd.getPoint( Geometry.W.H).getX() + pedalLength/2.0D, jd.getPoint( Geometry.W.H).getY() + pedalHeight/2.0D);
		arc.setArc( jd.getPoint( Geometry.W.H).getX() + jd.getRawParameter( Geometry.Prm.FFL) - toeclipHeight,
				jd.getPoint( Geometry.W.H).getY() + pedalHeight/2.0D,
				toeclipHeight, toeclipHeight, 90D, -170D, 0);
		path.append( arc, true );
		path.lineTo( path.getCurrentPoint().getX()- jd.getRawParameter(Geometry.Prm.FFL)+pedalLength,  path.getCurrentPoint().getY() + Math.sin( Math.toRadians(10D) ) * ( jd.getRawParameter( Geometry.Prm.FFL) - pedalLength ));
		path.lineTo( path.getCurrentPoint().getX()-pedalLength, path.getCurrentPoint().getY() + Math.sin( Math.toRadians(20D) ) * pedalLength );
		g2.draw(path);
		path.reset();

		


	}

	private void drawStem( Graphics2D g ){
		
		GeneralPath gp = new GeneralPath();

		gp.moveTo( jd.getPoint( Geometry.W.Stbl ).getX(), jd.getPoint( Geometry.W.Stbl ).getY() );  
		gp.lineTo( jd.getPoint( Geometry.W.Stur ).getX(), jd.getPoint( Geometry.W.Stur ).getY() );  
		gp.lineTo( jd.getPoint( Geometry.W.Stuf ).getX(), jd.getPoint( Geometry.W.Stuf ).getY() );  

		gp.moveTo( jd.getPoint( Geometry.W.Stlf ).getX(), jd.getPoint( Geometry.W.Stlf ).getY() );  
		gp.lineTo( jd.getPoint( Geometry.W.Stlr ).getX(), jd.getPoint( Geometry.W.Stlr ).getY() );  
		gp.lineTo( jd.getPoint( Geometry.W.Stbr ).getX(), jd.getPoint( Geometry.W.Stbr ).getY() );  

		g.draw( gp );

		g.draw( new Arc2D.Double( jd.getPoint( Geometry.W.Stmf).getX() - jd.getRawParameter( Geometry.Prm.STCD ) / 2D,
					  jd.getPoint( Geometry.W.Stmf).getY() - jd.getRawParameter( Geometry.Prm.STCD ) / 2D,
					  jd.getRawParameter( Geometry.Prm.STCD ), jd.getRawParameter( Geometry.Prm.STCD ), 0D, 360D, 0));
		gp.reset();

		

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

