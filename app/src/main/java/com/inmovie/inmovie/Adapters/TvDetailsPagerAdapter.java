package com.inmovie.inmovie.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments.TvBasicInfoFragment;
import com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments.TvSeasonFragment;

/**
 * Manages fragment, will be hooked up with TabLayout
 */

public class TvDetailsPagerAdapter extends FragmentStatePagerAdapter {

    protected int numbOfSeasons;
    protected int numbOfTabs;
    protected int id;

    public TvDetailsPagerAdapter(FragmentManager fm, int id, int numbOfSeasons, int numbOfTabs){
        super(fm);
        this.id = id;
        this.numbOfTabs = numbOfTabs;
        this.numbOfSeasons = numbOfSeasons;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                TvBasicInfoFragment basicInfoFragment = new TvBasicInfoFragment();
                Bundle savedInstance = new Bundle();
                savedInstance.putInt("tv_id", id);
                savedInstance.putInt("tv_seasons", numbOfSeasons);
                basicInfoFragment.setArguments(savedInstance);
                return basicInfoFragment;
            case 1:
                TvSeasonFragment seasonFragment = new TvSeasonFragment();
                Bundle savedInstance1 = new Bundle();
                savedInstance1.putInt("tv_id", id);
                savedInstance1.putInt("tv_seasons", numbOfSeasons);
                seasonFragment.setArguments(savedInstance1);
                return seasonFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount(){
        return numbOfTabs;
    }
}
