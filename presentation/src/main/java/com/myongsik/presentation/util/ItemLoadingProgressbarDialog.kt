package com.myongsik.presentation.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.myongsik.presentation.databinding.ItemLoadingProgressbarBinding

class ItemLoadingProgressbarDialog constructor(context: Context) : Dialog(context) {
    var binding: ItemLoadingProgressbarBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ItemLoadingProgressbarBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)
    }
}