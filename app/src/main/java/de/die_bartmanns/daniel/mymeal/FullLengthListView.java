package de.die_bartmanns.daniel.mymeal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import static android.view.View.MEASURED_SIZE_MASK;

public class FullLengthListView extends ListView {

    boolean expanded = true;

    public FullLengthListView(Context context, AttributeSet attrs,
                              int defaultStyle) {
        super(context, attrs, defaultStyle);
    }

    public FullLengthListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = View.MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                    View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            android.view.ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
