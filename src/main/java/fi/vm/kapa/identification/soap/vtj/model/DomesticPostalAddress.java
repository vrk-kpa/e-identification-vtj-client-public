package fi.vm.kapa.identification.soap.vtj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KotimainenPostiosoite")
@XmlAccessorType(XmlAccessType.FIELD)
public class DomesticPostalAddress {

    @JsonIgnore
    @XmlElement(name = "PostiosoiteS")
    private StringNode addressS;

    @JsonIgnore
    @XmlElement(name = "PostiosoiteR")
    private StringNode addressR;

    @JsonIgnore
    @XmlElement(name = "Postinumero")
    private StringNode postalCode;

    @JsonIgnore
    @XmlElement(name = "PostitoimipaikkaS")
    private StringNode cityS;

    @JsonIgnore
    @XmlElement(name = "PostitoimipaikkaR")
    private StringNode cityR;

    @JsonIgnore
    @XmlElement(name = "PostiosoiteAlkupvm")
    private StringNode recidencyStartDate;

    @JsonIgnore
    @XmlElement(name = "PostiosoiteLoppupvm")
    private StringNode recidencyEndDate;

    public StringNode getAddressS() {
        return addressS;
    }

    public void setAddressS(StringNode addressS) {
        this.addressS = addressS;
    }

    public StringNode getAddressR() {
        return addressR;
    }

    public void setAddressR(StringNode addressR) {
        this.addressR = addressR;
    }

    public StringNode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringNode postalCode) {
        this.postalCode = postalCode;
    }

    public StringNode getCityS() {
        return cityS;
    }

    public void setCityS(StringNode cityS) {
        this.cityS = cityS;
    }

    public StringNode getCityR() {
        return cityR;
    }

    public void setCityR(StringNode cityR) {
        this.cityR = cityR;
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
