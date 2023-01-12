package com.franalarza.dragonballfights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.databinding.ActivityLoginBinding
import com.franalarza.dragonballfights.databinding.FragmentBattleBinding

class BattleFragment : Fragment() {

    companion object {
        fun newInstance() = BattleFragment()
    }

    private lateinit var binding: FragmentBattleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBattleBinding.inflate(inflater)
        return binding.root
    }

}