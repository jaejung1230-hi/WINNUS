package com.winnus.winnus.src.main.home.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass.PopularWine.PopularWineArr
import com.winnus.winnus.config.ApplicationClass.themeWine.themeWineArr
import com.winnus.winnus.config.ApplicationClass.todayWine.todayWineArr
import com.winnus.winnus.config.BaseFragment
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.FragmentHomeBinding
import com.winnus.winnus.src.main.home.populor.PopularWineFragment
import com.winnus.winnus.src.main.home.ThemeWineFragmentService
import com.winnus.winnus.src.main.home.ThemeWineFragmentView
import com.winnus.winnus.src.main.home.model.*
import com.winnus.winnus.src.main.home.util.AdsBigViewPagerAdapter
import com.winnus.winnus.src.main.home.util.AdsViewPagerAdapter
import com.winnus.winnus.src.main.home.util.ThemeViewPagerAdapter
import com.winnus.winnus.src.main.home.util.TodayWineHorizontalAdapter
import com.winnus.winnus.util.PostSubscribeRequest
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView, ThemeWineFragmentView,  SwipeRefreshLayout.OnRefreshListener {
    val popularTabList = listOf<String>("전체","레드","로제","화이트","스파클링")
    val themeTabList = listOf<String>("#플로럴 아로마","#홈파티를 위한","#가을을 어울린")
    val themeWineList = listOf<String>("floral","homeParty","autumn")

    private var currentPosition = Int.MAX_VALUE/2
    private var myHandler = MyHandler()
    private val intervalTime = 10000.toLong()
    private var themeWineNum = 0

    val adarr_mini = listOf<AbsInfo>(AbsInfo(R.drawable.content1_mini,"와인이 있는 100가지 장면","영화로 시작해 와인으로 끝나는","https://book.naver.com/bookdb/book_detail.nhn?bid=20580383"),
        AbsInfo(R.drawable.content2,"해외 와인? 모두 여기에서","와인 직구 사이트 정리","https://www.sevenzone.com/bbs/board.php?bo_table=column&wr_id=376"),
        AbsInfo(R.drawable.content3,"천은의 자연 속 뉴질랜드 와인","본격 뉴질랜드 와인을 소개합니다","https://post.naver.com/viewer/postView.nhn?volumeNo=17126393&memberNo=4753342&vType=VERTICAL"),
        AbsInfo(R.drawable.content4,"슬기로운 와인생활","와인 Pick, 이젠 고민하지 마세요!","https://taling.me/Talent/Detail/30131"))

    val adarr = listOf<AbsInfo>(AbsInfo(R.drawable.content1,"와인이 있는 100가지 장면","영화로 시작해 와인으로 끝나는","https://book.naver.com/bookdb/book_detail.nhn?bid=20580383"),
        AbsInfo(R.drawable.content2,"해외 와인? 모두 여기에서","와인 직구 사이트 정리","https://www.sevenzone.com/bbs/board.php?bo_table=column&wr_id=376"),
        AbsInfo(R.drawable.content3,"천은의 자연 속 뉴질랜드 와인","본격 뉴질랜드 와인을 소개합니다","https://post.naver.com/viewer/postView.nhn?volumeNo=17126393&memberNo=4753342&vType=VERTICAL"),
        AbsInfo(R.drawable.content4,"슬기로운 와인생활","와인 Pick, 이젠 고민하지 마세요!","https://taling.me/Talent/Detail/30131"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeSwiperefreshlayout.setOnRefreshListener(this)

        // "전체","레드","화이트","로제","스파클링" 추가
        for(i in popularTabList){
            binding.tabPopularWine.addTab(binding.tabPopularWine.newTab().setText(i))
        }
        //최초 전체 선택
        getFragmentManager()?.beginTransaction()?.replace(R.id.frm_popular_wine, PopularWineFragment().apply {
            arguments = Bundle().apply {
                putInt("position", 0)
            }
        },"PopularWineFragment")?.commit()

        // 인기 탭 변경시 액션
        binding.tabPopularWine.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment = PopularWineFragment()
                val bundle = Bundle()
                bundle.putInt("position", position)
                selected.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(R.id.frm_popular_wine, selected,"PopularWineFragment")?.commitAllowingStateLoss()

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })



        if(todayWineArr == null){
            ThemeWineFragmentService(this).tryGetThemeWine(themeWineList[themeWineNum])
            showLoadingMessageDialog(requireContext(),"와인정보 불러오는 중")
        }else{
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.recyclerTodayWine.layoutManager = linearLayoutManager
            binding.recyclerTodayWine.adapter = TodayWineHorizontalAdapter(requireContext(), todayWineArr!!, this)

            binding.frmThemeWine.adapter = ThemeViewPagerAdapter(requireContext(),this)
            binding.frmThemeWine.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            TabLayoutMediator(binding.tabThemeWine,binding.frmThemeWine){tab,position->
                tab.text = themeTabList[position]
            }.attach()
        }

        //광고 뷰페이져
        binding.viewpagerBrandStory.adapter = AdsViewPagerAdapter(requireContext(), adarr_mini)
        binding.viewpagerBrandStory.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerBrandStory.setCurrentItem(currentPosition, false)
        binding.viewpagerBrandStory.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }

        //광고 뷰페이져
        binding.viewpagerBrandStoryBig.adapter = AdsBigViewPagerAdapter(requireContext(), adarr)
        binding.viewpagerBrandStoryBig.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerBrandStoryBig.setCurrentItem(currentPosition, false)
        binding.viewpagerBrandStoryBig.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }

    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, intervalTime)
    }

    private fun autoScrollStop(){
        myHandler.removeMessages(0)
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.what == 0) {
                ++currentPosition
                binding.viewpagerBrandStory.setCurrentItem(currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    override fun onGetTodayWineSuccess(response: GetTodayWineResponse) {
        dismissLoadingMessageDialog()

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerTodayWine.layoutManager = linearLayoutManager
        todayWineArr = response.result.todayWines as ArrayList<ResultTodayWine>
        binding.recyclerTodayWine.adapter = TodayWineHorizontalAdapter(requireContext(), response.result.todayWines as ArrayList<ResultTodayWine>, this)

    }

    override fun onGetTodayWineFailure(message: String) {
        dismissLoadingMessageDialog()
        todayWineArr = null
        Log.d("aaseTest", "실패")
        showCustomToast(message)
    }

    override fun onGetThemeWineSuccess(response: GetThemeWineResponse) {
        Log.d("aaseTest", "성공 $themeWineNum")
        themeWineArr[themeWineNum] = response.result.themeWineList.subList(0,6)
        themeWineNum += 1
        if(themeWineNum <= 2){
            Log.d("aaseTest", "무한루푸?")
            ThemeWineFragmentService(this).tryGetThemeWine(themeWineList[themeWineNum])
        }
        else{
            Log.d("aaseTest", "완료")

            HomeFragmentService(this).tryGetTodayWine()
            binding.frmThemeWine.adapter = ThemeViewPagerAdapter(requireContext(),this)
            binding.frmThemeWine.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            TabLayoutMediator(binding.tabThemeWine,binding.frmThemeWine){tab,position->
                tab.text = themeTabList[position]
            }.attach()
            val child = binding.frmThemeWine.getChildAt(0)
            (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            dismissLoadingMessageDialog()
        }
    }

    override fun onGetThemeWineFailure(message: String) {
        Log.d("aaseTest", "실패 $themeWineNum")
        themeWineArr[themeWineNum] = null
        themeWineNum += 1
        if(themeWineNum <= 2){
            ThemeWineFragmentService(this).tryGetThemeWine(themeWineList[themeWineNum])
        }
        else{
            Log.d("aaseTest", "완료")
            HomeFragmentService(this).tryGetTodayWine()
            binding.frmThemeWine.adapter = ThemeViewPagerAdapter(requireContext(),this)
            binding.frmThemeWine.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            TabLayoutMediator(binding.tabThemeWine,binding.frmThemeWine){tab,position->
                tab.text = themeTabList[position]
            }.attach()
            dismissLoadingMessageDialog()
        }
    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {

    }

    override fun onPostSubscribeFailure(message: String) {
        showCustomToast(message)
    }

    override fun tryPostSubscribe(wineId: Int) {
        val postSubscribeRequest= PostSubscribeRequest(wineId)
        HomeFragmentService(this).tryPostSubscribe(postSubscribeRequest)
    }

    override fun onRefresh() {
        binding.homeSwiperefreshlayout.isRefreshing = false
        showLoadingMessageDialog(requireContext(),"와인정보 불러오는 중")
        PopularWineArr = arrayListOf<ArrayList<ArrayList<ResultPopularWine>>?>(null,null,null,null,null)

        getFragmentManager()?.beginTransaction()?.replace(R.id.frm_popular_wine, PopularWineFragment().apply {
            arguments = Bundle().apply {
                putInt("position", 0)
            }
        })?.commitAllowingStateLoss()
        themeWineNum = 0
        ThemeWineFragmentService(this).tryGetThemeWine(themeWineList[themeWineNum])
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1820) { // today
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val pos = data?.getIntExtra("pos",-1)
                val sub = data?.getStringExtra("sub")
                if(pos != null){
                    if(pos != -1){
                        if (sub != null) {
                            todayWineArr?.get(pos)?.userSubscribeStatus = sub
                            binding.recyclerTodayWine.adapter?.notifyItemChanged(pos)
                        }
                    }
                }
            }
        }
        if (requestCode == 1821) { // today
            Log.d("aaaaa","onActivityResult 1821")
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val pos = data?.getIntExtra("pos",-1)
                val sub = data?.getStringExtra("sub")
                if(pos != null){
                    if(pos != -1){
                        if (sub != null) {
                            themeWineArr[binding.tabThemeWine.selectedTabPosition]?.get(pos)?.userSubscribeStatus = sub
                            binding.frmThemeWine.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        if (requestCode == 1819) { // today
            Log.d("aaaaa","onActivityResult 1819")
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val pos = data?.getIntExtra("pos",-1)
                val sub = data?.getStringExtra("sub")
                if(pos != null){
                    if(pos != -1){
                        if (sub != null) {

                            val fragment = getFragmentManager()?.findFragmentByTag("PopularWineFragment") as PopularWineFragment
                            Log.d("aaaaa","onActivityResult 1819 ${binding.tabPopularWine.selectedTabPosition} ${fragment.returnPos()} ${pos}")
                            PopularWineArr[binding.tabPopularWine.selectedTabPosition]?.get(fragment.returnPos())?.get(pos)?.userSubscribeStatus = sub
                            fragment.notifyPopularWine()
                        }
                    }
                }
            }
        }
    }
}