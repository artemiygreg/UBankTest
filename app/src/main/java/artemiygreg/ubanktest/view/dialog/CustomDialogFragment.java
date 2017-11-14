package artemiygreg.ubanktest.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import artemiygreg.ubanktest.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by artem_mobile_dev on 14.11.2017.
 */

public class CustomDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_close)
    void onClickCloseDialog() {
        if(getActivity() instanceof OnCloseListener) {
            ((OnCloseListener)getActivity()).close();
        }
    }

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    public interface OnCloseListener {
        void close();
    }
}
