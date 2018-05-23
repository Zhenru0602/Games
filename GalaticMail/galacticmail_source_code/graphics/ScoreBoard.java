package galacticmail.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ScoreBoard extends GameObject {

  private static BufferedReader source;
  private String line;
  private ArrayList<String> names = new ArrayList<String>();
  private ArrayList<Integer> scores = new ArrayList<Integer>();
  private ArrayList<Integer> levels = new ArrayList<Integer>();
  
  public ScoreBoard(String resourceLocation, ImageObserver observer) throws IOException {  
    super(resourceLocation, observer);   
    source = new BufferedReader( new FileReader( "resources/scoreboard.txt" ) );
    x = 400;
    y = 50;
  }
  
  public void readFile() throws IOException{
    int i = 0;
    String nameString = ""; 
    String scoreString = "";
    String levelString = "";
    while ( ( line = source.readLine() ) != null ) {
      if( i % 3 == 0){
        for(int j = 0; j < line.length(); j++){
          nameString += String.valueOf( line.charAt(j) );
          }
          names.add( nameString );
          nameString = "";
        }  else if( i % 3 == 1) {
             for(int j = 0; j < line.length(); j++){
               scoreString += String.valueOf( line.charAt(j) );
             }            
             scores.add( Integer.parseInt( scoreString ) );
             scoreString = "";
        }  else {
             for(int j = 0; j < line.length(); j++) {
               levelString += String.valueOf( line.charAt(j) );
             }               
             levels.add( Integer.parseInt( levelString ) );
             levelString = "";
           }
      i++;
      }
      source = new BufferedReader( new FileReader( "resources/scoreboard.txt" ) );
    } 
  

  public void add(String name, int score, int level) {
    for ( int i = 0; i < scores.size(); i++ ) {
      if( score > scores.get(i) ) {
        scores.add( i, score );
        scores.remove( scores.size() - 1 ); 
        names.add( i, name );
        names.remove( names.size() - 1 ); 
        levels.add( i, level );
        levels.remove( levels.size() - 1); 
        return;
       }
    }
  }

  public void overwrite() throws IOException {
    PrintWriter writer = new PrintWriter( "resources/scoreboard.txt" );
    writer.print("");
    for( int i = 0; i < names.size(); i++ ){
      writer.println( names.get(i) );
      writer.println( scores.get(i) );
      writer.println( levels.get(i) );
    }    
    writer.close();
  }    

  public void resetList() {
    names = new ArrayList<String>();
    scores = new ArrayList<Integer>();
    levels = new ArrayList<Integer>();
}

  @Override
  public void repaint( Graphics graphics ) {
    int scoreY = 100;
    graphics.setColor( Color.white );
    Font font = graphics.getFont().deriveFont( 25.0f );    
    graphics.setFont( font );    
    graphics.drawString( "Name         " + "Score          " + "Level         " , 420, scoreY );  
    scoreY = scoreY + 50;
    font = graphics.getFont().deriveFont( 20.0f );    
    graphics.setFont( font );   
    for ( int i = 0; i < 5; i++){
        graphics.drawString( names.get(i), 420, scoreY ); 
        graphics.drawString( Integer.toString(scores.get(i)) ,560, scoreY ); 
        graphics.drawString( Integer.toString(levels.get(i)) ,700, scoreY ); 
        scoreY = scoreY + 80;
    }    
  }
}