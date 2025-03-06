package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.activity

import android.os.Bundle
import android.os.Parcel
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.ganesh.hilt.mutualfund.sip.myportfolio.R
import com.ganesh.hilt.mutualfund.sip.myportfolio.databinding.ActivityAddNewSchemeBinding
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioEntity
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeNameModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.BaseActivity
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.adapter.SchemeAdapter
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.formatDate
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.hideKeyboard
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddNewSchemeActivity : BaseActivity() {
    private val binding by lazy { ActivityAddNewSchemeBinding.inflate(layoutInflater) }
    private var selectedDate: Long = Calendar.getInstance().timeInMillis
    private var selectedScheme: MFSchemeNameModel = MFSchemeNameModel()
    private lateinit var adapter: SchemeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        with(binding) {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            tvDate.setOnClickListener {
                showMaterialDatePicker { _, timeStamp ->
                    selectedDate = timeStamp
                    tvDate.text = selectedDate.formatDate()
                }
            }

            tvInvest.setOnClickListener {
                val investmentAmount: Long = if (etInvestment.text.isEmpty()) {
                    0
                } else {
                    etInvestment.text.toString().toLong()
                }

                if (selectedScheme.schemeName.isNullOrEmpty()) {
                    Toast.makeText(
                        this@AddNewSchemeActivity, "Please select a scheme", Toast.LENGTH_SHORT
                    ).show()
                } else if (tvDate.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@AddNewSchemeActivity, "Please select a date", Toast.LENGTH_SHORT
                    ).show()
                } else if (investmentAmount < 100) {
                    Toast.makeText(
                        this@AddNewSchemeActivity,
                        "Minimum investment amount is 100",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val portfolioEntity = PortfolioEntity()
                    portfolioEntity.schemeName = selectedScheme.schemeName ?: ""
                    portfolioEntity.date = selectedDate
                    portfolioEntity.firstInvestment = investmentAmount

                    portfolioDataBaseViewModel.insertPortfolio(portfolioEntity)
                    finish()
                }
            }

            // Initialize ArrayAdapter
            adapter = SchemeAdapter(
                this@AddNewSchemeActivity, emptyList()
            )

            // Set adapter to AutoCompleteTextView
            searchBox.setAdapter(adapter)

            // Show dropdown when typing
            searchBox.threshold = 1 // Show results after 1 character

            searchBox.setOnItemClickListener { _, _, position, _ ->
                selectedScheme = adapter.getItem(position)!!
                searchBox.setText(selectedScheme.schemeName) // Set selected item in text box
                searchBox.hideKeyboard()
                searchBox.dismissDropDown() // Hide dropdown after selection
            }

            // Search filter logic
            searchBox.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        mfDataViewModel.getMFNameResults(s.toString()) // Filter results when text changes
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
    }

    private fun initObserver() {
        mfDataViewModel.searchResult.observe(this) { result ->
            result.onSuccess { schemeList ->
                if (schemeList.isEmpty() && binding.searchBox.text.isNotEmpty()) return@onSuccess
                if (schemeList.size == 1 && schemeList[0].schemeName == selectedScheme.schemeName && schemeList[0].schemeCode == selectedScheme.schemeCode) {
                    binding.searchBox.dismissDropDown()
                    return@onSuccess
                }

                adapter.updateList(schemeList) // Update dropdown items
                if (schemeList.isNotEmpty()) binding.searchBox.showDropDown() // Show updated dropdown
                else binding.searchBox.dismissDropDown() // Hide dropdown if no results()
            }.onFailure { }
        }
    }

    private fun showMaterialDatePicker(onDateSelected: (String, Long) -> Unit) {
        // Restrict the selection range
        val constraints =
            CalendarConstraints.Builder().setValidator(object : CalendarConstraints.DateValidator {
                override fun isValid(date: Long): Boolean {
                    val calendar = Calendar.getInstance().apply { timeInMillis = date }
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

                    // Allow only 10th to 20th and exclude Saturdays & Sundays
                    return dayOfMonth in 10..20 && dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY
                }

                override fun describeContents(): Int = 0
                override fun writeToParcel(dest: Parcel, flags: Int) {}
            }).build()

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date")
            .setSelection(selectedDate)  // Show last selected date
            .setCalendarConstraints(constraints)
            .setTheme(R.style.CustomDatePicker)  // Apply custom theme
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selection))
            onDateSelected(selectedDate, selection)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }
}