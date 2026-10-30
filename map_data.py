
# -*- coding: utf-8 -*-
"""VARNONAMI map_data.py - نسخه آزمایشی ۱

داده‌های این فایل نمونه‌اند و هنوز نقشه کامل و دقیق تاریخی/جغرافیایی نیستند.
هر استان یک منطقه بازی با شناسه یکتا و فهرست همسایه‌هاست.
"""

from copy import deepcopy

SCENARIOS = {
    "ww1": {
        "title": "جنگ جهانی اول",
        "years": "۱۹۱۴ تا ۱۹۱۸",
        "countries": {
            "germany": {
                "name": "امپراتوری آلمان",
                "flag": "🇩🇪",
                "capital": "برلین",
                "provinces": [
                    {"id": "ger_1", "name": "برلین", "neighbors": ["ger_2", "fra_2"]},
                    {"id": "ger_2", "name": "باواریا", "neighbors": ["ger_1", "ger_3", "aus_1"]},
                    {"id": "ger_3", "name": "راینلند", "neighbors": ["ger_2", "fra_1"]}
                ]
            },
            "france": {
                "name": "فرانسه",
                "flag": "🇫🇷",
                "capital": "پاریس",
                "provinces": [
                    {"id": "fra_1", "name": "پاریس", "neighbors": ["fra_2", "ger_3"]},
                    {"id": "fra_2", "name": "نورماندی", "neighbors": ["fra_1", "fra_3", "ger_1"]},
                    {"id": "fra_3", "name": "پروانس", "neighbors": ["fra_2"]}
                ]
            },
            "britain": {
                "name": "امپراتوری بریتانیا",
                "flag": "🇬🇧",
                "capital": "لندن",
                "provinces": [
                    {"id": "gbr_1", "name": "لندن", "neighbors": ["gbr_2"]},
                    {"id": "gbr_2", "name": "اسکاتلند", "neighbors": ["gbr_1", "gbr_3"]},
                    {"id": "gbr_3", "name": "ولز", "neighbors": ["gbr_2"]}
                ]
            },
            "austria_hungary": {
                "name": "اتریش-مجارستان",
                "flag": "🇦🇹",
                "capital": "وین",
                "provinces": [
                    {"id": "aus_1", "name": "وین", "neighbors": ["aus_2", "ger_2"]},
                    {"id": "aus_2", "name": "بوداپست", "neighbors": ["aus_1", "aus_3"]},
                    {"id": "aus_3", "name": "بوهم", "neighbors": ["aus_2"]}
                ]
            },
            "ottoman": {
                "name": "امپراتوری عثمانی",
                "flag": "🇹🇷",
                "capital": "قسطنطنیه",
                "provinces": [
                    {"id": "ott_1", "name": "قسطنطنیه", "neighbors": ["ott_2"]},
                    {"id": "ott_2", "name": "آناتولی", "neighbors": ["ott_1", "ott_3"]},
                    {"id": "ott_3", "name": "شام", "neighbors": ["ott_2"]}
                ]
            }
        }
    },

    "ww2": {
        "title": "جنگ جهانی دوم",
        "years": "۱۹۳۹ تا ۱۹۴۵",
        "countries": {
            "germany": {
                "name": "آلمان",
                "flag": "🇩🇪",
                "capital": "برلین",
                "provinces": [
                    {"id": "ger_1", "name": "برلین", "neighbors": ["ger_2", "pol_1"]},
                    {"id": "ger_2", "name": "باواریا", "neighbors": ["ger_1", "ger_3"]},
                    {"id": "ger_3", "name": "راینلند", "neighbors": ["ger_2", "fra_1"]}
                ]
            },
            "poland": {
                "name": "لهستان",
                "flag": "🇵🇱",
                "capital": "ورشو",
                "provinces": [
                    {"id": "pol_1", "name": "ورشو", "neighbors": ["pol_2", "ger_1"]},
                    {"id": "pol_2", "name": "گدانسک", "neighbors": ["pol_1", "pol_3"]},
                    {"id": "pol_3", "name": "کراکوف", "neighbors": ["pol_2"]}
                ]
            },
            "france": {
                "name": "فرانسه",
                "flag": "🇫🇷",
                "capital": "پاریس",
                "provinces": [
                    {"id": "fra_1", "name": "پاریس", "neighbors": ["fra_2", "ger_3"]},
                    {"id": "fra_2", "name": "نورماندی", "neighbors": ["fra_1", "fra_3"]},
                    {"id": "fra_3", "name": "پروانس", "neighbors": ["fra_2"]}
                ]
            },
            "ussr": {
                "name": "اتحاد جماهیر شوروی",
                "flag": "☭",
                "capital": "مسکو",
                "provinces": [
                    {"id": "ussr_1", "name": "مسکو", "neighbors": ["ussr_2"]},
                    {"id": "ussr_2", "name": "لنینگراد", "neighbors": ["ussr_1", "ussr_3"]},
                    {"id": "ussr_3", "name": "کی‌یف", "neighbors": ["ussr_2"]}
                ]
            },
            "japan": {
                "name": "امپراتوری ژاپن",
                "flag": "🇯🇵",
                "capital": "توکیو",
                "provinces": [
                    {"id": "jpn_1", "name": "توکیو", "neighbors": ["jpn_2"]},
                    {"id": "jpn_2", "name": "اوساکا", "neighbors": ["jpn_1", "jpn_3"]},
                    {"id": "jpn_3", "name": "هوکایدو", "neighbors": ["jpn_2"]}
                ]
            }
        }
    },

    "modern": {
        "title": "جهانی سوم (سناریوی خیالی معاصر)",
        "years": "دوره معاصرِ خیالی",
        "countries": {
            "iran": {
                "name": "ایران",
                "flag": "🇮🇷",
                "capital": "تهران",
                "provinces": [
                    {"id": "ir_1", "name": "تهران", "neighbors": ["ir_2", "ir_3"]},
                    {"id": "ir_2", "name": "اصفهان", "neighbors": ["ir_1", "ir_3"]},
                    {"id": "ir_3", "name": "فارس", "neighbors": ["ir_1", "ir_2"]}
                ]
            },
            "usa": {
                "name": "ایالات متحده آمریکا",
                "flag": "🇺🇸",
                "capital": "واشینگتن دی‌سی",
                "provinces": [
                    {"id": "us_1", "name": "واشینگتن دی‌سی", "neighbors": ["us_2"]},
                    {"id": "us_2", "name": "نیویورک", "neighbors": ["us_1", "us_3"]},
                    {"id": "us_3", "name": "کالیفرنیا", "neighbors": ["us_2"]}
                ]
            },
            "france": {
                "name": "فرانسه",
                "flag": "🇫🇷",
                "capital": "پاریس",
                "provinces": [
                    {"id": "fr_1", "name": "پاریس", "neighbors": ["fr_2"]},
                    {"id": "fr_2", "name": "نورماندی", "neighbors": ["fr_1", "fr_3"]},
                    {"id": "fr_3", "name": "پروانس", "neighbors": ["fr_2"]}
                ]
            },
            "china": {
                "name": "چین",
                "flag": "🇨🇳",
                "capital": "پکن",
                "provinces": [
                    {"id": "cn_1", "name": "پکن", "neighbors": ["cn_2"]},
                    {"id": "cn_2", "name": "شانگهای", "neighbors": ["cn_1", "cn_3"]},
                    {"id": "cn_3", "name": "گوانگ‌دونگ", "neighbors": ["cn_2"]}
                ]
            },
            "palestine": {
                "name": "فلسطین",
                "flag": "🇵🇸",
                "capital": "قدس شرقی",
                "provinces": [
                    {"id": "ps_1", "name": "قدس", "neighbors": ["ps_2"]},
                    {"id": "ps_2", "name": "غزه", "neighbors": ["ps_1", "ps_3"]},
                    {"id": "ps_3", "name": "کرانه باختری", "neighbors": ["ps_2"]}
                ]
            }
        }
    }
}


