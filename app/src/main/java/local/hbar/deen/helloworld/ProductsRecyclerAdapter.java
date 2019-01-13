package local.hbar.deen.helloworld;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ProductsViewHolder> {

    private String[] productsText;
    private int[] productsDrawables;

    ProductsRecyclerAdapter(String[] productsText, int[] productsDrawables) {
        this.productsText = productsText;
        this.productsDrawables = productsDrawables;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView tv = (TextView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_text, viewGroup, false);

        return new ProductsViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        productsViewHolder.textView.setText(productsText[i]);
        productsViewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(productsViewHolder.textView.getContext(), productsDrawables[i]),
                null,
                null,
                null);
        productsViewHolder.textView.setOnClickListener((view) ->
                Toast.makeText(productsViewHolder.textView.getContext(),
                        "Long Press to add!",
                        Toast.LENGTH_SHORT).show());

        // TODO: Fix On long click listener
        // Is this even the correct class to implement it?

//        productsViewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                v.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        static int orderCount = 0;
//                        orderCount += 1;
//                        Log.d(ProductsRecyclerAdapter.class.getSimpleName(), Integer.toString(orderCount));
//                        return true;
//                    }
//                });
//                return true;
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productsText.length;
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ProductsViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }
}
