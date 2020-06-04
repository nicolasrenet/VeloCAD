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
import java.util.EnumMap;
import java.util.Map;


public class Geometry {

	/* For each point, its identifier, its label, and the view that contains it
	 *	
	 * Static by default
	 */
	public enum W { 
				
		A("A"),
		B("B"),
		C("C"),
		D("D"),
		E("E"),
		F("F"),
		Fl("Fl"),
		G("G"),
		Gp("Gp"),
		Gps("Gps"),
		H("H"),
		Hw("Hw"),
		Hf("Hf"),
		Hfs("Hfs"),
		Bs("Bs"),
		J("J"),
		K("K"),
		KG("KG"),
		Kp("Kp"),
		L("L"),
		M("M"),
		N("N"),
		R("R"),
		Rb("Rb"),
		rA("rA", View.REAR),
		rB("rB", View.REAR),
		rC("rC", View.REAR),
		rD("rD", View.REAR),
		rE("rE", View.REAR),
		rF("rF", View.REAR),
		rG("rG", View.REAR),
		rGl("rGl", View.REAR),
		rGr("rGr", View.REAR),
		rH("rH", View.REAR),
		rFl("rFl", View.REAR),
		rFr("rFr", View.REAR),
		rI("rI", View.REAR),
		rJ("rJ", View.REAR),
		rJl("rJl", View.REAR),
		rJr("rJr", View.REAR),
		rK("rK", View.REAR),
		rBn("rBn", View.REAR),
		rBs("rBs", View.REAR),
		rLl("rLl", View.REAR),
		rLr("rLr", View.REAR),
		rMl("rMl", View.REAR),
		rMr("rMr", View.REAR),
		rN("rN", View.REAR),
		Ai("Ai"),
		Bi("Bi"),
		Bsi("Bsi"),
		Bsor("Bsor"),
		Bsol("Bsol"),
		Ci("Ci"),
		Csi("Csi"),
		Di("Di"),
		Bol("Bol"),
		Bsu("Bsu"),
		Bor("Bor"),
		Col("Col"),
		Cor("Cor"),
		ColPrime("ColPrime"),
		CorPrime("CorPrime"),
		Dol("Dol"),
		Dor("Dor"),
		Aol("Aol"),
		Aom("Aom"),
		Aor("Aor"),
		Acu("Acu"),
		Acl("Acl"),
		Gl("Gl"),
		Gm("Gm"),
		Gr("Gr"),
		Sb("Sb"),
		Sf("Sf"),
		Tb("Tb"),
		Tf("Tf"),
		Tm("Tm"),
		Ub("Ub"),
		Uf("Uf"),
		Vb("Vb"),
		Vf("Vf"),
		Ha("Ha"),
		Ma("Ma"),
		Mal("Mal"),
		Mar("Mar"),
		Ba("Ba"),
		Ecu("Ecu"),
		Ecl("Ecl"),
		Esu("Esu"),
		Esl("Esl"),
		Ecmu("Ecmu"),
		Ecml("Ecml"),
		Jal("Jal"),
		Ja("Ja"),
		Jar("Jar"),
		Jla("Jla"),
		Jua("Jua"),
		Ji("Ji"),
		Jo("Jo"),
		Cmi("Cmi"),
		Cmo("Cmo"),
		Cal("Cal"),
		Car("Car"),
		Cat("Cat"),
		Cab("Cab"),
		Cel("Cel"),
		Cem("Cem"),
		Cell("Cell"),
		Celr("Celr"),
		Ceu("Ceu"),
		Ceuu("Ceuu"),
		Ceul("Ceul"),
		Co("Co"),
		Do("Do"),
		Bo("Bo"),
		Tce("Tce"),
		Tcw("Tcw"),
		Bspl("Bspl"),
		Bspr("Bspr"),
		Stmr("Stmr"),
		Stmf("Stmf"),
		Stur("Stur"),
		Stuf("Stuf"),
		Stlr("Stlr"),
		Stlf("Stlf"),
		Stbl("Stbl"),
		Stbr("Stbr"),
		Stbm("Stbm"),
		Stbh("Stbh"),

		A1("A1"),
		A2("A2"),
		A3("A3"),
		A4("A4"),
		B1("B1", View.REAR),
		B2("B2", View.REAR),
		B3("B3", View.REAR),
		B4("B4", View.REAR),
		C1("C1", View.REAR),
		C2("C2", View.REAR),
		C3("C3", View.REAR),
		C4("C4", View.REAR),
		D1("D1"),
		D2("D2"),
		D3("D3"),
		D4("D4"),
		D5("D5"),
		D6("D6"),
		F1("F1"),
		F2("F2"),
		F3("F3"),
		F4("F4"),
		G0("G0"),
		G1("G1"),
		G2("G2"),
		G3("G3"),
		G4("G4"),
		H1("H1"),
		H2("H2"),
		H3("H3"),
		H4("H4"),
		H5("H5"),
		H6("H6"),

		HJamesX("HJamesX"),
		HJamesY("HJamesY"),

		BodySeat("BodySeat"),
		BodyShoulder("BodyShoulder"),
		BodyElbow("BodyElbow");
		
		private final String label;
		private View view;

		W(String lab, View v){
			label = lab;
			view = v;
		}

		W(String lab){
			this(lab, View.SIDE);
		}

		W(){
			this("", View.SIDE);
		}

		public String getLabel(){ return label ; }
		public View getView(){ return view ; }
	}




	/*
	 * Pour chaque paramètre: ID, description, défaut, min, max
	 *
	 */
	public enum Prm { 
		ALPHA(Category.FT_TRIANGLE, "Seat tube angle", Math.toRadians(73D), Math.PI/4D, Math.PI/2D),
		CSL(Category.RR_TRIANGLE, "Chainstay length", 440D, 50D, 1000D),
		drop(Category.FT_TRIANGLE, "Drop", 70D, 0.0, 200D),
		STL(Category.FT_TRIANGLE, "Seat tube length", 570D, 200D, 1500D),
		TTL(Category.FT_TRIANGLE, "Top tube length", 580D, 200D, 1500D),
		BETA(Category.FT_TRIANGLE, "Headtube angle", Math.toRadians(73D), Math.PI/4D, Math.PI/2D),
		FR(Category.FORK, "Fork rake", 70D, 0D, 100D),
		SIGMA(Category.FT_TRIANGLE, "Top tube slope", Math.toRadians(6D), 0.0, Math.PI/2D),
		WR(Category.COMPONENTS, "Wheel radius", 334D, 160D, 400D), // 190 = roue de 12 pouces + pneus
		TW(Category.COMPONENTS, "Tire Width", 42D, 20D, 60D),
		CL(Category.COMPONENTS, "Crank length", 170D, 100D, 200D),
		FC(Category.COMPONENTS, "Fender clearance", 10D, 1D, 30D),
		FSH(Category.COMPONENTS, "Fender stack height", 5D, 0D, 30D),
		FH(Category.COMPONENTS, "Fender height", 20D, 0D, 40D),
		FBR(Category.FORK, "Fork bend radius", 6D*2.54*10, 4D*2.54*10, 20D*2.54*10),
		BUD(Category.FORK, "Fork blade upper ø", 29D, 20D, 40D),
		BLD(Category.FORK, "Fork blade lower ø", 20D, 10D, 40D),
		FCH(Category.FORK, "Fork crown height", 16D, 5D, 60D),
		BHS(Category.COMPONENTS, "Bottom headset stack", 12D, 2D, 30D),
		THS(Category.COMPONENTS, "Top headset stack", 30D, 2D, 40D),
		HTBO(Category.FT_TRIANGLE, "Headtube bottom offset", 3D, 2D, 30D),
		DTOD(Category.FT_TRIANGLE, "Downtube ø", 28.6D, 25D, 35D),
		HTOD(Category.FT_TRIANGLE, "Headtube ø", 31.8D, 25D, 35D),
		BBOD(Category.FT_TRIANGLE, "Bottom bracket ø", 38.05D, 35D, 55D),
		BBG(Category.FT_TRIANGLE, "Bottom bracket gauge", 2.1082D, 1D, 3D),
		BBW(Category.FT_TRIANGLE, "Bottom bracket width", 68.5D, 65D, 75D),
		BBL(Category.COMPONENTS, "Bottom bracket length", 110D, 107D, 122D),
		BBCSO(Category.RR_TRIANGLE, "Bottom bracket chainstay offset", 15D, 5D, 40D),
		CSODS(Category.RR_TRIANGLE, "Chainstay ø (start)", 22.2D, 20D, 30D),
		CSODE(Category.RR_TRIANGLE, "Chainstay ø (end)", 12.7D, 10D, 30D),
		RAW(Category.RR_TRIANGLE, "Rear axle width", 130D, 110D, 175D),
		RTSC(Category.RR_TRIANGLE, "Rear tire side clearance", 10D, 0D, 30D),
		RDOL(Category.RR_TRIANGLE, "Rear dropout offset (lower)", 20D, 5D, 50D),
		RDOU(Category.RR_TRIANGLE, "Rear dropout offset (upper)", 35D, 5D, 50D),
		FDO(Category.FORK, "Front dropout offset", 20D, 5D, 50D),
		QF(Category.RR_TRIANGLE, "Crank tread", 140D, 80D, 200D),
		CAW(Category.RR_TRIANGLE, "Crank arm width", 10D, 5D, 20D),
		CER(Category.COMPONENTS, "Crank eye radius", 15D, 10D, 20D),
		STOD(Category.FT_TRIANGLE, "Seat tube ø", 28.6D, 25D, 35D),
		TTOD(Category.FT_TRIANGLE, "Top tube ø", 25.6D, 25D, 35D),
		HTE(Category.FT_TRIANGLE, "Headtube upper extension", 10D, 0D, 50D),
		PH(Category.COMPONENTS, "Pedal height", 15.0, 5D, 25D),
		TCH(Category.COMPONENTS, "Toeclip height", 45D, 25D, 55D),
		SSODS(Category.RR_TRIANGLE, "Seatstay ø (start)", 15D, 10D, 20D),
		SSODE(Category.RR_TRIANGLE, "Seatstay ø (end)", 10D, 7D, 20D),
		SSLL(Category.RR_TRIANGLE, "Seatstay leave length", 60D, 10D, 100D),
		FFSA(Category.COMPONENTS, "Front fender start angle", Math.toRadians(-140D), -Math.PI, Math.toRadians(-130D)),
		FFE(Category.COMPONENTS, "Front fender extension", Math.toRadians(150D), Math.PI/2D, Math.PI),
		RFSA(Category.COMPONENTS, "Rear fender start angle", Math.toRadians(175D), Math.PI/2D, Math.PI),
		RFE(Category.COMPONENTS, "Rear fender extension", Math.toRadians(190D), Math.PI/2D, Math.toRadians(200D)),
		FFL(Category.ENGINE, "Forefoot length", 95D, 10D, 150D),
		STMH(Category.COMPONENTS, "Stem height", 100D, 20D, 300D),
		STML(Category.COMPONENTS, "Stem length", 100D, 10D, 150D),
		STQD(Category.COMPONENTS, "Stem quill diameter", 22.2D, 20D, 30D ),
		STHD(Category.COMPONENTS, "Stem hanger diameter", 25D, 20.0D, 40D ),
		STCD(Category.COMPONENTS, "Stem clamp diameter", 25.4D, 22D, 30D),
		SDH(Category.ENGINE, "Saddle height", 750D, 300D, 1200D),
		RFL(Category.ENGINE, "Rearfoot length", 95D, 50D, 250D),
		FMRL(Category.ENGINE, "Femur length", 950D, 200D, 1000D),
		TBL(Category.ENGINE, "Tibia length", 950D, 200D, 1000D),
		SHLDRH(Category.ENGINE, "Shoulder height (from floor)", 1450D, 740D, 2000D),
		FAL(Category.ENGINE, "Forearm length", 300D, 140D, 500D),
		UAL(Category.ENGINE, "Upper arm length", 300D, 140D, 500D),
		PBH(Category.ENGINE, "Pubic Bone height (from floor)", 830D, 300D, 1000D),
		AFL(Category.ENGINE, "Arm flexion", Math.toRadians(5D), 0D, Math.PI/2D);

				
		
				
		private final Category category;
		private final String label;
		private final Double defaultValue ;
		private final Double minimumValue;
		private final Double maximumValue;

