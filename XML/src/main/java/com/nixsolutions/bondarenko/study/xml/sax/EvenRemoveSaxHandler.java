package com.nixsolutions.bondarenko.study.xml.sax;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Stack;

public class EvenRemoveSaxHandler extends DefaultHandler {
    private XMLStreamWriter writer;

    private boolean lastWasStart = false;
    private Stack<Integer> indexStack = new Stack<>();

    public EvenRemoveSaxHandler(XMLStreamWriter writer) throws IOException, XMLStreamException {
        this.writer = writer;
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            writer.writeStartDocument();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Error while writing the start of document", e);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            writer.writeEndDocument();
            writer.close();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Error while writing the end of document", e);
        }
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (lastWasStart) {
            indexStack.push(1);
        } else {
            int index = 0;
            if (indexStack.size() > 0) {
                index = indexStack.pop();
            }
            indexStack.push(++index);
        }
        lastWasStart = true;

        if (checkIfToWrite()) {
            try {
                writer.writeStartElement(localName);
                for (int i = 0; i < attributes.getLength(); i++) {
                    writer.writeAttribute(attributes.getLocalName(i), attributes.getValue(i));
                }
            } catch (XMLStreamException e) {
                throw new RuntimeException("Error while writing the element", e);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!lastWasStart) {
            if (indexStack.size() > 0) {
                indexStack.pop();
            }
        }
        lastWasStart = false;

        if (checkIfToWrite()) {
            try {
                writer.writeEndElement();
            } catch (XMLStreamException e) {
                throw new RuntimeException("Error while writing the element", e);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (checkIfToWrite()) {
            try {
                writer.writeCharacters(new String(ch, start, length));
            } catch (XMLStreamException e) {
                throw new RuntimeException("Error while writing characters", e);
            }
        }
    }

    private boolean checkIfToWrite() {
        int size = indexStack.size();
        boolean toWrite = true;
        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                toWrite = indexStack.get(i) % 2 != 0;
                if (!toWrite) {
                    break;
                }
            }
        }
        return toWrite;
    }
}


