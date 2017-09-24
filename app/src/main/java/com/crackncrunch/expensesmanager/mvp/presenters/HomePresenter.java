package com.crackncrunch.expensesmanager.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.crackncrunch.expensesmanager.Screens;
import com.crackncrunch.expensesmanager.data.dto.User;
import com.crackncrunch.expensesmanager.mvp.views.HomeView;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private Router mRouter;

    public HomePresenter(Router router) {
        mRouter = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void clickOnButtonSwap() {
        mRouter.navigateTo(Screens.DETAILS_SCREEN, new User("Volodea", 34));
    }
}
