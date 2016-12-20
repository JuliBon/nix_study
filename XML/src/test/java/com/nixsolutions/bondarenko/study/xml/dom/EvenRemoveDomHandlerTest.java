package com.nixsolutions.bondarenko.study.xml.dom;

import com.nixsolutions.bondarenko.study.xml.dom.EvenRemoveDomHandler;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

public class EvenRemoveDomHandlerTest {
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private final String xmlOutPath = classLoader.getResource(".").getPath();
    private EvenRemoveDomHandler evenRemoveDomHandler = new EvenRemoveDomHandler();

    @Test
    public void testHandle() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        //String inputFileName = "goods.xml";
        String inputFileName = "goods2.xml";
        String expectedFileName = "expected_" + inputFileName;

        Document documentOut = evenRemoveDomHandler.handle(classLoader.getResourceAsStream(inputFileName));

        Element elementExpected = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(classLoader.getResourceAsStream(expectedFileName))
                .getDocumentElement();

        assertThat(the(documentOut), isEquivalentTo(the(elementExpected)));
    }

    @Test
    public void testWrite() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        //String inputFileName = "goods.xml";
        String inputFileName = "goods2.xml";
        String expectedFileName = "expected_" + inputFileName;
        File outFile = new File(xmlOutPath + "out_" + inputFileName);

        Document documentOut = evenRemoveDomHandler.handle(classLoader.getResourceAsStream(inputFileName));
        evenRemoveDomHandler.write(documentOut, new FileOutputStream(outFile));
        Element elementOut = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new FileInputStream(outFile))
                .getDocumentElement();

        Element elementExpected = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(classLoader.getResourceAsStream(expectedFileName))
                .getDocumentElement();

        assertThat(the(elementOut), isEquivalentTo(the(elementExpected)));
    }

    @Test(expected = SAXException.class)
    public void testBadSource() throws IOException, SAXException, ParserConfigurationException {
        evenRemoveDomHandler.handle(classLoader.getResourceAsStream("bad.xml"));
    }
}
