package com.dianakarenms.dogslisttd

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.dianakarenms.models.Dog
import com.dianakarenms.utils.PictureTools
import com.dianakarenms.utils.PictureTools.Companion.REQUEST_READ_EXTERNAL_STORAGE
import com.dianakarenms.utils.RealPathUtil
import kotlinx.android.synthetic.main.activity_add_new_dog.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast


class AddNewDogActivity : AppCompatActivity() {

    private var pathImage: String? = null
    private val REQUEST_PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_dog)

        setSupportActionBar(toolbar)
        toolbar.title = title

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        add_new_dog_photo.onClick {
            if (PictureTools.permissionReadMemory(this@AddNewDogActivity)) {
                chooseImageFromGallery()
            }
        }

        addNewDogSaveBtn.onClick {
            if (add_new_dog_name_edit.text.isNullOrEmpty()) {
                add_new_dog_name_edit.error = resources.getString(R.string.add_new_dog_empty_name)
                return@onClick
            } else if (pathImage.isNullOrEmpty()) {
                toast("Pick an image")
                return@onClick
            }

            val dog = Dog(add_new_dog_name_edit.text.toString(), pathImage ?: "")
            val returnIntent = Intent()
            returnIntent.putExtra("dog", dog)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, DogListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_PICK_IMAGE) {
            pathImage = getRealPathFromUri(data)
            setBitmapFromUri(pathImage!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults[1] == 0) {
            when (requestCode) {
                REQUEST_READ_EXTERNAL_STORAGE -> chooseImageFromGallery()
            }
        }
    }

    private fun setBitmapFromUri(pathImage: String) {
        if (pathImage.isNotEmpty()) {
            val bitmap = PictureTools.decodeSampledBitmapFromUri(pathImage, 500, 500)
            add_new_dog_photo.setImageBitmap(bitmap)
        } else {
            add_new_dog_photo.setImageResource(R.drawable.ic_pick_photo)
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Elige una imagen"), REQUEST_PICK_IMAGE)
    }

    private fun getRealPathFromUri(data: Intent?) : String {
        var path = ""
        data?.data!!.let { data ->
            path = when {
                Build.VERSION.SDK_INT < 11 -> RealPathUtil.getRealPathFromURI_BelowAPI11(this, data)
                Build.VERSION.SDK_INT < 19 -> RealPathUtil.getRealPathFromURI_API11to18(this, data)!!
                else -> RealPathUtil.getRealPathFromURI_API19(this, data)
            }
        }
        return path
    }
}