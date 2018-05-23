package galacticmail.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

public class StoneAnimation extends Animation {
  
  protected boolean isLanded = false;
  protected int speed = 10;
  protected int x, y, angle, frame, centerX, centerY;
  
  public StoneAnimation(Sprite sprite, int x, int y, int angle, int frameDelay, boolean loop, String description) {
    super( sprite, x, y, frameDelay, loop, description );
    this.x = x;
    this.y = y;
    this.angle = angle;
    frame = 0;
  }
  
  public boolean getIsLanded() {
    return isLanded;
  }  
  
  public Rectangle getRectangle() {
    Rectangle stoneRectangle;
    return stoneRectangle = new Rectangle( x, y, 60, 60 );
  }
  
  public void move() {
    x = x + ( int )( Math.cos( Math.toRadians( this.angle ) ) * speed );
    y = y - ( int )( Math.sin( Math.toRadians( this.angle ) ) * speed );
    if ( x + 60 < 0 ) {
      x += 1260;
    }
    
    if ( x > 1260 ) {
      x -= 1260;
    }
    
    if ( y < 0 ) {
      y +=  690;
    }
    if ( y + 60 > 690 ) {
      y -= 690;
    }    
  }
  
  public int getSpeed() {
    return speed;
  }

  @Override
  public void repaint( Graphics graphics ) {  
    currentFrame = frame;

    if( !stop ) {
      duration++;
      frameCount++;
      if( frameCount > frameDelay ) {
        frameCount = 0;
        stop = ( duration > this.totalTime() );
      } 
      graphics.drawImage( sprite.getFrame( currentFrame ), x, y, null );
      move();    
    }
  }  
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setStop() {
    stop = true;
  }
  
}
