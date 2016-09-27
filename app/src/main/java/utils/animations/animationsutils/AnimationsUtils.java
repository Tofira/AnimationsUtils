package utils.animations.animationsutils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;

/**
 * Created by Mickael on 27/09/2016.
 */

public class AnimationsUtils
{
    private static final String TAG = AnimationsUtils.class.getSimpleName();

    public static final int SLIDING_ANIMATION_OUT_LEFT = 1;
    public static final int SLIDING_ANIMATION_OUT_RIGHT = 2;
    public static final int SLIDING_ANIMATION_OUT_UP = 3;
    public static final int SLIDING_ANIMATION_OUT_DOWN = 4;

    public static final int SLIDING_ANIMATION_IN_LEFT = 5;
    public static final int SLIDING_ANIMATION_IN_RIGHT = 6;
    public static final int SLIDING_ANIMATION_IN_UP = 7;
    public static final int SLIDING_ANIMATION_IN_DOWN = 8;

    public static final int FADE_ANIMATION_IN = 9;
    public static final int FADE_ANIMATION_OUT = 10;

    public static final int SCALE_ANIMATION_IN = 11;
    public static final int SCALE_ANIMATION_OUT = 12;

    private int animationDuration = 500;
    private View viewToAnimate;
    private int slidingDirection = 0;
    private String propertyName;
    private float startPos = -1f, endPos = -1f;
    private boolean withFadeAnimation = false;
    private Interpolator interpolator;

    private Animator.AnimatorListener nativeListener;
    private AnimationFinishedListener customListener;
    public interface AnimationFinishedListener{
        void onAnimationEnd();
    }

    public AnimationsUtils(View viewToAnimate, int slidingDirection)
    {
        this.viewToAnimate = viewToAnimate;
        this.slidingDirection = slidingDirection;

        setProperties();

    }

    private void setProperties()
    {
        switch (slidingDirection)
        {
            case SLIDING_ANIMATION_OUT_LEFT:
            {
                propertyName = "X";

                endPos =  -(viewToAnimate.getX() + viewToAnimate.getWidth());
                startPos = 0;
                break;
            }
            case SLIDING_ANIMATION_OUT_RIGHT:
            {
                propertyName = "X";

                endPos = getScreenDimen(viewToAnimate.getContext(),GET_SCREEN_WIDTH) - viewToAnimate.getX();
                startPos = 0;
                break;
            }
            case SLIDING_ANIMATION_IN_LEFT:
            {
                propertyName = "X";

                endPos =  0;
                startPos = getScreenDimen(viewToAnimate.getContext(),GET_SCREEN_WIDTH) - viewToAnimate.getX();
                break;
            }
            case SLIDING_ANIMATION_IN_RIGHT:
            {
                propertyName = "X";

                endPos =  0;
                startPos = -(viewToAnimate.getX() + viewToAnimate.getWidth());
                break;
            }

            case SLIDING_ANIMATION_IN_UP:
            {
                propertyName = "Y";

                endPos =  0;
                startPos = getScreenDimen(viewToAnimate.getContext(),GET_SCREEN_HEIGHT) - (viewToAnimate.getY() + viewToAnimate.getHeight());
                break;
            }
            case SLIDING_ANIMATION_IN_DOWN:
            {
                propertyName = "Y";

                endPos =  0;
                startPos = - (viewToAnimate.getY() + viewToAnimate.getHeight());
                break;
            }

            case SLIDING_ANIMATION_OUT_UP:
            {
                propertyName = "Y";

                endPos =  - (viewToAnimate.getY() + viewToAnimate.getHeight());
                startPos =  0;
                break;
            }

            case SLIDING_ANIMATION_OUT_DOWN:
            {
                propertyName = "Y";

                endPos =  getScreenDimen(viewToAnimate.getContext(),GET_SCREEN_HEIGHT) - (viewToAnimate.getY() + viewToAnimate.getHeight());
                startPos =  0;
                break;
            }

            case FADE_ANIMATION_IN:
            {
                propertyName = "alpha";

                endPos =  1f;
                startPos = 0f;
                break;
            }
            case FADE_ANIMATION_OUT:
            {
                propertyName = "alpha";

                endPos =  0f;
                startPos = 1f;
                break;
            }

            case SCALE_ANIMATION_IN:
            {
                propertyName = "scale";

                endPos =  1f;
                startPos = 0f;
                break;
            }
            case SCALE_ANIMATION_OUT:
            {
                propertyName = "scale";

                endPos =  0f;
                startPos = 1f;
                break;
            }
        }

        if(isSlideAnimation())
            propertyName = "translation" + propertyName;
    }

