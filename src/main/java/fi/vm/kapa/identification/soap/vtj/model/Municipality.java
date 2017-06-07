package fi.vm.kapa.identification.soap.vtj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Kotikunta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Municipality {

    @JsonIgnore
    @XmlElement(name = "Kuntanumero")
    private StringNode municipalityNumber;

    @JsonIgnore
    @XmlElement(name = "KuntaS")
    private StringNode municipalityS;

    @JsonIgnore
    @XmlElement(name = "KuntaR")
    private StringNode municipalityR;

    @JsonIgnore
    @XmlElement(name = "KuntasuhdeAlkupvm")
    private StringNode municipalityStartDate;

    public Municipality() {
    	// UncommentedEmptyConstructor required by javax.xml
    }

    public Municipality(StringNode municipalityNumber, StringNode municipalityS, StringNode municipalityR,
                        StringNode municipalityStartDate) {
        this.municipalityNumber = municipalityNumber;
        this.municipalityS = municipalityS;
        this.municipalityR = municipalityR;
        this.municipalityStartDate = municipalityStartDate;
    }

    public StringNode getMunicipalityNumber() {
        return municipalityNumber;
    }

    public void setMunicipalityNumber(StringNode municipalityNumber) {
        this.municipalityNumber = municipalityNumber;
    }

    public StringNode getMunicipalityS() {
        return municipalityS;
    }

    public void setMunicipalityS(StringNode municipalityS) {
        this.municipalityS = municipalityS;
    }

    public StringNode getMunicipalityR() {
        return municipalityR;
    }

    public void setMunicipalityR(StringNode municipalityR) {
        this.municipalityR = municipalityR;
    }

    public StringNode getMunicipalityStartDate() {
        return municipalityStartDate;
    }

    public void setMunicipalityStartDate(StringNode municipalityStartDate) {
        this.municipalityStartDate = municipalityStartDate;
    }
}
