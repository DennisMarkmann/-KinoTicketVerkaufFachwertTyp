package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GeldbetragTest
{
    private String _wertEins;
    private int _wertEinsEuro;
    private int _wertEinsCent;
    private String _wertZwei;
    private int _wertZweiEuro;
    private int _wertZweiCent;
    private int _faktor;

    @Before
    public void createTestData()
    {
        _wertEinsEuro = 59;
        _wertEinsCent = 36;
        _wertEins = _wertEinsEuro + "," + _wertEinsCent;

        _wertZweiEuro = 123;
        _wertZweiCent = 96;
        _wertZwei = _wertZweiEuro + "," + _wertZweiCent;

        _faktor = 5;
    }

    @Test
    public void testeAddiereGeldbetraege()
    {
        Geldbetrag grundBetrag = Geldbetrag.createGeldbetrag(_wertEins);
        Geldbetrag zuAddierenderBetrag = Geldbetrag.createGeldbetrag(_wertZwei);
        grundBetrag = grundBetrag.addiere(zuAddierenderBetrag);

        int centGesamt = _wertEinsCent + _wertZweiCent;
        int euroGesamt = _wertEinsEuro + _wertZweiEuro;
        int zuAddierendeEuro = centGesamt / 100;
        centGesamt -= zuAddierendeEuro * 100;
        euroGesamt += zuAddierendeEuro;

        assertEquals(euroGesamt, grundBetrag.getEuroBetrag());
        assertEquals(centGesamt, grundBetrag.getCentBetrag());
    }

    @Test
    public void testeBerechneDifferenz()
    {
        Geldbetrag grundBetrag = Geldbetrag.createGeldbetrag(_wertZwei);
        Geldbetrag zuSubtrahierenderBetrag = Geldbetrag.createGeldbetrag(_wertEins);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);

        int centGesamt = _wertZweiCent - _wertEinsCent;
        int euroGesamt = _wertZweiEuro - _wertEinsEuro;

        int zuSubtrahierendeEuro = 0;
        while (centGesamt < 0)
        {
            zuSubtrahierendeEuro++;
            centGesamt += 100;
        }
        euroGesamt -= zuSubtrahierendeEuro;

        assertEquals(euroGesamt, grundBetrag.getEuroBetrag());
        assertEquals(centGesamt, grundBetrag.getCentBetrag());
    }

    @Test
    public void testeGeldbetragKonstruktoren()
    {
        Geldbetrag betragEins = Geldbetrag.createGeldbetrag(_wertEins);

        Geldbetrag betragZwei = Geldbetrag.createGeldbetrag(_wertZweiEuro, _wertZweiCent);

        assertEquals(_wertEinsEuro, betragEins.getEuroBetrag());
        assertEquals(_wertEinsCent, betragEins.getCentBetrag());
        assertEquals(_wertZweiEuro, betragZwei.getEuroBetrag());
        assertEquals(_wertZweiCent, betragZwei.getCentBetrag());
    }

    @Test
    public void testeGibGeldbetragDarstellung()
    {
        Geldbetrag geldbetrag = Geldbetrag.createGeldbetrag(_wertEins);
        String stringDarstellung = geldbetrag.gibGeldbetragDarstellung(false);
        assertEquals(_wertEinsEuro + "," + _wertEinsCent, stringDarstellung);
    }

    @Test
    public void testeIstBetragNegativ()
    {
        Geldbetrag grundBetrag = Geldbetrag.createGeldbetrag(_wertEins);
        Geldbetrag zuSubtrahierenderBetrag = Geldbetrag.createGeldbetrag(_wertZwei);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);
        assertTrue(grundBetrag.istBetragNegativ());
    }

    @Test
    public void testeIstBetragNull()
    {
        Geldbetrag grundBetrag = Geldbetrag.createGeldbetrag(_wertEins);
        Geldbetrag zuSubtrahierenderBetrag = Geldbetrag.createGeldbetrag(_wertEins);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);
        assertTrue(grundBetrag.istBetragNull());
    }

    @Test
    public void testeMultipliziere()
    {
        //TODO fix
        Geldbetrag geldbetrag = Geldbetrag.createGeldbetrag(_wertEins);
        geldbetrag = geldbetrag.multipliziere(_faktor);

        int expectedEuroBetrag = _wertEinsEuro * _faktor;
        int expectedCentBetrag = _wertEinsCent * _faktor;

        int zuAddierendeEuro = expectedCentBetrag / 100;
        expectedCentBetrag -= zuAddierendeEuro * 100;
        expectedEuroBetrag += zuAddierendeEuro;

        assertEquals(expectedEuroBetrag, geldbetrag.getEuroBetrag());
        assertEquals(expectedCentBetrag, geldbetrag.getCentBetrag());
    }

    //TODO add Tests for equals, hashwert, validate
}
