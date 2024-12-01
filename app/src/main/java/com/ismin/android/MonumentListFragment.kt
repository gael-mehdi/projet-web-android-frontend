package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val MONUMENTS = "monuments"

class MonumentListFragment : Fragment() {
    private lateinit var monuments: ArrayList<Monument>
    private lateinit var monumentAdapter: MonumentAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            monuments = it.getSerializable(MONUMENTS) as ArrayList<Monument>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_monument_list, container, false)

        recyclerView = rootView.findViewById(R.id.f_monument_list_rcv_monuments)
        monumentAdapter = MonumentAdapter(monuments)
        recyclerView.adapter = monumentAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(books: ArrayList<Monument>) =
            MonumentListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MONUMENTS, monuments)
                }
            }
    }
}