    public void startAnimation()
    {
        if(!checkContract()) return;

        viewToAnimate.setVisibility(View.VISIBLE);

        if(isScaleAnimation())
        {
            startScaleYAnimation();
        }

        ObjectAnimator anim = ObjectAnimator.ofFloat(viewToAnimate, propertyName+"X", startPos,endPos);
        if(nativeListener != null)
            anim.addListener(nativeListener);
        if(interpolator != null)
            anim.setInterpolator(interpolator);
        if(customListener != null)
            setCustomListener(anim);
        anim.setDuration(animationDuration);


        if(withFadeAnimation && !isFadeAnimation())
        {
            new AnimationsUtils(viewToAnimate, isSlideInAnim() ? FADE_ANIMATION_IN : FADE_ANIMATION_OUT)
                    .setAnimationDuration(animationDuration)
                    .startAnimation();
        }

        anim.start();
    }

    private void setCustomListener(ObjectAnimator anim)
    {
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                customListener.onAnimationEnd();
            }

        });
    }

    private void startScaleYAnimation()
    {
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(viewToAnimate, propertyName+"Y", startPos,endPos);
        animScaleY.setInterpolator(interpolator);
        animScaleY.setDuration(animationDuration);
        animScaleY.start();
    }

    private boolean checkContract()
    {
        if(viewToAnimate == null)
        {
            Log.e(TAG,"View to animate is NULL - aborting");
            return false;
        }
        if(startPos == -1 || endPos == -1)
        {
            Log.e(TAG,"Invalid animation type - aborting");
            return false;
        }

        if(nativeListener != null && customListener == null)
        {
            Log.e(TAG,"You cannot set both the custom and native listeners - aborting");
            return false;
        }

        return true;
    }

    private boolean isFadeAnimation()
    {
        return slidingDirection == FADE_ANIMATION_IN || slidingDirection == FADE_ANIMATION_OUT;
    }

    private boolean isSlideAnimation()
    {
        return !isFadeAnimation() && !isScaleAnimation();
    }

    private boolean isSlideInAnim()
    {
        return slidingDirection == SLIDING_ANIMATION_IN_RIGHT ||
                slidingDirection == SLIDING_ANIMATION_IN_LEFT ||
                slidingDirection == SLIDING_ANIMATION_IN_UP ||
                slidingDirection == SLIDING_ANIMATION_IN_DOWN;
    }

    private boolean isScaleAnimation()
    {
        return slidingDirection == SCALE_ANIMATION_IN || slidingDirection == SCALE_ANIMATION_OUT;
    }

    private boolean isSlideOutAnim()
    {
        return !isSlideInAnim();
    }

    public AnimationsUtils setWithFadeAnimation(boolean withFadeAnimation) {
        this.withFadeAnimation = withFadeAnimation;
        return this;
    }


    public AnimationsUtils setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    /**
     * Default animation duration - 500 milliseconds.
     * @param animationDuration
     * @return
     */
    public AnimationsUtils setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    public AnimationsUtils setNativeListener(Animator.AnimatorListener listener) {
        this.nativeListener = listener;
        return this;
    }

    public AnimationsUtils setListener(AnimationFinishedListener listener) {
        this.customListener = listener;
        return this;
    }

    public static void animateImageViewDrawables(ImageView imageView, Drawable fromDrawable, Drawable toDrawable, boolean withCrossfade, int duration)
    {
        Drawable[] framesAddPerson = {fromDrawable, toDrawable};
        TransitionDrawable transitionDrawableAddPerson = new TransitionDrawable(framesAddPerson);
        transitionDrawableAddPerson.setCrossFadeEnabled(withCrossfade);
        imageView.setImageDrawable(transitionDrawableAddPerson);
        transitionDrawableAddPerson.startTransition(duration);
    }
    public static void animateImageViewDrawables(ImageView imageView, int fromDrawable, int toDrawable, Context context,boolean withCrossfade, int duration)
    {
        animateImageViewDrawables(
                imageView,
                ContextCompat.getDrawable(context, fromDrawable),
                ContextCompat.getDrawable(context, toDrawable),
                withCrossfade,
                duration
        );
    }


    public static final int GET_SCREEN_HEIGHT = 1;
    public static final int GET_SCREEN_WIDTH = 2;
    public static int getScreenDimen(Context mContext, int whatToGet)
    {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (whatToGet == GET_SCREEN_HEIGHT) ? size.y : size.x;
    }



}