		Prm( Category cat, String lab, Double dflt, Double min, Double max){
			category = cat;
			label = lab;
			defaultValue = dflt;
			minimumValue = min;
			maximumValue = max;
		}

		Prm( Category cat){
			this(cat, "", 0.0, 0.0, 10000.0);
		}

		Prm(){
			this(Category.FT_TRIANGLE, "", 0.0, 0.0, 10000.0);
		}

		public String getLabel(){ return label ; }
		public Category getCategory(){ return category ; }
		public Double getDefault(){ return defaultValue ;}
		public Double getMinValue(){ return minimumValue ;}
		public Double getMaxValue(){ return maximumValue ;}

	}



	/**
	 * Computed data: some are target values to be monitored closely (BB height, trail), some 
	 * should typically exceed or stay within a certain threshold (clearances), such as standover,
	 * and some are just useful information for choosing the tubes, or setting up the jig.
	 *
	 * A case might be made for including the trail as a parameter, instead of the fork rake.
	 */
	public enum INDICATOR {


		BB_HEIGHT("BB height", "Bottom bracket height (above ground)", true),
		PEDAL_CLEARANCE("Pedal clearance", "Pedal height (above ground)", true),
		HJX("Henry James X", "Henry James X datum (BB to steerer axis (for jig setup)", true),
		HJY("Henry James Y", "Henry James Y datum (from HJX to bottom of head tube - for jig setup)", true),
		FORK_PROJECTION("Fork projection", "Distance from fork crown race to projection of front axle on steerer axis", true),
		CHAINSTAY_X_PROJ("Chainstay x-axis projection", "Chainstay x-axis projection (for jig setup)", true),
		TOE_OVERLAP("Toe overlap", "A (crude) measure of toe overlap", true),
		WHEELBASE("Wheelbase", "Distance between axles", true),
		STANDOVER("Standover", "Height of the (virtual) horizontal tube", true),
		STEERER_LENGTH("Steerer length", "Minimal length of the steerer tube", true),
		TRAIL("Trail", "Trail", true),
		CHAINSTAY_ANGLE_CURVED("Chainstay angle (curved CS)", "Chainstay angle with frame axis (curved CS)", true),
		CHAINSTAY_ANGLE_STRAIGHT("Chainstay angle (straight CS)", "Chainstay angle with frame axis (straight CS)", true),
		CRANK_CLEARANCE_CURVED("Crank/CS clearance (curved CS)", "Crank/CS clearance (curved CS)", true),
		CRANK_CLEARANCE_STRAIGHT("Crank/CS clearance (straight CS)", "Crank/CS clearance (straight CS)", true),
		CROSS_MEMBER_LENGTH("Cross-member length", "Cross-member length (for mixte frames)"),
		BB_TO_FRONT("BB to front axle", "BB to front axle distance (along x-axis)"),
		SADDLE_HEIGHT("Saddle height", "Saddle height");


		private String longDesc;
		private String shortDesc;
		private Boolean isKeyDatum=false; // indicator is a key datum


		INDICATOR( String shortDesc, String longDesc){
			this.shortDesc = shortDesc;
			this.longDesc = longDesc;
		}

		INDICATOR( String shortDesc, String longDesc, Boolean isKey ){
			this.shortDesc = shortDesc;
			this.longDesc = longDesc;
			this.isKeyDatum = isKey;
		}

		public String getShortDesc(){ return shortDesc; }
		public String getLongDesc(){ return longDesc; }
		public Boolean getIsKeyDatum(){ return isKeyDatum; }
	}


	private Double dist_L;

	/**
	 * Parameters: the actual values
	 *
	 */
	public class ParameterSet extends EnumMap<Prm, Double> {

		private Double[] doubleValues; 

		public ParameterSet(){
			super( Prm.class);
			doubleValues = new Double[ Prm.values().length ];
			for (Prm p: Prm.values()){
				put(p, p.getDefault());
			}
		};
	}

	/*
	 * Points: the actual values
	 *
	 */
	public class PointSet extends EnumMap<W, Point2D> {

		private Point2D[] pointValues; 

		public PointSet(){
			super( W.class);
			pointValues = new Point2D[ W.values().length ];
			for (W w: W.values()){
				put(w, new Point2D.Double(0, 0));
			
			}
		};
	}


	/*
	 * Indicator: the actual values
	 *
	 */
	public class IndicatorSet extends EnumMap<INDICATOR, Double> {

		private Double[] indicatorValues; 

		public IndicatorSet(){
			super( INDICATOR.class);
			indicatorValues = new Double[ INDICATOR.values().length ];
			for (INDICATOR i: INDICATOR.values()){
				put(i, 0.0);
			
			}
		};
	}




	private boolean hasValidData = false;

	public static enum Category { FT_TRIANGLE, RR_TRIANGLE, FORK, COMPONENTS, ENGINE };
	public static enum View { SIDE, REAR };
	public static enum Layer { SCHEMATICS, CONTOURS, DIMENSIONS, POINTS }
	public static enum FrameType { CLASSIC, MIXTE, CROSSED }

	private static final int POINT_COUNT = W.values().length;
	private static final int PARAMETER_COUNT = Prm.values().length;

	private ParameterSet parameters;
	private PointSet points;
	private IndicatorSet indicators;

	private File configFile; // Config file currently associated with this data set
	private String creationTime;
	private FrameType frameType = FrameType.CLASSIC;
	private boolean curvedChainstay = true;


	//private List<ViewPane> observers ;

	//===========================================================================


	/*
	 */
	public Geometry(){
		
		// System.out.println("Geometry() constructor call");

		parameters = new ParameterSet();
		points = new PointSet();
		indicators = new IndicatorSet();


		///observers = new ArrayList<ViewPane>();

		refresh();

	}

	public void setConfigFile( File f ){
		configFile = f ;
	}

	public File getFile(){
		return configFile;
	}

	public String getCreationTime(){
		return creationTime;
	}
	
	public void setCreationTime(String dateStr ){
		creationTime = dateStr;
	}

	public FrameType getFrameType(){
		return frameType ;
	}

	public void setFrameType(FrameType ft){
		frameType = ft;
	}

	public void setCurvedChainstay( boolean b){
		curvedChainstay = b;
	}

	public boolean hasCurvedChainstay(){
		return curvedChainstay;
	}

	public boolean hasValidDataSet(){
		return hasValidData;
	}

	public int refresh(){

		if (validParameters()){
			this.hasValidData = true ;
	
			// Performs the computations 
			update();
		} else {
			// System.out.println("Invalid parameters! Skipping computations.");
			this.hasValidData = false ;
			return -1;
		}
		// Redraw the plans
		//updateObservers();
		return 0;
		
	}

	/*
	 * Test for invalid parameters
	 *
	 */
	public boolean validParameters(){

		// Check for out-of-bounds values
		for (Prm p: Prm.values()){
			if  ( getRawParameter( p ) < p.getMinValue() || getRawParameter( p ) > p.getMaxValue()){
				// System.out.println("Parameter " + p + " should be comprised between " + p.getMinValue() + " and " + p.getMaxValue());
				return false ;
			}
		}

		// Check for consistency (incomplete)
		
		// drop + pedal length exceeds wheel radius
		if ((getRawParameter( Prm.drop ) + getRawParameter( Prm.CL )) >= getRawParameter( Prm.WR )){
			// System.out.println("Inconsistent data: drop + crank length > wheel radius!");
			return false;
		}
		// Chainstay length exceeds wheel radius
		if ( getRawParameter( Prm.WR ) >= getRawParameter( Prm.CSL )){
			// System.out.println("Inconsistent data: wheel radius > chainstay length!");
			return false;
		}
		// Top tube length is less that projection of A-Ap, w/ Ap projection of A on TT
		if ( getRawParameter( Prm.STL ) * Math.cos( getRawParameter( Prm.ALPHA ))  > getRawParameter( Prm.TTL )){
			// System.out.println("Inconsistent data: top tube length does not fit the seat tube specs!");
			return false;
		}
		
		return true;
	}

	/*public void addObserver( ViewPane vp ){
		observers.add( vp );
	}

	private void updateObservers(){
		for(ViewPane vp: observers){
			vp.update();
		}
	}*/

	/* 
	 * Assign a frameset parameter with user input values (mixed units),
	 * performing the necessary unit conversion
 	 */
	public int setParameter(Prm p, Double v){
		switch( p ){
			case ALPHA: case SIGMA: case BETA: case AFL:
				v *= Math.PI/180.0D ;
				break;
			case FBR:
				v *= 2.54D * 10.0 ;
				break;
			case STL: case CSL: case TTL: case drop: case FR: case FFL: case SSLL:
				v *= 10.0D ;
				break;
			default:
				;
		}
		parameters.put(p, v);
		return 0;
	}

	public void setDefaultParameters(){
		for ( Prm p: Prm.values()){
			setRawParameter( p, p.getDefault());
		}
	}

	/*
	 * Assign a frameset parameter with a computation-ready value
	 * (i.e. in mm).
	 *
	 */
	public void setRawParameter( Prm p, Double v){
		parameters.put(p, v );
	}


	/*
	 * Converts a parameter raw value (mm or rad) to suit the display
	 */
	public Double getParameter( Prm p){
		switch( p ){
			case ALPHA: case SIGMA: case BETA: case AFL:
				return parameters.get(p) * 180.0D / Math.PI ;
			case FBR:
				return (parameters.get(p) / 2.54D) / 10.0;
			case STL: case CSL: case TTL: case drop: case FR: case FFL: case SSLL:
				return parameters.get(p) / 10.0D;
			default:
				return parameters.get(p);
		}
	}

	public Double getRawParameter( Prm p ){
		return parameters.get( p );
	}


	public Point2D getPoint(W w){
		Point2D pt = points.get( w );
		return pt;
	}


