package tankgame.application;

import tankgame.graphics.Animation;
import tankgame.graphics.Bullet;
import tankgame.graphics.Sprite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class KeyboardPanel extends AnimationPanel implements KeyListener {
  public Sprite explosion;
  public Sprite bullet;
  protected AudioPlayer turret = new AudioPlayer("resources/turret.wav");
  protected AudioPlayer movingSound = new AudioPlayer("resources/movingSound.wav");
  protected AudioPlayer turningSound = new AudioPlayer("resources/turningSound.wav");
  private Thread thread;
  private boolean keyA, keyS, keyW,keyD, keyUp, keyDown, keyLeft, keyRight, keySpace, keyEnter;
  public KeyboardPanel() {
    super();

    this.addKeyListener( this );
    this.setFocusable( true );

    try {
      this.explosion = new Sprite( "resources/Explosion_small_strip6.png", 32 );
      this.bullet = new Sprite( "resources/Shell_basic_strip60.png", 24);
    } catch( IOException exception ) {
      exception.printStackTrace();
    }
  }

  @Override
  public void keyPressed( KeyEvent e ) {
    switch( e.getKeyCode() ) {
      case KeyEvent.VK_A:
        keyA = true;
        break;
      case KeyEvent.VK_D:
       keyD = true;
        break;
      case KeyEvent.VK_W:
          keyW = true;
          break;
      case KeyEvent.VK_S:
        keyS = true;
        break;
      case KeyEvent.VK_SPACE:
        keySpace = true;
        break;  
      case KeyEvent.VK_LEFT:
         keyLeft = true;
        break;
      case KeyEvent.VK_RIGHT:
          keyRight = true;
        break;
      case KeyEvent.VK_UP:
        keyUp = true;
        break;
      case KeyEvent.VK_DOWN:
        keyDown = true;
        break;
      case KeyEvent.VK_ENTER:
        keyEnter = true;
        break;
    }
    this.repaint();
  }
  
  
  @Override
  public void keyReleased( KeyEvent e ) {
       switch( e.getKeyCode() ) {
      case KeyEvent.VK_A:
        keyA = false;
        break;
      case KeyEvent.VK_D:
        keyD = false;
        break;
      case KeyEvent.VK_W:
          keyW = false;
          break;
      case KeyEvent.VK_S:
        keyS = false;
        break;
      case KeyEvent.VK_SPACE:
        keySpace = false;
        break;
      case KeyEvent.VK_LEFT:
         keyLeft = false;
        break;
      case KeyEvent.VK_RIGHT:
          keyRight = false;
        break;
      case KeyEvent.VK_UP:
        keyUp = false;
        break;
      case KeyEvent.VK_DOWN:
        keyDown = false;
        break;
      case KeyEvent.VK_ENTER:
        keyEnter = false;
        break;
       }
  }
  
  @Override
  public void keyTyped( KeyEvent e ) {
  }
  

  @Override
  public void run() {
      Thread me = Thread.currentThread();
      while( thread == me ) {
          if( keyA == true ) {
               turningSound.play();   
               proTank2.increaseAngle();
          }  if( keyD == true ) {
                turningSound.play();   
                proTank2.decreaseAngle();
          }  if( keyW == true ) {
                movingSound.play();
                if (proTank2.collision(wall, proTank, proTank2.nextX(), proTank2.nextY()) == false){
                    proTank2.tankMoveUp();
                }
          }  if( keyS == true ) {
                movingSound.play();          
                if (proTank2.collision(wall, proTank, proTank2.preX(), proTank2.preY()) == false){
                proTank2.tankMoveDown();
                }
          }  if( keySpace == true ) {
                turret.play();
                addAnimation( new Animation( this.explosion,
                        proTank2.getFirePositionX (), proTank2.getFirePositionY (),
                        3, false, "Explosion") );
                addAnimation( new Bullet( this.bullet,
                        proTank2.getFirePositionX (),
                        proTank2.getFirePositionY (),
                        3, false, "Bullet", proTank2, proTank, wall) ); 
             }
             if( keyLeft == true ) {
               turningSound.play();   
               proTank.increaseAngle();
          }  if( keyRight == true ) {
                turningSound.play();   
                proTank.decreaseAngle();
          }  if( keyUp == true ) {
                movingSound.play();
                if (proTank.collision(wall, proTank2, proTank.nextX(), proTank.nextY()) == false){
                    proTank.tankMoveUp();
                }
          }  if( keyDown == true ) {
                movingSound.play();          
                if (proTank.collision(wall, proTank2, proTank.preX(), proTank.preY()) == false){
                proTank.tankMoveDown();
                }
          }  if( keyEnter == true ) {
                turret.play();
                addAnimation( new Animation( this.explosion,
                        proTank.getFirePositionX (), proTank.getFirePositionY (),
                        3, false, "Explosion") );
                addAnimation( new Bullet( this.bullet,
                        proTank.getFirePositionX (),
                        proTank.getFirePositionY (),
                        3, false, "Bullet", proTank, proTank2, wall) ); 
             }
        
      this.repaint();
      try {
        Thread.sleep( 50 );
      } catch ( InterruptedException interrupted ) {

      }
    }
  }  
  
  public void start() {
       thread = new Thread( this );
       thread.start();
  }
  
}
