package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

class Geldbetrag
{
    private int _euroBetrag = 0;
    private int _centBetrag = 0;

    /**
     * 
     * @param wert
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
     * 
     * @param euroBetrag
     * @param centBetrag
     * 
     * @require euroBetrag > 0
     * @require centBetrag > 0
     */
    Geldbetrag(int euroBetrag, int centBetrag)
    {
        assert euroBetrag > 0 : "Vorbedingung verletzt: euroBetrag > 0";
        assert centBetrag > 0 : "Vorbedingung verletzt: centBetrag > 0";

        _euroBetrag = euroBetrag;
        _centBetrag = centBetrag;
    }

    /**
     * 
     * @param additionsBetrag
     * @return
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
     * 
     * @param subtraktionsBetrag
     * @return
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
     * 
     * @param multiplikator
     * @return
     * 
     * @require multiplikator > 0
     */
    Geldbetrag multipliziere(int multiplikator)
    {
        assert multiplikator > 0 : "Vorbedingung verletzt: multiplikator > 0";

        _euroBetrag *= multiplikator;
        _centBetrag *= multiplikator;
        haendleCentUeberschuss();
        return this;
    }

    private void haendleCentUeberschuss()
    {
        int euroAnpassung = 0;
        while (_centBetrag < 0)
        {
            euroAnpassung--;
            _centBetrag += 100;
        }
        while (_centBetrag > 100)
        {
            euroAnpassung++;
            _centBetrag -= 100;
        }
        _euroBetrag += euroAnpassung;
    }

    /**
     * 
     * @param wert
     * @return
     * 
     * @require wert != null
     *
     */
    private boolean isValueValid(String wert)
    {
        assert wert != null : "Vorbedingung verletzt: wert != null";

        if (containsInvalidSymbols(wert) || containsMultipleCommas(wert) || containsInvalidCentValues(wert))
        {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param wert
     * @return
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
     * 
     * @param wert
     * @return
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
     * 
     * @param wert
     * @return
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

    public int getEuroBetrag()
    {
        return _euroBetrag;
    }

    public int getCentBetrag()
    {
        return _centBetrag;
    }

    String gibGeldbetragDarstellung()
    {
        return _euroBetrag + "," + _centBetrag;
    }
}
