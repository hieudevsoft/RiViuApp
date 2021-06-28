package com.authencation.cloneriviu.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private lateinit var _binding : FragmentAddBinding
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRatingBar()
        initContainerBox()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initContainerBox() {
        binding.edtContent.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               when(s.toString().length){
                   0->binding.tvCountCharacters.text ="Nội dung không quá 200000 kí tự"
                   else -> binding.tvCountCharacters.text ="Bạn đã nhập ${s?.length} kí tự"
               }
                 requireActivity()
                     requireActivity().window.decorView.apply {
                                 systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                         or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                         or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                         or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                         or View.SYSTEM_UI_FLAG_FULLSCREEN
                                         or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                 }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun initRatingBar(){
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            when(rating){
                1.0f-> binding.statusRating.text = "Tệ"
                2.0f-> binding.statusRating.text = "Cần cải thiện"
                3.0f-> binding.statusRating.text = "Bình thường"
                4.0f-> binding.statusRating.text = "Tốt"
                5.0f-> binding.statusRating.text = "Suất sắc"
            }
        }
    }
}
