package com.nixsolutions.bondarenko.study.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class EvenRemoveDomParser {
    private Document doc;

    public EvenRemoveDomParser(String inFileName, String outFileName) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "test.xml";
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        doc = dBuilder.parse(resourceAsStream);

        doc.getDocumentElement().normalize();

    }

    void removeEven() throws TransformerException {
        if (doc.hasChildNodes()) {
            removeEvenRecurs(doc.getChildNodes());
        }
    }

    void write() throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/resource/out.xml"));
        transformer.transform(source, result);
    }

    void removeEvenRecurs(NodeList nodeList) {
        int index = 0;
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Node node = nodeList.item(i);
            String currentNode = node.getNodeName();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                index++;
                if (index % 2 == 0) {
                    String parentNode = node.getParentNode().getNodeName();
                    node.getParentNode().removeChild(node);
                } else {
                    System.out.println(index + "\t" + node.getNodeName());
                    if (node.hasChildNodes()) {
                        removeEvenRecurs(node.getChildNodes());
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        EvenRemoveDomParser evenRemoveDomParser = new
                EvenRemoveDomParser("test.xml", "out_test.xml");

        evenRemoveDomParser.removeEven();
        evenRemoveDomParser.write();
    }
}
