package com.example.sampleapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.databinding.MainFragmentBinding
import com.example.sampleapp.model.entity.DataState
import com.example.sampleapp.model.entity.User
import com.example.sampleapp.ui.adapter.UserListAdapter
import com.example.sampleapp.ui.adapter.core.ItemClickListener
import com.example.sampleapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), ItemClickListener<User> {
    private val userViewModel: UserViewModel by activityViewModels()
    lateinit var binding: MainFragmentBinding
    lateinit var listAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    private fun init() {
        listAdapter = UserListAdapter(this)
        binding.rvUsersMainFragment.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        userViewModel.getUsers()
    }

    private fun observe() {
        userViewModel.usersDataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success -> {
                    listAdapter.submitList(it.data)
                }
                is DataState.Failure -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    //show progress
                }
            }
        })
    }

    override fun onClick(item: User) {
        Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
        userViewModel.addUser(item)
    }

    override fun onLongClick(item: User) {

    }

}