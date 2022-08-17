package com.redemption.adey.Model

data class ItemViewModel(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
) {
    data class Item(
        val etag: String,
        val id: String,
        val kind: String,
        val snippet: Snippet
    ) {
        data class Snippet(
            val channelId: String,
            val channelTitle: String,
            val description: String,
            val playlistId: String,
            val position: Int,
            val publishedAt: String,
            val resourceId: ResourceId,
            val thumbnails: Thumbnails,
            val title: String,
            val videoOwnerChannelId: String,
            val videoOwnerChannelTitle: String
        ) {
            data class ResourceId(
                val kind: String,
                val videoId: String
            )

            data class Thumbnails(
                val default: Default,
                val high: High,
                val medium: Medium
            ) {
                data class Default(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                data class High(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                data class Medium(
                    val height: Int,
                    val url: String,
                    val width: Int
                )
            }
        }
    }

    data class PageInfo(
        val resultsPerPage: Int,
        val totalResults: Int
    )
}