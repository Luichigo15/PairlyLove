package app.luichigo15.pairly.data.api.response.puzzle

import com.google.gson.annotations.SerializedName

data class PLUploadPuzzleResponse(
    @SerializedName("public_id") val publicId: String,
    @SerializedName("url") val url: String,
)
