package com.example.coffeshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coffeshop.R
import com.example.coffeshop.databinding.ActivitySignInBinding
import com.example.coffeshop.ui.MainActivity.Companion.UserType

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {

    private val binding by viewBinding(ActivitySignInBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonCustomer.setOnClickListener {
            startActivity(
                MainActivity.intent(this, UserType.CUSTOMER)
            )
        }
        binding.buttonVendor.setOnClickListener {
            startActivity(
                MainActivity.intent(this, UserType.VENDOR)
            )
        }
    }
}