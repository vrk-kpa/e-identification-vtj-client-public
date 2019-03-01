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
package fi.vm.kapa.identification.util;

import fi.vm.kapa.identification.test.DummyPersonService;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import fi.vm.kapa.identification.vtjclient.service.DummyVTJService;
import org.junit.Test;

import static fi.vm.kapa.identification.type.Identifier.Types.HETU;
import static org.junit.Assert.assertEquals;

public class DummyDataDeviatorTest {

    @Test
    public void testDummyVtjServiceReturnsErrorWhenErrorPercent100() throws Exception {
        DummyDataDeviator deviator = new DummyDataDeviator(600 ,200, 100, 0);
        DummyVTJService service = new DummyVTJService(new DummyPersonService("010191-9630"), deviator);
        VTJResponse response = service.getVTJResponse("010191-9663", HETU, null);

        assertEquals(DummyDataDeviator.errorMsg, response.getError());
    }

    @Test
    public void testDummyDataErrorCountMatchesErrorPercent() throws Exception {
        for ( int i = 0; i < 100; i++ ) {
            assertEquals(i, getDummyDataRealErrorPercent(i));
        }
    }

    private int getDummyDataRealErrorPercent(int initialPercent) {
        DummyDataDeviator deviator = new DummyDataDeviator(0, 0, initialPercent, 0);

        int errorCount = 0;
        int size = deviator.delays.length;
        for (int i = 0; i < size; i++) {
            double delay = deviator.delays[i];
            if ( delay < 0 ) {
                errorCount++;
            }
        }

        return errorCount*100/size;
    }

}