package galacticmail.graphics;

import java.awt.image.ImageObserver;
import java.io.IOException;

public class Planet extends GameObject {

  Sprite planetSprite;

  
  public Planet( String resourceLocation, ImageObserver observer ) throws IOException {  
    super( resourceLocation, observer );
    planetSprite = new Sprite( resourceLocation, 96 );   
    x = 25;
    y = 25;    
  }
  
  public void setImage( int level ) {
      this.image = planetSprite.getFrame( (level - 1) % 7 );
  }
}