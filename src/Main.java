import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {
    public static void main(String[] args) {

    try {

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("datos.xml"));

       System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());

        NodeList listaEmpleados = document.getElementsByTagName("empleado");
        for (int i = 0; i < listaEmpleados.getLength(); i++) {
            Node nodo = listaEmpleados.item(i);
            System.out.println("Elemento:" + nodo.getNodeName() + " " + (i + 1));

            //nos aseguramos que lo que leemos es un nodo y no un comentario por ejemplo
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;
                System.out.println("id: " + element.getAttribute("id"));
                System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.println("username: " + element.getElementsByTagName("username").item(0).getTextContent());
                System.out.println("password: " + element.getElementsByTagName("password").item(0).getTextContent());
            }
        }


    } catch (Exception e) {
        System.out.println("Error de lectura");
    }


    }
}