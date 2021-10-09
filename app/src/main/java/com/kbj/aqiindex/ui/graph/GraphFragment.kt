package com.kbj.aqiindex.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kbj.aqiindex.databinding.FragmentGraphBinding
import com.kbj.aqiindex.models.AQIBean
import com.kbj.aqiindex.ui.DataViewModel
import com.kbj.aqiindex.utils.KeyConstants
import com.kbj.aqiindex.utils.UtilFunctions
import com.kbj.aqiindex.utils.roundTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class GraphFragment : Fragment() {
    private val viewModel: DataViewModel by viewModels()
    private var _binding: FragmentGraphBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var lineData: LineData? = null
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val dataObserver = Observer<List<AQIBean>> { list ->
        setLineChartData(list)
    }

    private fun setLineChartData(list: List<AQIBean>) {
        val entryList: ArrayList<Entry> = ArrayList()
        ArrayList(list).sortBy { it.seconds }
        val colorList = arrayListOf<Int>()
        list.forEach {
            colorList.add(UtilFunctions.processColors(requireContext(), it.aqi))
            entryList.add(
                Entry(
                    UtilFunctions.findTimeDifference(it).toFloat(),
                    it.aqi.roundTo(2).toFloat()
                )
            )
        }
        val lineDataSet = LineDataSet(entryList, "city")
        lineDataSet.colors = colorList
        lineDataSet.fillAlpha = 110
        lineData = LineData(lineDataSet)
        binding.mLineChart.data = lineData
        binding.mLineChart.setVisibleXRangeMaximum(10f)
        binding.mLineChart.invalidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mLineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.mLineChart.description.isEnabled = false
        binding.mLineChart.legend.isEnabled = false
        scope.launch{
            viewModel.getFlow(arguments?.getString(KeyConstants.CITY)).collect {
                setLineChartData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setWebHook()
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelSocket()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
        _binding = null
    }
}