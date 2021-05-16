package com.example.parsing

import Parser.Companion.logTag
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import data_model.Category

class FirestoreWriter() {

    var database: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun write(category: Category) {
        for (subcategory in category.subcategoryList) {
            val linkOfSubcategory = database.collection(category.categoryName).document(subcategory.subCategoryName)
                .collection(subcategory.subCategoryName)

            for (product in subcategory.productList) {
                val productToWrite = hashMapOf(
                    "ingredients" to product.ingredients
                )
                linkOfSubcategory.document(product.name).set(productToWrite)
                    .addOnSuccessListener {
                        Log.i(logTag, "Writing product \"${product.name}\" was successful!")
                    }
                    .addOnFailureListener {
                        Log.e(logTag, "Writing product \"${product.name}\" was failure!")
                    }
            }
        }
        Log.i(logTag, "Writing completed.")
    }
}