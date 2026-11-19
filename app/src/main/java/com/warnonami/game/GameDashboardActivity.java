package com.warnonami.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameDashboardActivity extends Activity {
    private String selectedEra;
    private String selectedCountry;
    private int turn = 1;
    private int treasury = 1000;
    private TextView turnValue;
    private TextView treasuryValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedEra = getIntent().getStringExtra("selected_era");
        selectedCountry = getIntent().getStringExtra("selected_country");
        if (selectedEra == null) selectedEra = "جهانی سوم (دوران معاصر)";
        if (selectedCountry == null) selectedCountry = "کشور انتخاب‌نشده";

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 28, 28, 24);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(15, 25, 45));

        TextView heading = new TextView(this);
        heading.setText("جهان بازی");
        heading.setTextColor(Color.WHITE);
        heading.setTextSize(28);
        heading.setGravity(Gravity.CENTER);
        root.addView(heading);

        TextView era = makeLabel("دوره تاریخی: " + selectedEra, 16, 0xFF9DB8E8);
        root.addView(era);

        TextView country = makeLabel("🏳 کشور شما: " + selectedCountry, 22, Color.WHITE);
        country.setPadding(0, 24, 0, 24);
        root.addView(country);

        LinearLayout stats = new LinearLayout(this);
        stats.setOrientation(LinearLayout.HORIZONTAL);
        stats.setGravity(Gravity.CENTER);
        stats.setPadding(0, 8, 0, 18);

        LinearLayout turnCard = makeCard();
        TextView turnTitle = makeLabel("نوبت", 14, 0xFF9DB8E8);
        turnValue = makeLabel("۱", 24, Color.WHITE);
        turnCard.addView(turnTitle);
        turnCard.addView(turnValue);
        stats.addView(turnCard, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout moneyCard = makeCard();
        TextView moneyTitle = makeLabel("خزانه", 14, 0xFF9DB8E8);
        treasuryValue = makeLabel("۱۰۰۰ دلار", 22, Color.WHITE);
        moneyCard.addView(moneyTitle);
        moneyCard.addView(treasuryValue);
        LinearLayout.LayoutParams moneyParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        moneyParams.leftMargin = 10;
        stats.addView(moneyCard, moneyParams);
        root.addView(stats);

        TextView mapPanel = makeLabel(
                "🌍\nنقشه جهان\n\nبخش نقشه تعاملی در مرحله توسعه است.",
                20, 0xFFE5ECF8);
        mapPanel.setGravity(Gravity.CENTER);
        mapPanel.setBackgroundColor(0xFF1B2D4B);
        mapPanel.setPadding(16, 36, 16, 36);
        root.addView(mapPanel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        Button reportButton = makeButton("گزارش کشور");
        root.addView(reportButton);
        reportButton.setOnClickListener(v -> showCountryReport());

        Button endTurnButton = makeButton("پایان نوبت");
        root.addView(endTurnButton);
        endTurnButton.setOnClickListener(v -> endTurn());

        Button backButton = makeButton("بازگشت به منوی اصلی");
        root.addView(backButton);
        backButton.setOnClickListener(v -> finish());

        setContentView(root);
    }

    private TextView makeLabel(String value, int size, int color) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextColor(color);
        text.setTextSize(size);
        text.setGravity(Gravity.CENTER);
        return text;
    }

    private LinearLayout makeCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setPadding(12, 16, 12, 16);
        card.setBackgroundColor(0xFF1B2D4B);
        return card;
    }

    private Button makeButton(String title) {
        Button button = new Button(this);
        button.setText(title);
        button.setAllCaps(false);
        button.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 8;
        button.setLayoutParams(params);
        return button;
    }

    private void showCountryReport() {
        new AlertDialog.Builder(this)
                .setTitle("گزارش کشور")
                .setMessage("کشور: " + selectedCountry
                        + "\nدوره: " + selectedEra
                        + "\nشماره نوبت: " + turn
                        + "\nموجودی خزانه: " + treasury + " دلار")
                .setPositiveButton("باشه", null)
                .show();
    }

    private void endTurn() {
        turn++;
        treasury += 100;
        turnValue.setText(toPersianDigits(String.valueOf(turn)));
        treasuryValue.setText(toPersianDigits(String.valueOf(treasury)) + " دلار");
        new AlertDialog.Builder(this)
                .setTitle("نوبت جدید")
                .setMessage("نوبت بعدی آغاز شد. ۱۰۰ دلار به خزانه اضافه شد.")
                .setPositiveButton("ادامه", null)
                .show();
    }

    private String toPersianDigits(String value) {
        return value.replace('0','۰').replace('1','۱').replace('2','۲')
                .replace('3','۳').replace('4','۴').replace('5','۵')
                .replace('6','۶').replace('7','۷').replace('8','۸').replace('9','۹');
    }
}
