package artemiygreg.ubanktest.mvp.view;

import android.support.annotation.NonNull;

import java.util.List;

import artemiygreg.ubanktest.model.Data;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public interface MainView {

    void showList(@NonNull List<Data> data);

    void showDialog();

    void hideImage();

    void showImage();

    boolean dialogIsShowing();
}
