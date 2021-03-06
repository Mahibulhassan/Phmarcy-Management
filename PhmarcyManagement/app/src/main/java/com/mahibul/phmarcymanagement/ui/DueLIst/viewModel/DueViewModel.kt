package com.mahibul.phmarcymanagement.ui.DueLIst.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahibul.phmarcymanagement.core.DataFetchCallback
import com.mahibul.phmarcymanagement.data.reposotory.due_coustomer.Customer
import com.mahibul.phmarcymanagement.data.reposotory.due_coustomer.DueListModel

class DueViewModel (private val model : DueListModel): ViewModel() {

    val customerListLiveData = MutableLiveData<MutableList<Customer>>()
    val customerListFailourLiveData = MutableLiveData<String>()
    val customerDeletionSuccessLiveData = MutableLiveData<Int>()
    val customerDeletionFailedLiveData = MutableLiveData<String>()

    fun getCustomerList(){
        model.getCustomerList(object : DataFetchCallback<MutableList<Customer>>{
            override fun onSuccess(data: MutableList<Customer>) {
                customerListLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                customerListFailourLiveData.postValue(throwable.localizedMessage)
            }
        })
    }

    fun deleteCustomer(id : String){
        model.deleteDueCustomer(id,object : DataFetchCallback<Int>{
            override fun onSuccess(data: Int) {
                customerDeletionSuccessLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                customerDeletionFailedLiveData.postValue(throwable.localizedMessage)
            }
        })
    }
}