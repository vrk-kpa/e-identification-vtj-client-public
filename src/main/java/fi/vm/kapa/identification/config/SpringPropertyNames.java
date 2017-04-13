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

public interface SpringPropertyNames {

    String VTJ_USERNAME = "${vtj_username}"; // vtj-palvelun käyttäjätunnus
    String VTJ_PASSWORD = "${vtj_password}"; // vtj-palvelun salasana
    String VTJ_SOSONIMI = "${vtj_sosonimi}";
    
    String VTJ_CA = "${vtj_ca}";
    String VTJ_CA_VALIDITY = "${vtj_ca_validity}";
    String VTJ_CA_BLACKLISTED  = "${vtj_ca_blacklisted}";

    String SERVICE_SDSB_INSTANCE = "${service_sdsb_instance}"; // xroad instanssin id (eg. FI_DEV)
    String SERVICE_MEMBER_CLASS = "${service_member_class}"; // xroadia kutsuvan organisaation tyyppi (eg. COM, ORG, GOV)
    String SERVICE_MEMBER_CODE = "${service_member_code}"; // xroadia kutsuvan organisaation id (eg. yt-tunnus)
    String SERVICE_SUBSYSTEM_CODE = "${service_subsystem_code}"; // xroadin kautta kutsuttavan alijärjestelmän nimi (eg. DemoService)
    String SERVICE_SERVICE_CODE = "${service_service_code}"; // xroadin kautta kutsuttavan palvelun nimi (eg. getRandom)
    String SERVICE_SERVICE_VERSION = "${service_service_version}";

    String SERVICE_OBJECT_TYPE = "${service_object_type}"; // xroad-kutsun palvelutyyppi 
    String CLIENT_OBJECT_TYPE = "${client_object_type}"; // xroad-kutsun objektityyppi	

    String CLIENT_SDSB_INSTANCE = "${client_sdsb_instance}";
    String CLIENT_MEMBER_CLASS = "${client_member_class}";
    String CLIENT_MEMBER_CODE = "${client_member_code}";
    String CLIENT_SUBSYSTEM_CODE = "${client_subsystem_code}";

    String XROAD_PROTOCOL_VERSION = "${xroad_protocol_version}";
    String XROAD_ENDPOINT = "${xroad_endpoint}"; // xroad-kutsun endpoint (oma liityntäpalvelin)
    String XROAD_CONNECTIVITY_KEYSTORE_PATH = "${xroad_connectivity_keystore_path}";
    String XROAD_CONNECTIVITY_KEYSTORE_PW = "${xroad_connectivity_keystore_pw}";
    
    String XROAD_TRUSTSTORE_PATH = "${xroad_truststore_path}";
    String XROAD_TRUSTSTORE_PASSWORD = "${xroad_truststore_password}";
}
