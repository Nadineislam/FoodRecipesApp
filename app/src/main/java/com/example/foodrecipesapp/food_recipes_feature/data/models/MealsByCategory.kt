package com.example.foodrecipesapp.food_recipes_feature.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class MealsByCategory(
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMeal)
        parcel.writeString(strMeal)
        parcel.writeString(strMealThumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MealsByCategory> {
        override fun createFromParcel(parcel: Parcel): MealsByCategory {
            return MealsByCategory(parcel)
        }

        override fun newArray(size: Int): Array<MealsByCategory?> {
            return arrayOfNulls(size)
        }
    }
}