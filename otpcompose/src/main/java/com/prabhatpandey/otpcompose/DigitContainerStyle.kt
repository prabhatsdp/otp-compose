package com.prabhatpandey.otpcompose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
/**
 * `DigitContainerStyle` is a sealed class representing different styles for displaying digit containers
 * for the [OTPTextField].
 * This class provides a flexible way to customize the appearance of digit containers in [OTPTextField].
 *
 * @property containerColor The background color of the digit container.
 * @property focusedBorderColor The border color when the digit container is focused.
 * @property unfocusedBorderColor The border color when the digit container is not focused.
 * @property focusedBorderWidth The border width when the digit container is focused.
 * @property unfocusedBorderWidth The border width when the digit container is not focused.
 * @property errorColor The color used to indicate an error state in the digit container.
 */
sealed class DigitContainerStyle(
    open val containerColor: Color = Color.Unspecified,
    open val focusedBorderColor: Color = Color.Unspecified,
    open val unfocusedBorderColor: Color = Color.Unspecified,
    open val focusedBorderWidth: Dp = 2.dp,
    open val unfocusedBorderWidth: Dp = 2.dp,
    open val errorColor: Color = Color.Unspecified,
) {

    /**
     * Returns the appropriate border width based on the focus state of the digit container.
     *
     * @param focused Whether the digit container is currently focused.
     * @return The border width to be used for the digit container.
     */
    fun borderWidth(focused: Boolean): Dp {
        return if (focused) focusedBorderWidth else unfocusedBorderWidth
    }

    /**
     * Returns the appropriate border color based on the focus and error states of the digit container.
     *
     * @param focused Whether the digit container is currently focused.
     * @param error Whether the digit container is in an error state.
     * @return The border color to be used for the digit container.
     */
    fun borderColor(focused: Boolean, error: Boolean): Color {
        return when {
            error -> errorColor
            focused -> focusedBorderColor
            else -> unfocusedBorderColor
        }
    }

    /**
     * Represents an outlined style for the digit container of our [OTPTextField].
     *
     * @property size The size of the digit container.
     * @property shape The shape of the digit container.
     */
    data class Outlined(
        val size: Dp = 54.dp,
        val shape: Shape = RoundedCornerShape(12.dp),
        override val containerColor: Color = Color.Unspecified,
        override val focusedBorderColor: Color = Color.Unspecified,
        override val unfocusedBorderColor: Color = Color.Unspecified,
        override val focusedBorderWidth: Dp = 2.dp,
        override val unfocusedBorderWidth: Dp = 2.dp,
        override val errorColor: Color = Color.Unspecified,
    ) : DigitContainerStyle(
        containerColor = containerColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        unfocusedBorderWidth = unfocusedBorderWidth,
        focusedBorderWidth = focusedBorderWidth,
        errorColor = errorColor,
    )

    /**
     * Represents an underlined style for the digit container of our [OTPTextField].
     *
     * @property size The size of the digit container.
     */
    data class Underlined(
        val size: Dp = 54.dp,
        override val containerColor: Color = Color.Unspecified,
        override val focusedBorderColor: Color = Color.Unspecified,
        override val unfocusedBorderColor: Color = Color.Unspecified,
        override val focusedBorderWidth: Dp = 2.dp,
        override val unfocusedBorderWidth: Dp = 2.dp,
        override val errorColor: Color = Color.Unspecified,
    ) : DigitContainerStyle(
        containerColor = containerColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        unfocusedBorderWidth = unfocusedBorderWidth,
        focusedBorderWidth = focusedBorderWidth,
        errorColor = errorColor
    )

}
