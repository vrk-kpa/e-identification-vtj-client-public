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
package fi.vm.kapa.identification.vtjclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.soap.vtj.VTJClient;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.vtj.model.Person;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import fi.vm.kapa.identification.type.Identifier;

@Service
public class VTJService {

    private static Logger LOG = Logger.getLogger(VTJService.class, Logger.VTJ_CLIENT);

    @Autowired
    private VTJClient client;

    final int HETU_LENGTH = 11;

    public VTJResponse getVTJResponse(String identifier, Identifier.Types identifierType, String IssuerDn) {
        Person person = null;
        VTJResponse vtjResponse = new VTJResponse();
        
        try {
            long startTime = System.currentTimeMillis();
            
            VTJResponseMessage response = client.getResponse(identifier, identifierType, IssuerDn);
            
            LOG.info("duration=" + (System.currentTimeMillis() - startTime));

            if ( response == null || response.getPerson() == null || response.getPerson().getHetu() == null ) {
                throw new VTJPersonNotExistException("Requested person not received from VTJ.");
            }
            
            try {
                person = fromSoapMessage(response);
                vtjResponse.setPerson(person);
                vtjResponse.setSuccess(true);
            } catch (Throwable e) {
                LOG.warning("Person parsing failed reason:" + e.getMessage());
                vtjResponse.setError("vtj.parsinta.epaonnistui");
            }
            
        } catch (Throwable e) {
            LOG.warning("VTJ request failed:" + e.getMessage());
            vtjResponse.setError("vtj.haku.epaonnistui");
        }
        
        return vtjResponse;
    }

