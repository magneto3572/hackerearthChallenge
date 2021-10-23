package com.example.hackerearthchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hackerearthchallenge.utils.NetworkUtil
import com.example.hackerearthchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var networkUtil: NetworkUtil
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        networkUtil = NetworkUtil(this)

        if(NetworkUtil.Variables.checkvalue.toString() == "true"){
            binding?.apply {
                internetError.root.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
            }
        }else{
            binding?.apply {
                internetError.root.visibility = View.VISIBLE
                fragmentContainer.visibility = View.GONE
            }
        }

        networkUtil.observe(this, {
            if(it == true){
                binding?.apply {
                    internetError.root.visibility = View.GONE
                    fragmentContainer.visibility = View.VISIBLE
                }
            }else{
                binding?.apply {
                    internetError.root.visibility = View.VISIBLE
                    fragmentContainer.visibility = View.GONE
                }
            }
        })
    }
}