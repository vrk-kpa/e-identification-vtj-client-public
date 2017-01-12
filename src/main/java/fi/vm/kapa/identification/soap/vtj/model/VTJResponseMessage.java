/**
 * The MIT License
 * Copyright (c) 2015 Population Register Centre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "VTJHenkiloVastaussanoma", namespace = "http://xml.vrk.fi/schema/vtjkysely")
@XmlAccessorType(XmlAccessType.FIELD)
public class VTJResponseMessage {

    @XmlElement(name = "Henkilo")
    private Person person;

    @XmlElement(name = "Paluukoodi")
    private ResponseCode responseCode;

    @XmlAttribute(name="sanomatunnus")
    private String msgIdentifier;
    
    @XmlAttribute(name = "tietojenPoimintaaika")
    private String dataTimeStamp;

    @XmlAttribute(name = "versio")
    private String version;
    
    @JsonIgnore
    @XmlAttribute(name = "faultCode")
    private String faultCode;
        
    @XmlElement(name="Asiakasinfo")
    private String customerInfo;
        
    @XmlElement(name="Hakuperusteet")
    private Reason reason;
    
        
    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getDataTimeStamp() {
        return dataTimeStamp;
    }

    public void setDataTimeStamp(String dataTimeStamp) {
        this.dataTimeStamp = dataTimeStamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsgIdentifier() {
        return msgIdentifier;
    }

    public void setMsgIdentifier(String msgIdentifier) {
        this.msgIdentifier = msgIdentifier;
    }

    @Override
    public String toString() {
        return "VTJResponseMessage [person=" + person + ", responseCode="
                + responseCode + ", dataTimeStamp=" + dataTimeStamp
                + ", version=" + version + "]";
    }

}
