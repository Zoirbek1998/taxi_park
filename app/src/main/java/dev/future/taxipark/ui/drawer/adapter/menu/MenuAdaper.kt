package dev.future.customdriwermenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.databinding.MenuItemBinding
import dev.future.taxipark.ui.drawer.model.MenuModel

class MenuAdaper(var items: ArrayList<MenuModel>, var click: OnClickListener) :
    RecyclerView.Adapter<MenuAdaper.ItemHolder>() {

    interface OnClickListener {
        fun onItemClickListener(model: MenuModel, position: Int)
    }

    class ItemHolder(var binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        holder.itemView.setOnClickListener {
            items.forEach {
                it.chacked = false
            }
            click.onItemClickListener(item, position)
            item.chacked = true

            notifyDataSetChanged()
        }

        if (item.chacked) {
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.active_color
                )
            )
            holder.binding.menuIcon.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        } else {
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.binding.menuIcon.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )

        }





        with(holder.binding) {
            menuIcon.setImageResource(item.image)
            menuName.text = item.name
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}