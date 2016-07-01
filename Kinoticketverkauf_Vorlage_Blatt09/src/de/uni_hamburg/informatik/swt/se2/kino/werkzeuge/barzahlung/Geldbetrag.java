package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

class Geldbetrag
{
    private int _euroBetrag = 0;
    private int _centBetrag = 0;

    /**
     * Konstruktor zur erzeugung eines neuen Geldbetrages.
     * 
     * @param wert der Wert der den Euro- und Centanteil des Betrages festlegt. Muss im Format: "EE,CC" vorliegen.
     * 
     * @require wert != null
     * @require isValueValid(wert)
     */
    Geldbetrag(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";
        assert isValueValid(wert) : "Vorbedingung verletzt: isValueValidisValueValid(wert)";

        if (wert.contains(","))
        {
            _euroBetrag = Integer.parseInt(wert.substring(0, wert.indexOf(",")));
            _centBetrag = Integer.parseInt(wert.substring(wert.indexOf(",") + 1));
        }
        else
        {
            _centBetrag = Integer.parseInt(wert);
        }
    }

    /**
     * Konstruktor zur erzeugung eines neuen Geldbetrages.
     * 
     * @param euroBetrag des Betrages.
     * @param centBetrag des Betrages.
     * 
     * @require euroBetrag >= 0 || centBetrag >= 0
     */
    Geldbetrag(int euroBetrag, int centBetrag)
    {
        //TODO abfangen das das hier eintritt
        //        assert euroBetrag >= 0 || centBetrag >= 0 : "Vorbedingung verletzt: euroBetrag >= 0 || centBetrag >= 0";

        _euroBetrag = euroBetrag;
        _centBetrag = centBetrag;
        haendleCentUeberschuss();
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
    Geldbetrag addiere(Geldbetrag additionsBetrag)
    {
        assert additionsBetrag != null : "Vorbedingung verletzt: additionsBetrag != null";

        _euroBetrag += additionsBetrag.getEuroBetrag();
        _centBetrag += additionsBetrag.getCentBetrag();
        haendleCentUeberschuss();
        return this;
    }

    /**
     * Subtrahiert den uebergebenen Geldbetrag von diesem Geldbetrag.
     * 
     * @param subtraktionsBetrag um den der Geldbetrag verringert werden soll.
     * 
     * @return neuer Geldbetrag.
     * 
     * @require subtraktionsBetrag != null
     */
    Geldbetrag subtrahiere(Geldbetrag subtraktionsBetrag)
    {
        assert subtraktionsBetrag != null : "Vorbedingung verletzt: subtraktionsBetrag != null";

        _euroBetrag -= subtraktionsBetrag.getEuroBetrag();
        _centBetrag -= subtraktionsBetrag.getCentBetrag();
        haendleCentUeberschuss();
        return this;
    }

    /**
     * Multipliziert den Geldbetrag mit dem gegebenen Faktor.
     * 
     * @param faktor mit dem der Geldbetrag multipliziert werden soll.
     * @return neuer Geldbetrag.
     * 
     * @require faktor > 0
     */
    Geldbetrag multipliziere(int faktor)
    {
        assert faktor > 0 : "Vorbedingung verletzt: faktor > 0";

        _euroBetrag *= faktor;
        _centBetrag *= faktor;
        haendleCentUeberschuss();
        return this;
    }

    /**
     * Gleicht negative oder mehr als zweistellige Centbetraege an indem er diese in Euro umwandelt.
     * Nur intern fuer die jeweiligen Berechnungen gedacht.
     */
    private void haendleCentUeberschuss()
    {
        int euroAnpassung = 0;
        while (_centBetrag < 0)
        {
            euroAnpassung--;
            _centBetrag += 100;
        }
        while (_centBetrag >= 100)
        {
            euroAnpassung++;
            _centBetrag -= 100;
        }
        _euroBetrag += euroAnpassung;
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
    private boolean isValueValid(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        if (containsInvalidSymbols(wert) || containsMultipleCommas(wert))
        //TODO rethink
        //                || containsInvalidCentValues(wert))
        {
            return false;
        }
        return true;
    }

    /**
     * Prueft ob der Wert ungueltige Zeichen enthaelt (Irgendetwas ausser zahlen und Kommas).
     * 
     * @param wert Eingabewert der geprueft werden soll.
     * 
     * @return true: enthaelt ungueltige Zeichen, false: Bedingung erfüllt
     * 
     * @require wert != null
     */
    private boolean containsInvalidSymbols(String wert)
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
     * Prueft ob der Wert Centbetraege enthaelt die nicht dem Schema "CC" entsprechen / nicht zweistellig sind.
     * 
     * @param wert Eingabewert der geprueft werden soll.
     * 
     * @return true: Format des Centbetrages ungueltig, false: Bedingung erfüllt
     * 
     * @require wert != null
     */
    private boolean containsInvalidCentValues(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        String centString = "";
        if (wert.contains(","))
        {
            centString = wert.substring(wert.indexOf(",") + 1);
        }
        else
        {
            centString = wert;
        }
        if (centString.length() != 2)
        {
            return true;
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
    private boolean containsMultipleCommas(String wert)
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
     * Gibt den aktuellen Euroanteil des Betrages als int wieder.
     * 
     * @return den aktuellen Euroanteil des Betrages als int.
     */
    public int getEuroBetrag()
    {
        return _euroBetrag;
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
    String gibGeldbetragDarstellung(boolean alwaysPositive)
    {
        StringBuffer sb = new StringBuffer();
        if (alwaysPositive && _euroBetrag < 0)
        {
            sb.append(_euroBetrag * -1);
        }
        else
        {
            sb.append(_euroBetrag);
        }
        sb.append(",");
        sb = formatCentValueString(sb);

        return sb.toString();
    }

    /**
     * Korrigiert den Centbetrag auf eine korrekte Darstellung in Form von zwei Stellen.
     * 
     * @param _centBetrag des Betrages.
     * 
     * @return format korrigierten Cent String.
     */
    private StringBuffer formatCentValueString(StringBuffer sb)
    {
        int centBetrag = _centBetrag;
        if (centBetrag < 0)
        {
            centBetrag *= -1;
        }

        if (centBetrag < 10)
        {
            sb.append("0");
        }
        sb.append(centBetrag);
        return sb;
    }

    /**
     * Prueft ob der Betrag genau 0 Euro und 0 Cent betraegt.
     * 
     * @return true: 0 Euro und 0 Cent
     */
    boolean istBetragNull()
    {
        return getEuroBetrag() == 0 && getCentBetrag() == 0;
    }

    /**
     * Prueft ob der Betrag negative Werte annimmt.
     * 
     * @return true: Betrag ist negativ, false: Betrag ist positiv
     */
    boolean istBetragNegativ()
    {
        return getEuroBetrag() < 0 || getEuroBetrag() == 0 && getCentBetrag() < 0;
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
    Geldbetrag berechneDifferenz(Geldbetrag betrag)
    {
        assert betrag != null : "Vorbedingung verletzt: betrag != null";

        int euroBetrag = _euroBetrag - betrag.getEuroBetrag();
        int centBetrag = _centBetrag - betrag.getCentBetrag();
        Geldbetrag differenz = new Geldbetrag(euroBetrag, centBetrag);
        haendleEuroUeberschussFuerDifferenz(differenz);
        haendleCentUeberschussFuerDifferenz(differenz);
        return differenz;

    }

    /**
     * Gleicht negative Eurobetraege an indem er diese in Cent umwandelt.
     * Nur fuer Differenz Berechnung gedacht.
     */
    private Geldbetrag haendleEuroUeberschussFuerDifferenz(Geldbetrag betrag)
    {
        //TODO bullshit solution as well?
        int centAnpassung = 0;
        while (betrag.getEuroBetrag() < 0)
        {
            centAnpassung = centAnpassung - 100;
            betrag.setEuroBetrag(betrag.getEuroBetrag() + 1);
        }
        betrag.setCentBetrag(betrag.getCentBetrag() + centAnpassung);
        return betrag;
    }

    public void setEuroBetrag(int _euroBetrag)
    {
        this._euroBetrag = _euroBetrag;
    }

    public void setCentBetrag(int _centBetrag)
    {
        this._centBetrag = _centBetrag;
    }

    /**
     * Gleicht negative oder mehr als zweistellige Centbetraege an indem er diese in Euro umwandelt.
     * Nur fuer Differenz Berechnung gedacht.
     */
    private Geldbetrag haendleCentUeberschussFuerDifferenz(Geldbetrag betrag)
    {
        //TODO bullshit solution (duplicate code)
        int euroAnpassung = 0;
        while (betrag.getCentBetrag() < 0)
        {
            if (betrag.getEuroBetrag() == 0)
            {
                break;
            }
            euroAnpassung--;
            betrag.setCentBetrag(betrag.getCentBetrag() + 100);
        }
        while (betrag.getCentBetrag() >= 100)
        {
            euroAnpassung++;
            betrag.setCentBetrag(betrag.getCentBetrag() - 100);
        }
        betrag.setEuroBetrag(betrag.getEuroBetrag() + euroAnpassung);
        return betrag;
    }
}
