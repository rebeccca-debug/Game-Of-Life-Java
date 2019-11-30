package a8;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameOfLife extends JPanel implements ActionListener {
	
	private JSpotBoard board;
	
	public void run() {
		board = new JSpotBoard(250,250);
	}
	
	public static void main(String[] args) {
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Tic Tac Toe");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Create panel for content. Uses BorderLayout. */
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);

		/* Create ExampleWidget component and put into center
		 * of content panel.
		 */
		
		GameOfLife g = new GameOfLife();
		top_panel.add(g, BorderLayout.CENTER);


		/* Pack main frame and make visible. */
		
		main_frame.pack();
		main_frame.setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
