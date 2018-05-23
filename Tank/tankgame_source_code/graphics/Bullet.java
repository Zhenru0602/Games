package tankgame.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Animation {
  private int angle;
  Sprite bulletSprite;
  Tank tank;
  Tank tank2;
  Wall wall;
  
  public Bullet ( Sprite sprite, int x, int y, int frameDelay, boolean loop, String description, Tank tank, Tank tank2, Wall wall ) {
    super(sprite, x, y, frameDelay, loop, description );
    this.tank = tank;
    this.tank2 = tank2;
    this.wall = wall;
    this.angle = tank.getAngle();  
  }
  
  public void bulletMove() {
    x = x + (int)( Math.cos( Math.toRadians( this.angle ) ) * 10 );
    y = y - (int)( Math.sin( Math.toRadians( this.angle ) ) * 10 );
  }
  
  public boolean isHitTank() {
    Rectangle tankRectanlge = new Rectangle( tank.getX(), tank.getY(), 54, 54 );
    Rectangle bulletRectanlge = new Rectangle( this.x, this.y, 10, 10);
    Rectangle tankRectanlge2 = new Rectangle( tank2.getX(), tank2.getY(), 54, 54 );
    
    if ( bulletRectanlge.intersects( tankRectanlge ) ) {
      tank.decreaseBlood();
      return true;
      } else if ( bulletRectanlge.intersects( tankRectanlge2 ) ) {
          tank2.decreaseBlood();
          return true;
    } return false;
  }
  
  public boolean isHitWall () {
    Rectangle bulletRectanlge = new Rectangle( this.x, this.y, 10, 10 );
    for ( int row= 0; row < 39; row++ ) {
      for ( int col = 0; col < 40; col++ ) {
        if ( ( wall.getArray()[col][row].equals( "1" ) ) ) {
          Rectangle wallRectangle = new Rectangle( row*32, col*32, 32, 32 );                                       
            if ( wallRectangle.intersects( bulletRectanlge ) ) {
              return true;
            }
        }  
        else if ( ( wall.getArray()[col][row].equals( "2" ) ) ) {
          Rectangle wallRectangle = new Rectangle( row*32, col*32, 32, 32 ); 
          if ( wallRectangle.intersects( bulletRectanlge ) ){
            wall.getArray()[col][row] = String.valueOf( 0 );
            return true;
          }
        }  
      }
    }
    return false;
}

   @Override
   public void repaint( Graphics graphics ) {
     currentFrame = angle / 6;
     if ( ! stop ) {
       duration++;
       frameCount++;
       if ( frameCount > frameDelay ) {
         frameCount = 0;
         stop = ( duration > this.totalTime() ) && ! loop;
         //currentFrame = angle / 6;
       }
       
       graphics.drawImage( sprite.getFrame( currentFrame ), x, y, null );
       if( isHitWall() == true || isHitTank() == true ){
         stop = true;
       }
       bulletMove();
     }
   }
  
}   
