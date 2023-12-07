package com.odfudndh.mvjsu.ui.home

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

class HomeViewModel : ViewModel() {


    val nodeLiveData = MutableLiveData<List<NoteEntity>>()
    val noteDao: NoteDao by lazy {
        DbDatabase.buildDatabase(NoteApplication.appContext).noteDao()
    }

    //查询所有数据
    fun getAllNoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempNotaDataList = noteDao.getAllNoteData()
            nodeLiveData.postValue(tempNotaDataList)
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            noteEntity.currentTimeMillis?.let {
                val temp = noteDao.deletedNoteData(it.toLong())

                if(temp != 0){
                    getAllNoteData()
                    RxBus.post(BusEvent("UpdateDash"))
                }
            }
        }
    }

}