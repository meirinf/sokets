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
                    System.out.println("Hola soy el servidor. El resultado es : ");
                    System.out.println(Double.valueOf(rebut[0])+Double.valueOf(rebut[2]));
                    //Guardo el resultado
                    resultado = Double.valueOf(rebut[0])+Double.valueOf(rebut[2]);
                    mensajeenviar = String.valueOf("El resultado es : "+ resultado);
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    //Introducir en el log
                    CrearLog(mensajeenviar);
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
                    //Introducir en el log
                    CrearLog(mensajeenviar);
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
                    //Introducir en el log
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
                    //Introducir en el log
                    CrearLog(mensajeenviar);
                    break;
                //Avisa si es introducida una operacion no valida o un formato no valido
                default: System.out.println("Operacion incorrecta ");
                    //Guardar
                    mensajeenviar = "Operacion incorrecta tienes que utilizar exactamente este orden ej : 1 + 1 ";
                    //Lo envio
                    System.out.println("Enviando mensaje");
                    os.write(mensajeenviar.getBytes());
                    System.out.println("Mensaje enviado");
                    //Introducir en el log

                    //CrearLog(newSocket.getInetAddress().getHostAddress()+" se le envia -> "+mensajeenviar);
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

        Element nodoNombreCampo = document.createElement("ElementoHijoDeLaRaíz"); //creamos un nuevo elemento
        Text nodoValorCampo = document.createTextNode("contenido del elemento hijo"); //Ingresamos la info
        nodoNombreCampo.appendChild(nodoValorCampo);
        raiz.appendChild(nodoNombreCampo); //pegamos el elemento a la raiz "Documento"

        /*//Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
        if (new File("log.txt").exists()==false){archivo=new FileWriter(new File("log.txt"),false);}
        archivo = new FileWriter(new File("log.txt"), true);
        //Empieza a escribir en el archivo
        archivo.write(mensajeenviar);
        archivo.close(); //Se cierra el archivo*/

        Source source = new DOMSource(document);
        Result result = new StreamResult(new java.io.File("resultado.xml")); //nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }
}
