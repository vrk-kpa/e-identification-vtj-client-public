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

import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.soap.vtj.VTJClient;
import fi.vm.kapa.identification.soap.vtj.model.SOAPPersonAdapter;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.vtj.model.VTJResponse;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealVTJService implements VTJService {

    private static Logger log = Logger.getLogger(RealVTJService.class, Logger.VTJ_CLIENT);

    @Autowired
    private VTJClient client;

    @Override
    public VTJResponse getVTJResponse(String identifier, Identifier.Types identifierType, String issuerDn) throws VTJPersonNotExistException {
        SOAPPersonAdapter soapPerson = null;
        VTJResponse vtjResponse = new VTJResponse();

        long startTime = System.currentTimeMillis();
        
        VTJResponseMessage response = null;
        
        try {
			response = client.getResponse(identifier, identifierType, issuerDn);
		} catch (JAXBException e) {
			log.warning("VTJ request failed:" + e.getMessage());
			vtjResponse.setError("vtj.haku.epaonnistui");
			return vtjResponse;
		}
        
        log.info("duration=" + (System.currentTimeMillis() - startTime));
        
        if (response == null) {
        	log.warning("VTJ request failed");
			vtjResponse.setError("vtj.haku.epaonnistui");
			return vtjResponse;
        }
        
        if (VTJResponseMessage.FAULT_CODE_PERSON_NOT_FOUND.equals(response.getFaultCode())) {
        	throw new VTJPersonNotExistException("Requested person does not exist in VTJ");
        }
        
        if (response.getSoapPerson() == null || response.getSoapPerson().getPerson().getHetu() == null) {
        	log.warning("VTJ request failed");
			vtjResponse.setError("vtj.haku.epaonnistui");
			return vtjResponse;
        }
        
        try {
        	soapPerson = response.getSoapPerson();
        	vtjResponse.setPerson(soapPerson.getPerson());
        	vtjResponse.setSuccess(true);
        } catch (Exception e) {
        	log.warning("Person parsing failed reason:" + e.getMessage(), e);
        	vtjResponse.setError("vtj.parsinta.epaonnistui");
        }

        return vtjResponse;
    }

    public void setClient(VTJClient client) {
        this.client = client;
    }
}
