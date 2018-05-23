package tankgame.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Score {
  
  Tank tank;
  private int scoreBar_x;
  private int scoreBar_y = 650;

  public Score( Tank tank ){     
    this.tank = tank;
  }
    
  public void repaint( Graphics graphics ) {    
    Graphics2D bloodBar = (Graphics2D) graphics;
    Graphics2D tankLives = (Graphics2D) graphics;
    Graphics2D scoreBar = (Graphics2D) graphics;
    
    if ( tank.getPlayer() == 1 ){
      scoreBar_x = 0;
    }
    if ( tank.getPlayer() == 2 ){
      scoreBar_x = 630;
    }
    
    switch ( tank.getBloodCount() ) {
      case 3:
        bloodBar.setColor( Color.green );
        bloodBar.fillRect( scoreBar_x, scoreBar_y, 150, 20 );
        break;
      case 2:
        bloodBar.setColor(Color.yellow);
        bloodBar.fillRect( scoreBar_x, scoreBar_y, 100, 20 );
        break;
      case 1:
        bloodBar.setColor( Color.red );              
        bloodBar.fillRect( scoreBar_x, scoreBar_y, 50, 20 );
        break;
      default:
        break;
    }
          
    switch ( tank.getLives() ){
      case 2:
        tankLives.setColor( Color.white );
        tankLives.fillOval( scoreBar_x + 175, scoreBar_y, 20 ,20 );
        tankLives.fillOval( scoreBar_x + 200, scoreBar_y, 20 ,20 );  
        break;
      case 1:
        tankLives.setColor( Color.white );
        tankLives.fillOval( scoreBar_x + 175, scoreBar_y, 20 ,20 );
        break;
      default:
        break;
    }
    
    scoreBar.setColor( Color.white );
    scoreBar.drawString( "Score: " + Integer.toString(tank.getPoints()),
            scoreBar_x, scoreBar_y - 5);       
  }  
  
}
