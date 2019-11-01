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
import fi.vm.kapa.identification.soap.vtj.CustomValidationEventHandler;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.util.DummyDataDeviator;
import fi.vm.kapa.identification.util.DummyDataDeviatorException;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyReqBodyTiedot;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;
import org.springframework.stereotype.Service;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ObjectFactory;
import java.io.StringReader;
import org.xml.sax.InputSource;

@Service
public class DummyVTJService implements VTJService {
    private static Logger log = Logger.getLogger(DummyVTJService.class, Logger.VTJ_CLIENT);

    public final PersonService personService;
    private final DummyDataDeviator dummyDataDeviator;   
    ObjectFactory factory;
    private final String dummySoap = "<Henkilo>\n" +
"                  <Henkilotunnus voimassaolokoodi=\"1\">010113+933W</Henkilotunnus>\n" +
"                  <NykyinenSukunimi>\n" +
"                     <Sukunimi>Tunnistettu</Sukunimi>\n" +
"                  </NykyinenSukunimi>\n" +
"                  <NykyisetEtunimet>\n" +
"                     <Etunimet>Testihenkilö</Etunimet>\n" +
"                  </NykyisetEtunimet>\n" +
"                  <NykyinenKutsumanimi>\n" +
"                     <Kutsumanimi/>\n" +
"                  </NykyinenKutsumanimi>\n" +
"                  <VakinainenKotimainenLahiosoite>\n" +
"                     <LahiosoiteS>Ateläniitynpolku 29 G</LahiosoiteS>\n" +
"					 <LahiosoiteR/>\n" +
"					 <Postinumero>00980</Postinumero>\n" +
"					 <PostitoimipaikkaS>HELSINKI</PostitoimipaikkaS>\n" +
"					 <PostitoimipaikkaR>HELSINKI</PostitoimipaikkaR>\n" +
"					 <AsuminenAlkupvm>20081221</AsuminenAlkupvm>\n" +
"                  </VakinainenKotimainenLahiosoite>\n" +
"                  <VakinainenUlkomainenLahiosoite>\n" +
"                     <UlkomainenLahiosoite/>\n" +
"                     <UlkomainenPaikkakuntaJaValtioS/>\n" +
"                     <UlkomainenPaikkakuntaJaValtioR/>\n" +
"                     <UlkomainenPaikkakuntaJaValtioSelvakielinen/>\n" +
"                     <Valtiokoodi3/>\n" +
"                     <AsuminenAlkupvm/>\n" +
"                     <AsuminenLoppupvm/>\n" +
"                  </VakinainenUlkomainenLahiosoite>\n" +
"                  <Kotikunta>\n" +
"                     <Kuntanumero>091</Kuntanumero>\n" +
"                     <KuntaS>Helsinki</KuntaS>\n" +
"                     <KuntaR>Helsingfors</KuntaR>\n" +
"                     <KuntasuhdeAlkupvm/>\n" +
"                  </Kotikunta>\n" +
"                  <Kuolintiedot>\n" +
"                     <Kuollut/>\n" +
"                     <Kuolinpvm/>\n" +
"                  </Kuolintiedot>\n" +
"                  <Kuolleeksijulistamistiedot>\n" +
"                     <Kuolleeksijulistamispvm/>\n" +
"                  </Kuolleeksijulistamistiedot>\n" +
"                  <Aidinkieli>\n" +
"                     <Kielikoodi>fi</Kielikoodi>\n" +
"                     <KieliS>suomi</KieliS>\n" +
"                     <KieliR>finska</KieliR>\n" +
"                     <KieliSelvakielinen/>\n" +
"                  </Aidinkieli>\n" +
"                  <SuomenKansalaisuusTietokoodi>1</SuomenKansalaisuusTietokoodi>\n" +
"                  <Turvakielto>\n" +
"                     <TurvakieltoTieto/>\n" +
"                  </Turvakielto>\n" +
"               </Henkilo>";

    private DummyVTJService() {
        this.personService = null;
        this.dummyDataDeviator = null;
    }

    public DummyVTJService(PersonService personService, DummyDataDeviator dummyDataDeviator) {
        this.personService = personService;
        this.dummyDataDeviator = dummyDataDeviator;
        factory = new ObjectFactory();
    }

    @Override
    public VTJResponse getVTJResponse(String identifier, Identifier.Types identifierType, String issuerDn) throws VTJPersonNotExistException {
        long startTime = System.currentTimeMillis();
        try {
            dummyMarshal();
            Thread.sleep(dummyDataDeviator.getDelay());
            dummyUnMarshal();
        } catch (DummyDataDeviatorException ddde) {
            try {
                Thread.sleep(dummyDataDeviator.getErrorSleep());
                dummyUnMarshal();
            } catch (InterruptedException e) {
                log.debug("sleep interrupted\n" + e);
                Thread.currentThread().interrupt();
            }
            VTJResponse response = new VTJResponse();
            response.setError(ddde.getMessage());
            return response;
        } catch (InterruptedException e) {
            log.debug("sleep interrupted\n" + e);
            Thread.currentThread().interrupt();
        }
        VTJResponse response = new VTJResponse();
        response.setPerson(personService.getPerson(identifier, identifierType));
        response.setSuccess(true);
        log.debug("returning person with HETU=" + response.getPerson().getHetu());
        log.info("duration=" + (System.currentTimeMillis() - startTime));
        return response;
    }
    
    private void dummyUnMarshal() {
        JAXBContext context;
        InputSource inputSource = new InputSource( new StringReader( dummySoap ) );
        try {
            context = JAXBContext
                    .newInstance(VTJResponseMessage.class);
                    Unmarshaller um = context.createUnmarshaller();
        um.setEventHandler(new CustomValidationEventHandler());
        Object unmarshalled = um.unmarshal(inputSource);
        if (unmarshalled instanceof VTJResponseMessage) {
            VTJResponseMessage vtjResponse = (VTJResponseMessage) unmarshalled;
        }
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(DummyVTJService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void dummyMarshal() {
        String identifier = "dummy";
        Identifier.Types identifierType = Identifier.Types.HETU;
        String issuerDn = "dummy";
        
        HenkiloTunnusKyselyReqBodyTiedot reqBodyTiedot = factory.createHenkiloTunnusKyselyReqBodyTiedot();

        if (identifierType == Identifier.Types.HETU) {
            reqBodyTiedot.setHenkilotunnus(identifier);
        } else if (identifierType == Identifier.Types.SATU) {
            reqBodyTiedot.setSahkoinenAsiointitunnus(identifier);
            reqBodyTiedot.setHenkilotunnus("");
        }

        reqBodyTiedot.setKayttajatunnus("dummy");
        reqBodyTiedot.setSalasana("dummy");
        reqBodyTiedot.setSoSoNimi("dummy");
        reqBodyTiedot.setLoppukayttaja(identifier);
        reqBodyTiedot.setVarmenteenMyontaja(issuerDn);
        reqBodyTiedot.setVarmenteenVoimassaolotarkistus("dummy");
        reqBodyTiedot.setVarmenteenSulkulistatarkistus("dummy");

        Holder<HenkiloTunnusKyselyReqBodyTiedot> request = new Holder<>(reqBodyTiedot);

        HenkiloTunnusKyselyResType resType = factory.createHenkiloTunnusKyselyResType();
    }
}
