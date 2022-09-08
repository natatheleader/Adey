package com.redemption.derama.Model


import com.google.gson.annotations.SerializedName

data class ItemViewModel(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
) {
    data class Item(
        @SerializedName("etag")
        val etag: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("snippet")
        val snippet: Snippet
    ) {
        data class Snippet(
            @SerializedName("channelId")
            val channelId: String,
            @SerializedName("channelTitle")
            val channelTitle: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("playlistId")
            val playlistId: String,
            @SerializedName("position")
            val position: Int,
            @SerializedName("publishedAt")
            val publishedAt: String,
            @SerializedName("resourceId")
            val resourceId: ResourceId,
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails,
            @SerializedName("title")
            val title: String,
            @SerializedName("videoOwnerChannelId")
            val videoOwnerChannelId: String,
            @SerializedName("videoOwnerChannelTitle")
            val videoOwnerChannelTitle: String
        ) {
            data class ResourceId(
                @SerializedName("kind")
                val kind: String,
                @SerializedName("videoId")
                val videoId: String
            )

            data class Thumbnails(
                @SerializedName("default")
                val default: Default,
                @SerializedName("high")
                val high: High,
                @SerializedName("medium")
                val medium: Medium
            ) {
                data class Default(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )

                data class High(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )

                data class Medium(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )
            }
        }
    }

    data class PageInfo(
        @SerializedName("resultsPerPage")
        val resultsPerPage: Int,
        @SerializedName("totalResults")
        val totalResults: Int
    )
}