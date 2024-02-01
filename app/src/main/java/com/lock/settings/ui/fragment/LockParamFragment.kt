package com.lock.settings.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lock.settings.R
import com.lock.settings.app.Constants
import com.lock.settings.databinding.FragmentLockParametersBinding
import com.lock.settings.model.LockEntity
import com.lock.settings.ui.view.LockAdapter
import com.lock.settings.viewmodel.LockViewModel


class LockParamFragment : BaseFragment(), LockAdapter.LockItemListener {
    private lateinit var binding: FragmentLockParametersBinding
    private val mViewModel: LockViewModel by viewModels()
    private var adapter: LockAdapter? = null
    private var entityList: List<LockEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_lock_parameters, container, false)
        initUI()
        return view
    }

    private fun initUI() {
        binding =
            DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_lock_parameters)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        mViewModel.lockEntityListLiveData.observe(viewLifecycleOwner) { mainItems ->
            entityList = mainItems
            adapter?.setLock(mainItems)
        }
        mViewModel.lockConfigLiveData.observe(viewLifecycleOwner) { config ->
            lockConfigModel = config
        }
        val dataList: List<LockEntity>? = null
        adapter = LockAdapter(requireContext(), dataList, this)
        binding.recyclerView.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        addTextChangeListener()

        getLockData()
    }


    private fun addTextChangeListener() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is called to notify you that characters within `charSequence` are about to be replaced with new text
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // This method is called to notify you that somewhere within `charSequence`, the text has been changed
            }

            override fun afterTextChanged(editable: Editable?) {
                // This method is called to notify you that the characters within `editable` have been changed
                val search = editable.toString()
                if (search.length > 1) {
                    adapter?.filter(search)
                } else {
                    adapter?.setLock(entityList)
                }
            }
        })
    }

    private fun getLockData() {
        mViewModel.fetchLockDetails()
    }

    override fun onItemClick(entity: LockEntity?) {
        val bundle = Bundle().apply {
            putString(Constants.LOCK_SCREEN_PARAM, entity?.name)
        }
        Navigation.findNavController(requireView())
            .navigate(R.id.action_fragmentLockParam_to_fragmentEdit, bundle)
    }
}
