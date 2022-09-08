package ch.comparis.challenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.comparis.challenge.R
import ch.comparis.challenge.databinding.ItemCarBinding
import ch.comparis.challenge.model.Car

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {
    private var cars: MutableList<Car> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    class CarViewHolder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        with(holder) {
            binding.isFavoriteButton.setImageResource(if (car.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
            binding.carMake.text = car.make.name
            binding.carMileage.text = car.mileage.toString()
            binding.isFavoriteButton.setOnClickListener {
                car.isFavorite = !car.isFavorite
            }
        }
    }

    override fun getItemCount(): Int = cars.size

    fun updateCars(it: MutableList<Car>?) {
        it?.let {
            cars = it
            notifyDataSetChanged()
        }
    }
}