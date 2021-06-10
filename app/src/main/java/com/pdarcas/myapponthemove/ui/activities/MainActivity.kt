package com.pdarcas.myapponthemove.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.storage.FirebaseStorage
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.MainActivityBinding
import com.pdarcas.myapponthemove.ui.activities.authentication.AuthenticationActivity
import com.pdarcas.myapponthemove.ui.fragments.home.ModalBottomSheetFragmentMenu
import com.pdarcas.myapponthemove.utils.activityViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private val _binding by activityViewBinding(MainActivityBinding::inflate)
    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding.fab.setOnClickListener {

            val bottomSheetFragmentMenu: ModalBottomSheetFragmentMenu =
                ModalBottomSheetFragmentMenu.getInstance()
            bottomSheetFragmentMenu.show(supportFragmentManager, ModalBottomSheetFragmentMenu.TAG)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(_binding.navView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        _binding.bottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        _binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            when (menuItem.itemId) {
                R.id.logout -> mainActivityViewModel.signOut()
                    .let {
                        val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
            }
            menuItem.isChecked = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            true
        }

        _binding.navView.getHeaderView(0).setOnClickListener {
            takePictureIntent()
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child(
                "pics/${mainActivityViewModel.getCurrentUser()?.uid}"
            )
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val image = byteArrayOutputStream.toByteArray()
        val upload = storageRef.putBytes(image)
        upload.addOnCompleteListener { uploadTask ->
            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        val profileImage: ImageView = findViewById(R.id.ivProfilePhoto)
                        profileImage.setImageBitmap(bitmap)
                    }
                }
            } else {
                uploadTask.exception?.let {
                    Toast.makeText(this@MainActivity, it.message!!, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}