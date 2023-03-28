package dev.future.taxipark.ui.drawer.adapter.myBrigada

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.databinding.MyBrigadaItemBinding
import dev.future.taxipark.databinding.RekvizitCardItemBinding
import dev.future.taxipark.ui.drawer.adapter.rekvizit.RekvizitCardAdapter
import dev.future.taxipark.ui.drawer.model.MyBrigadaModel

class MyBrigadaAdapter(val items: ArrayList<MyBrigadaModel>) :
    RecyclerView.Adapter<MyBrigadaAdapter.ItemHolder>() {

    class ItemHolder(var binding: MyBrigadaItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MyBrigadaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        with(holder.binding) {
            driverId.text = item.driverId.toString()
            driverName.text = item.referalId?.driver
            driverPhone.text = item.referalId?.phone
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }

}