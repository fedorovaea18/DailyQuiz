package ru.eafedorova.dailyquiz.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.eafedorova.dailyquiz.R

class LoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToQuiz()
        }, 2000)
    }

    private fun navigateToQuiz() {
        val quizFragment = QuizFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, quizFragment)
            .addToBackStack(null)
            .commit()
    }
} 