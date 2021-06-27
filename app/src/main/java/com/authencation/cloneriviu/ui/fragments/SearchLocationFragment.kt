package com.authencation.cloneriviu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.authencation.cloneriviu.adapters.Items.ItemOldLocationAdapter
import com.authencation.cloneriviu.adapters.Items.ItemPopularAddressAdapter
import com.authencation.cloneriviu.bussiness_logics.SearchLocation
import com.authencation.cloneriviu.database.CurrentLocationDatabases
import com.authencation.cloneriviu.databinding.FragmentSearchLocationBinding
import com.authencation.cloneriviu.networks.LocalDataSource
import com.authencation.cloneriviu.networks.Repository
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.viewmodels.CurrentLocationViewModel
import com.authencation.cloneriviu.viewmodels.factories.CurrentViewModelsFactory


class SearchLocationFragment : Fragment() {
    private val TAG = "SearchLocationFragment"
    private lateinit var _binding: FragmentSearchLocationBinding
    lateinit var popularAddressAdapter: ItemPopularAddressAdapter
    lateinit var oldLocationAdapter: ItemOldLocationAdapter
    lateinit var currentLocationViewModel: CurrentLocationViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchLocationBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.currentLocation = "Bạn đang ở Hà Nội"
        currentLocationViewModel = activity?.let {
            ViewModelProvider(
                it,
                CurrentViewModelsFactory(
                    Repository(
                        LocalDataSource(
                            CurrentLocationDatabases.getDatabase(
                                requireContext()
                            ).getDao()
                        )
                    )
                )
            )
                .get(CurrentLocationViewModel::class.java)
        } ?: throw Exception("Activity is Null")
        initConvinceVietNam()
        initOldLocationVietNam()
        binding.edtEnterLocation.addTextChangedListener(
            SearchLocation(
                requireActivity().window,
                popularAddressAdapter,
                binding.imageNoDataSearch,
                binding.scrollViewAddress
            )
        )
        super.onViewCreated(view, savedInstanceState)
    }

    fun initConvinceVietNam() {
        popularAddressAdapter = ItemPopularAddressAdapter(
            requireContext(),
            viewLifecycleOwner,
            currentLocationViewModel
        )
        binding.recyclerPopularLocation.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerPopularLocation.adapter = popularAddressAdapter
        popularAddressAdapter.setData(Support.createListConvinceVietNam())
    }

    fun initOldLocationVietNam() {
        oldLocationAdapter = ItemOldLocationAdapter(requireContext())
        binding.recyclerCurrentLocation.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCurrentLocation.adapter = oldLocationAdapter
        currentLocationViewModel.readOldLocations.observe(viewLifecycleOwner, { it ->
            val list = it.map { it.name } as ArrayList
            oldLocationAdapter.setData(list)
        })
    }

}