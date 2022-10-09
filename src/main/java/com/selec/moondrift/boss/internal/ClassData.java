package com.selec.moondrift.boss.internal;

import lombok.Getter;

@Getter
public enum ClassData {

    Barda("Bard", "Support"),
    Paladin("Paladin", "Support"),
    Berserker("Berserker", "DPS"),
    Destroyer("Destroyer", "DPS"),
    Gunlancer("Gunlancer", "DPS"),
    Shadowhunter("Shadowhunter", "DPS"),
    Deathblade("Deathblade", "DPS"),
    Wardancer("Wardancer", "DPS"),
    Striker("Striker", "DPS"),
    Soulfist("Soulfist", "DPS"),
    Scrapper("Scrapper", "DPS"),
    Lancemaster("Glaivier", "DPS"),
    Arcana("Arcana", "DPS"),
    Sorceress("Sorceress", "DPS"),
    Gunslinger("Gunslinger", "DPS"),
    Sharpshooter("Hawkeye", "DPS"),
    Artillerist("Artillerist", "DPS"),
    Deadeye("Deadeye", "DPS");

    String name;
    String type;

    ClassData(String name, String type) {
        this.name = name;
        this.type = type;
    }

}
