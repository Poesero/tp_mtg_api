package com.example.tp_mtg_api.model

data class Card (
        var obj: String? = null,
        var mana_cost: String? = null,
        var name: String? = null,
        var power: String? = null,
        var toughness: String? = null,
        var type_line: String? = null,
        var oracle_text: String? = null
)

