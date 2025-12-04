package com.ext.customcheckboxradiobutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.ViewCompat

class CustomCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.checkboxStyle
) : AppCompatCheckBox(context, attrs, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox, defStyleAttr, 0)

        // Custom icons
        val checkedIcon: Drawable? = typedArray.getDrawable(R.styleable.CustomCheckBox_checkboxCheckedIcon)
        val uncheckedIcon: Drawable? = typedArray.getDrawable(R.styleable.CustomCheckBox_checkboxUncheckedIcon)
        if (checkedIcon != null || uncheckedIcon != null) {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedIcon)
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked), uncheckedIcon)
            buttonDrawable = stateListDrawable
        }

        // Icon padding
        val iconPadding = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxIconPadding, 0)
        compoundDrawablePadding = iconPadding

        // Custom paddings
        val paddingStart = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxPaddingStart, paddingLeft)
        val paddingEnd = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxPaddingEnd, paddingRight)
        val paddingTop = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxPaddingTop, paddingTop)
        val paddingBottom = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxPaddingBottom, paddingBottom)
        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)

        // Custom margins
        val marginStart = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginStart, 0)
        val marginEnd = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginEnd, 0)
        val marginTop = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginTop, 0)
        val marginBottom = typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginBottom, 0)
        applyMargins(marginStart, marginTop, marginEnd, marginBottom)

        // Background tint
        val tintColor = typedArray.getColor(R.styleable.CustomCheckBox_checkboxBackgroundTintColor, 0)
        if (tintColor != 0) {
            ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(tintColor))
        }

        typedArray.recycle()
    }

    private fun applyMargins(start: Int, top: Int, end: Int, bottom: Int) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams
        if (params != null) {
            params.marginStart = start
            params.topMargin = top
            params.marginEnd = end
            params.bottomMargin = bottom
            layoutParams = params
        } else {
            val defaultParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            defaultParams.marginStart = start
            defaultParams.topMargin = top
            defaultParams.marginEnd = end
            defaultParams.bottomMargin = bottom
            layoutParams = defaultParams
        }
    }

    // Optional: setters for runtime customization
    fun setCheckedIcon(drawable: Drawable?) {
        val stateList = buttonDrawable as? StateListDrawable ?: StateListDrawable()
        stateList.addState(intArrayOf(android.R.attr.state_checked), drawable)
        buttonDrawable = stateList
    }

    fun setUncheckedIcon(drawable: Drawable?) {
        val stateList = buttonDrawable as? StateListDrawable ?: StateListDrawable()
        stateList.addState(intArrayOf(-android.R.attr.state_checked), drawable)
        buttonDrawable = stateList
    }

    fun setIconPadding(padding: Int) {
        compoundDrawablePadding = padding
    }

    fun setCustomPaddings(start: Int, top: Int, end: Int, bottom: Int) {
        setPadding(start, top, end, bottom)
    }

    fun setCustomMargins(start: Int, top: Int, end: Int, bottom: Int) {
        applyMargins(start, top, end, bottom)
    }

    fun setBackgroundTint(color: Int) {
        ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))
    }
}
