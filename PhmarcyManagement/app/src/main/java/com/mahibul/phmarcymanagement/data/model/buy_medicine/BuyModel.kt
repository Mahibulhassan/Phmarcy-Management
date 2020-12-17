package com.mahibul.phmarcymanagement.data.model.buy_medicine

import com.mahibul.phmarcymanagement.core.DataFetchCallback

interface BuyModel {
    fun insertMedicine(buyMedicineData: BuyMedicineData, callback: DataFetchCallback<BuyMedicineData>)
    fun getMedicineList(callback: DataFetchCallback<MutableList<BuyMedicineData>>)
}