def get_scenario(scenario_id):
    """اطلاعات یک سناریو را برمی‌گرداند."""
    if scenario_id not in SCENARIOS:
        raise KeyError(f"سناریو ناشناخته است: {scenario_id}")
    return deepcopy(SCENARIOS[scenario_id])


def list_scenarios():
    """فهرست سناریوها برای منوی انتخاب دوره."""
    return [
        {"id": key, "title": value["title"], "years": value["years"]}
        for key, value in SCENARIOS.items()
    ]


def list_countries(scenario_id):
    """کشورهای موجود در سناریوی انتخاب‌شده."""
    data = get_scenario(scenario_id)
    return [
        {
            "id": key,
            "name": value["name"],
            "flag": value["flag"],
            "capital": value["capital"]
        }
        for key, value in data["countries"].items()
    ]


def get_country(scenario_id, country_id):
    """اطلاعات یک کشور."""
    data = get_scenario(scenario_id)
    if country_id not in data["countries"]:
        raise KeyError(f"کشور در این سناریو وجود ندارد: {country_id}")
    return deepcopy(data["countries"][country_id])


def get_all_provinces(scenario_id):
    """تمام مناطق سناریو، همراه با مالک اولیه."""
    data = get_scenario(scenario_id)
    result = []

    for country_id, country in data["countries"].items():
        for province in country["provinces"]:
            item = deepcopy(province)
            item["owner"] = country_id
            result.append(item)

    return result


def get_province(scenario_id, province_id):
    """یک منطقه را با شناسه پیدا می‌کند."""
    for province in get_all_provinces(scenario_id):
        if province["id"] == province_id:
            return province

    raise KeyError(f"منطقه پیدا نشد: {province_id}")


def are_adjacent(scenario_id, province_a, province_b):
    """بررسی همسایگی دو منطقه برای حرکت زمینی."""
    a = get_province(scenario_id, province_a)
    b = get_province(scenario_id, province_b)

    return (
        province_b in a["neighbors"]
        or province_a in b["neighbors"]
    )


def validate_map_data():
    """خطاهای شناسه تکراری یا همسایه ناموجود را گزارش می‌کند."""
    errors = []

    for scenario_id, scenario in SCENARIOS.items():
        ids = [
            province["id"]
            for country in scenario["countries"].values()
            for province in country["provinces"]
        ]

        known = set(ids)

        if len(ids) != len(known):
            errors.append(f"{scenario_id}: شناسه استان تکراری است")

        for country in scenario["countries"].values():
            for province in country["provinces"]:
                for neighbor in province["neighbors"]:
                    if neighbor not in known:
                        errors.append(
                            f"{scenario_id}: همسایه {neighbor} برای "
                            f"{province['id']} تعریف نشده است"
                        )

    return errors


if __name__ == "__main__":
    print("سناریوهای VARNONAMI:")

    for item in list_scenarios():
        print(f"- {item['id']}: {item['title']} ({item['years']})")

    problems = validate_map_data()

    if not problems:
        print("بررسی داده‌ها: بدون خطا")
    else:
        print("خطاهای داده نقشه:")
        for problem in problems:
            print("-", problem)
