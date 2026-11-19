package com.warnonami.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButton = findViewById(R.id.button_new_game);
        Button myGamesButton = findViewById(R.id.button_my_games);

        newGameButton.setOnClickListener(view -> showGameModeDialog());
        myGamesButton.setOnClickListener(view -> showMyGamesDialog());
    }

    private void showGameModeDialog() {
        String[] modes = {"تک‌نفره", "چندنفره (به‌زودی)"};

        new AlertDialog.Builder(this)
                .setTitle("نوع بازی را انتخاب کنید")
                .setItems(modes, (dialog, which) -> {
                    if (which == 0) {
                        Toast.makeText(
                                this,
                                "بخش انتخاب دوره تاریخی در مرحله بعد ساخته می‌شود.",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        Toast.makeText(
                                this,
                                "بخش چندنفره هنوز در دست توسعه است.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("بازگشت", null)
                .show();
    }

    private void showMyGamesDialog() {
        new AlertDialog.Builder(this)
                .setTitle("بازی‌های من")
                .setMessage("هنوز بازی ذخیره‌شده‌ای وجود ندارد. پس از ساخت قابلیت ذخیره‌سازی، بازی‌هایت اینجا نمایش داده می‌شوند.")
                .setPositiveButton("باشه", null)
                .show();
    }
}
