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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Asiakasinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInfo {

    @XmlElement(name = "InfoS")
    private StringNode infoS;

    @XmlElement(name = "InfoR")
    private StringNode infoR;

    @XmlElement(name = "InfoE")
    private StringNode infoE;

    public StringNode getInfoS() {
        return infoS;
    }

    public void setInfoS(StringNode infoS) {
        this.infoS = infoS;
    }

    public StringNode getInfoR() {
        return infoR;
    }

    public void setInfoR(StringNode infoR) {
        this.infoR = infoR;
    }

    public StringNode getInfoE() {
        return infoE;
    }

    public void setInfoE(StringNode infoE) {
        this.infoE = infoE;
    }

    @Override
    public String toString() {
        return "CustomerInfo [infoS=" + infoS + ", infoR=" + infoR + ", infoE=" + infoE + "]";
    }

}
