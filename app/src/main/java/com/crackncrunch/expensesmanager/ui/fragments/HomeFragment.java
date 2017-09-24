package com.crackncrunch.expensesmanager.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.crackncrunch.expensesmanager.App;
import com.crackncrunch.expensesmanager.R;
import com.crackncrunch.expensesmanager.mvp.presenters.HomePresenter;
import com.crackncrunch.expensesmanager.mvp.views.HomeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    private static final String SAMPLE_DATA = "sample_data";

    @BindView(R.id.buttonSwap)
    Button mButtonSwap;

    @Inject
    Router router;

    @InjectPresenter
    HomePresenter mPresenter;

    @ProvidePresenter
    public HomePresenter createPresenter() {
        return new HomePresenter(router);
    }

    public static HomeFragment getNewInstance(int number) {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putInt(SAMPLE_DATA, number);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void setTitle(String title) {

    }

    //region ==================== Click events ===================

    @OnClick(R.id.buttonSwap)
    public void clickOnButtonSwap() {
        mPresenter.clickOnButtonSwap();
    }

    //endregion
}
