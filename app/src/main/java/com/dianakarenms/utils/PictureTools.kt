package com.dianakarenms.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat


class PictureTools {
    private var context: Context? = null

    private fun setContext(context: Context) {
        this.context = context
    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 1707

        private var instance: PictureTools? = null

        @Synchronized
        private fun createInstance() {
            if (instance == null) {
                instance = PictureTools()
            }
        }

        fun getInstance(): PictureTools? {
            if (instance == null) {
                createInstance()
            }
            return instance
        }

        fun with(context: Context): PictureTools? {
            getInstance()!!.setContext(context)
            return getInstance()
        }

        fun decodeSampledBitmapFromUri(dir: String, Width: Int, Height: Int): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true // si la imagen la solicitan cuadrada esto quita el restante del rectángulo de la foto
                BitmapFactory.decodeFile(dir, options)

                options.inSampleSize = calculateInSampleSize(options, Width, Height) // resize acotado dentro del área mantieniendo el aspect ratio
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeFile(dir, options)
            } catch (e: Exception) {
                return null
            }

            return bitmap
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, Width: Int, Height: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var sizeInitialize = 1

            if (height > Height || width > Width) {
                sizeInitialize = if (width > height) {
                    Math.round(height.toFloat() / Height.toFloat())
                } else {
                    Math.round(width.toFloat() / Width.toFloat())
                }
            }
            return sizeInitialize
        }

        fun permissionReadMemory(context: Activity): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), REQUEST_READ_EXTERNAL_STORAGE)
                        false
                    } else {
                        //No explanation needed, we can request the permissions.
                        ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), REQUEST_READ_EXTERNAL_STORAGE)
                        false
                    }
                } else {
                    true
                }
            } else {
                return true
            }
        }
    }
}