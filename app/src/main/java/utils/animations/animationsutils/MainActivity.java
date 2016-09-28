package utils.animations.animationsutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout testLayout = (RelativeLayout) findViewById(R.id.testLayout);

        new AnimationsUtils(testLayout,AnimationsUtils.SLIDING_ANIMATION_IN_DOWN)
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


    }
}
