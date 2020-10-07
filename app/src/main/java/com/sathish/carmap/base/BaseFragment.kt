package com.sathish.carmap.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {


    fun showMessage(message: String) {
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun hideKeyBoard() {
        val inputManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }
}


