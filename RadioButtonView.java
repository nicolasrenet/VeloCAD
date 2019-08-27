import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

public class RadioButtonView extends JRadioButton implements Command {

	Presenter presenter;
	
	RadioButtonView(String s, String command, Presenter p, ActionListener al){
	
		super( s );
		setActionCommand( command );
		presenter = p;
		addActionListener( al );
		presenter.registerControl( command, this );
	
	}

	public void execute(){
		System.out.println("calling frameFamily() on presenter");
		presenter.setFrameFamily(getActionCommand(), Presenter.UPDATE_PANEL);
	}
}
