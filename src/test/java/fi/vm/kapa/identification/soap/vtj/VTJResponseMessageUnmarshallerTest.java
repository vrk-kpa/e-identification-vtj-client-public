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
import fi.vrk.xml.ws.vtj.vtjkysely._1.HenkiloTunnusKyselyResType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class VTJResponseMessageUnmarshallerTest {

    @Autowired
    VTJResponseMessageUnmarshaller unmarshaller;

    @Test
    public void getVtjResponseMessageHandlesFaults() throws Exception {
        HenkiloTunnusKyselyResType kyselyRes = mock(HenkiloTunnusKyselyResType.class);

        ArrayList<Object> nodelist = new ArrayList<>(1);
        nodelist.add(getMockElement("faultCode", "0001"));
        nodelist.add(getMockElement("faultString", "hiphei"));
        when(kyselyRes.getAny()).thenReturn(nodelist);
        VTJResponseMessage response = unmarshaller.getVtjResponseMessage(kyselyRes);

        assertEquals("0001", response.getFaultCode());
        assertEquals("hiphei", response.getFaultString());

    }

    public Element getMockElement(String name, String data) {
        Element elementMock = mock(Element.class);
        when(elementMock.getNodeName()).thenReturn(name);
        when(elementMock.getLocalName()).thenReturn(name);
        when(elementMock.getNamespaceURI()).thenReturn("http://xml.vrk.fi/ws/vtj/vtjkysely/1");
        when(elementMock.getNodeType()).thenReturn(Node.ELEMENT_NODE);
        NodeList nodeListMock = mock(NodeList.class);
        when(nodeListMock.getLength()).thenReturn(1);
        com.sun.org.apache.xerces.internal.dom.TextImpl text = new com.sun.org.apache.xerces.internal.dom.TextImpl(mock(com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl.class), data);
        when(nodeListMock.item(0)).thenReturn(text);
        when(elementMock.getChildNodes()).thenReturn(nodeListMock);
        return elementMock;
    }

}
