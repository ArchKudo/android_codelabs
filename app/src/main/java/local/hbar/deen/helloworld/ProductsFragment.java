package local.hbar.deen.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        String[] productsText = new String[3];
        int[] productsDrawableId = new int[3];
        String packageName = getActivity().getPackageName();

        if (productsType.equals("sweets")) {
            productNames = getResources().getStringArray(R.array.sweets);
        } else {
            productNames = getResources().getStringArray(R.array.snacks);
        }

        for (int i = 0; i < productsText.length; i++) {
            productsText[i] = getString(getResources().getIdentifier(String.format("%s_desc", productNames[i]), "string", packageName));
            productsDrawableId[i] = getResources().getIdentifier(String.format("%s_circle", productNames[i]), "drawable", packageName);
        }


        ProductListAdapter mAdapter = new ProductListAdapter(productsText, productsDrawableId);
        mRecyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fab_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("orderCount", orderCount);
                intent.putExtra("productsText", productNames);
                startActivity(intent);
            }
        });

        return view;
    }


}
