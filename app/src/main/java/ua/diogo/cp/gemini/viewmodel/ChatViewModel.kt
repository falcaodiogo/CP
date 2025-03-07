package ua.diogo.cp.gemini.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch
import ua.diogo.cp.gemini.model.MessageModel
import ua.diogo.cp.gemini.pdfExtractor.extractTextFromPdf
import ua.diogo.cp.gemini.utilities.Constansts
import java.io.File

class ChatViewModel : ViewModel() {


    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constansts.apiKey
    )

    fun sendMessage(context: Context, question: String, pdfFile: File?) {
        viewModelScope.launch {
            try {
                val pdfText = pdfFile?.let { extractTextFromPdf(context, it) } ?: ""

                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing....", "model"))

                val response = chat.sendMessage(
                    "Responde apenas a questões relacionadas com a CP (Comboios de Portugal) e os seus trajetos. Não uses markdown. Se não souberes completamente uma resposta, reecaminha para o numero da CP 808 109 110.\nPergunta: $question"
                )

                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(), "model"))

            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Erro: ${e.message}", "model"))
            }
        }
    }
}