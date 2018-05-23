package galacticmail.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

public class MoonAnimation extends Animation {
  
  protected boolean isLanded = false;
  protected int speed = 10;
  protected int x, y, angle, frame, centerX, centerY;
  
  public MoonAnimation( Sprite sprite, int x, int y, int angle, int frame, int frameDelay, boolean loop, String description ) {
    super( sprite, x, y, frameDelay, loop, description );
    this.x = x;
    this.y = y;
    this.angle = angle;
    this.frame = frame;
  }
  
  public boolean getIsLanded() {
    return isLanded;
  }  
  
  public Rectangle getRectangle() {
    Rectangle moonRectangle;
    return moonRectangle = new Rectangle( x, y, 60, 60 );
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
  
  public void setIsLanded() {
    isLanded = true;
  }

  @Override
  public void repaint( Graphics graphics ) {
      
    currentFrame = frame;
    graphics.drawImage( sprite.getFrame( currentFrame ), x, y, null );
    move();    
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
  
  public int getFrame() {
      return this.frame;
  }
 
}
