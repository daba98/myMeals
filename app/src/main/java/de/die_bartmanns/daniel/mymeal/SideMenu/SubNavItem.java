package de.die_bartmanns.daniel.mymeal.SideMenu;

public class SubNavItem {

    private String mTitle;
    private int mIcon;

    public SubNavItem(String title, int icon) {
        mTitle = title;
        mIcon = icon;
    }

    public String getmTitle(){return mTitle;}

    public int getmIcon(){return mIcon;}
}
