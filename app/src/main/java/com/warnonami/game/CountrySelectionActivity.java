package com.warnonami.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CountrySelectionActivity extends Activity {

    private String selectedEra;
    private String[] countries;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedEra = getIntent().getStringExtra("selected_era");
        if (selectedEra == null) selectedEra = "جهانی سوم (دوران معاصر)";
        countries = getCountriesForEra(selectedEra);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 28, 24, 20);
        root.setBackgroundColor(0xFF0F192D);

        TextView title = new TextView(this);
        title.setText("کشور خود را انتخاب کنید");
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView eraLabel = new TextView(this);
        eraLabel.setText("دوره: " + selectedEra);
        eraLabel.setTextColor(0xFF9DB8E8);
        eraLabel.setTextSize(15);
        eraLabel.setGravity(Gravity.CENTER);
        eraLabel.setPadding(0, 10, 0, 18);
        root.addView(eraLabel);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("جست‌وجوی کشور...");
        search.setTextColor(0xFFFFFFFF);
        search.setHintTextColor(0xFF9AA8BD);
        root.addView(search);

        ListView listView = new ListView(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new ArrayList<>(Arrays.asList(countries)));
        listView.setAdapter(adapter);
        root.addView(listView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        TextView count = new TextView(this);
        count.setText("تعداد کشورهای این فهرست: " + countries.length);
        count.setTextColor(0xFF9DB8E8);
        count.setGravity(Gravity.CENTER);
        count.setPadding(0, 10, 0, 4);
        root.addView(count);

        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String country = adapter.getItem(position);
            if (country != null) confirmCountry(country);
        });

        setContentView(root);
    }

    private String[] getCountriesForEra(String era) {
        if (era.contains("اول")) {
            return new String[] {
                "امپراتوری آلمان", "اتریش-مجارستان", "امپراتوری عثمانی", "بلغارستان",
                "پادشاهی صربستان", "روسیه", "فرانسه", "بریتانیا", "بلژیک", "مونته‌نگرو",
                "ایتالیا", "ژاپن", "رومانی", "یونان", "پرتغال", "ایالات متحده آمریکا",
                "برزیل", "سیام (تایلند)", "چین", "لیبریا"
            };
        }
        if (era.contains("دوم")) {
            return new String[] {
                "آلمان", "ایتالیا", "ژاپن", "بریتانیا", "فرانسه", "لهستان", "اتحاد شوروی",
                "ایالات متحده آمریکا", "چین", "کانادا", "استرالیا", "نیوزیلند", "هند بریتانیا",
                "هلند", "بلژیک", "نروژ", "دانمارک", "یونان", "یوگسلاوی", "رومانی",
                "مجارستان", "بلغارستان", "فنلاند", "اسلواکی", "کرواسی", "ترکیه",
                "مکزیک", "برزیل", "آفریقای جنوبی", "اتیوپی"
            };
        }
        return new String[] {
            "ایران", "افغانستان", "آلبانی", "الجزایر", "آندورا", "آنگولا",
            "آنتیگوا و باربودا", "آرژانتین", "ارمنستان", "استرالیا", "اتریش",
            "آذربایجان", "باهاما", "بحرین", "بنگلادش", "بلاروس", "بلژیک",
            "بلیز", "بنین", "بوتان", "بولیوی", "بوسنی و هرزگوین", "بوتسوانا",
            "برزیل", "بلغارستان", "کامبوج", "کامرون", "کانادا", "شیلی",
            "چین", "کلمبیا", "کاستاریکا", "کرواسی", "کوبا", "قبرس",
            "چک", "دانمارک", "مصر", "استونی", "اتیوپی", "فنلاند",
            "فرانسه", "گرجستان", "آلمان", "غنا", "یونان", "هند",
            "اندونزی", "عراق", "ایرلند", "ایتالیا", "ژاپن", "اردن",
            "قزاقستان", "کویت", "لبنان", "مالزی", "مکزیک", "مغولستان",
            "مراکش", "نپال", "هلند", "نیوزیلند", "نیجریه", "کره شمالی",
            "نروژ", "عمان", "پاکستان", "فلسطین", "پرو", "فیلیپین",
            "لهستان", "پرتغال", "قطر", "رومانی", "روسیه", "عربستان سعودی",
            "سنگاپور", "اسلواکی", "اسلوونی", "آفریقای جنوبی", "کره جنوبی",
            "اسپانیا", "سری‌لانکا", "سوئد", "سوئیس", "سوریه", "تایوان",
            "تاجیکستان", "تایلند", "تونس", "ترکیه", "اوکراین",
            "امارات متحده عربی", "بریتانیا", "ایالات متحده آمریکا",
            "اروگوئه", "ازبکستان", "ویتنام", "یمن", "زیمبابوه"
        };
    }

    private void confirmCountry(String country) {
        new AlertDialog.Builder(this)
                .setTitle("تأیید کشور")
                .setMessage("کشور «" + country + "» برای دوره «" + selectedEra + "» انتخاب شود؟")
                .setPositiveButton("تأیید", (dialog, which) -> {
                    Toast.makeText(this, country + " انتخاب شد. مرحله بعد نقشه بازی است.",
                            Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("بازگشت", null)
                .show();
    }
}
