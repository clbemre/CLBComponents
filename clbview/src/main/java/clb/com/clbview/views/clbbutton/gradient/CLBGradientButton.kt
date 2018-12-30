package clb.com.clbview.views.clbbutton.gradient

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import clb.com.clbview.R
import clb.com.clbview.views.clbbutton.gradient.CLBGradientDrawables
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils


/**
 * Created by bt on 6/21/18. cLB
 */
open class CLBGradientButton : AppCompatButton {

    private var cornerRadius: Int = 0
    private var rippleColor: Int = Color.GRAY
    private var endColor: Int = Color.DKGRAY

    private var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        parseAttrs(attrs)
        initialProperties()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttrs(attrs)
        initialProperties()
    }

    constructor(context: Context?, cornerRadius: Int, rippleColor: Int, endColor: Int, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT) : super(context) {
        this.cornerRadius = cornerRadius
        this.rippleColor = rippleColor
        this.endColor = endColor
        this.orientation = orientation
        initialProperties()
    }

    @SuppressLint("Recycle")
    private fun parseAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBGradientButton, 0, 0)
                ?: return
        rippleColor = typedArray.getColor(R.styleable.CLBGradientButton_c_ripple_color, Color.GRAY)
        endColor = typedArray.getColor(R.styleable.CLBGradientButton_c_end_color, Color.DKGRAY)
        cornerRadius = typedArray.getDimensionPixelOffset(R.styleable.CLBGradientButton_c_corner_radius, 0)
        /*   val gradientOrientation = typedArray.getInt(R.styleable.CLBButton_gradient_orientation, GradientOrientation.LEFT_RIGHT.type)
           when (gradientOrientation) {
               GradientOrientation.RIGHT_LEFT.type -> orientation = GradientDrawable.Orientation.RIGHT_LEFT
               GradientOrientation.LEFT_RIGHT.type -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
               GradientOrientation.TOP_BOTTOM.type -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
               GradientOrientation.BOTTOM_TOP.type -> orientation = GradientDrawable.Orientation.BOTTOM_TOP
           }*/
        typedArray.recycle()
    }

    private fun initialProperties() {
        if (Build.VERSION.SDK_INT >= 16) {
            background = CLBGradientDrawables.getSelectableDrawableFor(rippleColor, endColor, orientation, cornerRadius)
        } else {
            setBackgroundDrawable(CLBGradientDrawables.getSelectableDrawableFor(rippleColor, endColor, orientation, cornerRadius))
        }
        typeface = Utils.getRobotoFont(context, RobotoFontType.RB_MEDIUM)
    }

    /* Use Subclasses or don't */
    fun setCustomProperties(rippleColor: Int, endColor: Int, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT, cornerRadius: Int) {
        this.rippleColor = rippleColor
        this.endColor = endColor
        this.cornerRadius = cornerRadius
        this.orientation = orientation
        initialProperties()
    }

    /*enum class GradientOrientation(val type: Int) {
        RIGHT_LEFT(0),
        LEFT_RIGHT(1),
        TOP_BOTTOM(2),
        BOTTOM_TOP(3)
    }*/
}