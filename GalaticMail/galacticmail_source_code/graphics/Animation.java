package galacticmail.graphics;

import java.awt.*;

public class Animation {
  Sprite sprite;

  protected int frameCount;
  protected int frameDelay;
  protected int currentFrame;
  protected int duration;

  protected int x;
  protected int y;

  protected boolean loop;
  protected boolean stop;
  protected String description;

  public Animation( Sprite sprite, int x, int y, int frameDelay, boolean loop, String description ) {
    this.sprite = sprite;
    this.x = x;
    this.y = y;
    this.frameCount = 0;
    this.frameDelay = frameDelay;
    this.currentFrame = 0;
    this.loop = loop;
    this.stop = false;
    this.description = description;
    this.duration = 0;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean isStopped() {
    return this.stop;
  }

  public int totalTime() {
    return this.frameDelay * sprite.frameCount();
  }

  public void repaint( Graphics graphics ) {
    if( ! stop ) {
      duration++;
      frameCount++;
      if ( frameCount > frameDelay ) {
        frameCount = 0;
        stop = ( duration > this.totalTime() ) && ! loop;
        currentFrame = ( currentFrame + 1 ) % sprite.frameCount();
      }
      graphics.drawImage( sprite.getFrame( currentFrame ), x, y, null );
    }
  }
}
