package com.warnonami.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EraSelectionActivity extends Activity {

    private final String[] eras = {
            "جنگ جهانی اول",
            "جنگ جهانی دوم",
            "جهانی سوم (دوران معاصر)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(28, 32, 28, 32);
        root.setBackgroundColor(Color.rgb(15, 25, 45));

        TextView title = new TextView(this);
        title.setText("دوره تاریخی را انتخاب کنید");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 18);
        root.addView(title, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView subtitle = new TextView(this);
        subtitle.setText("جهان بازی خود را در کدام دوران آغاز می‌کنید؟");
        subtitle.setTextColor(Color.rgb(143, 169, 214));
        subtitle.setTextSize(15);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 30);
        root.addView(subtitle);

        for (int i = 0; i < eras.length; i++) {
            final String selectedEra = eras[i];
            Button eraButton = new Button(this);
            eraButton.setText(selectedEra);
            eraButton.setAllCaps(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.bottomMargin = 14;
            root.addView(eraButton, params);

            eraButton.setOnClickListener(view -> confirmEraSelection(selectedEra));
        }

        Button backButton = new Button(this);
        backButton.setText("بازگشت");
        backButton.setAllCaps(false);
        root.addView(backButton);
        backButton.setOnClickListener(view -> finish());

        setContentView(root);
    }

    private void confirmEraSelection(String era) {
        new AlertDialog.Builder(this)
                .setTitle("دوره انتخاب شد")
                .setMessage("دوره «" + era + "» انتخاب شد. مرحله بعد، انتخاب کشور خواهد بود.")
                .setPositiveButton("ادامه", (dialog, which) ->
                        Toast.makeText(
                                this,
                                "انتخاب کشور در مرحله بعد اضافه می‌شود.",
                                Toast.LENGTH_SHORT
                        ).show())
                .setNegativeButton("تغییر دوره", null)
                .show();
    }
}
