package com.prabhatpandey.otpcompose

import androidx.annotation.IntRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * [OTPTextField] is a composable function for creating an OTP (One-Time Password) text field.
 *
 * @param value The current value of the OTP text field.
 * @param modifier Optional [Modifier] for configuring the layout behavior.
 * @param onTextChanged A callback triggered when the text in the OTP field changes.
 * @param numDigits The number of digits for the OTP. Default is 4.
 * @param isMasked Whether to mask the digits in the OTP field. Default is false.
 * @param mask A composable function for providing a custom masking visual for each digit.
 * @param digitContainerStyle The style configuration for the digit containers.
 * @param textStyle The style configuration for the text within the digit containers.
 * @param isError Whether the OTP field is in an error state.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPTextField(
    value: String,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    @IntRange(from = 4, to = 6) numDigits: Int = 4,
    isMasked: Boolean = false,
    mask: @Composable() (() -> Unit)? = null,
    digitContainerStyle: DigitContainerStyle = DigitContainerStyle.Underlined(),
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        fontSize = 28.sp
    ),
    isError: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    val autoFillNode = AutofillNode(
        autofillTypes = listOf(AutofillType.SmsOtpCode),
        onFill = onTextChanged
    )
    val autoFill = LocalAutofill.current

    LocalAutofillTree.current += autoFillNode

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                autoFillNode.boundingBox = it.boundsInWindow()
            }
            .onFocusChanged { focusState ->
                autoFill?.run {
                    if (focusState.isFocused) {
                        requestAutofillForNode(autoFillNode)
                    } else {
                        cancelAutofillForNode(autoFillNode)
                    }
                }
            },
        value = value,
        onValueChange = {
            if (it.length <= numDigits) {
                onTextChanged(it)
            }
            if (it.length >= numDigits) {
                focusManager.clearFocus()
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = { _ ->
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                repeat(numDigits) { index ->
                    val char = when {
                        index >= value.length -> ""
                        else -> value[index].toString()
                    }
                    val isFocused = value.length == index
                    Spacer(modifier = Modifier.weight(0.1f))
                    when (digitContainerStyle) {
                        is DigitContainerStyle.Outlined -> {
                            OutlinedDigitContainer(
                                digitBoxStyle = digitContainerStyle,
                                isFocused = isFocused,
                                digit = char,
                                textStyle = textStyle,
                                isMasked = isMasked,
                                mask = mask,
                                isError = isError
                            )
                        }

                        is DigitContainerStyle.Underlined -> {
                            UnderlinedDigitContainer(
                                digitBoxStyle = digitContainerStyle,
                                isFocused = isFocused,
                                digit = char,
                                textStyle = textStyle,
                                isMasked = isMasked,
                                mask = mask,
                                isError = isError
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }
    )
}

/**
 * A private composable function representing an outlined digit container within the [OTPTextField].
 *
 * @param digitBoxStyle The style configuration for the digit container.
 * @param isMasked Whether to mask the digit.
 * @param isFocused Whether the digit container is currently focused.
 * @param digit The digit value.
 * @param mask A composable function for providing a custom masking visual for the digit.
 * @param textStyle The style configuration for the text within the digit container.
 * @param isError Whether the digit container is in an error state.
 */
@Composable
private fun RowScope.OutlinedDigitContainer(
    digitBoxStyle: DigitContainerStyle.Outlined,
    isMasked: Boolean = true,
    isFocused: Boolean,
    digit: String,
    mask: @Composable (() -> Unit)? = null,
    textStyle: TextStyle,
    isError: Boolean = false,
) {

    val animatedColor by animateColorAsState(
        targetValue = digitBoxStyle.borderColor(focused = isFocused, error = isError),
        label = "Border Color",
        animationSpec = tween(durationMillis = 200)
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = digitBoxStyle.borderWidth(focused = isFocused),
        label = "BorderWidth",
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clip(digitBoxStyle.shape)
            .border(
                width = animatedBorderWidth,
                color = animatedColor,
                shape = digitBoxStyle.shape
            )
            .background(
                color = digitBoxStyle.containerColor
                    ?: MaterialTheme.colorScheme.background,
                shape = digitBoxStyle.shape
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isMasked && digit.isNotBlank()) {
            mask?.invoke()
                ?: Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
        } else {
            Text(
                text = digit,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 0.dp)
                    .wrapContentHeight(),
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}

/**
 * A private composable function representing an underlined digit container within the [OTPTextField].
 *
 * @param digitBoxStyle The style configuration for the digit container.
 * @param isMasked Whether to mask the digit.
 * @param isFocused Whether the digit container is currently focused.
 * @param digit The digit value.
 * @param mask A composable function for providing a custom masking visual for the digit.
 * @param textStyle The style configuration for the text within the digit container.
 * @param isError Whether the digit container is in an error state.
 */
@Composable
private fun RowScope.UnderlinedDigitContainer(
    digitBoxStyle: DigitContainerStyle.Underlined,
    isMasked: Boolean = true,
    isFocused: Boolean,
    digit: String,
    mask: @Composable (() -> Unit)? = null,
    textStyle: TextStyle,
    isError: Boolean = false,
) {

    val animatedBorderColor by animateColorAsState(
        targetValue = digitBoxStyle.borderColor(focused = isFocused, error = isError),
        label = "Border Color",
        animationSpec = tween(durationMillis = 200)
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = digitBoxStyle.borderWidth(focused = isFocused),
        label = "BorderWidth",
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .drawBehind {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    brush = SolidColor(animatedBorderColor),
                    strokeWidth = animatedBorderWidth.toPx(),
                )
            }
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isMasked && digit.isNotBlank()) {
            mask?.invoke()
                ?: Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
        } else {
            Text(
                text = digit,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 0.dp)
                    .wrapContentHeight(),
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}
