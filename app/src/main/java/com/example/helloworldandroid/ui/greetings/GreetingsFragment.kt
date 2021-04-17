package com.example.helloworldandroid.ui.greetings

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworldandroid.R
import com.example.helloworldandroid.databinding.FragmentGreetingsBinding
import com.example.helloworldandroid.greetings.Greeting
import com.example.helloworldandroid.greetings.GreetingsRepository
import com.example.helloworldandroid.ui.ItemClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GreetingsFragment : Fragment(), ItemClickListener<Greeting> {
    private lateinit var binding: FragmentGreetingsBinding
    private lateinit var viewmodel: GreetingsFragmentViewModel
    private lateinit var adapter: GreetingAdapter
    @Inject lateinit var greetingsRepository: GreetingsRepository


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewmodel = ViewModelProvider(this, GreetingsFragmentViewModelFactory(greetingsRepository)).get(GreetingsFragmentViewModel::class.java)
        binding = FragmentGreetingsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this

        adapter = GreetingAdapter(this)
        binding.greetingsRecylcerView.layoutManager = LinearLayoutManager(context)
        binding.greetingsRecylcerView.adapter = adapter
        viewmodel.greetings.observe(requireActivity(), { adapter.setGreetings(it) })
        viewmodel.failureCode.observe(requireActivity(), { Snackbar.make(binding.root, "Greetings could not be loaded ($it)", Snackbar.LENGTH_LONG).show() })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.greetings_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refreshMenuItem){
            viewmodel.refreshGreetings()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: Greeting, itemView: View) {
        val directions = GreetingsFragmentDirections.actionGreetingsFragmentToGreetingDetailFragment(item)
        val textView = itemView.findViewById<TextView>(R.id.greetingTextView)
        val extras = FragmentNavigatorExtras( textView to "greetingTransitionName")
        findNavController().navigate(directions, extras)
    }
}