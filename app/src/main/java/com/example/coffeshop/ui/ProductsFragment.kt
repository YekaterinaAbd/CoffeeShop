package com.example.coffeshop.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coffeshop.R
import com.example.coffeshop.data.api.Retrofit
import com.example.coffeshop.data.model.Order
import com.example.coffeshop.data.model.OrderItem
import com.example.coffeshop.data.model.Product
import com.example.coffeshop.databinding.FragmentProductsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val adapter: ProductsAdapter by lazy {
        ProductsAdapter(::createOrder)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        getProducts()
    }

    private fun bindViews(view: View) = with(binding) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        buttonOrders.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.container, OrderListFragment())
                .addToBackStack(null)
                .hide(this@ProductsFragment)
                .commit()
        }
    }

    private fun getProducts() {
        Retrofit.getApi().getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>, response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    val weatherResult = response.body()
                    if (weatherResult != null) {
                        setProducts(weatherResult)
                    }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {}
        })
    }

    private fun setProducts(products: List<Product>) {
        adapter.addItems(products)
    }

    private fun createOrder(product: Product) {
        val orderItem = OrderItem(
            product.id,
            5,
            product.price * 5.0,
            product.id,
            product
        )
        val order = Order(
            customer = "customer",
            created_at = "created at",
            total_price = orderItem.total_price,
            status = "FORMATTING",
            order_item = orderItem,
            id = orderItem.order_id,
            address = "Almaty, Kazakhstan, 050049"
        )
        Retrofit.getApi().createOrder(order).enqueue(object : Callback<Order> {
            override fun onResponse(
                call: Call<Order>, response: Response<Order>
            ) {
                if (response.isSuccessful) {
                    val weatherResult = response.body()
                    if (weatherResult != null) {

                    } else {

                    }
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                t
            }
        })
        showAlertDialog()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Order added")
            .setMessage("The order added successfully!")
            .setPositiveButton(
                "Ok"
            ) { dialog, arg1 -> dialog.dismiss() }

            .show()
    }

}
