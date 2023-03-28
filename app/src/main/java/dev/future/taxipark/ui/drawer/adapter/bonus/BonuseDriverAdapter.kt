package dev.future.taxipark.ui.drawer.adapter.bonus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.databinding.BonuseDriverItemBinding
import dev.future.taxipark.ui.drawer.model.BonusHistoryItem
import dev.future.taxipark.ui.drawer.model.ListItem

class BonuseDriverAdapter:  RecyclerView.Adapter<BonuseDriverAdapter.ItemHolder>(){

    var items: List<ListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ItemHolder(var binding:BonuseDriverItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(BonuseDriverItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        with(holder.binding){
            driverPhone.text = item.referral
            driverBonus.text = item.totalBonus
            driverSumma.text = item.totalSumma
            driverYonalish.text = item.count

        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}