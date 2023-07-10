package com.example.achiliveapp.main.achi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.achiliveapp.databinding.FragmentAchievementObjectBinding
import com.example.achiliveapp.firebase.AwardSchemeDTO
import com.example.achiliveapp.main.achi.adapters.AwardSchemeDTOListAdapter
import com.example.achiliveapp.main.states.ListUiState
import kotlinx.coroutines.launch

class AchievementObjectFragment : Fragment() {

    private var binding: FragmentAchievementObjectBinding? = null

    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val CATEGORY_ID: String = "CATEGORY_ID"
        fun newInstance(id: String): AchievementObjectFragment {
            val fragment = AchievementObjectFragment()
            fragment.arguments = Bundle().apply {
                putString(CATEGORY_ID, id)
            }
            return fragment
        }
    }

    private lateinit var viewModel: AchievementObjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementObjectBinding.inflate(layoutInflater, container, false)

        binding?.let {
            recyclerView = it.achiRecyclerView
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getString(CATEGORY_ID)!!
        val achievementViewModel = AchievementObjectViewModel(id)
        viewModel = ViewModelProvider(this, achievementViewModel)[AchievementObjectViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.itemState.collect{

                    if(it.isSuccess()){
                        initRecyclerView(it.data!!)
                    }

                    if(it.isError()){

                    }
                }
            }
        }


    }

    private fun initRecyclerView(items: List<AwardSchemeDTO>) {
        recyclerView.adapter = AwardSchemeDTOListAdapter().apply {
            this.items = items
        }
    }

}