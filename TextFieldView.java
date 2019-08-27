import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class TextFieldView extends JTextField implements Command {

	Presenter presenter;
	
	TextFieldView(String s, int l, String key, Presenter p, ActionListener al){
	
		super( s, l );
		presenter = p;
		addActionListener( al );
		presenter.registerControl( key, this );
	
	}

	public void execute(){
		System.out.println("calling commitParameters() on presenter");
		presenter.commitParameters();
	}
}
