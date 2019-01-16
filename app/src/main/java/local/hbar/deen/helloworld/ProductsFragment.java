package local.hbar.deen.helloworld;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


public class ProductsFragment extends Fragment {


    public ProductsFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO: Handle hardcoded lengths
        final int[] orderCount = new int[3];
        
        final String[] productsNames;
        final String[] productsDescription;
        final TypedArray productsDrawables;

        // TODO: Handle NPE
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        if (Objects.equals(getArguments().getString("items"), "sweets")) {
            productsNames = getResources().getStringArray(R.array.sweets_header);
            productsDescription = getResources().getStringArray(R.array.sweets_desc);
            productsDrawables = getResources().obtainTypedArray(R.array.sweets_images);
        } else {
            productsNames = getResources().getStringArray(R.array.snacks_header);
            productsDescription = getResources().getStringArray(R.array.snacks_desc);
            productsDrawables = getResources().obtainTypedArray(R.array.snacks_images);
        }

        ProductsRecyclerAdapter mAdapter = new ProductsRecyclerAdapter(productsNames,
                productsDescription,
                productsDrawables);
        mRecyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fab_cart).setOnClickListener((View) -> {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            intent.putExtra("orderCount", orderCount);
            intent.putExtra("productsText", productsNames);
            startActivity(intent);
        });


        return view;
    }


}
