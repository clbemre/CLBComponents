package clb.com.clbview.views.clblabel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import clb.com.clbview.R
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils
import clb.com.clbview.views.CLBBaseCardView


/**
 * Created by bt on 3/15/18. cLB
 * Default values are its my default values, please change it as you like default values.
 */
class CLBCardLabel : CLBBaseCardView {

    private lateinit var cardView: CardView
    private lateinit var cardLeftTextView: TextView
    private lateinit var cardRightTextView: TextView
    private lateinit var rightImageView: AppCompatImageView

    private var cardElevation: Float = toDp(2F).toFloat()
    private var cardBackgroundColor: Int = Color.WHITE
    private var cornerRadius: Float = toDp(1F).toFloat()
    private var cardCompatPadding: Boolean = true
    private var cardSelectableForeground: Boolean = false

    private var leftIcon: Int? = null
    private var leftIconTintColor: Int = Color.parseColor("#42A5F5")
    private var leftIconWidth: Float = toDp(25.0F).toFloat()
    private var leftIconHeight: Float = toDp(25.0F).toFloat()

    private var rightIcon: Int? = null
    private var rightIconTintColor: Int = Color.parseColor("#42A5F5")
    private var rightIconWidth: Float = toDp(25.0F).toFloat()
    private var rightIconHeight: Float = toDp(25.0F).toFloat()

    private var leftTextColor: Int = Color.parseColor("#757575")
    private var rightTextColor: Int = Color.parseColor("#616161")
    private var rightTextGravity: Int = Gravity.CENTER_VERTICAL or Gravity.END

    private var leftTextSize: Int = toSP(14F).toInt()
    private var rightTextSize: Int = toSP(14F).toInt()

