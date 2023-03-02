package com.gupta.republicservices.ui.route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gupta.republicservices.databinding.ActivityRouteBinding
import com.gupta.republicservices.viewmodels.RouteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RouteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouteBinding

    private val driverId by lazy { intent?.getStringExtra(EXTRA_DRIVER_ID) ?: "" }

    private val viewModel: RouteViewModel by viewModel { parametersOf(driverId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.route.observe(this) { route ->
            binding.tvRouteType.text = "Route Type: ${route?.type}"
            binding.tvRouteName.text = route?.name
        }
    }

    companion object {
        const val EXTRA_DRIVER_ID = "driver_id"
    }
}
