package com.crackncrunch.expensesmanager;

import android.app.Application;

import com.crackncrunch.expensesmanager.di.components.AppComponent;
import com.crackncrunch.expensesmanager.di.components.DaggerAppComponent;
import com.crackncrunch.expensesmanager.di.modules.ContextModule;

public class App extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
