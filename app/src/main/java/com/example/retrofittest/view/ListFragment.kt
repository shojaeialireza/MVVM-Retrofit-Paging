package com.example.retrofittest.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.NewsApp
import com.example.retrofittest.R
import com.example.retrofittest.databinding.ListFragmentBinding
import com.example.retrofittest.utils.NewsListAdapter
import com.example.retrofittest.utils.NewsLoadStateAdapter
import com.example.retrofittest.viewmodel.ListViewModel
import com.example.retrofittest.viewmodel.ListViewModelFactory
import kotlinx.coroutines.launch

class ListFragment:Fragment() , SearchView.OnQueryTextListener{
    private lateinit var binding: ListFragmentBinding
    private val viewModel: ListViewModel by viewModels {
        ListViewModelFactory(
                ((requireActivity().application as NewsApp).repository)
        )
    }
    private lateinit var adapter:NewsListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= ListFragmentBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= NewsListAdapter()
        linearLayoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.apply {
            recyclerView.adapter=adapter.withLoadStateHeaderAndFooter(
                NewsLoadStateAdapter{adapter.retry()},
                NewsLoadStateAdapter{adapter.retry()}
            )
            recyclerView.layoutManager=linearLayoutManager
        }

        adapter.addLoadStateListener { state->
            binding.apply {
                progressBar.isVisible=state.refresh is LoadState.Loading
                txtError.isVisible=state.refresh is LoadState.Error

                recyclerView.isVisible = state.refresh is LoadState.NotLoading&& adapter.itemCount>0
            }
        }

        setupObservers()

        binding.refreshLayout.setOnRefreshListener {
            adapter.retry()
            binding.refreshLayout.isRefreshing=false
        }
    }

    private fun setupObservers(){
        viewModel.data.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        viewModel.message.observe(this.viewLifecycleOwner,{
            if (it!=null){
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                viewModel.messageReceived()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_top_menu,menu)
        val searchView:SearchView=menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.isSubmitButtonEnabled=false
        searchView.queryHint=getString(R.string.search_view_hint)
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.fetchNewData(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}