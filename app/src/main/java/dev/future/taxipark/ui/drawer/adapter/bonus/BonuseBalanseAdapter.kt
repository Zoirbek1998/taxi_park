package dev.future.taxipark.ui.drawer.adapter.bonus

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.databinding.BonuseBalanseItemBinding
import dev.future.taxipark.ui.drawer.model.BonusHistoryItem

class BonuseBalanseAdapter(var onClick: OnClickListener) :
    RecyclerView.Adapter<BonuseBalanseAdapter.ItemHolder>() {

    interface OnClickListener {
        fun onItemClickListener(model: BonusHistoryItem)
    }

    var items: List<BonusHistoryItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ItemHolder(val binding: BonuseBalanseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            BonuseBalanseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        holder.itemView.setOnClickListener {
            onClick.onItemClickListener(item)
        }

        with(holder.binding) {


            bonus.text =  holder.itemView.context.getString(R.string.bonuslar)  + "1%"
            count.text = holder.itemView.context.getString(R.string.umumiy_xizmatlar) + item.count
            totalSumma.text = holder.itemView.context.getString(R.string.umumiy_qiymat) + item.count
            totalBonus.text = item.totalBonus + " " + holder.itemView.context.getString(R.string.so_m)


        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }

}