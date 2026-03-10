package app.luichigo15.pairly.utils.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import app.luichigo15.pairly.ui.home.girl.screen.puzzle.selected.model.PLPuzzlePiece
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlin.random.Random
import androidx.core.graphics.scale

const val DEFAULT_COLS_ROWS = 4
object PLPuzzleUtils {

    suspend fun generatePuzzle(
        context: Context, imageUrl: String,
        maxWidth: Int,
        maxHeight: Int,
        rows: Int = DEFAULT_COLS_ROWS,
        cols: Int = DEFAULT_COLS_ROWS
    ): List<PLPuzzlePiece> {
        val bitmap = loadBitmap(context, imageUrl)
        val resizedBitmap = resizeBitmapToFit(bitmap, maxWidth, maxHeight)
        val pieces = splitBitmap(resizedBitmap, rows, cols)
        return createPuzzlePieces(pieces, rows, cols)
    }

    private suspend fun loadBitmap(context: Context, url: String): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as SuccessResult)
        return (result.drawable as BitmapDrawable).bitmap
    }

    private fun resizeBitmapToFit(
        bitmap: Bitmap,
        maxWidth: Int,
        maxHeight: Int
    ): Bitmap {

        val ratio = minOf(
            maxWidth.toFloat() / bitmap.width,
            maxHeight.toFloat() / bitmap.height
        )

        val width = (bitmap.width * ratio).toInt()
        val height = (bitmap.height * ratio).toInt()

        return bitmap.scale(width, height)
    }

    private fun splitBitmap(
        bitmap: Bitmap, rows: Int, cols: Int
    ): List<Bitmap> {
        val pieceWidth = bitmap.width / cols
        val pieceHeight = bitmap.height / rows

        val pieces = mutableListOf<Bitmap>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val piece = Bitmap.createBitmap(
                    bitmap,
                    col * pieceWidth,
                    row * pieceHeight,
                    pieceWidth,
                    pieceHeight
                )

                pieces.add(piece)
            }
        }

        return pieces
    }

    private fun createPuzzlePieces(
        bitmaps: List<Bitmap>, rows: Int, cols: Int
    ): List<PLPuzzlePiece> {
        val pieceWidth = bitmaps.first().width.toFloat()
        val pieceHeight = bitmaps.first().height.toFloat()

        return bitmaps.mapIndexed { index, bmp ->
            val row = index / rows
            val col = index % cols

            PLPuzzlePiece(
                id = index,
                correctRow = row,
                correctCol = col,
                bitmap = bmp.asImageBitmap(),
                width = pieceWidth,
                height = pieceHeight,
                offset = Offset(
                    Random.nextFloat() * 300f,
                    Random.nextFloat() * 300f
                )
            )
        }
    }
}