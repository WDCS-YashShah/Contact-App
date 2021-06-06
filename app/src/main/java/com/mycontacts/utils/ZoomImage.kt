package com.mycontacts.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

object ZoomImage {

    fun zoomImageFromThumb(thumbView: View, imageRes: Drawable,parentLayout:FrameLayout) {

        val expandedImageView = ImageView(thumbView.context)
        val params = LinearLayout.LayoutParams(-1, -1)
        expandedImageView.layoutParams = params

        expandedImageView.setImageDrawable(imageRes)
        expandedImageView.setBackgroundColor(Color.TRANSPARENT)
        expandedImageView.visibility = View.GONE

        parentLayout.addView(expandedImageView)

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()


        thumbView.getGlobalVisibleRect(startBoundsInt)
        parentLayout.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {

            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }


        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        var currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left)).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = 500
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {

                }

                override fun onAnimationCancel(animation: Animator) {

                }
            })
            start()
        }

        expandedImageView.setOnClickListener {
            currentAnimator.cancel()

            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = 500
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                    }
                })
                start()
            }
        }
    }

}