package tankgame.examples.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class KeyBroadcaster implements Observer {

  private BufferedWriter writer;

  public KeyBroadcaster( int port ) throws IOException {
    Socket socket = new Socket( "localhost", port );

    this.writer = new BufferedWriter(
        new OutputStreamWriter( socket.getOutputStream() )
    );
  }

  @Override
  public void update( Observable o, Object arg ) {
    ObservableKeyEntry observable = (ObservableKeyEntry) o;

    try {
      this.writer.write( observable.getLastKey() );
      this.writer.flush();
    } catch( IOException exception ) {
      System.err.println( "Failed to send key." );
    }
  }
}
