package local.hbar.deen.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;


public class ProductsFragment extends Fragment {


    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        String productsType = getArguments().getString("items");
        final int[] orderCount = new int[3];
        final String[] productNames;
        String[] products = new String[3];
        String packageName = getActivity().getPackageName();

        if (productsType == "sweets") {
            productNames = getResources().getStringArray(R.array.sweets);
        } else {
            productNames = getResources().getStringArray(R.array.snacks);
        }

        for(int i = 0; i < products.length; i++) {
            Log.i(ProductsFragment.class.getSimpleName(), productNames[i]+"_desc");
            products[i] = getString(getResources().getIdentifier(String.format("%s_desc", productNames[i]), "string", packageName));
        }

        view.findViewById(R.id.fab_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("orderCount", orderCount);
                intent.putExtra("products", productNames);
                startActivity(intent);
            }
        });

        ProductListAdapter mAdapter = new ProductListAdapter(products);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
