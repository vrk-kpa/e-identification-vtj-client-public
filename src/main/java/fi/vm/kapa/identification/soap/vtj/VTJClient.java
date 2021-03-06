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

import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyReqBodyTiedot;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ISoSoAdapterService60;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;


@Component
@WebService(endpointInterface = "fi.vrk.xml.identification.vtj.ISoSoAdapterService60")
public class VTJClient {
    private static Logger log = Logger.getLogger(VTJClient.class, Logger.VTJ_CLIENT);

    private final ObjectFactory factory;
    private final ISoSoAdapterService60 iService;

    private final String vtjUsername;
    private final String vtjCAValidity;
    private final String vtjCABlacklisted;
    private final String vtjPassword;
    private final String vtjSoSonimi;
    private final VTJResponseMessageUnmarshaller vtjResponseMessageUnmarshaller;

    @Autowired
    public VTJClient(
            ISoSoAdapterService60 iService,
            @Named("vtjkyselyObjectFactory") ObjectFactory objectFactory,
            VTJResponseMessageUnmarshaller vtjResponseMessageUnmarshaller,
            @Value("${vtj_sosonimi}") String vtjSoSonimi,
            @Value("${vtj_username}") String vtjUsername,
            @Value("${vtj_password}") String vtjPassword,
            @Value("${vtj_ca_validity}") String vtjCAValidity,
            @Value("${vtj_ca_blacklisted}") String vtjCABlacklisted) {
        this.iService = iService;
        this.factory = objectFactory;
        this.vtjResponseMessageUnmarshaller = vtjResponseMessageUnmarshaller;
        this.vtjSoSonimi = vtjSoSonimi;
        this.vtjUsername = vtjUsername;
        this.vtjPassword = vtjPassword;
        this.vtjCABlacklisted = vtjCABlacklisted;
        this.vtjCAValidity = vtjCAValidity;
    }


    public VTJResponseMessage getResponse(String identifier, Identifier.Types identifierType, String issuerDn) throws JAXBException {
        log.debug("VTJClient.getResponse() starts");

        HenkiloTunnusKyselyReqBodyTiedot reqBodyTiedot = factory.createHenkiloTunnusKyselyReqBodyTiedot();

        if (identifierType == Identifier.Types.HETU) {
            reqBodyTiedot.setHenkilotunnus(identifier);
        } else if (identifierType == Identifier.Types.SATU) {
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

        Holder<HenkiloTunnusKyselyReqBodyTiedot> request = new Holder<>(reqBodyTiedot);

        HenkiloTunnusKyselyResType resType = factory.createHenkiloTunnusKyselyResType();
        Holder<HenkiloTunnusKyselyResType> response = new Holder<>(resType);

        iService.henkilonTunnusKysely(request, response);

        resType = response.value;

        return vtjResponseMessageUnmarshaller.getVtjResponseMessage(resType);
    }


}