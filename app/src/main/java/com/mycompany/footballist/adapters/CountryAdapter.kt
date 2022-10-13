package com.mycompany.footballist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mycompany.footballist.ui.CountryFragment
import com.mycompany.footballist.R
import com.mycompany.footballist.model.country.Country
import com.mycompany.footballist.utils.*
import kotlinx.android.synthetic.main.country_row.view.*


open class CountryAdapter(private var countryList: ArrayList<Country>,private val fragment: CountryFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_row,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = countryList[position]
        if(holder is CountryViewHolder){
            holder.itemView.countryName.text = model.name
            //holder.itemView.iv_countryImage.downloadFromUrl(model.flag, placeHolderProgressBar(holder.itemView.context))
            val imageLoaderForSvg = loadWithCoil(holder.itemView.context)
            holder.itemView.iv_countryImage.load(model.flag,imageLoaderForSvg)

            holder.itemView.setOnClickListener {
                if(model.code != null){
                    fragment.moveToLeagues(model.code)
                }
                else{
                    fragment.showErrorSnackBar(fragment.getString(R.string.no_league_data_available),true)
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}