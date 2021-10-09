package com.kbj.aqiindex.ui.dashboard

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kbj.aqiindex.R
import com.kbj.aqiindex.adapter.AdapterCallback
import com.kbj.aqiindex.adapter.DashboardAdapter
import com.kbj.aqiindex.callbacks.ConnectionCallBack
import com.kbj.aqiindex.databinding.FragmentDashboardBinding
import com.kbj.aqiindex.models.AQIBean
import com.kbj.aqiindex.ui.DataViewModel
import com.kbj.aqiindex.utils.KeyConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment(), AdapterCallback, ConnectionCallBack {
    private val viewModel: DataViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private var adapter: DashboardAdapter? = null
    private lateinit var connectivityManager: ConnectivityManager
    private var scope = CoroutineScope(Job() + Dispatchers.Main)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    private val dataObserver = Observer<List<AQIBean>> {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerDefaultNetworkCallback(netWorkCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mRecyclerView?.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        (binding?.mRecyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
        adapter = DashboardAdapter(requireContext(), this)
        binding?.mRecyclerView?.adapter = adapter
        viewModel.setConnectionCallBack(this)
    }

    private fun loadData() {
        if (!scope.isActive) {
            scope = CoroutineScope(Job() + Dispatchers.Main)
        }
        scope.launch {
            viewModel.getFlow().collect {
                dataLoadedUi()
                adapter?.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
        _binding = null
    }

    private val netWorkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            requireActivity().runOnUiThread {
                makeLoadingUI()
                viewModel.setWebHook()
                loadData()
            }
        }

        override fun onLost(network: Network) {
            requireActivity().runOnUiThread {
                makeFailureUI()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(netWorkCallback)
        viewModel.cancelSocket()
    }

    override fun onItemClicked(city: String) {
        val bundle = bundleOf(KeyConstants.CITY to city)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onConnectionFailure() {
        requireActivity().runOnUiThread {
            makeFailureUI()
        }
    }

    private fun makeFailureUI() {
        dataLoadedUi()
        binding?.textView8?.text = getString(R.string.no_network_connection)
    }

    private fun dataLoadedUi() {
        binding?.mRecyclerView?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.GONE
        binding?.netGroup?.visibility = View.GONE
    }

    private fun makeLoadingUI() {
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.netGroup?.visibility = View.GONE
    }
}