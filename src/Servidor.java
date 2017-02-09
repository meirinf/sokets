import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 53638138e on 01/02/17.
 */
public class Servidor {
    public static void main(String[] args) {
        boolean guay = true;

        try {
            System.out.println("generando el servidor");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el vinculo");
            //0.0.0.0 para recibir
            InetSocketAddress addr = new InetSocketAddress("localhost",5555);

            serverSocket.bind(addr);
            System.out.println("Aceptando conexiones");

            //Aqui llamo a la clase hiloPetición
            while (guay) {
                Socket newSocket = serverSocket.accept();
                HiloPeticion hp = new HiloPeticion(newSocket);
                hp.start();
                System.out.println("Conexión recivida");
            }
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
