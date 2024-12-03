package com.example.doga;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvTitle, tvAuthor, tvPages, tvYear;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvPages = findViewById(R.id.tvPages);
        tvYear = findViewById(R.id.tvYear);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String author = intent.getStringExtra("AUTHOR");
        int pages = intent.getIntExtra("PAGES", 0);

        int randomYear = generateRandomYear();

        tvTitle.setText("Cím: " + title);
        tvAuthor.setText("Szerző: " + author);
        tvPages.setText("Oldalszám: " + pages);
        tvYear.setText("Évszám: " + randomYear);

        btnBack.setOnClickListener(v -> finish());
    }

    private int generateRandomYear() {
        Random random = new Random();
        return random.nextInt(2024 - 1500 + 1) + 1500; // 1500 és 2024 között
    }
}
