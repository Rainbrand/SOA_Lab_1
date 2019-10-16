import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public int port;
    public String host;
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void TransferData(Object data) throws IOException {
        String msg = (String)data;
        output.write(msg.getBytes());
        output.flush();
    }

    public void GetMessage() throws IOException {
        byte[] buf = new byte[1024 * 32];
        var length = input.read(buf);   //Reads message from input into 'buf' and returns length
        if (length != -1){
            String s = new String(buf, 0, length);
            System.out.println(s);
        }
    }

    public void Connect(){
        try {
            socket = new Socket(host, port);
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
            System.out.println("Client connected.");
        } catch (ConnectException c){
            System.out.println("Host port is unavailable.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Host is: ");
        String host = scan.nextLine();
        System.out.print("Port is: ");
        int port = scan.nextInt();
        scan.nextLine();
        try{
            Client client = new Client(host, port);
            client.Connect();
            System.out.print("Enter text block: ");
            String textBlock = scan.nextLine();
            client.TransferData(textBlock);
            client.GetMessage();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
