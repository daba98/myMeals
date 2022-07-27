package de.die_bartmanns.daniel.mymeal.data;

import android.content.Context;

import de.die_bartmanns.daniel.mymeal.R;

public enum Difficulty {
    Easy(R.string.easy), Medium(R.string.medium), Hard(R.string.hard);

    private final int stringResId;

    Difficulty(int stringResId) {
        this.stringResId = stringResId;
    }

    public int getStringResId() {
        return stringResId;
    }

    public static Difficulty getDiff(Context context, String diff) {
        if (diff.equals(context.getString(R.string.easy))) return Easy;
        if (diff.equals(context.getString(R.string.medium))) return Medium;
        if (diff.equals(context.getString(R.string.hard))) return Hard;
        else return null;
    }

    public static Difficulty next(Difficulty diff){
        if(diff == Easy)
            return Medium;
        else if(diff == Medium)
            return Hard;
        else
            return Easy;
    }

    public static Difficulty previous(Difficulty diff){
        if(diff == Easy)
            return Hard;
        else if(diff == Medium)
            return Easy;
        else
            return Medium;
    }
}
