package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "Turvakielto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtectionOrder {

    @XmlElement(name = "TurvakieltoTieto")
    private StringNode protectionOrder;
    
    @JsonIgnore
    @XmlElement(name = "TurvakieltoPaattymispvm")
    private StringNode protectionOrderEndDate;

    public StringNode getProtectionOrder() {
        return protectionOrder;
    }

    public void setProtectionOrder(StringNode protectionOrder) {
        this.protectionOrder = protectionOrder;
    }

    public StringNode getProtectionOrderEndDate() {
        return protectionOrderEndDate;
    }

    public void setProtectionOrderEndDate(StringNode protectionOrderEndDate) {
        this.protectionOrderEndDate = protectionOrderEndDate;
    }
}