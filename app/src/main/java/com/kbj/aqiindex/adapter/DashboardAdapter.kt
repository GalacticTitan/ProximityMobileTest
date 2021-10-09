package com.kbj.aqiindex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kbj.aqiindex.databinding.ItemDashboardBinding
import com.kbj.aqiindex.models.AQIBean
import com.kbj.aqiindex.utils.UtilFunctions
import com.kbj.aqiindex.utils.roundTo
import java.util.ArrayList

/**
 * This recyclerview adapter will bind the incoming data with the view
 */
class DashboardAdapter(private val mContext: Context, private val mListener: AdapterCallback) : RecyclerView.Adapter<DashboardAdapter.AQIHolder>() {
    private val aqiList: ArrayList<AQIBean> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AQIHolder {
        val itemBinding = ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AQIHolder(itemBinding, mListener)
    }

    override fun onBindViewHolder(holder: AQIHolder, position: Int) {
        val aqiBean = aqiList[position]
        holder.bind(aqiBean)
    }

    override fun getItemCount(): Int = aqiList.size

    class AQIHolder(private val itemBinding: ItemDashboardBinding, private val mListener: AdapterCallback) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(aqiBean: AQIBean) {
            itemBinding.mCity.text = aqiBean.city
            itemBinding.mAqi.text = aqiBean.aqi.roundTo(2)
            itemBinding.mAqi.setTextColor(aqiBean.color!!)
            itemBinding.mLU.text = aqiBean.lastUpdated
            itemView.setOnClickListener { mListener.onItemClicked(aqiBean.city) }
        }
    }

    /**
     *  This method is used to bind the data with the adapter
     */
    fun updateList(dataList: List<AQIBean>){
        UtilFunctions.processData(mContext, dataList, aqiList)
        notifyDataSetChanged()
    }
}