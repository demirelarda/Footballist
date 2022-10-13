package com.mycompany.footballist.model.teams

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(
    var address: String = "",
    var capacity: Int = 0,
    var city: String= "",
    var id: Int=0,
    var image: String= "",
    var name: String= "",
    var surface: String= ""
):Parcelable