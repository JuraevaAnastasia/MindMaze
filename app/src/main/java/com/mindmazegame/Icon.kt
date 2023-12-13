package com.mindmazegame

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView

class Icon(val icon: ImageView) {
    private var side: ButtonSide = ButtonSide.BACK
    private var front: Int = 0
    private val back: Int = R.drawable.button

    enum class ButtonSide { FRONT, BACK }

    init {
        initialState()
    }

    fun initialState() {
        icon.setImageResource(back)
        side = ButtonSide.BACK
    }

    fun getFront(): Int {
        return front
    }

    fun setFront(imageResource: Int) {
        front = imageResource
    }

    fun flip() {
        val newImage: Int
        if (isButtonFlipped()) {
            newImage = back
            icon.isClickable = true
        } else {
            newImage = front
            icon.isClickable = false
        }
        startAnimation(newImage)
    }

    fun clickable(clickState: Boolean) {
        icon.isClickable = clickState
    }

    fun isButtonFlipped(): Boolean {
        return side == ButtonSide.FRONT
    }

    private fun flipButtonStatus() {
        side = if (side == ButtonSide.FRONT) ButtonSide.BACK
        else ButtonSide.FRONT
    }

    private fun startAnimation(newImage: Int) {
        val objectAnimator1 = ObjectAnimator.ofFloat(icon, "scaleX", 1f, 0f)
        val objectAnimator2 = ObjectAnimator.ofFloat(icon, "scaleX", 0f, 1f)
        objectAnimator1.interpolator = DecelerateInterpolator()
        objectAnimator2.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                icon.setImageResource(newImage)
                objectAnimator2.start()
            }
        })
        objectAnimator2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                flipButtonStatus()
            }
        })
        objectAnimator1.start()
    }
}
