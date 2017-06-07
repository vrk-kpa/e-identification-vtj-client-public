package fi.vm.kapa.identification.soap.vtj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Kuolintiedot")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deceased {

    @XmlElement(name = "Kuollut")
    private StringNode deceased;

    @JsonIgnore
    @XmlElement(name = "Kuolinpvm")
    private StringNode deceasedDate;

    public Deceased() {
    	// UncommentedEmptyConstructor required by javax.xml
    }

    public Deceased(StringNode deceased, StringNode deceasedDate) {
        this.deceased = deceased;
        this.deceasedDate = deceasedDate;
    }

    public StringNode getDeceased() {
        return deceased;
    }

    public void setDeceased(StringNode deceased) {
        this.deceased = deceased;
    }

    public StringNode getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(StringNode deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    @Override
    public String toString() {
        return "Deceased [deceased=" + deceased + "]";
    }
}