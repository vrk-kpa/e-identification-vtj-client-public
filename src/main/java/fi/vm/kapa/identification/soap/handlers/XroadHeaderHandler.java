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
package fi.vm.kapa.identification.soap.handlers;

import eu.x_road.xsd.xroad.Client;
import eu.x_road.xsd.xroad.ObjectFactory;
import eu.x_road.xsd.xroad.Service;
import fi.vm.kapa.identification.config.SpringPropertyNames;
import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.rest.identification.RequestIdentificationFilter;
import fi.vm.kapa.identification.vtj.model.VtjIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Component
public class XroadHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private static Logger log = Logger.getLogger(XroadHeaderHandler.class, Logger.VTJ_CLIENT);

    private ObjectFactory factory = new ObjectFactory();

    @Autowired
    private HttpServletRequest request;

    public static final String ORIG_USERID_HEADER = "origUserId";
    public static final String ORIG_REQUEST_ID_HEADER = "origRequestId";

    @Value(SpringPropertyNames.CLIENT_OBJECT_TYPE)
    private String clientObjectType;

    @Value(SpringPropertyNames.CLIENT_SDSB_INSTANCE)
    private String clientSdsbInstance;

    @Value(SpringPropertyNames.CLIENT_MEMBER_CLASS)
    private String clientMemberClass;

    @Value(SpringPropertyNames.CLIENT_MEMBER_CODE)
    private String clientMemberCode;

    @Value(SpringPropertyNames.CLIENT_SUBSYSTEM_CODE)
    private String clientSubsystemCode;

    @Value(SpringPropertyNames.SERVICE_OBJECT_TYPE)
    private String serviceObjectType;

    @Value(SpringPropertyNames.SERVICE_SDSB_INSTANCE)
    private String serviceSdsbInstance;

    @Value(SpringPropertyNames.SERVICE_MEMBER_CLASS)
    private String serviceMemberClass;

    @Value(SpringPropertyNames.SERVICE_MEMBER_CODE)
    private String serviceMemberCode;

    @Value(SpringPropertyNames.SERVICE_SUBSYSTEM_CODE)
    private String serviceSubsystemCode;

    @Value(SpringPropertyNames.SERVICE_SERVICE_CODE)
    private String serviceServiceCode;

    @Value(SpringPropertyNames.SERVICE_SERVICE_VERSION)
    private String serviceServiceVersion;

    @Value(SpringPropertyNames.XROAD_PROTOCOL_VERSION)
    private String xroadProtocolVersion;

    @Value(SpringPropertyNames.VTJ_CLIENT_ID)
    private String vtjClientId;

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
    
    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        Boolean outboundProperty = (Boolean) messageContext
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            SOAPMessage soapMsg = messageContext.getMessage();
            SOAPEnvelope soapEnv;
            try {
                soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader header = soapEnv.getHeader();
                if (header == null) {
                    header = soapEnv.addHeader();
                }

                JAXBElement<String> idElement = factory.createId(UUID.randomUUID().toString());
                SOAPHeaderElement idHeaderElement = header.addHeaderElement(idElement.getName());
                idHeaderElement.addTextNode(idElement.getValue());

                String origUserId = request.getHeader(RequestIdentificationFilter.XROAD_END_USER);
                if (origUserId == null) {
                    origUserId = "e-identification-end-user-unknown";
                }

                JAXBElement<String> userIdElement = factory.createUserId(origUserId);
                SOAPHeaderElement uidHeaderElement = header.addHeaderElement(userIdElement.getName());
                uidHeaderElement.addTextNode(userIdElement.getValue());

                String origRequestId = request.getHeader(VtjIssue.REQUEST_IDENTIFIER_HEADER);
                VtjIssue vtjIssue = new VtjIssue(origRequestId);
                vtjIssue.setVtjClientId(vtjClientId);

                JAXBElement<String> issueElement = factory.createIssue(vtjIssue.toString());
                SOAPHeaderElement issueHeaderElement = header.addHeaderElement(issueElement.getName());
                issueHeaderElement.addTextNode(issueElement.getValue());

                Client client = factory.createClient();
                JAXBElement<Client> clientElement = factory.createClient(client);
                client.setObjectType(this.clientObjectType);
                client.setXRoadInstance(this.clientSdsbInstance);
                client.setMemberClass(this.clientMemberClass);
                client.setMemberCode(this.clientMemberCode);
                client.setSubsystemCode(this.clientSubsystemCode);

                Marshaller marshaller;
                marshaller = JAXBContext.newInstance(Client.class).createMarshaller();
                marshaller.marshal(clientElement, header);

                Service service = factory.createService();
                JAXBElement<Service> serviceElement = factory.createService(service);
                service.setObjectType(this.serviceObjectType);
                service.setXRoadInstance(this.serviceSdsbInstance);
                service.setMemberClass(this.serviceMemberClass);
                service.setMemberCode(this.serviceMemberCode);
                service.setSubsystemCode(this.serviceSubsystemCode);
                service.setServiceCode(this.serviceServiceCode);
                service.setServiceVersion(this.serviceServiceVersion);

                marshaller = JAXBContext.newInstance(Service.class).createMarshaller();
                marshaller.marshal(serviceElement, header);

                JAXBElement<String> protocolVersionElement = factory.createProtocolVersion(this.xroadProtocolVersion);
                SOAPHeaderElement protocolVersionHeaderElement = header.addHeaderElement(protocolVersionElement.getName());
                protocolVersionHeaderElement.addTextNode(protocolVersionElement.getValue());

                soapMsg.saveChanges();

            } catch (Exception e) {
                log.error("Xroad header handler exception occured " + e);
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
    	// Note to sonar, needed to implement interface
    }
}
