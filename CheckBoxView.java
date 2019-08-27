import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

public class CheckBoxView extends JCheckBox implements Command {

	Presenter presenter;
	
	CheckBoxView(String s, String command, Presenter p, ActionListener al){
	
		super( s );
		setActionCommand( command );
		presenter = p;
		addActionListener( al );
		presenter.registerControl( command, this );
	
	}

	public void execute(){
		System.out.println("calling setLayer(" + getActionCommand() + ") on presenter");
		presenter.setOption(getActionCommand());
	}
}
