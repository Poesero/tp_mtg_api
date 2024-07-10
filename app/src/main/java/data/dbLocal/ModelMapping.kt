package data.dbLocal

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import model.Card



fun CardLocal.toCard() = Card(
    obj ?: "",
    "",
    name,
    "",
    "",
    type_line ?: "",
    oracle_text ?: "",

)

fun List<CardLocal>.toCardList() =map(CardLocal::toCard)

fun Card.toCardLocal() = CardLocal(
    obj ?: "",
    name,
    type_line ?:"",
    oracle_text ?:"",

)

fun List<Card>.toCardLocalList() = map(Card::toCardLocal)