package com.authencation.cloneriviu.support

import android.os.Build
import android.view.*
import android.widget.TextView
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.model.ItemOptionsOne
import com.authencation.cloneriviu.model.ItemOptionsTwo
import com.authencation.cloneriviu.model.ItemSlider
import com.google.android.material.bottomnavigation.BottomNavigationView

object Support {
    fun setFlagFullScreen(window: Window){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.R)
         window.decorView.apply {
                         systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                 or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                 or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                 or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                 or View.SYSTEM_UI_FLAG_FULLSCREEN
                                 or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
         }
        else{
            val windowController = window.insetsController
            windowController?.hide(WindowInsets.Type.statusBars())
            windowController?.hide(WindowInsets.Type.navigationBars())
            windowController?.hide(WindowInsets.Type.captionBar())
            windowController?.hide(WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE)
        }
    }
    fun createListSlider():List<ItemSlider>{
        val item1 = ItemSlider("Trending","Nơi bắt nguồn cho những địa điểm \n&\nxu hướng hót nhất", R.drawable.image_findfoodlove)
        val item2 = ItemSlider("Social","Cộng đồng kết nối\nngười yêu thích ăn uống & du lịch",R.drawable.image_livetracking)
        val item3 = ItemSlider("Riviu","Chất lượng - Khách quan - Chân\nthật",R.drawable.image_fastdelivery)
        return listOf(item1,item2,item3)
    }
    fun createListOptionsOne():List<ItemOptionsOne>{
        val item1 = ItemOptionsOne("https://image.freepik.com/free-photo/pots-vegetables-harvest_23-2147694057.jpg","Tổng hợp các địa điểm ăn uống, vui chơi Hà Nội", "1000 bài viết")
        val item2 = ItemOptionsOne("https://image.freepik.com/free-photo/top-view-bowls-with-coffee-beans-powder_23-2148937321.jpg","Coffee HN", "743 bài viết")
        val item3 = ItemOptionsOne("https://image.freepik.com/free-photo/iced-green-tea-milkshake_1339-5939.jpg","Trà Sữa", "169 bài viết")
        val item4 = ItemOptionsOne("https://image.freepik.com/free-photo/fried-chicken-wings-with-french-fries-tomato_74190-6308.jpg","Đồ ăn nhanh", "79 bài viết")
        val item5 = ItemOptionsOne("https://image.freepik.com/free-photo/noodles-chinese-pork-stewed-pork-stew-beautiful-side-dishes-thai-food_1150-24226.jpg","Phở Hà Nội", "69 bài viết")
        return listOf(item1,item2,item3,item4,item5)
    }

    fun createListOptionsTwo():List<ItemOptionsTwo>{
        val item1 = ItemOptionsTwo("https://image.freepik.com/free-photo/homemade-newyork-cheesecake-with-frozen-berries-mint-healthy-organic-dessert-top-view_114579-9852.jpg","Cách làm bánh ngọt", "93 bài viết")
        val item2 = ItemOptionsTwo("https://image.freepik.com/free-psd/summer-ice-cream-composition-with-copyspace_23-2148195775.jpg","Cách làm kem", "39 bài viết")
        val item3 = ItemOptionsTwo("https://image.freepik.com/free-photo/caramel-custard-pudding_1203-3459.jpg","Cách làm bánh flan", "10 bài viết")
        val item4 = ItemOptionsTwo("https://image.freepik.com/free-photo/almond-milk-bottle-with-almond-dark-background_1150-45117.jpg","Cách làm đồ uống", "11 bài viết")
        val item5 = ItemOptionsTwo("https://image.freepik.com/free-photo/man-cooking-fresh-vegetables-pan_1220-3199.jpg","Cách làm món chay", "13 bài viết")
        return listOf(item1,item2,item3,item4,item5)
    }
    fun createListConvinceVietNam():ArrayList<String>{
        val list = listOf("Hồ Chí Minh","Hà Nội","Đà Lạt","Vũng Tàu","Biên Hòa","Huế","Bảo Lộc","Buôn Ma Thuột","Cam Ranh","Cần Thơ",
        "Cao Lãnh","Châu Đốc","Đà Nẵng", "Dĩ An","Hà Tiên", "Hải Phòng","Long Khánh","Mỹ Tho","Nha Trang","Phan Thiết","Pleiku",
            "Quy Nhơn","Rạch Giá","Sa Đéc"
            )
        return ArrayList(list)
    }
    fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }
    fun setPresentTextView(textView:TextView,scale:Float,textColor: Int){
        textView.let {
            it.scaleX = scale
            it.scaleY = scale
            it.setTextColor(textColor)
        }
    }

}
