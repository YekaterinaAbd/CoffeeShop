package com.example.coffeshop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.R
import com.example.coffeshop.data.model.Product

class ProductsAdapter(
    private val itemClickListener: (item: Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder)
            holder.bind(movies[position])
    }

    fun clearAll() {
        movies.clear()
        notifyDataSetChanged()
    }

    fun addItems(moviesList: List<Product>) {
        movies = moviesList as MutableList<Product>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val image = layout.findViewById<ImageView>(R.id.image)
        val title = layout.findViewById<TextView>(R.id.textTitle)
        val description = layout.findViewById<TextView>(R.id.textDescription)
        val price = layout.findViewById<TextView>(R.id.textPrice)
        val button = layout.findViewById<ImageButton>(R.id.buttonAdd)

        fun bind(product: Product?) {
            if (product == null) return

            title.text = product.title
            description.text = product.description
            price.text = "$ " + product.price

            val pos = adapterPosition % movies.size
            val imageResourse = when (pos) {
                0 -> R.drawable.coffee1
                1 -> R.drawable.coffee2
                2 -> R.drawable.coffee3
                3 -> R.drawable.coffee4
                4 -> R.drawable.coffee5
                else -> R.drawable.coffee5
            }
            image.setImageResource(imageResourse)

            button.setOnClickListener {
                itemClickListener.invoke(product)
            }
        }
    }
}
