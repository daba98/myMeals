package de.die_bartmanns.daniel.mymeal.data;

import android.content.Context;

import de.die_bartmanns.daniel.mymeal.R;

public enum Unit {
    ESSLOEFFEL(R.string.soupspoon), TEELOEFFEL(R.string.teaspoon), GRAMM(R.string.gramm), KILO(R.string.kilo), MILLILITER(R.string.milliliter), LITER(R.string.liter), PRISE(R.string.pinch),
    STUECK(R.string.piece), PACKUNG(R.string.pack), SCHEIBE(R.string.scheibe), BUND(R.string.bund), TASSE(R.string.tasse), DOSE(R.string.dose);

    private final int stringResId;

    Unit(int stringResId) {
        this.stringResId = stringResId;
    }

    public int getStringResId() {
        return stringResId;
    }

    public static Unit getUnit(Context context, String s) {
        if (s.equals(context.getString(R.string.soupspoon))) return ESSLOEFFEL;
        if (s.equals(context.getString(R.string.teaspoon))) return TEELOEFFEL;
        if (s.equals(context.getString(R.string.gramm))) return GRAMM;
        if (s.equals(context.getString(R.string.kilo))) return KILO;
        if (s.equals(context.getString(R.string.milliliter))) return MILLILITER;
        if (s.equals(context.getString(R.string.liter))) return LITER;
        if (s.equals(context.getString(R.string.pinch))) return PRISE;
        if (s.equals(context.getString(R.string.piece))) return STUECK;
        if (s.equals(context.getString(R.string.pack))) return PACKUNG;
        if (s.equals(context.getString(R.string.scheibe))) return SCHEIBE;
        if (s.equals(context.getString(R.string.bund))) return BUND;
        if (s.equals(context.getString(R.string.tasse))) return TASSE;
        if (s.equals(context.getString(R.string.dose))) return DOSE;
        else return null;
    }
}