    private var cardLeftText: String = ""
    private var cardRightText: String = ""

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        parseAttrs(attrs)
        initialSubViews()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttrs(attrs)
        initialSubViews()
    }

    @SuppressLint("Recycle")
    private fun parseAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBCardLabel, 0, 0)
                ?: return

        cardElevation = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_elevation, toDp(2.0F).toFloat())
        cornerRadius = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_corner_radius, toDp(1.0F).toFloat())
        cardBackgroundColor = typedArray.getColor(R.styleable.CLBCardLabel_c_card_background_color, Color.WHITE)
        cardCompatPadding = typedArray.getBoolean(R.styleable.CLBCardLabel_c_card_use_compat_padding, true)
        cardSelectableForeground = typedArray.getBoolean(R.styleable.CLBCardLabel_c_card_selectable_foreground, false)

        leftIcon = typedArray.getResourceId(R.styleable.CLBCardLabel_c_card_icon_left, 0)
        leftIconTintColor = typedArray.getColor(R.styleable.CLBCardLabel_c_card_icon_left_tint_color, Color.parseColor("#42A5F5"))
        leftIconWidth = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_icon_left_width, toDp(25.0F).toFloat())
        leftIconHeight = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_icon_left_height, toDp(25.0F).toFloat())

        rightIcon = typedArray.getResourceId(R.styleable.CLBCardLabel_c_card_icon_right, 0)
        rightIconTintColor = typedArray.getColor(R.styleable.CLBCardLabel_c_card_icon_right_tint_color, Color.parseColor("#42A5F5"))
        rightIconWidth = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_icon_right_width, toDp(25.0F).toFloat())
        rightIconHeight = typedArray.getDimension(R.styleable.CLBCardLabel_c_card_icon_right_height, toDp(25.0F).toFloat())

        leftTextColor = typedArray.getColor(R.styleable.CLBCardLabel_card_l_left_text_color, Color.parseColor("#757575"))
        rightTextColor = typedArray.getColor(R.styleable.CLBCardLabel_card_l_right_text_color, Color.parseColor("#616161"))
        leftTextSize = typedArray.getDimensionPixelSize(R.styleable.CLBCardLabel_card_l_left_text_size, toSP(14F).toInt())
        rightTextSize = typedArray.getDimensionPixelSize(R.styleable.CLBCardLabel_card_l_right_text_size, toSP(14F).toInt())
        rightTextGravity = typedArray.getInt(R.styleable.CLBCardLabel_android_gravity, Gravity.CENTER_VERTICAL or Gravity.END)

        cardLeftText = if (typedArray.getString(R.styleable.CLBCardLabel_card_l_left_text) != null) {
            typedArray.getString(R.styleable.CLBCardLabel_card_l_left_text)
        } else {
            ""
        }

        cardRightText = if (typedArray.getString(R.styleable.CLBCardLabel_card_l_right_text) != null) {
            typedArray.getString(R.styleable.CLBCardLabel_card_l_right_text)
        } else {
            ""
        }

        typedArray.recycle()
    }

    private fun initialSubViews() {
        setParentCardViewProperties(cardBackgroundColor, cornerRadius, cardElevation, cardCompatPadding, cardSelectableForeground)
        if (leftIcon != null && leftIcon != 0)
            addSubView(getLeftImageView(leftIconTintColor)) // ADD LeftImageView

        addSubView(parentEditTextWithErrorMessageLayout()) // ADD ParentView

        if (rightIcon != null && rightIcon != 0)
            addSubView(getRightImageView(rightIconTintColor)) // ADD RightImageView

    }

    private fun parentEditTextWithErrorMessageLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        layout.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F)

        layout.addView(titlesContainerLayout()) // ADD EditText

        return layout
    }

    private fun titlesContainerLayout(): LinearLayout {
        val layout = LinearLayout(context)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(toDp(5.0F), 0, 0, 0)
        layout.layoutParams = layoutParams
        layout.orientation = LinearLayout.HORIZONTAL

        layout.addView(getLeftTitleTextView()) // ADD LeftTextView
        layout.addView(getRightTitleTextView()) // ADD  Right TextView

        return layout
    }

    private fun getLeftTitleTextView(): TextView {
        cardLeftTextView = TextView(context)
        cardLeftTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        cardLeftTextView.gravity = Gravity.CENTER_VERTICAL

        cardLeftTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        cardLeftTextView.text = cardLeftText
        cardLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize.toFloat())
        cardLeftTextView.setTextColor(leftTextColor)
        cardLeftTextView.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_REGULAR)

        return cardLeftTextView
    }

    private fun getRightTitleTextView(): TextView {
        cardRightTextView = TextView(context)
        cardRightTextView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        cardRightTextView.gravity = rightTextGravity

        cardRightTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        cardRightTextView.text = cardRightText
        cardRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize.toFloat())
        cardRightTextView.setTextColor(rightTextColor)
        cardRightTextView.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_REGULAR)

        return cardRightTextView
    }

    private fun getLeftImageView(tintColor: Int): ImageView {
        val imageView = AppCompatImageView(context)
        if (leftIcon != null && leftIcon != 0) {
            imageView.setImageResource(leftIcon!!)
        }
        imageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        val layoutParams = LinearLayout.LayoutParams(leftIconWidth.toInt(), leftIconHeight.toInt())
        imageView.layoutParams = layoutParams

        return imageView
    }

    private fun getRightImageView(tintColor: Int): ImageView {
        rightImageView = AppCompatImageView(context)
        if (rightIcon != null && rightIcon != 0) {
            rightImageView.setImageResource(rightIcon!!)
        }
        rightImageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

        val layoutParams = LinearLayout.LayoutParams(rightIconWidth.toInt(), rightIconHeight.toInt())
        rightImageView.layoutParams = layoutParams
        return rightImageView
    }

    fun getCardTextLeft(): CharSequence {
        return cardLeftTextView.text
    }

    fun setCardTextLeft(text: CharSequence) {
        cardLeftTextView.text = text
    }

    fun setCardTextLeft(@StringRes resId: Int) {
        cardLeftTextView.setText(resId)
    }

    fun getCardTextRight(): CharSequence {
        return cardRightTextView.text
    }

    fun setCardTextRight(text: CharSequence) {
        cardRightTextView.text = text
    }

    fun setCardTextRight(@StringRes resId: Int) {
        cardRightTextView.setText(resId)
    }

    private fun toSP(sp: Float): Float {
        return sp * resources.displayMetrics.scaledDensity
    }
}