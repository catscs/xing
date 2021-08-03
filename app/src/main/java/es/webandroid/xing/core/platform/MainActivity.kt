package es.webandroid.xing.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.xing.core.connectivity.base.ConnectivityProvider
import es.webandroid.xing.core.extensions.toast
import es.webandroid.xing.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ConnectivityProvider.ConnectivityStateListener {

    @Inject
    lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if (!state.isNetworkAvailable()) {
            toast(getString(es.webandroid.xing.R.string.not_internet_available))
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityProvider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityProvider.removeListener(this)
    }
}
