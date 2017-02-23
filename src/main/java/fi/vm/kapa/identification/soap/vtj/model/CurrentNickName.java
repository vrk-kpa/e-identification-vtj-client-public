package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "NykyinenKutsumanimi")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrentNickName {
    
    @JsonIgnore
    @XmlElement(name = "Kutsumanimi")
    private StringNode nickName;

    public StringNode getNickName() {
        return nickName;
    }

    public void setNickName(StringNode nickName) {
        this.nickName = nickName;
    }
}
