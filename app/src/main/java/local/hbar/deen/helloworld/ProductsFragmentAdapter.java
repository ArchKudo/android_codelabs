package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProductsFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] tabNames;

    ProductsFragmentAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("items", tabNames[i]);
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }
}
