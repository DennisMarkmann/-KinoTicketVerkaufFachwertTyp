package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

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
        //Standardfall
        Geldbetrag grundBetrag = Geldbetrag.create(_wertEins);
        Geldbetrag zuAddierenderBetrag = Geldbetrag.create(_wertZwei);
        grundBetrag = grundBetrag.addiere(zuAddierenderBetrag);

        int centGesamt = _wertEinsCent + _wertZweiCent;
        int euroGesamt = _wertEinsEuro + _wertZweiEuro;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, grundBetrag.getCentBetrag());

        //Extrem: Einzeln knapp unter Max, zusammen drueber
        grundBetrag = Geldbetrag.create(999, 10);
        zuAddierenderBetrag = Geldbetrag.create(800, 100);
        grundBetrag = grundBetrag.addiere(zuAddierenderBetrag);

        centGesamt = 10 + 100;
        euroGesamt = 999 + 800;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, grundBetrag.getCentBetrag());
    }

    @Test
    public void testeBerechneDifferenz()
    {
        //Standardfall
        Geldbetrag grundBetrag = Geldbetrag.create(_wertZwei);
        Geldbetrag zuSubtrahierenderBetrag = Geldbetrag.create(_wertEins);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);

        int centGesamt = _wertZweiCent - _wertEinsCent;
        int euroGesamt = _wertZweiEuro - _wertEinsEuro;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, grundBetrag.getCentBetrag());

        //Extrem: Hoher negativer Wert
        grundBetrag = Geldbetrag.create(1, 10);
        zuSubtrahierenderBetrag = Geldbetrag.create(998, 100);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);

        centGesamt = 10 - 100;
        euroGesamt = 1 - 998;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, grundBetrag.getCentBetrag());
    }

    @Test
    public void testeGeldbetragKonstruktoren()
    {
        Geldbetrag betragEins = Geldbetrag.create(_wertEins);
        Geldbetrag betragZwei = Geldbetrag.create(_wertZweiEuro, _wertZweiCent);

        assertEquals(_wertEinsCent + _wertEinsEuro * 100, betragEins.getCentBetrag());
        assertEquals(_wertZweiCent + _wertZweiEuro * 100, betragZwei.getCentBetrag());
    }

    @Test
    public void testeGibGeldbetragDarstellung()
    {
        Geldbetrag geldbetrag = Geldbetrag.create(_wertEins);
        String stringDarstellung = geldbetrag.gibGeldbetragDarstellung(false);
        assertEquals(_wertEinsEuro + "," + _wertEinsCent + " €", stringDarstellung);
    }

    @Test
    public void testeIstBetragKleinerGleichNull()
    {
        //negativ
        Geldbetrag grundBetrag = Geldbetrag.create(_wertEins);
        Geldbetrag zuSubtrahierenderBetrag = Geldbetrag.create(_wertZwei);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);
        assertTrue(grundBetrag.istBetragKleinerGleichNull());

        //null
        grundBetrag = Geldbetrag.create(_wertEins);
        zuSubtrahierenderBetrag = Geldbetrag.create(_wertEins);
        grundBetrag = grundBetrag.subtrahiere(zuSubtrahierenderBetrag);
        assertTrue(grundBetrag.istBetragKleinerGleichNull());
    }

    @Test
    public void testeMultipliziere()
    {
        //Standardfall
        Geldbetrag geldbetrag = Geldbetrag.create(_wertEins);
        geldbetrag = geldbetrag.multipliziere(_faktor);

        int euroGesamt = _wertEinsEuro * _faktor;
        int centGesamt = _wertEinsCent * _faktor;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, geldbetrag.getCentBetrag());

        //Extrem: Multiplikation eines hohen Betrages mit dem maximalen Faktor
        geldbetrag = Geldbetrag.create(1000, 0);
        geldbetrag = geldbetrag.multipliziere(30);

        euroGesamt = 1000 * 30;
        centGesamt = 0 * 30;
        centGesamt += euroGesamt * 100;

        assertEquals(centGesamt, geldbetrag.getCentBetrag());
    }

    @Test
    public void testEquals()
    {
        Geldbetrag geldbetrag = Geldbetrag.create(_wertEins);
        Geldbetrag geldbetrag2 = Geldbetrag.create(_wertEins);
        assertTrue(geldbetrag.equals(geldbetrag2));
    }

}
