import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.Box;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import java.io.File;

/*
 * TODO
 *
 * - vue du triangle arrière (dimension, meilleurs contours)
 * - contours roues
 * - ajout de champs (designer, rider, description) dans le panneau de sauvegarde
 * - mesure d'angle dans la vue de fourche arrière
 * - menu contextuel dynamique pour faire apparaître une mesure d'angle
 * - potence et cintre
 * - cycliste virtuel (avec paramètres correspondants)
 */

public class Sketch implements ActionListener {

	static int WIDTH = 900;
	static int HEIGHT = 500;

	private DecimalFormat df;

	private Presenter presenter;


	public final static void main(String[] args){
		
		String config = null;

		System.out.printf("\ncount=%d, arg0= %s", args.length, args[0]);
		if (args.length>0){
			config =  args[0] ;
		}
		Sketch sk = new Sketch(config);

	}

	public Sketch(String config){
	
		setUIFont (new javax.swing.plaf.FontUIResource("Tahoma",Font.PLAIN,12));

		Geometry jd = new Geometry();
		presenter = new Presenter( jd );

		df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		df.applyPattern("#.0");
		
		// 0. Master frame
		JFrame masterFrame = new JFrame();
		masterFrame.setLayout( new BorderLayout() );

		// 0.0 Menu Bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add( menu ) ;

		MenuItemView pdfMenuItem = new MenuItemView("Export to PDF", "menu.item.topdf", presenter, this);
		menu.add( pdfMenuItem);
		MenuItemView forkPdfMenuItem = new MenuItemView("Export fork to PDF", "menu.item.forktopdf", presenter, this);
		menu.add( forkPdfMenuItem);
		MenuItemView saveMenuItem = new MenuItemView("Save configuration","menu.item.saveconfig", presenter, this);
		menu.add( saveMenuItem);
		MenuItemView saveAsMenuItem = new MenuItemView("Save as...","menu.item.saveasconfig", presenter, this);
		menu.add( saveAsMenuItem);
		MenuItemView loadMenuItem = new MenuItemView("Load configuration","menu.item.loadconfig", presenter, this);
		menu.add( loadMenuItem);
		MenuItemView exitMenuItem = new MenuItemView("Quit","menu.item.quit", presenter, this);
		menu.add( exitMenuItem);

		final JFileChooser fc = new JFileChooser();
		presenter.registerControl( "filechooser", fc );

		
		// 0.1 Drawing Panel
		JPanel drawingPanel = new JPanel();
		drawingPanel.setLayout(new BorderLayout());


			// 0.1.0 Tabs
		JTabbedPane sketchTabs = new JTabbedPane();
		sketchTabs.setPreferredSize(new Dimension(WIDTH/2,HEIGHT));

		ViewPane sidePanel = new SideViewPane( jd ) ;
		sidePanel.setBackground(Color.WHITE);
		sketchTabs.add( (JPanel) sidePanel, "Side view");
		presenter.registerObserver( sidePanel);

		ViewPane rearPanel = new RearViewPane( jd );
		rearPanel.setBackground(Color.WHITE);
		sketchTabs.add( (JPanel) rearPanel, "Chainstays");
		drawingPanel.add( sketchTabs );
		presenter.registerObserver( rearPanel);

			// 0.1.1 Display controls
		JPanel buttonBar = new JPanel();
		createDisplayControls(buttonBar, sketchTabs );
		drawingPanel.add(buttonBar, BorderLayout.PAGE_START);

		// 0.2 Control Panel	
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.LIGHT_GRAY);
		controlPanel.setLayout(new BorderLayout());
		presenter.registerControl("container.controlpanel", controlPanel);

			// 0.2.0 Parameters tabs
		JTabbedPane tabs = new JTabbedPane();
		createParametersControls(tabs, jd);
		tabs.setPreferredSize(new Dimension(WIDTH/2, HEIGHT));
		controlPanel.add(tabs, BorderLayout.LINE_START);


			// 0.2.1 Option bar (frame type)
		JPanel optionBar = new JPanel();
		createFrameTypeChoice( optionBar, sketchTabs, jd );
		controlPanel.add(optionBar, BorderLayout.PAGE_END);

		// Putting everything together
		masterFrame.setJMenuBar( menuBar );
		masterFrame.add( controlPanel, BorderLayout.LINE_START);
		masterFrame.add( drawingPanel, BorderLayout.CENTER);

		masterFrame.setSize(WIDTH,HEIGHT);
		masterFrame.validate();

		tabs.requestFocusInWindow();
	
