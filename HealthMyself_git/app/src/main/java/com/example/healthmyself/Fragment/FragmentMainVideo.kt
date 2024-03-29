package com.example.healthmyself.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.healthmyself.Activity.YoutubeActivity
import com.example.healthmyself.R

class FragmentMainVideo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_video, null)

        val ex1 = view.findViewById<TextView>(R.id.btn_upperbody)
        val ex2 = view.findViewById<TextView>(R.id.btn_lower_body)
        val ex3 = view.findViewById<TextView>(R.id.btn_aerobic)
        val ex4 = view.findViewById<TextView>(R.id.btn_core)

        ex1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("test", "onClick: ")
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra("keyword", "上体ホームトレーニング")
                startActivity(intent)
            }
        })
        ex2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("test", "onClick: ")
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra("keyword", "下半身ホームトレーニング")
                startActivity(intent)
            }
        })
        ex3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("test", "onClick: ")
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra("keyword", "有酸素ホームトレーニング")
                startActivity(intent)
            }
        })
        ex4.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("test", "onClick: ")
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra("keyword", "コアホームトレーニング")
                startActivity(intent)
            }
        })
        return view
    }

}