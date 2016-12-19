package com.nixsolutions.bondarenko.study.xml.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.*;

public class EvenRemoveSaxHandler extends DefaultHandler {
    private XMLStreamWriter out;

    private Map<String, Integer> tagEvenMap = new HashMap<>();
    private Stack<String> tagStack = new Stack<>();


    private boolean writeChars = true;


    public EvenRemoveSaxHandler(String outFileName) throws IOException, XMLStreamException {
        OutputStream outputStream = new FileOutputStream(new File(outFileName));
        out = XMLOutputFactory.newInstance().createXMLStreamWriter(
                new OutputStreamWriter(outputStream, "utf-8"));
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            out.writeStartDocument();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            out.writeEndDocument();
            out.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tagStack.push(localName);

        int index = 1;
        if (!tagEvenMap.containsKey(localName)) {
            tagEvenMap.put(localName, index);
        } else {
            index = tagEvenMap.get(localName) + 1;
            tagEvenMap.put(localName, index);
        }

        if (index % 2 != 0) {
            writeChars = true;
            try {
                out.writeStartElement(localName);
                for (int i = 0; i < attributes.getLength(); i++) {
                    out.writeAttribute(attributes.getLocalName(i), attributes.getValue(i));
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        } else {
            writeChars = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String pop = tagStack.pop();

        int index = tagEvenMap.get(pop);
        if (index % 2 != 0) {
            try {
                out.writeEndElement();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (writeChars) {
            try {
                out.writeCharacters(new String(ch, start, length));
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }


    private static String convertToFileURL(String fileName) {
        String path = new File(fileName).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        String fileName = "test.xml";
        String baseDir = "src/main/resources/";
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(baseDir + fileName);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();

        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new EvenRemoveSaxHandler(baseDir + "out_" + fileName));
        xmlReader.parse(convertToFileURL(baseDir + fileName));
    }
}


