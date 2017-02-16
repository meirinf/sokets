import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;

/**
 * Created by 53638138e on 08/02/17.
 */
public class HiloPeticion  extends Thread{

    private static FileWriter archivo;//nuestro archivo log
    private Socket newSocket = new Socket();
    private String mensajeenviar = "";
    double resultado ;
    private static String IPS;
    private static String reci;


    public HiloPeticion( Socket n) {
        newSocket = n;
    }

    public void run(){

        try {
            InputStream io = newSocket.getInputStream();


            byte [] mensaje= new byte[50];
            io.read(mensaje);//Es para leer
            reci = String.valueOf("Mensaje recibido : "+new String(mensaje));
            System.out.println("Mensaje recibido : "+new String(mensaje));


            OutputStream os = newSocket.getOutputStream();//Es para enviar

            //Aqui recibo el mensaje del servidor lo separo por el espacio y meto cada elemento en la array
            String [] rebut = new String(mensaje).split(" ");
            //miro la posicion uno del array y saco el simbolo de la operación
            switch (rebut[1].charAt(0)) {
                //Si es + suma si es - resta etc.
                case '+':
                    //Imprimo
                    System.out.println("Hola soy el servidor. El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])+Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])+Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    //Almaceno la IP de envio
                    IPS = newSocket.getInetAddress().getHostAddress();
                    System.out.println(newSocket.getInetAddress().getHostAddress()+" se le envia -> "+mensajeenviar);

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
                    System.out.println(newSocket.getInetAddress().getHostAddress()+" se le envia -> "+mensajeenviar);
                    //Almaceno la IP de envio
                    IPS = newSocket.getInetAddress().getHostAddress();
                    break;
                case '*':
                    //Imprimo
                    System.out.println("Hola soy el servidor. El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])*Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])*Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    System.out.println(newSocket.getInetAddress().getHostAddress()+" se le envia -> "+mensajeenviar);
                    //Almaceno la IP de envio
                    IPS = newSocket.getInetAddress().getHostAddress();
                    CrearLog(mensajeenviar);
                    break;
                case '/':
                    //Imprimo
                    System.out.println("Hola soy el servidor. El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])/Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])/Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    //Almaceno la IP de envio
                    IPS = newSocket.getInetAddress().getHostAddress();
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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    private static void CrearLog(String mensajeenviar) throws IOException, ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        Document document = implementation.createDocument(null, "documento", null);
        document.setXmlVersion("1.0");

        Element raiz = document.getDocumentElement();

        Element nodoNombreCampo = document.createElement("numeroIP"); //creamos un nuevo elemento
        Text nodoValorCampo = document.createTextNode(IPS); //Ingresamos la info
        nodoNombreCampo.appendChild(nodoValorCampo);
        raiz.appendChild(nodoNombreCampo); //pegamos el elemento a la raiz "Documento"

        Element nodoNom = document.createElement("Operacion"); //creamos un nuevo elemento
        Text nodoVal = document.createTextNode(reci); //Ingresamos la info
        nodoNom.appendChild(nodoVal);
        raiz.appendChild(nodoNom); //pegamos el elemento a la raiz "Documento"


        Element nodoNombre = document.createElement("Resultado"); //creamos un nuevo elemento
        Text nodoValor = document.createTextNode(mensajeenviar); //Ingresamos la info
        nodoNombre.appendChild(nodoValor);
        raiz.appendChild(nodoNombre); //pegamos el elemento a la raiz "Documento"



        Source source = new DOMSource(document);
        Result result = new StreamResult(new java.io.File("resultado.xml")); //nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }
}
