package de.die_bartmanns.daniel.mymeal.data;

import de.die_bartmanns.daniel.mymeal.R;

public enum MealType {

    MAIN(R.string.mainmeal, true, R.color.colorPrimary, R.color.colorPrimaryDark, R.drawable.default_photo),
    SOUP(R.string.soup, true, R.color.colorSoup, R.color.colorSoupDark, R.drawable.suppe),
    SALAD(R.string.salad, true, R.color.colorSalad, R.color.colorSaladDark, R.drawable.salat),
    DESSERT(R.string.dessert, false, R.color.colorDessert, R.color.colorDessertDark, R.drawable.dessert),
    CAKE(R.string.cake, false, R.color.colorCake, R.color.colorCakeDark, R.drawable.kuchen),
    // WICHTIG: OTHER MUSS IMMER AN LETZTER POSITION SEIN
    OTHER(R.string.other, true, R.color.colorOther, R.color.colorOtherDark, R.drawable.other);

    private final int stringResId;
    private final boolean hasFilter;
    private final int colorResId;
    private final int darkColorResId;
    private final int photoResId;

    MealType(int stringResId, boolean hasFilter, int colorResId, int darkColorResId, int photoResId) {
        this.stringResId = stringResId;
        this.hasFilter = hasFilter;
        this.colorResId = colorResId;
        this.darkColorResId = darkColorResId;
        this.photoResId = photoResId;
    }

    public int getStringResId() {
        return stringResId;
    }

    public boolean hasFilter(){return hasFilter;}

    public int getColorResId(){return colorResId;}

    public int getDarkColorResId(){return darkColorResId;}

    public int getPhotoResId(){return this.photoResId;}
}
