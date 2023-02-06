package com.winnus.winnus.src.main.home.populor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass.PopularWine.PopularWineArr
import com.winnus.winnus.config.BaseFragment
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.FragmentPopularWineBinding
import com.winnus.winnus.src.main.home.model.GetPopularWineResponse
import com.winnus.winnus.src.main.home.model.ResultPopularWine
import com.winnus.winnus.src.main.home.util.popularWineViewPagerAdapter
import com.winnus.winnus.util.PostSubscribeRequest
import com.google.android.material.tabs.TabLayoutMediator

class PopularWineFragment: BaseFragment<FragmentPopularWineBinding>(FragmentPopularWineBinding::bind, R.layout.fragment_popular_wine),
    PopularWineFragmentView {

    var pos : Int? = null

    fun returnPos(): Int{
       return binding.indicatorPopularWine.selectedTabPosition
    }

    fun notifyPopularWine(){
        binding.viewpagerPopularWine.adapter?.notifyDataSetChanged()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("arguments",arguments?.getInt("position", 0).toString())
        pos = arguments?.getInt("position", 0)
        if(PopularWineArr[pos!!] == null){
            if(arguments?.getInt("position", 0) == 0){
                PopularWineFragmentService(this).tryGetPopularWine(null)
            }else{
                PopularWineFragmentService(this).tryGetPopularWine(arguments?.getInt("position", 1))
            }
        }else{
            binding.viewpagerPopularWine.adapter = popularWineViewPagerAdapter(requireContext(), PopularWineArr[pos!!]!!, this)
            binding.viewpagerPopularWine.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val child = binding.viewpagerPopularWine.getChildAt(0)
            (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            TabLayoutMediator(binding.indicatorPopularWine, binding.viewpagerPopularWine) { tab, position -> }.attach()
        }
    }

    override fun onGetPopularWineSuccess(response: GetPopularWineResponse) {
        if(response.code == 1000){
            Log.d("GetPopularWine",response.result.toString())
            var popularWine = ArrayList<ResultPopularWine>()
            popularWine.addAll(response.result)
            PopularWineArr[pos!!] = arrayListOf<ArrayList<ResultPopularWine>>()
            for(i in 0..2){
                val temp = arrayListOf<ResultPopularWine>()
                for(j in 0..5){
                    temp.add(popularWine.removeAt(0))
                }
                PopularWineArr[pos!!]!!.add(temp)
            }
            binding.viewpagerPopularWine.adapter = popularWineViewPagerAdapter(requireContext(), PopularWineArr[pos!!]!!, this)
            binding.viewpagerPopularWine.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val child = binding.viewpagerPopularWine.getChildAt(0)
            (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            TabLayoutMediator(binding.indicatorPopularWine, binding.viewpagerPopularWine) { tab, position -> }.attach()
        }
    }

    override fun onGetPopularWineFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {

    }

    override fun onPostSubscribeFailure(message: String) {
        showCustomToast(message)
    }

    override fun tryPostSubscribe(wineId: Int) {
        val postSubscribeRequest= PostSubscribeRequest(wineId)
        PopularWineFragmentService(this).tryPostSubscribe(postSubscribeRequest)
    }

}