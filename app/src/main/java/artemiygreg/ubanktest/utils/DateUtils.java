package artemiygreg.ubanktest.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

@SuppressLint("SimpleDateFormat")
public class DateUtils {
    public static final String TIME_FORMAT_RU = "HH:mm";

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_RU);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
