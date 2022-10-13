package mx.com.nu.challenge_shortener.data.model

import com.google.gson.annotations.SerializedName

class UrlResponse(
    @SerializedName("alias") var alias: String? = null,
    @SerializedName("_links") var links: Links? = null
)


class Links(
    @SerializedName("self") var self: String? = null,
    @SerializedName("short") var short: String? = null
)