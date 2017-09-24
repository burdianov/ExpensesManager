package com.crackncrunch.expensesmanager.navigation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {AnimationType.NO_ANIM, AnimationType.SLIDE_FADE_ANIM})
public @interface AnimationType {
    int NO_ANIM = 0;
    int SLIDE_FADE_ANIM = 1;
}
