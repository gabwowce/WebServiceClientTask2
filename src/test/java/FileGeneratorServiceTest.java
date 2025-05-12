import lt.eif.viko.gtamaseviciute.FileGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testų klasė, skirta testuoti {@link FileGeneratorService} metodus:
 * - XML failo konvertavimą į HTML
 * - XML failo konvertavimą į PDF
 *
 * Testai naudoja paruoštus testinius failus iš `src/test/testFiles/`.
 */
class FileGeneratorServiceTest {

    private String xmlFilename = "test-student.xml";
    private String htmlOutput = "test-html-output";
    private String pdfOutput = "test-pdf-output";

    /**
     * Prieš kiekvieną testą:
     * - Sukuria reikiamus aplankus, jei jų nėra (`xmlFiles`, `htmlFiles`, `pdfFiles`)
     * - Nukopijuoja testinį XML failą į `xmlFiles/` katalogą
     * - Nukopijuoja XSLT failus į `src/main/resources/`, kad jie būtų pasiekiami FOP transformacijoms
     *
     * @throws IOException jei kopijavimas nepavyksta
     */
    @BeforeEach
    void setup() throws IOException {
        File xmlDir = new File("xmlFiles");
        File htmlDir = new File("htmlFiles");
        File pdfDir = new File("pdfFiles");

        xmlDir.mkdirs();
        htmlDir.mkdirs();
        pdfDir.mkdirs();
    }

    /**
     * Tikrina ar HTML failas teisingai sugeneruojamas iš XML ir XSL failų.
     */
    @Test
    void testConvertToHTML_generatesHtmlFile() {
        FileGeneratorService.convertToHTML(htmlOutput, xmlFilename);
        File htmlFile = new File("htmlFiles", htmlOutput + ".html");
        assertTrue(htmlFile.exists(), "HTML file should be generated");
    }

    /**
     * Tikrina ar PDF failas teisingai sugeneruojamas iš XML ir XSL-FO failų.
     */
    @Test
    void testConvertToPDF_generatesPdfFile() {
        FileGeneratorService.convertToPDF(pdfOutput, xmlFilename);
        File pdfFile = new File("pdfFiles", pdfOutput + ".pdf");
        assertTrue(pdfFile.exists(), "PDF file should be generated");
    }
}
