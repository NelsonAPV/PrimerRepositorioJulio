import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Ejercicio_1_XML {

    //lectura de fichero XML un hijo

    public static void main(String[] args) {

                try {
                    // 1. Cargar y parsear el documento XML
                    Document doc = DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder()
                            .parse(new File("ejercicio_1.xml"));

                    // 2. Imprimir la etiqueta raíz ("empresa")
                    System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());
                    System.out.println("-----------------------------");

                    // 3. Obtener todos los elementos dentro de <empresa>
                    NodeList hijos = doc.getDocumentElement().getChildNodes();

                    // 4. Recorrer los subelementos
                    for (int i = 0; i < hijos.getLength(); i++) {
                        Node nodo = hijos.item(i);

                        // Comprobar que sea una etiqueta válida (y no un salto de línea)
                        if (nodo.getNodeType() == Node.ELEMENT_NODE) {

                            // .getNodeName() obtiene "nombre", "direccion", etc.
                            String nombreEtiqueta = nodo.getNodeName();

                            // .getTextContent() obtiene "Pepe.SA", "Avenida Ejercito", etc.
                            String contenido = nodo.getTextContent();

                            System.out.println(nombreEtiqueta + ": " + contenido);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


