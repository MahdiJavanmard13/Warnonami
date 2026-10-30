
[app]

title = وارنونامی
package.name = varnonami
package.domain = org.varnonami

source.dir = .
source.include_exts = py,png,jpg,jpeg,kv,atlas,ttf,json,txt

version = 0.1.0

requirements = python3,kivy,arabic-reshaper,python-bidi

orientation = landscape
fullscreen = 0

[buildozer]

log_level = 2
warn_on_root = 1

[android]

android.api = 35
android.minapi = 23
android.archs = arm64-v8a
