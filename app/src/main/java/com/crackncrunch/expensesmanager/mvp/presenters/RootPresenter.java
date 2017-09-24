package com.crackncrunch.expensesmanager.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.crackncrunch.expensesmanager.App;
import com.crackncrunch.expensesmanager.Screens;
import com.crackncrunch.expensesmanager.mvp.views.RootView;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class RootPresenter extends MvpPresenter<RootView> {
    @Inject
    Router mRouter;

    public RootPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mRouter.newRootScreen(Screens.HOME_SCREEN);
    }
}
