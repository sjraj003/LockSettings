package com.lock.settings.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.lock.settings.R
import com.lock.settings.app.Constants
import com.lock.settings.databinding.FragmentEditLockBinding
import com.lock.settings.model.LockEntity
import com.lock.settings.viewmodel.LockViewModel

class EditFragment : BaseFragment() {
    private lateinit var binding: FragmentEditLockBinding
    private var lockEntityName: String? = null
    private val mViewModel: LockViewModel by viewModels()
    private var defaultValue = ""
    private var lockEntity: LockEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_edit_lock, container, false)
        initUI()
        return view
    }

    private fun initUI() {
        lockEntityName = arguments?.getString(Constants.LOCK_SCREEN_PARAM)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_edit_lock)
        mViewModel.lockEntityLiveData.observe(viewLifecycleOwner) { entity ->
            lockEntity = entity
            updateUI()
        }

        binding.cancelButton.setOnClickListener {
            onBackPressed()
        }

        binding.okButton.setOnClickListener {
            mViewModel.updateData(
                lockEntityName!!,
                getCheckedText(binding.primaryRadiogroup),
                getCheckedText(binding.secondaryRadiogroup)
            )
            onBackPressed()
        }

        lockEntityName?.let { mViewModel.getLockValue(it) }
    }


    private fun getCheckedText(radioGroup: RadioGroup): String {
        val selectedId = radioGroup.checkedRadioButtonId
        return if (selectedId !== -1) (requireActivity().findViewById<RadioButton>(selectedId))?.text.toString() else ""
    }

    private fun updateUI() {
        binding.primaryDefaultValue.text = lockEntity?.details?.defaults
        binding.secondaryDefaultValue.text = lockEntity?.details?.defaults

        val values: Array<String?>? = getEntity()
        values?.let {
            binding.primaryRadiogroup.orientation = LinearLayout.VERTICAL
            var selectedValue =
                lockEntity?.details?.primary?.ifEmpty { lockEntity?.details?.defaults }
            for (value in values) {
                val rbn = RadioButton(context)
                rbn.id = View.generateViewId()
                rbn.text = value
                rbn.isChecked = selectedValue.equals(value)
                binding.primaryRadiogroup.addView(rbn)
            }

            binding.secondaryRadiogroup.orientation = LinearLayout.VERTICAL
            selectedValue =
                lockEntity?.details?.secondary?.ifEmpty { lockEntity?.details?.defaults }
            for (value in values) {
                val rbn = RadioButton(context)
                rbn.id = View.generateViewId()
                rbn.text = value
                rbn.isChecked = selectedValue.equals(value)
                binding.secondaryRadiogroup.addView(rbn)
            }
        }
    }

    private fun getEntity(): Array<String?>? {
        return when (lockEntityName) {
            Constants.LOCK_VOLTAGE -> {
                defaultValue = lockConfigModel.lockVoltage?.default!!
                lockConfigModel.lockVoltage?.values
            }

            Constants.LOCK_TYPE -> {
                defaultValue = lockConfigModel.lockType?.default!!
                lockConfigModel.lockType?.values
            }

            Constants.LOCK_KICK -> {
                defaultValue = lockConfigModel.lockKick?.default!!
                lockConfigModel.lockKick?.values
            }

            Constants.LOCK_RELEASE -> {
                defaultValue = lockConfigModel.lockRelease?.default!!
                lockConfigModel.lockRelease?.values
            }

            else -> {
                null
            }
        }
    }

}