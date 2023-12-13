package com.mindmazegame

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar()

        val playButton: ImageView = findViewById(R.id.playBtn)
        playButton.setOnClickListener { showLevelDialog() }

        val exitButton = findViewById<ImageView>(R.id.exit)
        exitButton.setOnClickListener { exitGame() }
    }

    private fun setActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.action_bar_layout)
    }

    private fun showLevelDialog() {
        val levels = arrayOf("Животные", "Мебель", "Растения")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите уровень")
            .setItems(levels) { _, which ->
                // which - индекс выбранного уровня
                val selectedLevel = levels[which]
                startGame(selectedLevel)
            }
            .setNegativeButton("Отмена", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun startGame(level: String) {
        // Создать Intent для перехода к соответствующей активности уровня
        val intent: Intent = when (level) {
            "Животные" -> Intent(this, AnimalsActivity::class.java)
            "Мебель" -> Intent(this, FurnitureActivity::class.java)
            "Растения" -> Intent(this, PlantsActivity::class.java)
            else -> throw IllegalArgumentException("Неизвестный уровень: $level")
        }

        // Передать выбранный уровень как дополнительную информацию
        intent.putExtra("LEVEL", level)

        // Запустить активность уровня
        startActivity(intent)
    }
    private fun exitGame() {
        finish()
    }
}

