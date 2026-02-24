package app.luichigo15.pairly.data.api.response.puzzle

import app.luichigo15.pairly.domain.model.PLPuzzle
import com.google.gson.annotations.SerializedName

data class PLUploadPuzzleResponse(
    @SerializedName("public_id") val publicId: String,
    @SerializedName("secure_url") val url: String,
) {
    fun toDomain(name: String) = PLPuzzle(
        id = publicId,
        imageUrl = url,
        name = name
    )
}
