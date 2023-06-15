package com.id.etourism.data.local.dummy

import com.id.etourism.data.network.model.Wisata
import kotlin.random.Random

object DummyData {
    fun generateDummy(): List<Wisata> {
        val data: MutableList<Wisata> = mutableListOf()
        for (
        item in 1..19

        ) {
            val randomRating = Random.nextDouble(0.0, 5.0)
            data.add(
                Wisata(
                    Random.nextLong(), "Place $item",
                    "Description $item",
                    "Category $item",
                    "City $item",
                    null,
                    randomRating,
                    "",
                    "",
                    null,
                    null

                )


            )

        }
        return data
    }
}
