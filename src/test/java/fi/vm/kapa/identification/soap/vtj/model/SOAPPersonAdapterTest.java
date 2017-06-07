package fi.vm.kapa.identification.soap.vtj.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SOAPPersonAdapterTest {

    @Test
    public void setHetuSetsHetuString() {
        Hetu hetu = createHetu("121212-9999", "1");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setHetu(hetu);
        assertEquals("121212-9999", person.getPerson().getHetu());
    }

    @Test
    public void setHetuSetsHetuValidityTrue() {
        Hetu hetu = createHetu("121212-9999", "1");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setHetu(hetu);
        assertTrue(person.getPerson().isHetuValid());
    }

    @Test
    public void setHetuSetsHetuValidityFalse() {
        Hetu hetu = createHetu("121212-9999", "0");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setHetu(hetu);
        assertFalse(person.getPerson().isHetuValid());
    }

    private Hetu createHetu(String hetuStr, String validityCode) {
        Hetu hetu = new Hetu();
        hetu.setHetu(hetuStr);
        hetu.setValidityCode(validityCode);
        return hetu;
    }

    @Test
    public void setElectronicIdentifierSetsSatuString() {
        ElectronicIdentifier satu = createSatu("12345", "0");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setElectronicIdentifier(satu);
        assertEquals("12345", person.getPerson().getSatu());
    }

    @Test
    public void setElectronicIdentifierSetsValidityFalse() {
        ElectronicIdentifier satu = createSatu("12345", "0");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setElectronicIdentifier(satu);
        assertFalse(person.getPerson().isSatuValid());
    }

    @Test
    public void setElectronicIdentifierSetsValidityTrue() {
        ElectronicIdentifier satu = createSatu("12345", "1");
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setElectronicIdentifier(satu);
        assertTrue(person.getPerson().isSatuValid());
    }

    private ElectronicIdentifier createSatu(String satuString, String validityCode) {
        ElectronicIdentifier satu = new ElectronicIdentifier();
        satu.setElectronicIdentifier(satuString);
        satu.setValidityCode(validityCode);
        return satu;
    }

    @Test
    public void personDeceasedIsFalseByDefault() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        assertFalse(person.getPerson().isDeceased());
    }

    @Test
    public void setDeceasedInfoSetsDeceasedTrue() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setDeceasedInfo(new Deceased(new StringNode("1"), new StringNode("20160101")));
        assertTrue(person.getPerson().isDeceased());
    }

    @Test
    public void personProtectionOrderDefaultIsFalse() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        assertFalse(person.getPerson().isProtectionOrder());
    }

    @Test
    public void setProtectionOrderSetsProtectionOrderTrue() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setProtectionOrder(new ProtectionOrder(new StringNode("1"), new StringNode("20180101")));
        assertTrue(person.getPerson().isProtectionOrder());
    }

    @Test
    public void SOAPPersonHandlesNamesAndEmail() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setFirstName(new CurrentFirstNames(new StringNode("Etunimi")));
        person.setLastName(new CurrentLastName(new StringNode("Sukunimi")));
        person.setNickName(new CurrentNickName(new StringNode("Kutsumanimi")));
        person.getPerson().setEmailAddress("email");
        // For some reason citizenship is a string.
        person.getPerson().setFinnishCitizenship("soumi");
        assertEquals("Etunimi", person.getPerson().getFirstNames());
        assertEquals("Sukunimi", person.getPerson().getLastName());
        assertEquals("Kutsumanimi", person.getPerson().getNickName());
        assertEquals("email", person.getPerson().getEmailAddress());
        assertEquals("soumi", person.getPerson().getFinnishCitizenship());
    }

    @Test
    public void SOAPPersonHandlesMunicipalityTest() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setMunicipality(new Municipality(
                new StringNode("municipalityNumber"),
                new StringNode("municipalityS"),
                new StringNode("municipalityR"),
                new StringNode("municipalityStartDate")));
        assertEquals("municipalityNumber", person.getPerson().getMunicipalityCode());
        assertEquals("municipalityS", person.getPerson().getMunicipalityS());
        assertEquals(null, person.getPerson().getMunicipalityR());
        person.setMunicipality(new Municipality(
                new StringNode("municipalityNumber"),
                null,
                new StringNode("municipalityR"),
                new StringNode("municipalityStartDate")));
        assertEquals("municipalityR", person.getPerson().getMunicipalityR());
    }

    @Test
    public void SOAPPersonHandlesDomesticAddressTest() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setDomesticAddress(new DomesticAddress(
                new StringNode("addressS"),
                new StringNode("addressR"),
                new StringNode("postalCode"),
                new StringNode("cityS"),
                new StringNode("cityR"),
                new StringNode("recidencyStartDate"),
                new StringNode("recidencyEndDate")));
        assertEquals("addressS", person.getPerson().getDomesticAddressS());
        assertEquals(null, person.getPerson().getDomesticAddressR());
        assertEquals("postalCode", person.getPerson().getPostalCode());
        assertEquals("cityS", person.getPerson().getCityS());
        assertEquals(null, person.getPerson().getCityR());
        person.setDomesticAddress(new DomesticAddress(
                null,
                new StringNode("addressR"),
                new StringNode("postalCode"),
                new StringNode(""),
                new StringNode("cityR"),
                new StringNode("recidencyStartDate"),
                new StringNode("recidencyEndDate")));
        assertEquals("addressR", person.getPerson().getDomesticAddressR());
        assertEquals("cityR", person.getPerson().getCityR());
    }

    @Test
    public void SOAPPersonHandlesForeignAddressTest() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setForeignAddress(
                new ForeignAddress(new StringNode("foreignStreetAddress"),
                        new StringNode("foreignLocalityAndStateS"),
                        new StringNode("foreignLocalityAndStateR"),
                        new StringNode("foreignLocalityAndStateClearText"),
                        new StringNode("stateCode"),
                        new StringNode("recidencyStartDate"),
                        new StringNode("recidencyEndDate")));
        assertEquals("foreignStreetAddress", person.getPerson().getForeignAddress());
        assertEquals("foreignLocalityAndStateS", person.getPerson().getForeignLocalityAndStateS());
        assertEquals(null, person.getPerson().getForeignLocalityAndStateR());
        assertEquals("foreignLocalityAndStateClearText", person.getPerson().getForeignLocalityAndStateClearText());
        assertEquals("stateCode", person.getPerson().getStateCode());
        person.setForeignAddress(
                new ForeignAddress(new StringNode("foreignStreetAddress"),
                        null,
                        new StringNode("foreignLocalityAndStateR"),
                        new StringNode("foreignLocalityAndStateClearText"),
                        new StringNode("stateCode"),
                        new StringNode("recidencyStartDate"),
                        new StringNode("recidencyEndDate")));
        assertEquals("foreignLocalityAndStateR", person.getPerson().getForeignLocalityAndStateR());
    }

    @Test
    public void SOAPPersonHandlesTemporaryDomesticAddressTest() {
        SOAPPersonAdapter person = new SOAPPersonAdapter();
        person.setTemporaryDomesticAddress(new TemporaryDomesticAddress(
                new StringNode("addressS"),
                new StringNode("addressR"),
                new StringNode("postalCode"),
                new StringNode("cityS"),
                new StringNode("cityR"),
                new StringNode("recidencyStartDate"),
                new StringNode("recidencyEndDate")));
        assertEquals("addressS", person.getPerson().getTemporaryDomesticAddressS());
        assertEquals(null, person.getPerson().getTemporaryDomesticAddressR());
        assertEquals("postalCode", person.getPerson().getTemporaryPostalCode());
        assertEquals("cityS", person.getPerson().getTemporaryCityS());
        assertEquals(null, person.getPerson().getTemporaryCityR());
        person.setTemporaryDomesticAddress(new TemporaryDomesticAddress(
                null,
                new StringNode("addressR"),
                new StringNode("postalCode"),
                new StringNode(""),
                new StringNode("cityR"),
                new StringNode("recidencyStartDate"),
                new StringNode("recidencyEndDate")));
        assertEquals("addressR", person.getPerson().getTemporaryDomesticAddressR());
        assertEquals("cityR", person.getPerson().getTemporaryCityR());
    }
}
