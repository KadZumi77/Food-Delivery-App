package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Adapters.CartAdapter
import com.example.fooddelivery.Models.PopularModel
import com.example.fooddelivery.Models.SharedModel
import com.example.fooddelivery.databinding.FragmentCartBinding
import kotlin.collections.ArrayList


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
//    private lateinit var list: ArrayList<PopularModel>
    private lateinit var sharedModel: SharedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        sharedModel = ViewModelProvider(requireActivity()).get(SharedModel::class.java)

        adapter = CartAdapter(requireContext(), sharedModel.cartItem.value ?: ArrayList())

        binding.cartRv.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRv.adapter = adapter

        binding.proceedBtn.setOnClickListener {
            val totalPrice = sharedModel.cartItem.value?.let { it1 -> sumPrice(it1) }
            if(totalPrice == 0){
                Toast.makeText(requireContext(), "List is Empty", Toast.LENGTH_SHORT).show()
            }
            val totalPrices = totalPrice.toString()
            val intent = Intent(requireContext(), DetailsViewActivity::class.java)
            intent.putExtra("totalPrice", totalPrices)
            startActivity(intent)
        }

        return binding.root
    }



    fun sumPrice(itemPrices: List<PopularModel>): Int{

        var totalPrice = 0

        itemPrices.forEach{itemPrice ->
            val price = itemPrice.getFoodPriceConstant() * itemPrice.getFoodCount()
            totalPrice += price
        }
        return totalPrice
    }
}