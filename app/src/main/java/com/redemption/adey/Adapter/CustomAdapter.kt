package com.redemption.adey.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.redemption.adey.Model.ItemViewModel
import com.redemption.adey.Model.ItemsViewModel
import com.redemption.adey.Model.ViewItemModel
import com.redemption.adey.R
import com.squareup.picasso.Picasso

class CustomAdapter(private var mList: MutableList<ViewItemModel>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<CustomAdapter.DataAdapterViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAdapter.DataAdapterViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.bet_item, parent, false)
//
//        return TipHolder(view)

        val layout = when (viewType) {
            TYPE_Tip -> R.layout.card_view_design
            TYPE_Ad -> R.layout.native_ad_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)

    }

    companion object {
        private const val TYPE_Ad = 1
        private const val TYPE_Tip =0
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomAdapter.DataAdapterViewHolder, position: Int) {


        holder.bind(mList[position],onClickListener,position)
    }

    override fun getItemCount() = mList.size

    class OnClickListener(val clickListener: (category: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }
    override fun getItemViewType(position: Int): Int {
        if(mList[position].isAd){
            return TYPE_Ad
        }else{
            return TYPE_Tip
        }
    }

    fun setData(data: List<ViewItemModel>) {
        mList.apply {
            clear()
            addAll(data)
        }
    }
    fun updateList(playlist: ArrayList<ViewItemModel>, oldCount: Int) {
        this.mList.addAll(oldCount,playlist)
        notifyItemInserted(oldCount - 1);
        notifyItemRangeInserted(oldCount, playlist.size)
    }

    class DataAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2


        private var view: View = v
        private var itemData: ItemsViewModel? = null

        fun bind(mList: ViewItemModel,onClickListener: OnClickListener, position: Int ) {
            if(mList.isAd){
                bindAd()
            }else{
                bindTip(mList.data!!, onClickListener,position)
            }
        }

        private fun bindTip(data: ItemsViewModel, onClickListener: OnClickListener,position: Int) {



            val thumb: ImageView = itemView.findViewById(R.id.thumb)
            val title: TextView = itemView.findViewById(R.id.title)
            val itemContainer: ConstraintLayout = itemView.findViewById(R.id.itemContainer)
//            Picasso.get().load(Decrypt(match.Status)).into(match_status)
            Picasso.get().load(data.image).into(thumb);

            // sets the text to the textview from our itemHolder class
            title.text = data.title

            itemContainer.setOnClickListener{
                onClickListener.onClick(position)
            }


        }

        private fun bindAd() {
            val template = view.findViewById<TemplateView>(R.id.my_template)
            MobileAds.initialize(template.context)
            val adLoader: AdLoader = AdLoader.Builder(template.context, template.context.getString(R.string.nativeAdUnit))
                .forNativeAd(object : NativeAd.OnNativeAdLoadedListener {
                    override fun onNativeAdLoaded(nativeAd: NativeAd) {
//                        val mainBg = ColorDrawable(ContextCompat.getColor(template.context, R.color.primaryBlue))
//                        val tertiaryTextBg = ColorDrawable(ContextCompat.getColor(this@MainActivity, R.color.customBlack))
                        val styles =
                            NativeTemplateStyle.Builder()
                                .build()
                        template.visibility = View.VISIBLE
                        template.setStyles(styles)
                        template.setNativeAd(nativeAd)
                    }
                })
                .build()

            adLoader.loadAd(AdRequest.Builder().build())

        }

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            //5
        }
    }
}


//class CustomAdapter(private var mList: MutableList<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
//    // create new views
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        // inflates the card_view_design view
//        // that is used to hold list item
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.card_view_design, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    // binds the list items to a view
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val ItemsViewModel = mList[position]
//
//        // sets the image to the imageview from our itemHolder class
//        // holder.thumb.setImageResource(ItemsViewModel.image)
//        Picasso.get().load(ItemsViewModel.image).into(holder.thumb);
//
//        // sets the text to the textview from our itemHolder class
//        holder.title.text = ItemsViewModel.title
//
//        // sets the text to the textview from our itemHolder class
////        holder.duration.text = ItemsViewModel.duration
//
//    }
//
//    // return the number of the items in the list
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    // Holds the views for adding it to image and text
//    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val thumb: ImageView = itemView.findViewById(R.id.thumb)
//        val title: TextView = itemView.findViewById(R.id.title)
////        val duration: TextView = itemView.findViewById(R.id.duration)
//    }
//
//    fun updateList(playlist: ArrayList<ItemsViewModel>, oldCount: Int) {
//        this.mList.addAll(oldCount,playlist)
//        notifyItemInserted(oldCount - 1);
//        notifyItemRangeInserted(oldCount, playlist.size)
//    }
//}