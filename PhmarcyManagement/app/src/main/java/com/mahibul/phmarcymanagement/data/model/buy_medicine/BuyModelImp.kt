package com.mahibul.phmarcymanagement.data.model.buy_medicine

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mahibul.phmarcymanagement.constants.COLUMN_medicine_NAME
import com.mahibul.phmarcymanagement.constants.COLUMN_medicine_price
import com.mahibul.phmarcymanagement.constants.COLUMN_medicine_unit
import com.mahibul.phmarcymanagement.constants.TABLE_BUY_MEDICINE
import com.mahibul.phmarcymanagement.core.DataFetchCallback
import com.mahibul.phmarcymanagement.data.local.DbHelper
import java.lang.Exception

class BuyModelImp(private val context: Context) : BuyModel {

    override fun insertMedicine(buyMedicineData: BuyMedicineData, callback: DataFetchCallback<BuyMedicineData>) {
        val dbHelper = DbHelper.getInstance(context)
        val database = dbHelper.writableDatabase

        val contentvalues = ContentValues()
        contentvalues.put(COLUMN_medicine_NAME, buyMedicineData.name)
        contentvalues.put(COLUMN_medicine_price, buyMedicineData.price)
        contentvalues.put(COLUMN_medicine_unit, buyMedicineData.unit)
        try {
            val id = database.insertOrThrow(TABLE_BUY_MEDICINE, null, contentvalues)
            if (id > 0) {
                callback.onSuccess(buyMedicineData)
            } else
                callback.onError(Throwable("Insertion failed for unknown reason"))
        } catch (e: Exception) {
            callback.onError(e)
        } finally {
            database.close()
        }
    }

    override fun getMedicineList(callback: DataFetchCallback<MutableList<BuyMedicineData>>) {
        val dbHelper = DbHelper.getInstance(context)
        val database = dbHelper.readableDatabase

        var cursor: Cursor? = null
        try{
            cursor = database.query(TABLE_BUY_MEDICINE,null,null,null,null,null,null,null)

            if(cursor.moveToFirst()==true){
                val medicineList = mutableListOf<BuyMedicineData>()

                do{
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_medicine_NAME))
                    val price = cursor.getInt(cursor.getColumnIndex(COLUMN_medicine_price))
                    val unite = cursor.getInt(cursor.getColumnIndex(COLUMN_medicine_unit))

                    medicineList.add(BuyMedicineData(name,price,unite))
                }while (cursor.moveToNext())

                callback.onSuccess(medicineList)
            }
        }catch (e: Exception){
            callback.onError(e)
        }finally {
            if (cursor != null) {
                cursor.close()
            }
            database.close()
        }
    }
}