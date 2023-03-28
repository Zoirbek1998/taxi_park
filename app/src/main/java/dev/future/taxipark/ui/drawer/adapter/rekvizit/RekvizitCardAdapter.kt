package dev.future.taxipark.ui.drawer.adapter.rekvizit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.databinding.RekvizitCardItemBinding
import dev.future.taxipark.ui.drawer.model.CardsModel


class RekvizitCardAdapter(val itemClick: ItemClick) :
    RecyclerView.Adapter<RekvizitCardAdapter.ItemHolder>() {

    var items: List<CardsModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ItemHolder(var binding: RekvizitCardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RekvizitCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]


        val isExpandable: Boolean = item.isExpandable
        with(holder.binding) {

            upDateCard.setOnClickListener {
                itemClick.updateCard(item)
            }

            deleteCard.setOnClickListener {
                itemClick.deleteClick(item)
            }



            container3.visibility = if (isExpandable) View.VISIBLE else View.GONE
            container2.visibility = if (isExpandable) View.VISIBLE else View.GONE
            container1.visibility = if (isExpandable) View.GONE else View.VISIBLE


            parentCardNum.text = item.cardNumber
            secondCardNum.text = item.cardNumber
            parentName.text = item.fullName
            parentCardName.text = item.bank
            secondCardName.text = item.bank

            contaonerFull.setOnClickListener {
                isAnyItemExpanded(position)
                item.isExpandable = !item.isExpandable
                notifyItemChanged(position, Unit)
            }

        }

    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = items.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position) {
            items[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    interface ItemClick {
        fun deleteClick(item: CardsModel)
        fun updateCard(item: CardsModel)
    }
}