package be.kuleuven.csa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;

public class ResourceHelper{

    public static void main(String[] args) {
        System.out.println(getPersistenceXmlSchemaGenerationDatabaseActionValue());
        setPersistenceXmlSchemaGenerationDatabaseAction("drop-and_create");
        System.out.println(getPersistenceXmlSchemaGenerationDatabaseActionValue());
    }

    public static String getString(String tagName){
        String result="EMPTY";
        try{
            File file = new File("./src/main/resources/values/strings.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("string");
            for (int i = 0; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                String attributeName = node.getAttributes().getNamedItem("name").toString();
                attributeName = attributeName.substring(6,attributeName.length()-1);
                //System.out.println("Node "+i+" attributename = "+attributeName+" & tagName  = "+tagName);
                if(attributeName.equals(tagName)){
                    result = node.getTextContent();
                };
            }

            if (result.equals("EMPTY")){
                throw new NullPointerException("String not found in strings.xml");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setPersistenceXmlSchemaGenerationDatabaseAction(String newValue) {
        String filePath = "./src/main/resources/META-INF/persistence.xml";
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
            Node rootNode = doc.getFirstChild();//for getting the root node
            String expression="persistence/persistence-unit/properties/property";//x-path expression
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile(expression);
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            Node updateNode=null;
            NodeList nodes = (NodeList) result;
            updateNode=nodes.item(4).getAttributes().getNamedItem("value");
            updateNode.setNodeValue(newValue);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult streamResult =  new StreamResult(new File(filePath));
            transformer.transform(source, streamResult);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getPersistenceXmlSchemaGenerationDatabaseActionValue(){
        String result="EMPTY";
        try{
            File file = new File("./src/main/resources/META-INF/persistence.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("property");
            result = nodeList.item(4).getAttributes().getNamedItem("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

