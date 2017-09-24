package com.crackncrunch.expensesmanager.ui.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.crackncrunch.expensesmanager.App;
import com.crackncrunch.expensesmanager.R;
import com.crackncrunch.expensesmanager.Screens;
import com.crackncrunch.expensesmanager.data.dto.User;
import com.crackncrunch.expensesmanager.mvp.presenters.RootPresenter;
import com.crackncrunch.expensesmanager.mvp.views.RootView;
import com.crackncrunch.expensesmanager.navigation.AnimFragmentNavigator;
import com.crackncrunch.expensesmanager.ui.common.BackButtonListener;
import com.crackncrunch.expensesmanager.ui.fragments.DetailsFragment;
import com.crackncrunch.expensesmanager.ui.fragments.HomeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

import static com.crackncrunch.expensesmanager.navigation.AnimationType.SLIDE_FADE_ANIM;

public class RootActivity extends MvpAppCompatActivity implements RootView {

    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorContainer;
    @BindView(R.id.main_container)
    FrameLayout mRootFrame;

    private AlertDialog.Builder mExitDialog;
    private ActionBar mActionBar;

    @InjectPresenter
    RootPresenter mRootPresenter;

    @Inject
    NavigatorHolder mNavigatorHolder;

    private Navigator mNavigator = new AnimFragmentNavigator
            (getSupportFragmentManager(), R.id.main_container, SLIDE_FADE_ANIM) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case Screens.HOME_SCREEN:
                    return new HomeFragment();
                case Screens.DETAILS_SCREEN:
                    return DetailsFragment.getNewInstance((User) data);
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(RootActivity.this, message, Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected void exit() {
            finish();
        }

        @Override
        public void applyCommand(Command command) {
            super.applyCommand(command);
        }
    };

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id
                .main_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            mExitDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        App.getAppComponent().inject(this);

        initToolbar();
        initExitDialog();

        if (savedInstanceState == null) {
            mNavigator.applyCommand(new Replace(Screens.HOME_SCREEN, 1));
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
    }

    private void initExitDialog() {
        mExitDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.close_app)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes,
                        (dialog, which) -> finish())
                .setNegativeButton(R.string.no,
                        (dialog, which) -> {
                        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNavigatorHolder.removeNavigator();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mNavigatorHolder.setNavigator(mNavigator);
    }
}
