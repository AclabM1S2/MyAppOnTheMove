package com.pdarcas.myapponthemove.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.pdarcas.myapponthemove.R

class ModalBottomSheetFragmentMenu: BottomSheetDialogFragment() {
    private var btnNaviguer: Button? = null
    private var btnPosition: Button? = null
    private var btnCharger: Button? = null

    override fun onStart() {
        super.onStart()
        (requireView().parent as? View)?.let { safeView ->
            BottomSheetBehavior.from(safeView).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(p0: View, p1: Int) {
                        if (p1 == BottomSheetBehavior.STATE_DRAGGING) {
                            state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }

                    override fun onSlide(p0: View, p1: Float) {}
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.modal_fragment_menu, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCharger = view.findViewById(R.id.btn_charger)
        btnNaviguer=view.findViewById(R.id.btn_naviguer)
        btnPosition=view.findViewById(R.id.btn_position)
        setListeners()
    }

    private fun setListeners() {
        btnCharger?.setOnClickListener { setResult("charger") }
        btnNaviguer?.setOnClickListener { setResult("naviguer") }
        btnPosition?.setOnClickListener { setResult("position") }

    }

    private fun setResult(action: String) {
        parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf("data" to action))
        findNavController().popBackStack()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setResult("")
    }
}