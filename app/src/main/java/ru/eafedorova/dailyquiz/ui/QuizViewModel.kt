package ru.eafedorova.dailyquiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.eafedorova.dailyquiz.model.Question

class QuizViewModel : ViewModel() {

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _isQuizFinished = MutableLiveData(false)
    val isQuizFinished: LiveData<Boolean> = _isQuizFinished

    private val _selectedAnswerIndex = MutableLiveData<Int?>()
    val selectedAnswerIndex: LiveData<Int?> = _selectedAnswerIndex

    private val questions = listOf(
        Question(
            id = 1,
            questionText = "Как переводится слово «apple»?",
            options = listOf("Груша", "Яблоко", "Апельсин", "Ананас"),
            correctAnswerIndex = 1,
        ),
        Question(
            id = 2,
            questionText = "Какое слово означает цвет?",
            options = listOf("Table", "Blue", "Run", "Chair"),
            correctAnswerIndex = 1,
        ),
        Question(
            id = 3,
            questionText = "Какое из слов — местоимение?",
            options = listOf("He", "Cat", "Big", "Go"),
            correctAnswerIndex = 0,
        ),
        Question(
            id = 4,
            questionText = "Как по-английски сказать: «У меня есть книга»??",
            options = listOf("I am a book", "I was a book", "Big", "I has a book"),
            correctAnswerIndex = 3,
        ),
        Question(
            id = 5,
            questionText = "Выберите правильный перевод фразы «Good morning»",
            options = listOf("Добрый вечер", "Привет", "Доброе утро", "Спокойной ночи"),
            correctAnswerIndex = 2,
        )
    )

    fun getCurrentQuestion(): Question {
        return questions[_currentQuestionIndex.value ?: 0]
    }

    fun getTotalQuestions(): Int {
        return questions.size
    }

    fun selectAnswer(answerIndex: Int) {
        _selectedAnswerIndex.value = answerIndex
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: 0
        if (currentIndex < questions.size - 1) {
            _currentQuestionIndex.value = currentIndex + 1
        } else {
            _isQuizFinished.value = true
        }
    }

    fun submitAnswer() {
        val currentIndex = _currentQuestionIndex.value ?: 0
        val selectedAnswer = _selectedAnswerIndex.value

        if (selectedAnswer != null) {
            val currentQuestion = questions[currentIndex]
            if (selectedAnswer == currentQuestion.correctAnswerIndex) {
                _score.value = (_score.value ?: 0) + 1
            }
        }

        _selectedAnswerIndex.value = null
    }

}