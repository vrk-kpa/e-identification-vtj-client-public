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

import fi.vm.kapa.identification.test.DummyPersonService;
import fi.vm.kapa.identification.vtjclient.service.DummyVTJService;
import fi.vm.kapa.identification.vtjclient.service.RealVTJService;
import fi.vm.kapa.identification.vtjclient.service.VTJService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class VTJServiceConfiguration {
    static final String INSTANCE_HINT = String.valueOf(new Random().nextLong());

    @Value("${vtjclient_dummydata:false}")
    private boolean vtjDummydataToBeUsed;

    @Value("${vtjclient_dummydata_defaultHetu:010191-9641}")
    private String dummyDataDefaultHetu;

    @Value("${vtjclient_dummydata_sleep:0}")
    private int vtjDummydataSleep;

    @Bean(name = "vtjService")
    VTJService provideVTJService() {
        if (vtjDummydataToBeUsed) {
            DummyPersonService dummyPersonService = new DummyPersonService(dummyDataDefaultHetu);
            dummyPersonService.setAdditionalStateInfo(getAdditionalStateInfo());
            return new DummyVTJService(dummyPersonService, vtjDummydataSleep);
        } else {
            return new RealVTJService();
        }
    }

    private String getAdditionalStateInfo() {
        return "instance=" + INSTANCE_HINT + ";delay=" + vtjDummydataSleep;
    }

}
