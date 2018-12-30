package clb.com.clbview.utils

import android.content.Context
import android.graphics.Typeface

/**
 * Created by Emre CELEBI on 30.12.2018. cLB
 * clb.emrx
 */
class Utils {

    companion object {
        fun getRobotoFont(context: Context, fontType: RobotoFontType): Typeface {
            return when (fontType.type) {
                RobotoFontType.RB_MEDIUM.type -> Typeface.createFromAsset(context.assets, "roboto/Roboto-Medium.ttf")
                RobotoFontType.RB_REGULAR.type -> Typeface.createFromAsset(context.assets, "roboto/Roboto-Regular.ttf")
                RobotoFontType.RB_CONDENSED.type -> Typeface.createFromAsset(context.assets, "roboto/Roboto-Condensed.ttf")
                RobotoFontType.RB_BOLD_CONDENSED.type -> Typeface.createFromAsset(context.assets, "roboto/Roboto-BoldCondensed.ttf")
                RobotoFontType.RB_BOLD_CONDENSED_ITALIC.type -> Typeface.createFromAsset(context.assets, "roboto/Roboto-BoldCondensedItalic.ttf")
                else -> Typeface.createFromAsset(context.assets, "roboto/Roboto-Regular.ttf")

            }
        }
    }
}