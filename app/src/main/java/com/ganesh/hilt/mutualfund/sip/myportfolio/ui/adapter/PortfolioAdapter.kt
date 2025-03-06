package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.hilt.mutualfund.sip.myportfolio.databinding.ItemPortfolioBinding
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioEntity
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.formatDate
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.formatIndianCurrency

class PortfolioAdapter :
    RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

    private val portfolioEntities = mutableListOf<PortfolioEntity>()

    fun submitList(newList: List<PortfolioEntity>) {
        val diffCallback = UserDiffCallback(portfolioEntities, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        portfolioEntities.clear()
        portfolioEntities.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(portfolioEntity: PortfolioEntity) {
            binding.tvSchemeName.text = portfolioEntity.schemeName
            binding.tvJoinDate.text = portfolioEntity.date.formatDate()
            binding.tvInvestment.text = portfolioEntity.totalInvestment.formatIndianCurrency()
            binding.tvTotalReturn.text = portfolioEntity.totalReturn.formatIndianCurrency()
            binding.tvInterestRate.text = portfolioEntity.totalReturn.formatIndianCurrency()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portfolioEntities[position])
    }

    override fun getItemCount(): Int = portfolioEntities.size

    inner class UserDiffCallback(
        private val oldList: List<PortfolioEntity>,
        private val newList: List<PortfolioEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
