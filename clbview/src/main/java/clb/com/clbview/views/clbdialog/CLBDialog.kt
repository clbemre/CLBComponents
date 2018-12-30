package clb.com.clbview.views.clbdialog

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import clb.com.clbview.R
import clb.com.clbview.listener.MyAnimationListener
import kotlinx.android.synthetic.main.dialog_clb_container.*

/**
 * Created by bt on 3/12/18. cLB
 */
class CLBDialog private constructor(context: Context,
                                    private var title: String?,
                                    private var message: String?,
                                    private var isAutoDismiss: Boolean = true) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    private var isPositiveButton: Boolean? = null
    private var positiveText: String? = null
    private var positiveBlock: ((dialog: CLBDialog?) -> Unit)? = null

    private var isNegativeButton: Boolean? = null
    private var negativeText: String? = null
    private var negativeBlock: ((dialog: CLBDialog?) -> Unit)? = null

    private var isNeutralButton: Boolean? = null
    private var neutralText: String? = null
    private var neutralBlock: ((dialog: CLBDialog?) -> Unit)? = null

    private var dialogType: CLBDialog.Type? = null
    private var buttonType: CLBDialog.ButtonType? = null

    /*
    * SUCCESS
    * */
    private var imageSuccess = R.drawable.icon_tick_large
    private var colorSuccessBackground = R.color.md_light_green_50
    private var colorSuccessImageTint = R.color.md_light_green_100
    private var colorSuccessTitle = R.color.md_light_green_500
    private var colorSuccessMesaj = R.color.md_light_green_400
    private var colorSuccessPositiveButton = R.color.md_light_green_700
    private var colorSuccessNegativeButton = R.color.md_light_green_400
    private var colorSuccessNeutralButton = R.color.md_light_green_200

    /*
    * WARNING
    * */
    private var imageWarning = R.drawable.icon_warning_large
    private var colorWarningBackground = R.color.md_cyan_50
    private var colorWarningImageTint = R.color.md_cyan_100
    private var colorWarningTitle = R.color.md_cyan_500
    private var colorWarningMesaj = R.color.md_cyan_400
    private var colorWarningPositiveButton = R.color.md_cyan_700
    private var colorWarningNegativeButton = R.color.md_cyan_400
    private var colorWarningNeutralButton = R.color.md_cyan_200

    /*
    * QUESTION
    * */
    private var imageQuestion = R.drawable.icon_question_large
    private var colorQuestionBackground = R.color.md_orange_50
    private var colorQuestionImageTint = R.color.md_orange_100
    private var colorQuestionTitle = R.color.md_orange_500
    private var colorQuestionMesaj = R.color.md_orange_400
    private var colorQuestionPositiveButton = R.color.md_orange_700
    private var colorQuestionNegativeButton = R.color.md_orange_400
    private var colorQuestionNeutralButton = R.color.md_orange_200

    /*
    * ERROR
    * */
    private var imageError = R.drawable.icon_cross_large
    private var colorErrorBackground = R.color.md_red_50
    private var colorErrorImageTint = R.color.md_red_100
    private var colorErrorTitle = R.color.md_red_500
    private var colorErrorMesaj = R.color.md_red_400
    private var colorErrorPositiveButton = R.color.md_red_700
    private var colorErrorNegativeButton = R.color.md_red_400
    private var colorErrorNeutralButton = R.color.md_red_200

    /*
    * CONTAINER
    * */
    private var colorContainerBackground: Int? = null
    private var imageContainer: Int? = null
    private var colorContainerImageTint: Int? = null
    private var colorContainerTitle: Int? = null
    private var colorContainerMessage: Int? = null
    private var colorContainerPositiveButton: Int? = null
    private var colorContainerPositiveButtonText: Int? = null
    private var colorContainerNegativeButton: Int? = null
    private var colorContainerNegativeButtonText: Int? = null
    private var colorContainerNeutralButton: Int? = null
    private var colorContainerNeutralButtonText: Int? = null

    private constructor(context: Context, dialogType: CLBDialog.Type?, buttonType: CLBDialog.ButtonType?, title: String?, message: String?,
                        isPositiveButton: Boolean?, positiveText: String?, positiveButtonTextColorID: Int?, positiveBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isNegativeButton: Boolean?, negativeText: String?, negativeButtonTextColorID: Int?, negativeBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isNeutralButton: Boolean?, neutralText: String?, neutralButtonTextColorID: Int?, neutralBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isAutoDismiss: Boolean) : this(context, title, message, isAutoDismiss) {

        this.dialogType = dialogType
        this.buttonType = buttonType

        this.isPositiveButton = isPositiveButton
        this.positiveText = positiveText
        this.colorContainerPositiveButtonText = positiveButtonTextColorID
        this.positiveBlock = positiveBlock

        this.isNegativeButton = isNegativeButton
        this.negativeText = negativeText
        this.colorContainerNegativeButtonText = negativeButtonTextColorID
        this.negativeBlock = negativeBlock

        this.isNeutralButton = isNeutralButton
        this.neutralText = neutralText
        this.colorContainerNeutralButtonText = neutralButtonTextColorID
        this.neutralBlock = neutralBlock

    }

    // FOR CUSTOM
    private constructor(context: Context, dialogType: CLBDialog.Type?, buttonType: CLBDialog.ButtonType?, title: String?, message: String?,
                        isPositiveButton: Boolean?, positiveText: String?, positiveButtonTextColorID: Int?, positiveButtonBackgroundColorID: Int?, positiveBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isNegativeButton: Boolean?, negativeText: String?, negativeButtonTextColorID: Int?, negativeButtonBackgroundColorID: Int?, negativeBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isNeutralButton: Boolean?, neutralText: String?, neutralButtonTextColorID: Int?, neutralButtonBackgroundColorID: Int?, neutralBlock: ((dialog: CLBDialog?) -> Unit)?,
                        isAutoDismiss: Boolean, containerBackgroundColorID: Int?, imageDrawableID: Int?, imageTintColorID: Int?, titleColorID: Int?, messageColorID: Int?) : this(context, title, message, isAutoDismiss) {

        this.dialogType = dialogType
        this.buttonType = buttonType

        this.colorContainerBackground = containerBackgroundColorID

        this.imageContainer = imageDrawableID
        this.colorContainerImageTint = imageTintColorID

        this.colorContainerTitle = titleColorID
        this.colorContainerMessage = messageColorID

        this.isPositiveButton = isPositiveButton
        this.positiveText = positiveText
        this.colorContainerPositiveButton = positiveButtonBackgroundColorID
        this.colorContainerPositiveButtonText = positiveButtonTextColorID
        this.positiveBlock = positiveBlock

        this.isNegativeButton = isNegativeButton
        this.negativeText = negativeText
        this.colorContainerNegativeButton = negativeButtonBackgroundColorID
        this.colorContainerNegativeButtonText = negativeButtonTextColorID
        this.negativeBlock = negativeBlock


        this.isNeutralButton = isNeutralButton
        this.neutralText = neutralText
        this.colorContainerNeutralButton = neutralButtonBackgroundColorID
        this.colorContainerNeutralButtonText = neutralButtonTextColorID
        this.neutralBlock = neutralBlock
    }

    override fun onStart() {
        super.onStart()
        initialProperties()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (buttonType == ButtonType.VERTICAL) {
            setContentView(R.layout.dialog_clb_container)
        } else if (buttonType == ButtonType.HORIZONTAL) {
            setContentView(R.layout.dialog_clb_container_horizontal_button)
        }
        initializeComponents()
        setCancelable(false)
    }

    private fun initialProperties() {
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.CENTER
        window.attributes = lp
    }

    private fun initializeComponents() {
        initialDialogTheme()
        parentContainerCardView.startAnimation(openAnimation())
        txtContainerDialogTitle.text = title
        txtContainerDialogMessage.text = message

        if (isPositiveButton!!) {
            setPositiveButton(positiveText!!, positiveBlock)
        }

        if (isNegativeButton!!) {
            setNegativeButton(negativeText!!, negativeBlock)
        }

        if (isNeutralButton!!) {
            setNeutralButton(neutralText!!, neutralBlock)
        }
    }

    private fun initialDialogTheme() {
        when (dialogType) {
            Type.SUCCESS -> {
                imageContainer = imageSuccess
                colorContainerBackground = colorSuccessBackground
                colorContainerImageTint = colorSuccessImageTint
                colorContainerTitle = colorSuccessTitle
                colorContainerMessage = colorSuccessMesaj
                colorContainerPositiveButton = colorSuccessPositiveButton
                colorContainerNegativeButton = colorSuccessNegativeButton
                colorContainerNeutralButton = colorSuccessNeutralButton
            }
            Type.WARNING -> {
                imageContainer = imageWarning
                colorContainerBackground = colorWarningBackground
                colorContainerImageTint = colorWarningImageTint
                colorContainerTitle = colorWarningTitle
                colorContainerMessage = colorWarningMesaj
                colorContainerPositiveButton = colorWarningPositiveButton
                colorContainerNegativeButton = colorWarningNegativeButton
                colorContainerNeutralButton = colorWarningNeutralButton
            }
            Type.QUESTION -> {
                imageContainer = imageQuestion
                colorContainerBackground = colorQuestionBackground
                colorContainerImageTint = colorQuestionImageTint
                colorContainerTitle = colorQuestionTitle
                colorContainerMessage = colorQuestionMesaj
                colorContainerPositiveButton = colorQuestionPositiveButton
                colorContainerNegativeButton = colorQuestionNegativeButton
                colorContainerNeutralButton = colorQuestionNeutralButton
            }
            Type.ERROR -> {
                imageContainer = imageError
                colorContainerBackground = colorErrorBackground
                colorContainerImageTint = colorErrorImageTint
                colorContainerTitle = colorErrorTitle
                colorContainerMessage = colorErrorMesaj
                colorContainerPositiveButton = colorErrorPositiveButton
                colorContainerNegativeButton = colorErrorNegativeButton
                colorContainerNeutralButton = colorErrorNeutralButton
            }

            Type.CUSTOM -> {
                // NOTHING HERE
            }

            null -> {
                imageContainer = imageSuccess
                colorContainerBackground = colorSuccessBackground
                colorContainerImageTint = colorSuccessImageTint
                colorContainerTitle = colorSuccessTitle
                colorContainerMessage = colorSuccessMesaj
                colorContainerPositiveButton = colorSuccessPositiveButton
                colorContainerNegativeButton = colorSuccessNegativeButton
                colorContainerNeutralButton = colorSuccessNeutralButton
            }
        }

        imgContainerBackgroundIcon.setImageDrawable(ContextCompat.getDrawable(context, imageContainer!!))
        imgContainerBackgroundIcon.setColorFilter(ContextCompat.getColor(context, colorContainerImageTint!!), PorterDuff.Mode.SRC_IN)

        parentContainerCardView.setCardBackgroundColor(ContextCompat.getColor(context, colorContainerBackground!!))

        txtContainerDialogTitle.setTextColor(ContextCompat.getColor(context, colorContainerTitle!!))
        txtContainerDialogMessage.setTextColor(ContextCompat.getColor(context, colorContainerMessage!!))

        if (isPositiveButton!!) {
            btnContainerDialogPositive.setCardBackgroundColor(ContextCompat.getColor(context, colorContainerPositiveButton!!))
            txtContainerDialogPositiveText.setTextColor(ContextCompat.getColor(context, colorContainerPositiveButtonText!!))
        }

        if (isNegativeButton!!) {
            btnContainerDialogNegative.setCardBackgroundColor(ContextCompat.getColor(context, colorContainerNegativeButton!!))
            txtContainerDialogNegativeText.setTextColor(ContextCompat.getColor(context, colorContainerNegativeButtonText!!))
        }

        if (isNeutralButton!!) {
            btnContainerDialogNeutral.setCardBackgroundColor(ContextCompat.getColor(context, colorContainerNeutralButton!!))
            txtContainerDialogNeutralText.setTextColor(ContextCompat.getColor(context, colorContainerNeutralButtonText!!))
        }
    }

    private fun setPositiveButton(text: CharSequence, block: ((dialog: CLBDialog?) -> Unit)? = null) {
        this.btnContainerDialogPositive.visibility = View.VISIBLE
        this.txtContainerDialogPositiveText.text = text
        this.btnContainerDialogPositive.setOnClickListener {
            parentContainerCardView.clearAnimation()
            if (isAutoDismiss) {
                parentLayout.startAnimation(closeAnimationAndCompletionHandler(block))
            } else {
                block?.let { it1 -> it1(this@CLBDialog) }
            }
        }
    }

    private fun setNegativeButton(text: CharSequence, block: ((dialog: CLBDialog?) -> Unit)? = null) {
        this.btnContainerDialogNegative.visibility = View.VISIBLE
        this.txtContainerDialogNegativeText.text = text
        this.btnContainerDialogNegative.setOnClickListener {
            parentContainerCardView.clearAnimation()
            if (isAutoDismiss) {
                parentLayout.startAnimation(closeAnimationAndCompletionHandler(block))
            } else {
                block?.let { it1 -> it1(this@CLBDialog) }
            }
        }
    }

    private fun setNeutralButton(text: CharSequence, block: ((dialog: CLBDialog?) -> Unit)? = null) {
        this.btnContainerDialogNeutral.visibility = View.VISIBLE
        this.txtContainerDialogNeutralText.text = text
        this.btnContainerDialogNeutral.setOnClickListener {
            parentContainerCardView.clearAnimation()
            if (isAutoDismiss) {
                parentLayout.startAnimation(closeAnimationAndCompletionHandler(block))
            } else {
                block?.let { it1 -> it1(this@CLBDialog) }
            }
        }
    }

    // TODO LATER ADD MORE ANIMATION (WITH ENUM SELECTION)
    private fun openAnimation(): Animation? {
        return AnimationUtils.loadAnimation(context, R.anim.slide_up_dialog)
    }

    private fun closeAnimationAndCompletionHandler(block: ((dialog: CLBDialog?) -> Unit)? = null): Animation? {
        val fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        fadeAnimation.setAnimationListener(object : MyAnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                block?.let { it(this@CLBDialog) }
                this@CLBDialog.dismiss()
            }
        })
        return fadeAnimation
    }

    /** DÜZELTME YAPILACAK .build() */
    class Builder(private var context: Context) {

        companion object;

        private var title: String? = null
        private var message: String? = null

        private var isPositiveButton: Boolean? = true
        private var positiveText: String? = null
        private var positiveBlock: ((dialog: CLBDialog?) -> Unit)? = null

        private var isNegativeButton: Boolean? = false
        private var negativeText: String? = null
        private var negativeBlock: ((dialog: CLBDialog?) -> Unit)? = null

        private var isNeutralButton: Boolean? = false
        private var neutralText: String? = null
        private var neutralBlock: ((dialog: CLBDialog?) -> Unit)? = null

        private var buttonType: ButtonType = ButtonType.VERTICAL

        /*
        * CUSTOM COLOR - Change it as you like.
        * */
        private var imageContainer: Int? = android.R.drawable.ic_dialog_alert
        private var backgroundColorID: Int? = android.R.color.holo_green_light
        private var imageTintColorID: Int? = android.R.color.holo_green_dark
        private var titleColorID: Int? = android.R.color.white
        private var messageColorID: Int? = android.R.color.white
        private var positiveButtonBackgroundColorID: Int? = android.R.color.holo_green_dark
        private var positiveButtonTextColorID: Int? = android.R.color.white
        private var negativeButtonBackgroundColorID: Int? = android.R.color.holo_green_dark
        private var negativeButtonTextColorID: Int? = android.R.color.white
        private var neutralButtonBackgroundColorID: Int? = android.R.color.holo_green_dark
        private var neutralButtonTextColorID: Int? = android.R.color.white


        fun withTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun withTitle(@StringRes title: Int): Builder {
            this.title = context.getString(title)
            return this
        }

        fun withMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun withMessage(@StringRes message: Int): Builder {
            this.message = context.getString(message)
            return this
        }

        fun withPositiveButton(positiveText: String?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            positiveText?.let {
                this.isPositiveButton = true
                this.positiveText = positiveText
                this.positiveBlock = block
            }
            return this
        }

        fun withPositiveButton(@StringRes positiveText: Int?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            positiveText?.let {
                this.isPositiveButton = true
                this.positiveText = context.getString(positiveText)
                this.positiveBlock = block
            }
            return this
        }

        fun withNegativeButton(negativeText: String?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            negativeText?.let {
                this.isNegativeButton = true
                this.negativeText = negativeText
                this.negativeBlock = block
            }
            return this
        }

        fun withNegativeButton(@StringRes negativeText: Int?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            negativeText?.let {
                this.isNegativeButton = true
                this.negativeText = context.getString(negativeText)
                this.negativeBlock = block
            }
            return this
        }

        fun withNeutralButton(neutralText: String?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            neutralText?.let {
                this.isNeutralButton = true
                this.neutralText = neutralText
                this.neutralBlock = block
            }
            return this
        }

        fun withNeutralButton(@StringRes neutralText: Int?, block: ((dialog: CLBDialog?) -> Unit)? = null): Builder {
            neutralText?.let {
                this.isNeutralButton = true
                this.neutralText = context.getString(neutralText)
                this.neutralBlock = block
            }
            return this
        }

        /**
         * @param buttonType -> Default (CLBDialog.ButtonType) VERTICAL, if you want to set HORIZONTAL
         * */
        fun buttonType(buttonType: ButtonType): Builder {
            this.buttonType = buttonType
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param drawableID Just resource id, image(R.id.exampleColorId)
         * @sample drawableID
         * @author cLb
         * */
        fun image(@DrawableRes drawableID: Int?): Builder {
            this.imageContainer = drawableID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param colorID Just resource id, image(R.id.exampleColorId)
         * @sample colorID
         * @author cLb
         * */
        fun background(@ColorRes colorID: Int?): Builder {
            this.backgroundColorID = colorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param tintColorID Just resource id, image(R.id.exampleColorId)
         * @sample tintColorID
         * @author cLb
         * */
        fun imageTint(@ColorRes tintColorID: Int?): Builder {
            this.imageTintColorID = tintColorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param colorID Just resource id, image(R.id.exampleColorId)
         * @sample colorID
         * @author cLb
         * */
        fun titleColor(@ColorRes colorID: Int?): Builder {
            this.titleColorID = colorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param colorID Just resource id, image(R.id.exampleColorId)
         * @sample colorID
         * @author cLb
         * */
        fun messageColor(@ColorRes colorID: Int?): Builder {
            this.messageColorID = colorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param backgroundColorID Just resource id, image(R.id.exampleColorId)
         * @sample backgroundColorID
         * @param textColorID Just resource id, image(R.id.exampleColorId)
         * @sample textColorID
         * @author cLb
         * */
        fun positiveButtonColor(@ColorRes backgroundColorID: Int?, @ColorRes textColorID: Int? = android.R.color.white): Builder {
            this.positiveButtonBackgroundColorID = backgroundColorID
            this.positiveButtonTextColorID = textColorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param backgroundColorID Just resource id, image(R.id.exampleColorId)
         * @sample backgroundColorID
         * @param textColorID Just resource id, image(R.id.exampleColorId)
         * @sample textColorID
         * @author cLb
         * */
        fun negativeButtonColor(@ColorRes backgroundColorID: Int?, @ColorRes textColorID: Int? = android.R.color.white): Builder {
            this.negativeButtonBackgroundColorID = backgroundColorID
            this.negativeButtonTextColorID = textColorID
            return this
        }

        /**
         * if type == CLBDialog.Type.CUSTOM else don't use
         * @param backgroundColorID Just resource id, image(R.id.exampleColorId)
         * @sample backgroundColorID
         * @param textColorID Just resource id, image(R.id.exampleColorId)
         * @sample textColorID
         * @author cLb
         * */
        fun neutralButtonColor(@ColorRes backgroundColorID: Int?, @ColorRes textColorID: Int? = android.R.color.white): Builder {
            this.neutralButtonBackgroundColorID = backgroundColorID
            this.neutralButtonTextColorID = textColorID
            return this
        }

        /**
         *
         * @param dialogType -> Default (CLBDialog.Type) SUCCESS, if you want to change these are  ->  SUCCESS,QUESTION,WARNING,ERROR,CUSTOM
         * @sample dialogType
         * @param isAutoDismiss -> Default true, if you do false -> please use dialog.dismiss() so completionBlock inside (it?.dismiss())
         * if you change the type of dialog: CUSTOM -> please use image(..), background(..), imageTint(..), titleColor(..), messageColor(..), positiveButtonColor(....), negativeButtonColor(..), neutralButtonColor(..)
         * @author cLB
         * */
        fun show(dialogType: CLBDialog.Type = CLBDialog.Type.SUCCESS, isAutoDismiss: Boolean = true) {
            if (dialogType == CLBDialog.Type.CUSTOM) {
                CLBDialog(context, dialogType, buttonType, title, message,
                        isPositiveButton, positiveText, positiveButtonTextColorID, positiveButtonBackgroundColorID, positiveBlock,
                        isNegativeButton, negativeText, negativeButtonTextColorID, negativeButtonBackgroundColorID, negativeBlock,
                        isNeutralButton, neutralText, neutralButtonTextColorID, neutralButtonBackgroundColorID, neutralBlock, isAutoDismiss,
                        backgroundColorID, imageContainer, imageTintColorID, titleColorID, messageColorID).show()
            } else {
                CLBDialog(context, dialogType, buttonType, title, message,
                        isPositiveButton, positiveText, positiveButtonTextColorID, positiveBlock,
                        isNegativeButton, negativeText, negativeButtonTextColorID, negativeBlock,
                        isNeutralButton, neutralText, neutralButtonTextColorID, neutralBlock,
                        isAutoDismiss).show()
            }

        }
    }

    enum class Type(val type: Int) {
        SUCCESS(0),
        QUESTION(1),
        WARNING(2),
        ERROR(3),
        CUSTOM(4)
    }

    enum class ButtonType(val type: Int) {
        VERTICAL(0),
        HORIZONTAL(1),

    }

}


fun CLBDialog.Builder.Companion.clbSuccessDialog(
        context: Context, title: String = "Başarılı!", message: String,
        positiveText: String? = "TAMAM", blockPositive: ((dialog: CLBDialog?) -> Unit)? = null,
        negativeText: String? = null, blockNegative: ((dialog: CLBDialog?) -> Unit)? = null,
        neutralText: String? = null, blockNeutral: ((dialog: CLBDialog?) -> Unit)? = null) {
    CLBDialog.Builder(context)
            .withTitle(title)
            .withMessage(message)
            .withPositiveButton(positiveText, blockPositive)
            .withNegativeButton(negativeText, blockNegative)
            .withNeutralButton(neutralText, blockNeutral)
            .show(CLBDialog.Type.SUCCESS)
}

fun CLBDialog.Builder.Companion.clbWarningDialog(
        context: Context, title: String = "Uyarı!", message: String,
        positiveText: String? = "TAMAM", blockPositive: ((dialog: CLBDialog?) -> Unit)? = null,
        negativeText: String? = null, blockNegative: ((dialog: CLBDialog?) -> Unit)? = null,
        neutralText: String? = null, blockNeutral: ((dialog: CLBDialog?) -> Unit)? = null) {
    CLBDialog.Builder(context)
            .withTitle(title)
            .withMessage(message)
            .withPositiveButton(positiveText, blockPositive)
            .withNegativeButton(negativeText, blockNegative)
            .withNeutralButton(neutralText, blockNeutral)
            .show(CLBDialog.Type.WARNING)
}

fun CLBDialog.Builder.Companion.clbQuestionDialog(
        context: Context, title: String = "?", message: String,
        positiveText: String? = "TAMAM", blockPositive: ((dialog: CLBDialog?) -> Unit)? = null,
        negativeText: String? = null, blockNegative: ((dialog: CLBDialog?) -> Unit)? = null,
        neutralText: String? = null, blockNeutral: ((dialog: CLBDialog?) -> Unit)? = null) {
    CLBDialog.Builder(context)
            .withTitle(title)
            .withMessage(message)
            .withPositiveButton(positiveText, blockPositive)
            .withNegativeButton(negativeText, blockNegative)
            .withNeutralButton(neutralText, blockNeutral)
            .show(CLBDialog.Type.QUESTION)
}

fun CLBDialog.Builder.Companion.clbErrorDialog(
        context: Context, title: String = "Hata!", message: String,
        positiveText: String? = "TAMAM", blockPositive: ((dialog: CLBDialog?) -> Unit)? = null,
        negativeText: String? = null, blockNegative: ((dialog: CLBDialog?) -> Unit)? = null,
        neutralText: String? = null, blockNeutral: ((dialog: CLBDialog?) -> Unit)? = null) {
    CLBDialog.Builder(context)
            .withTitle(title)
            .withMessage("$message Lütfen daha sonra tekrar deneyiniz.")
            .withPositiveButton(positiveText, blockPositive)
            .withNegativeButton(negativeText, blockNegative)
            .withNeutralButton(neutralText, blockNeutral)
            .show(CLBDialog.Type.ERROR)
}
