package clb.com.clbview.views.clbedittext

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.*
import clb.com.clbview.R
import clb.com.clbview.helper.JavaHelper
import clb.com.clbview.listener.MyAnimatorListener
import clb.com.clbview.listener.MyTextWatcher
import clb.com.clbview.utils.RobotoFontType
import clb.com.clbview.utils.Utils
import clb.com.clbview.utils.ViewIDGenerator
import clb.com.clbview.views.CLBBaseCardView
import clb.com.clbview.views.clbdialog.CLBDialog
import clb.com.clbview.views.clbdialog.clbWarningDialog


/**
 * Created by bt on 3/15/18. cLB
 */
class CLBEditText : CLBBaseCardView {

    private lateinit var editText: EditText
    private lateinit var errorView: TextView
    private lateinit var clearIconView: AppCompatImageView
    private lateinit var infoIconView: AppCompatImageView
    private lateinit var leftImageView: AppCompatImageView
    private lateinit var clearLeftIconView: AppCompatImageView

    private var cardElevation: Float = toDp(2F).toFloat()
    private var cardBackgroundColor: Int = Color.WHITE
    private var cornerRadius: Float = toDp(1F).toFloat()
    private var cardCompatPadding: Boolean = true
    private var cardInputType: Int? = null

    private var leftIcon: Int? = null
    private var leftIconTintColor: Int = Color.parseColor("#42A5F5")
    private var leftIconWidth: Float = toDp(25.0F).toFloat()
    private var leftIconHeight: Float = toDp(25.0F).toFloat()

    private var isClearIcon: Boolean? = false
    private var clearIcon: Int? = null
    private var clearIconTintColor: Int = Color.parseColor("#EF5350")
    private var clearIconWidth: Float = toDp(25.0F).toFloat()
    private var clearIconHeight: Float = toDp(25.0F).toFloat()

    private var isLeftClearIcon: Boolean? = false
    private var clearLeftIcon: Int? = null
    private var clearLeftIconTintColor: Int = Color.parseColor("#EF5350")
    private var clearLeftIconWidth: Float = toDp(25.0F).toFloat()
    private var clearLeftIconHeight: Float = toDp(25.0F).toFloat()

    private var isInfoIcon: Boolean? = false
    private var infoIcon: Int? = null
    private var infoIconTintColor: Int = Color.parseColor("#42A5F5")
    private var infoIconWidth: Float = toDp(25.0F).toFloat()
    private var infoIconHeight: Float = toDp(25.0F).toFloat()

    private var cardSingleLine: Boolean = true
    private var textColor: Int = Color.parseColor("#616161")
    private var hintTextColor: Int = Color.parseColor("#BDBDBD")
    private var errorColor: Int = Color.parseColor("#D32F2F")

    private var textSize: Int = toSP(14F).toInt()
    private var textMaxCharacterLength: Int = -1

    private var cardText: String = ""
    private var cardHint: String = ""
    private var errorMessage: String = "" // şuanda xmlden girilirse düzgün çalışmıyor.

