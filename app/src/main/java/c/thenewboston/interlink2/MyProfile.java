package c.thenewboston.interlink2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.Toast;


import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class MyProfile extends AppCompatActivity {



    //toolbar
    private android.support.v7.widget.Toolbar mToolbar;

    //fragment's part as view pager
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private TabLayout mTabLayout;

    public String myname,myemail,mycontact,myurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);



        //get the content from Login Activity
         myname=getIntent().getStringExtra("name");
         myemail=getIntent().getStringExtra("email");
         mycontact=getIntent().getStringExtra("contact");
         myurl=getIntent().getStringExtra("imgurl");









        //Toolbar
        mToolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.myprofile_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Interlink");


        //Tabs
        mViewPager=(ViewPager) findViewById(R.id.main_tabPager);

        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout =(TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.main_logout_btn){
            Intent startIntent=new Intent(MyProfile.this,StartActivity.class);
            startActivity(startIntent);
            finish();
            StyleableToast.makeText(MyProfile.this,"You are Logged Out",R.style.intelinkToastDone).show();
        }
        if(item.getItemId()==R.id.main_admin_tool){
            Intent adminIntent=new Intent(MyProfile.this,AdminActivity1.class);
            startActivity(adminIntent);
            finish();

        }
        return  true;
    }
}