		if (config != null){
			presenter.loadFromXMLFile(new File(config));
		}
		masterFrame.setVisible(true);

	}

	private void createFrameTypeChoice(JPanel p, JTabbedPane tabs, Geometry jd ){
		
		RadioButtonView buttonClassic = new RadioButtonView("Classic", "CLASSIC", presenter, this);
		RadioButtonView buttonMixte = new RadioButtonView("Mixte", "MIXTE", presenter, this);
		RadioButtonView buttonCrossed = new RadioButtonView("Crossed", "CROSSED", presenter, this);
		switch( jd.getFrameType()){
			case CLASSIC:
				buttonClassic.setSelected( true );
				break;
			case MIXTE:
				buttonMixte.setSelected( true );
				break;
			case CROSSED:
				buttonCrossed.setSelected( true );
				break;
			default: ;
		}

		ButtonGroup group = new ButtonGroup();
		group.add( buttonClassic);
		group.add( buttonMixte);
		group.add( buttonCrossed);

		p.add( buttonClassic );
		p.add( buttonMixte );
		p.add( buttonCrossed );
		
	}

	private void createDisplayControls(JPanel bb, JTabbedPane tabs){

		CheckBoxView schematicsCb = new CheckBoxView("Schematics", "schematics_checkbox", presenter, this );
		schematicsCb.setSelected( ViewPane.hasLayer( ViewPane.Layer.SCHEMATICS ));
		bb.add( schematicsCb );

		CheckBoxView contoursCb = new CheckBoxView("Contours", "contours_checkbox", presenter, this );
		contoursCb.setSelected( ViewPane.hasLayer( ViewPane.Layer.CONTOURS ));
		bb.add( contoursCb );

		CheckBoxView pointsCb = new CheckBoxView("Points", "points_checkbox", presenter, this );
		pointsCb.setSelected( ViewPane.hasLayer( ViewPane.Layer.POINTS ));
		bb.add( pointsCb );

		CheckBoxView cotesCb = new CheckBoxView("Dimensions", "dimensions_checkbox", presenter, this );
		cotesCb.setSelected( ViewPane.hasLayer( ViewPane.Layer.DIMENSIONS ));
		bb.add( cotesCb );
	}
	
	private void createParametersControls(JTabbedPane tbs, Geometry jd){

		JPanel ftcTab = new JPanel(); // front triangle params.
		JPanel rtcTab = new JPanel(); // rear triangle params.
		JPanel frkTab = new JPanel(); // fork params.
		JPanel tbTab = new JPanel(); // components params.
		JPanel hmnTab  = new JPanel(); // human propulsion

		// Populates the tabs with their respective fields
		for (Geometry.Prm prm: Geometry.Prm.values()){
			Double parameterValue = Double.valueOf(jd.getParameter(prm));
			String parameterString = "";
			if (df != null) parameterString = df.format(parameterValue);
			TextFieldView tf = new TextFieldView( parameterString, 5, prm.toString(), presenter, this);

			JPanel singleParameterPanel = new JPanel();
			singleParameterPanel.add( new JLabel( prm.getLabel() ));
			singleParameterPanel.add( tf );

			switch( prm.getCategory() ){
				case FT_TRIANGLE:
					ftcTab.add( singleParameterPanel );
					break;
				case RR_TRIANGLE:
					rtcTab.add( singleParameterPanel );
					break;
				case FORK:
					frkTab.add( singleParameterPanel );
					break;
				case COMPONENTS:
					tbTab.add( singleParameterPanel );
					break;
				case ENGINE:
					hmnTab.add( singleParameterPanel );
					break;
				default: ;
			}
		}
		
		JPanel curvedChainstayPanel = new JPanel();
		curvedChainstayPanel.add( new JLabel( "Curved chainstays" ));
		CheckBoxView curvedChainstayCb = new CheckBoxView("Curved chainstays", "chainstay_checkbox", presenter, this );	
		curvedChainstayCb.setSelected( jd.hasCurvedChainstay());
		curvedChainstayPanel.add( curvedChainstayCb );
		rtcTab.add( curvedChainstayCb );

		tbs.add(ftcTab, "Front triangle");
		tbs.add(rtcTab, "Rear triangle");
		tbs.add(frkTab, "Fork");
		tbs.add(tbTab, "Components");
		tbs.add(hmnTab, "Engine");


	}
	
	public void actionPerformed(ActionEvent e){
		Command c = (Command) e.getSource();
		c.execute();
	}
	

	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
		Object key = keys.nextElement();
		Object value = UIManager.get (key);
		if (value != null && value instanceof javax.swing.plaf.FontUIResource)
			UIManager.put (key, f);
		}
	} 


}
