package clb.com.clbview.views.clbbutton

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import clb.com.clbview.R

/**
 * Created by EMRE CELEBI on 6/22/18. cLB
 */
class SecondaryButton : CLBButton {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialPropertiesPrimaryButton()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPropertiesPrimaryButton()
    }

    private fun initialPropertiesPrimaryButton() {
        val cornerRadius = context?.resources!!.getDimensionPixelOffset(R.dimen.dp_three)
        val rippleColor = ContextCompat.getColor(context, R.color.md_blue_900)
        setCustomProperties(rippleColor, cornerRadius)
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, context?.resources!!.getDimension(R.dimen.sp_eighteen))
        setAllCaps(false)
        setSupportAllCaps(false)
    }
}