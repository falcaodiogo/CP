package ua.diogo.cp.gemini.pdfExtractor

import android.content.Context
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.InputStream

fun extractTextFromPdf(context: Context, pdfFile: File): String {
    return try {
        val inputStream: InputStream = pdfFile.inputStream()
        val document = PDDocument.load(inputStream)
        val stripper = PDFTextStripper()
        val text = stripper.getText(document)
        document.close()
        text
    } catch (e: Exception) {
        "Erro ao processar o PDF: ${e.message}"
    }
}
