package com.table20.remed.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by noelchew on 19/08/2016.
 */
public class AnimationUtil {
    public static void animateScaleXY(View view, int delay, long duration) {
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(delay)
                .setDuration(duration)
                .start();
    }

    public static void animateFadeIn(final View view, int delay, long duration) {
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(delay)
                .setDuration(duration)
                .setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override public void onAnimationEnd(Animator animator) {

                    }

                    @Override public void onAnimationCancel(Animator animator) {

                    }

                    @Override public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();

    }

    public static void animateFadeOut(final View view, int delay, long duration) {
        view.setAlpha(1f);
        view.animate()
                .alpha(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(delay)
                .setDuration(duration)
                .setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {

                    }

                    @Override public void onAnimationEnd(Animator animator) {
                        view.setVisibility(View.GONE);
                    }

                    @Override public void onAnimationCancel(Animator animator) {

                    }

                    @Override public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void enterCircularReveal(View v) {
        int cx = v.getMeasuredWidth() / 2;
        int cy = v.getMeasuredHeight() / 2;

        int finalRadius = Math.max(v.getWidth(), v.getHeight()) / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
        v.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void exitCircularReveal(final View v) {
        int cx = v.getMeasuredWidth() / 2;
        int cy = v.getMeasuredHeight() / 2;

        int initialRadius = v.getWidth() / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }
}
