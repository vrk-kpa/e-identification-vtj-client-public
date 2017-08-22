package fi.vm.kapa.identification.vtjclient.service;

import fi.vm.kapa.identification.soap.vtj.VTJClient;
import fi.vm.kapa.identification.soap.vtj.model.Hetu;
import fi.vm.kapa.identification.soap.vtj.model.SOAPPersonAdapter;
import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.vtj.model.VTJResponse;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class VTJServiceTest {

    @Test
    public void getVTJResponseSuccessTest() throws JAXBException {
        VTJClient vtjMock = mock(VTJClient.class);
        VTJResponseMessage message = getMessage("121212-9999", "1");
        when(vtjMock.getResponse(any(), any(), any())).thenReturn(message);
        RealVTJService vtjService = new RealVTJService();
        vtjService.setClient(vtjMock);
        VTJResponse response = null;
		try {
			response = vtjService.getVTJResponse("identifier", Identifier.Types.HETU, "issuerDn");
		} catch (VTJPersonNotExistException e) {
			e.printStackTrace();
			Assert.fail();
		}
        assertEquals(message.getSoapPerson().getPerson(), response.getPerson());
        assertEquals("121212-9999", response.getPerson().getHetu());
    }

    @Ignore
    @Test
    public void getVTJResponseFaultcodeWithPersonTest() throws JAXBException {
        VTJClient vtjMock = mock(VTJClient.class);
        VTJResponseMessage message = getMessage("121212-9999", "1");
        message.setFaultCode("0001");
        message.setFaultString("faultString");
        when(vtjMock.getResponse(any(), any(), any())).thenReturn(message);
        RealVTJService vtjService = new RealVTJService();
        vtjService.setClient(vtjMock);
        VTJResponse response = null;
		try {
			response = vtjService.getVTJResponse("identifier", Identifier.Types.HETU, "issuerDn");
		} catch (VTJPersonNotExistException e) {
			e.printStackTrace();
			Assert.fail();
		}
        assertFalse(response.isSuccess());
        assertEquals("vtj.haku.epaonnistui", response.getError());
    }

    @Test
    public void getVTJResponsePersonNotFoundTest() throws JAXBException {
        VTJClient vtjMock = mock(VTJClient.class);
        VTJResponseMessage message = new VTJResponseMessage();
        message.setFaultCode("0001");
        message.setFaultString("faultString");
        when(vtjMock.getResponse(any(), any(), any())).thenReturn(message);
        RealVTJService vtjService = new RealVTJService();
        vtjService.setClient(vtjMock);
        VTJResponse response = null;
        boolean notExistThrown = false;
		try {
			response = vtjService.getVTJResponse("identifier", Identifier.Types.HETU, "issuerDn");
		} catch (VTJPersonNotExistException e) {
			notExistThrown = true;
		}
        Assert.assertTrue(notExistThrown);
    }

    private VTJResponseMessage getMessage(String hetuString, String validityCode) {
        Hetu hetu = new Hetu();
        hetu.setHetu(hetuString);
        hetu.setValidityCode(validityCode);
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setHetu(hetu);
        VTJResponseMessage message = new VTJResponseMessage();
        message.setSoapPerson(person);
        return message;
    }

}
