package com.ananya.rakshakavach.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ananya.rakshakavach.data.model.QuizQuestion

class QuizViewModel : ViewModel() {

    private val questions = listOf(
        QuizQuestion(1, "What does PPE stand for?", listOf("Personal Protective Equipment", "Private Power Energy", "Public Protection Event", "Personal Proper Entry"), 0),
        QuizQuestion(2, "Which color is typically used for caution signs?", listOf("Red", "Blue", "Yellow", "Green"), 2),
        QuizQuestion(3, "When working at heights, what is essential?", listOf("Hard Hat", "Safety Harness", "Gloves", "Sun Glasses"), 1),
        QuizQuestion(4, "What should you do first in case of a gas leak?", listOf("Light a match", "Seal the windows", "Evacuate and sound alarm", "Ignore it"), 2),
        QuizQuestion(5, "Which type of fire extinguisher is for electrical fires?", listOf("Water", "CO2", "Foam", "Wet Chemical"), 1),
        QuizQuestion(6, "What is the primary purpose of a safety helmet?", listOf("Looks good", "Identification", "Protection from falling objects", "Warmth"), 2),
        QuizQuestion(7, "Before operating a machine, you should?", listOf("Read the manual", "Press all buttons", "Clean it", "Ask a friend"), 0),
        QuizQuestion(8, "Where should oily rags be stored?", listOf("In an open bin", "In a drawer", "In a closed metal container", "Outside"), 2),
        QuizQuestion(9, "What should be used to protect eyes while welding?", listOf("Sunglasses", "Safety Goggles", "Welding Shield/Mask", "Nothing"), 2),
        QuizQuestion(10, "A 'Near Miss' should be?", listOf("Forgotten", "Reported immediately", "Laughed at", "Told to no one"), 1)
    )

    var currentQuestionIndex by mutableStateOf(0)
    var userAnswers = mutableStateOf(mutableMapOf<Int, Int>())
    var isQuizFinished by mutableStateOf(false)
    var score by mutableStateOf(0)

    val currentQuestion: QuizQuestion get() = questions[currentQuestionIndex]
    val totalQuestions: Int get() = questions.size

    fun selectAnswer(questionId: Int, answerIndex: Int) {
        val newAnswers = userAnswers.value.toMutableMap()
        newAnswers[questionId] = answerIndex
        userAnswers.value = newAnswers
    }

    fun nextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
        } else {
            finishQuiz()
        }
    }

    fun previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
        }
    }

    private fun finishQuiz() {
        var calculatedScore = 0
        questions.forEach { q ->
            if (userAnswers.value[q.id] == q.correctAnswerIndex) {
                calculatedScore++
            }
        }
        score = calculatedScore
        isQuizFinished = true
    }

    fun restartQuiz() {
        currentQuestionIndex = 0
        userAnswers.value = mutableMapOf()
        isQuizFinished = false
        score = 0
    }
}
