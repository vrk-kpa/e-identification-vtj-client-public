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
package fi.vm.kapa.identification.config;

import fi.vm.kapa.identification.soap.handlers.XroadHeaderHandler;
import fi.vrk.xml.ws.vtj.vtjkysely._1.ISoSoAdapterService60;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.BindingProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
public class SoapConfiguration {

    @Value("${xroad_endpoint}")
    String xroadEndpoints;

    @Value("${vtj_connection_timeout}")
    private String vtjConnectTimeout;

    @Value("${vtj_receive_timeout}")
    private String vtjReceiveTimeout;

    @Autowired
    private XroadHeaderHandler xroadHeaderHandler;


    @Bean
    ISoSoAdapterService60 vtjClient() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = buildJaxWsProxyFactoryBean();
        ISoSoAdapterService60 iSoSoAdapterService60 = (ISoSoAdapterService60) jaxWsProxyFactoryBean.create();
        Map<String,Object> requestContext = ((BindingProvider) iSoSoAdapterService60).getRequestContext();
        // for options, see http://stackoverflow.com/questions/13967069/how-to-set-timeout-for-jax-ws-webservice-call
        requestContext.put("javax.xml.ws.client.connectionTimeout", vtjConnectTimeout);
        requestContext.put("javax.xml.ws.client.receiveTimeout", vtjReceiveTimeout);
        return iSoSoAdapterService60;
    }

    private JaxWsProxyFactoryBean buildJaxWsProxyFactoryBean() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        factory.setAddress(getEndpoints().get(0));

        factory.getHandlers().add(xroadHeaderHandler);

        factory.setServiceClass(ISoSoAdapterService60.class);

        return factory;
    }

    private List<String> getEndpoints() {
        String[] endpoints = xroadEndpoints.split(",");
        return Arrays.asList(endpoints);
    }

}
