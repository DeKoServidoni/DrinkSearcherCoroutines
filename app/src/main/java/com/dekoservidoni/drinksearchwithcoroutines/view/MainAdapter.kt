package com.dekoservidoni.drinksearchwithcoroutines.view

import com.dekoservidoni.drinksearchwithcoroutines.BaseAdapter
import com.dekoservidoni.drinksearchwithcoroutines.R
import com.dekoservidoni.drinksearchwithcoroutines.databinding.RowMainBinding
import com.dekoservidoni.drinksearchwithcoroutines.models.Drink

class MainAdapter: BaseAdapter<RowMainBinding, Drink>(R.layout.row_main) {

    override fun bind(holder: DataBindViewHolder<RowMainBinding>, position: Int) {
        val drink = content[position]
        holder.binding.viewModel = MainRowViewModel(drink)
        holder.binding.executePendingBindings()
    }

    override fun updateContent(newContent: List<Drink>?) {
        newContent?.let {
            content.clear()
            content.addAll(it)
            notifyDataSetChanged()
        }
    }
}