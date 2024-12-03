package com.example.doga;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Book> books;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        TextView tvPages = convertView.findViewById(R.id.tvPages);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        Book book = books.get(position);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvPages.setText(String.valueOf(book.getPages()));

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Biztosan törölni szeretnéd a könyvet?")
                    .setPositiveButton("Igen", (dialog, which) -> {
                        books.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Nem", null)
                    .show();
        });

        return convertView;
    }
}
