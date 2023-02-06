package com.winnus.winnus.src.main.search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.acidityGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.bodyGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.sweetnessGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.tanninGlobal
import com.winnus.winnus.config.BaseFragment
import com.winnus.winnus.databinding.FragmentSearchBinding
import com.winnus.winnus.src.filter.FilterActivity
import com.winnus.winnus.src.filter.FilterRecyclerAdapter
import com.winnus.winnus.src.main.search.model.*
import com.winnus.winnus.src.main.search.util.HotSearchedRecyclerAdapter
import com.winnus.winnus.src.main.search.util.MySearchedRecyclerAdapter
import com.winnus.winnus.src.searchResult.SearchResultActivity
import com.winnus.winnus.src.shopResult.ShopResultActivity
import com.winnus.winnus.util.AutoCompleteAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.util.ArrayList

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchFragmentView  {
    var wordList = mutableListOf<String>()

    var locList = mutableListOf<String>("전체","서울","성남", "부산", "대구", "대전", "울산", "광주", "인천")
    var locListAll = mutableListOf<String>("전체")
    var locListSeoul = mutableListOf<String>("전체","종로구", "중구", "용산구", "성동구", "광진구", "중랑구","성북구","동대문구","강북구","도봉구",
        "노원구","은평구","서대문구","마포구","양천구","강서구","구로구","금천구", "영등포구", "동작구", "관악구", "서초구",
        "강남구","송파구","강동구")
    var locListSungnam = mutableListOf<String>("전체", "수정구","중원구","분당구")
    var locListBusan = mutableListOf<String>("전체","중구", "동구", "서구", "영도구", "부산진구", "동래구", "연제구", "금정구", "북구", "사상구", "사하구", "강서구", "남구", "해운대구", "수영구", "기장군")
    var locListDaegu = mutableListOf<String>("전체","서구", "북구", "동구", "달서구", "중구", "수성구", "남구", "달성군")
    var locListDaejun = mutableListOf<String>("전체","중구", "동구", "서구", "유성구", "대덕구")
    var locListUlsan = mutableListOf<String>("전체","북구", "남구", "둥구", "동구", "울주군")
    var locListGwangju = mutableListOf<String>("전체","동구", "남구", "서구", "북구", "광산구")
    var locListInchon = mutableListOf<String>("전체","중구", "동구", "미추홀구", "남동구", "연수구", "부평구", "계양구", "서구", "강화군", "옹진군")

    lateinit var adapter : ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.containerFragmentSearchBinding.setOnClickListener {
            hideKeyboard(binding.editSearchShop)
        }

        ViewCompat.setTransitionName(binding.editSearchWineFragment, binding.editSearchWineFragment.transitionName)

        if(com.winnus.winnus.config.ApplicationClass.wineNames.wordList == null){
            showLoadingMessageDialog(requireContext(),"와인정보를 불러오는 중입니다")
            SearchFragmentService(this).tryGetSearchWineName()
        }else{
            val adapter2 = AutoCompleteAdapter(requireContext(), R.layout.row_dropdown, R.id.text1, com.winnus.winnus.config.ApplicationClass.wineNames.wordList as ArrayList<String>)
            binding.editSearchShop.setAdapter(adapter2)

            binding.editSearchShop.threshold = 1
            binding.editSearchShop.setAdapter(adapter2)
            binding.editSearchShop.setOnTouchListener { v: View, event: MotionEvent ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    (v as AutoCompleteTextView).showDropDown()
                    v.requestFocus()
                    v.performClick() // Added to avoid warning "onTouch lambda should call View#performClick when a click is detected".
                }
                false
            }
        }
        binding.tvSearchWine.setOnClickListener {
            changeSearch(it)
        }

        binding.tvSearchShop.setOnClickListener {
            changeSearch(it)
        }
        binding.btnSearchShop.setOnClickListener {
            val intent = Intent(requireContext(), ShopResultActivity::class.java)
            intent.putExtra("LocationBig",binding.spinnerLocationBig.selectedItemPosition)
            intent.putExtra("LocationSmall",binding.spinnerLocationSmall.selectedItemPosition)
            intent.putExtra("text",binding.editSearchShop.text.toString())

            context?.startActivity(intent)
        }

        binding.editSearchWineFragment.setOnClickListener {
            val searchNameFragment: Fragment = SearchNameFragment()
            val fragmentManager: FragmentManager? = this.fragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            val bundle : Bundle = Bundle()
            searchNameFragment.arguments = bundle;
            fragmentManager
                .beginTransaction()
                .addSharedElement(binding.editSearchWineFragment, binding.editSearchWineFragment.transitionName)
                .replace(R.id.main_frm, searchNameFragment)
                .addToBackStack(null)
                .commit();
        }

        binding.spinnerLocationBig.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locList)
        binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListAll)

        binding.spinnerLocationBig.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListAll)}
                    1->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListSeoul)}
                    2->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListSungnam)}
                    3->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListBusan)}
                    4->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListDaegu)}
                    5->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListDaejun)}
                    6->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListUlsan)}
                    7->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListGwangju)}
                    8->{binding.spinnerLocationSmall.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locListInchon)}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.btnSearchFilter.setOnClickListener {
            val intent = Intent(context, FilterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr = arrayListOf(arrayListOf(false,false,false,false), arrayListOf(false,false,false,false,false,false),
            arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false), arrayListOf(false,false,false,false,false,false))
        sweetnessGlobal = 0
        acidityGlobal= 0
        bodyGlobal = 0
        tanninGlobal = 0
    }

    override fun onGetSearchWineNameSuccess(response: GetWineNameResponse) {
        dismissLoadingMessageDialog()
        if(response.isSuccess){

            for(i in response.result.wineNames){
                wordList.add(i.wineName)
            }

            com.winnus.winnus.config.ApplicationClass.wineNames.wordList = wordList

            val adapter2 = AutoCompleteAdapter(requireContext(), R.layout.row_dropdown, R.id.text1, wordList as ArrayList<String>)
            binding.editSearchShop.setAdapter(adapter2)

            binding.editSearchShop.threshold = 1
            binding.editSearchShop.setAdapter(adapter2)
            binding.editSearchShop.setOnTouchListener { v: View, event: MotionEvent ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    (v as AutoCompleteTextView).showDropDown()
                    v.requestFocus()
                    v.performClick() // Added to avoid warning "onTouch lambda should call View#performClick when a click is detected".
                }
                false
            }
        }
    }

    override fun onGetSearchWineNameFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }

    override fun onPostSearchedSuccess(response: postSearchedResponse) {
    }

    override fun onPostSearchedFailure(message: String) {

    }

    override fun onGetSearchedSuccess(response: GetSearchedResponse) {
    }

    override fun onGetSearchedFailure(message: String) {
    }

    override fun onPatchSearchedSuccess(response: PatchSearchedResponse) {

    }

    override fun onPatchSearchedFailure(message: String) {

    }

    override fun onGetHotSearchedSuccess(response: HotSearchedResponse) {
    }

    override fun onGetHotSearchedFailure(message: String) {

    }

    override fun deleteSearched(i : Int){
    }

    override fun clickSearched(i : String){
    }

    fun changeSearch(view : View){
        val sort = listOf<TextView>(binding.tvSearchWine,binding.tvSearchShop)
        val linear = listOf<LinearLayout>(binding.linearSearchWine,binding.linearSearchShop)
        for(tv in sort){
            tv.setTextColor(Color.parseColor("#E6E6E6"))
        }
        for(tv in linear){
            tv.visibility = View.GONE
        }
        var count = 0
        for(tv in sort){
            if(view.id  == tv.id){
                tv.setTextColor(Color.parseColor("#252525"))
                linear[count].visibility = View.VISIBLE
            }
            count += 1
        }
    }
}