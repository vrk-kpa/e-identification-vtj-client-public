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

import fi.vm.kapa.identification.soap.vtj.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class VTJServiceProtectionOrderTest {
    RealVTJService vtjService = new RealVTJService();

    @Test
    public void protectionOrderReturnsFalseWhenNullProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        person.setProtectionOrder(null);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertFalse(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsFalseWhenNullProtectionOrderProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        person.setProtectionOrder(new ProtectionOrder());
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertFalse(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsFalseWhenNullProtectionOrderProtectionOrderStringNode() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        ProtectionOrder protectionOrder = new ProtectionOrder();
        protectionOrder.setProtectionOrder(new StringNode());
        person.setProtectionOrder(protectionOrder);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertFalse(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsFalseWhenEmptyProtectionOrderProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        ProtectionOrder protectionOrder = new ProtectionOrder();
        StringNode protectionOrderValue = new StringNode();
        protectionOrderValue.setValue("");
        protectionOrder.setProtectionOrder(protectionOrderValue);
        person.setProtectionOrder(protectionOrder);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertFalse(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsFalseWhenZeroProtectionOrderProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        ProtectionOrder protectionOrder = new ProtectionOrder();
        StringNode protectionOrderValue = new StringNode();
        protectionOrderValue.setValue("0");
        protectionOrder.setProtectionOrder(protectionOrderValue);
        person.setProtectionOrder(protectionOrder);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertFalse(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsTrueWhenOneProtectionOrderProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        ProtectionOrder protectionOrder = new ProtectionOrder();
        StringNode protectionOrderValue = new StringNode();
        protectionOrderValue.setValue("1");
        protectionOrder.setProtectionOrder(protectionOrderValue);
        person.setProtectionOrder(protectionOrder);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertTrue(resultPerson.isProtectionOrder());
    }

    @Test
    public void protectionOrderReturnsNotFalseWhenUnexpectedProtectionOrderProtectionOrder() throws Exception {
        VTJResponseMessage response = new VTJResponseMessage();
        Person person = getTestPersonWithHetu("010101-1010");
        ProtectionOrder protectionOrder = new ProtectionOrder();
        StringNode protectionOrderValue = new StringNode();
        protectionOrderValue.setValue("0100");
        protectionOrder.setProtectionOrder(protectionOrderValue);
        person.setProtectionOrder(protectionOrder);
        response.setPerson(person);
        fi.vm.kapa.identification.vtj.model.Person resultPerson = vtjService.fromSoapMessage(response);
        assertTrue("protection order should be true when value is not zero", resultPerson.isProtectionOrder());
    }

    private Person getTestPersonWithHetu(String hetu) {
        Person person = new Person();
        Hetu hetuInstance = new Hetu();
        hetuInstance.setHetu(hetu);
        hetuInstance.setValidityCode("1");
        person.setHetu(hetuInstance);
        return person;
    }

}