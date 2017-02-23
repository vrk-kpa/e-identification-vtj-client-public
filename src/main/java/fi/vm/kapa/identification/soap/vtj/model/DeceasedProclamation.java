package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement(name = "Kuolleeksijulistamistiedot")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeceasedProclamation {
    
    @JsonIgnore
    @XmlElement(name = "Kuolleeksijulistamispvm")
    private StringNode deceasedDate;

    public StringNode getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(StringNode deceasedDate) {
        this.deceasedDate = deceasedDate;
    }    
    
    
}
