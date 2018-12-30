package clb.com.clbview.views.clbbutton

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import clb.com.clbview.R


/**
 * Created by bt on 6/21/18. cLB
 */
open class CLBButton : AppCompatButton {

    private var cornerRadius: Int = 0
    private var rippleColor: Int = Color.GRAY

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        parseAttrs(attrs)
        initialProperties()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttrs(attrs)
        initialProperties()
    }

    private fun initialProperties() {
        if (Build.VERSION.SDK_INT >= 16) {
            background = CLBDrawables.getSelectableDrawableFor(rippleColor, cornerRadius)
        } else {
            setBackgroundDrawable(CLBDrawables.getSelectableDrawableFor(rippleColor, cornerRadius))
        }
    }

    @SuppressLint("Recycle")
    private fun parseAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBButton, 0, 0)
            ?: return
        rippleColor = typedArray.getColor(R.styleable.CLBButton_ripple_color, Color.GRAY)
        cornerRadius = typedArray.getDimensionPixelOffset(R.styleable.CLBButton_corner_radius, 0)
        typedArray.recycle()
    }

    /* Use Subclasses or don't */
    fun setCustomProperties(rippleColor: Int, cornerRadius: Int) {
        this.rippleColor = rippleColor
        this.cornerRadius = cornerRadius
        initialProperties()
    }
}