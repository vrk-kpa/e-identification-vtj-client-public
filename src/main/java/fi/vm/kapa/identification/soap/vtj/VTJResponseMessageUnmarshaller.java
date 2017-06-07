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
package fi.vm.kapa.identification.soap.vtj;

import fi.vm.kapa.identification.soap.vtj.model.FaultCode;
import fi.vm.kapa.identification.soap.vtj.model.FaultString;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.List;

@Component
public class VTJResponseMessageUnmarshaller {

    public VTJResponseMessage getVtjResponseMessage(HenkiloTunnusKyselyResType resType) throws JAXBException {
        List<Object> list = resType.getAny();
        VTJResponseMessage vtjResponse = null;
        FaultCode faultCode = null;
        FaultString faultString = null;
        for (Object o : list) {
            JAXBContext context = JAXBContext
                    .newInstance(VTJResponseMessage.class, FaultCode.class, FaultString.class);
            Unmarshaller um = context.createUnmarshaller();
            um.setEventHandler(new CustomValidationEventHandler());
            Object unmarshalled = um.unmarshal((Node) o);
            if (unmarshalled instanceof VTJResponseMessage) {
                vtjResponse = (VTJResponseMessage) unmarshalled;
            } else if (unmarshalled instanceof FaultCode) {
                faultCode = (FaultCode) unmarshalled;
            } else if (unmarshalled instanceof FaultString) {
                faultString = (FaultString) unmarshalled;
            }
        }
        if (null != faultCode || null != faultString) {
            VTJResponseMessage faultMessage = new VTJResponseMessage();
            if (faultCode != null) {
            	faultMessage.setFaultCode(faultCode.getValue());
            }
            if (faultString != null) {
            	faultMessage.setFaultString(faultString.getValue());
            }
            return faultMessage;
        }
        return vtjResponse;
    }

}
