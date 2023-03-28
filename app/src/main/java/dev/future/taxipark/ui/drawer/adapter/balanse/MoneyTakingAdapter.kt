package dev.future.taxipark.ui.drawer.adapter.balanse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.databinding.MoneyCardItemBinding
import dev.future.taxipark.databinding.RekvizitCardItemBinding
import dev.future.taxipark.ui.drawer.model.CardsItem
import dev.future.taxipark.ui.drawer.model.CardsModel
import dev.future.taxipark.ui.drawer.model.TransactionsItem

class MoneyTakingAdapter(val callback:MoneyCallBack) : RecyclerView.Adapter<MoneyTakingAdapter.ItemHolder>() {


    interface MoneyCallBack{
        fun onClikItem(item:CardsItem)
    }

    var items: List<CardsItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ItemHolder(var binding: MoneyCardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MoneyCardItemBinding.inflate(
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


            holder.itemView.setOnClickListener {
                items.forEach {
                    it.chacked = false
                }
                callback.onClikItem(item)
                item.chacked =true

                notifyDataSetChanged()
            }

            if (item.chacked){
               cardContainer.setBackgroundResource(R.drawable.card_border_line)
            }
            else{
                cardContainer.setBackgroundResource(R.color.white)
            }


            container3.visibility = if (isExpandable) View.VISIBLE else View.GONE
            container2.visibility = if (isExpandable) View.VISIBLE else View.GONE
            container1.visibility = if (isExpandable) View.GONE else View.VISIBLE


            parentCardNum.text = item.cardNumber
            secondCardNum.text = item.cardNumber
            parentName.text = item.fullName
            parentCardName.text = item.bank
            secondCardName.text = item.bank

            contaonerFull1.setOnClickListener {
                isAnyItemExpanded(position)
                item.isExpandable = !item.isExpandable
                notifyItemChanged(position, Unit)
            }
            contaonerFull2.setOnClickListener {
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

}