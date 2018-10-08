package c.thenewboston.interlink2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tomer.fadingtextview.FadingTextView;

public class StartActivity extends AppCompatActivity {

   private FadingTextView  fadingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

       //fading text view that welcome
        fadingTextView=findViewById(R.id.fading_text_view);

        //Animation Color
        RelativeLayout relativeLayout=findViewById(R.id.startLayout);
        AnimationDrawable animationDrawable=(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();




        //Buttons for register page and login page
        Button RegisterBtn =findViewById(R.id.regBtn);
        Button alHvAccBtn=findViewById(R.id.alHvAccBtn);

        //onclick listener to Register
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(startIntent);
            }
        });

        //onclick listener to Login
        alHvAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }


}
