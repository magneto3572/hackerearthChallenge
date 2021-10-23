package com.example.hackerearthchallenge.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackerearthchallenge.R
import com.example.hackerearthchallenge.data.Resource
import com.example.hackerearthchallenge.data.adapter.DataAdapter
import com.example.hackerearthchallenge.data.models.Item
import com.example.hackerearthchallenge.databinding.HomeFragmentBinding
import com.example.hackerearthchallenge.utils.CShowProgress
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Home : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var progress: CShowProgress
    private var dataAdapter: DataAdapter? = null
    private var binding : HomeFragmentBinding? = null
    private lateinit var item : ArrayList<Item>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        Apicall()

        binding?.searchEditText?.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase()
            filterWithQuery(query)
        }
    }

    private fun attachAdapter(list: List<Item>) {
        dataAdapter = DataAdapter(requireContext(), list)
        binding?.recycler?.adapter = dataAdapter
    }

    private fun toggleRecyclerView(sportsList: List<Item>) {
        if (sportsList.isEmpty()) {
            binding?.recycler?.visibility = View.INVISIBLE
        } else {
            binding?.recycler?.visibility = View.VISIBLE
        }
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            val filteredList: List<Item> = onFilterChanged(query)
            attachAdapter(filteredList)
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            attachAdapter(item)
        }
    }

    private fun onFilterChanged(filterQuery: String): List<Item> {
        val filteredList = ArrayList<Item>()
        for (it in item) {
            if (it.owner.display_name.lowercase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun Apicall() {
        viewModel.apply {
            fetchData()
            datRes.observe(viewLifecycleOwner)
            {
                when (it) {
                    is Resource.Success -> {
                        Log.d("LogTag", it.toString())
                        progress.hideProgress()
                        try {
                             item = it.value.items
                            Log.d("LogTagItem", item.toString())
                            val mLayoutManager = LinearLayoutManager(requireContext())
                            binding?.apply {
                                recycler.layoutManager = mLayoutManager
                                dataAdapter = DataAdapter(requireContext(), item)
                                recycler.adapter = dataAdapter
                            }
                        } catch (e: NullPointerException) {
                            Toast.makeText(
                                requireActivity(),
                                "oops..! Something went wrong.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Failure -> {
                        progress.hideProgress()
                        val snack =
                            Snackbar.make(requireView(), "Failed to load", Snackbar.LENGTH_LONG)
                        snack.setAction(
                            "Retry"
                        ) {
                           fetchData()
                        }
                        snack.show()
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        if (progress.mDialog?.isShowing == true) {
                            progress.hideProgress()
                        } else {
                            progress.showProgress(requireContext())
                        }
                    }
                }
            }
        }
    }
}
