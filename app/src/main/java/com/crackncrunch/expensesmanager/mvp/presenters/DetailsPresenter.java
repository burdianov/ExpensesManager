package com.crackncrunch.expensesmanager.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.crackncrunch.expensesmanager.data.dto.User;
import com.crackncrunch.expensesmanager.mvp.views.DetailsView;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    private Router mRouter;
    private User mUser;

    public DetailsPresenter(Router router, User user) {
        mRouter = router;
        mUser = user;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onBackCommandClick() {
        mRouter.exit();
    }

    public void setText() {
        getViewState().setTitle(String.valueOf(mUser.getName()));
    }
}
