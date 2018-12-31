package clb.com.clbview.views.clbspinner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import clb.com.clbview.R
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils
import clb.com.clbview.utils.ViewIDGenerator.Companion.mGenerateViewId
import clb.com.clbview.views.CLBBaseCardView


/**
 * Created by EMRE CELEBI on 3/15/18. cLB
 * BU Component'e eğer setDropDown ardından showPopup methodu tetiklenirse spinner  gbi çalıştırabilir, yada direk listener ile ekranda istediğiniz birşeyi gösterebilirsiniz.
 */
class CLBCardSpinner : CLBBaseCardView {

    private lateinit var cardTitleTextView: TextView
    private lateinit var errorView: TextView
    private lateinit var rightIconView: AppCompatImageView
    private var popupWindow: PopupWindow? = null
    private var listViewPopup: ListView? = null

    private var cardElevation: Float = toDp(2F).toFloat()
    private var cardBackgroundColor: Int = Color.WHITE
    private var cornerRadius: Float = toDp(1F).toFloat()
    private var cardCompatPadding: Boolean = true
    private var cardSelectableForeground: Boolean = true

    private var leftIcon: Int? = null
    private var leftIconTintColor: Int = Color.parseColor("#42A5F5")
    private var leftIconWidth: Float = toDp(25.0F).toFloat()
    private var leftIconHeight: Float = toDp(25.0F).toFloat()

    private var rightIcon: Int? = null
    private var rightIconTintColor: Int = Color.parseColor("#42A5F5")
    private var rightIconWidth: Float = toDp(25.0F).toFloat()
    private var rightIconHeight: Float = toDp(25.0F).toFloat()

    private var textColor: Int = Color.parseColor("#717070")
    private var errorColor: Int = Color.parseColor("#D32F2F")
    private var textSize: Int = toSP(14F).toInt()

