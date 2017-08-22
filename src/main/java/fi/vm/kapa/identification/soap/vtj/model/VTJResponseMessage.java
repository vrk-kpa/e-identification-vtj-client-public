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

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "VTJHenkiloVastaussanoma", namespace = "http://xml.vrk.fi/schema/vtjkysely")
@XmlAccessorType(XmlAccessType.FIELD)
public class VTJResponseMessage {
	
	@XmlTransient
	public static final String FAULT_CODE_PERSON_NOT_FOUND = "0001";

    @XmlElement(name = "Henkilo")
    private SOAPPersonAdapter soapPerson;

    @XmlElement(name = "Paluukoodi")
    private ResponseCode responseCode;

    @XmlAttribute(name = "sanomatunnus")
    private String msgIdentifier;

    @XmlAttribute(name = "tietojenPoimintaaika")
    private String dataTimeStamp;

    @XmlAttribute(name = "versio")
    private String version;

    @JsonIgnore
    @XmlAttribute(name = "faultCode")
    private String faultCode;

    @JsonIgnore
    @XmlAttribute(name = "faultString")
    private String faultString;

    @XmlElement(name = "Asiakasinfo")
    private CustomerInfo customerInfo;


    @XmlElement(name = "Hakuperusteet")
    private Reason reason;


    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
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

    public SOAPPersonAdapter getSoapPerson() {
        return soapPerson;
    }

    public void setSoapPerson(SOAPPersonAdapter soapPerson) {
        this.soapPerson = soapPerson;
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

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public String toString() {
        return "VTJResponseMessage [soapPerson=" + soapPerson + ", responseCode="
                + responseCode + ", dataTimeStamp=" + dataTimeStamp
                + ", version=" + version + ", faultCode=" + faultCode + ", faultString=" + faultString + "]";
    }

}
