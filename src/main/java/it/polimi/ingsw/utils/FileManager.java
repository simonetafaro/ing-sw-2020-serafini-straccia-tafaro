package it.polimi.ingsw.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *Class that manages the access to xml files (god power )
 */
public class FileManager {

    /**
     * Returns the Document instance of the given file (by path name)
     *
     * @param path pathname of the file
     * @return the Document instance of the given file (by path name)
     * @throws IOException if something in opening the file fails
     * @throws ParserConfigurationException if build of a new document fails
     * @throws SAXException if parsing of the document fails
     * @throws URISyntaxException if something getting the url of the document fails
     */
    public Document getFileDocument(String path) throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(getClass().getClassLoader().getResource(path).toURI().toString());
    }

}
