package c.thenewboston.interlink2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminActivity1 extends AppCompatActivity {

    String FacultyName;
    int noOfStudentMoratuwaIT;
    int noOfStudentMoratuwaCSE;
    int noOfStudentSLIIT;
    int noOfStudentUCSC;
    int noOfStudentOther;
    int noOfStudentRajarata;
    int noOfStudentIIT;
    int noOfStudentNIBM;

    String no;
    GraphView graph;


    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);
        mQueue=Volley.newRequestQueue(this);

        getGraphDetails();
        graph = (GraphView) findViewById(R.id.graph);
//
//        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, noOfStudentMoratuwaIT),
//                new DataPoint(1, noOfStudentMoratuwaCSE),
//                new DataPoint(2, noOfStudentSLIIT),
//                new DataPoint(3, noOfStudentUCSC),
//                new DataPoint(4, noOfStudentOther)
//        });
//        graph.addSeries(series);
//
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setHorizontalLabels(new String[] {"IT", "Cse", "Sliit","UCSC","Other"});
//
//        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }

    private void getGraphDetails() {


        String url = "http://10.10.27.242:4000/signup";



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("student");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject student = jsonArray.getJSONObject(i);

                                FacultyName= student.getString("facname");


                                if(FacultyName.equals("Moratuwa IT")){
                                    noOfStudentMoratuwaIT++;
                                }

                                if(FacultyName.equals("Moratuwa CSE")){
                                    noOfStudentMoratuwaCSE++;
                                }
                                if(FacultyName.equals("SLIIT")){
                                    noOfStudentSLIIT++;
                                }
                                if(FacultyName.equals("UCSC")){
                                    noOfStudentUCSC++;
                                }
                                if(FacultyName.equals("Rajarata ICT")){
                                    noOfStudentRajarata++;
                                }
                                if(FacultyName.equals("NIBM")){
                                    noOfStudentNIBM++;
                                }
                                if(FacultyName.equals("IIT")){
                                    noOfStudentIIT++;
                                }
                                else{
                                    noOfStudentOther++;
                                }



                            }
                            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                                    new DataPoint(0, noOfStudentMoratuwaIT),
                                    new DataPoint(1, noOfStudentMoratuwaCSE),
                                    new DataPoint(2, noOfStudentSLIIT),
                                    new DataPoint(3, noOfStudentUCSC),
                                    new DataPoint(4, noOfStudentRajarata),
                                    new DataPoint(5, noOfStudentNIBM),
                                    new DataPoint(6, noOfStudentIIT),
                                    new DataPoint(7, noOfStudentOther)
                            });
                            graph.addSeries(series);

                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                            staticLabelsFormatter.setHorizontalLabels(new String[] {"IT", "CSE", "SLIIT","UCSC","RajarataICT","NIBM","IIT","OTHER"});

                            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                            StyleableToast.makeText(AdminActivity1.this,no,R.style.intelinkToastDone).show();







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
