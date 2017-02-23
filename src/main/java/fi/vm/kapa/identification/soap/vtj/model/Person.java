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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.vm.kapa.identification.soap.vtj.model.CurrentFirstNames;
import fi.vm.kapa.identification.soap.vtj.model.CurrentLastName;

@XmlRootElement(name = "Henkilo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @JsonIgnore
    @XmlElement(name = "Henkilotunnus")
    private Hetu hetu;

    @JsonIgnore
    @XmlElement(name = "SahkoinenAsiointitunnus")
    private ElectronicIdentifier electronicIdentifier;
    
    @JsonIgnore
    @XmlElement(name = "NykyinenSukunimi")
    private CurrentLastName lastName;

    @JsonIgnore
    @XmlElement(name = "NykyisetEtunimet")
    private CurrentFirstNames firstName;
    
    @JsonIgnore
    @XmlElement(name = "NykyinenKutsumanimi")
    private CurrentNickName nickName;
    
    @JsonIgnore
    @XmlElement(name = "Sahkopostiosoite")
    private String emailAddress;
    
    @JsonIgnore
    @XmlElement(name = "VakinainenKotimainenLahiosoite")
    private DomesticAddress domesticAddress;

    @JsonIgnore
    @XmlElement(name = "VakinainenUlkomainenLahiosoite")
    private ForeignAddress foreignAddress;
    
    @JsonIgnore
    @XmlElement(name = "TilapainenKotimainenLahiosoite")
    private TemporaryDomesticAddress temporaryDomesticAddress;
    
    @JsonIgnore
    @XmlElement(name = "KotimainenPostiosoite")
    private DomesticPostalAddress domesticPostalAddress;
    
    @JsonIgnore
    @XmlElement(name = "Kotikunta")
    private Municipality municipality;
    
    @JsonIgnore
    @XmlElement(name = "Kuolintiedot")
    private Deceased deceasedInfo;
    
    @JsonIgnore
    @XmlElement(name = "Kuolleeksijulistamistiedot")
    private DeceasedProclamation deceasedProclamation;
        
    @JsonIgnore
    @XmlElement(name = "Aidinkieli")
    private MotherTongue motherTongue;
    
    @JsonIgnore
    @XmlElement(name = "Asiointikieli")
    private PreferredLanguage preferredLang;
    
    
    @XmlElement(name = "SuomenKansalaisuusTietokoodi")
    private String finnishCitizenship;
    
    @JsonIgnore
    @XmlElement(name="Turvakielto")
    private ProtectionOrder protectionOrder;
    
    public Hetu getHetu() {
        return hetu;
    }

    public void setHetu(Hetu hetu) {
        this.hetu = hetu;
    }
   
    public ElectronicIdentifier getElectronicIdentifier() {
        return electronicIdentifier;
    }

    public void setElectronicIdentifier(ElectronicIdentifier electronicIdentifier) {
        this.electronicIdentifier = electronicIdentifier;
    }

    public DomesticAddress getDomesticAddress() {
        return domesticAddress;
    }

    public void setDomesticAddress(DomesticAddress domesticAddress) {
        this.domesticAddress = domesticAddress;
    }

    public ForeignAddress getForeignAddress() {
        return foreignAddress;
    }

    public void setForeignAddress(ForeignAddress foreignAddress) {
        this.foreignAddress = foreignAddress;
    }

    public TemporaryDomesticAddress getTemporaryDomesticAddress() {
        return temporaryDomesticAddress;
    }

    public void setTemporaryDomesticAddress(
            TemporaryDomesticAddress temporaryDomesticAddress) {
        this.temporaryDomesticAddress = temporaryDomesticAddress;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public ProtectionOrder getProtectionOrder() {
        return protectionOrder;
    }

    public void setProtectionOrder(ProtectionOrder protectionOrder) {
        this.protectionOrder = protectionOrder;
    }

    public CurrentLastName getLastName() {
        return lastName;
    }

    public void setLastName(CurrentLastName lastName) {
        this.lastName = lastName;
    }

    public CurrentFirstNames getFirstName() {
        return firstName;
    }

    public void setFirstName(CurrentFirstNames firstName) {
        this.firstName = firstName;
    }

    public CurrentNickName getNickName() {
        return nickName;
    }

    public void setNickName(CurrentNickName nickName) {
        this.nickName = nickName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Deceased getDeceasedInfo() {
        return deceasedInfo;
    }

    public void setDeceasedInfo(Deceased deceasedInfo) {
        this.deceasedInfo = deceasedInfo;
    }

    public String getFinnishCitizenship() {
        return finnishCitizenship;
    }

    public void setFinnishCitizenship(String finnishCitizenship) {
        this.finnishCitizenship = finnishCitizenship;
    }

    @Override
    public String toString() {
        return "Person [hetu=" + hetu + ", satu=" + electronicIdentifier + "]";
    }
}
