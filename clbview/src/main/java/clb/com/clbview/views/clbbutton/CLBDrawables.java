package clb.com.clbview.views.clbbutton;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by EMRE CELEBI on 6/21/18. cLB
 */

public class CLBDrawables {

    @NonNull
    public static Drawable getSelectableDrawableFor(int color, int cornerRadius) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    getDefaultColor(lightenOrDarken(color, 0.30D), cornerRadius)
            );
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_focused},
                    getDefaultColor(lightenOrDarken(color, 0.40D), cornerRadius)
            );
            stateListDrawable.addState(
                    new int[]{},
                    getDefaultColor(color, cornerRadius)
            );
            return stateListDrawable;
        } else {
            ColorStateList pressedColor = ColorStateList.valueOf(lightenOrDarken(color, 0.40D));
            Drawable defaultColor = getDefaultColor(color, cornerRadius);
            Drawable rippleColor = getRippleColor(color, cornerRadius);
            return new RippleDrawable(pressedColor, defaultColor, rippleColor);
        }
    }

    @NonNull
    private static Drawable getRippleColorGradient(int color, int cornerRadius) {
        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, cornerRadius);
        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    @NonNull
    private static Drawable getRippleColor(int color, int cornerRadius) {
        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, cornerRadius);
        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);

        return shapeDrawable;
    }

    @NonNull
    private static Drawable getDefaultColor(int color, int cornerRadius) {
        //  GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{0xFF616261, 0xFF131313});

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(2, color);
        gradientDrawable.setCornerRadius(cornerRadius);
        return gradientDrawable;
    }

    private static int lightenOrDarken(int color, double fraction) {
        if (canLighten(color, fraction)) {
            return lighten(color, fraction);
        } else {
            return darken(color, fraction);
        }
    }

    private static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);

        return Color.argb(alpha, red, green, blue);
    }

    private static boolean canLighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return canLightenComponent(red, fraction)
                && canLightenComponent(green, fraction)
                && canLightenComponent(blue, fraction);
    }

    private static boolean canLightenComponent(int colorComponent, double fraction) {
        int red = Color.red(colorComponent);
        int green = Color.green(colorComponent);
        int blue = Color.blue(colorComponent);
        return red + (red * fraction) < 255
                && green + (green * fraction) < 255
                && blue + (blue * fraction) < 255;
    }

    private static int darkenColor(int color, double fraction) {
        return (int) Math.max(color - (color * fraction), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }
}

/*
*
    private static ShapeDrawable createDrawable(int radius, int color) {
        float[] outerRadius = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        // float[] outerRadius = new float[]{radius, 0, 0, 0, 0, 0, 0, 0};
        RoundRectShape roundRect = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRect);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
* */