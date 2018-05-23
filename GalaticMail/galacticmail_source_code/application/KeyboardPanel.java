package galacticmail.application;

import galacticmail.graphics.MoonAnimation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class KeyboardPanel extends AnimationPanel implements KeyListener {
  protected AudioPlayer launchSound = new AudioPlayer( "resources/Launch.wav" );
  protected AudioPlayer music = new AudioPlayer( "resources/Music.mid" );
  private Thread thread;
  private boolean keyLeft, keyRight;
  
  public KeyboardPanel() {
    super();

    this.addKeyListener( this );
    this.setFocusable( true );
  }

  @Override
  public void keyPressed( KeyEvent e ) {
    switch( e.getKeyCode() ) {
      case KeyEvent.VK_SPACE:
        if( gameSituation == "gaming" ) {
          MoonAnimation moonAnimation;
          for( int counter = 0; counter < moonAnimations.size(); counter++ ) {
            moonAnimation = moonAnimations.get( counter );
      
            if( moonAnimation.getIsLanded() == true ) {
              moonAnimation.setStop();
            } 
            launchSound.play();
            rocket.flying();
          }
        }
      break;  
      case KeyEvent.VK_LEFT:
        keyLeft = true;
        break;
      case KeyEvent.VK_RIGHT:
        keyRight = true;
        break;
      case KeyEvent.VK_ENTER:
        if( gameSituation == "welcome" ){
          scoreboard.resetList();
          gameSituation = "gaming";
        }  else if( gameSituation == "gameOver" ){
             gameSituation = "welcome";
             restart();
           }
        break;
    }
  }
  
  
  @Override
  public void keyReleased( KeyEvent e ) {
    switch( e.getKeyCode() ) {
      case KeyEvent.VK_LEFT:
        keyLeft = false;
        break;
      case KeyEvent.VK_RIGHT:
        keyRight = false;
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
      if( gameSituation == "gaming" ) {
        if( keyLeft == true ) {  
          rocket.increaseAngle();
        }
        if( keyRight == true ) {   
          rocket.decreaseAngle();
        }
        if( rocket.getLanding() == true && rocket.getScore() > minScore ){
          rocket.decreaseScore();
        }
      }
        
      this.repaint();
      music.loop();
        
      if( gameSituation == "recording" ) {
        try {
          scoreboard.readFile();
          scoreboard.add( userName, rocket.getScore(), rocket.getLevel() );
          scoreboard.overwrite();
        }  catch( IOException e) {
             System.out.println( "ERROR!" );
           }
        gameSituation = "gameOver";
      }
      try {
        Thread.sleep( 50 );
      } catch ( InterruptedException interrupted ) { }
    }
  }  
  
  public void start() {
    thread = new Thread( this );
    thread.start();
  }
  
}
