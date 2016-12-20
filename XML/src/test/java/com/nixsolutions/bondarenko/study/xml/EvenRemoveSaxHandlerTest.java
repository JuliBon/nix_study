package com.nixsolutions.bondarenko.study.xml;

import com.nixsolutions.bondarenko.study.xml.sax.EvenRemoveSaxHandler;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;


public class EvenRemoveSaxHandlerTest {
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private final String xmlOutDir = classLoader.getResource(".").getPath();

    private XMLReader xmlReader;

    @Before
    public void before() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        xmlReader = spf.newSAXParser().getXMLReader();
    }


    @Test
    public void test() throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        //String inputFileName = "goods.xml";
        //String inputFileName = "goods2.xml";
        String inputFileName = "test.xml";
        String expectedFileName = "expected_" + inputFileName;
        File outFile = new File(xmlOutDir + "out_" + inputFileName);

        InputSource inputSource = new InputSource(classLoader.getResourceAsStream(inputFileName));
        OutputStream outputStream = new FileOutputStream(outFile);
        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
                new OutputStreamWriter(outputStream, "utf-8"));

        xmlReader.setContentHandler(new EvenRemoveSaxHandler(out));
        xmlReader.parse(inputSource);

        InputStream xmlOut = new FileInputStream(outFile);
        InputStream xmlExpected = classLoader.getResourceAsStream(expectedFileName);

        Element elementOut = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(xmlOut)
                .getDocumentElement();

        Element elementExpected = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(xmlExpected)
                .getDocumentElement();

        assertThat(the(elementOut), isEquivalentTo(the(elementExpected)));
    }
}
