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
import fi.vm.kapa.identification.vtj.model.Person;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Henkilo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOAPPersonAdapter {

    private final Person person = new Person();
    
    private static final String HETU_VALIDITY_TRUE = "1";
    private static final String SATU_VALIDITY_TRUE = "1";
    private static final String DECEASED_VALUE_TRUE = "1";
    private static final String PROTECTION_ORDER_FALSE = "0";
    
    @JsonIgnore
    @XmlElement(name = "KotimainenPostiosoite")
    private DomesticPostalAddress domesticPostalAddress;

    @JsonIgnore
    @XmlElement(name = "Kuolleeksijulistamistiedot")
    private DeceasedProclamation deceasedProclamation;

    @JsonIgnore
    @XmlElement(name = "Aidinkieli")
    private MotherTongue motherTongue;

    @JsonIgnore
    @XmlElement(name = "Asiointikieli")
    private PreferredLanguage preferredLang;

    @XmlElement(name = "Henkilotunnus")
    public void setHetu(Hetu hetu) {
        person.setHetu(hetu.getHetu());

        if (HETU_VALIDITY_TRUE.equals(hetu.getValidityCode())) {
            person.setHetuValid(true);
        } else {
            person.setHetuValid(false);
        }

    }

    @XmlElement(name = "SahkoinenAsiointitunnus")
    public void setElectronicIdentifier(ElectronicIdentifier electronicIdentifier) {
        if (electronicIdentifier != null) {
            person.setSatu(electronicIdentifier.getElectronicIdentifier());
            if (SATU_VALIDITY_TRUE.equals(electronicIdentifier.getValidityCode())) {
                person.setSatuValid(true);
            } else {
                person.setSatuValid(false);
            }
        }
    }

    @XmlElement(name = "VakinainenKotimainenLahiosoite")
    public void setDomesticAddress(DomesticAddress domesticAddress) {
        if (domesticAddress != null) {
            if (domesticAddress.getAddressS() != null && domesticAddress.getAddressS().getValue() != null
                    && domesticAddress.getAddressS().getValue().length() > 0) {
                person.setDomesticAddressS(domesticAddress.getAddressS().getValue());
            } else if (domesticAddress.getAddressR() != null && domesticAddress.getAddressR().getValue() != null
                    && domesticAddress.getAddressR().getValue().length() > 0) {
                person.setDomesticAddressR(domesticAddress.getAddressR().getValue());
            }

            if (domesticAddress.getPostalCode() != null && domesticAddress.getPostalCode().getValue() != null
                    && domesticAddress.getPostalCode().getValue().length() > 0) {
                person.setPostalCode(domesticAddress.getPostalCode().getValue());
            }

            if (domesticAddress.getCityS() != null && domesticAddress.getCityS().getValue() != null
                    && domesticAddress.getCityS().getValue().length() > 0) {
                person.setCityS(domesticAddress.getCityS().getValue());
            } else if (domesticAddress.getCityR() != null && domesticAddress.getCityR().getValue() != null
                    && domesticAddress.getCityR().getValue().length() > 0) {
                person.setCityR(domesticAddress.getCityR().getValue());
            }
        }
    }

    @XmlElement(name = "VakinainenUlkomainenLahiosoite")
    public void setForeignAddress(ForeignAddress foreignAddress) {
        if (foreignAddress != null) {
            if (foreignAddress.getForeignStreetAddress() != null && foreignAddress.getForeignStreetAddress().getValue() != null
                    && foreignAddress.getForeignStreetAddress().getValue().length() > 0) {
                person.setForeignAddress(foreignAddress.getForeignStreetAddress().getValue());
            }

            if (foreignAddress.getForeignLocalityAndStateS() != null && foreignAddress.getForeignLocalityAndStateS().getValue() != null
                    && foreignAddress.getForeignLocalityAndStateS().getValue().length() > 0) {
                person.setForeignLocalityAndStateS(foreignAddress.getForeignLocalityAndStateS().getValue());
            } else if (foreignAddress.getForeignLocalityAndStateR() != null && foreignAddress.getForeignLocalityAndStateR().getValue() != null
                    && foreignAddress.getForeignLocalityAndStateR().getValue().length() > 0) {
                person.setForeignLocalityAndStateR(foreignAddress.getForeignLocalityAndStateR().getValue());
            }

            if (foreignAddress.getForeignLocalityAndStateClearText() != null && foreignAddress.getForeignLocalityAndStateClearText().getValue() != null
                    && foreignAddress.getForeignLocalityAndStateClearText().getValue().length() > 0) {
                person.setForeignLocalityAndStateClearText(foreignAddress.getForeignLocalityAndStateClearText().getValue());
            }

            if (foreignAddress.getStateCode() != null && foreignAddress.getStateCode().getValue() != null
                    && foreignAddress.getStateCode().getValue().length() > 0) {
                person.setStateCode(foreignAddress.getStateCode().getValue());
            }
        }
    }

    @XmlElement(name = "TilapainenKotimainenLahiosoite")
    public void setTemporaryDomesticAddress(
            TemporaryDomesticAddress temporaryDomesticAddress) {
        if (temporaryDomesticAddress != null) {
            if (temporaryDomesticAddress.getAddressS() != null && temporaryDomesticAddress.getAddressS().getValue() != null
                    && temporaryDomesticAddress.getAddressS().getValue().length() > 0) {
                person.setTemporaryDomesticAddressS(temporaryDomesticAddress.getAddressS().getValue());
            } else if (temporaryDomesticAddress.getAddressR() != null && temporaryDomesticAddress.getAddressR().getValue() != null
                    && temporaryDomesticAddress.getAddressR().getValue().length() > 0) {
                person.setTemporaryDomesticAddressR(temporaryDomesticAddress.getAddressR().getValue());
            }
            if (temporaryDomesticAddress.getPostalCode() != null && temporaryDomesticAddress.getPostalCode().getValue() != null
                    && temporaryDomesticAddress.getPostalCode().getValue().length() > 0) {
                person.setTemporaryPostalCode(temporaryDomesticAddress.getPostalCode().getValue());
            }
            if (temporaryDomesticAddress.getCityS() != null && temporaryDomesticAddress.getCityS().getValue() != null
                    && temporaryDomesticAddress.getCityS().getValue().length() > 0) {
                person.setTemporaryCityS(temporaryDomesticAddress.getCityS().getValue());
            } else if (temporaryDomesticAddress.getCityR() != null && temporaryDomesticAddress.getCityR().getValue() != null
                    && temporaryDomesticAddress.getCityR().getValue().length() > 0) {
                person.setTemporaryCityR(temporaryDomesticAddress.getCityR().getValue());
            }
        }
    }

    @XmlElement(name = "Kotikunta")
    public void setMunicipality(Municipality municipality) {
        if (municipality != null) {
            if (municipality.getMunicipalityNumber() != null
	        		&& municipality.getMunicipalityNumber().getValue() != null
	        		&& municipality.getMunicipalityNumber().getValue().length() > 0) {
                person.setMunicipalityCode(municipality.getMunicipalityNumber().getValue());
            }
            if (municipality.getMunicipalityS() != null
            		&& municipality.getMunicipalityS().getValue() != null
                    && municipality.getMunicipalityS().getValue().length() > 0) {
                person.setMunicipalityS(municipality.getMunicipalityS().getValue());
            } else if (municipality.getMunicipalityR() != null
            		&& municipality.getMunicipalityR().getValue() != null
                    && municipality.getMunicipalityR().getValue().length() > 0) {
                person.setMunicipalityR(municipality.getMunicipalityR().getValue());
            }
        }
    }

    @XmlElement(name = "Turvakielto")
    public void setProtectionOrder(ProtectionOrder protectionOrder) {
        person.setProtectionOrder(false);
        if (protectionOrder != null
        		&& protectionOrder.getProtectionOrder() != null
                && protectionOrder.getProtectionOrder().getValue() != null
                && !PROTECTION_ORDER_FALSE.equals(protectionOrder.getProtectionOrder().getValue())
                && protectionOrder.getProtectionOrder().getValue().length() > 0) {
            person.setProtectionOrder(true);
        }
    }

    @XmlElement(name = "NykyinenSukunimi")
    public void setLastName(CurrentLastName lastName) {
        if (lastName != null) {
            person.setLastName(lastName.getLastName().getValue());
        }
    }


    @XmlElement(name = "NykyisetEtunimet")
    public void setFirstName(CurrentFirstNames firstName) {
        if (firstName != null) {
            person.setFirstNames(firstName.getFirstName().getValue());
        }
    }

    @XmlElement(name = "NykyinenKutsumanimi")
    public void setNickName(CurrentNickName nickName) {
        if (nickName != null && nickName.getNickName().getValue().length() > 0) {
            person.setNickName(nickName.getNickName().getValue());
        }
    }

    @XmlElement(name = "Sahkopostiosoite")
    public void setEmail(String emailAddress) {
        person.setEmailAddress(emailAddress);
    }


    @XmlElement(name = "Kuolintiedot")
    public void setDeceasedInfo(Deceased deceasedInfo) {
        person.setDeceased(false);
        if (deceasedInfo != null &&
        		deceasedInfo.getDeceased() != null &&
        		DECEASED_VALUE_TRUE.equals(deceasedInfo.getDeceased().getValue())) {
            person.setDeceased(true);
        }
    }

    @XmlElement(name = "SuomenKansalaisuusTietokoodi")
    public void setFinnishCitizenshipCode(String finnishCitizenship) {
        person.setFinnishCitizenship(finnishCitizenship);
    }

    public Person getPerson() {
        return person;
    }
}
