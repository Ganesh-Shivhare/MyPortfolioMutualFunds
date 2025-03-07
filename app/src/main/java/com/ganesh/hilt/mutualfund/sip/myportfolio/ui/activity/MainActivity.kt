package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.ganesh.hilt.mutualfund.sip.myportfolio.databinding.ActivityMainBinding
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.BaseActivity
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.adapter.PortfolioAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter: PortfolioAdapter by lazy { PortfolioAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        binding.recyclerView.adapter = this@MainActivity.adapter

        binding.ivAddNewData.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNewSchemeActivity::class.java))
        }
    }

    private fun initObserver() {
        portfolioDataBaseViewModel.notifications.observe(this) { portfolioEntityList ->
            binding.txtNoDataFound.isVisible = portfolioEntityList.isEmpty()

            portfolioEntityList.forEachIndexed { index, portfolioEntity ->
                Log.d("TAG_scheme_details", "initObserver:schemeCode " + portfolioEntity.schemeCode)
                mfDataViewModel.getSchemeResults(portfolioEntity) { it ->
                    it.onSuccess {
                        adapter.updateIndex(
                            index,
                            mfDataViewModel.getMonthlyNAVSum(it.data!!, portfolioEntity)
                        )
                    }
                }
            }

            adapter.submitList(portfolioEntityList)
        }
    }
}