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

                System.out.println("Creando una conecxion");
            //172.31.73.45
                InetSocketAddress addr = new InetSocketAddress("localhost",5555);
                cliente.connect(addr);
                InputStream is = cliente.getInputStream();//es para leer
                OutputStream os = cliente.getOutputStream();// es para enviar
                System.out.println("Creando socket del cliente");

            //Enviamos la operación al servidor tiene que ser exactamente el formato introducido
                String mensaje =  "5 + 6";
                System.out.println("Enviando mensaje");
                os.write(mensaje.getBytes());
                System.out.println("Mensaje enviado");

            //Recivimos el resultado de la operación realizada en el servidor
                byte [] mensajeRecivido= new byte[50];
                is.read(mensajeRecivido);
                System.out.println("Mensaje recivido : "+new String(mensajeRecivido));

                //Cerramos cliente
                cliente.close();
                System.out.println("Terminado");

        }catch (IOException e){
         e.printStackTrace();
        }
    }
}
