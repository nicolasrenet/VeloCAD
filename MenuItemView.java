import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class MenuItemView extends JMenuItem implements Command {

	private Presenter presenter;
	private String id ;
	
	MenuItemView(String label, String idStr, Presenter p, ActionListener al){

		super( label );
		id = idStr;
		presenter = p;
		addActionListener( al );
		presenter.registerControl( id, this );
	
	}

	public void execute(){
		System.out.println("Menu action " + id + "()");
		presenter.menuAction( id );
	}
}
