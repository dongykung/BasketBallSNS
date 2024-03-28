package com.dkproject.presentation.ui.component.writeshop

import androidx.compose.runtime.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.*
import java.text.*

class DecimalMarkedNumberVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        // 입력된 텍스트를 숫자와 소수점만 남기도록 정규식을 사용하여 변환
        val trimmedText = originalText.replace(Regex("[^\\d.]"), "")

        // 숫자를 3자리마다 쉼표를 추가하여 포맷팅
        val formattedText = addCommasToNumericString(trimmedText)

        return TransformedText(
            text = AnnotatedString(formattedText),
            // OffsetMapping은 CurrencyOffsetMapping을 사용하여 변환된 텍스트와 원래 텍스트의 오프셋을 매핑
            offsetMapping = OffsetMapping.Identity
        )
    }

    private fun addCommasToNumericString(text: String): String {
        if (text.isEmpty()) return text

        val parts = text.split(".")
        val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
        return if (parts.size > 1) "$integerPart.${parts[1]}" else integerPart
    }
}

class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {

    private val indexMap: Map<Int, Int>

    init {
        indexMap = buildIndexMap(originalText, formattedText)
    }

    private fun buildIndexMap(originalText: String, formattedText: String): Map<Int, Int> {
        val indexMap = mutableMapOf<Int, Int>()
        var originalIndex = 0
        var formattedIndex = 0
        while (originalIndex < originalText.length && formattedIndex < formattedText.length) {
            if (originalText[originalIndex] == formattedText[formattedIndex]) {
                indexMap[formattedIndex] = originalIndex
                originalIndex++
                formattedIndex++
            } else {
                formattedIndex++
            }
        }
        return indexMap
    }
    val indexs = originalText.length
    override fun originalToTransformed(offset: Int): Int {
        // 변환된 텍스트에서 원래 텍스트로의 오프셋을 반환
        return indexMap[offset] ?: offset
    }

    override fun transformedToOriginal(offset: Int): Int {
        // 원래 텍스트에서 변환된 텍스트로의 오프셋을 반환
        return indexMap.entries.find { it.value == offset }?.key ?: indexs
    }
}
