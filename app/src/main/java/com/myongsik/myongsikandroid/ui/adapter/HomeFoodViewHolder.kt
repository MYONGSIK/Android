package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.Book
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){


    fun bind(book : Book){
        val author = book.authors.toString().removeSurrounding("[", "]")
        val publisher = book.publisher
        val date = if(book.datetime.isNotEmpty()) book.datetime.substring(0, 10) else ""
//        val price = dec.format(book.price.toLong())

        itemView.apply{
//            binding.weekAfternoonFoodCl1.text = book.title
//            binding.tvPrice.text = "$price 원"
        }
    }
}