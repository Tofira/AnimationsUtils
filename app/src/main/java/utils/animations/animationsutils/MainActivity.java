package utils.animations.animationsutils;

import android.animation.Animator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AnimationsUtils(textView,AnimationsUtils.SCALE_ANIMATION_OUT)
                        .setAnimationDuration(1000)
                        .setWithFadeAnimation(true)
                        .setInterpolator(new AnticipateInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                new AnimationsUtils(textView,AnimationsUtils.SCALE_ANIMATION_IN)
                                        .setAnimationDuration(1000)
                                        .setWithFadeAnimation(true)
                                        .setInterpolator(new OvershootInterpolator())
                                        .startAnimation();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        })
                        .startAnimation();
            }
        },1000);

    }
}
