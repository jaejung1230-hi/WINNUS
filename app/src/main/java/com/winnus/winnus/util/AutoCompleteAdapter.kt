package com.winnus.winnus.util

import android.content.Context;
import android.graphics.Color
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

//참조
//https://stackoverflow.com/questions/15119113/autocomplete-textbox-highlighting-the-typed-character-in-the-suggestion-list
class AutoCompleteAdapter(context: Context, @LayoutRes resource: Int, @IdRes textViewResourceId: Int, objects: ArrayList<String>) : ArrayAdapter<String>(context, resource, textViewResourceId, objects), Filterable {
    @LayoutRes
    private val layoutRes: Int = resource

    @IdRes
    private val textViewResId: Int = textViewResourceId
    private var fullList: ArrayList<String>
    private var originalValues: ArrayList<String>?
    private var filter: ArrayFilter? = null
    private val inflater: LayoutInflater
    private var query = ""

    init {
        fullList = objects as ArrayList<String>
        originalValues = ArrayList(fullList)
        inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return fullList.size
    }

    override fun getItem(position: Int): String {
        return fullList[position]
    }

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(inflater, position, convertView, parent, layoutRes)
    }

    private fun createViewFromResource(inflater: LayoutInflater, position: Int, @Nullable convertView: View?, parent: ViewGroup, resource: Int): View {
        val view: View
        val text: TextView?
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
        } else {
            view = convertView
        }
        try {
            if (textViewResId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = view as TextView
            } else {
                //  Otherwise, find the TextView field within the layout
                text = view.findViewById(textViewResId)
                if (text == null) {
                    throw RuntimeException(("Failed to find view with ID " + context.resources.getResourceName(textViewResId)) + " in item layout"
                    )
                }
            }
        } catch (e: ClassCastException) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView")
            throw IllegalStateException(
                "ArrayAdapter requires the resource ID to be a TextView", e
            )
        }
        val item = getItem(position)
        text!!.text = highlight(query, item.toString())
        //        if (item instanceof CharSequence) {
//            text.setText(highlight(query, (CharSequence) item));
//        } else {
//            text.setText(item.toString());
//        }
        return view
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = ArrayFilter()
        }
        return filter as ArrayFilter
    }

    private inner class ArrayFilter() : Filter() {
        private val lock = Any()
        protected override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (prefix == null) {
                query = ""
            } else {
                query = prefix.toString()
            }
            if (originalValues == null) {
                synchronized(lock) { originalValues = ArrayList(fullList) }
            }
            if (prefix == null || prefix.length == 0) {
                synchronized(lock) {
                    val list: ArrayList<String> = ArrayList(originalValues)
                    results.values = list
                    results.count = list.size
                }
            } else {
                val prefixString = prefix.toString().toLowerCase()
                val values = originalValues
                val count: Int = values!!.size
                val newValues: ArrayList<String> = ArrayList(count)
                for (i in 0 until count) {
                    val item = values[i]
                    if (item.toString().toLowerCase().contains(prefixString)) {
                        newValues.add(item)
                    }
                }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        protected override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values != null) {
                fullList = results.values as ArrayList<String>
            } else {
                fullList = ArrayList()
            }
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }

    companion object {
        private fun highlight(search: String, originalText: CharSequence): CharSequence {
            if (search.isEmpty()) return originalText

            // ignore case and accents
            // the same thing should have been done for the search text
            val normalizedText: String = Normalizer
                .normalize(originalText, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.KOREA)
            var start = originalText.indexOf(search)
            if (start < 0) {
                // not found, nothing to do
                return originalText
            } else {
                // highlight each appearance in the original text
                // while searching in normalized text
                val highlighted: Spannable = SpannableString(originalText)
                while (start >= 0) {
                    val spanStart = start.coerceAtMost(originalText.length)
                    val spanEnd = (start + search.length).coerceAtMost(originalText.length)
                    highlighted.setSpan(ForegroundColorSpan(Color.RED), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    start = originalText.indexOf(search, spanEnd)
                }
                return highlighted
            }
        }
    }
}