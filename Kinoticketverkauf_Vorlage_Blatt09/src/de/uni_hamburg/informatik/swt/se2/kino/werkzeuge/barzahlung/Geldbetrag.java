package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

class Geldbetrag
{
    private int _euroBetrag = 0;
    private int _centBetrag = 0;

    Geldbetrag(String wert)
    {
        if (!validate(wert))
        {
            return;
            //TODO ungültig
        }
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

    Geldbetrag(int euroBetrag, int centBetrag)
    {
        _euroBetrag = euroBetrag;
        _centBetrag = centBetrag;
    }

    Geldbetrag addiere(Geldbetrag additionsBetrag)
    {
        _euroBetrag += additionsBetrag.getEuroBetrag();
        _centBetrag += additionsBetrag.getCentBetrag();
        haendleCentUeberschuss();
        return this;
    }

    Geldbetrag subtrahiere(Geldbetrag subtraktionsBetrag)
    {
        _euroBetrag -= subtraktionsBetrag.getEuroBetrag();
        _centBetrag -= subtraktionsBetrag.getCentBetrag();
        haendleCentUeberschuss();
        return this;
    }

    Geldbetrag multipliziere(int multiplikator)
    {
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

    private boolean validate(String wert)
    {
        //TODO check more stupid stuff
        //Symbols / Centvalue > 100 / Centvalue only 1 long
        int counter = 0;
        for (char c : wert.toCharArray())
        {
            if (c == ',')
            {
                counter++;
                if (counter == 2)
                {
                    return false;
                }
            }
        }
        return true;
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
