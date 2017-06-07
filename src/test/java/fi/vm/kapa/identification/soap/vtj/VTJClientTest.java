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

import fi.vm.kapa.identification.soap.vtj.model.VTJResponseMessage;
import fi.vm.kapa.identification.type.Identifier;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyReqBodyTiedot;
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ISoSoAdapterService60;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ObjectFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@ContextConfiguration(locations = "classpath:testContext.xml")
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class
})
@RunWith(SpringJUnit4ClassRunner.class)

public class VTJClientTest {

    @Autowired
    @InjectMocks
    VTJClient vtjClient;

    @Autowired
    ObjectFactory factory;

    @Test
    public void getResponseThrowsSocketTimeoutExceptionOnTimeout() throws JAXBException {
        ISoSoAdapterService60 iServiceMock = mock(ISoSoAdapterService60.class);
        SocketTimeoutException socketException = new SocketTimeoutException();
        WebServiceException webException = new WebServiceException(socketException);
        Mockito.doThrow(webException).when(iServiceMock).henkilonTunnusKysely(any(), any());
        VTJClient vtjClient = new VTJClient(iServiceMock, factory, mock(VTJResponseMessageUnmarshaller.class),
                "vtjSoSonimi", "vtjUsername", "vtjPassword", "vtjCAValidity", "vtjCABlacklisted");
        try {
            vtjClient.getResponse("huuhaa", Identifier.Types.HETU, "huuhaa");
            fail("Expected WebServiceException");
        } catch (WebServiceException e) {
            assertEquals(socketException, e.getCause());
        }
    }

    @Test
    public void getResponseUsesUnmarshaller() throws JAXBException {
        ISoSoAdapterService60 iServiceMock = mock(ISoSoAdapterService60.class);

        ObjectFactory factory = mock(ObjectFactory.class);
        when(factory.createHenkiloTunnusKyselyReqBodyTiedot()).thenReturn(mock(HenkiloTunnusKyselyReqBodyTiedot.class));
        HenkiloTunnusKyselyResType kyselyRes = mock(HenkiloTunnusKyselyResType.class);

        when(factory.createHenkiloTunnusKyselyResType()).thenReturn(kyselyRes);
        VTJResponseMessageUnmarshaller unmarshaller = mock(VTJResponseMessageUnmarshaller.class);

        when(unmarshaller.getVtjResponseMessage(any())).thenReturn(new VTJResponseMessage());
        VTJClient vtjClient = new VTJClient(iServiceMock, factory, unmarshaller,
                "vtjSoSonimi", "vtjUsername", "vtjPassword", "vtjCAValidity", "vtjCABlacklisted");
        vtjClient.getResponse("210281-9988", Identifier.Types.HETU, null);
        verify(unmarshaller, times(1)).getVtjResponseMessage(kyselyRes);
    }

    /* Commented out because it is broken
    @Test
    public void getResponse() throws Exception {
        Server server = new Server(12345);
        try {
            server.setHandler(new SleepServer());

            try {
                VTJResponseMessage message = vtjClient.getResponse("210281-9988", Identifier.Types.HETU, null);
                System.out.println(message);
                System.out.println(message.getSoapPerson());
            } catch (WebServiceException e) {
                assertThat(e.getCause(), instanceOf(SocketTimeoutException.class));
            } catch (JAXBException e) {
                System.out.println(e);
                System.out.println(e.getLinkedException());
                System.out.println(e.getMessage());
            }
        } finally {
        }
    }

    class SleepServer extends AbstractHandler {
        public void handle(String target,
                           Request baseRequest,
                           HttpServletRequest request,
                           HttpServletResponse response)
                throws IOException, ServletException {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println("<h1>Hello World</h1>");
        }


    }
    */
}