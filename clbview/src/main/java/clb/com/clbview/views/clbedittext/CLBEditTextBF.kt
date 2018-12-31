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
import clb.com.clbview.views.CLBBaseLayoutView
import clb.com.clbview.views.clbdialog.CLBDialog
import clb.com.clbview.views.clbdialog.clbWarningDialog


/**
 * Created by EMRE CELEBI on 3/15/18. cLB
 */
class CLBEditTextBF : CLBBaseLayoutView {

    private lateinit var editText: EditText
    private lateinit var errorView: TextView
    private lateinit var floatingLabel: TextView
    private lateinit var clearIconView: AppCompatImageView
    private lateinit var infoIconView: AppCompatImageView
    private lateinit var leftImageView: AppCompatImageView
    private lateinit var clearLeftIconView: AppCompatImageView

    private var cbfBackgroundColor: Int = Color.WHITE
    private var cbfInputType: Int? = null

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

    private var cbfSingleLine: Boolean = true
    private var textColor: Int = Color.parseColor("#F57C00") // orange_700
    private var floatingTextColor: Int = Color.parseColor("#BDBDBD") // grey
    private var hintTextColor: Int = Color.parseColor("#BDBDBD") // grey
    private var errorColor: Int = Color.parseColor("#D32F2F")

    private var textSize: Int = toSP(18F).toInt()
    private var hintSize: Int = toSP(16F).toInt()
    private var floatingTextSize: Int = toSP(16F).toInt()
    private var textMaxCharacterLength: Int = -1

    private var cbfText: String = ""
    private var floatingText: String = ""
    private var cbfHint: String = ""
    private var errorMessage: String = ""

    private var infoMessageTitle: String = ""
    private var infoMessageDescription: String = ""

    private var floatingLabelTranslationY = 50.0F

    private var blockCompletionFocus: (() -> Unit)? = null
    private var blockCompletionAfterTextWatcher: ((Editable?) -> Unit)? = null

