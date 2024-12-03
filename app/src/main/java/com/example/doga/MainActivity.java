package com.example.doga;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etPages;
    private Button btnAdd;
    private ListView listView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPages = findViewById(R.id.etPages);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.lvBooks);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        listView.setAdapter(bookAdapter);

        btnAdd.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String pagesStr = etPages.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(pagesStr)) {
                Toast.makeText(MainActivity.this, "Minden mezőt tölts ki!", Toast.LENGTH_SHORT).show();
                return;
            }

            int pages;
            try {
                pages = Integer.parseInt(pagesStr);
                if (pages < 50) {
                    Toast.makeText(MainActivity.this, "Az oldalszámnak legalább 50-nek kell lennie!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Az oldalszámnak számnak kell lennie!", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(title, author, pages);
            bookList.add(newBook);
            bookAdapter.notifyDataSetChanged();

            etTitle.setText("");
            etAuthor.setText("");
            etPages.setText("");

            Toast.makeText(MainActivity.this, "Könyv sikeresen hozzáadva!", Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = bookList.get(position);

            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("TITLE", selectedBook.getTitle());
            intent.putExtra("AUTHOR", selectedBook.getAuthor());
            intent.putExtra("PAGES", selectedBook.getPages());

            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Törlés megerősítése")
                    .setMessage("Biztosan törölni szeretnéd ezt a könyvet?")
                    .setPositiveButton("Igen", (dialog, which) -> {
                        bookList.remove(position);
                        bookAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Könyv törölve!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Nem", null)
                    .create()
                    .show();

            return true;
        });
    }
}
