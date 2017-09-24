package com.crackncrunch.expensesmanager.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.crackncrunch.expensesmanager.R;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

import static com.crackncrunch.expensesmanager.navigation.AnimationType.NO_ANIM;
import static com.crackncrunch.expensesmanager.navigation.AnimationType.SLIDE_FADE_ANIM;

public abstract class AnimFragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;

    private int animInRight;
    private int animOutLeft;
    private int animInLeft;
    private int animOutRight;

    public AnimFragmentNavigator(FragmentManager fragmentManager,
                                 int containerId, @AnimationType int animType) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        setAnimationTransaction(animType);
    }

    public void setAnimationTransaction(int animType) {
        switch (animType) {
            case NO_ANIM:
                animInRight = NO_ANIM;
                animOutLeft = NO_ANIM;
                animInLeft = NO_ANIM;
                animOutRight = NO_ANIM;
                break;
            case SLIDE_FADE_ANIM:
                animInRight = R.anim.slide_in_right;
                animOutLeft = R.anim.slide_out_left;
                animInLeft = R.anim.slide_in_left;
                animOutRight = R.anim.slide_out_right;
                break;
        }
    }

    @Override
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            Forward forward = (Forward) command;
            Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
            if (fragment == null) {
                unknownScreen(command);
                return;
            }
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(animInRight, animOutLeft,
                            animInLeft, animOutRight)
                    .replace(containerId, fragment)
                    .addToBackStack(forward.getScreenKey())
                    .commit();
        } else if (command instanceof Back) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            } else {
                exit();
            }
        } else if (command instanceof Replace) {
            Replace replace = (Replace) command;
            Fragment fragment = createFragment(replace.getScreenKey(), replace.getTransitionData());
            if (fragment == null) {
                unknownScreen(command);
                return;
            }
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(animInRight, animOutLeft,
                                animInLeft, animOutRight)
                        .replace(containerId, fragment)
                        .addToBackStack(replace.getScreenKey())
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(animInRight, animOutLeft,
                                animInLeft, animOutRight)
                        .replace(containerId, fragment)
                        .commit();
            }
        } else if (command instanceof BackTo) {
            String key = ((BackTo) command).getScreenKey();

            if (key == null) {
                backToRoot();
            } else {
                boolean hasScreen = false;
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    if (key.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                        fragmentManager.popBackStackImmediate(key, 0);
                        hasScreen = true;
                        break;
                    }
                }
                if (!hasScreen) {
                    backToNonExistent();
                }
            }
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    private void backToRoot() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        fragmentManager.executePendingTransactions();
    }

    protected abstract Fragment createFragment(String screenKey, Object data);

    protected abstract void showSystemMessage(String message);

    protected abstract void exit();

    protected void backToNonExistent() {
        backToRoot();
    }

    protected void unknownScreen(Command command) {
        throw new RuntimeException("Cannot create a screen for passed screenKey.");
    }
}