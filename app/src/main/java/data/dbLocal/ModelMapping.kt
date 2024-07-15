package data.dbLocal

import model.Card
import model.CardImages

fun CardLocal.toCard() = Card(
    obj ?: "",
    "",
    name,
    "",
    "",
    type_line ?: "",
    oracle_text ?: "",
    image_uri?.let { CardImages(it) }
)

fun List<CardLocal>.toCardList() = map(CardLocal::toCard)

fun Card.toCardLocal() = CardLocal(
    obj ?: "",
    name,
    type_line ?: "",
    oracle_text ?: "",
    image_uris?.png
)

fun List<Card>.toCardLocalList() = map(Card::toCardLocal)
