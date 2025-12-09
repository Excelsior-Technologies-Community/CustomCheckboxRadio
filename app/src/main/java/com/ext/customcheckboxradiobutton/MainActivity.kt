package com.ext.customcheckboxradiobutton

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.customcheckboxradiobutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Safe area padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Click listener to demonstrate runtime customization
        binding.btnChangeStyle.setOnClickListener {
            binding.checkbox3.apply {
                // First set the new icon size (48dp)
                val newSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    48f,
                    resources.displayMetrics
                ).toInt()
                setIconSize(newSize)

                // Then change icons (they will use the new size)
                setCheckedIcon(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_check_circle_red_32dp
                    )
                )
                setUncheckedIcon(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_circle_gray_32dp
                    )
                )

                // Change padding & tint
                setIconPadding(20)
                setCustomPaddings(40, 24, 40, 24)
                setBackgroundTint(Color.parseColor("#FFEBEE"))

                // Change text
                text = "Changed at runtime (48dp icons)!"
                textSize = 18f
                setTextColor(Color.parseColor("#D32F2F"))

                // Optional: force check
                isChecked = !isChecked
            }
        }
    }
}