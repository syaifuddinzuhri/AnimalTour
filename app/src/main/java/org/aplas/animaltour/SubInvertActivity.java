package org.aplas.animaltour;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubInvertActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_invert);

        mTextView = (TextView) findViewById(R.id.text);

        ImageView img = findViewById(R.id.mainPic);
        TextView name = findViewById(R.id.mainName);
        TextView detail = findViewById(R.id.mainDetail);

        LinearLayout subLayout = findViewById(R.id.subLayout);

        String title = getIntent().getStringExtra("TITLE_ANIMAL");

        if (title != null) {
            if (title.equals("Insects")) {
                img.setImageResource(R.drawable.insects);
                name.setText(title);
                detail.setText(R.string.insect_content);
            } else if (title.equals("Arachnids")) {
                img.setImageResource(R.drawable.arachnids);
                name.setText(title);
                detail.setText(R.string.arachnid_content);
            } else if (title.equals("Molluscs")) {
                img.setImageResource(R.drawable.molluscs);
                name.setText(title);
                detail.setText(R.string.mollusc_content);
            } else if (title.equals("Crustaceans")) {
                img.setImageResource(R.drawable.crustaceans);
                name.setText(title);
                detail.setText(R.string.crustacean_content);
            }
        }

        subLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });
    }
}