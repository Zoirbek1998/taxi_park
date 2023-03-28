package dev.future.taxipark.ui.drawer.adapter.balanse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R

import dev.future.taxipark.databinding.RekvizitCardItemBinding
import dev.future.taxipark.databinding.ZayavkaNaVivodItemBinding
import dev.future.taxipark.ui.drawer.adapter.rekvizit.RekvizitCardAdapter
import dev.future.taxipark.ui.drawer.model.*

class BalanseAdapter(var items: List<TransactionsItem>, var click: OnClickListener,) :
    RecyclerView.Adapter<BalanseAdapter.ItemHolderView>() {

    class ItemHolderView(var binding: ZayavkaNaVivodItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BalanseAdapter.ItemHolderView {
        return ItemHolderView(
            ZayavkaNaVivodItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BalanseAdapter.ItemHolderView, position: Int) {
        var item = items?.get(position)

        holder.itemView.setOnClickListener {
            click.onItemClickListener(item)
        }

        with(holder.binding){

            summa.text = item?.summa.toString() +" сум"
            driverId.text = "#"+item?.driverId.toString()

                if (item?.status?.jsonMemberInt == NEW){

                    statusName.text = holder.itemView.context.getString(R.string.otkazilgan)
                    statusName.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                    summa.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                    statusCom1.visibility = View.VISIBLE

            }else if (item?.status?.jsonMemberInt == PROCESS){
                    statusName.text = holder.itemView.context.getString(R.string.otkazilgan)
                    statusName.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.yellov))
                    summa.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.yellov))
                    statusCom1.visibility = View.VISIBLE

            }else if (item?.status?.jsonMemberInt == CONFIRM){

                statusName.text = holder.itemView.context.getString(R.string.otkazilgan)
                statusName.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                    summa.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                statusCom1.visibility = View.VISIBLE


//                myText.setTextColor(AppCompatResources.getColorStateList(this, R.color.xxx))

            }else if (item?.status?.jsonMemberInt == CANCEL){
                    statusName.text = holder.itemView.context.getString(R.string.otkazilgan)
                    statusName.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
                    summa.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
                    statusCom1.visibility = View.VISIBLE
            }
        }





    }

    override fun getItemCount(): Int {
        return items?.count() ?: 0
    }

    interface OnClickListener {
        fun onItemClickListener(model: TransactionsItem)
    }
}