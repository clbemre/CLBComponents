package com.clb.components.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.clb.components.R

/**
 * Created by Emre CELEBI on 30.12.2018. cLB
 * clb.emrx
 */
class AdapterSpinner(private var context: Context, private val sList: ArrayList<String>?) : BaseAdapter() {

    private var listener: AdapterSpinner.Listener? = null

    fun setListener(listener: AdapterSpinner.Listener) {
        this.listener = listener
    }

    override fun getCount(): Int {
        return sList!!.size
    }

    override fun getItem(position: Int): Any {
        return sList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder?
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.clb_spinner_layout_textview, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        if (position == 0) {
            viewHolder.tvTitle?.setTypeface(null, Typeface.BOLD)
        } else {
            viewHolder.tvTitle?.setTypeface(null, Typeface.NORMAL)
        }

        val item: String = sList!![position]
        viewHolder.tvTitle?.text = item
        return view!!
    }

    private class ViewHolder(row: View?) {
        var tvTitle: TextView? = null

        init {
            tvTitle = row?.findViewById(R.id.tvTitle) as TextView
        }
    }

    interface Listener {
        fun onItemClicked(parent: View, positionItem: Int, item: String)
    }

}