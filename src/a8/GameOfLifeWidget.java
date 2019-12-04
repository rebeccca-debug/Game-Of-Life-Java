package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
public class GameOfLifeWidget extends JPanel implements ActionListener, SpotListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpotBoard _board;
	private Thread gameRunner;
	private boolean running;
	private Timer timer;
	private JTextField width;
	private JTextField height;
	private JTextField lowBirth;
	private JTextField highBirth;
	private JTextField lowSurvive;
	private JTextField highSurvive;
	
	public GameOfLifeWidget() {
	/* Create SpotBoard and message label. */
	
	_board = new JSpotBoard(25,25);
	
	for (Spot s : _board) {
		s.setBackground(Color.BLACK);
	}
	
	
	/* Set layout and place SpotBoard at center. */
	
	this.setLayout(new BorderLayout());
	this.add(_board, BorderLayout.CENTER);

	/* Create subpanel for message area and reset button. */
	
	JPanel reset_message_panel = new JPanel();
	reset_message_panel.setLayout(new FlowLayout());

	/* Reset button. Add ourselves as the action listener. */
	JButton reset_button = new JButton("Reset");
	reset_button.addActionListener(this);
	reset_message_panel.add(reset_button);
	//reset_message_panel.add(_message, BorderLayout.CENTER);
	
	JButton random = new JButton("Randomize Field");
	random.addActionListener(this);
	reset_message_panel.add(random);
	
	width =  new JTextField("Width");
	reset_message_panel.add(width);
	JLabel x = new JLabel("x");
	reset_message_panel.add(x);
	height =  new JTextField("Height");
	reset_message_panel.add(height);
	
	JButton adjust_height = new JButton("Update Dimensions");
	adjust_height.addActionListener(this);
	reset_message_panel.add(adjust_height);
	
	JTextField time =  new JTextField("Time");
	reset_message_panel.add(time);
	running = false;
	
	JButton start_button = new JButton("Start/Stop");
	start_button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
			try {
				if(Integer.parseInt(time.getText())> 1000 || Integer.parseInt(time.getText()) < 10) {
					System.out.println("Invalid time");
					return;
				}
			}
			catch(Exception ex){
				System.out.println("Invalid time");
				return;
			}
        	if (!running) {
	        	gameRunner = new Thread(new Runnable() {
	        		JSpotBoard board = _board;
	    			public void run() {
	    				timer = new Timer(Integer.parseInt(time.getText()), new ActionListener() {
	    	                @Override
	    	                public void actionPerformed(ActionEvent e) {
	    	                   board.updateSpots();
	    	                }
	    	            });
	    	            timer.start();
	    			}
	    		});
	        	gameRunner.start();
	        	running = true;
	        }
        	else {
        		timer.stop();
        		running = false;
        	}
        }
    });
	reset_message_panel.add(start_button);

	/* Add subpanel in south area of layout. */
	JPanel thresholdPanel = new JPanel();
	thresholdPanel.setLayout(new FlowLayout());
	
	JButton torus = new JButton("Torus Mode");
	thresholdPanel.add(torus);
	JLabel birth = new JLabel("Birth");
	lowBirth =  new JTextField("Low");
	highBirth =  new JTextField("High");
	JLabel survive = new JLabel("Survive");
	lowSurvive =  new JTextField("Low");
	highSurvive =  new JTextField("High");
	JButton threshold = new JButton("Update Thresholds");
	torus.addActionListener(this);
	threshold.addActionListener(this);
	
	thresholdPanel.add(birth);
	thresholdPanel.add(lowBirth);
	thresholdPanel.add(highBirth);
	thresholdPanel.add(survive);
	thresholdPanel.add(lowSurvive);
	thresholdPanel.add(highSurvive);
	thresholdPanel.add(threshold);
	this.add(thresholdPanel, BorderLayout.NORTH);
	
	this.add(reset_message_panel, BorderLayout.SOUTH);

	/* Add ourselves as a spot listener for all of the
	 * spots on the spot board.
	 */
	_board.addSpotListener(this);
	this.revalidate();
	this.repaint();
}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Update Dimensions")){
			_board.removeSpotListener(this);
			this.remove(_board);
			_board = new JSpotBoard(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
			_board.addSpotListener(this);
			for (Spot s : _board) {
				s.setBackground(Color.BLACK);
			}
			this.add(_board, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}
		if (e.getActionCommand().equals("Reset")) {
			try {
				timer.stop();
				running = false;
				for (Spot s : _board) {
					if (s.isLiving()) {
						s.toggleLiving();
					}
				}
			}
			catch(Exception a){
				
			}
			for (Spot s : _board) {
				if (s.isLiving()) {
					s.toggleLiving();
				}
			}
		}
		if (e.getActionCommand().equals("Randomize Field")) {
			try {
				timer.stop();
				running = false;
			}
			catch(Exception a){
				
			}
			for (Spot s : _board) {
				if (s.isLiving()) {
					s.toggleLiving();
				}
			}
			Random ran = new Random();
			int numberOfCells = ran.nextInt(_board.getSpotWidth() * _board.getSpotHeight());
			for ( Spot s : _board) {
				double prob = ran.nextDouble();
				if ( prob <= ((double)numberOfCells / (_board.getSpotWidth() * _board.getSpotHeight()))) {
					s.toggleLiving();
				}
			}
		}
		if (e.getActionCommand().equals("Torus Mode")) {
			_board.toggleTorus();
		}
		if (e.getActionCommand().equals("Update Thresholds")){
			try {
				_board.updateThresholds(Integer.parseInt(lowBirth.getText()), Integer.parseInt(highBirth.getText()),
						Integer.parseInt(lowSurvive.getText()), Integer.parseInt(highSurvive.getText()));
			}
			catch(Exception a){
				System.out.println("Invalid Threshold");
			}
		}
	}
	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		spot.toggleLiving();
	}
	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		spot.toggleHighlight();
	}
	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		spot.toggleHighlight();
	}
}