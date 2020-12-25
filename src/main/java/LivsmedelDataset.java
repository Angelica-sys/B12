import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Part of the process of unmarshalling XML data into an executable Java object with fasterxml.jackson
 * @author Carin Loven
 * @version 1.0
 */
@JacksonXmlRootElement(localName = "LivsmedelDataset")
public class LivsmedelDataset {

    @JsonProperty("Version")
    private String version;

    @JacksonXmlProperty(localName = "LivsmedelsLista")
    @JsonProperty("LivsmedelsLista")
    private LivsmedelsLista livsmedelsLista;

    public LivsmedelsLista getLivsmedelsLista() {
        return livsmedelsLista;
    }
}
