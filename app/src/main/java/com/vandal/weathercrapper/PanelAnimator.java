package com.vandal.weathercrapper;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class PanelAnimator {

    //  Fait apparaître la vue en glissant du bas vers le haut
    public static void slideInFromBottom(View view, long duration) {
        view.post(() -> {
            int height = view.getHeight();
            view.setVisibility(View.VISIBLE);
            view.clearAnimation(); // Empêche les conflits d'animations
            TranslateAnimation animation = new TranslateAnimation(
                0, 0, height, 0
            );
            animation.setDuration(duration);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        });
    }

    // Fait disparaître la vue en glissant du haut vers le bas
    public static void slideOutToBottom(View view, long duration) {
        view.post(() -> {
            int height = view.getHeight();
            view.clearAnimation();
            TranslateAnimation animation = new TranslateAnimation(
                0, 0, 0, height
            );
            animation.setDuration(duration);
            animation.setFillAfter(true);
            animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                @Override public void onAnimationStart(android.view.animation.Animation animation) {}
                @Override public void onAnimationRepeat(android.view.animation.Animation animation) {}

                @Override
                public void onAnimationEnd(android.view.animation.Animation animation) {
                    view.setVisibility(View.GONE);
                }
            });
            view.startAnimation(animation);
        });
    }
}
