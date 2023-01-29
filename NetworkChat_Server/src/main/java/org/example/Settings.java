package org.example;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Settings {
    private String serverName = null;
    private int serverPort = 0;

    public Settings(String settingsPath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(settingsPath));
            doc.getDocumentElement().normalize();
            serverName = doc.getElementsByTagName("host").item(0).getTextContent();
            serverPort = Integer.parseInt(doc.getElementsByTagName("port").item(0).getTextContent());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.getStackTrace();
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerName() {
        return serverName;
    }
}