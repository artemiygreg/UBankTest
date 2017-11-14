package artemiygreg.ubanktest.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import artemiygreg.ubanktest.R;
import artemiygreg.ubanktest.model.Data;
import artemiygreg.ubanktest.mvp.model.MainDataModel;
import artemiygreg.ubanktest.mvp.presenter.MainPresenter;
import artemiygreg.ubanktest.mvp.view.MainView;
import artemiygreg.ubanktest.utils.ViewUtils;
import artemiygreg.ubanktest.view.adapter.DataAdapter;
import artemiygreg.ubanktest.view.dialog.CustomDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class MainActivity extends AppCompatActivity implements MainView, CustomDialogFragment.OnCloseListener {

    @BindView(R.id.image_view_icon)
    ImageView imageViewIcon;

    @BindView(R.id.recycler_view_data)
    RecyclerView recyclerViewData;

    private MainPresenter presenter;
    private DataAdapter adapter;
    private CustomDialogFragment dialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        adapter = new DataAdapter(getLayoutInflater());
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewData.setAdapter(adapter);
        presenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if(presenter == null) {
            presenter = new MainPresenter(new MainDataModel());
        }
        dialogFragment = (CustomDialogFragment)getSupportFragmentManager()
                .findFragmentByTag(CustomDialogFragment.class.getName());
        if(dialogFragment == null) {
            dialogFragment = new CustomDialogFragment();
            dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Dialog);
        }
        presenter.bindView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!dialogFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(dialogFragment, CustomDialogFragment.class.getName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @OnClick(R.id.button_show_list)
    void onClickLoadData() {
        presenter.loadData();
    }

    @OnClick(R.id.button_show_dialog)
    void onClickShowDialog() {
        presenter.showDialog();
    }

    @Override
    public void showList(@NonNull List<Data> data) {
        adapter.setData(data);
        if(!ViewUtils.isVisible(recyclerViewData)) {
            ViewUtils.setVisible(recyclerViewData, true);
        }
    }

    @Override
    public void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialogFragment.show(ft, CustomDialogFragment.class.getName());
    }

    @Override
    public void hideImage() {
        if(ViewUtils.isVisible(imageViewIcon)) {
            ViewUtils.setVisible(imageViewIcon, false);
        }
    }

    @Override
    public void showImage() {
        if(!ViewUtils.isVisible(imageViewIcon)) {
            ViewUtils.setVisible(imageViewIcon, true);
        }
    }

    @Override
    public boolean dialogIsShowing() {
        return dialogFragment != null && dialogFragment.isShowing();
    }

    @Override
    public void close() {
        dialogFragment.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView(this);
    }
}
