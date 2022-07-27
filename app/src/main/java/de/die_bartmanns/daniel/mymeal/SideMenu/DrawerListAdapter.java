package de.die_bartmanns.daniel.mymeal.SideMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.die_bartmanns.daniel.mymeal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 07.01.2017.
 */
public class DrawerListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<NavItem> mNavItems;
    private Map<NavItem, List<SubNavItem>> subItemMap;

    public DrawerListAdapter(Context context, ArrayList<NavItem> navItems, Map<NavItem, List<SubNavItem>> subItemMap) {
        mContext = context;
        mNavItems = navItems;
        this.subItemMap = subItemMap;
    }

    @Override
    public int getGroupCount() {
        return mNavItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subItemMap.get(mNavItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mNavItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subItemMap.get(mNavItems.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        NavItem navItem = (NavItem) getGroup(groupPosition);
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(navItem.getmTitle() );
        subtitleView.setText(navItem.getmSubtitle() );
        iconView.setImageResource(navItem.getmIcon());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubNavItem subNavItem = (SubNavItem) getChild(groupPosition, childPosition);
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_subitem, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(subNavItem.getmTitle() );
        iconView.setImageResource(subNavItem.getmIcon());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
