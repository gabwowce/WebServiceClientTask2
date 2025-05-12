
package lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="averageGrade" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "averageGrade"
})
@XmlRootElement(name = "getStudentsAverageGradeResponse")
public class GetStudentsAverageGradeResponse {

    protected float averageGrade;

    /**
     * Gets the value of the averageGrade property.
     * 
     */
    public float getAverageGrade() {
        return averageGrade;
    }

    /**
     * Sets the value of the averageGrade property.
     * 
     */
    public void setAverageGrade(float value) {
        this.averageGrade = value;
    }

}
