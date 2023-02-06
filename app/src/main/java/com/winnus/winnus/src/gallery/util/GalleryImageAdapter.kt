package com.winnus.winnus.src.gallery.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.winnus.winnus.R
import com.winnus.winnus.src.gallery.GalleryActivityView
import com.winnus.winnus.src.gallery.model.GalleryImage

class GalleryImageAdapter(private val context: Context, private val imageList: ArrayList<GalleryImage>,
                          private val galleryActivityView: GalleryActivityView) : RecyclerView.Adapter<GalleryImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_gallery_image,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imageList[position]
        Log.d("test",currentImage.imagePath.toString())
        Glide.with(context)
            .load(currentImage.imagePath)
            .error(R.mipmap.ic_launcher)
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)

        holder.image!!.setOnClickListener {
            galleryActivityView.changeImg(currentImage.imagePath.toString())
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var image : ImageView? = null

        init {
            image = itemView.findViewById(R.id.image_gallery)
        }

    }
}