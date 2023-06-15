package com.id.etourism.data.network.repository.wisata

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.utils.ExceptionState
import com.id.etourism.utils.FireStoreTable

class WisataRepositoryImpl(
    private val database: FirebaseFirestore
) : WisataRepository {

    override fun getWisata(result: (ExceptionState<List<Wisata>>) -> Unit) {
        database.collection(FireStoreTable.TEST)
            .get()
            .addOnSuccessListener {
                val wisata = ArrayList<Wisata>()
                for (document in it) {
                    val data = document.toObject(Wisata::class.java)
                    wisata.add(data)
                }
                result.invoke(
                    ExceptionState.Success(wisata)
                )
            }
            .addOnFailureListener{
                result.invoke(
                    ExceptionState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

}