package dev.future.taxipark.ui.splash.viewpager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import dev.future.taxipark.R
import dev.future.taxipark.databinding.AdapterViewpagerBinding
import dev.future.taxipark.network.ApiService
import dev.future.taxipark.ui.registeration.model.SliderModel

class ViewPagerAdapter (var context:Context,var list:ArrayList<SliderModel>):RecyclerView.Adapter<ViewPagerAdapter.MyHolderView>() {
   var layoutInflater =LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        var view  = AdapterViewpagerBinding.inflate(layoutInflater,parent,false)
    return MyHolderView(view)
    }
    override fun onBindViewHolder(holder: MyHolderView, position: Int) {
       var model =list[position]
        with(holder){
           binding.apply {

               Log.e( "onBindViewHolder: ", ApiService.IMAGE_URL+model.url.toString() )
               Glide.with(context.applicationContext).load(ApiService.IMAGE_URL+model.url).placeholder(
                   R.drawable.ic_logo).into(ivView)
              // ivView.setImageResource(model.image)
               tvTitle.text=model.header
               tvTitle1.text =model.title
               tvDesc.text =model.description
           }
        }
    }

    override fun getItemCount() = list.size
    class MyHolderView(var binding:AdapterViewpagerBinding):ViewHolder(binding.root) {

    }


}
