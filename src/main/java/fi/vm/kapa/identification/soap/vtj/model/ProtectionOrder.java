package fi.vm.kapa.identification.soap.vtj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Turvakielto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtectionOrder {

    @XmlElement(name = "TurvakieltoTieto")
    private StringNode protectionOrder;

    @JsonIgnore
    @XmlElement(name = "TurvakieltoPaattymispvm")
    private StringNode protectionOrderEndDate;

    public ProtectionOrder() {
    	// UncommentedEmptyConstructor required by javax.xml
    }

    public ProtectionOrder(StringNode protectionOrder, StringNode protectionOrderEndDate) {
        this.protectionOrder = protectionOrder;
        this.protectionOrderEndDate = protectionOrderEndDate;
    }

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