	public Double getIndicator(INDICATOR id){
		return indicators.get( id  );
	}



	private Double cos_ALPHA = 0.0; 
	private Double sin_ALPHA = 0.0; 
	private Double tan_ALPHA = 0.0; 
	private Double cos_ALPHAc = 0.0; 
	private Double sin_ALPHAc = 0.0; 
	private Double tan_ALPHAc = 0.0; 
	private Double cos_BETA = 0.0; 
	private Double sin_BETA = 0.0; 
	private Double tan_BETA = 0.0; 
	private Double cos_BETAc = 0.0; 
	private Double sin_BETAc = 0.0; 
	private Double tan_BETAc = 0.0; 
	private Double cos_SIGMA = 0.0; 
	private Double sin_SIGMA = 0.0; 
	private Double tan_SIGMA = 0.0; 
	private Double cos_SIGMAc = 0.0; 
	private Double sin_SIGMAc = 0.0; 
	private Double tan_SIGMAc = 0.0; 



	private Double angle_EAB ;
	private Double angle_CBD ;
	private Double angle_DAx ;
	private Double angle_epsilon ; 
	private Double angle_psi ;
	private Double angle_BEx ;
	private Double angle_CEx ;

	private void update(){
		
		compute_trigo();
		compute_schematics();
		compute_distances();
		compute_contours();
		compute_rear_fork();
		compute_rear_fork_distances();
		compute_body();

		compute_henry_james_points();

	}


	private void compute_trigo(){
		cos_ALPHA = Math.cos( parameters.get(Prm.ALPHA) ); 
		sin_ALPHA = Math.sin( parameters.get(Prm.ALPHA) ); 
		tan_ALPHA = Math.tan( parameters.get(Prm.ALPHA) ); 
		cos_ALPHAc = Math.sin( parameters.get(Prm.ALPHA) ); 
		sin_ALPHAc = Math.cos( parameters.get(Prm.ALPHA) ); 
		tan_ALPHAc = 1/tan_ALPHA; 
		cos_BETA = Math.cos( parameters.get(Prm.BETA) ); 
		sin_BETA = Math.sin( parameters.get(Prm.BETA) ); 
		tan_BETA = Math.tan( parameters.get(Prm.BETA) ); 
		cos_BETAc = Math.sin( parameters.get(Prm.BETA) ); 
		sin_BETAc = Math.cos( parameters.get(Prm.BETA) ); 
		tan_BETAc = 1/tan_BETA;
		cos_SIGMA = Math.cos( parameters.get(Prm.SIGMA) ); 
		sin_SIGMA = Math.sin( parameters.get(Prm.SIGMA) ); 
		tan_SIGMA = Math.tan( parameters.get(Prm.SIGMA) ); 
		cos_SIGMAc = Math.sin( parameters.get(Prm.SIGMA) ); 
		sin_SIGMAc = Math.cos( parameters.get(Prm.SIGMA) ); 
		tan_SIGMAc = 1/tan_SIGMA;

	}

	private void compute_schematics(){
	
		
		Double diag;
		Double a;
		Double offset;
		Double chi;
		Double rho;
		Double hHTOD = parameters.get(Prm.HTOD)/2;
		Double hDTOD = parameters.get(Prm.DTOD)/2;
		Double FBA ;

		Double dist_fork_length = parameters.get(Prm.WR) + parameters.get(Prm.FC) + parameters.get(Prm.FSH);

		//dist_L = parameters.get(Prm.STL) * cos(Math.PI / 2 - parameters.get(Prm.ALPHA)) - parameters[drop];

		// distance top tube to wheels axis
		Double dist_L = parameters.get(Prm.STL) * cos_ALPHAc - parameters.get(Prm.drop);

		Double dist_Lprime = dist_L / cos_BETAc;
		Double dist_Gdelta = parameters.get(Prm.FR) * tan_BETAc;
		angle_EAB = parameters.get(Prm.ALPHA) - Math.asin(parameters.get(Prm.drop) / parameters.get(Prm.CSL));
		Double dist_Sdelta = ( parameters.get(Prm.SIGMA)==0.0 ? 0.0 : (parameters.get(Prm.TTL) / sin_ALPHA) / (1/tan_ALPHA + (1/tan_SIGMA)));
		// angle fork line / steerer
		chi = Math.asin(parameters.get(Prm.FR) / dist_fork_length);
		// Angle fork line / vertical
		rho = chi + Math.PI/2 - parameters.get(Prm.BETA);

		getPoint(W.A).setLocation(0.0, 0.0);

		getPoint(W.B).setLocation( 	cos_ALPHA * parameters.get(Prm.STL) * (-1),
						sin_ALPHA * parameters.get(Prm.STL));
		
		getPoint(W.Bs).setLocation(	cos_ALPHA * (parameters.get(Prm.STL) - dist_Sdelta) * (-1),
						sin_ALPHA * (parameters.get(Prm.STL) - dist_Sdelta));

		getPoint(W.C).setLocation(	getPoint(W.B).getX() + parameters.get(Prm.TTL) ,
						getPoint(W.B).getY());


		getPoint(W.G).setLocation(	getPoint(W.C).getX() + Math.sqrt( dist_Lprime * dist_Lprime - dist_L * dist_L) + Math.sqrt(dist_Gdelta * dist_Gdelta + parameters.get(Prm.FR) * parameters.get(Prm.FR)),
						parameters.get(Prm.drop));

		getPoint(W.F).setLocation(	getPoint(W.G).getX() - Math.sin(rho) * (dist_fork_length),
						getPoint(W.G).getY() + Math.cos(rho) * (dist_fork_length));
		getPoint(W.Fl).setLocation(	getPoint(W.F).getX() + Math.cos(parameters.get(Prm.BETA)) * 20,
						getPoint(W.F).getY() - Math.sin(parameters.get(Prm.BETA)) * 20);

		getPoint(W.E).setLocation(	Math.sqrt(parameters.get(Prm.CSL) * parameters.get(Prm.CSL) - parameters.get(Prm.drop) * parameters.get(Prm.drop)) * -1,
						parameters.get(Prm.drop));

		getPoint(W.R).setLocation(	getPoint(W.F).getX() - cos_BETA * (parameters.get(Prm.FCH) + parameters.get(Prm.BHS) + parameters.get(Prm.HTBO)),
						getPoint(W.F).getY() + sin_BETA * (parameters.get(Prm.FCH) + parameters.get(Prm.BHS) + parameters.get(Prm.HTBO)));

		getPoint(W.Rb).setLocation(	getPoint(W.R).getX() - cos_BETAc * hHTOD,
						getPoint(W.R).getY() - sin_BETAc * hHTOD);

		diag = Math.sqrt( getPoint(W.Rb).getX() * getPoint(W.Rb).getX() + getPoint(W.Rb).getY() * getPoint(W.Rb).getY());
		angle_DAx = Math.atan( getPoint(W.Rb).getY() / getPoint(W.Rb).getX()) + Math.asin(hDTOD / diag);
		a = angle_DAx - Math.PI/2 + parameters.get(Prm.BETA);
		offset = (hDTOD + hHTOD * Math.sin(a))/Math.cos(a);
		// System.out.printf("offset=%f\n", offset);

		getPoint(W.D).setLocation(	getPoint(W.R).getX() - offset * cos_BETA,
						getPoint(W.R).getY() + offset * sin_BETA);

		

		// Seatstay angle
		angle_BEx = Math.atan((getPoint(W.Bs).getY() - getPoint(W.E).getY()) / (getPoint(W.Bs).getX() - getPoint(W.E).getX()));
		// System.out.printf("Lambda=%f\n", angle_BEx);
			
		/* Cross-member intersection 
		 *
		 * With A=(0,0), det(AB)=0, which simplifies the computation
		 *
		 */
		
		getPoint(W.J).setLocation( cross_member_intersection());
		angle_CEx = Math.atan( (getPoint(W.C).getY() - getPoint(W.E).getY()) / (getPoint(W.C).getX() - getPoint(W.E).getX()));

		/* Fork curve */
		
		FBA = get_FBA();
		// System.out.printf("Fork bend angle: %f\n", FBA * 180/Math.PI);
		getPoint(W.K).setLocation(	getPoint(W.C).getX() + (dist_L / dist_Lprime) * (dist_Lprime + dist_Gdelta - parameters.get(Prm.FBR) * Math.sin(FBA)) / tan_BETA,
			
						getPoint(W.C).getY() - (dist_L / dist_Lprime) * (dist_Lprime + dist_Gdelta - parameters.get(Prm.FBR) * Math.sin(FBA))); // fork bend start (center)

		getPoint(W.L).setLocation(	getPoint(W.K).getX() + parameters.get(Prm.FBR) * cos_BETAc, // fork bend virtual center
						getPoint(W.K).getY() + parameters.get(Prm.FBR) * sin_BETAc);

		getPoint(W.KG).setLocation( 
				intersect( -tan_BETA, slope_point(-tan_BETA, getPoint(W.K)), 
				-Math.tan(parameters.get(Prm.BETA)-FBA), slope_point(-Math.tan(parameters.get(Prm.BETA)-FBA), getPoint(W.G))));  



		// Projection of the steerer tube on the floor line
		Double KpY = parameters.get(Prm.drop) - parameters.get(Prm.WR);
		getPoint(W.Kp).setLocation(	getPoint(W.D).getX() + (getPoint(W.D).getY() - KpY) / Math.tan( parameters.get(Prm.BETA) ) ,
						KpY) ;

		// Projection of front dropout on the floor line
		getPoint(W.Gp).setLocation( 	getPoint(W.G).getX(),
						getPoint(W.Kp).getY());


		// Stem
		getPoint(W.Stmr).setLocation(	getPoint(W.C).getX() - cos_BETA * parameters.get(Prm.STMH),
						getPoint(W.C).getY() + sin_BETA * parameters.get(Prm.STMH));
		getPoint(W.Stmf).setLocation(	getPoint(W.Stmr).getX() + parameters.get(Prm.STML),
						getPoint(W.Stmr).getY()) ;

		// projection of the front axle on the steering axis
		getPoint(W.Gps).setLocation(	getPoint(W.G).getX() - parameters.get(Prm.FR)*Math.cos(Math.PI/2.0-parameters.get(Prm.BETA)),
						getPoint(W.G).getY() - parameters.get(Prm.FR)*Math.sin(Math.PI/2.0-parameters.get(Prm.BETA)));
						
	
	
	}

