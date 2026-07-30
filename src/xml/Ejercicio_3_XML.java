package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Ejercicio_3_XML {

    public static void main(String[] args) {

        try {
            // 1. Cargar y parsear el documento XML
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File("ejercicio_3.xml"));

            // 2. Imprimir la etiqueta raíz ("empresa")
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());
            System.out.println("-----------------------------");

            // 3. Obtener todos los elementos dentro de <empresa>
            NodeList hijos = doc.getDocumentElement().getChildNodes();

            // 4. Recorrer los subelementos
            for (int i = 0; i < hijos.getLength(); i++) {
                Node cursoNodo = hijos.item(i);

                if (cursoNodo.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(cursoNodo.getNodeName() + " ---");

                    // 4. Obtener las etiquetas dentro de este <curso> concreto
                    NodeList datosCurso = cursoNodo.getChildNodes();

                    for (int j = 0; j < datosCurso.getLength(); j++) {
                        Node dato = datosCurso.item(j);

                        // Filtramos para leer solo etiquetas reales
                        if (dato.getNodeType() == Node.ELEMENT_NODE) {
                            // .getNodeName() obtiene "nombre", "alumnos", etc.
                            String etiqueta = dato.getNodeName();
                            String valor = dato.getTextContent();

                            System.out.println(etiqueta + ": " + valor);
                        }
                    }
                    System.out.println(); // Línea en blanco entre cursos
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
