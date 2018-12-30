package clb.com.clbview.views.clbbutton.gradient

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import clb.com.clbview.R
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils

/**
 * Created by bt on 6/22/18. cLB
 */
class DialogCancelGradientButton : CLBGradientButton {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialPropertiesPrimaryButton()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPropertiesPrimaryButton()
    }

    private fun initialPropertiesPrimaryButton() {
        typeface = Utils.getRobotoFont(context, RobotoFontType.RB_MEDIUM)
        val cornerRadius = context?.resources!!.getDimensionPixelOffset(R.dimen.dp_one)
        val rippleColor = ContextCompat.getColor(context, R.color.md_red_900)
        val endColor = ContextCompat.getColor(context, R.color.md_red_300)
        setCustomProperties(
            rippleColor,
            endColor,
            cornerRadius = cornerRadius,
            orientation = GradientDrawable.Orientation.BOTTOM_TOP
        )
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, context?.resources!!.getDimension(R.dimen.sp_eighteen))
        setAllCaps(false)
        setSupportAllCaps(false)
    }
}