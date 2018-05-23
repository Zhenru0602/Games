package tankgame.application;

import tankgame.graphics.Animation;
import tankgame.graphics.Bullet;
import tankgame.graphics.GameObject;
import tankgame.graphics.Score;
import tankgame.graphics.Sprite;
import tankgame.graphics.Tank;
import tankgame.graphics.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AnimationPanel extends JPanel implements Runnable {
  protected int WIDTH = 1250;
  protected int HEIGHT = 1280;
  protected int STEP = 5;
  
  protected String BACKGROUND_IMAGE = "resources/Background.png";
  protected String TANK_IMAGE = "resources/Tank_blue_light_strip60.png";
  protected String WALL_IMAGE = "resources/Blue_wall2.png";
  
  protected Dimension dimension;
 
  protected Tank proTank;
  protected Tank proTank2;
  protected Wall wall;
  protected Score score;
  protected Score score2;
  
  protected ArrayList <Bullet> bullets = new ArrayList<Bullet>();
  private Image leftSide, rightSide;
  private BufferedImage offScreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  protected GameObject background;
  public Sprite deadExplosion;
  protected ArrayList< Animation > animations;

  public AnimationPanel() {
     try {
       proTank = new Tank( TANK_IMAGE );
       proTank2 = new Tank( TANK_IMAGE );
       proTank.setPlayer( 1 );
       proTank.setPlayer( 2 );
       score = new Score( proTank );
       score2 = new Score ( proTank2 );
       wall = new Wall( WALL_IMAGE );
       wall.setArray();
       wall.setTankPosition();
       proTank.setX( wall.getproTank_x() );
       proTank.setY( wall.getproTank_y() );
       proTank2.setX( wall.getproTank2_x() );
       proTank2.setY( wall.getproTank2_y() );        
       background = new GameObject( BACKGROUND_IMAGE );
       this.deadExplosion = new Sprite( "resources/Explosion_small_strip6.png", 32 );
    } catch( IOException exception ) {
      System.err.println( "Failed to load sprite." );
      exception.printStackTrace();
    }
    this.animations = new ArrayList<>();
    this.dimension = new Dimension( 1260, 690 );
  }

  @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }

  @Override
  public void paintComponent( Graphics graphics ) {
    Graphics offScreenGraphics = offScreen.getGraphics();
    super.paintComponent( offScreenGraphics );
    for( int x = 0; x < WIDTH; x += background.getWidth() ) {
      for( int y = 0; y < HEIGHT; y += background.getHeight() ) {
        background.setX( x );
        background.setY( y );
        background.repaint( offScreenGraphics );
      }
    }

    Animation animation;

    for( int counter = 0; counter < animations.size(); counter++ ) {
      animation = animations.get( counter );

      if( animation.isStopped() ) {
        animations.remove( counter );
      } else {
        animation.repaint( offScreenGraphics );
        }
    }
      
    proTank.setImage();
    proTank2.setImage();
           
    wall.repaint( offScreenGraphics );
    
    if( proTank.getBloodCount()  <= 0 ) {
       addAnimation( new Animation( this.deadExplosion,
               proTank.getX (), proTank.getY (),
               30, false, "Dead Explosion") );
      proTank2.increasePoints();
      if( proTank.getLives() > 0 ) {
        proTank.decreaseLives();
        proTank.resetBlood();
      }
      else {
        proTank.resetLives();
        proTank.resetBlood();
        proTank2.resetLives();
        proTank2.resetBlood();
        proTank2.increasePoints();
        proTank2.setX( wall.getproTank2_x() );
        proTank2.setY( wall.getproTank2_y() );
        wall.resetArray();
      }        
      proTank.setX( wall.getproTank_x() );
      proTank.setY( wall.getproTank_y() ); 
      proTank.repaint ( offScreenGraphics );  
    }
    else {
      proTank.repaint ( offScreenGraphics );  
      proTank2.repaint ( offScreenGraphics );       
    }
    
    if( proTank2.getBloodCount()  <= 0 ) {
      addAnimation( new Animation( this.deadExplosion,
              proTank2.getX (), proTank2.getY (),
              30, false, "Dead Explosion") );
      proTank.increasePoints();
      if( proTank2.getLives() > 0 ){
        proTank2.decreaseLives();
        proTank2.resetBlood();
       }
      else {
        proTank2.resetLives();
        proTank2.resetBlood();
        proTank.resetLives();
        proTank.resetBlood();
        proTank.increasePoints();
        proTank.setX(wall.getproTank_x());
        proTank.setY(wall.getproTank_y());
        wall.resetArray();
      }
        proTank2.setX(wall.getproTank2_x());
        proTank2.setY(wall.getproTank2_y());
        proTank2.repaint ( offScreenGraphics ); 
    }
    else {
      proTank2.repaint ( offScreenGraphics );
      proTank.repaint( offScreenGraphics );
    }
    offScreenGraphics = graphics;
    int xLeft, yLeft, xRight, yRight, width, height;
    xLeft = proTank2.getX() - 120;
    yLeft = proTank2.getY() - 300;
    xRight = proTank.getX() - 120;
    yRight = proTank.getY() - 300;
    width = 650;
    height = 705;
    
    if( xLeft < 0 ) {
      xLeft = 0;
    }
    if( xRight < 0 ) {
      xRight = 0;
    }
    if( yLeft < 0 ) {
      yLeft = 0;
    }
    if( yRight < 0 ) {
      yRight = 0;
    }
    if( xLeft + width > 1250 ) {
      xLeft = 600;
    }    
    if( xRight + width > 1250 ) {
      xRight = 600;
    }
    if( yLeft + height > 1280 ) {
      yLeft = 575;
    }
    if( yRight + height > 1280 ) {
      yRight = 575;
    }

    leftSide = offScreen.getSubimage( xLeft, yLeft, width, height );
    rightSide = offScreen.getSubimage( xRight, yRight, width, height );
    offScreenGraphics.drawImage( leftSide, 0, 0, this );
    offScreenGraphics.drawImage( rightSide, 625, 0, this );
    offScreenGraphics.drawImage( offScreen, 550, 450, 180, 180, this );
    score.repaint( offScreenGraphics );
    score2.repaint( offScreenGraphics );      

  }

  public void addAnimation( Animation animation ) {
    animations.add( animation );
  }
}
