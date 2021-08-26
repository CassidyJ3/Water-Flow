import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class EPanel extends JPanel implements MouseListener{

	private float elevation;
	private int xLoc;
	private int yLoc;
	private boolean visited = false;
	private ADelegate wf;
	
	public EPanel(ADelegate wf, float elevation, int x, int y){
		this.elevation = elevation;
		setxLoc(x);
		setyLoc(y);
		this.wf = wf;
	}
		
	@Override
	public void mouseClicked(MouseEvent arg0) {
		wf.handleAction(xLoc, yLoc);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println(elevation);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited() {
		this.visited = true;
	}


	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}


	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}


}
