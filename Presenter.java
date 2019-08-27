import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DateFormat;
import java.util.Date;



public class Presenter {
	
		
	private Geometry data;
	private List<JPanel> observers;
	private Map<String, JComponent> controls;

	public static final boolean UPDATE_PANEL = true;
	public static final boolean SILENT = false;

			



	public Presenter( Geometry jd){
		data = jd;
		observers = new ArrayList<JPanel>();
		controls = new HashMap<String, JComponent>();
	}

	public void registerControl(String s, JComponent jc){
		controls.put( s, jc );
	}

	public void registerObserver(JPanel jp){
		observers.add( jp );
	}

	public void commitParameters(){	
		System.out.println("commitParameters()");
		for (Geometry.Prm prm: Geometry.Prm.values()){
			JTextField tf = (JTextField) controls.get( prm.toString() );
			data.setParameter( prm, Double.valueOf(tf.getText()) );
		}
		data.refresh();
		for(JPanel jp: observers){
			ViewPane vp = (ViewPane) jp;
			vp.update();
		}
	}

	public void menuAction(String id){
		System.out.println("menuAction("  + id + ")");
		if (id.equals( "menu.item.topdf" )){
			int fileCount = 1;
			try {
				for (JPanel jp: observers){
					ViewPane vp = (ViewPane) jp;
					String pageSize = "A0";
					if (fileCount==2){ pageSize = "A1"; }
					vp.toPdf(String.valueOf("frameset_" + fileCount + ".pdf"),pageSize);
					fileCount++;
				}
			} catch (IOException e){
				System.out.println(e);
			} catch (DocumentException e){
				System.out.println(e);
			}
		} else if (id.equals("menu.item.saveconfig")){

			saveOnXMLFile();
			
		} else if (id.equals("menu.item.saveasconfig")){

			saveOnNewXMLFile();

		} else if (id.equals("menu.item.loadconfig")){

			loadFromXMLFile();

		} else if (id.equals("menu.item.quit")){
			System.exit(0);
		}
	}

	public void setFrameFamily( String actionCommand, boolean update ){
		
		if (actionCommand.equals( "MIXTE" )){
			System.out.println("Switch to 'MIXTE' view");
			data.setFrameType( Geometry.FrameType.MIXTE); 
		} else if (actionCommand.equals( "CLASSIC")){
			System.out.println("Switch to 'CLASSIC' view");
			data.setFrameType( Geometry.FrameType.CLASSIC); 
		} else  if (actionCommand.equals( "CROSSED")){
			System.out.println("Switch to 'CROSSED' view");
			data.setFrameType( Geometry.FrameType.CROSSED); 
		}
		if ( update ){
			JRadioButton cb = (JRadioButton) controls.get( actionCommand );
			cb.setSelected( true );
			for(JPanel jp: observers){
				ViewPane vp = (ViewPane) jp;
				vp.update();
			}
		}
	}

	public void setOption( String actionCommand ){
		JCheckBox cb = (JCheckBox) controls.get( actionCommand );
		
		boolean toggle = cb.isSelected();
		if (actionCommand.equals("schematics_checkbox")){
			ViewPane.setLayer(ViewPane.Layer.SCHEMATICS, toggle);
		} else if (actionCommand.equals("contours_checkbox")){
			ViewPane.setLayer(ViewPane.Layer.CONTOURS, toggle);
		} else if (actionCommand.equals("dimensions_checkbox")){
			ViewPane.setLayer(ViewPane.Layer.DIMENSIONS, toggle);
		} else if (actionCommand.equals("points_checkbox")){
			ViewPane.setLayer(ViewPane.Layer.POINTS, toggle);
		} else if (actionCommand.equals("chainstay_checkbox")){
			data.setCurvedChainstay( toggle );
		}
		for(JPanel jp: observers){
			ViewPane vp = (ViewPane) jp;
			vp.update();
		}
			
	}	

	private void saveOnFile(){
		JFileChooser fc = (JFileChooser) controls.get("filechooser");
		int returnValue = fc.showSaveDialog( controls.get("container.controlpanel"));

		if (returnValue == JFileChooser.APPROVE_OPTION){
			try {
				BufferedWriter outputStream = new BufferedWriter(new FileWriter(fc.getSelectedFile()));

				for (Geometry.Prm prm: Geometry.Prm.values()){
					//JTextField tf = (JTextField) controls.get( prm.toString() );
					//data.setParameter( prm, Double.valueOf(tf.getText()) );
					outputStream.write(prm.toString() + " " + data.getRawParameter( prm ) );
					outputStream.newLine();

				}

				outputStream.close();
			} catch (IOException e){
				System.out.println(e);
			}
		}
	}

