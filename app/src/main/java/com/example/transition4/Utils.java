package com.example.transition4;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.stream.IntStream;


/**
 * @author Konstantin Epifanov
 * @since 22.11.2019
 */
public class Utils {

  static int[] TEXT_COLORS = {
    0xFFF44336, 0xFFe91e63, 0xFF9C27B0, 0xFF673AB7, 0xFF3F51B5, 0xFF2196F3, 0xFF03A9F4, 0xFF00BCD4,
    0xFF009688, 0xFF4CAF50, 0xFF8BC34A, 0xFFFF9800, 0xFFFF5722, 0xFF607D8B
  };

  private static int[] BG_COLORS = IntStream
    .of(TEXT_COLORS)
    .map(colorInt -> mixTwoColors(colorInt, 0xFFFFFFFF, 0.2f))
    .toArray();

  public static void animateViewScale(View v1, float fromX, float toX, float fromY, float toY,
                                      int duration, Runnable after) {

    Animation anim = new ScaleAnimation(fromX, toX, // Start and end values for the X axis scaling
                                        fromY, toY, // Start and end values for the Y axis scaling
                                        Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                                        Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
    anim.setFillAfter(true); // Needed to keep the result of the animation
    anim.setDuration(duration); v1.startAnimation(anim); v1.postDelayed(after, duration);
  }

  public static int mixTwoColors(int c1, int c2, float strength) {
    final byte ALPHA_CHANNEL = 24; final byte RED_CHANNEL = 16; final byte GREEN_CHANNEL = 8;
    final byte BLUE_CHANNEL = 0; final float inverseAmount = 1.0f - strength; final int a =
      ((int) (((float) (c1 >> ALPHA_CHANNEL & 0xff) * strength) + (
        (float) (c2 >> ALPHA_CHANNEL & 0xff) * inverseAmount))) & 0xff; final int r =
      ((int) (((float) (c1 >> RED_CHANNEL & 0xff) * strength) + ((float) (c2 >> RED_CHANNEL & 0xff)
        * inverseAmount))) & 0xff; final int g =
      ((int) (((float) (c1 >> GREEN_CHANNEL & 0xff) * strength) + (
        (float) (c2 >> GREEN_CHANNEL & 0xff) * inverseAmount))) & 0xff; final int b =
      ((int) (((float) (c1 & 0xff) * strength) + ((float) (c2 & 0xff) * inverseAmount))) & 0xff;
    return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
  }
}