	private void compute_distances(){
		

		indicators.put(INDICATOR.PEDAL_CLEARANCE, parameters.get(Prm.WR) - (parameters.get(Prm.drop) + parameters.get(Prm.CL)));
		indicators.put(INDICATOR.TRAIL, (parameters.get(Prm.WR) * cos_BETA - parameters.get(Prm.FR)) / sin_BETA);
		indicators.put(INDICATOR.BB_HEIGHT, parameters.get(Prm.WR) - parameters.get(Prm.drop));
		indicators.put(INDICATOR.WHEELBASE, getPoint(W.G).getX() - getPoint(W.E).getX());
		indicators.put(INDICATOR.CROSS_MEMBER_LENGTH, Math.sqrt( (getPoint(W.C).getX() - getPoint(W.E).getX()) * (getPoint(W.C).getX() - getPoint(W.E).getX()) + (getPoint(W.C).getY() - getPoint(W.E).getY()) * (getPoint(W.C).getY() - getPoint(W.E).getY()) ));

		indicators.put(INDICATOR.STANDOVER, indicators.get(INDICATOR.BB_HEIGHT) + parameters.get(Prm.STL)*cos_ALPHAc);
		indicators.put(INDICATOR.BB_TO_FRONT, getPoint(W.A).distance(getPoint(W.G)));
		indicators.put(INDICATOR.CHAINSTAY_X_PROJ, getPoint(W.A).getX() - getPoint(W.E).getX());
		indicators.put(INDICATOR.FORK_PROJECTION, getPoint(W.Tm).distance( getPoint(W.Gps)));

		

		indicators.put(INDICATOR.SADDLE_HEIGHT, get_lemonds_saddle_height( parameters.get(Prm.PBH) ));


		// Distance to centerline of seatstay bridge (5mm is leather washer + boss)
		
		double bridge_diameter = 11.2;
		double fender_washer_thickness = 4.5;

		double axle_to_bridge_centerline = parameters.get(Prm.WR) + parameters.get(Prm.FC) + fender_washer_thickness + bridge_diameter/2;

		// Distance to top of of seatstay bridge
		double axle_to_bridge_top = axle_to_bridge_centerline + bridge_diameter/2;

		// angle between seatstay bridge and seatstay
		// (account for the fact that the SS starts ~ 20mm above the axle center line) 
		Double dist_ssl = Math.sqrt(parameters.get(Prm.CSL) * parameters.get(Prm.CSL) + parameters.get(Prm.STL) * parameters.get(Prm.STL) - 2 * parameters.get(Prm.CSL) * parameters.get(Prm.STL) * Math.cos(angle_EAB));
		double beta = Math.atan(  (dist_ssl-25)  / ((parameters.get(Prm.RAW)/2)-(parameters.get(Prm.STOD)/2)));
		// bridge shortest width
		double bridge_shortest_width = (dist_ssl - axle_to_bridge_top)*2 /Math.tan(beta) + parameters.get(Prm.STOD);

		// System.out.printf("Distance to top of SS bridge=%f\nAngle SS/bridge=%f\nBridge shortest width=%f\n", axle_to_bridge_top, degrees(beta), bridge_shortest_width );

	}


	private void compute_henry_james_points(){
		// intersection of steering axis with x axis
		getPoint(W.HJamesX).setLocation( 
			getPoint(W.C).getX() + getPoint(W.C).getY()/Math.tan(parameters.get(Prm.BETA)),
			0
		);
		indicators.put(INDICATOR.HJX, getPoint(W.HJamesX).getX());

		getPoint(W.HJamesY).setLocation(
			(getPoint(W.Sf).getX()+getPoint(W.Sb).getX())/2,
			(getPoint(W.Sf).getY()+getPoint(W.Sb).getY())/2
		);
		
		indicators.put(INDICATOR.HJY, getPoint(W.HJamesX).distance( getPoint(W.HJamesY) ));

	}

	private Point2D  intersect( Double a1, Double b1, Double a2, Double b2){
		if (a1==a2) return null;

		Double x = (b2-b1) / (a1-a2);
		Double y = a1 * x + b1  ;

		return new Point2D.Double( x, y );
	}


	private Point2D cross_member_intersection(){
		
		Double detEC;
		Double detXY;

		detEC = getPoint(W.E).getX() * getPoint(W.C).getY() - getPoint(W.E).getY() * getPoint(W.C).getX();
		detXY = getPoint(W.B).getX() * (getPoint(W.C).getY() - getPoint(W.E).getY()) + (getPoint(W.E).getX() - getPoint(W.C).getX()) * getPoint(W.B).getY();
		
		Point2D cmi = new Point2D.Double( 
			getPoint(W.B).getX() * detEC / detXY,
			getPoint(W.B).getY() * detEC / detXY
		);

		return cmi;

	}


	private Double get_FBA(){
		return Math.acos(1 - parameters.get(Prm.FR) / parameters.get(Prm.FBR) ) ;
	}

	private Double slope_point(Double sl, Point2D p){
		// given slope and a point, compute intersection with y axis (b)
		return p.getY() - sl * p.getX();
	}



	private void compute_contours(){
	
		contour_front_triangle();
		contour_chainstay();
		contour_fork();
		contour_seatstay();
		contour_crank();
		compute_toe_overlap();

	}

