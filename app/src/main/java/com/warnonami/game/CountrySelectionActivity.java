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

public class CountrySelectionActivity extends Activity {

    private String selectedEra;
    private String[] countries;
    private ArrayAdapter<String> adapter;

    // Current list: 193 UN members plus the two UN observer states.
    private static final String CURRENT_COUNTRIES =
            "🇦🇫|افغانستان\n🇦🇱|آلبانی\n🇩🇿|الجزایر\n🇦🇩|آندورا\n🇦🇴|آنگولا\n" +
            "🇦🇬|آنتیگوا و باربودا\n🇦🇷|آرژانتین\n🇦🇲|ارمنستان\n🇦🇺|استرالیا\n🇦🇹|اتریش\n" +
            "🇦🇿|آذربایجان\n🇧🇸|باهاما\n🇧🇭|بحرین\n🇧🇩|بنگلادش\n🇧🇧|باربادوس\n" +
            "🇧🇾|بلاروس\n🇧🇪|بلژیک\n🇧🇿|بلیز\n🇧🇯|بنین\n🇧🇹|بوتان\n" +
            "🇧🇴|بولیوی\n🇧🇦|بوسنی و هرزگوین\n🇧🇼|بوتسوانا\n🇧🇷|برزیل\n🇧🇳|برونئی\n" +
            "🇧🇬|بلغارستان\n🇧🇫|بورکینافاسو\n🇧🇮|بوروندی\n🇨🇻|کیپ‌ورد\n🇰🇭|کامبوج\n" +
            "🇨🇲|کامرون\n🇨🇦|کانادا\n🇨🇫|جمهوری آفریقای مرکزی\n🇹🇩|چاد\n🇨🇱|شیلی\n" +
            "🇨🇳|چین\n🇨🇴|کلمبیا\n🇰🇲|کومور\n🇨🇬|جمهوری کنگو\n🇨🇩|جمهوری دموکراتیک کنگو\n" +
            "🇨🇷|کاستاریکا\n🇨🇮|ساحل عاج\n🇭🇷|کرواسی\n🇨🇺|کوبا\n🇨🇾|قبرس\n" +
            "🇨🇿|چک\n🇩🇰|دانمارک\n🇩🇯|جیبوتی\n🇩🇲|دومینیکا\n🇩🇴|جمهوری دومینیکن\n" +
            "🇪🇨|اکوادور\n🇪🇬|مصر\n🇸🇻|السالوادور\n🇬🇶|گینه استوایی\n🇪🇷|اریتره\n" +
            "🇪🇪|استونی\n🇸🇿|اسواتینی\n🇪🇹|اتیوپی\n🇫🇯|فیجی\n🇫🇮|فنلاند\n" +
            "🇫🇷|فرانسه\n🇬🇦|گابن\n🇬🇲|گامبیا\n🇬🇪|گرجستان\n🇩🇪|آلمان\n" +
            "🇬🇭|غنا\n🇬🇷|یونان\n🇬🇩|گرنادا\n🇬🇹|گواتمالا\n🇬🇳|گینه\n" +
            "🇬🇼|گینه بیسائو\n🇬🇾|گویان\n🇭🇹|هائیتی\n🇭🇳|هندوراس\n🇭🇺|مجارستان\n" +
            "🇮🇸|ایسلند\n🇮🇳|هند\n🇮🇩|اندونزی\n🇮🇷|ایران\n🇮🇶|عراق\n" +
            "🇮🇪|ایرلند\n🇮🇱|اسرائیل\n🇮🇹|ایتالیا\n🇯🇲|جامائیکا\n🇯🇵|ژاپن\n" +
            "🇯🇴|اردن\n🇰🇿|قزاقستان\n🇰🇪|کنیا\n🇰🇮|کیریباتی\n🇰🇼|کویت\n" +
            "🇰🇬|قرقیزستان\n🇱🇦|لائوس\n🇱🇻|لتونی\n🇱🇧|لبنان\n🇱🇸|لسوتو\n" +
            "🇱🇷|لیبریا\n🇱🇾|لیبی\n🇱🇮|لیختن‌اشتاین\n🇱🇹|لیتوانی\n🇱🇺|لوکزامبورگ\n" +
            "🇲🇬|ماداگاسکار\n🇲🇼|مالاوی\n🇲🇾|مالزی\n🇲🇻|مالدیو\n🇲🇱|مالی\n" +
            "🇲🇹|مالت\n🇲🇭|جزایر مارشال\n🇲🇷|موریتانی\n🇲🇺|موریس\n🇲🇽|مکزیک\n" +
            "🇫🇲|میکرونزی\n🇲🇩|مولداوی\n🇲🇨|موناکو\n🇲🇳|مغولستان\n🇲🇪|مونته‌نگرو\n" +
            "🇲🇦|مراکش\n🇲🇿|موزامبیک\n🇲🇲|میانمار\n🇳🇦|نامیبیا\n🇳🇷|نائورو\n" +
            "🇳🇵|نپال\n🇳🇱|هلند\n🇳🇿|نیوزیلند\n🇳🇮|نیکاراگوئه\n🇳🇪|نیجر\n" +
            "🇳🇬|نیجریه\n🇰🇵|کره شمالی\n🇲🇰|مقدونیه شمالی\n🇳🇴|نروژ\n🇴🇲|عمان\n" +
            "🇵🇰|پاکستان\n🇵🇼|پالائو\n🇵🇸|فلسطین\n🇵🇦|پاناما\n🇵🇬|پاپوآ گینه نو\n" +
            "🇵🇾|پاراگوئه\n🇵🇪|پرو\n🇵🇭|فیلیپین\n🇵🇱|لهستان\n🇵🇹|پرتغال\n" +
            "🇶🇦|قطر\n🇷🇴|رومانی\n🇷🇺|روسیه\n🇷🇼|رواندا\n🇰🇳|سنت کیتس و نویس\n" +
            "🇱🇨|سنت لوسیا\n🇻🇨|سنت وینسنت و گرنادین‌ها\n🇼🇸|ساموآ\n🇸🇲|سان مارینو\n🇸🇹|سائوتومه و پرنسیپ\n" +
            "🇸🇦|عربستان سعودی\n🇸🇳|سنگال\n🇷🇸|صربستان\n🇸🇨|سیشل\n🇸🇱|سیرالئون\n" +
            "🇸🇬|سنگاپور\n🇸🇰|اسلواکی\n🇸🇮|اسلوونی\n🇸🇧|جزایر سلیمان\n🇸🇴|سومالی\n" +
            "🇿🇦|آفریقای جنوبی\n🇰🇷|کره جنوبی\n🇸🇸|سودان جنوبی\n🇪🇸|اسپانیا\n🇱🇰|سری‌لانکا\n" +
            "🇸🇩|سودان\n🇸🇷|سورینام\n🇸🇪|سوئد\n🇨🇭|سوئیس\n🇸🇾|سوریه\n" +
            "🇹🇯|تاجیکستان\n🇹🇿|تانزانیا\n🇹🇭|تایلند\n🇹🇱|تیمور شرقی\n🇹🇬|توگو\n" +
            "🇹🇴|تونگا\n🇹🇹|ترینیداد و توباگو\n🇹🇳|تونس\n🇹🇷|ترکیه\n🇹🇲|ترکمنستان\n" +
            "🇹🇻|تووالو\n🇺🇬|اوگاندا\n🇺🇦|اوکراین\n🇦🇪|امارات متحده عربی\n🇬🇧|بریتانیا\n" +
            "🇺🇸|ایالات متحده آمریکا\n🇺🇾|اروگوئه\n🇺🇿|ازبکستان\n🇻🇺|وانواتو\n🇻🇦|واتیکان\n" +
            "🇻🇪|ونزوئلا\n🇻🇳|ویتنام\n🇾🇪|یمن\n🇿🇲|زامبیا\n🇿🇼|زیمبابوه";

