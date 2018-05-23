package galacticmail.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class Rocket extends GameObject {

  Sprite flyingRocketSprite;
  Sprite landedRocketSprite;
  private int angle = 0;
  private int speed = 10;
  private int score = 0;
  private int level = 1;
  
  
  private boolean landing = false;
  private boolean isAlive = true;

  
  public Rocket( String resourceLocation, ImageObserver observer, String resourceLocation2 ) throws IOException {  
    super( resourceLocation, observer );
    landedRocketSprite = new Sprite( resourceLocation, 48 );       
    flyingRocketSprite = new Sprite( resourceLocation2, 48 );
  }
  
  public void increaseAngle() {
    this.angle = this.angle + 5;
    if ( this.angle >= 360 ){
      this.angle = 5;
    }
  }
  
  public void decreaseAngle() {
    this.angle = this.angle - 5;
    if( this.angle <= 0 ){
      this.angle = 355;
    }
  }

  public int getAngle() {
    return this.angle;
  }  
  
  public void setImage() {
    if ( landing == false ){
      this.image = flyingRocketSprite.getFrame( this.angle / 5 );
    } 
    else {
      this.image = landedRocketSprite.getFrame( this.angle / 5 );    
    }
  }
  
  public void rocketMoveUp( ) {
    if(landing == false){  
      x = x + ( int )( Math.cos( Math.toRadians( this.angle ) ) * speed );
      y = y - ( int )( Math.sin( Math.toRadians( this.angle ) ) * speed );
    }  else{
         x = x + ( int )( Math.cos( Math.toRadians( this.angle ) ) * speed );
         y = y - ( int )( Math.sin( Math.toRadians( this.angle ) ) * speed );
       }
    
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
  
  public void flying() {
    landing = false;
  }
  
  public void landing() {
    landing = true;
  }
  
  public boolean instersectMoon( MoonAnimation moon ) {
    if( isAlive == true ) {  
      Rectangle rocketRectangle;
      rocketRectangle = new Rectangle( x, y, this.getWidth(), this.getHeight() );
      if ( rocketRectangle.intersects( moon.getRectangle() ) ) {
          return true;
      }
    }
    return false;
  }
  
  public boolean getLanding() {
      return landing;
  }
  
  public boolean intersectStone( StoneAnimation stone) {
    if( isAlive == true ) {
      Rectangle rocketRectangle = new Rectangle( x, y, this.getWidth(), this.getHeight() );
      if( rocketRectangle.intersects( stone.getRectangle() ) ){
        return true;
      }
    }  
    return false;      
  }
  
  public void kill() {
    isAlive = false;
  }
  
  public boolean getIsAlive() {
    return isAlive;
  }
  
  public int getScore() {
    return this.score;
  }
  
  public void increaseScore( MoonAnimation moon ) {
    if ( moon.getFrame() == 0 ){
      score += 100;      
    }
    if ( moon.getFrame() == 1 ){
      score += 150;      
    }
    if ( moon.getFrame() == 2 ){
      score += 200;      
    }
    if ( moon.getFrame() == 3 ){
      score += 300;      
    }
    if ( moon.getFrame() == 4 ){
      score += 350;      
    }
    if ( moon.getFrame() == 5 ){
      score += 400;      
    }
    if ( moon.getFrame() == 6 ){
      score += 450;      
    }    
    if ( moon.getFrame() == 7 ){
      score += 500;      
    }
    
  }
   
  public void decreaseScore() {    
    score -= 1;
  }
    
  public void increaseLevel() {
    level++;
  }
  
  public int getLevel() {
    return this.level;
  }
  
  public void repaintScore( Graphics graphics ) {
    graphics.setColor( Color.white );
    Font font = graphics.getFont().deriveFont( 25.0f );    
    graphics.setFont( font );    
    graphics.drawString( "Score: " + Integer.toString( score ) 
            + "   Level: " + Integer.toString( level ), 50, 50 );  
  }  
  
  public void restart() {
    angle = 0;
    speed = 10;
    score = 0;
    level = 1;
    landing = false;
    isAlive = true;
  }
  
  public void levelUp() {
    angle = 0;
    speed = 10;
    landing = false;
    isAlive = true;
  }
  
}
