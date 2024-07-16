package ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import model.Card
import kotlin.coroutines.CoroutineContext

class FavViewmodel: ViewModel() {
    private val _TAG = "API-CHECK"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("card")
    private val scope = CoroutineScope(coroutineContext)
    var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData<ArrayList<Card>>()

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val mail = auth.currentUser?.uid ?: ""

    fun init(context: Context) {
        fetchFavorites()
    }

    private fun fetchFavorites() {
        scope.launch {
            firestore.collection("users")
                .document(mail)
                .collection("fav_cards")
                .get()
                .addOnSuccessListener { documents ->
                    val favoriteCards = ArrayList<Card>()
                    for (document in documents) {
                        document.toObject(Card::class.java)?.let { card ->
                            favoriteCards.add(card)
                        }
                    }
                    cards.postValue(favoriteCards)
                }
                .addOnFailureListener { exception ->
                    Log.e(_TAG, "Error fetching favorite cards", exception)
                }
        }
    }
}
