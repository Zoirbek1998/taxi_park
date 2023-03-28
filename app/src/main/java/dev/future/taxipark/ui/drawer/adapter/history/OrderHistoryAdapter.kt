package dev.future.taxipark.ui.drawer.adapter.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.databinding.HistoryTravelItemBinding
import dev.future.taxipark.databinding.OrderHistoryDetaileItemBinding
import dev.future.taxipark.ui.drawer.model.OrderHistoryItem
import dev.future.taxipark.ui.drawer.model.OrderItem

class OrderHistoryAdapter(val context: Context) : RecyclerView.Adapter<OrderHistoryAdapter.HistoryHolder>() {


    var items: List<OrderItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class HistoryHolder(var binding: OrderHistoryDetaileItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            OrderHistoryDetaileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    @SuppressLint("SetTextI18n")
    @Suppress("UsePropertyAccessSyntax")
    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        var item = items[position]


        with(holder.binding) {
            orderTime.text = item.time
            orderDate.text = item.date
            orderSumm.text = item.price.toString()
            if(item.price!! > 0){
                orderSumm.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            }else{
                orderSumm.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
            }
            orderNumber.text = "№"+item.orderId
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}