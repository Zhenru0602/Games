package tankgame.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Wall extends GameObject{
    private static BufferedReader source;
    private static String line;
    private BufferedImage unbreakableWall;
    
    private int proTank_x;
    private int proTank_y;
    private int proTank2_x;
    private int proTank2_y;
    
    private static int row = 0;
    private static String[][] wallArray = new String[40][39];

    public Wall( String resourceLocation ) throws IOException{
      super( resourceLocation );
      source = new BufferedReader( new FileReader( "resources/level.txt" ) );
      unbreakableWall = ImageIO.read( new File( "resources/Blue_wall1.png" ) );
    }
    
    public void setArray() throws IOException{
      while ( ( line = source.readLine() ) != null ) {
        for ( int col = 0; col < wallArray[row].length; col++ ) {
          wallArray[row][col] = String.valueOf( line.charAt(col) );
        }
        row++;
      }
    }
    
    public String[][] getArray(){
      return wallArray;
    }

  @Override
  public void repaint( Graphics graphics ) {
    for ( x = 0; x < 39; x++ ) {
      for ( y = 0; y < 40; y++ ) {
        if ( wallArray[y][x].equals( "1" ) ) {
          graphics.drawImage( unbreakableWall, x*unbreakableWall.getWidth(), y*unbreakableWall.getHeight(), observer );
        }
        else if ( wallArray[y][x].equals( "2" ) ) {
          graphics.drawImage( image, x*image.getWidth(), y*image.getHeight(), observer );
        }
      }
    }
  }

  public void resetArray(){
    for ( x = 0; x < 39; x++ ) {
      for ( y = 0; y < 40; y++ ) {
        if ( wallArray[y][x].equals( "0" ) ) {
          wallArray[y][x] = String.valueOf( 2 );
        }
      }
    }
  } 
  
  public void setTankPosition(){
    for ( x = 0; x < 39; x++ ) {
      for ( y = 0; y < 40; y++ ) {
        if ( wallArray[y][x].equals( "3" ) ) {
          proTank2_x = x * image.getWidth();
          proTank2_y = y * image.getHeight();
        }
        else if ( wallArray[y][x].equals( "4" ) ) {
          proTank_x = x * image.getWidth();
          proTank_y = y * image.getHeight();
        }
      }
    }  
  }
  
  public int getproTank_x(){
    return this.proTank_x;
  }
  
  public int getproTank_y(){
    return this.proTank_y;
  }
  
  public int getproTank2_x(){
    return this.proTank2_x;
  }
  
  public int getproTank2_y(){
    return this.proTank2_y;
  }
  
}

