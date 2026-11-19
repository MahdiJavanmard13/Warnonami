package com.warnonami.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
        root.setPadding(18, 18, 18, 16);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(15, 25, 45));

        TextView heading = makeLabel("جهان بازی", 27, Color.WHITE);
        root.addView(heading);
        root.addView(makeLabel("دوره تاریخی: " + selectedEra, 15, 0xFF9DB8E8));

        TextView country = makeLabel("🏳 کشور شما: " + selectedCountry, 20, Color.WHITE);
        country.setPadding(0, 12, 0, 12);
        root.addView(country);

        LinearLayout stats = new LinearLayout(this);
        stats.setOrientation(LinearLayout.HORIZONTAL);
        stats.setGravity(Gravity.CENTER);
        stats.setPadding(0, 4, 0, 12);

        LinearLayout turnCard = makeCard();
        turnValue = makeLabel("۱", 23, Color.WHITE);
        turnCard.addView(makeLabel("نوبت", 14, 0xFF9DB8E8));
        turnCard.addView(turnValue);
        stats.addView(turnCard, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout moneyCard = makeCard();
        treasuryValue = makeLabel("۱۰۰۰ دلار", 21, Color.WHITE);
        moneyCard.addView(makeLabel("خزانه", 14, 0xFF9DB8E8));
        moneyCard.addView(treasuryValue);
        LinearLayout.LayoutParams moneyParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        moneyParams.leftMargin = 8;
        stats.addView(moneyCard, moneyParams);
        root.addView(stats);

        TextView mapTitle = makeLabel("نقشه جهان", 19, Color.WHITE);
        mapTitle.setPadding(0, 4, 0, 6);
        root.addView(mapTitle);

        WorldMapView map = new WorldMapView(this);
        LinearLayout.LayoutParams mapParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        mapParams.bottomMargin = 8;
        root.addView(map, mapParams);

        root.addView(makeButton("گزارش کشور", v -> showCountryReport()));
        root.addView(makeButton("پایان نوبت", v -> endTurn()));
        root.addView(makeButton("بازگشت به منوی اصلی", v -> finish()));
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
        card.setPadding(10, 10, 10, 10);
        card.setBackgroundColor(0xFF1B2D4B);
        return card;
    }

    private Button makeButton(String title, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(title);
        button.setAllCaps(false);
        button.setTextSize(15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 5;
        button.setLayoutParams(params);
        button.setOnClickListener(listener);
        return button;
    }

    private void showCountryReport() {
        new AlertDialog.Builder(this).setTitle("گزارش کشور")
                .setMessage("کشور: " + selectedCountry + "\nدوره: " + selectedEra
                        + "\nشماره نوبت: " + turn
                        + "\nموجودی خزانه: " + treasury + " دلار")
                .setPositiveButton("باشه", null).show();
    }

    private void endTurn() {
        turn++;
        treasury += 100;
        turnValue.setText(toPersianDigits(String.valueOf(turn)));
        treasuryValue.setText(toPersianDigits(String.valueOf(treasury)) + " دلار");
        new AlertDialog.Builder(this).setTitle("نوبت جدید")
                .setMessage("نوبت بعدی آغاز شد. ۱۰۰ دلار به خزانه اضافه شد.")
                .setPositiveButton("ادامه", null).show();
    }

    private String toPersianDigits(String value) {
        return value.replace('0','۰').replace('1','۱').replace('2','۲')
                .replace('3','۳').replace('4','۴').replace('5','۵')
                .replace('6','۶').replace('7','۷').replace('8','۸').replace('9','۹');
    }

    // A lightweight offline schematic map. Country borders and country selection
    // will be added in a later map update.
    private static class WorldMapView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        WorldMapView(Activity activity) {
            super(activity);
            setBackgroundColor(0xFF102A46);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float sx = getWidth() / 1000f;
            float sy = getHeight() / 520f;
            canvas.save();
            canvas.scale(sx, sy);

            paint.setColor(0xFF173B5C);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, 1000, 520, paint);

            paint.setColor(0xFF4D9B79);
            paint.setStyle(Paint.Style.FILL);
            drawLand(canvas, new float[]{65,95,115,55,175,35,225,65,250,125,220,165,190,205,165,260,130,245,105,190,75,165,50,120});
            drawLand(canvas, new float[]{205,270,255,280,285,325,300,385,275,455,245,420,230,355,210,320});
            drawLand(canvas, new float[]{420,80,465,55,515,65,545,95,585,105,610,145,580,175,545,165,525,205,490,190,465,155,430,145,405,110});
            drawLand(canvas, new float[]{485,205,530,195,560,230,555,285,530,350,500,405,475,360,460,300,465,245});
            drawLand(canvas, new float[]{575,75,650,45,735,55,815,90,900,105,955,150,925,190,865,180,830,220,775,205,735,175,690,190,650,155,610,145});
            drawLand(canvas, new float[]{790,300,835,285,885,305,905,350,875,395,830,410,795,380,775,340});
            drawLand(canvas, new float[]{70,465,250,465,390,475,560,465,720,470,900,465,960,485,820,500,610,495,390,505,180,495});

            paint.setColor(0xFFB7D9C5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            drawCoast(canvas, new float[]{65,95,115,55,175,35,225,65,250,125,220,165,190,205,165,260,130,245,105,190,75,165,50,120});
            drawCoast(canvas, new float[]{205,270,255,280,285,325,300,385,275,455,245,420,230,355,210,320});
            drawCoast(canvas, new float[]{420,80,465,55,515,65,545,95,585,105,610,145,580,175,545,165,525,205,490,190,465,155,430,145,405,110});
            drawCoast(canvas, new float[]{485,205,530,195,560,230,555,285,530,350,500,405,475,360,460,300,465,245});
            drawCoast(canvas, new float[]{575,75,650,45,735,55,815,90,900,105,955,150,925,190,865,180,830,220,775,205,735,175,690,190,650,155,610,145});
            drawCoast(canvas, new float[]{790,300,835,285,885,305,905,350,875,395,830,410,795,380,775,340});

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xFFDCEAF5);
            paint.setTextSize(24);
            canvas.drawText("نقشه نمایشی قاره‌ها", 365, 495, paint);
            canvas.restore();
        }

        private void drawLand(Canvas canvas, float[] points) {
            Path path = makePath(points);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }

        private void drawCoast(Canvas canvas, float[] points) {
            Path path = makePath(points);
            path.close();
            canvas.drawPath(path, paint);
        }

        private Path makePath(float[] points) {
            Path path = new Path();
            if (points.length < 4) return path;
            path.moveTo(points[0], points[1]);
            for (int i = 2; i < points.length; i += 2) {
                path.lineTo(points[i], points[i + 1]);
            }
            path.close();
            return path;
        }
    }
}
