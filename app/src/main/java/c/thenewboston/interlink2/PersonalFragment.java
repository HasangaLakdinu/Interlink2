package c.thenewboston.interlink2;

//THIS FRAGMENT FOR THE USERS LIST IN THE UI
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements ExampleAdapter.OnItemClickListener {

    //this data is goin to User Activity via Intent
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_STUDENT = "creatorName";
    public static final String EXTRA_EMAIL = "likeCount";


    //fragment personal things
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private EditText msearchFieldText;

    String searchName;


    private View mMainView;


    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView=inflater.inflate(R.layout.fragment_personal,container,false);





        mRecyclerView=mMainView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(getContext());
        parseJSON();

        msearchFieldText=mMainView.findViewById(R.id.searchFieldTxt);
        msearchFieldText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        return mMainView;
    }

    private void filter(String text){
     ArrayList<ExampleItem>filteredList=new ArrayList<>();
        for (ExampleItem item : mExampleList) {
            if (item.getStudent().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mExampleAdapter.filterList(filteredList);
    }

    private void parseJSON() {
        String url = "http://10.10.27.242:4000/signup";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("student");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject student = jsonArray.getJSONObject(i);

                                String studentName = student.getString("fname");
                                String imageUrl = student.getString("imgurl");
                                String email = student.getString("email");

                                mExampleList.add(new ExampleItem(imageUrl, studentName, email));
                            }

                            mExampleAdapter = new ExampleAdapter(getContext(), mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(PersonalFragment.this);



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

        mRequestQueue.add(request);
    }


    //we made a onItemClick listener interface and there was a functuion call onItem click we re gonna override here
    @Override
    public void onItemClick(int position) {
        Intent UserIntent = new Intent(getContext(), UserActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        UserIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        UserIntent.putExtra(EXTRA_STUDENT, clickedItem.getStudent());
        UserIntent.putExtra(EXTRA_EMAIL, clickedItem.getEmail());

        startActivity(UserIntent);
    }


}
