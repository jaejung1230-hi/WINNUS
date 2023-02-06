package com.winnus.winnus.src.gallery

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.winnus.winnus.R
import com.winnus.winnus.databinding.ActivityGalleryBinding
import com.winnus.winnus.src.gallery.model.GalleryImage
import com.winnus.winnus.src.gallery.util.GalleryImageAdapter

class GalleryActivity : com.winnus.winnus.config.BaseActivity<ActivityGalleryBinding>(ActivityGalleryBinding::inflate),GalleryActivityView {
    private var allPic:ArrayList<GalleryImage>? = null
    lateinit var currentImageUri : String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choice, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.menu_choice -> {
                val intent = Intent()
                if(this::currentImageUri.isInitialized){
                    intent.putExtra("currentImageUri",currentImageUri)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
            android.R.id.home ->{finish()}
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.recyclerGallery.layoutManager = GridLayoutManager(this,4)
        binding.recyclerGallery.setHasFixedSize(true)

        allPic = ArrayList()
        if(allPic!!.isEmpty()){
            binding.recyclerProgress.visibility = View.VISIBLE
            allPic = getAllImages()
            binding.recyclerGallery.adapter = GalleryImageAdapter(this, allPic!!, this)
            binding.recyclerProgress?.visibility = View.GONE
        }
    }

    private fun getAllImages() : ArrayList<GalleryImage>? {
        val images = ArrayList<GalleryImage>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor = this@GalleryActivity.contentResolver.query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = GalleryImage()
                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)

            }while (cursor.moveToNext())
            cursor.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return images
    }

    override fun changeImg(uri: String) {
        currentImageUri = uri
        Glide.with(this)
            .load(uri)
            .error(R.mipmap.ic_launcher)
            .apply(RequestOptions().centerCrop())
            .into(binding.imgSelectedItem)
    }
}