	private void saveOnXMLFile(){
		
		if( data.getFile() == null){
			saveOnNewXMLFile();
			return;
		}

		String modifiedTimeStr = DateFormat.getDateTimeInstance().format(new Date());	

		try {
			BufferedWriter outputStream = new BufferedWriter(new FileWriter(data.getFile()));

			writeConfig( outputStream, data.getCreationTime(), modifiedTimeStr);

			outputStream.close();
		} catch (IOException e){
			System.out.println(e);
		}
	}

	private void saveOnNewXMLFile(){
		
		JFileChooser fc = (JFileChooser) controls.get("filechooser");
		int returnValue = fc.showSaveDialog( controls.get("container.controlpanel"));

		String modifiedTimeStr = DateFormat.getDateTimeInstance().format(new Date());	

		if (returnValue == JFileChooser.APPROVE_OPTION){
			try {
				File file = fc.getSelectedFile();
				BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));

				writeConfig( outputStream, modifiedTimeStr, modifiedTimeStr );

				outputStream.close();

				data.setConfigFile( file );
				data.setCreationTime( modifiedTimeStr );
			} catch (IOException e){
				System.out.println(e);
			}
		}
	}

	private void writeConfig(BufferedWriter outputStream, String creationDateStr, String modifiedTimeStr) throws IOException {
		
				outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); outputStream.newLine();
				outputStream.write("<designer></designer>"); outputStream.newLine();
				outputStream.write("<rider></rider>"); outputStream.newLine();
				outputStream.write("<family>" + data.getFrameType() + "</family>"); outputStream.newLine(); 
				outputStream.write("<created>" + creationDateStr + "</created>"); outputStream.newLine();
				outputStream.write("<last-modified>" + modifiedTimeStr + "</last-modified>"); outputStream.newLine();
				outputStream.write("<parameters>"); outputStream.newLine();
				for (Geometry.Prm prm: Geometry.Prm.values()){
					//JTextField tf = (JTextField) controls.get( prm.toString() );
					//data.setParameter( prm, Double.valueOf(tf.getText()) );
					outputStream.write("<parameter name=\"" + prm.toString() + "\">" + data.getRawParameter( prm ) + "</parameter>" );
					outputStream.newLine();

				}
				outputStream.write("</parameters>");
				outputStream.newLine();
	}

	private void loadFromFile(){
			
		JFileChooser fc = (JFileChooser) controls.get("filechooser");
		int returnValue = fc.showOpenDialog( controls.get("container.controlpanel"));

		if (returnValue == JFileChooser.APPROVE_OPTION){
			try {
				File file = fc.getSelectedFile();
				Scanner sc = new Scanner( file );

				while(sc.hasNext()){
					String paramStr = sc.next();
					System.out.println( "Read String \"" + paramStr + "\"");
					double doubleVal = sc.nextDouble();
					data.setRawParameter( Geometry.Prm.valueOf(paramStr), doubleVal );
					JTextField tf = (JTextField) controls.get( paramStr );
					tf.setText( String.valueOf( data.getParameter( Geometry.Prm.valueOf(paramStr))));
				}

				commitParameters();

				sc.close();

				data.setConfigFile( file );
			} catch (IOException e){
				System.out.println(e);
			}
		}
	}

	public void loadFromXMLFile( File file ){
		
		try {
			BufferedReader br = new BufferedReader( new FileReader( file ));
			Pattern prmPattern = Pattern.compile("<parameter +name=\"([A-Za-z]+)\" *> *([0-9.]+) *</parameter>");
			Pattern familyPattern = Pattern.compile("<family> *([A-Z]+) *</family>");
			Pattern riderPattern = Pattern.compile("<rider>([A-Za-z ]+)</rider>");
			Pattern createdPattern = Pattern.compile("<created>(.+)</created>");
			Pattern modifiedPattern = Pattern.compile("<last-modified>(.+)</last-modified>");
			Matcher prmMatcher;
			Matcher familyMatcher;
			Matcher riderMatcher;
			Matcher createdMatcher;
			Matcher modifiedMatcher;
			boolean inParameters = false;

			while( br.ready()){
				String input = br.readLine();
				// Meta-data
				if (! inParameters ){
					if (Pattern.matches("<parameters>", input)){
						inParameters = true;
					} else {
						familyMatcher = familyPattern.matcher(input);
						riderMatcher = riderPattern.matcher(input);
						createdMatcher = createdPattern.matcher(input);
						modifiedMatcher = modifiedPattern.matcher(input);
						if (familyMatcher.find()){
							setFrameFamily( familyMatcher.group(1), SILENT);	
						} else if (riderMatcher.find()){
							System.out.println( riderMatcher.group(1));
						} else if (createdMatcher.find()){
							data.setCreationTime( createdMatcher.group(1));
						} else if (modifiedMatcher.find()){
							System.out.println( modifiedMatcher.group(1));
						}
					}
				// Parameters
				} else if (inParameters){
					prmMatcher = prmPattern.matcher(input);	
					if( prmMatcher.find()){
						String paramStr = prmMatcher.group(1);
						double doubleVal = Double.valueOf(prmMatcher.group(2));
						//System.out.println( paramStr + " = " + doubleVal );
					
						data.setRawParameter( Geometry.Prm.valueOf(paramStr), doubleVal );
						JTextField tf = (JTextField) controls.get( paramStr );
						tf.setText( String.valueOf( data.getParameter( Geometry.Prm.valueOf(paramStr))));
					}
				}
			}

			br.close();
			commitParameters();
			data.setConfigFile( file );

		} catch (IOException e){
			System.out.println(e);
		}
		
	}
	

	private void loadFromXMLFile(){
			
		JFileChooser fc = (JFileChooser) controls.get("filechooser");
		int returnValue = fc.showOpenDialog( controls.get("container.controlpanel"));

		if (returnValue == JFileChooser.APPROVE_OPTION){
			loadFromXMLFile( fc.getSelectedFile() );

		}
	}

	private void loadFromXMLFileOld(){
			
		JFileChooser fc = (JFileChooser) controls.get("filechooser");
		int returnValue = fc.showOpenDialog( controls.get("container.controlpanel"));

		if (returnValue == JFileChooser.APPROVE_OPTION){
			try {
				File file = fc.getSelectedFile();
				BufferedReader br = new BufferedReader( new FileReader( file ));
				Pattern prmPattern = Pattern.compile("<parameter +name=\"([A-Za-z]+)\" *> *([0-9.]+) *</parameter>");
				Pattern familyPattern = Pattern.compile("<family> *([A-Z]+) *</family>");
				Pattern riderPattern = Pattern.compile("<rider>([A-Za-z ]+)</rider>");
				Pattern createdPattern = Pattern.compile("<created>(.+)</created>");
				Pattern modifiedPattern = Pattern.compile("<last-modified>(.+)</last-modified>");
				Matcher prmMatcher;
				Matcher familyMatcher;
				Matcher riderMatcher;
				Matcher createdMatcher;
				Matcher modifiedMatcher;
				boolean inParameters = false;

				while( br.ready()){
					String input = br.readLine();
					// Meta-data
					if (! inParameters ){
						if (Pattern.matches("<parameters>", input)){
							inParameters = true;
						} else {
							familyMatcher = familyPattern.matcher(input);
							riderMatcher = riderPattern.matcher(input);
							createdMatcher = createdPattern.matcher(input);
							modifiedMatcher = modifiedPattern.matcher(input);
							if (familyMatcher.find()){
								setFrameFamily( familyMatcher.group(1), SILENT);	
							} else if (riderMatcher.find()){
								System.out.println( riderMatcher.group(1));
							} else if (createdMatcher.find()){
								data.setCreationTime( createdMatcher.group(1));
							} else if (modifiedMatcher.find()){
								System.out.println( modifiedMatcher.group(1));
							}
						}
					// Parameters
					} else if (inParameters){
						prmMatcher = prmPattern.matcher(input);	
						if( prmMatcher.find()){
							String paramStr = prmMatcher.group(1);
							double doubleVal = Double.valueOf(prmMatcher.group(2));
							//System.out.println( paramStr + " = " + doubleVal );
						
							data.setRawParameter( Geometry.Prm.valueOf(paramStr), doubleVal );
							JTextField tf = (JTextField) controls.get( paramStr );
							tf.setText( String.valueOf( data.getParameter( Geometry.Prm.valueOf(paramStr))));
						}
					}
				}

				br.close();
				commitParameters();
				data.setConfigFile( file );

			} catch (IOException e){
				System.out.println(e);
			}
		}
	}

}
