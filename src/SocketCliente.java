import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by 53638138e on 01/02/17.
 */
public class SocketCliente {
    public static void main(String[] args) {
        try {
            System.out.println("Creando un cliente");
            Socket cliente = new Socket();
            DatagramSocket udp = new DatagramSocket();

            //Esto es para ir canbiando de  puerto el que actualmete es 5555
            //for(int i = 1000 ; i > 5555; i++) {
                System.out.println("Creando una conecxion");
                InetSocketAddress addr = new InetSocketAddress("172.31.73.45",5555);
                cliente.connect(addr);
                InputStream is = cliente.getInputStream();
                OutputStream os = cliente.getOutputStream();

                String mensaje = "wowwwendrhdbndgdf";
                System.out.println("Enviando mensaje");
                os.write(mensaje.getBytes());
                System.out.println("Mensaje enviado");

                System.out.println("Creando socket del cliente");
                cliente.close();

                System.out.println("Terminado");
            //}
        }catch (IOException e){
         e.printStackTrace();
        }
    }
}
