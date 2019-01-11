package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProductsFragmentAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public ProductsFragmentAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();

        // TODO: Remove switch case
        switch (i) {
            case 0:
                bundle.putString("items", "sweets");
                break;
            case 1:
                bundle.putString("items", "snacks");
                break;
        }
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
