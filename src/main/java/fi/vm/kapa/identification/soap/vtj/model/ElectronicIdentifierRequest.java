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
package fi.vm.kapa.identification.soap.vtj.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "SahkoinenAsiointitunnus")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectronicIdentifierRequest {
    
    @XmlValue
    private String electronicIdentifier;

    @XmlAttribute(name = "hakuperustePaluukoodi")
    private String returnCode;
    
    @XmlAttribute(name = "hakuperusteTekstiE")
    private String returnTextE;

    @XmlAttribute(name = "hakuperusteTekstiR")
    private String returnTextR;
    
    @XmlAttribute(name = "hakuperusteTekstiS")
    private String returnTextS;
    
    public String getElectronicIdentifier() {
        return electronicIdentifier;
    }

    public void setElectronicIdentifier(String electronicIdentifier) {
        this.electronicIdentifier = electronicIdentifier;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnTextE() {
        return returnTextE;
    }

    public void setReturnTextE(String returnTextE) {
        this.returnTextE = returnTextE;
    }

    public String getReturnTextR() {
        return returnTextR;
    }

    public void setReturnTextR(String returnTextR) {
        this.returnTextR = returnTextR;
    }

    public String getReturnTextS() {
        return returnTextS;
    }

    public void setReturnTextS(String returnTextS) {
        this.returnTextS = returnTextS;
    }

        
}
