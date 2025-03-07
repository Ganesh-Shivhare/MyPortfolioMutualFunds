package com.ganesh.hilt.mutualfund.sip.myportfolio.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.viewmodel.MFDataViewModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.viewmodel.PortfolioDataBaseViewModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.PermissionUtils
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.PreferenceClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    internal val mfDataViewModel: MFDataViewModel by viewModels()
    internal val permissionUtils: PermissionUtils by lazy { PermissionUtils(this) }
    internal val portfolioDataBaseViewModel: PortfolioDataBaseViewModel by viewModels()

    internal val preferenceClass: PreferenceClass by lazy {
        PreferenceClass(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}