    // TODO HINT KALDIR- FLOATING LABEL HINT OLARAK KULLANILDI
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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CLBEditTextBF, 0, 0)
            ?: return

        cbfBackgroundColor = typedArray.getColor(R.styleable.CLBEditTextBF_cbf_background_color, Color.WHITE)
        cbfInputType = typedArray.getInt(R.styleable.CLBEditTextBF_android_inputType, EditorInfo.TYPE_NULL)

        leftIcon = typedArray.getResourceId(R.styleable.CLBEditTextBF_clb_icon_left, 0)
        leftIconTintColor =
                typedArray.getColor(R.styleable.CLBEditTextBF_clb_icon_left_tint_color, Color.parseColor("#42A5F5"))
        leftIconWidth = typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_left_width, toDp(25.0F).toFloat())
        leftIconHeight = typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_left_height, toDp(25.0F).toFloat())

        isClearIcon = typedArray.getBoolean(R.styleable.CLBEditTextBF_clb_is_clear_icon, false)
        clearIcon = typedArray.getResourceId(R.styleable.CLBEditTextBF_clb_icon_clear, 0)
        clearIconTintColor =
                typedArray.getColor(R.styleable.CLBEditTextBF_clb_icon_clear_tint_color, Color.parseColor("#EF5350"))
        clearIconWidth = typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_clear_width, toDp(25.0F).toFloat())
        clearIconHeight =
                typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_clear_height, toDp(25.0F).toFloat())

        isLeftClearIcon = typedArray.getBoolean(R.styleable.CLBEditTextBF_clb_is_left_clear_icon, false)
        clearLeftIcon = typedArray.getResourceId(R.styleable.CLBEditTextBF_clb_icon_left_clear, 0)
        clearLeftIconTintColor = typedArray.getColor(
            R.styleable.CLBEditTextBF_clb_left_icon_clear_tint_color,
            Color.parseColor("#EF5350")
        )
        clearLeftIconWidth =
                typedArray.getDimension(R.styleable.CLBEditTextBF_clb_left_icon_clear_width, toDp(25.0F).toFloat())
        clearLeftIconHeight =
                typedArray.getDimension(R.styleable.CLBEditTextBF_clb_left_icon_clear_height, toDp(25.0F).toFloat())

        isInfoIcon = typedArray.getBoolean(R.styleable.CLBEditTextBF_clb_is_info_icon, false)
        infoIcon = typedArray.getResourceId(R.styleable.CLBEditTextBF_clb_icon_info, 0)
        infoIconTintColor =
                typedArray.getColor(R.styleable.CLBEditTextBF_clb_icon_info_tint_color, Color.parseColor("#42A5F5"))
        infoIconWidth = typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_info_width, toDp(25.0F).toFloat())
        infoIconHeight = typedArray.getDimension(R.styleable.CLBEditTextBF_clb_icon_info_height, toDp(25.0F).toFloat())

        cbfSingleLine = typedArray.getBoolean(R.styleable.CLBEditTextBF_clb_single_line, true)
        textColor = typedArray.getColor(R.styleable.CLBEditTextBF_clb_text_color, Color.parseColor("#F57C00"))
        floatingTextColor =
                typedArray.getColor(R.styleable.CLBEditTextBF_cbf_floating_text_color, Color.parseColor("#BDBDBD"))
        hintTextColor = typedArray.getColor(R.styleable.CLBEditTextBF_clb_hint_text_color, Color.parseColor("#BDBDBD"))
        errorColor = typedArray.getColor(R.styleable.CLBEditTextBF_clb_error_color, Color.parseColor("#D32F2F"))

        textSize = typedArray.getDimensionPixelSize(R.styleable.CLBEditTextBF_clb_text_size, toSP(18F).toInt())
        floatingTextSize =
                typedArray.getDimensionPixelSize(R.styleable.CLBEditTextBF_cbf_floating_text_size, toSP(16F).toInt())
        hintSize = typedArray.getDimensionPixelSize(R.styleable.CLBEditTextBF_clb_hint_size, toSP(16F).toInt())

        textMaxCharacterLength = typedArray.getInteger(R.styleable.CLBEditTextBF_clb_text_max_character, -1)

        cbfText = if (typedArray.getString(R.styleable.CLBEditTextBF_clb_text) != null) {
            typedArray.getString(R.styleable.CLBEditTextBF_clb_text)
        } else {
            ""
        }

        floatingText = if (typedArray.getString(R.styleable.CLBEditTextBF_cbf_floating_text) != null) {
            typedArray.getString(R.styleable.CLBEditTextBF_cbf_floating_text)
        } else {
            ""
        }

        cbfHint = if (typedArray.getString(R.styleable.CLBEditTextBF_clb_hint) != null) {
            typedArray.getString(R.styleable.CLBEditTextBF_clb_hint)
        } else {
            ""
        }

        errorMessage = if (typedArray.getString(R.styleable.CLBEditTextBF_clb_error_message) != null) {
            typedArray.getString(R.styleable.CLBEditTextBF_clb_error_message)
        } else {
            ""
        }

        typedArray.recycle()
    }

    private fun initialSubViews() {
        addSubView(getFrameLayoutForLeftImage()) // ADD FrameLayoutForLeftImage
        addSubView(addFrameLayoutParentEditTextWithErrorMessageLayout()) // ADD ParentView
        listenChangeImageViewListener() // listenChangeImageViewListener
        listenFloatingLabel() // listenFloatingLabel
        listenTextWatcherOut() // listenTextWatcherOut
        setParentOnClickListener(View.OnClickListener {
            if (editText.isFocusable) {
                editText.requestFocus()
            }
        })
    }

    private fun addFrameLayoutParentEditTextWithErrorMessageLayout(): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F)
        layoutParams.setMargins(toDp(5.0F), 0, 0, 0)
        frameLayout.layoutParams = layoutParams

        frameLayout.addView(parentEditTextWithClearWithInfoIconLayout())
        frameLayout.addView(getErrorTextView())
        return frameLayout
    }

    private fun parentEditTextWithClearWithInfoIconLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        layout.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        layout.addView(addFloatingEditTextLayout())

        if (isClearIcon!!)
            layout.addView(getClearImageButton(clearIconTintColor)) // ADD ClearImageButton

        if (isInfoIcon!!)
            layout.addView(getInfoImageButton(infoIconTintColor)) // ADD InfoImageButton

        return layout
    }

    private fun addFloatingEditTextLayout(): FrameLayout {
        val layout = FrameLayout(context)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        layout.layoutParams = layoutParams
        /*layout.gravity = Gravity.CENTER
        layout.orientation = LinearLayout.VERTICAL*/
        layout.setPadding(toDp(10F), toDp(10F), toDp(10F), toDp(10F))

        layout.addView(addEditText())
        layout.addView(addFloatingLabel())
        return layout
    }

    private fun addFloatingLabel(): TextView {
        floatingLabel = TextView(context)
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        floatingLabel.layoutParams = layoutParams

        floatingLabel.text = floatingText
        floatingLabel.id = ViewIDGenerator.mGenerateViewId()
        floatingLabel.gravity = Gravity.CENTER
        if (editText.text.isNotEmpty()) {
            floatingLabel.translationY = floatingLabelTranslationY
        } else {
            floatingLabel.translationY = 0.0F
        }
        floatingLabel.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_CONDENSED)
        floatingLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, floatingTextSize.toFloat())
        floatingLabel.setTextColor(floatingTextColor)
        return floatingLabel
    }

    private fun addEditText(): EditText {
        editText = EditText(context)
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        editText.layoutParams = layoutParams

        editText.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        editText.hint = ""
        editText.gravity = Gravity.CENTER
        editText.setText(cbfText)
        editText.setPadding(0, 0, 0, 0)
        editText.setSingleLine(cbfSingleLine)
        editText.setHintTextColor(hintTextColor)
        editText.setTextColor(textColor)
        if (editText.text.isNotEmpty()) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        } else {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize.toFloat())
        }
        JavaHelper.setCursorDrawableColor(
            editText, ContextCompat.getColor(
                context,
                R.color.md_orange_300
            )
        )
        JavaHelper.colorHandles(editText, ContextCompat.getColor(context, R.color.md_orange_300))
        editText.highlightColor = ContextCompat.getColor(context, R.color.md_orange_100)

        editText.typeface = Utils.getRobotoFont(context, RobotoFontType.RB_CONDENSED)

        if (cbfInputType != EditorInfo.TYPE_NULL) {
            editText.inputType = cbfInputType!!
        }

        if (textMaxCharacterLength != -1) {
            editText.filters = arrayOf(InputFilter.LengthFilter(textMaxCharacterLength))
        }

        onFocusListener(OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                blockCompletionFocus?.let { it() }
                selectedLayout(true)
                if (editText.text.isNotEmpty()) {
                    clearIconVisibility(true)
                    clearLeftIconVisibility(true)
                }
            } else {
                selectedLayout(false)
                clearLeftIconVisibility(false)
                clearIconVisibility(false)
            }
        })

        return editText
    }


    private fun getFrameLayoutForLeftImage(): FrameLayout {
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

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
        clearLeftIconView.layoutParams =
                FrameLayout.LayoutParams(clearLeftIconWidth.toInt(), clearLeftIconHeight.toInt())

        clearLeftIconView.visibility = View.INVISIBLE

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

        clearIconView.visibility = View.INVISIBLE

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
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
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
                clearIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200)
                    .setListener(object : MyAnimatorListener() {
                        override fun onAnimationStart(animator: Animator?) {
                            if (isInfoIcon!!) {
                                infoIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100)
                                    .setListener(object : MyAnimatorListener() {
                                        override fun onAnimationEnd(animator: Animator?) {
                                            infoIconView.visibility = View.INVISIBLE
                                        }
                                    })
                            }// isInfoIcon
                        }
                    })

            } else {
                clearIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100)
                    .setListener(object : MyAnimatorListener() {
                        override fun onAnimationEnd(animator: Animator?) {
                            clearIconView.visibility = View.INVISIBLE
                            if (isInfoIcon!!) {
                                infoIconView.visibility = View.VISIBLE
                                infoIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200)
                                    .setListener(object : MyAnimatorListener() {
                                        override fun onAnimationEnd(animator: Animator?) {
                                            // (setListener) BUNU SİLİNCE ANLAMSIZ BİR ŞEKİLDE BOZULUYOR
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
                clearLeftIconView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200)
                    .setListener(object : MyAnimatorListener() {
                        override fun onAnimationStart(animator: Animator?) {
                            if (isLeftIcon()) {
                                leftImageView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100)
                                    .setListener(object : MyAnimatorListener() {
                                        override fun onAnimationEnd(animator: Animator?) {
                                            leftImageView.visibility = View.INVISIBLE
                                        }
                                    })
                            }// isLeftIcon
                        }
                    })
            } else {
                clearLeftIconView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(100)
                    .setListener(object : MyAnimatorListener() {
                        override fun onAnimationEnd(animator: Animator?) {
                            clearLeftIconView.visibility = View.INVISIBLE
                            if (isLeftIcon()) {
                                leftImageView.visibility = View.VISIBLE
                                leftImageView.animate().scaleX(1.0F).scaleY(1.0F).setDuration(200)
                                    .setListener(object : MyAnimatorListener() {
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

    private fun floatingLabelScaleWithTranslationAnimation(isFloating: Boolean) {
        if (isFloating) {
            floatingLabel.animate().scaleX(0.8F).scaleY(0.8F).translationY(floatingLabelTranslationY).setDuration(100)
                .setListener(object : MyAnimatorListener() {
                    override fun onAnimationStart(animator: Animator?) {

                    }
                })

        } else {
            floatingLabel.animate().scaleX(1F).scaleY(1F).translationY(0F).setDuration(100)
                .setListener(object : MyAnimatorListener() {
                    override fun onAnimationStart(animator: Animator?) {

                    }
                })
        }
    }

    fun addTextWatcherListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    private fun onFocusListener(listener: View.OnFocusChangeListener) {
        editText.onFocusChangeListener = listener
    }

    fun completionFocusListener(block: (() -> Unit)? = null) {
        blockCompletionFocus = block
    }

    fun completionOnClickListener(block: (() -> Unit)? = null) {
        setEditTextOnClickListener(View.OnClickListener {
            block?.let { it1 -> it1() }
            if (editText.isFocusable) {
                editText.requestFocus()
            }
        })
    }

    fun completionAfterTextWatcher(block: ((Editable?) -> Unit)? = null) {
        blockCompletionAfterTextWatcher = block
    }

    fun setEditTextOnClickListener(listener: View.OnClickListener) {
        editText.setOnClickListener(listener)
        setParentOnClickListener(listener)
    }

    fun setDisableCursorMove() {
        editText.movementMethod = null
        editText.isCursorVisible = false
    }

    fun setSecurityTextForPassword() {
        editText.transformationMethod = PasswordTransformationMethod()
    }

    fun setDigits(digits: String) {
        editText.keyListener = DigitsKeyListener.getInstance(digits)
    }

    fun setEditTextAllCaps() {
        val editFilters = editText.filters
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = InputFilter.AllCaps()
        editText.filters = newFilters
    }

    fun setEditTextSize(textSize: Int) {
        this.textSize = textSize
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

    }

    fun getEditText(): EditText {
        return editText
    }

    fun getCardText(): CharSequence {
        return editText.text
    }

    fun setCardText(text: CharSequence) {
        editText.setText(text)
        clearIconVisibility(false)
        clearLeftIconVisibility(false)
    }

    fun setCardText(@StringRes resId: Int) {
        editText.setText(resId)
    }

    fun setCardHint(text: CharSequence) {
        // editText.hint = text
        floatingLabel.text = text
    }

    fun setCardHint(@StringRes resId: Int) {
        editText.setHint(resId)
        floatingLabel.setText(resId)
    }

    fun setCardEditTextMaxLength(limit: Int) {
        editText.filters = arrayOf(InputFilter.LengthFilter(limit))
    }

    fun setError() {
        this.errorView.visibility = View.VISIBLE
    }

    fun clearError() {
        errorView.visibility = View.GONE
    }

    fun clearEditTextFocus() {
        editText.clearFocus()
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

    /** JUST isInfoIcon == true*/

    private fun listenTextWatcherOut() {
        addTextWatcherListener(object : MyTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                blockCompletionAfterTextWatcher?.let { it(s) }
            }
        })
    }

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

    private fun listenFloatingLabel() {
        addTextWatcherListener(object : MyTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
                    floatingLabelScaleWithTranslationAnimation(true)
                } else {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize.toFloat())
                    floatingLabelScaleWithTranslationAnimation(false)
                }
            }
        })
    }

    private fun toSP(sp: Float): Float {
        return sp * resources.displayMetrics.scaledDensity
    }
}