package artemiygreg.ubanktest.mvp.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import artemiygreg.ubanktest.model.Data;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public interface MainView {

    void showList(@NonNull ArrayList<Data> data);

    void showDialog();

    void hideImage();

    @Nullable
    ArrayList<Data> getDataFromAdapter();

    void showImage();

    boolean dialogIsShowing();
}
