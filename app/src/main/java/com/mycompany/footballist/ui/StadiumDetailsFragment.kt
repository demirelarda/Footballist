package com.mycompany.footballist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mycompany.footballist.R
import com.mycompany.footballist.model.teams.Venue
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import kotlinx.android.synthetic.main.fragment_stadium_details.*
import java.util.*


class StadiumDetailsFragment : Fragment() {

    var stadiumDetails : Venue = Venue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments

        if(bundle != null){
            val args = StadiumDetailsFragmentArgs.fromBundle(bundle)
            stadiumDetails = args.stadium!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stadium_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_stadium_name.text = stadiumDetails.name
        tv_stadiumAddress.text = stadiumDetails.address
        tv_stadium_capacity.text = stadiumDetails.capacity.toString()
        tv_stadiumSurface.text = stadiumDetails.surface.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        tv_stadium_city.text = stadiumDetails.city
        imageView.downloadFromUrl(stadiumDetails.image, placeHolderProgressBar(requireContext()))
    }


}