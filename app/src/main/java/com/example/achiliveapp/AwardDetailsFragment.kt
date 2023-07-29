package com.example.achiliveapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AwardDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = AwardDetailsFragment()
    }

    private lateinit var viewModel: AwardDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_award_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AwardDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}