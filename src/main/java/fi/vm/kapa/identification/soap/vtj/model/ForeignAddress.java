package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "VakinainenUlkomainenLahiosoite")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForeignAddress {

    @JsonIgnore
    @XmlElement(name = "UlkomainenLahiosoite")
    private StringNode foreignStreetAddress;
    
    @JsonIgnore
    @XmlElement(name = "UlkomainenPaikkakuntaJaValtioS")
    private StringNode foreignLocalityAndStateS;
  
    @JsonIgnore
    @XmlElement(name = "UlkomainenPaikkakuntaJaValtioR")
    private StringNode foreignLocalityAndStateR;
  
    @JsonIgnore
    @XmlElement(name = "UlkomainenPaikkakuntaJaValtioSelvakielinen")
    private StringNode foreignLocalityAndStateClearText;
  
    @JsonIgnore
    @XmlElement(name = "Valtiokoodi3")
    private StringNode stateCode;
    
    @JsonIgnore
    @XmlElement(name = "AsuminenAlkupvm")
    private StringNode recidencyStartDate;
    
    @JsonIgnore
    @XmlElement(name = "AsuminenLoppupvm")
    private StringNode recidencyEndDate;

    public StringNode getForeignStreetAddress() {
        return foreignStreetAddress;
    }

    public void setForeignStreetAddress(StringNode foreignStreetAddress) {
        this.foreignStreetAddress = foreignStreetAddress;
    }

    public StringNode getForeignLocalityAndStateS() {
        return foreignLocalityAndStateS;
    }

    public void setForeignLocalityAndStateS(StringNode foreignLocalityAndStateS) {
        this.foreignLocalityAndStateS = foreignLocalityAndStateS;
    }

    public StringNode getForeignLocalityAndStateR() {
        return foreignLocalityAndStateR;
    }

    public void setForeignLocalityAndStateR(StringNode foreignLocalityAndStateR) {
        this.foreignLocalityAndStateR = foreignLocalityAndStateR;
    }

    public StringNode getForeignLocalityAndStateClearText() {
        return foreignLocalityAndStateClearText;
    }

    public void setForeignLocalityAndStateClearText(
            StringNode foreignLocalityAndStateClearText) {
        this.foreignLocalityAndStateClearText = foreignLocalityAndStateClearText;
    }

    public StringNode getStateCode() {
        return stateCode;
    }

    public void setStateCode(StringNode stateCode) {
        this.stateCode = stateCode;
    }

    public StringNode getRecidencyStartDate() {
        return recidencyStartDate;
    }

    public void setRecidencyStartDate(StringNode recidencyStartDate) {
        this.recidencyStartDate = recidencyStartDate;
    }

    public StringNode getRecidencyEndDate() {
        return recidencyEndDate;
    }

    public void setRecidencyEndDate(StringNode recidencyEndDate) {
        this.recidencyEndDate = recidencyEndDate;
    }
    
    

}
