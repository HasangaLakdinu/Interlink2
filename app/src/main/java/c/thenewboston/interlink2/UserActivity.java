package c.thenewboston.interlink2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static c.thenewboston.interlink2.PersonalFragment.EXTRA_STUDENT;
import static c.thenewboston.interlink2.PersonalFragment.EXTRA_EMAIL;

import static c.thenewboston.interlink2.PersonalFragment.EXTRA_URL;

public class UserActivity extends AppCompatActivity {
    //STUDENT DETAILS
    String studentName;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       //DATA FROM PERSONAL FRAGMENT
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        studentName = intent.getStringExtra(EXTRA_STUDENT);
        email = intent.getStringExtra(EXTRA_EMAIL);

        //XML ELEMENTS ASSIGNIN HERE
        CircleImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_student_detail);
        TextView textViewLikes = findViewById(R.id.text_view_email_detail);
        Button msgBtn=findViewById(R.id.msgBtn);
        msgBtn.setText("chat with "+studentName);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(studentName);
        textViewLikes.setText(email);

          msgBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //INTENT TO CHAT ACTIVITY
               Intent ChatIntent=new Intent(UserActivity.this,ChatActivity.class);

              ChatIntent.putExtra("Username",studentName);
              ChatIntent.putExtra("Useremail",email);
               startActivity(ChatIntent);
               finish();

           }
       });

    }
}
