package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement(name = "Aidinkieli")
@XmlAccessorType(XmlAccessType.FIELD)
public class MotherTongue {

    @JsonIgnore
    @XmlElement(name = "AidinkieliTietokoodi")
    private StringNode motherTongueCode;
    
    @JsonIgnore
    @XmlElement(name = "Kielikoodi")
    private StringNode languageCode;
        
    @JsonIgnore
    @XmlElement(name = "KieliS")
    private StringNode languageS;

    @JsonIgnore
    @XmlElement(name = "KieliR")
    private StringNode languageR;
        
    @JsonIgnore
    @XmlElement(name = "KieliSelvakielinen")
    private StringNode languageClearText;

   
    public StringNode getMotherTongueCode() {
        return motherTongueCode;
    }

    public void setMotherTongueCode(StringNode motherTongueCode) {
        this.motherTongueCode = motherTongueCode;
    }

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

    public StringNode getLanguageClearText() {
        return languageClearText;
    }

    public void setLanguageClearText(StringNode languageClearText) {
        this.languageClearText = languageClearText;
    }
}

