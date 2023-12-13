package com.mindmazegame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class PlantsActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private val buttonList = mutableListOf<Icon>()
    private val allImageList = mutableListOf(
        R.drawable.strawberry,
        R.drawable.onion,
        R.drawable.corn,
        R.drawable.broccoli,
        R.drawable.berry,
        R.drawable.carrot,
        R.drawable.banana,
        R.drawable.eggplant,
        R.drawable.pumpkin

    )
    private var previouslySelected: Icon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants)
        setUpResources()
        startButton = findViewById(R.id.start)
        startButton.setOnClickListener { startButton() }
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener { navigateBack() }
    }

    private fun startButton() {
        startButton.isClickable = false
        startButton.visibility = View.GONE
        setUpMatches()
    }
    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Закрываем текущую активность
    }

    private fun setUpMatches() {
        val countOfImagesToUse = 6
        val imageList = getRandomImages(countOfImagesToUse)

        val imagePattern = LinkedList<Int>().apply {
            for (i in 0 until imageList.size) {
                add(i)
                add(i) // каждое изображение должно использоваться дважды
            }
        }

        val buttonPattern = LinkedList<Int>().apply {
            for (i in 0 until buttonList.size) add(i)
            shuffle()
        }

        for (i in 0 until imageList.size * 2) {
            val image = imageList[imagePattern[i]]
            buttonList[buttonPattern[i]].setFront(image)
        }

        buttonList.forEach {
            it.flip()
            it.clickable(true)
        }

        Handler().postDelayed({
            buttonList.forEach { it.flip() }
        }, 3000)
    }
    private fun getRandomImages(count: Int): List<Int> {
        if (count >= allImageList.size) {
            return allImageList.toList()
        } else {
            val shuffledImages = allImageList.shuffled()
            return shuffledImages.subList(0, count)
        }
    }


    private fun setUpResources() {
        val buttons = arrayOf(
            R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9,
            R.id.button10, R.id.button11, R.id.button12
        )

        for (buttonId in buttons) {
            val button = findViewById<ImageView>(buttonId)
            val icon = Icon(button)
            icon.icon.setOnClickListener { buttonClick(icon) }
            icon.clickable(false)
            buttonList.add(icon)
        }
    }

    private fun buttonClick(icon: Icon) {
        icon.flip()

        Handler().postDelayed({
            if (previouslySelected != null) {
                if (!buttonsMatch(icon)) {
                    previouslySelected?.flip()
                    icon.flip()
                }
            }
            if (matchesFound()) restartGame()
            previouslySelected = if (previouslySelected == null) icon else null
        }, 800)
    }

    private fun buttonsMatch(icon: Icon): Boolean {
        return icon.isButtonFlipped() && previouslySelected?.isButtonFlipped() == true &&
                icon.getFront() == previouslySelected?.getFront()
    }

    private fun matchesFound(): Boolean {
        return buttonList.all { it.isButtonFlipped() }
    }

    @SuppressLint("SetTextI18n")
    private fun restartGame() {
        startButton.text = "начать заново"
        startButton.visibility = View.VISIBLE
        startButton.isClickable = true
        buttonList.forEach { it.initialState() }
    }
}
