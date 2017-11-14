package artemiygreg.ubanktest.model;

import android.support.annotation.NonNull;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class Data {
    private String title;
    private String time;

    public Data() {

    }

    public Data(@NonNull String title, @NonNull String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
