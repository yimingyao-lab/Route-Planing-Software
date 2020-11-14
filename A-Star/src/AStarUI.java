import javax.swing.JFrame;

public class AStarUI {
	static JFrame frame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frame = new JFrame();
		WholePanel wholePanel = new WholePanel(frame);
		frame.setTitle("AStar");
		frame.getContentPane().add(wholePanel);
		frame.setSize(480, 360);
		frame.setVisible(true);
		// frame.pack();
	//

}
}
