
package lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.0
 * Generated source version: 3.0
 * 
 */
@WebServiceClient(name = "StudentsPortService", targetNamespace = "http://viko.eif.lt/gtamaseviciute/studentWebSerice", wsdlLocation = "http://localhost:8080/ws/students.wsdl")
public class StudentsPortService
    extends Service
{

    private final static URL STUDENTSPORTSERVICE_WSDL_LOCATION;
    private final static WebServiceException STUDENTSPORTSERVICE_EXCEPTION;
    private final static QName STUDENTSPORTSERVICE_QNAME = new QName("http://viko.eif.lt/gtamaseviciute/studentWebSerice", "StudentsPortService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/ws/students.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        STUDENTSPORTSERVICE_WSDL_LOCATION = url;
        STUDENTSPORTSERVICE_EXCEPTION = e;
    }

    public StudentsPortService() {
        super(__getWsdlLocation(), STUDENTSPORTSERVICE_QNAME);
    }

    public StudentsPortService(WebServiceFeature... features) {
        super(__getWsdlLocation(), STUDENTSPORTSERVICE_QNAME, features);
    }

    public StudentsPortService(URL wsdlLocation) {
        super(wsdlLocation, STUDENTSPORTSERVICE_QNAME);
    }

    public StudentsPortService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, STUDENTSPORTSERVICE_QNAME, features);
    }

    public StudentsPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StudentsPortService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns StudentsPort
     */
    @WebEndpoint(name = "StudentsPortSoap11")
    public StudentsPort getStudentsPortSoap11() {
        return super.getPort(new QName("http://viko.eif.lt/gtamaseviciute/studentWebSerice", "StudentsPortSoap11"), StudentsPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StudentsPort
     */
    @WebEndpoint(name = "StudentsPortSoap11")
    public StudentsPort getStudentsPortSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://viko.eif.lt/gtamaseviciute/studentWebSerice", "StudentsPortSoap11"), StudentsPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (STUDENTSPORTSERVICE_EXCEPTION!= null) {
            throw STUDENTSPORTSERVICE_EXCEPTION;
        }
        return STUDENTSPORTSERVICE_WSDL_LOCATION;
    }

}
