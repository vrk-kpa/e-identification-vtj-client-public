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

import java.util.List;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import fi.vm.kapa.identification.config.SpringPropertyNames;
import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyReqBodyTiedot;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ISoSoAdapterService60;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ObjectFactory;


@Component
@WebService(endpointInterface = "fi.vrk.xml.identification.vtj.ISoSoAdapterService60")
public class VTJClient implements SpringPropertyNames {

    ObjectFactory factory = new ObjectFactory();
    
    @Autowired
    ISoSoAdapterService60 iService;

    @Autowired
    private HttpServletRequest request;

    @Value(VTJ_USERNAME)
    private String vtjUsername;
    
    @Value(VTJ_CA)
    private String vtjCA;
    
    @Value(VTJ_CA_VALIDITY)
    private String vtjCAValidity;
    
    @Value(VTJ_CA_BLACKLISTED)
    private String vtjCABlacklisted;
    
    @Value(VTJ_PASSWORD)
    private String vtjPassword;
    
    @Value(VTJ_SOSONIMI)
    private String vtjSoSonimi;
            
    @Value(XROAD_ENDPOINT)
    private String xrdEndPoint;
    
    @Value(XROAD_CONNECTIVITY_KEYSTORE_PATH)
    String xrdEndPointKeystorePath;
    
    @Value(XROAD_CONNECTIVITY_KEYSTORE_PW)
    String xrdEndPointKeystorePw;
       
   
    private static Logger LOG = Logger.getLogger(VTJClient.class, Logger.VTJ_CLIENT);


    public VTJResponseMessage getResponse(String identifier, Identifier.Types identifierType, String issuerDn) throws JAXBException {
        LOG.debug("VTJClient.getResponse() starts");
                    
        HenkiloTunnusKyselyReqBodyTiedot reqBodyTiedot = factory.createHenkiloTunnusKyselyReqBodyTiedot();
        
        if ( identifierType == Identifier.Types.HETU ) {
            reqBodyTiedot.setHenkilotunnus(identifier);
        } else if ( identifierType == Identifier.Types.SATU ) {
            reqBodyTiedot.setSahkoinenAsiointitunnus(identifier);
            reqBodyTiedot.setHenkilotunnus("");
        }            
        reqBodyTiedot.setKayttajatunnus(vtjUsername);
        reqBodyTiedot.setSalasana(vtjPassword);
        reqBodyTiedot.setSoSoNimi(vtjSoSonimi);
        reqBodyTiedot.setLoppukayttaja(identifier);
        reqBodyTiedot.setVarmenteenMyontaja(issuerDn);
        reqBodyTiedot.setVarmenteenVoimassaolotarkistus(vtjCAValidity);
        reqBodyTiedot.setVarmenteenSulkulistatarkistus(vtjCABlacklisted);
               
        Holder<HenkiloTunnusKyselyReqBodyTiedot> request = new Holder<HenkiloTunnusKyselyReqBodyTiedot>(reqBodyTiedot);

        HenkiloTunnusKyselyResType resType = factory.createHenkiloTunnusKyselyResType();
        Holder<HenkiloTunnusKyselyResType> response = new Holder<HenkiloTunnusKyselyResType>(resType);
        iService.henkilonTunnusKysely(request, response);
        resType = response.value;

        List<Object> list = resType.getAny();
        for (Object o : list) {
            JAXBContext context = JAXBContext
                    .newInstance(VTJResponseMessage.class);
            Unmarshaller um = context.createUnmarshaller();
            um.setEventHandler(new CustomValidationEventHandler());
            return (VTJResponseMessage) um.unmarshal((Node) o);
        }
        return null;
    }
    
}
