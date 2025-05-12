package lt.eif.viko.gtamaseviciute;

import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentRequest;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsRequest;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsByGroupResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsByGroupRequest;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.SetStudentActiveStatusResponse;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.SetStudentActiveStatusRequest;
import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsAverageGradeResponse;

import lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl.GetStudentsAverageGradeRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * <p>
 * SOAP kliento klasė, naudojama siųsti užklausas į Studentų Web Service ir gauti atsakymus.
 * Klasė paveldi iš {@link WebServiceGatewaySupport} ir leidžia siųsti marshall'intus objektus.
 * </p>
 */
public class StudentClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(StudentClient.class);

    /**
     * Iškviečia WebService metodą ir gauna vieną studentą pagal vardą.
     *
     * @param name Studentas, kurio duomenys ieškomi pagal vardą
     * @return gautas {@link GetStudentResponse} atsakymas su studento duomenimis
     */
    public GetStudentResponse getStudent(String name) {
        GetStudentRequest request = new GetStudentRequest();
        request.setName(name);
        GetStudentResponse response = (GetStudentResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://viko.eif.lt/gtamaseviciute/studentWebSerice/GetStudentRequest"));
        return response;
    }

    /**
     * Grąžina visų studentų sąrašą iš WebService.
     *
     * @return {@link GetStudentsResponse} atsakymas su studentų sąrašu ir jų duomenimis
     */
    public GetStudentsResponse getAllStudents() {
        GetStudentsRequest request = new GetStudentsRequest();
        return (GetStudentsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://viko.eif.lt/gtamaseviciute/studentWebSerice/GetStudentsRequest"));
    }

    /**
     * Grąžina visus studentus, priklausančius nurodytai grupei.
     *
     * @param group grupės pavadinimas
     * @return {@link GetStudentsByGroupResponse} su studentais, priklausančiais šiai grupei
     */
    public GetStudentsByGroupResponse getStudentsByGroup(String group) {
        GetStudentsByGroupRequest request = new GetStudentsByGroupRequest();
        request.setGroup(group);

        return (GetStudentsByGroupResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://viko.eif.lt/gtamaseviciute/studentWebSerice/GetStudentsByGroupRequest"));
    }

    /**
     * Nustato studento aktyvumo statusą (aktyvus/neaktyvus).
     *
     * @param name     studento vardas
     * @param isActive true jei studentas turi būti aktyvus, false – neaktyvus
     * @return {@link SetStudentActiveStatusResponse} su atnaujintu statusu
     */
    public SetStudentActiveStatusResponse setStudentStatus(String name, boolean isActive) {
        SetStudentActiveStatusRequest request = new SetStudentActiveStatusRequest();
        request.setName(name);
        request.setActive(isActive);

        return (SetStudentActiveStatusResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://viko.eif.lt/gtamaseviciute/studentWebSerice/SetStudentActiveStatusRequest"));
    }

    /**
     * Grąžina visų studentų pažymių vidurkį iš WebService.
     *
     * @return {@link GetStudentsAverageGradeResponse} su vidurkiu
     */
    public GetStudentsAverageGradeResponse getAverageStudentsGrade() {
        GetStudentsAverageGradeRequest request = new GetStudentsAverageGradeRequest();
        return (GetStudentsAverageGradeResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request);
    }



}
