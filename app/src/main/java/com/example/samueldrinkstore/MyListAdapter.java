package com.example.samueldrinkstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<Product> {
    private List<Product> objects;

    public MyListAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_simple_list_view, parent, false);
        }

        Product product = objects.get(position);

        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);
        TextView textView3 = convertView.findViewById(R.id.textView3);
        TextView textView4 = convertView.findViewById(R.id.textView4);
        TextView textView5 = convertView.findViewById(R.id.textView5);

        textView1.setText(product.getBarcode());
        textView2.setText(product.getProductCode());
        textView3.setText(product.getProductDescription());
        textView4.setText("R"+product.getPrice());
        textView5.setText(product.getCategory());

        return convertView;
    }
}
