package com.ext.custom_checkbox_radiobutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.ViewCompat

class CustomCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.checkboxStyle
) : AppCompatCheckBox(context, attrs, defStyleAttr) {

    private var iconSize: Int = 0

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox, defStyleAttr, 0)

        // Default icon size is 20dp
        val defaultSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            20f,
            context.resources.displayMetrics
        ).toInt()

        // Custom icons
        val checkedIcon: Drawable? =
            typedArray.getDrawable(R.styleable.CustomCheckBox_checkboxCheckedIcon)
        val uncheckedIcon: Drawable? =
            typedArray.getDrawable(R.styleable.CustomCheckBox_checkboxUncheckedIcon)

        // Icon size - default to 20dp
        iconSize = typedArray.getDimensionPixelSize(
            R.styleable.CustomCheckBox_checkboxIconSize,
            defaultSize
        )

        if (checkedIcon != null || uncheckedIcon != null) {
            val stateListDrawable = StateListDrawable()

            val checkedDrawable = applyIconSize(checkedIcon)
            val uncheckedDrawable = applyIconSize(uncheckedIcon)

            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked), uncheckedDrawable)
            buttonDrawable = stateListDrawable
        }

        // Icon padding
        val iconPadding =
            typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxIconPadding, 0)
        compoundDrawablePadding = iconPadding

        // Custom paddings
        val paddingStart = typedArray.getDimensionPixelSize(
            R.styleable.CustomCheckBox_checkboxPaddingStart,
            paddingLeft
        )
        val paddingEnd = typedArray.getDimensionPixelSize(
            R.styleable.CustomCheckBox_checkboxPaddingEnd,
            paddingRight
        )
        val paddingTop = typedArray.getDimensionPixelSize(
            R.styleable.CustomCheckBox_checkboxPaddingTop,
            paddingTop
        )
        val paddingBottom = typedArray.getDimensionPixelSize(
            R.styleable.CustomCheckBox_checkboxPaddingBottom,
            paddingBottom
        )
        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)

        // Custom margins
        val marginStart =
            typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginStart, 0)
        val marginEnd =
            typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginEnd, 0)
        val marginTop =
            typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginTop, 0)
        val marginBottom =
            typedArray.getDimensionPixelSize(R.styleable.CustomCheckBox_checkboxMarginBottom, 0)
        applyMargins(marginStart, marginTop, marginEnd, marginBottom)

        // Background tint
        val tintColor =
            typedArray.getColor(R.styleable.CustomCheckBox_checkboxBackgroundTintColor, 0)
        if (tintColor != 0) {
            ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(tintColor))
        }

        typedArray.recycle()
    }

    private fun applyIconSize(drawable: Drawable?): Drawable? {
        if (drawable == null) return null

        // Get the size to use
        val size = if (iconSize > 0) iconSize else {
            // Fallback to intrinsic size if available
            if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
                drawable.intrinsicWidth
            } else {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20f,
                    context.resources.displayMetrics
                ).toInt()
            }
        }

        // Create a bitmap with the desired size
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw the original drawable scaled to fit
        drawable.setBounds(0, 0, size, size)
        drawable.draw(canvas)

        // Return as BitmapDrawable
        return BitmapDrawable(context.resources, bitmap)
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
        val sizedDrawable = applyIconSize(drawable)
        val stateList = StateListDrawable()

        // Get current unchecked drawable if exists
        val currentDrawable = buttonDrawable as? StateListDrawable
        if (currentDrawable != null) {
            stateList.addState(intArrayOf(android.R.attr.state_checked), sizedDrawable)
            // Try to preserve unchecked state - this is a simplified approach
            stateList.addState(intArrayOf(-android.R.attr.state_checked), currentDrawable.current)
        } else {
            stateList.addState(intArrayOf(android.R.attr.state_checked), sizedDrawable)
        }

        buttonDrawable = stateList
    }

    fun setUncheckedIcon(drawable: Drawable?) {
        val sizedDrawable = applyIconSize(drawable)
        val stateList = StateListDrawable()

        // Get current checked drawable if exists
        val currentDrawable = buttonDrawable as? StateListDrawable
        if (currentDrawable != null) {
            // Try to preserve checked state - this is a simplified approach
            stateList.addState(intArrayOf(android.R.attr.state_checked), currentDrawable.current)
            stateList.addState(intArrayOf(-android.R.attr.state_checked), sizedDrawable)
        } else {
            stateList.addState(intArrayOf(-android.R.attr.state_checked), sizedDrawable)
        }

        buttonDrawable = stateList
    }

    fun setIconPadding(padding: Int) {
        compoundDrawablePadding = padding
    }

    fun setIconSize(size: Int) {
        iconSize = size
        // Force redraw with new size
        invalidate()
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