
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class MouseHandler extends MouseAdapter {

	ViewPane sk;

	public MouseHandler( JComponent c ){
		sk=(ViewPane) c;
	}

	public void mouseClicked(MouseEvent e){
		System.out.printf("Click location: (%d, %d)\n", e.getX(), e.getY());
		switch(e.getButton()){
			case MouseEvent.BUTTON1:
				int mod = e.getModifiersEx();
				if ((mod & MouseEvent.CTRL_DOWN_MASK) != 0 ){
					if ((mod & MouseEvent.SHIFT_DOWN_MASK) != 0){
						sk.zoom(e.getX(), e.getY());
					} else { 
						sk.unzoom(e.getX(), e.getY());
					}
				} else {
					sk.center( e.getX(), e.getY());
				}
				sk.repaint();
				
				break;
			case MouseEvent.BUTTON2:
				//showContextualMenu( e );
				break;

			case MouseEvent.BUTTON3:
				break;
			default:;
		}

	}

	public void mouseEntered(MouseEvent e){
		System.out.println("Mouse in drawing pad");
		sk.setMouseIn( true );
		sk.requestFocus();
	}

	public void mouseExited(MouseEvent e){
		sk.setMouseIn( false );
	}

	public void mousePressed(MouseEvent e){
	}
	
	
	public void mouseReleased(MouseEvent e){
	}

	private void showContextualMenu( MouseEvent e ){
		if (e.isPopupTrigger()){
			//sk.getMenu().show( e.getComponent(), e.getX(), e.getY());
		}
	}
	

}
