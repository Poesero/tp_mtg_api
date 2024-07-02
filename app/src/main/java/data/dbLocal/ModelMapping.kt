package data.dbLocal

import model.Card
import model.CardImages


fun CardLocal.toCard() = Card(
    obj,
    "",
    name,
    "",
    "",
    type_line,
    oracle_text,
    image_uris = null
)

fun List<CardLocal>.toCardList() =map(CardLocal::toCard)

fun Card.toCardLocal() = CardLocal(
    obj ?: "",
    name,
    type_line,
    oracle_text,
    image_uris ?: CardImages("")
)

fun List<Card>.toCardLocalList() = map(Card::toCardLocal)