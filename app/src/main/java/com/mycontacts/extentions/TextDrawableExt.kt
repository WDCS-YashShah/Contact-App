package com.mycontacts.extentions

import android.media.Image
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.TextDrawable.builder
import com.amulyakhare.textdrawable.util.ColorGenerator

fun ImageView.buildRoundDrawable(name:String){
    val generator: ColorGenerator = ColorGenerator.MATERIAL
    val color = generator.randomColor
    val drawable= builder().beginConfig().width(100).height(100).endConfig().buildRound(name.substring(0, 1), color)
    this.setImageDrawable(drawable)
}