package c.thenewboston.interlink2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    //Tool bar
    private android.support.v7.widget.Toolbar mToolbar;

    //Edit text for email and password fields
    EditText emailField11,passwordField11;

    //Shared preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;






    //-------------------------------------------------------------------------------------------------
    String emailtxtbox;
    String passwordtxtbox;
    String resEmail;
    String resPass;
    String resfirstname;
    String reslastname;
    String resContact;
    String resimageUrl;

    //Progress Dialog
    private ProgressDialog mLogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //the request JSON is gather by this mQueue
        mQueue=Volley.newRequestQueue(this);

        //Attaching xml elements to the variable
        emailField11=findViewById(R.id.emailField11);
        passwordField11=findViewById(R.id.passwordField11);
        passwordField11.setTypeface(Typeface.DEFAULT);//  set Typeface default of password fied
        Button submitbtn11=findViewById(R.id.btnSubmit11);

        //Shared preferences things here
        sharedPreferences = getSharedPreferences("loginref",MODE_PRIVATE);

        editor=sharedPreferences.edit();

        //Progress Dialog
        mLogProgress =new ProgressDialog(this);

        //Set Toolbar
        mToolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.login_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");


        //Set On Click Listener for Submit Button
        submitbtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Values of the input fields as Strings
                emailtxtbox=emailField11.getText().toString();
                passwordtxtbox=passwordField11.getText().toString();

                if(!TextUtils.isEmpty(emailtxtbox)&&!TextUtils.isEmpty(passwordtxtbox)){//Check whether fields ar empty

                    //Log progress circle works start here


                    loginPass();
                }

                else{

                    StyleableToast.makeText(LoginActivity.this,"Enter both email and password",R.style.intelinkToastError).show();
                }


            }
        });

        savelogin=sharedPreferences.getBoolean("savelogin",true);
        if(savelogin==true) {
            emailField11.setText(sharedPreferences.getString("username", null));
            passwordField11.setText(sharedPreferences.getString("password", null));
        }

    }

    private void loginPass(){
        //Calling List End point
        String loginurl="http://10.10.27.242:4000/signup/";

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, loginurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonArray=response.getJSONArray("student");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject student=jsonArray.getJSONObject(i);

                                //Get  all emails of responses
                                String studentemail=student.getString("email");

                                if(studentemail.equals(emailtxtbox)){//check whether text box email and res email are same or not if same
                                    //Assigning Values from Response Json object
                                    resEmail=student.getString("email");
                                    resPass=student.getString("password");
                                    resfirstname=student.getString("fname");
                                    reslastname=student.getString("lname");
                                    resContact=student.getString("contact");
                                    resimageUrl=student.getString("imgurl");
                                }

                            }


                            if(resEmail.equals(emailtxtbox)&&resPass.equals(passwordtxtbox)){//check selected email and password are matched or not
                                //if match User will redirect to MyProfile Activity
                                mLogProgress.setTitle("Loggin User");
                                mLogProgress.setMessage("Please wait !");
                                mLogProgress.setCanceledOnTouchOutside(false);
                                mLogProgress.show();
                               // mLogProgress.dismiss();

                                Intent profileIntent=new Intent(LoginActivity.this,MyProfile.class);
                                //Not Only Redirect Some Data are passing to MyProfile Activity as key value pair
                                profileIntent.putExtra("name",resfirstname);
                                profileIntent.putExtra("email",resEmail);
                                profileIntent.putExtra("contact",resContact);
                                profileIntent.putExtra("imgurl",resimageUrl);

                                editor.putBoolean("savelogin",true);
                                editor.putString("username",emailtxtbox);
                                editor.putString("password",passwordtxtbox);
                                editor.putString("name",resfirstname);
                                editor.commit();
                                startActivity(profileIntent);
                                finish();

                                StyleableToast.makeText(LoginActivity.this,"You are successfully Logged in",R.style.intelinkToastDone).show();
                            }
                            else{
                                StyleableToast.makeText(LoginActivity.this,"User name and password are doesn't match",R.style.intelinkToastError).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}
