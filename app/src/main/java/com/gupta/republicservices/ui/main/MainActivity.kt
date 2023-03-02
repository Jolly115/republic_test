package com.gupta.republicservices.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gupta.republicservices.dao.DriverDao
import com.gupta.republicservices.databinding.ActivityMainBinding
import com.gupta.republicservices.network.Status
import com.gupta.republicservices.ui.route.RouteActivity
import com.gupta.republicservices.viewmodels.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val driverDao by inject<DriverDao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = DriverAdapter().apply {
                setOnItemClickListener { driver ->
                    onDriverSelected(driver.id)
                }
            }
        }

        binding.btnSort.setOnClickListener {
            (binding.recyclerView.adapter as DriverAdapter).sortListByLastName()
        }

        binding.btnRefresh.setOnClickListener {
            loadDataFromApi()
        }

        loadDataFromDB()
    }

    private fun loadDataFromApi() {
        viewModel.getData()
            .observe(this) {
                when (it.status) {
                    Status.ERROR -> {
                        //hide loader
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        //show loader
                    }
                    Status.SUCCESS -> {
                        //hide loader
                        (binding.recyclerView.adapter as DriverAdapter).submitList(it.data?.drivers)
                    }
                }
            }
    }

    private fun loadDataFromDB() {
        driverDao.getAll().observe(this) {
            if (it.isEmpty()) loadDataFromApi()
            else (binding.recyclerView.adapter as DriverAdapter).submitList(it)
        }
    }

    private fun onDriverSelected(driverId: String) {
        val intent = Intent(this, RouteActivity::class.java).apply {
            putExtra(RouteActivity.EXTRA_DRIVER_ID, driverId)
        }
        startActivity(intent)
    }
}
