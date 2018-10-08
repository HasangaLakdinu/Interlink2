package c.thenewboston.interlink2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Asus on 9/18/2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                myProfFragment myproffragment=new myProfFragment();
                return myproffragment;
            case 1:
                PersonalFragment personalFragment=new PersonalFragment();
                return personalFragment;

            case 2:
                ChatsFragment chatsFragment=new ChatsFragment();
                return  chatsFragment;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Chats";

            default:
                    return null;
        }
    }


}
