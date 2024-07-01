package data.dbLocal

import model.Card


fun CardLocal.toCard() = Card(
    obj,
    mana_cost ?: "",
    name,
    power ?: "",
    toughness ?: "",
    type_line ?: "",
    oracle_text ?: "",
    image_uris
)

fun List<CardLocal>.toCardList() =map(CardLocal::toCard)

fun Card.toCardLocal() = CardLocal(
    "",
    ""
)