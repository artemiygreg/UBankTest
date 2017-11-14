package artemiygreg.ubanktest.utils;

import android.view.View;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class ViewUtils {

    public static void setVisible(View target, boolean visible) {
        target.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static boolean isVisible(View target) {
        return target.getVisibility() == View.VISIBLE;
    }
}
