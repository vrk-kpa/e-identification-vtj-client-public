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
import fi.vm.kapa.identification.service.PersonService;
import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import org.springframework.stereotype.Service;

@Service
public class DummyVTJService implements VTJService {
    private static Logger LOG = Logger.getLogger(DummyVTJService.class, Logger.VTJ_CLIENT);

    public final PersonService personService;
    private final int vtjDelay;

    private DummyVTJService() {
        this.personService = null;
        this.vtjDelay = 0;
    }

    public DummyVTJService(PersonService personService, int sleepTime) {
        this.personService = personService;
        this.vtjDelay = sleepTime;
    }

    @Override
    public VTJResponse getVTJResponse(String identifier, Identifier.Types identifierType, String issuerDn) {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(vtjDelay);
        } catch (InterruptedException e) {
            LOG.debug("sleep interrupted\n" + e);
        }
        VTJResponse response = new VTJResponse();
        response.setPerson(personService.getPerson(identifier, identifierType));
        response.setSuccess(true);
        LOG.debug("returning person with HETU=" + response.getPerson().getHetu());
        LOG.info("duration=" + (System.currentTimeMillis() - startTime));
        return response;
    }
}
