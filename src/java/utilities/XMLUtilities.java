/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author PhuongNT
 */
public class XMLUtilities {

    public static String unicodeEscaped(char ch) {
        if (ch < 0x10) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100) {
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000) {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }

    public static String unicodeEscaped(String str) {
        String returnValue = "";
        for (int i = 0; i < str.length(); i++) {
            returnValue += unicodeEscaped(str.charAt(i));
        }
        return returnValue;
    }

    public static boolean checkValidationXML(String xmlData, String schemaPath) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));

            Validator valid = schema.newValidator();
            InputSource is = new InputSource(new StringReader(xmlData));
            valid.validate(new SAXSource(is));
        } catch (SAXException ex) {
            Logger.getLogger(XMLUtilities.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(XMLUtilities.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static <T> boolean checkValidationXML(T object, String schemaPath) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));

            JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.marshal(object, new DefaultHandler());
        } catch (SAXException ex) {
            Logger.getLogger(XMLUtilities.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtilities.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static <T> String marshallerToString(T object) throws JAXBException {
        JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = jaxb.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }

    public static <T> void marshallerToTransfer(T object, Writer writer) throws JAXBException {
        JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = jaxb.createMarshaller();

//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(object, writer);
    }

    public static <T> T unmarshalXMLString(String xml, Class<T> classType) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        T result = (T) unmarshaller.unmarshal(reader);
        return result;
    }
}