    private static final String WWI_COUNTRIES =
            "🇩🇪|امپراتوری آلمان\n🇦🇹|اتریش-مجارستان\n🇹🇷|امپراتوری عثمانی\n🇧🇬|پادشاهی بلغارستان\n" +
            "🇷🇸|پادشاهی صربستان\n🇲🇪|پادشاهی مونته‌نگرو\n🇷🇺|امپراتوری روسیه\n🇫🇷|فرانسه\n" +
            "🇬🇧|بریتانیا\n🇧🇪|بلژیک\n🇮🇹|ایتالیا\n🇯🇵|ژاپن\n🇷🇴|رومانی\n" +
            "🇬🇷|یونان\n🇵🇹|پرتغال\n🇺🇸|ایالات متحده آمریکا\n🇧🇷|برزیل\n🇹🇭|سیام\n" +
            "🇨🇳|جمهوری چین\n🇱🇷|لیبریا\n🇦🇱|آلبانی\n🇳🇱|هلند (بی‌طرف)\n🇱🇺|لوکزامبورگ\n" +
            "🇪🇸|اسپانیا (بی‌طرف)\n🇸🇪|سوئد (بی‌طرف)\n🇳🇴|نروژ (بی‌طرف)\n🇩🇰|دانمارک (بی‌طرف)\n" +
            "🇨🇭|سوئیس (بی‌طرف)\n🇮🇷|ایران (اعلام بی‌طرفی)\n🇦🇫|افغانستان\n🇪🇹|اتیوپی\n" +
            "🇲🇽|مکزیک\n🇦🇷|آرژانتین (بی‌طرف)\n🇨🇱|شیلی (بی‌طرف)\n🇨🇦|کانادا\n" +
            "🇦🇺|استرالیا\n🇳🇿|نیوزیلند\n🇿🇦|اتحادیه آفریقای جنوبی\n🇮🇳|هند بریتانیا";

