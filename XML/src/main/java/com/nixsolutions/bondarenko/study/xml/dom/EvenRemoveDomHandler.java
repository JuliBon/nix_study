package com.nixsolutions.bondarenko.study.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class EvenRemoveDomHandler {

    public Document handle(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        document.getDocumentElement().normalize();
        removeEvenRecurs(document.getChildNodes());
        return document;
    }

    public void write(Document document, OutputStream outputStream) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        transformer.transform(source, result);
    }

    private void removeEvenRecurs(NodeList nodeList) {
        int index = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.DOCUMENT_NODE) {
                index++;
                if (index % 2 == 0) {
                    node.getParentNode().removeChild(node);
                } else {
                    if (node.hasChildNodes()) {
                        removeEvenRecurs(node.getChildNodes());
                    }
                }
            }
        }
    }
}
