import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Part of the process of unmarshalling XML data into an executable Java object with fasterxml.jackson
 *
 * @author Carin Loven
 * @version 1.0
 */
@JacksonXmlRootElement(localName = "Livsmedel")
public class Livsmedel {

    @JsonProperty("Nummer")
    private String nummer;

    @JsonProperty("Namn")
    private String namn;

    @JsonProperty("ViktGram")
    private String viktGram;

    @JsonProperty("Huvudgrupp")
    private String huvudgrupp;

    @JacksonXmlProperty(localName = "Naringsvarden")
    @JsonProperty("Naringsvarden")
    private Naringsvarden naringsvarden;

    public Naringsvarden getNaringsvarden() {
        return naringsvarden;
    }

    public String getNamn() {
        return namn;
    }
}