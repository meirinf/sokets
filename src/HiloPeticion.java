import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 53638138e on 08/02/17.
 */
public class HiloPeticion  extends Thread{

    private Socket newSocket = new Socket();
    String mensajeenviar = "";
    double resultado ;


    public HiloPeticion( Socket n) {
        newSocket = n;
    }

    public void run(){

        try {
            InputStream io = newSocket.getInputStream();

            byte [] mensaje= new byte[50];
            io.read(mensaje);//Es para leer
            System.out.println("Mensaje recivido : "+new String(mensaje));


            OutputStream os = newSocket.getOutputStream();//Es para enviar

            //Aqui recibo el mensaje del servidor lo separo por el espacio y meto cada elemento en la array
            String [] rebut = new String(mensaje).split(" ");
            //miro la posicion uno del array y saco el simbolo de la operación
            switch (rebut[1].charAt(0)) {
                //Si es + suma si es - resta etc.
                case '+':
                    //Imprimo
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])+Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])+Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    break;
                case '-':
                    //Imprimo
                    System.out.println("Hola soy el servidor. El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])-Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])-Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    break;
                case '*':
                    //Imprimo
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])*Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])*Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    break;
                case '/':
                    //Imprimo
                    System.out.println("El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])/Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])/Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    break;
                //Avisa si es introducida una operacion no valida o un formato no valido
                default: System.out.println("Operacion incorrecta ");
                    //Guardar
                    mensajeenviar = "Operacion incorrecta tienes que utilizar exactamente este orden ej : 1 + 1 ";
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
            }
            System.out.println("Cerrando el socket");
            newSocket.close();
            System.out.println("Cerrando el socket servidor");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
