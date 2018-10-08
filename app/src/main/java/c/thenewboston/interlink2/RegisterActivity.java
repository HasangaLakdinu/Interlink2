package c.thenewboston.interlink2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Edit Text for required fields
    EditText firstNameField,secNameField,emailField,contactNoField,passwordField,reEnterpasswordField;
    //Spinner for select faculty

    Spinner spinner;
    String facultyname;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GalleryIntent
    private static final int PICK_IMAGE_REQUEST=1;

    private CircleImageView choose_image;
    private ProgressBar mProgressBar;
    private Uri mImageUri;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    Upload upload;

    //*********************************************************************************************************************************/

    //ToolBar
    private android.support.v7.widget.Toolbar mToolbar;

    //Create endpointURL
    String Posturl="http://10.10.27.242:4000/signup";

    //Progress Dialog
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar
        mToolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.register_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Register");

        //Reg  Circle process
        mRegProgress =new ProgressDialog(this);
        //attach variables to xml element
        Button btnSubmit =findViewById(R.id.btnSubmit);
        firstNameField=(EditText)findViewById(R.id.firstNameField);
        secNameField=(EditText)findViewById(R.id.secNameField);
        emailField=(EditText)findViewById(R.id.emailField);
        contactNoField=(EditText)findViewById(R.id.contactNoField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        passwordField.setTypeface(Typeface.DEFAULT);
        reEnterpasswordField=(EditText)findViewById(R.id.reEnterpasswordField);
        reEnterpasswordField.setTypeface(Typeface.DEFAULT);
        spinner=(Spinner)findViewById(R.id.spinner1);

        //array adapter for spinner
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.faculties,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        /////////////////////////////////////////////////////////-----IN--ONCREATE-////////////////////////////////////////////////////////////////////////////////////

        choose_image=findViewById(R.id.button_choose_image);
        mProgressBar=findViewById(R.id.progress_bar);

        mStorageRef=FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");


       choose_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openFileChooser();
           }
       });








        //****************************************************************************************************************************************************/








        //onclick Listener for Submit Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the inserted values for varialbes
                String password2=passwordField.getText().toString();
                String repassword2=reEnterpasswordField.getText().toString();
                String email2=emailField.getText().toString().trim();

                if(Patterns.EMAIL_ADDRESS.matcher(email2).matches()) { //checks the type of email in  correct format

                    if (!TextUtils.isEmpty(password2)) { //check whether password field is empty or not
                        if (password2.equals(repassword2)) {

                            if(mImageUri !=null){
                                StorageReference fileReference=mStorageRef.child(System.currentTimeMillis()
                                        +"."+getFileExtension(mImageUri));

                                mUploadTask=fileReference.putFile(mImageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Handler handler=new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mProgressBar.setProgress(0);
                                                    }
                                                },500);
                                                StyleableToast.makeText(RegisterActivity.this,"Your data has been saved successfully",R.style.intelinkToastDone).show();

                                                upload=new Upload(firstNameField.getText().toString().trim(),
                                                        taskSnapshot.getDownloadUrl().toString() );
                                                String uploadId=mDatabaseRef.push().getKey();
                                                mDatabaseRef.child(uploadId).setValue(upload);
                                                registerUser();
                                            }

                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                StyleableToast.makeText(RegisterActivity.this,e.getMessage(),R.style.intelinkToastError).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                mProgressBar.setProgress((int)progress);
                                            }
                                        });

                        }else{
                                StyleableToast.makeText(RegisterActivity.this,"No file selected",R.style.intelinkToastError).show();
                            }



                        } else {
                            //if password doesn't match with the interns email show this toast
                            StyleableToast.makeText(RegisterActivity.this,"Password doesn't match",R.style.intelinkToastError).show();
                        }

                    } else {
                        //if password field is empty this message will be shown
                        StyleableToast.makeText(RegisterActivity.this,"Enter Password",R.style.intelinkToastError).show();
                    }


                }
             else{
                    //if email field format is incorrect this message will be shown
                   StyleableToast.makeText(RegisterActivity.this,"Enter a Valid email",R.style.intelinkToastError).show();
              }

          }
        });

    }
//////////////////////////////////////////////////----OUTSIDE ON CREATE -----////////////////////////////////////////////////////////////////////
    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK
                && data !=null && data.getData()!=null ){
            mImageUri=data.getData();

            Picasso.with(this).load(mImageUri).into(choose_image);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    //**********************************************************************************************************************************************************




    private void registerUser(){
////////////////////////////////////////////////---Inside Reg User func---////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Volleypostfunc(Posturl);

//*************************************************************************************************************************************************************/

        //If user create a new account user will rediresct to login Activity
       Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
       startActivity(loginIntent);
       finish();
    }

    public void  Volleypostfunc(String Posturl)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("text", firstNameField.getText().toString());
            jsonBody.put("text2", secNameField.getText().toString());
            jsonBody.put("text3", emailField.getText().toString());
            jsonBody.put("text4", contactNoField.getText().toString());
            jsonBody.put("text5", passwordField.getText().toString());
            jsonBody.put("text6", upload.getmImageUrl());
            jsonBody.put("text7",facultyname);



            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Posturl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }


                public byte[] getBody () throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupperted Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                protected Response<String> parseNetworkResponse (NetworkResponse response){
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // these two methods for spinner functions

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       facultyname=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

