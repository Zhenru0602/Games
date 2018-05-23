package tankgame.application;

import javax.swing.*;


public class GameFrame extends JFrame {

  public GameFrame( KeyboardPanel panel ) {
    setTitle( "Tank Game" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setResizable( false );

    add( panel );
    pack();

    panel.start();
    
    setVisible( true );
  }
}
