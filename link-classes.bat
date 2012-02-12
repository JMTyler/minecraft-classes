@echo off

pushd .

set source_path="C:\Users\jtyler\Documents\Programming\Java\Minecraft\Classes"
set dest_path=".\src\minecraft\net\minecraft\src"


REM remove old links or files
del /s /q  %dest_path%\EnumArmorType.java
del /s /q  %dest_path%\ItemClassArmor.java
del /s /q  %dest_path%\ItemClassWeapon.java
del /s /q  %dest_path%\mod_Classes.java


REM rebuild links
mklink  %dest_path%\EnumArmorType.java  %source_path%\EnumArmorType.java
mklink  %dest_path%\ItemClassArmor.java  %source_path%\ItemClassArmor.java
mklink  %dest_path%\ItemClassWeapon.java  %source_path%\ItemClassWeapon.java
mklink  %dest_path%\mod_Classes.java  %source_path%\mod_Classes.java


popd

pause
