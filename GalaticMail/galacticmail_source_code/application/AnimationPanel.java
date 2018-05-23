package galacticmail.application;

import galacticmail.graphics.Animation;
import galacticmail.graphics.GameObject;
import galacticmail.graphics.Rocket;
import galacticmail.graphics.Sprite;
import galacticmail.graphics.MoonAnimation;
import galacticmail.graphics.Planet;
import galacticmail.graphics.ScoreBoard;
import galacticmail.graphics.StoneAnimation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AnimationPanel extends JPanel implements Runnable {
  protected int WIDTH = 1280;
  protected int HEIGHT = 960;
  protected int STEP = 5;
  protected int addMoon = 5;
  protected int moonCount = 4;
  protected int minScore = 0;
  protected String BACKGROUND_IMAGE = "resources/Background.png";
  protected String FLYING_ROCKET = "resources/Flying_strip72.png";
  protected String LANDED_ROCKET = "resources/Landed_strip72.png";
  protected String MOON_IMAGE = "resources/Bases_strip8.png";
  protected String STONE_IMAGE = "resources/Asteroid_strip180.png";
  protected String EXPLOSION_IMAGE = "resources/Explosion_strip9.png";
  protected String WELCOME_IMAGE = "resources/Title.png";
  protected String PLANET_IMAGE = "resources/Planets_strip8.png";
  protected String SCORE_IMAGE = "resources/scoreboard.jpg";
  protected String gameSituation = "welcome";
  protected Dimension dimension;
  protected Rocket rocket;
  protected Planet planet;
  public Sprite moonSprite, stoneSprite, explosionSprite;
  protected GameObject background;
  protected GameObject welcome;
  protected GameObject temp;
  protected ArrayList< MoonAnimation > moonAnimations;
  protected ArrayList< StoneAnimation > stoneAnimations;
  protected Animation explosion;
  protected AudioPlayer bonusSound = new AudioPlayer( "resources/Bonus.wav" );
  protected AudioPlayer explosionSound = new AudioPlayer( "resources/Explosion.wav" );
  protected AudioPlayer launchSound = new AudioPlayer( "resources/Launch.wav" );
  protected ScoreBoard scoreboard;
  protected String userName = "unknown";
  
  public AnimationPanel() {
    try {       
      background = new GameObject( BACKGROUND_IMAGE );
      welcome = new GameObject( WELCOME_IMAGE );
      rocket = new Rocket( LANDED_ROCKET, this, FLYING_ROCKET );
      planet = new Planet( PLANET_IMAGE, this);
      scoreboard = new ScoreBoard( SCORE_IMAGE, this );
      rocket.setX( 300 );
      rocket.setY( 300 );
      this.moonSprite = new Sprite( MOON_IMAGE, 64);
      this.stoneSprite = new Sprite( STONE_IMAGE, 50);
      this.explosionSprite = new Sprite( EXPLOSION_IMAGE, 64 );
    } catch( IOException exception ) {
      System.err.println( "Failed to load sprite." );
      exception.printStackTrace();
    }
    this.moonAnimations = new ArrayList<>();
    this.stoneAnimations = new ArrayList<>();
    this.dimension = new Dimension( 1260, 690 );
  }

  @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }

  @Override
  public void paintComponent( Graphics graphics ) {
    super.paintComponent( graphics );
    
    if( gameSituation != "welcome" ){
      temp = background;
    }  else if( gameSituation == "welcome" ) {
      temp = welcome;
    }
    
    for( int x = 0; x < WIDTH; x += temp.getWidth() ) {
      for( int y = 0; y < HEIGHT; y += temp.getHeight() ) {
        temp.setX( x );
        temp.setY( y );
        temp.repaint( graphics );
      }
    }
    
    if ( gameSituation == "welcome" ) {
      graphics.setColor( Color.red );
      Font font = graphics.getFont().deriveFont( 25.0f );    
      graphics.setFont( font );    
      graphics.drawString( "Press Enter to Start" , 550, 300 );  
    }
    
    if( gameSituation != "welcome" ) {
      planet.setImage( rocket.getLevel() );
      planet.repaint( graphics );
    }

    // to determine is the rocket landed on any moons
    MoonAnimation moonAnimation;
    for( int counter = 0; counter < moonAnimations.size(); counter++ ) {
      moonAnimation = moonAnimations.get( counter );
      if ( rocket.instersectMoon( moonAnimation ) && rocket.getLanding() == false ) {
        rocket.landing();
        moonAnimation.setIsLanded();
        if( gameSituation == "gaming" ) {
          rocket.increaseScore( moonAnimation );            
          bonusSound.play();
        }
      }
      
      if(rocket.getLanding() == true && moonAnimation.getIsLanded() == true){
        rocket.setX( moonAnimation.getX() );
        rocket.setY( moonAnimation.getY() );
      }
      
      if( moonAnimation.isStopped() ) {
        rocket.flying();
        moonAnimations.remove( counter );
        moonCount--;
      } else {
          moonAnimation.repaint( graphics );
        }
    }
     
    // to determine does the rocket hit on any stones
    StoneAnimation stoneAnimation;
    for( int counter = 0; counter < stoneAnimations.size(); counter++ ) {
      stoneAnimation = stoneAnimations.get( counter );
      if( rocket.intersectStone( stoneAnimation ) && rocket.getLanding() == false ) {
          rocket.kill();
          explosionSound.play();
          userName = JOptionPane.showInputDialog( "Enter your name:");
          gameSituation = "recording";
          this.explosion = new Animation( explosionSprite, rocket.getX(), rocket.getY(), 30, false, "explosion" );
          explosion.repaint( graphics );
          explosionSound.play();
      }
      
      if( stoneAnimation.isStopped() ) {
        stoneAnimations.remove( counter );
      } else {
        stoneAnimation.repaint( graphics );
        }
    }
    
    if( rocket.getIsAlive() == true) {
      rocket.setImage();
      rocket.repaint( graphics );
      if( rocket.getLanding() == false ) {
        rocket.rocketMoveUp();
      }
    }
    
    if( gameSituation != "welcome" ) {
      rocket.repaintScore( graphics );
    }
    
    if( moonCount == 0 ){
      levelUp();
    }
    
    // draw innitail mon and moons with signing random position
    if( moonAnimations.size() == 0) {
        addMoonAnimation( new MoonAnimation( this.moonSprite, rocket.getX(), rocket.getY(), rocket.getAngle(), 0, 3, false, "moon" ) );
        addMoon--;
    }  else if( addMoon > 0){
        int x, y;  
        int randomNumber = 0 + (int)( Math.random() * 1 ); 
        if ( randomNumber == 0 ){
          x = 0 + (int)( Math.random() * 1260 ); 
          int randomNumber2 = 0 + (int)( Math.random() * 1 );
          if ( randomNumber2 == 0 ){
            y = 0;
          }  else {
               y = 690;
             }
        }  else {
             y = 0 + (int)( Math.random() * 690 );
             int randomNumber2 = 0 + (int)( Math.random() * 1 ); 
             if ( randomNumber2 == 0 ){
             x = 0;
             }  else {
                  x = 1280;
                }
           }
        
        int angle =  0 + (int)( Math.random() * 360 );
        int frame = 0 + (int)( Math.random() * 7 );
  
        addMoonAnimation( new MoonAnimation( this.moonSprite, x, y, angle, frame, 3, false, "moon" ) );
    }    
    
    // draw random stones
    if( stoneAnimations.size() < rocket.getLevel() + 4 ) {
      int x, y;  
      int randomNumber = 0 + ( int )( Math.random() * 1 ); 
      if ( randomNumber == 0 ) {
        x = 0 + ( int )( Math.random() * 1260 ); 
        int randomNumber2 = 0 + ( int )( Math.random() * 1 );
        if ( randomNumber2 == 0 ) {
          y = 0;
        }  else {
              y = 690;
           }
      }  else {
           y = 0 + ( int )( Math.random() * 690 );
           int randomNumber2 = 0 + ( int )( Math.random() * 1 ); 
           if ( randomNumber2 == 0 ) {
             x = 0;
           }  else {
                x = 1280;
           }
         }
        
      int angle =  0 + (int)( Math.random() * 360 );
      addStoneAnimation( new StoneAnimation( this.stoneSprite, x, y, angle, 3, false, "moon" ) );
      addMoon--;
    }
      
    if( gameSituation == "gameOver" ) {
      scoreboard.repaint(graphics);
    }  
      
  }

  public void addMoonAnimation( MoonAnimation animation ) {
    moonAnimations.add( animation );
  } 
  
  public void addStoneAnimation (StoneAnimation animation) {
    stoneAnimations.add( animation );
  }
  
  public void restart() {
    rocket.setX( 300 );
    rocket.setY( 300 );
    this.moonAnimations = new ArrayList<>();
    this.stoneAnimations = new ArrayList<>();
    rocket.restart();
    moonCount = 4;
    addMoon = 5;
    userName = "unknown";
    minScore = 0;
  }

  public void levelUp() {
    rocket.setX( 300 );
    rocket.setY( 300 );
    this.moonAnimations = new ArrayList<>();
    this.stoneAnimations = new ArrayList<>();
    moonCount = 4;
    addMoon = 5;
    rocket.increaseLevel();
    rocket.levelUp();
    minScore = rocket.getScore();
  }
}
