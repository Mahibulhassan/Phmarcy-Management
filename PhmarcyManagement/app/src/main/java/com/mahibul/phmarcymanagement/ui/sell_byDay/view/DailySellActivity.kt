package com.mahibul.phmarcymanagement.ui.sell_byDay.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahibul.phmarcymanagement.R
import com.mahibul.phmarcymanagement.core.BaseActivity
import com.mahibul.phmarcymanagement.data.SharePreference.AppPreference
import com.mahibul.phmarcymanagement.data.local.DataChangeLIstner
import com.mahibul.phmarcymanagement.data.reposotory.sell_byDay.DailySell
import com.mahibul.phmarcymanagement.data.reposotory.sell_byDay.DailySellModelImp
import com.mahibul.phmarcymanagement.ui.sell_byDay.viewModel.DailySellViewFactory
import com.mahibul.phmarcymanagement.ui.sell_byDay.viewModel.DailySellViewModel
import kotlinx.android.synthetic.main.activity_bye.*
import kotlinx.android.synthetic.main.activity_daily_sell.*
import kotlinx.android.synthetic.main.toolbar.*

class DailySellActivity : BaseActivity(),DataChangeLIstner{
    private lateinit var appPreferance: AppPreference
    private val model by lazy { DailySellModelImp(applicationContext) }
    private val viewModel by lazy {
        val factory = DailySellViewFactory(model)
        ViewModelProvider(this,factory).get(DailySellViewModel::class.java)
    }
    private val sellList by lazy { mutableListOf<DailySell>() }

    private val dailyselladapter by lazy {
        DailySellAdapter(sellList,object : DailySellAdapter.dailysellListClickListener{
            override fun onEditButtonClicked(id: Long, sell_item_name: String) {
                TODO("Not yet implemented")
            }

            override fun onDeleteButtonClicked(id: Long) {
                showDeletionDialouge(id)
            }

        })
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_daily_sell
    }

    override fun setToolbar(): Toolbar {
        toolbar.title = "Today Sell List"
        return toolbar
    }

    override val isHomeUpButtonEnable: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        viewModel.getSellList()
        viewModel.dailysellListLiveData.observe(this,{
            dailyselladapter.replaceData(it)
        })
        viewModel.dailySellListFailourLiveData.observe(this,{
            ShowToast(it)
        })
        viewModel.dailySellDeletionSuccessLiveData.observe(this,{
            viewModel.getSellList()
        })
        viewModel.dailySellDeletionFailedLiveData.observe(this,{
            ShowToast(it)
        })
        sell_btn_add.setOnClickListener {

        }
    }

    override fun onDataChanged() {
        viewModel.getSellList()
    }

    override fun onDataSetChangeError(error: String) {
        ShowToast(error)
    }
    private fun initRecyclerView() {
        dailysell_recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        dailysell_recyclerview.adapter= dailyselladapter
    }

    private fun showDeletionDialouge(id: Long) {

        var dialog: AlertDialog? = null

        dialog = MaterialAlertDialogBuilder(this)
            .setMessage("Are You Sure to Delete ?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteItem(id)
            }
            .setNegativeButton("No") { _, _ ->
                dialog?.dismiss()
            }
            .show()
    }
}