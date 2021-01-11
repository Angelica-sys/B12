import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Part of the process of unmarshalling XML data into an executable Java object with fasterxml.jackson
 *
 * @author Carin Loven
 * @version 1.0
 */
@JacksonXmlRootElement(localName = "Naringsvarden")
public class Naringsvarden {

    @JacksonXmlProperty(localName = "Naringsvarde")
    @JsonProperty("Naringsvarde")
    private List<Naringsvarde> naringsvarde;

    public List<Naringsvarde> getListOfNaringsvarde() {
        return naringsvarde;
    }
}
