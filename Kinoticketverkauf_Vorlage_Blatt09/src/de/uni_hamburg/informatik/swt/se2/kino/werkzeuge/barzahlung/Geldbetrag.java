package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

public class Geldbetrag
{
    /**
     * Prueft ob der Wert Centbetraege enthaelt die nicht dem Schema "CC" entsprechen / nicht zweistellig sind.
     *
     * @param wert Eingabewert der geprueft werden soll.
     *
     * @return true: Format des Centbetrages ungueltig, false: Bedingung erfüllt
     *
     * @require wert != null
     */
    private static boolean containsInvalidCentValues(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        String centString = "";
        if (wert.contains(","))
        {
            centString = wert.substring(wert.indexOf(",") + 1);
            if (centString.length() != 2)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Prueft ob der Wert ungueltige Zeichen enthaelt (Irgendetwas ausser Zahlen und Kommas).
     *
     * @param wert Eingabewert der geprueft werden soll.
     *
     * @return true: enthaelt ungueltige Zeichen, false: Bedingung erfüllt
     *
     * @require wert != null
     */
    private static boolean containsInvalidSymbols(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        for (char c : wert.toCharArray())
        {
            if (!Character.isDigit(c) && c != ',')
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Prueft ob der Wert mehr als ein Komma enthaelt.
     *
     * @param wert Eingabewert der geprueft werden soll.
     *
     * @return true: zu viele Kommas, false: Bedingung erfüllt
     *
     * @require wert != null
     */
    private static boolean containsMultipleCommas(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        int counter = 0;
        for (char c : wert.toCharArray())
        {
            if (c == ',')
            {
                counter++;
                if (counter == 2)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prueft ob der Wert zu viele Zeichen hat.
     *
     * @param wert Eingabewert der geprueft werden soll.
     *
     * @return true: Eingabewert hat zu viele Zeichen, false: Bedingung erfüllt
     */
    private static boolean containsTooManyNumbers(String wert)
    {
        return wert.length() > 9;
    }

    /**
     * Konstruktor zur erzeugung eines neuen Geldbetrages.
     *
     * @param euroBetrag des Betrages.
     * @param centBetrag des Betrages.
     *
     * @require euroBetrag != null
     * @require centBetrag != null
     * @require euroBetrag * 100 + centBetrag <= 100000
     */
    public static Geldbetrag createGeldbetrag(Integer euroBetrag, Integer centBetrag)
    {
        assert euroBetrag != null : "Vorbedingung verletzt: euroBetrag != null";
        assert centBetrag != null : "Vorbedingung verletzt: centBetrag != null";
        assert euroBetrag * 100
                + centBetrag <= 100000 : "Vorbedingung verletzt: euroBetrag * 100 + centBetrag <= 100000";

        return new Geldbetrag(euroBetrag, centBetrag);
    }

    /**
     * Konstruktor zur erzeugung eines neuen Geldbetrages.
     *
     * @param wert der Wert der den Euro- und Centanteil des Betrages festlegt. Muss im Format: "EE,CC" vorliegen.
     *
     * @require wert != null
     * @require isValueValid(wert)
     * @require euroBetrag * 100 + centBetrag <= 100000
     */
    static Geldbetrag createGeldbetrag(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";
        assert isValueValid(wert) : "Vorbedingung verletzt: isValueValidisValueValid(wert)";

        int euroBetrag = 0;
        int centBetrag = 0;

        if (wert.contains(","))
        {
            euroBetrag = Integer.parseInt(wert.substring(0, wert.indexOf(",")));
            centBetrag = Integer.parseInt(wert.substring(wert.indexOf(",") + 1));
        }
        else
        {
            euroBetrag = Integer.parseInt(wert);
        }
        assert euroBetrag * 100
                + centBetrag <= 100000 : "Vorbedingung verletzt: euroBetrag * 100 + centBetrag <= 100000";

        return new Geldbetrag(euroBetrag, centBetrag);
    }

    /**
     * Prueft ob diverse gueltigkeits Checks Fehler melden und den Wert als ungueltig einstufen. Gueltiges Format: "EE,CC"
     *
     * @param wert Eingabewert der geprueft werden soll.
     *
     * @return true: Wert ist ungueltig, false: Bedingung erfüllt
     *
     * @require wert != null
     */
    private static boolean isValueValid(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        if (containsInvalidSymbols(wert) || containsMultipleCommas(wert) || containsInvalidCentValues(wert)
                || containsTooManyNumbers(wert))
        {
            return false;
        }
        return true;
    }

    private final int _centBetrag;

    /**
     * Konstruktor zur Erzeugung eines neuen Geldbetrages.
     *
     * @param euroBetrag des Betrages.
     * @param centBetrag des Betrages.
     *
     */
    private Geldbetrag(int euroBetrag, int centBetrag)
    {
        _centBetrag = centBetrag + euroBetrag * 100;
    }

    /**
     * Konstruktor zur erzeugung eines neuen Geldbetrages.
     *
     * @param euroBetrag des Betrages.
     * @param centBetrag des Betrages.
     *
     */
    private Geldbetrag(Integer euroBetrag, Integer centBetrag) // NO_UCD (unused code)
    {
        _centBetrag = centBetrag + euroBetrag * 100;
    }

    /**
     * Addiert den uebergebenen Geldbetrag auf diesen Geldbetrag.
     *
     * @param additionsBetrag um den der Geldbetrag erhoeht werden soll.
     *
     * @return neuer Geldbetrag.
     *
     * @require additionsBetrag != null
     */
    Geldbetrag addiere(Geldbetrag additionsBetrag) // NO_UCD (test only)
    {
        assert additionsBetrag != null : "Vorbedingung verletzt: additionsBetrag != null";

        int centBetrag = _centBetrag + additionsBetrag.getCentBetrag();
        Geldbetrag betrag = new Geldbetrag(0, centBetrag);
        return betrag;
    }

    /**
     * Prueft ob zwei Geldbetraege gleich sind / ihre Summe identisch ist.
     */
    @Override
    public boolean equals(Object geldBetrag)
    {
        return geldBetrag instanceof Geldbetrag && _centBetrag == ((Geldbetrag) geldBetrag).getCentBetrag();
    }

    /**
     * Gibt den aktuellen Centanteil des Betrages als int wieder.
     *
     * @return den aktuellen Centanteil des Betrages als int.
     */
    public int getCentBetrag()
    {
        return _centBetrag;
    }

    /**
     * Gibt den Geldbetrag als String im Format "Eurobetrag,Centbetrag" wieder.
     *
     * @return Geldbetrag als String im Format "Eurobetrag,Centbetrag".
     */
    public String gibGeldbetragDarstellung(boolean alwaysPositive)
    {
        int centBetrag = _centBetrag;
        if (alwaysPositive && _centBetrag < 0)
        {
            centBetrag *= -1;
        }

        int euroBetrag = centBetrag / 100;
        centBetrag -= euroBetrag * 100;

        StringBuffer sb = new StringBuffer();
        sb.append(euroBetrag);
        sb.append(",");
        sb.append(centBetrag);
        sb.append(" €");
        return sb.toString();
    }

    /**
     * Generiert den Hashcode.
     */
    @Override
    public int hashCode()
    {
        return _centBetrag;
    }

    /**
     * Prueft ob der Betrag negative Werte annimmt.
     *
     * @return true: Betrag ist negativ, false: Betrag ist positiv
     */
    boolean istBetragKleinerGleichNull()
    {
        return getCentBetrag() <= 0;
    }

    /**
     * Multipliziert den Geldbetrag mit dem gegebenen Faktor.
     *
     * @param faktor mit dem der Geldbetrag multipliziert werden soll.
     * @return neuer Geldbetrag.
     *
     * @require faktor > 0
     * @require faktor <= 30
     */
    public Geldbetrag multipliziere(int faktor)
    {
        assert faktor > 0 : "Vorbedingung verletzt: faktor > 0";
        assert faktor <= 30 : "Vorbedingung verletzt: faktor <= 30";

        int centBetrag = _centBetrag;
        centBetrag *= faktor;
        Geldbetrag geldbetrag = new Geldbetrag(0, centBetrag);
        return geldbetrag;
    }

    /**
     * Berechnet die Differenz zweier Betraege.
     *
     * @param betrag mit dem die Differenz berechnet werden soll.
     *
     * @return Differenz Betrag.
     *
     * @require betrag != null
     */
    Geldbetrag subtrahiere(Geldbetrag betrag)
    {
        assert betrag != null : "Vorbedingung verletzt: betrag != null";

        int centBetrag = _centBetrag - betrag.getCentBetrag();
        Geldbetrag differenz = new Geldbetrag(0, centBetrag);
        return differenz;
    }
}
