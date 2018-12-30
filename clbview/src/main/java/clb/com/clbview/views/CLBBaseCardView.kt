package clb.com.clbview.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import clb.com.clbview.R

/**
 * Created by bt on 7/19/18. cLB
 */
open class CLBBaseCardView : LinearLayout {

    private lateinit var parentLayout: LinearLayout
    private lateinit var parentCardView: CardView
    private lateinit var cardContainerLayout: LinearLayout

    private constructor(context: Context?) : super(context) {
        initial()
    }

    protected constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initial()
    }

    protected constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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
        parentLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        parentLayout.addView(addParentCardView()) // ADD parentCardView
        return parentLayout
    }

    private fun addParentCardView(): CardView {
        parentCardView = CardView(context)
        parentCardView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        parentCardView.addView(addCardContainerLayout())
        return parentCardView
    }

    private fun addCardContainerLayout(): LinearLayout {
        cardContainerLayout = LinearLayout(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(toDp(10.0F), 0, toDp(10.0F), 0)
        cardContainerLayout.layoutParams = layoutParams

        cardContainerLayout.orientation = LinearLayout.HORIZONTAL
        cardContainerLayout.gravity = Gravity.CENTER

        return cardContainerLayout
    }

    protected fun setParentCardViewProperties(cardBackgroundColor: Int, cornerRadius: Float, cardElevation: Float, cardCompatPadding: Boolean, cardSelectableForeground: Boolean) {
        parentCardView.setCardBackgroundColor(cardBackgroundColor)
        parentCardView.radius = cornerRadius
        parentCardView.cardElevation = cardElevation
        parentCardView.useCompatPadding = cardCompatPadding

        if (cardSelectableForeground)
            setSelectableForegroundRipple(parentCardView)

    }

    protected fun getParentCardView(): CardView {
        return parentCardView
    }

    private fun setSelectableForegroundRipple(cardView: CardView) {
        cardView.isFocusable = true
        cardView.isClickable = true
        cardView.foreground = ContextCompat.getDrawable(context, R.drawable.ripple_default)

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             val outValue = TypedValue()
             context.theme.resolveAttribute(
                     android.R.attr.selectableItemBackground, outValue, true)
             cardView.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
         }*/
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

    fun cardViewChangeBackgroundColorWithAnimation(context: Context, fromColor: Int, toColor: Int, duration: Long = 1500) {
        val colorFrom = ContextCompat.getColor(context, fromColor)
        val colorTo = ContextCompat.getColor(context, toColor)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = duration
        colorAnimation.addUpdateListener {
            parentCardView.setCardBackgroundColor(it.animatedValue as Int)
        }
        colorAnimation.start()
    }

    protected fun toDp(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}