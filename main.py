
# -*- coding: utf-8 -*-
# VARNONAMI | وارنونامی - Prototype V1
# Install:
# pip install kivy arabic-reshaper python-bidi
# Run:
# python main.py

from kivy.app import App
from kivy.core.window import Window
from kivy.metrics import dp
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.popup import Popup
from kivy.uix.spinner import Spinner

try:
    import arabic_reshaper
    from bidi.algorithm import get_display

    def fa(text):
        return get_display(arabic_reshaper.reshape(str(text)))

except ImportError:
    def fa(text):
        return str(text)


Window.clearcolor = (0.035, 0.055, 0.09, 1)
Window.size = (420, 820)


# استان‌های نمونه؛ نقشه واقعی در نسخه‌های بعدی اضافه می‌شود.
COUNTRIES = {
    "ایران": ["تهران", "فارس", "خراسان", "آذربایجان"],
    "فلسطین": ["شمال", "مرکز", "جنوب"],
    "آرمانیا": ["آذر", "باران", "سپهر"],
    "دریانا": ["ساحل", "جزیره", "بندر"],
}

FLAGS = [
    "🇮🇷", "🇵🇸", "🏳️", "🌊",
    "⭐", "🟦", "🟥", "🟩"
]


class Varnonami(App):

    def build(self):

        self.title = "VARNONAMI"

        self.era = ""
        self.country = "ایران"
        self.flag = "🇮🇷"
        self.capital = "تهران"

        self.turn = 1
        self.resources = 100

        self.owned = {
            name: list(provinces)
            for name, provinces in COUNTRIES.items()
        }

        self.selected = None

        # داده‌های آزمایشی دشمنان اولیه
        self.enemies = {
            "فلسطین": ["کشور الف", "کشور ب"]
        }

        self.root_box = BoxLayout(
            orientation="vertical",
            padding=dp(12),
            spacing=dp(8)
        )

        self.home()

        return self.root_box

    # ابزارهای رابط کاربری

    def clear(self):
        self.root_box.clear_widgets()

    def label(self, text, size=18, height=42):

        return Label(
            text=fa(text),
            font_size=dp(size),
            size_hint_y=None,
            height=dp(height),
            color=(0.92, 0.96, 1, 1),
            halign="center",
            valign="middle"
        )

    def button(self, text, function, height=48):

        btn = Button(
            text=fa(text),
            size_hint_y=None,
            height=dp(height),
            background_normal="",
            background_color=(0.10, 0.34, 0.52, 1),
            color=(1, 1, 1, 1),
            font_size=dp(15)
        )

        btn.bind(
            on_release=lambda *_: function()
        )

        return btn

    def popup(self, title, message):

        box = BoxLayout(
            orientation="vertical",
            padding=dp(10),
            spacing=dp(8)
        )

        box.add_widget(
            Label(
                text=fa(message),
                color=(1, 1, 1, 1),
                halign="center"
            )
        )

        pop = Popup(
            title=fa(title),
            content=box,
            size_hint=(0.88, 0.4)
        )

        box.add_widget(
            self.button("باشه", pop.dismiss)
        )

        pop.open()

    # صفحه اصلی

    def home(self):

        self.clear()

        self.root_box.add_widget(
            self.label(
                "VARNONAMI | وارنونامی",
                24,
                60
            )
        )

        self.root_box.add_widget(
            self.label("بازی راهبردی نوبتی", 17)
        )

        self.root_box.add_widget(
            self.button(
                "ساخت بازی جدید",
                self.new_game
            )
        )

        self.root_box.add_widget(
            self.button(
                "بازی‌های من",
                lambda: self.popup(
                    "بازی‌های من",
                    "ذخیره و بارگذاری در نسخه بعدی اضافه می‌شود."
                )
            )
        )

        self.root_box.add_widget(Label())

    # ساخت بازی

    def new_game(self):

        self.clear()

        self.root_box.add_widget(
            self.label("ساخت بازی جدید")
        )

        self.root_box.add_widget(
            self.button("تک‌نفره", self.choose_era)
        )

        self.root_box.add_widget(
            self.button(
                "چندنفره",
                lambda: self.popup(
                    "چندنفره",
                    "اتصال بلوتوث هنوز پیاده‌سازی نشده است."
                )
            )
        )

        self.root_box.add_widget(
            self.button("بازگشت", self.home)
        )

    # انتخاب دوره تاریخی

    def choose_era(self):

        self.clear()

        self.root_box.add_widget(
            self.label("انتخاب دوره تاریخی")
        )

        eras = [
            "جنگ جهانی اول (۱۹۱۴ تا ۱۹۱۸)",
            "جنگ جهانی دوم (۱۹۳۹ تا ۱۹۴۵)",
            "جهان معاصر (سناریوی خیالی)"
        ]

        for era in eras:

            self.root_box.add_widget(
                self.button(
                    era,
                    lambda e=era: self.choose_country(e),
                    56
                )
            )

        self.root_box.add_widget(
            self.button("بازگشت", self.new_game)
        )

    # انتخاب کشور

    def choose_country(self, era):

        self.era = era

        self.clear()

        self.root_box.add_widget(
            self.label("انتخاب کشور")
        )

        for country in COUNTRIES:

            self.root_box.add_widget(
                self.button(
                    country,
                    lambda c=country: self.start(c)
                )
            )

        self.root_box.add_widget(
            self.button("بازگشت", self.choose_era)
        )

    # شروع بازی

    def start(self, country):

        self.country = country

        self.flag = {
            "ایران": "🇮🇷",
            "فلسطین": "🇵🇸"
        }.get(country, "🏳️")

        self.capital = COUNTRIES[country][0]

        self.turn = 1
        self.resources = 100

        self.game()

    # صفحه بازی

    def game(self):

        self.clear()

        self.root_box.add_widget(
            self.label(
                "VARNONAMI | وارنونامی",
                22,
                48
            )
        )

        enemy = ""

        if "معاصر" in self.era and self.country in self.enemies:

            enemy = (
                "\nدشمنان اولیه: "
                + "، ".join(self.enemies[self.country])
            )

        self.root_box.add_widget(
            self.label(
                f"{self.flag} کشور: {self.country} | {self.era}{enemy}",
                13,
                66
            )
        )

        self.root_box.add_widget(
            self.label(
                f"نوبت: {self.turn}   "
                f"منابع: {self.resources}   "
                f"پایتخت: {self.capital}",
                13
            )
        )

        self.root_box.add_widget(
            self.label("نقشه آزمایشی استان‌ها", 17)
        )

        grid = GridLayout(
            cols=2,
            spacing=dp(5),
            size_hint_y=0.52
        )

        for country, provinces in self.owned.items():

            for province in provinces:

                owner = (
                    "شما"
                    if country == self.country
                    else country
                )

                grid.add_widget(
                    self.button(
                        f"{province}\n({owner})",
                        lambda c=country, p=province: self.select(c, p),
                        58
                    )
                )

        self.root_box.add_widget(grid)

        actions = GridLayout(
            cols=2,
            spacing=dp(5),
            size_hint_y=None,
            height=dp(160)
        )

        actions.add_widget(
            self.button("حمله به استان انتخابی", self.attack)
        )

        actions.add_widget(
            self.button("پایان نوبت", self.end_turn)
        )

        actions.add_widget(
            self.button("تغییر پایتخت", self.change_capital)
        )

        actions.add_widget(
            self.button("تغییر پرچم", self.change_flag)
        )

        actions.add_widget(
            self.button("کنسولگری مرکزی", self.embassy)
        )

        actions.add_widget(
            self.button(
                "اطلاعات کشور",
                lambda: self.popup(
                    "اطلاعات کشور",
                    f"کشور: {self.country}\n"
                    f"پایتخت: {self.capital}\n"
                    f"استان‌ها: {len(self.owned[self.country])}"
                )
            )
        )

        self.root_box.add_widget(actions)

    # انتخاب استان

    def select(self, country, province):

        self.selected = (country, province)

        self.popup(
            "استان انتخاب شد",
            f"استان {province} از کشور {country} انتخاب شد."
        )

    # فتح استان

    def attack(self):

        if not self.selected:

            self.popup(
                "حمله",
                "ابتدا یک استان را انتخاب کن."
            )

            return

        country, province = self.selected

        if country == self.country:

            self.popup(
                "حمله",
                "این استان متعلق به خودت است."
            )

            return

        if self.resources < 20:

            self.popup(
                "منابع",
                "برای حمله آزمایشی ۲۰ واحد منابع لازم است."
            )

            return

        self.resources -= 20

        self.owned[country].remove(province)

        self.owned[self.country].append(province)

        if not self.owned[country]:

            self.popup(
                "کشور سقوط کرد",
                f"تمام استان‌های {country} تصرف شدند. "
                "بازیکن می‌تواند با کنسولگری مرکزی ادامه دهد."
            )

        self.selected = None

        self.game()

    # پایان نوبت

    def end_turn(self):

        self.turn += 1

        self.resources += 15

        self.game()

    # پنجره انتخاب

    def choose_popup(self, title, prompt, values, callback):

        box = BoxLayout(
            orientation="vertical",
            padding=dp(10),
            spacing=dp(8)
        )

        spinner = Spinner(
            text=values[0],
            values=values,
            size_hint_y=None,
            height=dp(48)
        )

        box.add_widget(
            Label(
                text=fa(prompt),
                color=(1, 1, 1, 1)
            )
        )

        box.add_widget(spinner)

        pop = Popup(
            title=fa(title),
            content=box,
            size_hint=(0.88, 0.38)
        )

        box.add_widget(
            self.button(
                "ثبت",
                lambda: (
                    callback(spinner.text),
                    pop.dismiss(),
                    self.game()
                )
            )
        )

        pop.open()

    # تغییر پایتخت

    def change_capital(self):

        provinces = self.owned[self.country]

        if provinces:

            self.choose_popup(
                "تغییر پایتخت",
                "پایتخت جدید:",
                provinces,
                lambda value: setattr(
                    self,
                    "capital",
                    value
                )
            )

        else:

            self.popup(
                "تغییر پایتخت",
                "برای تغییر پایتخت باید یک استان داشته باشی."
            )

    # تغییر پرچم

    def change_flag(self):

        self.choose_popup(
            "تغییر پرچم",
            "پرچم جدید:",
            FLAGS,
            lambda value: setattr(
                self,
                "flag",
                value
            )
        )

    # کنسولگری مرکزی

    def embassy(self):

        hosts = [
            country
            for country in COUNTRIES
            if country != self.country
        ]

        self.choose_popup(
            "کنسولگری مرکزی",
            "کشور میزبان:",
            hosts,
            lambda value: self.popup(
                "کنسولگری مرکزی",
                f"محل کنسولگری مرکزی: {value}"
            )
        )


if __name__ == "__main__":
    Varnonami().run()
