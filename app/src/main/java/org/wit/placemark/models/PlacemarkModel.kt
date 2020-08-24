package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun delete(placemark: PlacemarkModel)
}
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
@Parcelize
data class PlacemarkModel(var id: Long = 0,
                          var title: String = "",
                          var address: String = "",
                          var website: String = "",
                          var phone: String = "",
                          var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

