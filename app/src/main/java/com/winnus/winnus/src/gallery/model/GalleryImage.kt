package com.winnus.winnus.src.gallery.model

class GalleryImage {
    var imagePath:String? = null
    var imageName:String? = null
    constructor(imagePath:String?, imageName:String?){
        this.imagePath = imagePath
        this.imageName = imageName
    }
    constructor(){}
}