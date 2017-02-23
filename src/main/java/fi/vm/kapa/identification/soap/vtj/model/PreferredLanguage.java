package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "Asiointikieli")
@XmlAccessorType(XmlAccessType.FIELD)
public class PreferredLanguage {

    @JsonIgnore
    @XmlElement(name = "Kielikoodi")
    private StringNode languageCode;
        
    @JsonIgnore
    @XmlElement(name = "KieliS")
    private StringNode languageS;

    @JsonIgnore
    @XmlElement(name = "KieliR")
    private StringNode languageR;

    public StringNode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(StringNode languageCode) {
        this.languageCode = languageCode;
    }

    public StringNode getLanguageS() {
        return languageS;
    }

    public void setLanguageS(StringNode languageS) {
        this.languageS = languageS;
    }

    public StringNode getLanguageR() {
        return languageR;
    }

    public void setLanguageR(StringNode languageR) {
        this.languageR = languageR;
    }
}
