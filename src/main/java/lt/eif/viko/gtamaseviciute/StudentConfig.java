package lt.eif.viko.gtamaseviciute;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * <p>
 * Konfigūracinė klasė, apibrėžianti Spring konteinerio komponentus, susijusius su SOAP kliento veikimu.
 * </p>
 * <p>
 * Čia sukuriami ir registruojami {@code Jaxb2Marshaller} bei {@code StudentClient} komponentai.
 * </p>
 */
@Configuration
public class StudentConfig {

    /**
     * Sukuria ir sukonfigūruoja {@link Jaxb2Marshaller}, kuris atsakingas už XML ir Java objektų konvertavimą.
     *
     * @return sukonfigūruotas marshaller objektas
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("lt.eif.viko.gtamaseviciute.consumingwebservice.wsdl");
        return marshaller;
    }

    /**
     * Sukuria {@link StudentClient} objektą, kuris naudoja SOAP ryšį bendrauti su WebService serveriu.
     *
     * @param marshaller JAXB marshaller naudojamas serializacijai/de-serializacijai
     * @return sukonfigūruotas StudentClient komponentas
     */
    @Bean
    public StudentClient studentClient(Jaxb2Marshaller marshaller) {
        StudentClient client = new StudentClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
