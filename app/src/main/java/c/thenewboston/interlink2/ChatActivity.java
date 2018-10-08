package c.thenewboston.interlink2;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    //FIREBASE RFERENCES
    private DatabaseReference mDatabaseRef;
    private DatabaseReference message1ref;

    //LIST STUFF
    private RecyclerView mRecyclerView2;
    private ExampleAdapter2 mExampleAdapter2;
    private ArrayList<ExampleItem2> mExampleList2;

    //XML VIEW ITEMS
    private EditText msgEditText;
    private Button msgSendButton;

    //SHARED PREFERENCES
    SharedPreferences sharedPreferences;

    //GLOBAL VARIABLES
    String myname;//USER'S NAME
    String myemail;//USER'S EMAIL
    String userName;//OTHER PARTICULAR USER'S NAME
    String userEmail;//OTHER PARTICULAR USER'S EMAIL

    //CURRENT TIME MILIS
    String Time2;
    c.thenewboston.interlink2.Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //SHARED PREFERENCES FOR LOGIN REF WE ARE GETTING THE APP USER DATA FROM THIS
        sharedPreferences = getSharedPreferences("loginref",MODE_PRIVATE);
        myname=(sharedPreferences.getString("name", null));
        myemail=(sharedPreferences.getString("username", null));

        //INTENT DATA FROM USER ACTIVITY
        Intent intent = getIntent();
        userName= intent.getStringExtra("Username");
        userEmail = intent.getStringExtra("Useremail");

        //XML ITEMS
        msgSendButton = findViewById(R.id.msgSndBtn);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Messages");
        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));


        mExampleList2 = new ArrayList<>();
        msgEditText=findViewById(R.id.msgTxt);


        //FIREBASE DATABASE REFERENCING AND GET DATA TO THE RECYCLER VIEW
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String senderEmail = dataSnapshot.child("msenderEmail").getValue(String.class);
                String receiverEmail = dataSnapshot.child("mReceiverEmail").getValue(String.class);
                String Description1 = dataSnapshot.child("mDescription").getValue(String.class);
                String sender = dataSnapshot.child("msender").getValue(String.class);
                String timer=dataSnapshot.child("mTime").getValue(String.class);


                if ((senderEmail.equals(myemail)||senderEmail.equals(userEmail))&& (receiverEmail.equals(myemail)||receiverEmail.equals(userEmail)))
                {
                    mExampleList2.add(new ExampleItem2(Description1,sender,timer,senderEmail));
                    mExampleAdapter2 = new ExampleAdapter2(ChatActivity.this, mExampleList2,myemail);
                    mRecyclerView2.setAdapter(mExampleAdapter2);

                    mRecyclerView2.scrollToPosition(mExampleAdapter2.getItemCount()-1);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View view) {
              String msgDescription=msgEditText.getText().toString();

              //GET CURRENT TIME IN MILISECOND AND CONVERT THAT LONG DATA TO STRING HERE
              long Time = System.currentTimeMillis();
              Time2 = String.valueOf(Time);

              //msg IS INSTANCE OF MESSAGE CLASS THAT REQUIRE THOSE PARAMETERS
              msg=new c.thenewboston.interlink2.Message(myname,msgDescription,userName,userEmail,myemail,timer());
              //WE MAKE A DATABASE REFERENCE USING CURRENT TIME IN MILIS
                //AND THAT REFERENCE'S DATA ARE msg OBJECT
              mDatabaseRef.child(Time2).setValue(msg);

            }
        });
    }


    private String timer() {
        //THIS FUNCTION GET THE CURRENT TIME AND IT FORMATS TO A STRING AND GIVE THE CURRENT DATE TIME WE ARE USE IT IN MESSAGE
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

}
