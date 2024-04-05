package com.example.tp_mtg_api.model;
public class Card {
    private String name;
    private String manaValue;
    private String description;

    public Card() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaValue() {
        return manaValue;
    }

    public void setManaValue(String manaValue) {
        this.manaValue = manaValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