    private var cardText: String = ""
    private var errorMessage: String = ""

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBCardSpinner, 0, 0)
                ?: return

        cardElevation = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_elevation, toDp(2.0F).toFloat())
        cornerRadius = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_corner_radius, toDp(1.0F).toFloat())
        cardBackgroundColor = typedArray.getColor(R.styleable.CLBCardSpinner_c_card_background_color, Color.WHITE)
        cardCompatPadding = typedArray.getBoolean(R.styleable.CLBCardSpinner_c_card_use_compat_padding, true)
        cardSelectableForeground = typedArray.getBoolean(R.styleable.CLBCardSpinner_c_card_selectable_foreground, true)

        leftIcon = typedArray.getResourceId(R.styleable.CLBCardSpinner_c_card_icon_left, 0)
        leftIconTintColor = typedArray.getColor(R.styleable.CLBCardSpinner_c_card_icon_left_tint_color, Color.parseColor("#42A5F5"))
        leftIconWidth = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_icon_left_width, toDp(25.0F).toFloat())
        leftIconHeight = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_icon_left_height, toDp(25.0F).toFloat())

        rightIcon = typedArray.getResourceId(R.styleable.CLBCardSpinner_c_card_icon_right, 0)
        rightIconTintColor = typedArray.getColor(R.styleable.CLBCardSpinner_c_card_icon_right_tint_color, Color.parseColor("#42A5F5"))
        rightIconWidth = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_icon_right_width, toDp(25.0F).toFloat())
        rightIconHeight = typedArray.getDimension(R.styleable.CLBCardSpinner_c_card_icon_right_height, toDp(25.0F).toFloat())

        textColor = typedArray.getColor(R.styleable.CLBCardSpinner_card_s_text_color, Color.parseColor("#717070"))
        errorColor = typedArray.getColor(R.styleable.CLBCardSpinner_card_s_error_color, Color.parseColor("#D32F2F"))
        textSize = typedArray.getDimensionPixelSize(R.styleable.CLBCardSpinner_card_s_text_size, toSP(14F).toInt())

        cardText = if (typedArray.getString(R.styleable.CLBCardSpinner_card_s_text) != null) {
            typedArray.getString(R.styleable.CLBCardSpinner_card_s_text)
        } else {
            ""
        }

        errorMessage = if (typedArray.getString(R.styleable.CLBCardSpinner_card_s_error_message) != null) {
            typedArray.getString(R.styleable.CLBCardSpinner_card_s_error_message)
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
    }

    private fun parentEditTextWithErrorMessageLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F)
        layoutParams.setMargins(toDp(10.0F), 0, 0, 0)
        layout.layoutParams = layoutParams

        layout.addView(getTitleTextView()) // ADD EditText
        layout.addView(getErrorTextView()) // ADD ErrorView

        if (rightIcon != null && rightIcon != 0)
            layout.addView(getRightImageView(rightIconTintColor)) // ADD RightImageView

        return layout
    }

    private fun getTitleTextView(): TextView {
        cardTitleTextView = TextView(context)
        cardTitleTextView.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        cardTitleTextView.gravity = Gravity.CENTER_VERTICAL
        cardTitleTextView.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_REGULAR)

        cardTitleTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        cardTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        cardTitleTextView.setTextColor(textColor)
        cardTitleTextView.text = cardText

        return cardTitleTextView
    }

    private fun getLeftImageView(tintColor: Int): ImageView {
        val imageView = AppCompatImageView(context)
        imageView.layoutParams = LinearLayout.LayoutParams(leftIconWidth.toInt(), leftIconHeight.toInt())

        if (leftIcon != null && leftIcon != 0)
            imageView.setImageResource(leftIcon!!)

        imageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

        return imageView
    }

    private fun getRightImageView(tintColor: Int): ImageView {
        rightIconView = AppCompatImageView(context)
        val layoutParams = RelativeLayout.LayoutParams(rightIconWidth.toInt(), rightIconHeight.toInt())
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        rightIconView.layoutParams = layoutParams

        if (rightIcon != null && rightIcon != 0)
            rightIconView.setImageResource(rightIcon!!)

        rightIconView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        setRippleBorderLessEffect(rightIconView)

        return rightIconView
    }

    private fun getErrorTextView(): TextView {
        errorView = TextView(context)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        }
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.setMargins(0, 0, 10, 10)
        errorView.layoutParams = layoutParams

        errorView.id = mGenerateViewId()
        errorView.visibility = View.GONE
        errorView.gravity = Gravity.END
        errorView.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_MEDIUM)
        errorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
        errorView.setTextColor(errorColor)
        errorView.text = errorMessage

        return errorView
    }

    fun getCardText(): CharSequence {
        return cardTitleTextView.text
    }

    fun setCardText(text: CharSequence) {
        cardTitleTextView.text = text
    }

    fun setCardText(@StringRes resId: Int) {
        cardTitleTextView.setText(resId)
    }

    fun setCardOnClickListener(listener: View.OnClickListener? = null) {
        getParentCardView().setOnClickListener(listener)
        rightIconView.setOnClickListener(listener)
    }

    fun setCardOnLongCLickListener(listener: View.OnLongClickListener? = null) {
        getParentCardView().setOnLongClickListener(listener)
        rightIconView.setOnLongClickListener(listener)
    }

    fun setErrorMessage(errorMessage: String, errColor: Int = errorColor) {
        this.errorMessage = errorMessage
        this.errorColor = errColor
        this.errorView.visibility = View.VISIBLE
        this.errorView.setTextColor(errorColor)
        this.errorView.text = errorMessage
    }


    fun clearErrorMessage() {
        this.errorView.visibility = View.GONE
        this.errorView.text = ""
    }

    fun setError() {
        this.errorView.visibility = View.VISIBLE
    }

    fun clearError() {
        errorView.visibility = View.GONE
    }


    fun setDropDown(adapter: ListAdapter, listener: AdapterView.OnItemClickListener) {
        listViewPopup = ListView(context)
        listViewPopup?.adapter = adapter
        listViewPopup?.onItemClickListener = listener
        listViewPopup?.dividerHeight = 0
        listViewPopup?.divider = null
        popupWindow = PopupWindow(context)
        popupWindow?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_drawable_clb_spinner))
        popupWindow?.isFocusable = true
        popupWindow?.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow?.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow?.contentView = listViewPopup
    }

    fun showPopup() {
        popupWindow?.showAsDropDown(getParentCardView(), (getParentCardView().width / 9), -(getParentCardView().height / 1.5).toInt())
    }

    fun dismissPopup() {
        popupWindow?.dismiss()
    }

    private fun toSP(sp: Float): Float {
        return sp * resources.displayMetrics.scaledDensity
    }
}