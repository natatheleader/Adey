package com.redemption.derama.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.redemption.derama.Model.Season
import com.redemption.derama.Model.SeasonViewItemModel
import com.redemption.derama.R

class SeasonAdapter (private val data: MutableList<SeasonViewItemModel>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<SeasonAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layout = when (viewType) {
            TYPE_DATA -> R.layout.season_item
            TYPE_Ad -> R.layout.native_ad_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return MyViewHolder(view)
    }

    companion object {
        private const val TYPE_Ad = 1
        private const val TYPE_DATA =0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position], onClickListener, position)
    }

    override fun getItemCount() = data.size

    class OnClickListener(val clickListener: (category: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if(data[position].isAd){
            TYPE_Ad
        }else{
            TYPE_DATA
        }
    }

    fun updateList(playlist: ArrayList<SeasonViewItemModel>, oldCount: Int) {
        this.data.addAll(oldCount,playlist)
        notifyItemInserted(oldCount )
        notifyItemRangeInserted(oldCount, playlist.size)
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v

        fun bind(data: SeasonViewItemModel, onClickListener: OnClickListener, position: Int ) {
            if(data.isAd){
                bindAd()
            }else{
                bindTip(data.season!!, onClickListener,position)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun bindTip(data: Season, onClickListener: OnClickListener, position: Int) {

            val title: TextView = itemView.findViewById(R.id.seasonTitle)
            val itemContainer: ConstraintLayout = itemView.findViewById(R.id.season)

            title.text = "Season - " + data.number.toString() + " / ክፍል - " + data.number.toString()

            itemContainer.setOnClickListener{
                onClickListener.onClick(position)
            }
        }

        private fun bindAd() {
            val template = view.findViewById<TemplateView>(R.id.my_template)
            MobileAds.initialize(template.context)
            val adLoader: AdLoader = AdLoader.Builder(template.context, template.context.getString(R.string.nativeAdUnit))
                .forNativeAd { nativeAd -> //                        val mainBg = ColorDrawable(ContextCompat.getColor(template.context, R.color.primaryBlue))
                    //                        val tertiaryTextBg = ColorDrawable(ContextCompat.getColor(this@MainActivity, R.color.customBlack))
                    val styles =
                        NativeTemplateStyle.Builder()
                            .build()
                    template.visibility = View.VISIBLE
                    template.setStyles(styles)
                    template.setNativeAd(nativeAd)
                }
                .build()

            adLoader.loadAd(AdRequest.Builder().build())
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {

        }
    }
}