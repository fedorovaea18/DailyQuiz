package ru.eafedorova.dailyquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import ru.eafedorova.dailyquiz.R

class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by activityViewModels()
    
    private lateinit var questionTextView: TextView
    private lateinit var progressTextView: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var nextButton: Button
    private lateinit var backButton: ImageButton
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initializeViews(view)
        setupObservers()
        setupClickListeners()
    }
    
    private fun initializeViews(view: View) {
        questionTextView = view.findViewById(R.id.tvQuestion)
        progressTextView = view.findViewById(R.id.tvProgress)
        nextButton = view.findViewById(R.id.btnNext)
        backButton = view.findViewById(R.id.btnBack)
        
        optionButtons = listOf(
            view.findViewById(R.id.rbOption1),
            view.findViewById(R.id.rbOption2),
            view.findViewById(R.id.rbOption3),
            view.findViewById(R.id.rbOption4)
        )
    }
    
    private fun setupObservers() {
        viewModel.currentQuestionIndex.observe(viewLifecycleOwner, Observer { index ->
            updateQuestion()
            updateProgress()
        })
        
        viewModel.selectedAnswerIndex.observe(viewLifecycleOwner, Observer { selectedIndex ->
            updateOptionButtons(selectedIndex)
        })

    }
    
    private fun setupClickListeners() {
        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.selectAnswer(index)
            }
        }
        
        nextButton.setOnClickListener {
            viewModel.submitAnswer()
            viewModel.nextQuestion()
        }

    }
    
    private fun updateQuestion() {
        val currentQuestion = viewModel.getCurrentQuestion()
        questionTextView.text = currentQuestion.questionText
        
        currentQuestion.options.forEachIndexed { index, option ->
            optionButtons[index].text = option
        }
    }
    
    private fun updateProgress() {
        val currentIndex = viewModel.currentQuestionIndex.value ?: 0
        val totalQuestions = viewModel.getTotalQuestions()
        progressTextView.text = "Вопрос ${currentIndex + 1} из $totalQuestions"
    }
    
    private fun updateOptionButtons(selectedIndex: Int?) {
        optionButtons.forEachIndexed { index, button ->
            if (selectedIndex == index) {
                button.setBackgroundColor(resources.getColor(R.color.selected_option, null))
            } else {
                button.setBackgroundColor(resources.getColor(android.R.color.white, null))
            }
        }
        
        nextButton.isEnabled = selectedIndex != null
    }

}
