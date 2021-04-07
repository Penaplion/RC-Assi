package utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

/*
https://developers.google.com/ml-kit/vision/text-recognition/android
https://github.com/googlesamples/mlkit/blob/1e79fb6ba939b5dc8ce620a791cd9d5556f34e6f/android/android-snippets/app/src/main/java/com/google/example/mlkit/kotlin/TextRecognitionActivity.kt#L30-L30

 */

class ReceiptAnalyser : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Pass image to an ML Kit Vision API
            // ...

            // get an instance of TextRecognizer
            val recognizer = TextRecognition.getClient()
            // image processing
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    // Task completed successfully
                    val resultText = visionText.text
                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        val blockCornerPoints = block.cornerPoints
                        val blockFrame = block.boundingBox
                        for (line in block.lines) {
                            val lineText = line.text
                            val lineCornerPoints = line.cornerPoints
                            val lineFrame = line.boundingBox
                            for (element in line.elements) {
                                val elementText = element.text
                                val elementCornerPoints = element.cornerPoints
                                val elementFrame = element.boundingBox
                            }
                        }
                    }

                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Log.e(TAG, "Photo capture failed: ${e.message}", e)
                }
        }
        imageProxy.close()
    }

    companion object {
        private const val TAG = "CameraXBasic"
    }
}