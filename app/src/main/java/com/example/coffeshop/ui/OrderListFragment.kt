package com.example.coffeshop.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coffeshop.R
import com.example.coffeshop.data.api.Retrofit
import com.example.coffeshop.data.model.Order
import com.example.coffeshop.databinding.FragmentOrderListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private val formattingViews = mutableListOf<View>()
    private val deliveringViews = mutableListOf<View>()
    private val deliveredViews = mutableListOf<View>()

    private val binding by viewBinding(FragmentOrderListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrders()
    }

    private fun getOrders() {
        Retrofit.getApi().getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>, response: Response<List<Order>>
            ) {
                if (response.isSuccessful) {
                    val weatherResult = response.body()
                    if (weatherResult != null) {
                        setOrders(weatherResult)
                    }
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {}
        })
    }

    private fun setOrders(orders: List<Order>) {
        orders.forEach { order ->

            val root: ViewGroup =
                when (order.status) {
                    "ON_WAY" -> binding.llDelivering
                    "DELIVERED" -> binding.llDelivered
                    else -> binding.llPackage
                }

            val layout =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_order, root, false)
            val image = layout.findViewById<ImageView>(R.id.image)
            val title = layout.findViewById<TextView>(R.id.textTitle)
            val description = layout.findViewById<TextView>(R.id.textDescription)
            val price = layout.findViewById<TextView>(R.id.textPrice)

            val product_id = order.order_item.product.id

            val imageResourse = with(product_id) {
                when {
                    contains("1") -> R.drawable.coffee1
                    contains("2") -> R.drawable.coffee2
                    contains("3") -> R.drawable.coffee3
                    contains("4") -> R.drawable.coffee4
                    contains("5") -> R.drawable.coffee5
                    else -> R.drawable.coffee5
                }
            }
            image.setImageResource(imageResourse)

            title.text = order.order_item.product.title
            description.text = order.address
            price.text = "$ " + order.total_price

            if (MainActivity.userType == MainActivity.Companion.UserType.VENDOR) {
                layout.setOnClickListener {
                    if (!deliveredViews.contains(it))
                        showAlertDialog(layout)
                }
            }

            when (order.status) {
                "FORMATTING" -> {
                    formattingViews.add(layout)
                }
                "ON_WAY" -> {
                    deliveringViews.add(layout)
                }
                "DELIVERED" -> {
                    deliveredViews.add(layout)
                }
                else -> {
                    formattingViews.add(layout)
                }
            }
        }
        updateLayouts()
    }

    private fun updateLayouts() {
        binding.llPackage.removeAllViews()
        binding.llDelivering.removeAllViews()
        binding.llDelivered.removeAllViews()
        formattingViews.forEach { binding.llPackage.addView(it) }
        deliveringViews.forEach { binding.llDelivering.addView(it) }
        deliveredViews.forEach { binding.llDelivered.addView(it) }
    }

    private fun showAlertDialog(layout: View) {
        AlertDialog.Builder(requireContext())
            .setTitle("Status Change")
            .setMessage("Change order status?")
            .setPositiveButton(
                "Yes"
            ) { dialog, arg1 ->
                if (formattingViews.contains(layout)) {
                    formattingViews.remove(layout)
                    deliveringViews.add(layout)
                } else {
                    deliveringViews.remove(layout)
                    deliveredViews.add(layout)
                }
                updateLayouts()
                dialog.dismiss()
            }
            .setNegativeButton(
                "No"
            ) { dialog, arg1 -> dialog.dismiss() }
            .show()
    }

}

