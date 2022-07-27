package de.die_bartmanns.daniel.mymeal.SideMenu;

/**
 * Created by Daniel on 07.01.2017.
 */
public class NavItem {
    private String mTitle;
    private String mSubtitle;
    private int mIcon;

    public NavItem(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public String getmTitle(){return mTitle;}

    public String getmSubtitle(){return mSubtitle;}

    public int getmIcon(){return mIcon;}
}
