package utils.animations.animationsutils;

import android.animation.Animator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                new AnimationsUtils(textView,AnimationsUtils.FADE_ANIMATION_IN)
                        .setAnimationDuration(1000)
                        .setListener(new AnimationsUtils.AnimationFinishedListener() {
                            @Override
                            public void onAnimationEnd() {
                                Log.v("SampleProject","Animation Finished!!");
                                new AnimationsUtils(textView,AnimationsUtils.SCALE_ANIMATION_OUT)
                                        .setAnimationDuration(3000)
                                        .setWithFadeAnimation(true)
                                        .startAnimation();
                            }
                        })
                        .setInterpolator(new AnticipateInterpolator())
                        .startAnimation();
            }
        },1000);

    }
}
