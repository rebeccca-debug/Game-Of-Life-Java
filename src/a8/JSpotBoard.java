package a8;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/*
 * JSpotBoard is a user interface component that implements SpotBoard.
 * 
 * Spot width and spot height are specified to the constructor. 
 * 
 * By default, the spots on the spot board are set up with a checker board pattern
 * for background colors and yellow highlighting.
 * 
 * Uses SpotBoardIterator to implement Iterable<Spot>
 * 
 */

public class JSpotBoard extends JPanel implements SpotBoard {

	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
	private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;
	private int lowBirth = 3;
	private int highBirth = 3;
	private int lowSurvive = 2;
	private int highSurvive = 3;
	private boolean torus = false;
	private Spot[][] _spots;
	
	public JSpotBoard(int width, int height) {
		if (width < 10 || height < 10 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		setLayout(new GridLayout(height, width));
		_spots = new Spot[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				Color bg = ((x+y)%2 == 0) ? DEFAULT_BACKGROUND_LIGHT : DEFAULT_BACKGROUND_DARK;
				_spots[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
				((JSpot)_spots[x][y]).setPreferredSize(preferred_size);
				add(((JSpot) _spots[x][y]));
			}			
		}
	}

	// Getters for SpotWidth and SpotHeight properties
	
	@Override
	public int getSpotWidth() {
		return _spots.length;
	}
	
	@Override
	public int getSpotHeight() {
		return _spots[0].length;
	}

	// Lookup method for Spot at position (x,y)
	
	
	@Override
	public void updateSpots() {
		
		ArrayList<Spot> spotsToUpdate = new ArrayList<Spot>();
		for(int x = 0; x < getSpotWidth(); x++) {
     	   for(int y = 0; y < getSpotHeight(); y++) {
     		  int numAroundLiving = 0;
     		  if (!torus) {
	     		  if(x-1 > 0) {
	     			  if(_spots[x-1][y].isLiving()) {
	     				  numAroundLiving++;
	     			  }
	     			 if(y+1 < getSpotHeight()) {
	       			  if(_spots[x-1][y+1].isLiving()) {
	       				  numAroundLiving++;
	       			  }
	     			 }
	     			 if(y-1 > 0) {
	        			  if(_spots[x-1][y-1].isLiving()) {
	        				  numAroundLiving++;
	        			  }
	        		  }
	     		  }
	     		  if(x+1 < getSpotWidth()) {
	    			  if(_spots[x+1][y].isLiving()) {
	    				  numAroundLiving++;
	    			  }
	    			  if(y+1 < getSpotHeight()) {
	           			  if(_spots[x+1][y+1].isLiving()) {
	           				  numAroundLiving++;
	           			  }
	         			 }
	         		  if(y-1 > 0) {
	            		  if(_spots[x+1][y-1].isLiving()) {
	            				  numAroundLiving++;
	            			  }
	            		  }
	    		  }
	     		  if(y-1 > 0) {
	     			  if(_spots[x][y-1].isLiving()) {
	     				  numAroundLiving++;
	     			  }
	     		  }
	     		  if(y+1 < getSpotHeight()) {
	    			  if(_spots[x][y+1].isLiving()) {
	    				  numAroundLiving++;
	    			  }
	    		  }
     		  }
     		  else {
     			 if(_spots[Math.floorMod((x+1),this.getSpotWidth())][Math.floorMod((y),this.getSpotHeight())].isLiving()) {
     				 numAroundLiving++;
     			 }
     			 if(_spots[Math.floorMod((x+1),this.getSpotWidth())][Math.floorMod((y+1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x),this.getSpotWidth())][Math.floorMod((y+1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x-1),this.getSpotWidth())][Math.floorMod((y+1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x-1),this.getSpotWidth())][Math.floorMod((y),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x-1),this.getSpotWidth())][Math.floorMod((y-1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x),this.getSpotWidth())][Math.floorMod((y-1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     			 if(_spots[Math.floorMod((x+1),this.getSpotWidth())][Math.floorMod((y-1),this.getSpotHeight())].isLiving()) {
    				 numAroundLiving++;
    			 }
     		  }
     		  if(!_spots[x][y].isLiving() && (numAroundLiving >= lowBirth && numAroundLiving <= highBirth)) {
     			  spotsToUpdate.add(_spots[x][y]);
     		  }
     		  if(_spots[x][y].isLiving() && (numAroundLiving < lowSurvive || numAroundLiving > highSurvive)) {
    			  spotsToUpdate.add(_spots[x][y]);
    		  }
     		  
     	   }
        }
		
		for(Spot s : spotsToUpdate) {
			s.toggleLiving();
		}
		
	}
	
	public void updateThresholds(int a, int b, int c, int d) {
		lowBirth = a;
		highBirth = b;
		lowSurvive = c;
		highSurvive = d;
	}
	
	@Override
	public Spot getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}
		
		return _spots[x][y];
	}
	
	// Convenience methods for (de)registering spot listeners.
	
	@Override
	public void addSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].addSpotListener(spot_listener);
			}
		}
	}
	
	@Override
	public void removeSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].removeSpotListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		return new SpotBoardIterator(this);
	}
	
	public void toggleTorus() {
		if (torus) {
			torus = false;
		}
		else {
			torus = true;
		}
	}
}
