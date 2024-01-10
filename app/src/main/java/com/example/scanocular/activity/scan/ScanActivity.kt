package com.example.scanocular.activity.scan

// Import necessary packages
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.scanocular.api.scan.ScanAPI
import com.example.scanocular.databinding.ActivityScanBinding
import com.example.scanocular.model.scan.ScanResponse
import com.example.scanocular.model.scan.ScanUploadRequest
import com.example.scanocular.util.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: ActivityScanBinding
    private lateinit var imageCapture: ImageCapture
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cameraExecutor = Executors.newSingleThreadExecutor()

        showCustomDialog()

        startCamera()

        // Set up click listener for the "Take Picture" button
        binding.btnCapture.setOnClickListener {
            takePicture()
        }
    }

    private fun showCustomDialog() {
        val customDialog = ScanTOSDialog(this)
        customDialog.show()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setTargetResolution(android.util.Size(1080, 2098)) // Set the target resolution here
                .build()


            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                // Handle exceptions
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture() {

        binding.cardViewprogress.visibility = View.VISIBLE

        val imageCapture = imageCapture ?: return

        val photoFile = File(externalMediaDirs.firstOrNull(), "${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // Image saved successfully
                    imageFile = photoFile
                    convertBase64toString()
                }

                override fun onError(exc: ImageCaptureException) {
                    // Handle error
                }
            }
        )
    }

    private fun convertBase64toString() {
        val bitmap = imageFile?.let {
            BitmapFactory.decodeFile(it.absolutePath)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

        // Pass the base64Image to the next activity
        ScanImageToServer(base64Image.toString())
//        Log.d("images", base64Image)
        val maxLength = 100
        Log.d("images", base64Image.take(maxLength))



    }



    private fun ScanImageToServer(base64Image: String) {
        try {
            val apiService = RetrofitClient.instance.create(ScanAPI::class.java)

            val request = ScanUploadRequest(user_id = 4, img = base64Image)

            val call = apiService.uploadImage(request)


            call.enqueue(object : Callback<ScanResponse> {
                override fun onResponse(call: Call<ScanResponse>, response: Response<ScanResponse>) {
                    // Handle successful response
                    if (response.isSuccessful) {
                        val diagnoseResponse = response.body()
                        if (diagnoseResponse != null) {
                            // Access the diagnosa value
                            binding.cardViewprogress.visibility = View.GONE
                            val diagnosa = diagnoseResponse.diagnosa

                            // Create an Intent
                            val intent = Intent(this@ScanActivity, ScanResultActivity::class.java)
                            intent.putExtra("diagnosa", diagnosa)
                            startActivity(intent)

                        } else {
                            // Handle null response body
                            binding.cardViewprogress.visibility = View.GONE
                        }
                    } else {
                        // Handle unsuccessful response (non-2xx status codes)
                        binding.cardViewprogress.visibility = View.GONE
                        Toast.makeText(this@ScanActivity, "Eye not detected", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                    // Handle network error or other failures
//                    Toast.makeText(this@ScanActivity, "Failure", Toast.LENGTH_SHORT).show()
//                    println("API Call Failed: ${t.message}")
                    Toast.makeText(this@ScanActivity, "Error!", Toast.LENGTH_SHORT).show()
                    binding.cardViewprogress.visibility = View.GONE
                    t.printStackTrace()
                }
            })
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("ImageUploader", "Exception: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun decodeBase64(base64Image: String): String {
        val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
        return String(decodedBytes)
    }




    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
