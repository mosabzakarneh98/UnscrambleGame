package com.example.android.unscramble.ui.game

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@BindingAdapter("textWithSpaceBetweenItsCharacters")
fun TextView.addSpaceBetweenCharacters(text: String) {
    var temp = ""
    for (char in text) temp += "$char "
    this.text = temp
}

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener {
            onSubmitWord()
        }

        binding.skip.setOnClickListener {
            onSkipWord()
        }

        viewModel.flagSingleLiveEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "correct answer", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onSubmitWord() {
        if (viewModel.count != MAX_NO_OF_WORDS) {
            if (viewModel.isCorrectWord(binding.textInputEditText.text.toString())) {
                viewModel.nextWord()

                viewModel.increaseScore()

                setErrorTextField(false)
            } else setErrorTextField(true)

        } else showFinalScoreDialog()
    }

    private fun onSkipWord() {
        if (viewModel.count != MAX_NO_OF_WORDS) {
            binding.gameViewModel?.nextWord()

            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }


    private fun restartGame() {
        viewModel.reInit()

        setErrorTextField(false)
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.scoreLiveData.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true

            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false

            binding.textInputEditText.text = null
        }
    }

}

