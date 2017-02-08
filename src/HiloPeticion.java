import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by 53638138e on 08/02/17.
 */
public class HiloPeticion  extends Thread{

    private Socket newSocket = new Socket();


    public HiloPeticion( Socket n) {
        newSocket = n;
    }

    public void run(){

        try {
            InputStream io = newSocket.getInputStream();
            byte [] mensaje= new byte[50];
            io.read(mensaje);
            System.out.println("Mensaje recivido : "+new String(mensaje));
            System.out.println("Cerrando el socket");
            newSocket.close();
            System.out.println("Cerrando el socket servidor");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
