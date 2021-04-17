package com.example.helloworldandroid.ui.greetingdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.helloworldandroid.databinding.FragmentGreetingDetailBinding


class GreetingDetailFragment : Fragment() {
    private lateinit var viewmodel: GreetingsDetailFragmentViewModel
    private lateinit var binding: FragmentGreetingDetailBinding
    private val args: GreetingDetailFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val greeting = args.greeting
        viewmodel = ViewModelProvider(this, GreetingsDetailFragmentViewModelFactory(greeting)).get(GreetingsDetailFragmentViewModel::class.java)
        binding = FragmentGreetingDetailBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this

        return binding.root
    }
}