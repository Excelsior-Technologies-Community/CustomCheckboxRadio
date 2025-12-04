package com.ext.customcheckboxradiobutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.ViewCompat

class CustomRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.radioButtonStyle
) : AppCompatRadioButton(context, attrs, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton, defStyleAttr, 0)

        // Custom icons
        val checkedIcon: Drawable? = typedArray.getDrawable(R.styleable.CustomRadioButton_radioCheckedIcon)
        val uncheckedIcon: Drawable? = typedArray.getDrawable(R.styleable.CustomRadioButton_radioUncheckedIcon)
        if (checkedIcon != null || uncheckedIcon != null) {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedIcon)
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked), uncheckedIcon)
            buttonDrawable = stateListDrawable
        }

        // Icon padding
        val iconPadding = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioIconPadding, 0)
        compoundDrawablePadding = iconPadding

        // Custom paddings
        val paddingStart = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioPaddingStart, paddingLeft)
        val paddingEnd = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioPaddingEnd, paddingRight)
        val paddingTop = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioPaddingTop, paddingTop)
        val paddingBottom = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioPaddingBottom, paddingBottom)
        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)

        // Custom margins
        val marginStart = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioMarginStart, 0)
        val marginEnd = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioMarginEnd, 0)
        val marginTop = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioMarginTop, 0)
        val marginBottom = typedArray.getDimensionPixelSize(R.styleable.CustomRadioButton_radioMarginBottom, 0)
        applyMargins(marginStart, marginTop, marginEnd, marginBottom)

        // Background tint
        val tintColor = typedArray.getColor(R.styleable.CustomRadioButton_radioBackgroundTintColor, 0)
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

    // Optional setters for runtime customization
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
