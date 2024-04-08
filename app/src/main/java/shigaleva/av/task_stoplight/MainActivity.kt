package shigaleva.av.task_stoplight

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var numberLight = 0   //кол-во смены света светофора
    private var redColorCount = 0  //кол-во смены света светофора по кругу
    private lateinit var lights: Array<View>   //массив для хранения представлений(View), отрисовывающих свет светофора

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lights = arrayOf(
            findViewById(R.id.red_color_stoplight),
            findViewById(R.id.yellow_color_stoplight),
            findViewById(R.id.green_color_stoplight))

        val clickBtnStart = findViewById<Button>(R.id.btn_start)
        clickBtnStart.setOnClickListener {
            changeColorLight()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun  changeColorLight() {
        when(numberLight) {
            -1 -> {
                numberLight++   //откладка события при повороте события с полчением кейса -1
            }

            0 -> {
                lights[1].setBackgroundResource(R.drawable.unactive_circle)  //отключение желтого света
                lights[0].setBackgroundResource(R.drawable.red_circle)  //включение красного света
                numberLight++
            }

            1 -> {
                lights[0].setBackgroundResource(R.drawable.unactive_circle)  //отключение красного света
                lights[1].setBackgroundResource(R.drawable.yellow_circle)  //включение желтого света
                numberLight++
            }

            2 -> {
                lights[1].setBackgroundResource(R.drawable.unactive_circle)  //отключение желтого света
                lights[2].setBackgroundResource(R.drawable.green_circle)  //включение зеленого света
                numberLight++
            }

            3 -> {
                lights[2].setBackgroundResource(R.drawable.unactive_circle)  //отключение зеленого света
                lights[1].setBackgroundResource(R.drawable.yellow_circle)  //включение желтого света
                numberLight = 0
                redColorCount++
            }
        }
    }

    //сохранение значений переменных
    override fun onSaveInstanceState(outState: Bundle) {
        if (numberLight == 0 && redColorCount != 0) numberLight = 4
        outState.putInt("numberLight", numberLight - 1)  //отнимаем еденицу, так как в конце каждого кейса выполняется  numberLight++
        super.onSaveInstanceState(outState)
    }

    //использование сохраненных значений переменных
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        numberLight = savedInstanceState.getInt("numberLight")
        changeColorLight()
    }
}