package com.odfudndh.mvjsu.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odfudndh.mvjsu.NoteApplication
import com.odfudndh.mvjsu.db.DbDatabase
import com.odfudndh.mvjsu.db.imp.NoteDao
import com.odfudndh.mvjsu.entity.BusEvent
import com.odfudndh.mvjsu.entity.NoteEntity
import com.odfudndh.mvjsu.utils.RxBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    val nodeLiveData = MutableLiveData<List<NoteEntity>>()
    val noteDao : NoteDao by lazy {
        DbDatabase.buildDatabase(NoteApplication.appContext).noteDao()
    }
    //查询所有数据
    fun getAllNoteData(){
        viewModelScope.launch(Dispatchers.IO){
            val tempNotaDataList = noteDao.getDeletedNoteData()
            Log.e("pLog","111---- ${tempNotaDataList.size}")
            nodeLiveData.postValue(tempNotaDataList)
        }
    }

    fun clearNote(noteEntity: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
           val temp =   noteDao.clearNoteData(noteEntity.currentTimeMillis)
            if(temp != 0){
                getAllNoteData()
            }
        }
    }

    fun restoreNote(noteEntity: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
           val temp =  noteEntity.currentTimeMillis?.toLong()?.let { noteDao.restoredNoteData(it) }
            if(temp != 0){
                getAllNoteData()
                RxBus.post(BusEvent("UpdateHome"))
            }
        }
    }

    fun clearAllNote(){
        viewModelScope.launch(Dispatchers.IO) {
            val temp = noteDao.clearAllNoteData()
            if(temp != 0){
                getAllNoteData()
            }
        }
    }
}

