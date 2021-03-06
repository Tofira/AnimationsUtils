# AnimationsUtils
Android animations library that supports different types of animations.

Sample usage - 
```java
new AnimationsUtils(viewToAnimate,AnimationsUtils.SLIDING_ANIMATION_IN_DOWN)
        .setAnimationDuration(1000)
        .setInterpolator(new OvershootInterpolator())
        .setInitialDelay(1000)
        .setListener(new AnimationsUtils.AnimationFinishedListener() {
            @Override
            public void onAnimationEnd() {
                Log.v("TestProject", "Animation ended!");
            }
        })
        .startAnimation();
```

This library supports 12 different animation types - 
*   SLIDING_ANIMATION_OUT_LEFT
*    SLIDING_ANIMATION_OUT_RIGHT
*    SLIDING_ANIMATION_OUT_UP
*    SLIDING_ANIMATION_OUT_DOWN
*    SLIDING_ANIMATION_IN_LEFT
*    SLIDING_ANIMATION_IN_RIGHT
*    SLIDING_ANIMATION_IN_UP
*    SLIDING_ANIMATION_IN_DOWN
*    FADE_ANIMATION_IN
*    FADE_ANIMATION_OUT
*    SCALE_ANIMATION_IN
*    SCALE_ANIMATION_OUT

<b>Features - </b>

* Supports custom listener, that only has one method - `onAnimationEnd`. This is because it is the most commonly used method, and many times you do no need the rest of the methods provided with the native listener.
* Supports settings native Interpolators and Listeners.
* Supports combining Fade in/out animation together with sliding/scaling animations. This is achieved through the `setWithFadeAnimation()` method (Default value - false).

<b>How to install - </b>

Simply copy the file `AnimationsUtils` to your project.
