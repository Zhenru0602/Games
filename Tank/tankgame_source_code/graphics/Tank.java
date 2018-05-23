package tankgame.graphics;

import java.awt.image.ImageObserver;
import java.io.IOException;
import java.awt.Rectangle;


public class Tank extends GameObject {

  Sprite tankSprite;
  private int angle = 0;
  private int bloodCount = 3;
  private int lives = 2;
  private int points = 0;
  private int player;
   
  public Tank ( String resourceLocation ) throws IOException {
    super( resourceLocation );
    tankSprite = new Sprite( resourceLocation, 64 );
  }

  public Tank ( String resourceLocation, ImageObserver observer )throws IOException {
    super( resourceLocation, observer );
    tankSprite = new Sprite( resourceLocation, 64 );
  } 
   
  public boolean collision( Wall wall, Tank tank, int newX, int newY ) {
    Rectangle tankRectanlge = new Rectangle( newX, newY, 54, 54 );
    Rectangle tank2Rectanlge = new Rectangle( tank.getX(), tank.getY(), 54, 54 );
    if( tank2Rectanlge.intersects( tankRectanlge ) ){
      return true;
    }
    for ( int row= 0; row < 39; row++ ) {
      for ( int col = 0; col < 40; col++ ) {
        if ( ( wall.getArray()[col][row].equals( "1" ) ) || 
                ( wall.getArray()[col][row].equals( "2" ) ) ){
          Rectangle wallRectangle = new Rectangle( row*32, col*32, 25, 25 );                                                 
          if ( wallRectangle.intersects( tankRectanlge ) ) {
            return true;
          }
        }
      }
    } 
    return false;
 }
  
  public void increaseAngle() {
    this.angle = this.angle + 6;
    if ( this.angle >= 360 ){
      this.angle = 6;
    }
  }
  
  public void decreaseAngle() {
    this.angle = this.angle - 6;
    if( this.angle <= 0 ){
      this.angle = 354;
    }
  }
  
  public void setImage(){
      this.image = tankSprite.getFrame( this.angle/6 );
  }
  
  public void tankMoveUp(){
    x = x + ( int )( Math.cos( Math.toRadians( this.angle ) ) * 10 );
    y = y - ( int )( Math.sin( Math.toRadians( this.angle ) ) * 10 );
  }
  
  public void tankMoveDown(){
    x = x - ( int )( Math.cos( Math.toRadians( this.angle ) ) * 10 );
    y = y + ( int )( Math.sin( Math.toRadians( this.angle ) ) * 10 );
  }
  
  public int nextX(){
    return x + ( int )( Math.cos( Math.toRadians( this.angle ) ) * 10 );
  }
  
  public int nextY(){
    return y - ( int )( Math.sin( Math.toRadians( this.angle ) ) * 10 );
}
  
  public int preX(){
    return x - ( int )( Math.cos( Math.toRadians( this.angle ) ) * 10 );
  }
  
  public int preY(){
    return y + ( int )( Math.sin( Math.toRadians( this.angle ) ) * 10 );
}
  
  public int getFirePositionX(){
      
    int centerX = x + this.getWidth()/2;  
    int r = ( int )( this.getWidth()/2 * ( Math.cos( Math.toRadians( 45 ) ) ) ) * 3;
    
    return centerX + ( int )( r * ( Math.cos( Math.toRadians( this.angle ) ) ) );
  }
  
  public int getFirePositionY(){
      
    int centerY = y + this.getHeight()/2;
    int r = ( int )  ( this.getWidth()/2 * ( Math.cos( Math.toRadians( 45 ) ) ) ) * 3;
      
    return centerY - ( int )( r * ( Math.sin( Math.toRadians( this.angle ) ) ) );
  }
  
  public int getBloodCount(){
    return this.bloodCount;
  }
  
  public void decreaseBlood(){
    this.bloodCount --;
  }
  
  public void resetBlood(){
    this.bloodCount = 3;
  }
  
  public int getAngle(){
    return this.angle;
  }
  
  public int getLives(){
    return this.lives;
  }
  
  public void decreaseLives(){
    this.lives --;
  }
  
  public void resetLives(){
    this.lives = 2;
  }
  
  public int getPoints(){
    return this.points;
  }
  
  public void increasePoints(){
    this.points += 100;
  }
  
  public void setPlayer( int player ){
    this.player = player;
  }
  
  public int getPlayer(){
    return this.player;
  }
  
}  

