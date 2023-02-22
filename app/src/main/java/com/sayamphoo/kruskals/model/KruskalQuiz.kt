package com.sayamphoo.kruskals.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
class KruskalQuiz(
    @SerializedName("id")
    val id: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("node")
    val node: String,
    @SerializedName("minimum_spanning_tree")
    val minimum_spanning_tree: String,
    @SerializedName("branch")
    val branch: List<String>,
    @SerializedName("mst_branch")
    val mst_branch: List<String>,
    @SerializedName("many_events")
    val many_events: Boolean
) : Parcelable
