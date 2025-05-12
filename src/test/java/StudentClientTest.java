
import lt.eif.viko.gtamaseviciute.StudentClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.ws.test.client.RequestMatchers.*;
import static org.springframework.ws.test.client.ResponseCreators.*;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsByGroupResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.SetStudentActiveStatusResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsAverageGradeResponse;

/**
 * Testuoja {@link StudentClient} veikimą naudojant netikrą (mock) SOAP WebService serverį.
 * Naudojama Spring WS testavimo biblioteka {@link MockWebServiceServer}.
 */
public class StudentClientTest {

    private StudentClient studentClient;
    private MockWebServiceServer mockServer;

    /**
     * Paruošia testavimo aplinką prieš kiekvieną testą:
     * - Inicializuoja {@link StudentClient} su marshalleriu
     * - Sukuria {@link MockWebServiceServer} serverį
     */
    @BeforeEach
    void setup() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl");
        try {
            studentClient = new StudentClient();
            studentClient.setMarshaller(marshaller);
            studentClient.setUnmarshaller(marshaller);
            studentClient.setDefaultUri("http://localhost:8080/ws");
            studentClient.afterPropertiesSet();

            mockServer = MockWebServiceServer.createServer(studentClient);
        }catch (Exception e) {
            throw new RuntimeException("Failed to initialize StudentClient", e);
        }

    }

    /**
     * Testuoja, ar {@link StudentClient#getStudent(String)} siunčia tinkamą XML
     * ir ar teisingai interpretuoja gautą atsakymą.
     */
    @Test
    void testGetStudent() {
        String requestPayload = """
    <ns2:getStudentRequest xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice">
        <ns2:name>Tomas</ns2:name>
    </ns2:getStudentRequest>
""";


        String responsePayload = """
    <ns2:getStudentResponse xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice">
        <ns2:student>
            <ns2:name>Tomas</ns2:name>
            <ns2:age>20</ns2:age>
            <ns2:group>PI22A</ns2:group>
        </ns2:student>
    </ns2:getStudentResponse>
""";


        mockServer.expect(payload(new StringSource(requestPayload)))
                .andRespond(withPayload(new StringSource(responsePayload)));

        GetStudentResponse response = studentClient.getStudent("Tomas");
        assertNotNull(response.getStudent());
        assertEquals("Tomas", response.getStudent().getName());
    }

    @Test
    void testGetStudentsByGroup() {
        String group = "PI22A";
        String requestPayload = """
            <ns2:getStudentsByGroupRequest xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\">
                <ns2:group>PI22A</ns2:group>
            </ns2:getStudentsByGroupRequest>
        """;
        String responsePayload = """
            <ns2:getStudentsByGroupResponse xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\">
                <ns2:students>
                    <ns2:name>Tomas</ns2:name>
                </ns2:students>
            </ns2:getStudentsByGroupResponse>
        """;

        mockServer.expect(payload(new StringSource(requestPayload)))
                .andRespond(withPayload(new StringSource(responsePayload)));

        GetStudentsByGroupResponse response = studentClient.getStudentsByGroup(group);
        assertEquals("Tomas", response.getStudents().get(0).getName());
    }

    @Test
    void testSetStudentStatus() {
        String requestPayload = """
            <ns2:setStudentActiveStatusRequest xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\">
                <ns2:name>Tomas</ns2:name>
                <ns2:active>true</ns2:active>
            </ns2:setStudentActiveStatusRequest>
        """;
        String responsePayload = """
            <ns2:setStudentActiveStatusResponse xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\">
                <ns2:active>true</ns2:active>
            </ns2:setStudentActiveStatusResponse>
        """;

        mockServer.expect(payload(new StringSource(requestPayload)))
                .andRespond(withPayload(new StringSource(responsePayload)));

        SetStudentActiveStatusResponse response = studentClient.setStudentStatus("Tomas", true);
        assertTrue(response.isActive());
    }

    @Test
    void testGetAverageStudentsGrade() {
        String responsePayload = """
            <ns2:getStudentsAverageGradeResponse xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\">
                <ns2:averageGrade>8.25</ns2:averageGrade>
            </ns2:getStudentsAverageGradeResponse>
        """;

        mockServer.expect(payload(new StringSource("<ns2:getStudentsAverageGradeRequest xmlns:ns2=\"http://viko.eif.lt/gtamaseviciute/studentWebSerice\"/>")))
                .andRespond(withPayload(new StringSource(responsePayload)));

        GetStudentsAverageGradeResponse response = studentClient.getAverageStudentsGrade();
        assertEquals(8.25f, response.getAverageGrade(), 0.001);
    }


}
