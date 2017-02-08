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

            String [] rebut = new String(mensaje).split(" ");

            switch (rebut[1].charAt(0)) {
                case '+':
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])+Double.valueOf(rebut[2]));
                    break;
                case '-':
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])-Double.valueOf(rebut[2]));
                    break;
                case '*':
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])*Double.valueOf(rebut[2]));
                    break;
                case '/':
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])/Double.valueOf(rebut[2]));
                    break;
                default: System.out.println("Operacion incorrecta tienes que utilizar exactamente este orden ej : 1 + 1 ");
            }
            System.out.println("Cerrando el socket");
            newSocket.close();
            System.out.println("Cerrando el socket servidor");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
