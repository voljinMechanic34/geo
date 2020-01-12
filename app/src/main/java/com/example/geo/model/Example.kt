package com.example.geo.model

import com.google.gson.annotations.SerializedName

data class Example (
    @SerializedName("services") var services : List<String>,
    @SerializedName("pins") var pins : List<Pin>
)