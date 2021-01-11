import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of the process of unmarshalling XML data into an executable Java object with fasterxml.jackson
 *
 * @author Carin Loven
 * @version 1.0
 */
public class Naringsvarde {

    @JsonProperty("Namn")
    private String namn;

    @JsonProperty("Forkortning")
    private String forkortning;

    @JsonProperty("Varde")
    private String varde;

    @JsonProperty("Enhet")
    private String enhet;

    @JsonProperty("SenastAndrad")
    private String senastAndrad;

    @JsonProperty("Vardetyp")
    private String vardetyp;

    @JsonProperty("Ursprung")
    private String ursprung;

    @JsonProperty("Publikation")
    private String publikation;

    @JsonProperty("Metodtyp")
    private String metodtyp;

    @JsonProperty("Framtagningsmetod")
    private String framtagningsmetod;

    @JsonProperty("Referenstyp")
    private String referenstyp;

    @JsonProperty("Kommentar")
    private String kommentar;

    public String getNamn() {
        return namn;
    }

    public String getForkortning() {
        return forkortning;
    }

    public String getVarde() {
        return varde;
    }

    public String getUnit() {
        return enhet;
    }
}
