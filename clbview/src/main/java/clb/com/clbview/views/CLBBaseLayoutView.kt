package clb.com.clbview.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import clb.com.clbview.R

/**
 * Created by bt on 7/19/18. cLB
 */
open class CLBBaseLayoutView : LinearLayout {

    private lateinit var parentLayout: LinearLayout
    private lateinit var cardContainerLayout: LinearLayout

    private constructor(context: Context?) : super(context) {
        initial()
    }

    protected constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initial()
    }

    protected constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initial()
    }

    private fun initial() {
        addView(addParentLayout())
    }

    protected fun addSubView(subView: View) {
        cardContainerLayout.addView(subView)
    }

    private fun addParentLayout(): LinearLayout {
        parentLayout = LinearLayout(context)
        parentLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        parentLayout.addView(addCardContainerLayout()) // ADD parentCardView
        return parentLayout
    }

    private fun addCardContainerLayout(): LinearLayout {
        cardContainerLayout = LinearLayout(context)
        val layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(toDp(10.0F), 0, toDp(10.0F), 0)
        cardContainerLayout.layoutParams = layoutParams

        cardContainerLayout.orientation = LinearLayout.HORIZONTAL
        cardContainerLayout.gravity = Gravity.CENTER

        return cardContainerLayout
    }

    fun selectedLayout(isSelected: Boolean) {
        if (isSelected) {
            parentLayout.setBackgroundResource(R.drawable.background_drawable_selected_for_edittext)
        } else {
            parentLayout.setBackgroundResource(0)
        }
    }

    protected fun setParentOnClickListener(listener: View.OnClickListener) {
        parentLayout.setOnClickListener(listener)
    }

    protected fun setRippleBorderLessEffect(view: View) {
        view.isFocusable = true
        view.isClickable = true
        view.setBackgroundResource(R.drawable.ripple_circle_default)
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val outValue = TypedValue()
            context.theme.resolveAttribute(
                    android.R.attr.selectableItemBackgroundBorderless, outValue, true)
            view.setBackgroundResource(outValue.resourceId)
        }*/
    }

    fun cardViewChangeBackgroundColorWithAnimation(
        context: Context,
        fromColor: Int,
        toColor: Int,
        duration: Long = 1500
    ) {
        val colorFrom = ContextCompat.getColor(context, fromColor)
        val colorTo = ContextCompat.getColor(context, toColor)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = duration
        colorAnimation.addUpdateListener {
            //            parentCardView.setCardBackgroundColor(it.animatedValue as Int)
        }
        colorAnimation.start()
    }

    protected fun toDp(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}