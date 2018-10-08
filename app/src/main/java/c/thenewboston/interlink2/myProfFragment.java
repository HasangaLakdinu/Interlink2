package c.thenewboston.interlink2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class myProfFragment extends Fragment {
    private View mMainView;

  TextView textName,textEmail,TextContactNo;
  CircleImageView profileImage;


    public myProfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        mMainView=inflater.inflate(R.layout.fragment_my_prof,container,false);

        textName=(TextView)mMainView.findViewById(R.id.myNameTxt);
        textEmail=(TextView)mMainView.findViewById(R.id.myEmailTxt);
        TextContactNo=(TextView)mMainView.findViewById(R.id.myContactNoTxt);
        profileImage=(CircleImageView)mMainView.findViewById(R.id.circleImageView1);

        MyProfile myProfile=(MyProfile) getActivity();
        String mynamee=myProfile.myname;
        String myemaile=myProfile.myemail;
        String mycontacte=myProfile.mycontact;
        String myurle=myProfile.myurl;

        textName.setText(mynamee);
        textEmail.setText(myemaile);
        TextContactNo.setText(mycontacte);

       Picasso.with(getContext()).load(myurle).fit().centerInside().into(profileImage);




        return mMainView;

    }



}