	private void contour_front_triangle(){
		
		Double cos_DAx = Math.cos(angle_DAx);
		Double sin_DAx = Math.sin(angle_DAx);
		Double tan_DAx = Math.tan(angle_DAx);

		Double ht_y_intercept_lower = slope_point(-tan_BETA, getPoint(W.C)) - parameters.get(Prm.HTOD)/(2*cos_BETA); // HT inner
		Double dt_y_intercept_upper = parameters.get(Prm.DTOD)/(2*cos_DAx); // DT inner/outer
		Double st_y_intercept_upper = parameters.get(Prm.STOD)/(2*cos_ALPHA); // ST inner/outer
		Double tt_y_intercept_upper = slope_point( tan_SIGMA, getPoint(W.C)) + parameters.get(Prm.TTOD)/(2*cos_SIGMA);
		Double tt_y_intercept_lower = tt_y_intercept_upper - parameters.get(Prm.TTOD)/cos_SIGMA ;
		Double cmt_y_intercept_upper = slope_point( Math.tan(angle_CEx), getPoint(W.J) ) + parameters.get(Prm.TTOD)/(2 * Math.cos(angle_CEx));
		Double cmt_y_intercept_lower = cmt_y_intercept_upper - parameters.get(Prm.TTOD) / Math.cos(angle_CEx);
		
		// Where DT and ST intersect
		getPoint(W.Di).setLocation( intersect( -tan_BETA, ht_y_intercept_lower, tan_DAx, dt_y_intercept_upper));
		getPoint(W.Ai).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, tan_DAx, dt_y_intercept_upper));

		// Front triangle: inner points
		// top tube is level: simplified computations

		Double tt_to_ht_depth = (parameters.get(Prm.HTOD)/2) * Math.sin( Math.acos(parameters.get(Prm.TTOD)/parameters.get(Prm.HTOD)));
		Double tt_to_st_depth = (parameters.get(Prm.STOD)/2) * Math.sin( Math.acos(parameters.get(Prm.TTOD)/parameters.get(Prm.STOD)));
		if (parameters.get(Prm.SIGMA)==0.0){
			getPoint(W.Bi).setLocation( 
				getPoint(W.B).getX() + parameters.get(Prm.TTOD)/(2*tan_ALPHA) + parameters.get(Prm.STOD)/(2*sin_ALPHA),
				getPoint(W.B).getY() - parameters.get(Prm.TTOD)/2);

			getPoint(W.Ci).setLocation( 
				getPoint(W.C).getX() - parameters.get(Prm.HTOD)/(2*sin_ALPHA) + parameters.get(Prm.TTOD)/(2*tan_ALPHA),
				getPoint(W.C).getY() - parameters.get(Prm.TTOD)/2);

			getPoint(W.Col).setLocation( 
				getPoint(W.Ci).getX() - parameters.get(Prm.TTOD) / tan_BETA,
				getPoint(W.Ci).getY() + parameters.get(Prm.TTOD)) ;

			getPoint(W.Bor).setLocation( 
				getPoint(W.Bi).getX() - parameters.get(Prm.TTOD) / tan_ALPHA,
				getPoint(W.Bi).getY() + parameters.get(Prm.TTOD)) ;

			getPoint(W.Bol).setLocation( 
				getPoint(W.Bor).getX() - parameters.get(Prm.STOD) / sin_ALPHA,
				getPoint(W.Bor).getY());

			// TT wraps around HT only up to that point
			getPoint(W.Co).setLocation(
				getPoint(W.C).getX() - tt_to_ht_depth,
				getPoint(W.C).getY());

			getPoint(W.Bo).setLocation( 
				getPoint(W.B).getX() + tt_to_st_depth,
				getPoint(W.B).getY());

		} else {
			// Top tube

			getPoint(W.Bi).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, tan_SIGMA, tt_y_intercept_lower));
			getPoint(W.Ci).setLocation( intersect( -tan_BETA, ht_y_intercept_lower, tan_SIGMA, tt_y_intercept_lower ));
			getPoint(W.Col).setLocation( intersect( -tan_BETA, ht_y_intercept_lower, tan_SIGMA, tt_y_intercept_upper));
			getPoint(W.Bor).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, tan_SIGMA, tt_y_intercept_upper));
			getPoint(W.Bol).setLocation( intersect( -tan_ALPHA, -st_y_intercept_upper, tan_SIGMA, tt_y_intercept_upper));
			
			Double tt_y_intercept = tt_y_intercept_upper - (parameters.get(Prm.TTOD)/2) / cos_SIGMA;
			getPoint(W.Co).setLocation( intersect( tan_SIGMA, tt_y_intercept, -tan_BETA, ht_y_intercept_lower + tt_to_ht_depth / cos_BETA ));
			getPoint(W.Bo).setLocation( intersect( tan_SIGMA, tt_y_intercept, -tan_ALPHA, tt_to_st_depth / cos_ALPHA ));
		}

		Double seatpost_extension = 10.0;

		getPoint(W.Bspl).setLocation( 
			getPoint(W.Bol).getX() - cos_ALPHA * seatpost_extension,
			getPoint(W.Bol).getY() + sin_ALPHA * seatpost_extension);

		getPoint(W.Bspr).setLocation( 
			getPoint(W.Bspl).getX() + cos_SIGMA * parameters.get(Prm.STOD) / Math.sin( Math.PI - parameters.get(Prm.SIGMA) - parameters.get(Prm.ALPHA)) ,
			getPoint(W.Bspl).getY() + sin_SIGMA * parameters.get(Prm.STOD) / Math.sin( Math.PI - parameters.get(Prm.SIGMA) - parameters.get(Prm.ALPHA)));
		getPoint(W.Bsu).setLocation( 
			getPoint(W.Bspr).getX() + cos_ALPHA * seatpost_extension ,
			getPoint(W.Bspr).getY() - sin_ALPHA * seatpost_extension );

		// Cross-member junctions
		// ... w/ ST
		getPoint(W.Ji).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, Math.tan(angle_CEx), cmt_y_intercept_lower));
		getPoint(W.Jo).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, Math.tan(angle_CEx), cmt_y_intercept_upper));
		// ... and HT
		getPoint(W.Cmi).setLocation( intersect( -tan_BETA, ht_y_intercept_lower, Math.tan(angle_CEx), cmt_y_intercept_lower));
		getPoint(W.Cmo).setLocation( intersect( -tan_BETA, ht_y_intercept_lower, Math.tan(angle_CEx), cmt_y_intercept_upper));

		getPoint(W.Cor).setLocation( 
			getPoint(W.Col).getX() + parameters.get(Prm.HTOD) * sin_BETA,
			getPoint(W.Col).getY() + parameters.get(Prm.HTOD) * cos_BETA);

		getPoint(W.ColPrime).setLocation(
			getPoint(W.Col).getX() - parameters.get(Prm.HTE) * cos_BETA,
			getPoint(W.Col).getY() + parameters.get(Prm.HTE) * sin_BETA);

		getPoint(W.CorPrime).setLocation( 
			getPoint(W.Cor).getX() - parameters.get(Prm.HTE) * cos_BETA,
			getPoint(W.Cor).getY() + parameters.get(Prm.HTE) * sin_BETA);

		getPoint(W.Dol).setLocation( getPoint(W.Rb));
		getPoint(W.Dor).setLocation( 
			getPoint(W.Dol).getX() + parameters.get(Prm.HTOD) * sin_BETA,
			getPoint(W.Dol).getY() + parameters.get(Prm.HTOD) * cos_BETA);

		// DT wraps around HT only up to that point
		Double angle_ht_dt= parameters.get(Prm.ALPHA) + angle_DAx;
		Double dt_to_ht_depth = (parameters.get(Prm.HTOD)/2) * Math.sin( Math.acos( parameters.get(Prm.DTOD)/parameters.get(Prm.HTOD)) );
		Double l = dt_to_ht_depth / Math.cos(angle_ht_dt - Math.PI/2);

		getPoint(W.Do).setLocation(
			getPoint(W.D).getX() - cos_DAx * l,
			getPoint(W.D).getY() - sin_DAx * l);

		getPoint(W.Sb).setLocation( 
			getPoint(W.Rb).getX() + cos_BETA * parameters.get(Prm.HTBO), // bottom edge of HT
			getPoint(W.Rb).getY() - sin_BETA * parameters.get(Prm.HTBO));

		getPoint(W.Sf).setLocation( 
			getPoint(W.Sb).getX() + parameters.get(Prm.HTOD) * sin_BETA,
			getPoint(W.Sb).getY() + parameters.get(Prm.HTOD) * cos_BETA);

		
		// Where lower DT line intersects w/ BB shell
		Double Aor_x = Math.cos(angle_DAx - Math.asin(parameters.get(Prm.DTOD)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2;
		getPoint(W.Aor).setLocation( 
			Aor_x,
			tan_DAx * Aor_x - dt_y_intercept_upper);

		// Where back ST line intersects w/ BB shell
		Double Aol_x = -Math.cos(parameters.get(Prm.ALPHA) - Math.asin(parameters.get(Prm.DTOD)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2;
		getPoint(W.Aol).setLocation( 
			Aol_x,
			-tan_ALPHA * Aol_x - st_y_intercept_upper);

		// Where ST/DT junction intersect w/ BB shell
		Double Ai_norm = Math.sqrt(getPoint(W.Ai).getX()*getPoint(W.Ai).getX() + getPoint(W.Ai).getY()*getPoint(W.Ai).getY());
		if (Ai_norm > parameters.get(Prm.BBOD)/2){
			getPoint(W.Aom).setLocation(
				getPoint(W.Ai).getX() * (parameters.get(Prm.BBOD)/(2*Ai_norm)),
				getPoint(W.Ai).getY() * (parameters.get(Prm.BBOD)/(2*Ai_norm)));
		} else {
			getPoint(W.Aom).setLocation( getPoint(W.Ai));
		}

		// Where top TT line intersects w/ ST
		getPoint(W.Bsor).setLocation( intersect( -tan_ALPHA, st_y_intercept_upper, tan_SIGMA, tt_y_intercept_upper));

		// Pedal position: closest to front wheel?
		getPoint(W.H).setLocation(
			getPoint(W.G).getX() * parameters.get(Prm.CL) / indicators.get(INDICATOR.BB_TO_FRONT),
			parameters.get(Prm.drop) * parameters.get(Prm.CL) / indicators.get(INDICATOR.BB_TO_FRONT));
		// Pedal position: horizontal?
		getPoint(W.H).setLocation(
			parameters.get(Prm.CL),
			0D
		);
		
		getPoint(W.Hw).setLocation(
			getPoint(W.G).getX() * (1 - parameters.get(Prm.WR) / indicators.get(INDICATOR.BB_TO_FRONT)),
			getPoint(W.G).getY() * (1 - parameters.get(Prm.WR) / indicators.get(INDICATOR.BB_TO_FRONT)));

		// Overlapping point on front fender
		getPoint(W.Hf).setLocation(
			getPoint(W.G).getX() * (1 - (parameters.get(Prm.WR)+parameters.get(Prm.FC)) /  indicators.get(INDICATOR.BB_TO_FRONT)),
			getPoint(W.G).getY() * (1 - (parameters.get(Prm.WR)+parameters.get(Prm.FC)) /  indicators.get(INDICATOR.BB_TO_FRONT)));

		// projecting Hf on the steerer axis
		getPoint(W.Hfs).setLocation(
			intersect( tan_BETAc, slope_point(tan_BETAc, getPoint(W.Hf)), -tan_BETA, slope_point(-tan_BETA, getPoint(W.D)))
		);

	
	
	};


	private void contour_chainstay(){
	
	
		Double chainstay_angle = parameters.get(Prm.ALPHA)-angle_EAB ;

		// Bottom bracket / chainstay junction
		getPoint(W.Acu).setLocation( 
				-Math.cos( chainstay_angle + Math.asin(parameters.get(Prm.CSODS)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2,
				Math.sin( chainstay_angle + Math.asin(parameters.get(Prm.CSODS)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2);

		getPoint(W.Acl).setLocation(
				-Math.cos( chainstay_angle - Math.asin(parameters.get(Prm.CSODS)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2,
				Math.sin( chainstay_angle - Math.asin(parameters.get(Prm.CSODS)/parameters.get(Prm.BBOD)) ) * parameters.get(Prm.BBOD)/2);

		// end of chainstay
		getPoint(W.Ecu).setLocation( 
				getPoint(W.E).getX() + parameters.get(Prm.RDOL) * Math.cos(chainstay_angle) + (parameters.get(Prm.CSODE)/2) * Math.sin(chainstay_angle),
				getPoint(W.E).getY() - parameters.get(Prm.RDOL) * Math.sin(chainstay_angle) + (parameters.get(Prm.CSODE)/2) * Math.cos(chainstay_angle));
		getPoint(W.Ecl).setLocation( 
				getPoint(W.E).getX() + parameters.get(Prm.RDOL) * Math.cos(chainstay_angle) - (parameters.get(Prm.CSODE)/2) * Math.sin(chainstay_angle),
				getPoint(W.E).getY() - parameters.get(Prm.RDOL) * Math.sin(chainstay_angle) - (parameters.get(Prm.CSODE)/2) * Math.cos(chainstay_angle));

	};

	private void contour_fork(){
	
		Double FBA = get_FBA();
		Double tg2 = Math.tan(parameters.get(Prm.BETA)-FBA);
		Point2D tmp_pt;
		// Computing fork blade length: partial perimeter of raked part + straight segment
		Double raked_length = (FBA/(2*Math.PI))*parameters.get(Prm.FBR);
		Double straight_length = (getPoint(W.F).getY() - getPoint(W.K).getY())/sin_BETA;
		// Computing taper angle
		Double taper_angle = Math.atan((parameters.get(Prm.BUD)-parameters.get(Prm.BLD))/(2*(raked_length+straight_length)));
		// Computing decrease in blade diameter at length K
		Double decrease = (straight_length/2) * Math.tan(taper_angle);
		Double y_intercept_M_blade_line = 0.0;
		Double y_intercept_N_blade_line = 0.0;
		Double y_intercept_Gl_blade_line = 0.0;
		Double y_intercept_Gr_blade_line = 0.0;
		Double y_intercept_fender_control_line_1 = 0.0;
		Double y_intercept_fender_control_line_2 = 0.0;
		Double y_intercept_fender_control_line_3 = 0.0;
		// calculating angle offset for given front parameters.get(Prm.drop)out offset
		// (offset is approximation of arc)
		Double angle_front_offset = Math.atan(parameters.get(Prm.FDO)/parameters.get(Prm.FBR));
		Double angle_at_offset = parameters.get(Prm.BETA)+angle_front_offset-FBA;

		
		// System.out.printf("Blade diameter: %f\n", parameters.get(Prm.BUD)-decrease*2);
		getPoint(W.M).setLocation( getPoint(W.K).getX() + ((parameters.get(Prm.BUD)/2)-decrease) * cos_BETAc, // fork bend start (upper)
			getPoint(W.K).getY() + ((parameters.get(Prm.BUD)/2)-decrease) * sin_BETAc);
		getPoint(W.N).setLocation( getPoint(W.K).getX() - ((parameters.get(Prm.BUD)/2)-decrease) * cos_BETAc, // fork bend start (lower)
			getPoint(W.K).getY() - ((parameters.get(Prm.BUD)/2)-decrease) * sin_BETAc);

		getPoint(W.Gm).setLocation( getPoint(W.L).getX() - (parameters.get(Prm.FBR)) * Math.sin(angle_at_offset),
			getPoint(W.L).getY() - (parameters.get(Prm.FBR)) * Math.cos(angle_at_offset));

		getPoint(W.Gl).setLocation( getPoint(W.Gm).getX() - (parameters.get(Prm.BLD)/2) * Math.sin(angle_at_offset),
			getPoint(W.Gm).getY() - (parameters.get(Prm.BLD)/2) * Math.cos(angle_at_offset));
		getPoint(W.Gr).setLocation( getPoint(W.Gm).getX() + (parameters.get(Prm.BLD)/2) * Math.sin(angle_at_offset),
			getPoint(W.Gm).getY() + (parameters.get(Prm.BLD)/2) * Math.cos(angle_at_offset));

		y_intercept_M_blade_line = slope_point(-Math.tan(parameters.get(Prm.BETA)+taper_angle), getPoint(W.M));
		y_intercept_N_blade_line = slope_point(-Math.tan(parameters.get(Prm.BETA)-taper_angle), getPoint(W.N));
		y_intercept_Gl_blade_line = slope_point(-(tg2-taper_angle), getPoint(W.Gl)); 
		y_intercept_Gr_blade_line = slope_point(-(tg2+taper_angle), getPoint(W.Gr)); 

		// 1 Control point for quadratic Bezier fork bend (upper)
		tmp_pt = intersect( -Math.tan(parameters.get(Prm.BETA)+taper_angle), y_intercept_M_blade_line, -tg2-taper_angle, y_intercept_Gr_blade_line);

		// 2 control points for cubic Bezier fork bend (upper)
		getPoint(W.A1).setLocation( getPoint(W.M).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.M).getX()),
			getPoint(W.M).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.M).getY()));
		getPoint(W.A2).setLocation( getPoint(W.Gr).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Gr).getX()),
			getPoint(W.Gr).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Gr).getY()));

		
		// 1 Control point for quadratic Bezier fork bend (lower)
		tmp_pt = intersect( -Math.tan(parameters.get(Prm.BETA)-taper_angle), y_intercept_N_blade_line, -tg2+taper_angle, y_intercept_Gl_blade_line);

		// 2 control points for cubic Bezier fork bend (lower)
		getPoint(W.A3).setLocation( getPoint(W.N).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.N).getX()),
			getPoint(W.N).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.N).getY()));
		getPoint(W.A4).setLocation( getPoint(W.Gl).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Gl).getX()),
			getPoint(W.Gl).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Gl).getY()));
		
		// Headset

		getPoint(W.Tb).setLocation( getPoint(W.Sb).getX() + cos_BETA * parameters.get(Prm.BHS), // bottom edge of headset
			getPoint(W.Sb).getY() - sin_BETA * parameters.get(Prm.BHS));
		getPoint(W.Tf).setLocation( getPoint(W.Tb).getX() + parameters.get(Prm.HTOD) * sin_BETA,
			getPoint(W.Tb).getY() + parameters.get(Prm.HTOD) * cos_BETA);
		getPoint(W.Tm).setLocation( (getPoint(W.Tb).getX()+getPoint(W.Tf).getX())/2.0, (getPoint(W.Tb).getY()+getPoint(W.Tf).getY())/2.0);

		tmp_pt = intersect( tan_BETAc, slope_point(tan_BETAc, getPoint(W.Tf)),
				-Math.tan(parameters.get(Prm.BETA)-Math.PI/6), slope_point(-Math.tan(parameters.get(Prm.BETA)-Math.PI/6), getPoint(W.Sf)));
		getPoint(W.F1).setLocation( getPoint(W.Sf).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Sf).getX()),
			getPoint(W.Sf).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Sf).getY()));
		getPoint(W.F2).setLocation( getPoint(W.Tf).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Tf).getX()),
			getPoint(W.Tf).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Tf).getY()));

		tmp_pt = intersect( tan_BETAc, slope_point(tan_BETAc, getPoint(W.Tf)),
				Math.tan(-parameters.get(Prm.BETA)-Math.PI/6), slope_point(Math.tan(-parameters.get(Prm.BETA)-Math.PI/6), getPoint(W.Sb)));
		getPoint(W.F4).setLocation( getPoint(W.Sb).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Sb).getX()),
			getPoint(W.Sb).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Sb).getY()));
		getPoint(W.F3).setLocation( getPoint(W.Tb).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Tb).getX()),
			getPoint(W.Tb).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Tb).getY()));

		// Fork crown
		getPoint(W.Ub).setLocation( getPoint(W.Tb).getX() + cos_BETA * parameters.get(Prm.FCH), // bottom edge of fork crown
			getPoint(W.Tb).getY() - sin_BETA * parameters.get(Prm.FCH));
		getPoint(W.Uf).setLocation( getPoint(W.Ub).getX() + parameters.get(Prm.HTOD) * sin_BETA,
			getPoint(W.Ub).getY() + parameters.get(Prm.HTOD) * cos_BETA);

		tmp_pt.setLocation( (3*getPoint(W.Sb).getX() + getPoint(W.Sf).getX())/4,	// Fork crown lug (back half)
				 (3*getPoint(W.Sb).getY() + getPoint(W.Sf).getY())/4);	
		getPoint(W.G0).setLocation( (getPoint(W.Ub).getX() + getPoint(W.Uf).getX())/2 + 2.5 * cos_BETA,
			(getPoint(W.Ub).getY() + getPoint(W.Uf).getY())/2 - 2.5 * sin_BETA);
		getPoint(W.G1).setLocation( getPoint(W.Ub).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Ub).getX()),
			getPoint(W.Ub).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Ub).getY()));
		getPoint(W.G2).setLocation( getPoint(W.G0).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.G0).getX()),
			getPoint(W.G0).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.G0).getY()));
		tmp_pt.setLocation( (getPoint(W.Sb).getX() + 3*getPoint(W.Sf).getX())/4,	// Fork crown lug (front half)
			(getPoint(W.Sb).getY() + 3*getPoint(W.Sf).getY())/4);	
		getPoint(W.G3).setLocation( getPoint(W.G0).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.G0).getX()),
			getPoint(W.G0).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.G0).getY()));
		getPoint(W.G4).setLocation( getPoint(W.Uf).getX() + (2.0/3) * (tmp_pt.getX() - getPoint(W.Uf).getX()),
			getPoint(W.Uf).getY() + (2.0/3) * (tmp_pt.getY() - getPoint(W.Uf).getY()));


		// Start of fork blades
		getPoint(W.Vb).setLocation( getPoint(W.Ub).getX() + (parameters.get(Prm.HTOD)-parameters.get(Prm.BUD))/2 * sin_BETA,
			getPoint(W.Ub).getY() + (parameters.get(Prm.HTOD)-parameters.get(Prm.BUD))/2 * cos_BETA);
		getPoint(W.Vf).setLocation( getPoint(W.Uf).getX() - (parameters.get(Prm.HTOD)-parameters.get(Prm.BUD))/2 * sin_BETA,
			getPoint(W.Uf).getY() - (parameters.get(Prm.HTOD)-parameters.get(Prm.BUD))/2 * cos_BETA);


		// Fenders: Q-Bezier control points (fender has a 90° extension)
		/*
		Double fender_outer_radius = parameters.get(Prm.WR) + parameters.get(Prm.FC);
		Double fender_inner_radius = fender_outer_radius - parameters.get(Prm.FH);
		Double fender_angle_start = radians( -130.0 );
		Double fender_extension = radians( 160.0 );

		getPoint(W.Fflo).setLocation( getPoint(W.G).getX() + Math.cos( fender_angle_start ) * fender_outer_radius;
			getPoint(W.G).getY() + Math.sin( fender_angle_start ) * fender_outer_radius;
		getPoint(W.Ffli).setLocation( getPoint(W.G).getX() + Math.cos( fender_angle_start ) * fender_inner_radius;
			getPoint(W.G).getY() + Math.sin( fender_angle_start ) * fender_inner_radius;
		fender_angle_start -= radians(3.0);	
		fender_extension -= radians(6.0);	
		getPoint(W.Ffli).setLocation( getPoint(W.G).getX() + Math.cos( fender_angle_start ) * fender_inner_radius;
			getPoint(W.G).getY() + Math.sin( fender_angle_start ) * fender_inner_radius;
		getPoint(W.Ffu).setLocation( getPoint(W.G).getX() + Math.cos( fender_angle_start - fender_extension ) * fender_inner_radius;
			getPoint(W.G).getY() + Math.sin( fender_angle_start - fender_extension ) * fender_inner_radius;
		*/

		// Stem
		Double small_parallelog_side =  parameters.get(Prm.STHD) / sin_BETA;
		Double big_parallelog_side = parameters.get(Prm.STQD) / sin_BETA;

		getPoint(W.Stbl).setLocation( getPoint(W.ColPrime).getX() + sin_BETA * ( parameters.get(Prm.HTOD) - parameters.get(Prm.STQD) ) / 2.0,
			getPoint(W.ColPrime).getY() + cos_BETA * ( parameters.get(Prm.HTOD) - parameters.get(Prm.STQD) ) / 2.0);
		getPoint(W.Stbr).setLocation( getPoint(W.CorPrime).getX() - sin_BETA * ( parameters.get(Prm.HTOD) - parameters.get(Prm.STQD) ) / 2.0,
			getPoint(W.CorPrime).getY() - cos_BETA * ( parameters.get(Prm.HTOD) - parameters.get(Prm.STQD) ) / 2.0);

		getPoint(W.Stur).setLocation( getPoint(W.Stmr).getX() - big_parallelog_side / 2.0 - cos_BETA * small_parallelog_side / 2.0 ,
			getPoint(W.Stmr).getY() + parameters.get(Prm.STHD) / 2.0);

		getPoint(W.Stlr).setLocation( getPoint(W.Stur).getX() + big_parallelog_side + cos_BETA * small_parallelog_side ,
			getPoint(W.Stmr).getY() - parameters.get(Prm.STHD) / 2.0 );

		getPoint(W.Stuf).setLocation( getPoint(W.Stmf).getX() - Math.sqrt( parameters.get(Prm.STCD) / 2.0 * parameters.get(Prm.STCD) / 2.0 - parameters.get(Prm.STHD) / 2.0 * parameters.get(Prm.STHD) / 2.0),
					getPoint(W.Stmf).getY() + parameters.get(Prm.STHD) / 2.0);

		getPoint(W.Stlf).setLocation( getPoint(W.Stuf).getX(),
				getPoint(W.Stlr).getY());
	
		// head-tube, steering axis point
		getPoint(W.Stbm).setLocation(	(getPoint(W.Stbl).getX() + getPoint(W.Stbr).getX())/2,
						(getPoint(W.Stbl).getY() + getPoint(W.Stbr).getY())/2 );
		// System.out.printf("Stbm: %f, %f", getPoint(W.Stbm).getX(), getPoint(W.Stbm).getY());
		// above head-tube, steering axis point (end of threaded steerer)
		getPoint(W.Stbh).setLocation(
						getPoint(W.Stbm).getX() - Math.cos(parameters.get(Prm.BETA))*parameters.get(Prm.THS),
						getPoint(W.Stbm).getY() + Math.sin(parameters.get(Prm.BETA))*parameters.get(Prm.THS));
		indicators.put(INDICATOR.STEERER_LENGTH, 
			getPoint(W.Stbh).distance( getPoint(W.F)));
	};

	private void contour_seatstay(){
		

		Double cos_CEx = Math.cos(angle_CEx);
		Double sin_CEx = Math.sin(angle_CEx);

		Double cos_BEx = Math.cos(angle_BEx);
		Double sin_BEx = Math.sin(angle_BEx);

		Double seatstay_top_offset_factor = 1.2; // wr/ Bs
		
		// Seatstay attachment
		getPoint(W.Ha).setLocation(
			getPoint(W.Bs).getX() + seatstay_top_offset_factor * cos_BEx ,
			getPoint(W.Bs).getY() + seatstay_top_offset_factor * sin_BEx );
		getPoint(W.Ma).setLocation( 
			getPoint(W.Ha).getX() - (parameters.get(Prm.SSLL)/2) * cos_BEx,
			getPoint(W.Ha).getY() - (parameters.get(Prm.SSLL)/2) * sin_BEx);
		getPoint(W.Mal).setLocation( 
			getPoint(W.Ma).getX() - (parameters.get(Prm.SSODS)/2) * sin_BEx,
			getPoint(W.Ma).getY() + (parameters.get(Prm.SSODS)/2) * cos_BEx); 
		getPoint(W.Mar).setLocation(
			getPoint(W.Ma).getX() + (parameters.get(Prm.SSODS)/2) * sin_BEx,
			getPoint(W.Ma).getY() - (parameters.get(Prm.SSODS)/2) * cos_BEx); 
		getPoint(W.Ba).setLocation(
			getPoint(W.Ma).getX() - (parameters.get(Prm.SSLL)/2) * cos_BEx,
			getPoint(W.Ma).getY() - (parameters.get(Prm.SSLL)/2) * sin_BEx);
			
		
		Point2D ptbz = new Point2D.Double();
		ptbz.setLocation( 
			getPoint(W.Ma).getX() - parameters.get(Prm.SSODS) * sin_BEx,
			getPoint(W.Ma).getY() + parameters.get(Prm.SSODS) * cos_BEx);
		
		// Bezier control points for leave cover
		getPoint(W.D1).setLocation( getPoint(W.Ha));
		getPoint(W.D4).setLocation( getPoint(W.Ba));
		getPoint(W.D2).setLocation( 
			getPoint(W.D1).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.D1).getX()),
			getPoint(W.D1).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.D1).getY()));
		getPoint(W.D3).setLocation( 
			getPoint(W.D4).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.D4).getX()),
			getPoint(W.D4).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.D4).getY()));

		
		ptbz.setLocation( 
			getPoint(W.Ma).getX() + parameters.get(Prm.SSODS) * sin_BEx,
			getPoint(W.Ma).getY() - parameters.get(Prm.SSODS) * cos_BEx);
		getPoint(W.D5).setLocation( 
			getPoint(W.D1).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.D1).getX()),
			getPoint(W.D1).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.D1).getY()));
		getPoint(W.D6).setLocation( 
			getPoint(W.D4).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.D4).getX()),
			getPoint(W.D4).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.D4).getY()));

		
		// end of seatstay
		getPoint(W.Esu).setLocation( 
			getPoint(W.E).getX() + parameters.get(Prm.RDOU) * cos_BEx - (parameters.get(Prm.SSODE)/2) * sin_BEx,
			getPoint(W.E).getY() + parameters.get(Prm.RDOU) * sin_BEx + (parameters.get(Prm.SSODE)/2) * cos_BEx);

		getPoint(W.Esl).setLocation( 
			getPoint(W.E).getX() + parameters.get(Prm.RDOU) * cos_BEx + (parameters.get(Prm.SSODE)/2) * sin_BEx,
			getPoint(W.E).getY() + parameters.get(Prm.RDOU) * sin_BEx - (parameters.get(Prm.SSODE)/2) * cos_BEx);
			

		// Cross-member (for mixte): upper part
		getPoint(W.Jua).setLocation( (
			getPoint(W.Ji).getX() + getPoint(W.Jo).getX() ) / 2.0 ,
			(getPoint(W.Ji).getY() + getPoint(W.Jo).getY() ) / 2.0 );
		getPoint(W.Ja).setLocation( 
			getPoint(W.Jua).getX() - (parameters.get(Prm.SSLL)/2) * cos_CEx,
			getPoint(W.Jua).getY() - (parameters.get(Prm.SSLL)/2) * sin_CEx);
		getPoint(W.Jal).setLocation( 
			getPoint(W.Ja).getX() - (parameters.get(Prm.SSODS)/2) * sin_CEx,
			getPoint(W.Ja).getY() + (parameters.get(Prm.SSODS)/2) * cos_CEx);
		getPoint(W.Jar).setLocation( 
			getPoint(W.Ja).getX() + (parameters.get(Prm.SSODS)/2) * sin_CEx,
			getPoint(W.Ja).getY() - (parameters.get(Prm.SSODS)/2) * cos_CEx); 
		getPoint(W.Jla).setLocation( 
			getPoint(W.Ja).getX() - (parameters.get(Prm.SSLL)/2) * cos_CEx,
			getPoint(W.Ja).getY() - (parameters.get(Prm.SSLL)/2) * sin_CEx);

		
		ptbz.setLocation( 
			getPoint(W.Ja).getX() - parameters.get(Prm.SSODS) * sin_CEx,
			getPoint(W.Ja).getY() + parameters.get(Prm.SSODS) * cos_CEx);
			
		getPoint(W.H1).setLocation( getPoint(W.Jua));
		getPoint(W.H4).setLocation( getPoint(W.Jla));
		getPoint(W.H2).setLocation( 
			getPoint(W.H1).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.H1).getX()),
			getPoint(W.H1).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.H1).getY()));
		getPoint(W.H3).setLocation(
			getPoint(W.H4).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.H4).getX()),
			getPoint(W.H4).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.H4).getY()));

		ptbz.setLocation( 
			getPoint(W.Ja).getX() + parameters.get(Prm.SSODS) * sin_CEx,
			getPoint(W.Ja).getY() - parameters.get(Prm.SSODS) * cos_CEx);

		getPoint(W.H5).setLocation( 
			getPoint(W.H1).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.H1).getX()),
			getPoint(W.H1).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.H1).getY()));
		
		getPoint(W.H6).setLocation(
			getPoint(W.H4).getX() + (2.0/3) * (ptbz.getX() - getPoint(W.H4).getX()),
			getPoint(W.H4).getY() + (2.0/3) * (ptbz.getY() - getPoint(W.H4).getY()));

		getPoint(W.Ecmu).setLocation( 
			getPoint(W.E).getX() + parameters.get(Prm.RDOU) * cos_CEx - (parameters.get(Prm.SSODE)/2) * sin_CEx,
			getPoint(W.E).getY() + parameters.get(Prm.RDOU) * sin_CEx + (parameters.get(Prm.SSODE)/2) * cos_CEx);

		getPoint(W.Ecml).setLocation( 
			getPoint(W.E).getX() + parameters.get(Prm.RDOU) * cos_CEx + (parameters.get(Prm.SSODE)/2) * sin_CEx,
			getPoint(W.E).getY() + parameters.get(Prm.RDOU) * sin_CEx - (parameters.get(Prm.SSODE)/2) * cos_CEx);
			

		};

	private void contour_crank(){
		
		Double crank_attachment_radius = 20.0;
		Double crank_overlap_angle = Math.atan( getPoint(W.G).getY() / getPoint(W.G).getX() );

		getPoint(W.Cal).setLocation( - crank_attachment_radius, 0);
		getPoint(W.Car).setLocation( crank_attachment_radius, 0);
		getPoint(W.Cel).setLocation( 0, -( parameters.get(Prm.CL) + parameters.get(Prm.CER)));
		getPoint(W.Cem).setLocation( 0, - parameters.get(Prm.CL));
		getPoint(W.Cell).setLocation( 
			getPoint(W.Cem).getX() - parameters.get(Prm.CER),
			getPoint(W.Cem).getY());
		getPoint(W.Celr).setLocation( 
			getPoint(W.Cem).getX() + parameters.get(Prm.CER),
			getPoint(W.Cem).getY());
		getPoint(W.Cat).setLocation( 
			-Math.sin( crank_overlap_angle ) * parameters.get(Prm.CER),
			Math.cos( crank_overlap_angle ) * parameters.get(Prm.CER));
		getPoint(W.Cab).setLocation( 
			Math.sin( crank_overlap_angle ) * parameters.get(Prm.CER),
			-Math.cos( crank_overlap_angle ) * parameters.get(Prm.CER));
		getPoint(W.Ceu).setLocation( 
			Math.cos( crank_overlap_angle ) * (parameters.get(Prm.CL) + parameters.get(Prm.CER)),
			Math.sin( crank_overlap_angle ) * (parameters.get(Prm.CL) + parameters.get(Prm.CER)));
		getPoint(W.Ceuu).setLocation( 
			getPoint(W.H).getX() - Math.sin( crank_overlap_angle ) * parameters.get(Prm.CER),
			getPoint(W.H).getY() + Math.cos( crank_overlap_angle ) * parameters.get(Prm.CER));
		getPoint(W.Ceul).setLocation( 
			getPoint(W.H).getX() + Math.sin( crank_overlap_angle ) * parameters.get(Prm.CER),
			getPoint(W.H).getY() - Math.cos( crank_overlap_angle ) * parameters.get(Prm.CER));


		// Toeclip end point
		getPoint(W.Tce).setLocation( 
			getPoint(W.H).getX() + parameters.get(Prm.FFL),
			getPoint(W.H).getY() + parameters.get(Prm.PH)/2.0 + parameters.get(Prm.TCH)/2.0);
		
		// Computing point on fender with same ordinate
		Double b = -2.0 * getPoint(W.G).getX();
		Double c = getPoint(W.G).getX() * getPoint(W.G).getX() + (getPoint(W.Tce).getY() - getPoint(W.G).getY()) * (getPoint(W.Tce).getY() - getPoint(W.G).getY()) - (parameters.get(Prm.WR)+parameters.get(Prm.FC)) * (parameters.get(Prm.WR)+parameters.get(Prm.FC));

		getPoint(W.Tcw).setLocation( (-b - Math.sqrt(b*b - 4*c))/2, getPoint(W.Tce).getY());


		indicators.put(INDICATOR.TOE_OVERLAP, compute_toe_overlap());

	};

	private Double compute_toe_overlap() {
		// 
		// Calculate the motion of the point on the wheel that is
		// closest (perpendicular) to the axis: the point rises as the wheel
		// turns, reaching the position closest to the pedal when the crank 
		// is approximately horizontal.

		// A: toeclip end point, from above (y = middle of pedal platform)
		Double pedal_x = getPoint(W.Tce).getX();
		Double pedal_y = 0D;
		Double pedal_z = parameters.get(Prm.QF)/2.0 + 50D;
		System.out.printf("\nPedal point: %f, %f, %f\n", pedal_x, pedal_y, pedal_z);

		Double alpha = 0D;
		Double y_delta = 0D;
		Double x_delta = 0D;
		Double z_delta = 0D;
		Double steps = 20D;
		Double twr = getPoint(W.Hf).distance(getPoint(W.Hfs));//parameters.get(Prm.WR) - parameters.get(Prm.FR);

		// deltas are applied to the reference point that is the projection of the front axle
		// on the steering axis
		Double x_orig = getPoint(W.Hfs).getX();
		Double y_orig = getPoint(W.Hfs).getY();
		Double z_orig = 0D;

		Double min_distance = 1000D;

		for (int i=0; i<=steps; i++){
			
			alpha = i/steps * Math.PI/2.0;
			x_delta = twr * Math.cos(alpha) * Math.sin(parameters.get(Prm.BETA));
			y_delta = twr * Math.cos(alpha) * Math.cos(parameters.get(Prm.BETA));
			z_delta = twr * Math.sin(alpha);
			//System.out.printf("%d: Alpha: %f --> x_delta: %f y_delta: %f z_delta: %f\n", i, alpha, x_delta, y_delta, z_delta );

			Double x_new = x_orig - x_delta;
			Double y_new = y_orig - y_delta;
			Double z_new = z_orig + z_delta;
			//System.out.printf("%d: Alpha: %f --> x_new: %f y_new: %f z_new: %f\n", i, alpha, x_new, y_new, z_new );

			min_distance = Math.min(
				min_distance,
				Math.sqrt( (x_new-pedal_x)*(x_new-pedal_x) + (y_new-pedal_y)*(y_new-pedal_y) + (z_new-pedal_z)*(z_new-pedal_z))
			);

			//System.out.printf("  Distance = %f\n", min_distance);
		}

		return -min_distance;
		
	}

	private void compute_rear_fork(){
	

		Double hBBOD = parameters.get(Prm.BBOD)/2;
		Double hBBW = parameters.get(Prm.BBW)/2;
		Double hRAW = parameters.get(Prm.RAW)/2;
		Double hTW = parameters.get(Prm.TW)/2;
		Double hCSODS = parameters.get(Prm.CSODS)/2;
		Double hCSODE = parameters.get(Prm.CSODE)/2;
		Double hCSODM ;

		// Center of BB shell
		getPoint(W.rA).setLocation( 0, 0);

		// BB sheel corners
		getPoint(W.rB).setLocation( hBBOD, hBBW);

		getPoint(W.rC).setLocation( hBBOD, -hBBW);

		getPoint(W.rD).setLocation( -hBBOD, -hBBW);

		getPoint(W.rE).setLocation( -hBBOD, hBBW);

		// Start end of the chainstay
		getPoint(W.rF).setLocation( -hBBOD, parameters.get(Prm.BBCSO) - hBBW);

		getPoint(W.rFl).setLocation( -hBBOD, getPoint(W.rF).getY() + parameters.get(Prm.CSODS)/2);

		getPoint(W.rFr).setLocation( -hBBOD, getPoint(W.rF).getY() - parameters.get(Prm.CSODS)/2);

		// Tapered end of the chainstay
		getPoint(W.rG).setLocation( -parameters.get(Prm.CSL) + parameters.get(Prm.RDOL), -hRAW);
		
		getPoint(W.rGr).setLocation( -parameters.get(Prm.CSL) + parameters.get(Prm.RDOL), -hRAW-hCSODE);

		getPoint(W.rGl).setLocation( -parameters.get(Prm.CSL) + parameters.get(Prm.RDOL), -hRAW+hCSODE);

		// Rear axle middle point
		getPoint(W.rH).setLocation( -parameters.get(Prm.CSL), 0);

		// Point closest to the tire on straight chainstay
		getPoint(W.rI).setLocation( 
			parameters.get(Prm.WR) - (parameters.get(Prm.CSL)+hTW),
			getPoint(W.rFl).getY() - (getPoint(W.rFl).getY()-getPoint(W.rGl).getY()) * (-getPoint(W.rI).getX() - hBBOD) / (parameters.get(Prm.CSL)-hBBOD)) ;
		
		/* For curved chainstay design:
		 * curve (and subsequent placement of points Jl, J, Jr is 
		 * dependent on the desired tire clearance and the chainstay taper
		 */

		// Starting with an approximation: tire clearance determines choice
		// of Jl, the middle control point of a quadratic Bezier
		getPoint(W.rJl).setLocation( 
			parameters.get(Prm.WR)-(parameters.get(Prm.CSL)+hTW),
			-(hTW + parameters.get(Prm.RTSC)));

		// Approximating the chainstay diameter at tire
		hCSODM = hCSODS  + getPoint(W.rJl).getX() * (hCSODS - hCSODE)/parameters.get(Prm.CSL);

		// Iterating (by interval searching), until desired clearance
		// is obtained on the curve
		Double dist_rt_clearance_curved = 0.0;
		Double l1, L1, h1;
		Double angle_a1 = 0.0, angle_mu = 0.0;
		int iter=1;

		// initial interval limits: [ Jl, Jl+1cm ]
		Double left = getPoint(W.rJl).getY();
		Double right = left - 10;
		Double PRECISION = 0.1;

		while((left - right) > PRECISION){

			getPoint(W.rJl).setLocation( getPoint(W.rJl).getX(), (left + right)/2.0);

			// System.out.printf("Iteration %d\n", iter);

			// Constructing the Bezier control points for the inner curvature
			// using Jl as middle control point

			// Start point: B1
			getPoint(W.B1).setLocation( 
				(getPoint(W.rFl).getX() + getPoint(W.rJl).getX())/2.0,
				(getPoint(W.rFl).getY() + getPoint(W.rJl).getY())/2.0);

			l1 = getPoint(W.B1).getY() - getPoint(W.rJl).getY();
			L1 = getPoint(W.B1).getX() - getPoint(W.rJl).getX();
			h1 = Math.sqrt(l1*l1 + L1*L1);
			angle_a1 = Math.atan(l1 / L1);
			
			// End point: B4
			angle_mu = Math.atan( (getPoint(W.rJl).getY() - getPoint(W.rGl).getY()) / (getPoint(W.rJl).getX() - getPoint(W.rGl).getX()));	

			getPoint(W.B4).setLocation( 
				getPoint(W.rJl).getX() - h1 * Math.cos(angle_mu),
				getPoint(W.rJl).getY() - h1 * Math.sin(angle_mu));

			// Calculating B2 and B3 from B1, Jl, and B4
			getPoint(W.B2).setLocation( 
				getPoint(W.B1).getX() + (2.0/3) * (getPoint(W.rJl).getX() - getPoint(W.B1).getX()),
				getPoint(W.B1).getY() + (2.0/3) * (getPoint(W.rJl).getY() - getPoint(W.B1).getY()));

			getPoint(W.B3).setLocation( 
				getPoint(W.B4).getX() + (2.0/3) * (getPoint(W.rJl).getX() - getPoint(W.B4).getX()),
				getPoint(W.B4).getY() + (2.0/3) * (getPoint(W.rJl).getY() - getPoint(W.B4).getY()));

			// Line B2B3 is tangent to the curve in (B2+B3)/2
			dist_rt_clearance_curved = -hTW - (getPoint(W.B2).getY() + getPoint(W.B3).getY())/2.0;
			// System.out.printf("Actual clearance = %f\n", dist_rt_clearance_curved);

			if (dist_rt_clearance_curved > parameters.get(Prm.RTSC)){
				right = getPoint(W.rJl).getY();
			} else {
				left = getPoint(W.rJl).getY();
			}
			iter++;

		}

		// Calculating J and Jl, on bisector of angles[Fl]-Jl-Gl
		Double angle_b1 = (((Math.PI/2.0) - angle_mu) - angle_a1) / 2.0 ;// bisector angle

		getPoint(W.rJ).setLocation( 
			getPoint(W.rJl).getX() + 2.0 * hCSODM * Math.sin(angle_b1),
			getPoint(W.rJl).getY() - 2.0 * hCSODM * Math.cos(angle_b1));

		getPoint(W.rJr).setLocation( 
			getPoint(W.rJl).getX() + 2.0 * hCSODM * Math.sin(angle_b1),
			getPoint(W.rJl).getY() - 2.0 * hCSODM * Math.cos(angle_b1));
	 

		// Constructing the Bezier control points for the outer curvature
		// using Jr as middle control point

		// Start point: C1
		getPoint(W.C1).setLocation( 
			(getPoint(W.rFr).getX() + getPoint(W.rJr).getX())/2.0,
			(getPoint(W.rFr).getY() + getPoint(W.rJr).getY())/2.0);

		l1 = getPoint(W.C1).getY() - getPoint(W.rJr).getY();
		L1 = getPoint(W.C1).getX() - getPoint(W.rJr).getX();
		h1 = Math.sqrt(l1*l1 + L1*L1);
		angle_a1 = Math.atan(l1 / L1);
		
		// End point: C4
		angle_mu = Math.atan( (getPoint(W.rJr).getY() - getPoint(W.rGr).getY()) / (getPoint(W.rJr).getX() - getPoint(W.rGr).getX()));	

		getPoint(W.C4).setLocation( 
			getPoint(W.rJr).getX() - h1 * Math.cos(angle_mu),
			getPoint(W.rJr).getY() - h1 * Math.sin(angle_mu));

		// Calculating C2 and C3 from C1, Jl, and C4
		getPoint(W.C2).setLocation( 
			getPoint(W.C1).getX() + (2.0/3) * (getPoint(W.rJr).getX() - getPoint(W.C1).getX()),
			getPoint(W.C1).getY() + (2.0/3) * (getPoint(W.rJr).getY() - getPoint(W.C1).getY()));

		getPoint(W.C3).setLocation( 
			getPoint(W.C4).getX() + (2.0/3) * (getPoint(W.rJr).getX() - getPoint(W.C4).getX()),
			getPoint(W.C4).getY() + (2.0/3) * (getPoint(W.rJr).getY() - getPoint(W.C4).getY()));

		getPoint(W.rK).setLocation( 
			parameters.get(Prm.WR) - (parameters.get(Prm.CSL)+hTW), 0);


		// crank attachment
		getPoint(W.rLr).setLocation( 0, -parameters.get(Prm.QF)/2);
		getPoint(W.rLl).setLocation( 0, getPoint(W.rLr).getY() + parameters.get(Prm.CAW));

		getPoint(W.rMr).setLocation(
			-parameters.get(Prm.CL) - parameters.get(Prm.CER), // Total crank length = nominal crank length + crank eye radius
			-parameters.get(Prm.QF)/2);
		getPoint(W.rMl).setLocation( 
			getPoint(W.rMr).getX(),
			-parameters.get(Prm.QF)/2 + parameters.get(Prm.CAW));

		// System.out.printf("BB->crank: %f\n", parameters.get(Prm.QF)/2 - (parameters.get(Prm.CAW) + parameters.get(Prm.BBW)/2));
	
	}

	private void compute_rear_fork_distances(){
	
		// Angle for straight chainstay (center)
		indicators.put(INDICATOR.CHAINSTAY_ANGLE_STRAIGHT, Math.atan((getPoint(W.rF).getX() - getPoint(W.rG).getX()) / (getPoint(W.rF).getY() - getPoint(W.rG).getY()) ));
		// Angle for curved chainstay (center)
		indicators.put(INDICATOR.CHAINSTAY_ANGLE_CURVED, Math.atan((getPoint(W.rF).getX() - getPoint(W.rJ).getX()) / (getPoint(W.rF).getY() - getPoint(W.rJ).getY())));

		// Crank clearance for straight chainstay
		indicators.put(INDICATOR.CRANK_CLEARANCE_STRAIGHT, distance_to_segment(getPoint(W.rMl), getPoint(W.rGr), getPoint(W.rFr)) );

		// Crank clearance for curved chainstay
		indicators.put(INDICATOR.CRANK_CLEARANCE_CURVED,  distance_to_segment(getPoint(W.rMl), getPoint(W.rGr), getPoint(W.rJr)) );
		
	
	}


	private Double distance_to_segment(Point2D pt, Point2D pt1, Point2D pt2){
		
		Double distance = 0D;
		Double slope = (pt2.getY()-pt1.getY())/(pt2.getX()-pt1.getX());

		// equation of the form ax + by + c = 0
		Double a = -slope;
		Double b = 1D;
		Double c = slope*pt1.getX()-pt1.getY();

		// d = |ax_0 + by_0 + c| / sqrt( a^2 + b^2 ) 
		distance = Math.abs( a*pt.getX() + b*pt.getY() + c ) / Math.sqrt( a*a + b*b);
		
		return distance;
	}

	private void compute_body(){}



	private Double cotan( Double a){
		if (a == 0.0) return -1.0;
		return 1/Math.tan(a);
	}

	private Double get_lemonds_saddle_height( Double pbh ){
		return pbh * .883;
	}

	private Double degrees( Double a ){
		return a/Math.PI*180.0;
	}
}


