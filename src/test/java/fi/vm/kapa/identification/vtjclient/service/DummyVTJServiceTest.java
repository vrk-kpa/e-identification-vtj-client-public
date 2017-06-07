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

import fi.vm.kapa.identification.test.DummyPersonService;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import org.junit.Before;
import org.junit.Test;

import static fi.vm.kapa.identification.type.Identifier.Types.HETU;
import static fi.vm.kapa.identification.type.Identifier.Types.SATU;
import static org.junit.Assert.assertEquals;

public class DummyVTJServiceTest {
    private DummyVTJService service;

    @Before
    public void setUp() throws Exception {
        service = new DummyVTJService(new DummyPersonService("010191-9630"), 0);
    }

    @Test
    public void getVTJResponseReturnsPersonWithGivenHetu() throws Exception {
        VTJResponse response = service.getVTJResponse("010191-9663", HETU, null);
        assertEquals("010191-9663", response.getPerson().getHetu());
    }

    @Test
    public void getVTJResponseReturnsPersonWithGivenSatu() throws Exception {
        VTJResponse response = service.getVTJResponse("999196993", SATU, null);
        assertEquals("010191-9630", response.getPerson().getHetu());
    }

}