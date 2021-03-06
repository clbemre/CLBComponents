package clb.com.clbview.views.clbbutton

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import clb.com.clbview.R
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils

/**
 * Created by EMRE CELEBI on 6/22/18. cLB
 */
class DialogCancelButton : CLBButton {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialPropertiesPrimaryButton()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPropertiesPrimaryButton()
    }

    private fun initialPropertiesPrimaryButton() {
        typeface = Utils.getRobotoFont(context, RobotoFontType.RB_MEDIUM)
        val cornerRadius = context?.resources!!.getDimensionPixelOffset(R.dimen.dp_one)
        val rippleColor = ContextCompat.getColor(context, R.color.md_red_600)
        setCustomProperties(rippleColor, cornerRadius)
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, context?.resources!!.getDimension(R.dimen.sp_eighteen))
        setAllCaps(false)
        setSupportAllCaps(false)
    }
}