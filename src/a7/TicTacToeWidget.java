package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

	/* Enum to identify player. */
	
	private enum Player {BLACK, WHITE};
	
	private JSpotBoard _board;		/* SpotBoard playing area. */
	private JLabel _message;		/* Label for messages. */
	private boolean _game_won;		/* Indicates if games was been won already.*/
	private Player _next_to_play;	/* Identifies who has next turn. */
	
	public TicTacToeWidget() {
		
		/* Create SpotBoard and message label. */
		
		_board = new JSpotBoard(3,3);
		for (Spot s : _board) {
			s.setBackground(Color.GRAY);
		}
		_message = new JLabel();
		/* Set layout and place SpotBoard at center. */
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

		/* Create subpanel for message area and reset button. */
		
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());

		/* Reset button. Add ourselves as the action listener. */
		
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);

		/* Add subpanel in south area of layout. */
		
		add(reset_message_panel, BorderLayout.SOUTH);

		/* Add ourselves as a spot listener for all of the
		 * spots on the spot board.
		 */
		_board.addSpotListener(this);

		/* Reset game. */
		resetGame();
	}

	/* resetGame
	 * 
	 * Resets the game by clearing all the spots on the board,
	 * picking a new secret spot, resetting game status fields, 
	 * and displaying start message.
	 * 
	 */

	private void resetGame() {
		/* Clear all spots on board. Uses the fact that SpotBoard
		 * implements Iterable<Spot> to do this in a for-each loop.
		 */

		for (Spot s : _board) {
			s.clearSpot();
		}

		/* Reset the background of the old secret spot.
		 * Check _secret_spot for null first because call to 
		 * resetGame from constructor won't have a secret spot 
		 * chosen yet.
		 */
		
		/* Reset game won and next to play fields */
		_game_won = false;
		_next_to_play = Player.WHITE;		
		
		/* Display game start message. */
		
		_message.setText("Welcome to Tic Tac Toe. White to play");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Handles reset game button. Simply reset the game. */
				resetGame();
	}
	
	@Override
	public void spotClicked(Spot s) {
		
		/* If game already won, do nothing. */
		if (_game_won) {
			return;
		}


		/* Set up player and next player name strings,
		 * and player color as local variables to
		 * be used later.
		 */
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.WHITE) {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;			
		}
				
		
		/* Set color of spot clicked and toggle. */
		if (!s.isEmpty()) {
			return;
		}
		s.setSpotColor(player_color);
		s.toggleSpot();

	
		if (s.getSpotX() == 0){
			if (!_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).isEmpty() &&
					(!_board.getSpotAt(s.getSpotX()+2, s.getSpotY()).isEmpty())){
				if (player_color.equals(_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).getSpotColor()) &&
						s.getSpotColor().equals(_board.getSpotAt(s.getSpotX()+2, s.getSpotY()).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (s.getSpotX() == 1){
			if (!_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).isEmpty() &&
					(!_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).isEmpty())){
				if (s.getSpotColor().equals(
						_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).getSpotColor()) &&
						s.getSpotColor().equals(
						_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (s.getSpotX() == 2){
			if (!_board.getSpotAt(s.getSpotX()-2, s.getSpotY()).isEmpty() &&
					(!_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).isEmpty())){
					if (s.getSpotColor().equals(
							_board.getSpotAt(s.getSpotX()-2, s.getSpotY()).getSpotColor()) &&
							s.getSpotColor().equals(
							_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).getSpotColor())) {
								_game_won = true;
					}
			}
		}
		
		if (s.getSpotY() == 0){
			if (!_board.getSpotAt(s.getSpotY(), s.getSpotY()+1).isEmpty() &&
					(!_board.getSpotAt(s.getSpotY(), s.getSpotY()+2).isEmpty())){
				if (s.getSpotColor().equals(
					_board.getSpotAt(s.getSpotX(), s.getSpotY()+1).getSpotColor()) &&
					s.getSpotColor().equals( 
					_board.getSpotAt(s.getSpotX(), s.getSpotY()+2).getSpotColor())) {
						_game_won = true;
				}
			}
		}
		
		if (s.getSpotY() == 1){
			if (!_board.getSpotAt(s.getSpotX(), s.getSpotY()-1).isEmpty() &&
					(!_board.getSpotAt(s.getSpotY(), s.getSpotY()+1).isEmpty())){
				if (s.getSpotColor().equals(
					_board.getSpotAt(s.getSpotX(), s.getSpotY()-1).getSpotColor())&&
					s.getSpotColor().equals(
					_board.getSpotAt(s.getSpotX(), s.getSpotY()+1).getSpotColor())) {
						_game_won = true;
				}
			}
		}
		if (s.getSpotY() == 2){
			if (!_board.getSpotAt(s.getSpotX(), s.getSpotY()-2).isEmpty() &&
					(!_board.getSpotAt(s.getSpotX(), s.getSpotY()-1).isEmpty())){
					if (s.getSpotColor().equals(
						_board.getSpotAt(s.getSpotX(), s.getSpotY()-2).getSpotColor())&&
						s.getSpotColor().equals(
						_board.getSpotAt(s.getSpotX(), s.getSpotY()-1).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (s.getSpotX() == 0 && s.getSpotY() == 0) {
			if (!_board.getSpotAt(1, 1).isEmpty() && (!_board.getSpotAt(2, 2).isEmpty())){
					if (s.getSpotColor().equals(
						_board.getSpotAt(1,1).getSpotColor())&&
						s.getSpotColor().equals(
						_board.getSpotAt(2,2).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (s.getSpotX() == 1 && s.getSpotY() == 1) {
			if (!_board.getSpotAt(2, 2).isEmpty() && (!_board.getSpotAt(0, 0).isEmpty())){
					if (s.getSpotColor().equals(
						_board.getSpotAt(2,2).getSpotColor())&&
						s.getSpotColor().equals(
						_board.getSpotAt(0,0).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (s.getSpotX() == 2 && s.getSpotY() == 2) {
			if (!_board.getSpotAt(1, 1).isEmpty() && (!_board.getSpotAt(0, 0).isEmpty())){
					if (s.getSpotColor().equals(
						_board.getSpotAt(1,1).getSpotColor())&&
						s.getSpotColor().equals(
						_board.getSpotAt(0,0).getSpotColor())) {
							_game_won = true;
				}
			}
		}
		
		if (_game_won)  {
			_message.setText(player_name + " wins!");
		}
		
		boolean drawCheck = true;
		if (!_game_won) {
			for ( Spot sss : _board) {
				if (sss.isEmpty()) {
					drawCheck = false;
				}
			}
			if (drawCheck == true) {
				_message.setText("Draw game.");
			}
			else {
				_message.setText(next_player_name + " to play");
			}
		}
	}

	@Override
	public void spotEntered(Spot s) {
		/* Highlight spot if game still going on. */
		if (_game_won) {
			return;
		}
		if (!s.isEmpty()){
			return;
		}
		s.highlightSpot();
	}

	@Override
	public void spotExited(Spot s) {
		/* Unhighlight spot. */
		
		s.unhighlightSpot();
	}
	
}