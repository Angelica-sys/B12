import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Part of the process of unmarshalling XML data into an executable Java object with fasterxml.jackson
 * @author Carin Loven
 * @version 1.0
 */
@JacksonXmlRootElement(localName = "LivsmedelsLista")
public class LivsmedelsLista {

    @JacksonXmlProperty(localName = "Livsmedel")
    @JsonProperty("Livsmedel")
    private List<Livsmedel> livsmedel;

    public List<Livsmedel> getListOfLivsmedel() {
        return livsmedel;
    }
}