    private static final String WWII_COUNTRIES =
            "🇩🇪|آلمان\n🇮🇹|پادشاهی ایتالیا\n🇯🇵|امپراتوری ژاپن\n🇬🇧|بریتانیا\n" +
            "🇫🇷|فرانسه\n🇵🇱|لهستان\n🇷🇺|اتحاد شوروی\n🇺🇸|ایالات متحده آمریکا\n" +
            "🇨🇳|جمهوری چین\n🇨🇦|کانادا\n🇦🇺|استرالیا\n🇳🇿|نیوزیلند\n🇮🇳|هند بریتانیا\n" +
            "🇳🇱|هلند\n🇧🇪|بلژیک\n🇳🇴|نروژ\n🇩🇰|دانمارک\n🇬🇷|یونان\n" +
            "🇾🇺|یوگسلاوی\n🇷🇴|رومانی\n🇭🇺|مجارستان\n🇧🇬|بلغارستان\n🇫🇮|فنلاند\n" +
            "🇸🇰|جمهوری اسلواکی (دوران جنگ)\n🇭🇷|دولت مستقل کرواسی\n🇹🇷|ترکیه\n🇲🇽|مکزیک\n" +
            "🇧🇷|برزیل\n🇿🇦|اتحادیه آفریقای جنوبی\n🇪🇹|اتیوپی\n🇱🇺|لوکزامبورگ\n" +
            "🇨🇿|سرزمین‌های چک / بوهم و موراویا\n🇪🇸|اسپانیا\n🇵🇹|پرتغال\n🇸🇪|سوئد\n" +
            "🇨🇭|سوئیس\n🇮🇪|ایرلند\n🇦🇫|افغانستان\n🇮🇷|ایران\n🇸🇦|عربستان سعودی\n" +
            "🇪🇬|مصر\n🇹🇭|تایلند\n🇵🇭|فیلیپین\n🇲🇳|مغولستان\n🇹🇼|تایوان (تحت حاکمیت ژاپن)\n" +
            "🇮🇸|ایسلند\n🇨🇺|کوبا\n🇨🇱|شیلی\n🇦🇷|آرژانتین\n🇹🇷|ترکیه\n" +
            "🇳🇵|نپال\n🇧🇴|بولیوی\n🇨🇴|کلمبیا\n🇻🇪|ونزوئلا\n🇵🇪|پرو\n" +
            "🇵🇾|پاراگوئه\n🇺🇾|اروگوئه\n🇪🇨|اکوادور\n🇨🇷|کاستاریکا\n🇵🇦|پاناما\n" +
            "🇬🇹|گواتمالا\n🇭🇳|هندوراس\n🇳🇮|نیکاراگوئه\n🇸🇻|السالوادور\n🇩🇴|جمهوری دومینیکن\n" +
            "🇭🇹|هائیتی\n🇱🇷|لیبریا\n🇮🇷|ایران";

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
        count.setText("تعداد کشورها و حکومت‌های این دوره: " + countries.length);
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
        String source;
        if (era.contains("اول")) source = WWI_COUNTRIES;
        else if (era.contains("دوم")) source = WWII_COUNTRIES;
        else source = CURRENT_COUNTRIES;

        return source.split("\\n");
    }

    private void confirmCountry(String countryEntry) {
        String[] parts = countryEntry.split("\\|", 2);
        String countryName = parts.length > 1 ? parts[1] : countryEntry;
        new AlertDialog.Builder(this)
                .setTitle("تأیید کشور")
                .setMessage("کشور «" + countryName + "» برای دوره «" + selectedEra + "» انتخاب شود؟")
                .setPositiveButton("تأیید", (dialog, which) -> {
                    android.content.Intent intent = new android.content.Intent(
                            this, GameDashboardActivity.class);
                    intent.putExtra("selected_era", selectedEra);
                    intent.putExtra("selected_country", countryName);
                    startActivity(intent);
                })
                .setNegativeButton("بازگشت", null)
                .show();
    }
}
