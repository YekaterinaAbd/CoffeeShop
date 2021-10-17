package com.example.coffeshop.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.coffeshop.R
import java.io.Serializable

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val ARG_USER_TYPE = "arg_user_type"

        fun intent(context: Context, userType: UserType) =
            Intent(context, MainActivity::class.java).apply {
                putExtras(bundleOf(ARG_USER_TYPE to userType))
            }

        enum class UserType : Serializable {
            VENDOR,
            CUSTOMER
        }
    }

    var userType: UserType = UserType.CUSTOMER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userType = intent.extras?.getSerializable(ARG_USER_TYPE) as UserType
        val fragment = when (userType) {
            UserType.CUSTOMER -> ProductsFragment()
            UserType.VENDOR -> OrderListFragment()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }
}