    private var infoMessageTitle: String = ""
    private var infoMessageDescription: String = ""

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBEditText, 0, 0)
                ?: return

        cardElevation = typedArray.getDimension(R.styleable.CLBEditText_c_card_elevation, toDp(2.0F).toFloat())
        cornerRadius = typedArray.getDimension(R.styleable.CLBEditText_c_card_corner_radius, toDp(1.0F).toFloat())
        cardBackgroundColor = typedArray.getColor(R.styleable.CLBEditText_c_card_background_color, Color.WHITE)
        cardCompatPadding = typedArray.getBoolean(R.styleable.CLBEditText_c_card_use_compat_padding, true)
        cardInputType = typedArray.getInt(R.styleable.CLBEditText_android_inputType, EditorInfo.TYPE_NULL)

        leftIcon = typedArray.getResourceId(R.styleable.CLBEditText_clb_icon_left, 0)
        leftIconTintColor = typedArray.getColor(R.styleable.CLBEditText_clb_icon_left_tint_color, Color.parseColor("#42A5F5"))
        leftIconWidth = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_left_width, toDp(25.0F).toFloat())
        leftIconHeight = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_left_height, toDp(25.0F).toFloat())

        isClearIcon = typedArray.getBoolean(R.styleable.CLBEditText_clb_is_clear_icon, false)
        clearIcon = typedArray.getResourceId(R.styleable.CLBEditText_clb_icon_clear, 0)
        clearIconTintColor = typedArray.getColor(R.styleable.CLBEditText_clb_icon_clear_tint_color, Color.parseColor("#EF5350"))
        clearIconWidth = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_clear_width, toDp(25.0F).toFloat())
        clearIconHeight = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_clear_height, toDp(25.0F).toFloat())

        isLeftClearIcon = typedArray.getBoolean(R.styleable.CLBEditText_clb_is_left_clear_icon, false)
        clearLeftIcon = typedArray.getResourceId(R.styleable.CLBEditText_clb_icon_left_clear, 0)
        clearLeftIconTintColor = typedArray.getColor(R.styleable.CLBEditText_clb_left_icon_clear_tint_color, Color.parseColor("#EF5350"))
        clearLeftIconWidth = typedArray.getDimension(R.styleable.CLBEditText_clb_left_icon_clear_width, toDp(25.0F).toFloat())
        clearLeftIconHeight = typedArray.getDimension(R.styleable.CLBEditText_clb_left_icon_clear_height, toDp(25.0F).toFloat())

        isInfoIcon = typedArray.getBoolean(R.styleable.CLBEditText_clb_is_info_icon, false)
        infoIcon = typedArray.getResourceId(R.styleable.CLBEditText_clb_icon_info, 0)
        infoIconTintColor = typedArray.getColor(R.styleable.CLBEditText_clb_icon_info_tint_color, Color.parseColor("#42A5F5"))
        infoIconWidth = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_info_width, toDp(25.0F).toFloat())
        infoIconHeight = typedArray.getDimension(R.styleable.CLBEditText_clb_icon_info_height, toDp(25.0F).toFloat())

        cardSingleLine = typedArray.getBoolean(R.styleable.CLBEditText_clb_single_line, true)
        textColor = typedArray.getColor(R.styleable.CLBEditText_clb_text_color, Color.parseColor("#616161"))
        hintTextColor = typedArray.getColor(R.styleable.CLBEditText_clb_hint_text_color, Color.parseColor("#BDBDBD"))
        errorColor = typedArray.getColor(R.styleable.CLBEditText_clb_error_color, Color.parseColor("#D32F2F"))
        textSize = typedArray.getDimensionPixelSize(R.styleable.CLBEditText_clb_text_size, toSP(14F).toInt())

        textMaxCharacterLength = typedArray.getInteger(R.styleable.CLBEditText_clb_text_max_character, -1)

        cardText = if (typedArray.getString(R.styleable.CLBEditText_clb_text) != null) {
            typedArray.getString(R.styleable.CLBEditText_clb_text)
        } else {
            ""
        }
        cardHint = if (typedArray.getString(R.styleable.CLBEditText_clb_hint) != null) {
            typedArray.getString(R.styleable.CLBEditText_clb_hint)
        } else {
            ""
        }

        errorMessage = if (typedArray.getString(R.styleable.CLBEditText_clb_error_message) != null) {
            typedArray.getString(R.styleable.CLBEditText_clb_error_message)
        } else {
            ""
        }

        typedArray.recycle()
    }

    private fun initialSubViews() {
        setParentCardViewProperties(cardBackgroundColor, cornerRadius, cardElevation, cardCompatPadding, false)
        addSubView(getFrameLayoutForLeftImage()) // ADD FrameLayoutForLeftImage
        addSubView(addFrameLayoutParentEditTextWithErrorMessageLayout()) // ADD ParentView
        listenChangeImageViewListener() // listenChangeImageViewListener
    }

    private fun addFrameLayoutParentEditTextWithErrorMessageLayout(): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F)
        layoutParams.setMargins(toDp(5.0F), 0, 0, 0)
        frameLayout.layoutParams = layoutParams

        frameLayout.addView(parentEditTextWithErrorMessageLayout())
        frameLayout.addView(getErrorTextView())
        return frameLayout
    }

    private fun parentEditTextWithErrorMessageLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        layout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        layout.addView(addEditText()) // ADD EditText

        if (isClearIcon!!)
            layout.addView(getClearImageButton(clearIconTintColor)) // ADD ClearImageButton

        if (isInfoIcon!!)
            layout.addView(getInfoImageButton(infoIconTintColor)) // ADD InfoImageButton

        return layout
    }

    private fun addEditText(): EditText {
        editText = EditText(context)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        editText.layoutParams = layoutParams

        editText.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        editText.hint = cardHint
        editText.setText(cardText)
        editText.setSingleLine(cardSingleLine)
        editText.setHintTextColor(hintTextColor)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        editText.setTextColor(textColor)

        JavaHelper.setCursorDrawableColor(editText, ContextCompat.getColor(context, R.color.md_blue_300))
        JavaHelper.colorHandles(editText, ContextCompat.getColor(context, R.color.md_blue_300))
        editText.highlightColor = ContextCompat.getColor(context, R.color.md_blue_100)

        editText.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_REGULAR)

        if (cardInputType != EditorInfo.TYPE_NULL) {
            editText.inputType = cardInputType!!
        }

        if (textMaxCharacterLength != -1) {
            editText.filters = arrayOf(InputFilter.LengthFilter(textMaxCharacterLength))
        }

        onFocusListener(OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (editText.text.isNotEmpty()) {
                    clearIconVisibility(true)
                    clearLeftIconVisibility(true)
                }
            } else {
                clearLeftIconVisibility(false)
                clearIconVisibility(false)
            }
        })

        return editText
    }


    private fun getFrameLayoutForLeftImage(): FrameLayout {
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        if (isLeftIcon())
            frameLayout.addView(getLeftImageView(leftIconTintColor)) // ADD LeftImageView

        if (isLeftClearIcon!!)
            frameLayout.addView(getLeftClearImageButton(clearLeftIconTintColor)) // ADD leftClearIconView

        return frameLayout
    }

    private fun getLeftImageView(tintColor: Int): ImageView {
        leftImageView = AppCompatImageView(context)
        leftImageView.layoutParams = FrameLayout.LayoutParams(leftIconWidth.toInt(), leftIconHeight.toInt())

        if (isLeftIcon())
            leftImageView.setImageResource(leftIcon!!)

        leftImageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

        return leftImageView
    }

    private fun isLeftIcon(): Boolean {
        return leftIcon != null && leftIcon != 0
    }

    private fun getLeftClearImageButton(tintColor: Int): ImageView {
        clearLeftIconView = AppCompatImageView(context)
        clearLeftIconView.layoutParams = FrameLayout.LayoutParams(clearLeftIconWidth.toInt(), clearLeftIconHeight.toInt())

        clearLeftIconView.visibility = View.GONE

        if (clearLeftIcon != null && clearLeftIcon != 0) {
            clearLeftIconView.setImageResource(clearLeftIcon!!)
        } else {
            clearLeftIconView.setImageResource(android.R.drawable.ic_notification_clear_all)
        }

        clearLeftIconView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        setRippleBorderLessEffect(clearLeftIconView)

        clearLeftIconView.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                setCardText("")
            }
        }

        return clearLeftIconView
    }


    private fun getClearImageButton(tintColor: Int): ImageView {
        clearIconView = AppCompatImageView(context)
        val layoutParams = RelativeLayout.LayoutParams(clearIconWidth.toInt(), clearIconHeight.toInt())
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        clearIconView.layoutParams = layoutParams

        clearIconView.visibility = View.GONE

        if (clearIcon != null && clearIcon != 0) {
            clearIconView.setImageResource(clearIcon!!)
        } else {
            clearIconView.setImageResource(android.R.drawable.ic_notification_clear_all)
        }
        clearIconView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        setRippleBorderLessEffect(clearIconView)

        clearIconView.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                setCardText("")
            }
        }

        return clearIconView
    }

    private fun getInfoImageButton(tintColor: Int): ImageView {
        infoIconView = AppCompatImageView(context)
        val layoutParams = RelativeLayout.LayoutParams(infoIconWidth.toInt(), infoIconHeight.toInt())
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        infoIconView.layoutParams = layoutParams

        if (infoIcon != null && infoIcon != 0) {
            infoIconView.setImageResource(infoIcon!!)
        } else {
            infoIconView.setImageResource(android.R.drawable.ic_menu_info_details)
        }

        infoIconView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        setRippleBorderLessEffect(infoIconView)

        infoIconView.setOnClickListener {
            if (isInfoIcon!!) {
               CLBDialog.Builder.clbWarningDialog(context, infoMessageTitle, infoMessageDescription)
            }
        }

        return infoIconView
    }

    private fun getErrorTextView(): TextView {
        errorView = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        layoutParams.setMargins(0, 0, 0, 10)
        errorView.layoutParams = layoutParams

        errorView.id = ViewIDGenerator.mGenerateViewId()
        errorView.text = errorMessage
        errorView.gravity = Gravity.END
        errorView.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_MEDIUM)
        errorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
        errorView.setTextColor(errorColor)
        errorView.visibility = View.GONE

        return errorView
    }

    private fun clearIconVisibility(visible: Boolean) {
        if (isClearIcon!!) {
            if (visible) {
                clearIconView.visibility = View.VISIBLE
                clearIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200).setListener(object : MyAnimatorListener() {
                    override fun onAnimationStart(animator: Animator?) {
                        if (isInfoIcon!!) {
                            infoIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100).setListener(object : MyAnimatorListener() {
                                override fun onAnimationEnd(animator: Animator?) {
                                    infoIconView.visibility = View.GONE
                                }
                            })
                        }// isInfoIcon
                    }
                })

            } else {
                clearIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100).setListener(object : MyAnimatorListener() {
                    override fun onAnimationEnd(animator: Animator?) {
                        clearIconView.visibility = View.GONE
                        if (isInfoIcon!!) {
                            infoIconView.visibility = View.VISIBLE
                            infoIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200).setListener(object : MyAnimatorListener() {
                                override fun onAnimationEnd(animator: Animator?) {
                                    // (setListener(object : MyAnimatorListener()) BUNU SİLİNCE ANLAMSIZ BİR ŞEKİLDE BOZULUYOR
                                }
                            })
                        }// isLeftIcon
                    }
                })
            }
        }
    }

    private fun clearLeftIconVisibility(visible: Boolean) {
        if (isLeftClearIcon!!) {
            if (visible) {
                clearLeftIconView.visibility = View.VISIBLE
                clearLeftIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200).setListener(object : MyAnimatorListener() {
                    override fun onAnimationStart(animator: Animator?) {
                        if (isLeftIcon()) {
                            leftImageView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100).setListener(object : MyAnimatorListener() {
                                override fun onAnimationEnd(animator: Animator?) {
                                    leftImageView.visibility = View.GONE
                                }
                            })
                        }// isLeftIcon
                    }
                })
            } else {
                clearLeftIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100).setListener(object : MyAnimatorListener() {
                    override fun onAnimationEnd(animator: Animator?) {
                        clearLeftIconView.visibility = View.GONE
                        if (isLeftIcon()) {
                            leftImageView.visibility = View.VISIBLE
                            leftImageView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200).setListener(object : MyAnimatorListener() {
                                override fun onAnimationEnd(animator: Animator?) {
                                    // BUNU SİLİNCE ANLAMSIZ BİR ŞEKİLDE BOZULUYOR
                                }
                            })
                        }// isLeftIcon
                    }
                })
            }
        }
    }

    fun addTextWatcherListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    private fun onFocusListener(listener: View.OnFocusChangeListener) {
        editText.onFocusChangeListener = listener
    }

    fun setEditTextOnClickListener(listener: View.OnClickListener) {
        editText.setOnClickListener(listener)
    }

    fun setSecurityTextForPassword() {
        editText.transformationMethod = PasswordTransformationMethod()
    }

    fun setDigits(digits: String) {
        editText.keyListener = DigitsKeyListener.getInstance(digits)
    }

    fun getEditText(): EditText {
        return editText
    }

    fun getCardText(): CharSequence {
        return editText.text
    }

    fun setCardText(text: CharSequence) {
        editText.setText(text)
    }

    fun setCardText(@StringRes resId: Int) {
        editText.setText(resId)
    }

    fun setClearTextWithClearError(){
        setCardText("")
        clearError()
    }

    fun setCardHint(text: CharSequence) {
        editText.hint = text
    }

    fun setCardHint(@StringRes resId: Int) {
        editText.setHint(resId)
    }

    fun setCardEditTextMaxLength(limit: Int) {
        editText.filters = arrayOf(InputFilter.LengthFilter(limit))
    }

    fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
        this.errorView.text = this.errorMessage
        this.errorView.visibility = View.VISIBLE
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

    /** JUST isInfoIcon == true*/
    fun setInfoMessageTitle(messageTitle: String) {
        this.infoMessageTitle = messageTitle
    }

    fun setInfoMessageDescription(messageDescription: String) {
        this.infoMessageDescription = messageDescription
    }

    fun setInfoMessage(messageTitle: String, messageDescription: String) {
        this.infoMessageTitle = messageTitle
        this.infoMessageDescription = messageDescription
    }

    /** JUST isInfoIcon == true END*/

    fun listenErrorMessageTextWatcher() {
        addTextWatcherListener(object : MyTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    clearError()
                } else {
                    setError()
                }
            }
        })
    }

    private fun listenChangeImageViewListener() {
        addTextWatcherListener(object : MyTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    clearLeftIconVisibility(true)
                    clearIconVisibility(true)
                } else {
                    clearLeftIconVisibility(false)
                    clearIconVisibility(false)
                }
            }
        })
    }

    private fun toSP(sp: Float): Float {
        return sp * resources.displayMetrics.scaledDensity
    }
}