    private Person fromSoapMessage(VTJResponseMessage message) {
        fi.vm.kapa.identification.soap.vtj.model.Person sPerson = message.getPerson();
        Person person = new Person();
        person.setHetu(sPerson.getHetu().getHetu());
        
        if (sPerson.getHetu().getValidityCode().equals("1")) { // "1" = hetu valid
            person.setHetuValid(true);
        } else {
            person.setHetuValid(false);
        }
        
        if ( sPerson.getLastName() != null ) {
            person.setLastName(sPerson.getLastName().getLastName().getValue());
        }
        if ( sPerson.getFirstName() != null ) {
            person.setFirstNames(sPerson.getFirstName().getFirstName().getValue());
        }    
        
        if ( sPerson.getElectronicIdentifier() != null ) {
            person.setSatu(sPerson.getElectronicIdentifier().getElectronicIdentifier());
            if (sPerson.getElectronicIdentifier().getValidityCode().equals("1")) { // "1" = satu valid
                person.setSatuValid(true);
            } else {
                person.setSatuValid(false);
            }
        }
        
        if ( sPerson.getNickName() != null && sPerson.getNickName().getNickName().getValue().length() > 0 ) {
            person.setNickName(sPerson.getNickName().getNickName().getValue());
        }
        
        if ( sPerson.getEmailAddress() != null && sPerson.getEmailAddress().length() > 0 ) {
            person.setEmailAddress(sPerson.getEmailAddress());
        }
      
        if ( sPerson.getMunicipality() != null) {
            if ( sPerson.getMunicipality().getMunicipalityNumber() != null ) {
                if ( sPerson.getMunicipality().getMunicipalityNumber().getValue() != null 
                        && sPerson.getMunicipality().getMunicipalityNumber().getValue().length() > 0 ) {
                    person.setMunicipalityCode(sPerson.getMunicipality().getMunicipalityNumber().getValue());
                }
            }    
            if (  sPerson.getMunicipality().getMunicipalityS() != null && sPerson.getMunicipality().getMunicipalityS().getValue() != null 
                    && sPerson.getMunicipality().getMunicipalityS().getValue().length() > 0 ) {
                person.setMunicipalityS(sPerson.getMunicipality().getMunicipalityS().getValue());
            } else if ( sPerson.getMunicipality().getMunicipalityR() != null && sPerson.getMunicipality().getMunicipalityR().getValue() != null 
                    && sPerson.getMunicipality().getMunicipalityR().getValue().length() > 0 ) {
                person.setMunicipalityR(sPerson.getMunicipality().getMunicipalityR().getValue());
            }
        }    
        
        if ( sPerson.getDomesticAddress() != null) {
            if ( sPerson.getDomesticAddress().getAddressS() != null && sPerson.getDomesticAddress().getAddressS().getValue() != null 
                    && sPerson.getDomesticAddress().getAddressS().getValue().length() > 0 ) {
                person.setDomesticAddressS(sPerson.getDomesticAddress().getAddressS().getValue());
            } else if ( sPerson.getDomesticAddress().getAddressR() != null && sPerson.getDomesticAddress().getAddressR().getValue() != null 
                    && sPerson.getDomesticAddress().getAddressR().getValue().length() > 0 ) {
                person.setDomesticAddressR(sPerson.getDomesticAddress().getAddressR().getValue());
            }
        
            if ( sPerson.getDomesticAddress().getPostalCode() != null && sPerson.getDomesticAddress().getPostalCode().getValue() != null 
                    && sPerson.getDomesticAddress().getPostalCode().getValue().length() > 0 ) {
                person.setPostalCode(sPerson.getDomesticAddress().getPostalCode().getValue());
            }
        
            if ( sPerson.getDomesticAddress().getCityS() != null && sPerson.getDomesticAddress().getCityS().getValue() != null 
                    && sPerson.getDomesticAddress().getCityS().getValue().length() > 0 ) {
                person.setCityS(sPerson.getDomesticAddress().getCityS().getValue());
            } else if ( sPerson.getDomesticAddress().getCityR() != null && sPerson.getDomesticAddress().getCityR().getValue() != null 
                    && sPerson.getDomesticAddress().getCityR().getValue().length() > 0 ) {
                person.setCityR(sPerson.getDomesticAddress().getCityR().getValue());
            }
        }
        
        if (sPerson.getForeignAddress() != null) {
            if ( sPerson.getForeignAddress().getForeignStreetAddress() != null && sPerson.getForeignAddress().getForeignStreetAddress().getValue() != null 
                    && sPerson.getForeignAddress().getForeignStreetAddress().getValue().length() > 0 ) {
                person.setForeignAddress(sPerson.getForeignAddress().getForeignStreetAddress().getValue());
            }
        
            if ( sPerson.getForeignAddress().getForeignLocalityAndStateS() != null && sPerson.getForeignAddress().getForeignLocalityAndStateS().getValue() != null 
                    && sPerson.getForeignAddress().getForeignLocalityAndStateS().getValue().length() > 0 ) {
                person.setForeignLocalityAndStateS(sPerson.getForeignAddress().getForeignLocalityAndStateS().getValue());
            } else if ( sPerson.getForeignAddress().getForeignLocalityAndStateR() != null && sPerson.getForeignAddress().getForeignLocalityAndStateR().getValue() != null 
                    && sPerson.getForeignAddress().getForeignLocalityAndStateR().getValue().length() > 0 ) {
                person.setForeignLocalityAndStateR(sPerson.getForeignAddress().getForeignLocalityAndStateR().getValue());
            }
        
            if ( sPerson.getForeignAddress().getForeignLocalityAndStateClearText() != null && sPerson.getForeignAddress().getForeignLocalityAndStateClearText().getValue() != null 
                    && sPerson.getForeignAddress().getForeignLocalityAndStateClearText().getValue().length() > 0 ) {
                person.setForeignLocalityAndStateClearText(sPerson.getForeignAddress().getForeignLocalityAndStateClearText().getValue());
            }
        
            if ( sPerson.getForeignAddress().getStateCode() != null && sPerson.getForeignAddress().getStateCode().getValue() != null 
                    && sPerson.getForeignAddress().getStateCode().getValue().length() > 0 ) {
                person.setStateCode(sPerson.getForeignAddress().getStateCode().getValue());
            }
        }    
        
        if (sPerson.getTemporaryDomesticAddress() != null) {
            if ( sPerson.getTemporaryDomesticAddress().getAddressS() != null && sPerson.getTemporaryDomesticAddress().getAddressS().getValue() != null 
                    && sPerson.getTemporaryDomesticAddress().getAddressS().getValue().length() > 0 ) {
                person.setTemporaryDomesticAddressS(sPerson.getTemporaryDomesticAddress().getAddressS().getValue());
            } else if ( sPerson.getTemporaryDomesticAddress().getAddressR() != null && sPerson.getTemporaryDomesticAddress().getAddressR().getValue() != null 
                    && sPerson.getTemporaryDomesticAddress().getAddressR().getValue().length() > 0 ) {
                person.setTemporaryDomesticAddressR(sPerson.getTemporaryDomesticAddress().getAddressR().getValue());
            }
            if ( sPerson.getTemporaryDomesticAddress().getPostalCode() != null && sPerson.getTemporaryDomesticAddress().getPostalCode().getValue() != null 
                    && sPerson.getTemporaryDomesticAddress().getPostalCode().getValue().length() > 0 ) {
                person.setTemporaryPostalCode(sPerson.getTemporaryDomesticAddress().getPostalCode().getValue());
            }
            if ( sPerson.getTemporaryDomesticAddress().getCityS() != null && sPerson.getTemporaryDomesticAddress().getCityS().getValue() != null 
                    && sPerson.getTemporaryDomesticAddress().getCityS().getValue().length() > 0 ) {
                person.setTemporaryCityS(sPerson.getTemporaryDomesticAddress().getCityS().getValue());
            } else if ( sPerson.getTemporaryDomesticAddress().getCityR() != null && sPerson.getTemporaryDomesticAddress().getCityR().getValue() != null 
                    && sPerson.getTemporaryDomesticAddress().getCityR().getValue().length() > 0 ) {
                person.setTemporaryCityR(sPerson.getTemporaryDomesticAddress().getCityR().getValue());
            }
        }
                       
        person.setDeceased(false);
        if ( sPerson.getDeceasedInfo() != null && sPerson.getDeceasedInfo().getDeceased() != null && sPerson.getDeceasedInfo().getDeceased().getValue().length() > 0 ) {
            if ( sPerson.getDeceasedInfo().getDeceased().getValue().equals("1") ) {
                person.setDeceased(true);
            }    
        }
        
        person.setProtectionOrder(false);
        if ( sPerson.getProtectionOrder() != null && sPerson.getProtectionOrder().getProtectionOrder() != null 
                && sPerson.getProtectionOrder().getProtectionOrder().getValue().length() > 0 ) {
            if ( sPerson.getProtectionOrder().getProtectionOrder().getValue().equals("1") ) {
                person.setProtectionOrder(true);
            }    
        }
        
        if ( sPerson.getFinnishCitizenship() != null && sPerson.getFinnishCitizenship().length() > 0  ) {
             person.setFinnishCitizenship(sPerson.getFinnishCitizenship());
        }
        
        LOG.debug("fromSoapMessage: person=" + person);
        return person;
    }
}
