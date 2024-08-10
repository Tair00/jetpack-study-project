package com.example.compositionapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.compositionapp.databinding.FragmentChooseLevelBinding
import com.example.compositionapp.databinding.FragmentGameFinishedBinding
import com.example.compositionapp.domain.entity.GameResult

class GameFinishedFragment: Fragment() {
    private lateinit var gameResult: GameResult
    private  var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get ()= _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
    private fun parseArgs(){
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }
    companion object{
        private const val  KEY_GAME_RESULT = "game result"
        fun newInstance(gameResult: GameResult):GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments =  Bundle().apply {
                    putSerializable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }
}