package com.winnus.winnus.src.main.search

import android.content.Intent
import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass.wineNames.wordList
import com.winnus.winnus.config.BaseFragment
import com.winnus.winnus.databinding.FragmentSearchNameBinding
import com.winnus.winnus.src.main.search.model.*
import com.winnus.winnus.src.main.search.util.HotSearchedRecyclerAdapter
import com.winnus.winnus.src.main.search.util.MySearchedRecyclerAdapter
import com.winnus.winnus.src.searchResult.SearchResultActivity
import com.winnus.winnus.util.AutoCompleteAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.util.ArrayList

class SearchNameFragment : BaseFragment<FragmentSearchNameBinding>(FragmentSearchNameBinding::bind, R.layout.fragment_search_name), SearchFragmentView  {

    var locList = mutableListOf<String>("전체","서울","성남시")
    var locListAll = mutableListOf<String>("전체")
    var locListSeoul = mutableListOf<String>("전체","종로구", "중구", "용산구", "성동구", "광진구", "중랑구","성북구","동대문구","강북구","도봉구",
        "노원구","은평구","서대문구","마포구","양천구","강서구","구로구","금천구", "영등포구", "동작구", "관악구", "서초구",
        "강남구","송파구","강동구")
    var locListSungnam = mutableListOf<String>("전체", "수정구","중원구","분당구")

    lateinit var adapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setTransitionName(binding.editSearchWineFragment, binding.editSearchWineFragment.transitionName)

        binding.containerFragmentSearchNameBinding.setOnClickListener {
            hideKeyboard(binding.editSearchWineFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        SearchFragmentService(this).tryGetSearched()



        binding.imgSearchBack.setOnClickListener {
            val searchFragment: Fragment = SearchFragment()
            val fragmentManager: FragmentManager? = this.fragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            val bundle : Bundle = Bundle()
            searchFragment.arguments = bundle;
            fragmentTransaction.replace(R.id.main_frm, searchFragment)
            fragmentTransaction.addSharedElement(binding.editSearchWineFragment, binding.editSearchWineFragment.transitionName)
            fragmentTransaction.commit()
        }

        val adapter2 = AutoCompleteAdapter(requireContext(), R.layout.row_dropdown, R.id.text1, wordList as ArrayList<String>)
        binding.editSearchWineFragment.setAdapter(adapter2)

        binding.editSearchWineFragment.threshold = 1
        binding.editSearchWineFragment.setAdapter(adapter2)
        binding.editSearchWineFragment.setOnTouchListener { v: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (v as AutoCompleteTextView).showDropDown()
                v.requestFocus()
                v.performClick() // Added to avoid warning "onTouch lambda should call View#performClick when a click is detected".
            }
            false
        }

        binding.editSearchWineFragment.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val postSearchedRequest = PostSearchedRequest(binding.editSearchWineFragment.text.toString())
                SearchFragmentService(this).tryPostSearched(postSearchedRequest)
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("text",binding.editSearchWineFragment.text.toString())
                binding.editSearchWineFragment.setText("")
                context?.startActivity(intent)
                handled = true
            }
            handled
        }
    }

    override fun onGetSearchWineNameSuccess(response: GetWineNameResponse) {
    }

    override fun onGetSearchWineNameFailure(message: String) {
    }

    override fun onPostSearchedSuccess(response: postSearchedResponse) {
    }

    override fun onPostSearchedFailure(message: String) {

    }

    override fun onGetSearchedSuccess(response: GetSearchedResponse) {
        if(response.result.searchedList != null){
            binding.btnDeleteSearched.setOnClickListener {
                SearchFragmentService(this).tryPatchSearched(null)
                SearchFragmentService(this).tryGetSearched()
                for(i in response.result.searchedList.indices){
                    binding.recyclerMySearched.adapter?.notifyItemRemoved(i)
                }
                binding.recyclerMySearched.adapter?.notifyDataSetChanged()
            }
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
                flexDirection = FlexDirection.ROW // 가로 방향 정렬
                justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
            }.let {
                binding.recyclerMySearched.layoutManager = it
                binding.recyclerMySearched.adapter = MySearchedRecyclerAdapter(requireContext(), response.result.searchedList, this)
            }
        }

        SearchFragmentService(this).tryGetHotSearched()
    }

    override fun onGetSearchedFailure(message: String) {
        SearchFragmentService(this).tryGetHotSearched()

    }

    override fun onPatchSearchedSuccess(response: PatchSearchedResponse) {

    }

    override fun onPatchSearchedFailure(message: String) {

    }

    override fun onGetHotSearchedSuccess(response: HotSearchedResponse) {
        val gridLayoutManager = object : GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams) : Boolean {
                lp.width = width / 2 - 30
                return true
            }
        }
        binding.recyclerHotSearched.layoutManager = gridLayoutManager
        binding.recyclerHotSearched.setHasFixedSize(true)
        binding.recyclerHotSearched.adapter = HotSearchedRecyclerAdapter(requireContext(), response.result.hotSearchedList, this)
    }

    override fun onGetHotSearchedFailure(message: String) {

    }

    override fun deleteSearched(i : Int){
        SearchFragmentService(this).tryPatchSearched(i)
    }

    override fun clickSearched(i : String){
        val postSearchedRequest = PostSearchedRequest(i)
        SearchFragmentService(this).tryPostSearched(postSearchedRequest)
        val intent = Intent(context, SearchResultActivity::class.java)
        intent.putExtra("text",i)
        binding.editSearchWineFragment.setText("")
        context?.startActivity(intent)
    }
}