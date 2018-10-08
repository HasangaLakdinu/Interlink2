package c.thenewboston.interlink2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fade Animation

        ImageView imageView=findViewById(R.id.startupLogo);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);


        Thread timer=new Thread(){
            @Override
            public void run() {

                try {
                    sleep(5000);
                    Intent startIntent =new Intent(getApplicationContext(),StartActivity.class);
                    startActivity(startIntent);
                    finish();

                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };

        timer.start();

    }
}
