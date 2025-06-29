
package com.vandal.weathercrapper;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class PanelAnimator {

    public static void slideInFromBottom(View view, long duration) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
            0, 0, view.getHeight(), 0 // From bottom to current Y
        );
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    public static void slideOutToBottom(View view, long duration) {
        TranslateAnimation animation = new TranslateAnimation(
            0, 0, 0, view.getHeight() // From current Y to bottom
        );
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override public void onAnimationEnd(android.view.animation.Animation animation) {
                view.setVisibility(View.GONE);
            }
            @Override public void onAnimationStart(android.view.animation.Animation animation) {}
            @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}
        });
    }
}