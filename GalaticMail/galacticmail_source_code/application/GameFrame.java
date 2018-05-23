package galacticmail.application;

import javax.swing.*;


public class GameFrame extends JFrame {

  public GameFrame( KeyboardPanel panel ) {
    setTitle( "Galactic Mail");
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setResizable( false );

    add( panel );
    pack();

    panel.start();
    
    setVisible( true );
  }
}
