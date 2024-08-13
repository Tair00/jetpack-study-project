package com.example.compositionapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compositionapp.R
import com.example.compositionapp.databinding.FragmentChooseLevelBinding
import com.example.compositionapp.databinding.FragmentGameBinding
import com.example.compositionapp.domain.entity.GameResult
import com.example.compositionapp.domain.entity.Level

class GameFragment: Fragment() {
    private lateinit var level: Level
    private  var _binding: FragmentGameBinding? = null
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }



    private val viewModel: GameViewModel by lazy{
        ViewModelProvider(
            this,
         ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private val binding: FragmentGameBinding
        get ()= _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
    private fun observeViewModel(){
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text= it.sum.toString()
            binding.tvLeftNumber.text = it.visibledNumber.toString()
        }
    }
    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
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
        level = requireArguments().getSerializable(KEY_LEVEL) as Level

    }
    companion object{

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL,level)
                }
            }
        }
    }
}