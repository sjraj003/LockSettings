package com.lock.settings.ui.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.lock.settings.network.model.LockConfigModel


open class BaseFragment : Fragment() {
    companion object {
        lateinit var lockConfigModel: LockConfigModel
    }

    fun onBackPressed() {
        Navigation.findNavController(requireView()).navigateUp()
    }
}