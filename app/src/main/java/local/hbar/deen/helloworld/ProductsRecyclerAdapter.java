package local.hbar.deen.helloworld;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ProductsViewHolder> {

    private String[] productsName;
    private String[] productsDesc;
    private TypedArray productsDrawables;

    ProductsRecyclerAdapter(String[] productsName, String[] productsDesc, TypedArray productsDrawables) {
        this.productsName = productsName;
        this.productsDesc = productsDesc;
        this.productsDrawables = productsDrawables;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_card, viewGroup, false);

//        TextView tv = (TextView) LayoutInflater
//                .from(viewGroup.getContext())
//                .inflate(R.layout.item_text, viewGroup, false);

        return new ProductsViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        TextView textView = (TextView) productsViewHolder.cardView.getChildAt(0);
        textView.setText(Html.fromHtml(String.format(Locale.getDefault(), "<b>%s</b>", productsName[i]), Html.FROM_HTML_MODE_LEGACY));
        textView.append("\n");
        textView.append(productsDesc[i]);
        textView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(productsViewHolder.cardView.getContext(), productsDrawables.getResourceId(i, 0)),
                null,
                null,
                null);
        textView.setOnClickListener((view) ->
                Toast.makeText(productsViewHolder.cardView.getContext(),
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
        return productsName.length;
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ProductsViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
