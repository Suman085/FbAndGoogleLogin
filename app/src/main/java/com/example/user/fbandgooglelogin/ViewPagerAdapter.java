package com.example.user.fbandgooglelogin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by User on 4/4/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment= new HomeFragment();
                break;
            case 1:
                fragment= new ServicesFragment();
                break;
            case 2:
                fragment= new TeamFragment();
                break;
            case 3:
                fragment= new CareersFragment();
                break;
            case 4:
                fragment= new AboutUsFragment();
                break;
            case 5:
                fragment= new ContactUsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence charsq=null;
        switch (position) {
            case 0:
                charsq= "Home";
            break;
            case 1:
                charsq= "Services";
            break;
            case 2:
                charsq= "Team";
                break;
            case 3:
                charsq= "Careers";
                break;
            case 4:
                charsq= "About Us";
                break;
            case 5:
                charsq= "Contact Us";
                break;
        }
        return charsq;
    }
}

