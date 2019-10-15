import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public int port = 19000;
    public String host = "localhost";
    private Socket socket;
    private InputStream input;
    public OutputStream output;

    Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void TransferData(Object data) throws IOException {
        String msg = (String)data;
        output.write(msg.getBytes());
        output.flush();
    }

    public void Connect(){
        try {
            socket = new Socket(host, port);
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (ConnectException c){
            System.out.println("Host port is unavailable.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
