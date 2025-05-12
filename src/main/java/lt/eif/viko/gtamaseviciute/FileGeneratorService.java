package lt.eif.viko.gtamaseviciute;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Klasė atsakinga už XML išsaugojimą ir jo konvertavimą į PDF bei HTML formatus naudojant XSLT transformacijas.
 */
public class FileGeneratorService {

    /**
     * Išsaugo XML failą iš pateikto atsakymo objekto, naudodamas JAXB marshallerį.
     *
     * @param response objektas, kurį reikia išsaugoti kaip XML
     * @param clazz    objekto klasė, naudojama JAXB kontekstui sukurti
     * @param filename norimas failo pavadinimas (pvz. "students.xml")
     * @param <T>      generinis tipo parametras
     */
    public static <T> void saveXmlFromResponse(T response, Class<T> clazz, String filename) {
        try {
            File dir = new File("xmlFiles");
            if(!dir.exists()){
                dir.mkdirs();
            }

            File file = new File(dir, filename);
            if (file.exists()) {
                return;
            }

            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(response, file);
            System.out.println("XML saved: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Konvertuoja XML failą į PDF naudodamas FO (.xsl-fo) XSLT šabloną.
     * PDF failas išsaugomas kataloge „pdfFiles“.
     *
     * @param outputPdfName PDF failo pavadinimas be plėtinio
     * @param xmlFileName   XML failo pavadinimas (pvz. "students.xml")
     */
    public static void convertToPDF(String outputPdfName, String xmlFileName) {
        String xsltFile = "src/main/resources/student-pdf.fo.xsl";
        File xmlFile = new File("xmlFiles", xmlFileName);

        File pdfDir = new File("pdfFiles");
        if (!pdfDir.exists()) {
            pdfDir.mkdirs();
        }

        File pdfFile = new File(pdfDir, outputPdfName + ".pdf");

        try {
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            OutputStream out = new FileOutputStream(pdfFile);

            try {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFile)));

                Result res = new SAXResult(fop.getDefaultHandler());
                transformer.transform(new StreamSource(xmlFile), res);
            } finally {
                out.close();
            }

            System.out.println("PDF generated: " + pdfFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Konvertuoja XML failą į HTML naudodamas XSLT šabloną.
     * HTML failas išsaugomas kataloge „htmlFiles“.
     *
     * @param outputHtmlName HTML failo pavadinimas be plėtinio
     * @param xmlFileName    XML failo pavadinimas (pvz. "students.xml")
     */
    public static void convertToHTML(String outputHtmlName, String xmlFileName) {
        String xsltFile = "src/main/resources/student-html.xsl";
        File xmlFile = new File("xmlFiles", xmlFileName);

        File htmlDir = new File("htmlFiles");
        if (!htmlDir.exists()) {
            htmlDir.mkdirs();
        }

        File htmlFile = new File(htmlDir, outputHtmlName + ".html");

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFile)));
            Result output = new javax.xml.transform.stream.StreamResult(htmlFile);
            transformer.transform(new StreamSource(xmlFile), output);

            System.out.println("HTML generated: " + htmlFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
