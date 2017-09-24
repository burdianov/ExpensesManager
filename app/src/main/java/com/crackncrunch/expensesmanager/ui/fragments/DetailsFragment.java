package com.crackncrunch.expensesmanager.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.crackncrunch.expensesmanager.App;
import com.crackncrunch.expensesmanager.R;
import com.crackncrunch.expensesmanager.data.dto.User;
import com.crackncrunch.expensesmanager.mvp.presenters.DetailsPresenter;
import com.crackncrunch.expensesmanager.mvp.views.DetailsView;
import com.crackncrunch.expensesmanager.ui.common.BackButtonListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class DetailsFragment extends MvpAppCompatFragment implements
        DetailsView, BackButtonListener {

    private static final String KEY_USER = "userKey";

    @BindView(R.id.text)
    TextView mText;

    @Inject
    Router mRouter;

    @InjectPresenter
    DetailsPresenter mPresenter;

    @ProvidePresenter
    public DetailsPresenter createPresenter() {
        return new DetailsPresenter(mRouter, (User) getArguments()
                .getSerializable(KEY_USER));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.setText();
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void setTitle(String title) {
        mText.setText(title);
    }

    public static DetailsFragment getNewInstance(User user) {
        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackCommandClick();
        return true;
    }
}
