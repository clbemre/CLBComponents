package clb.com.clbview.views.clbbutton.gradient

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import clb.com.clbview.R

/**
 * Created by EMRE CELEBI on 6/22/18. cLB
 */
class PrimaryGradientButton : CLBGradientButton {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialPropertiesPrimaryButton()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPropertiesPrimaryButton()
    }

    private fun initialPropertiesPrimaryButton() {
        val cornerRadius = context?.resources!!.getDimensionPixelOffset(R.dimen.dp_three)
        val rippleColor = ContextCompat.getColor(context, R.color.md_blue_400)
        val endColor = ContextCompat.getColor(context, R.color.md_blue_800)
        val rightLeft = GradientDrawable.Orientation.RIGHT_LEFT
        setCustomProperties(rippleColor, endColor, rightLeft, cornerRadius)
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, context?.resources!!.getDimension(R.dimen.sp_eighteen))
        setAllCaps(false)
        setSupportAllCaps(false)
    }
}