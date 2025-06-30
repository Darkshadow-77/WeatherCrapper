package com.vandal.weathercrapper;

import android.view.View;
import android.view.ViewTreeObserver;

public class PanelAnimatorV2 {

    // Slide panel up from bottom (off-screen) to visible position
    public static void slideInFromBottom(View view, long duration) {
        view.setVisibility(View.VISIBLE);

        // Wait for layout to be done to get height
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove listener to avoid repeated calls
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int height = view.getHeight();
                view.setTranslationY(height);  // Start below screen
                view.animate()
                    .translationY(0)           // Animate to natural position
                    .setDuration(duration)
                    .start();
            }
        });
    }

    // Slide panel down to bottom and hide it at the end
    public static void slideOutToBottom(View view, long duration) {
        int height = view.getHeight();
        if (height == 0) {
            // Height not measured yet, wait for layout first
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int measuredHeight = view.getHeight();
                    animateOut(view, measuredHeight, duration);
                }
            });
        } else {
            animateOut(view, height, duration);
        }
    }

    private static void animateOut(View view, int height, long duration) {
        view.animate()
            .translationY(height)
            .setDuration(duration)
            .withEndAction(() -> view.setVisibility(View.GONE))
            .start();
    }
}