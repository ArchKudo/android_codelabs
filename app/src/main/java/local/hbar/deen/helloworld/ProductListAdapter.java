package local.hbar.deen.helloworld;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private String[] productsText;
    private int[] productsDrawables;

    public ProductListAdapter(String[] productsText, int[] productsDrawables) {
        this.productsText = productsText;
        this.productsDrawables = productsDrawables;
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView tv = (TextView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_text, viewGroup, false);

        ProductViewHolder productViewHolder = new ProductViewHolder(tv);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductViewHolder productViewHolder, int i) {
        productViewHolder.textView.setText(productsText[i]);
        productViewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(productViewHolder.textView.getContext(), productsDrawables[i]),
                null,
                null,
                null);
    }

    @Override
    public int getItemCount() {
        return productsText.length;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ProductViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }
}
