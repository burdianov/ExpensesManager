package com.crackncrunch.expensesmanager.di.components;

import android.content.Context;

import com.crackncrunch.expensesmanager.di.modules.ContextModule;
import com.crackncrunch.expensesmanager.mvp.presenters.RootPresenter;
import com.crackncrunch.expensesmanager.ui.activities.RootActivity;
import com.crackncrunch.expensesmanager.di.modules.NavigationModule;
import com.crackncrunch.expensesmanager.ui.fragments.DetailsFragment;
import com.crackncrunch.expensesmanager.ui.fragments.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NavigationModule.class, ContextModule.class})
public interface AppComponent {
    Context getContext();

    void inject(RootActivity activity);
    void inject(RootPresenter presenter);
    void inject(HomeFragment fragment);
    void inject(DetailsFragment fragment);
}
