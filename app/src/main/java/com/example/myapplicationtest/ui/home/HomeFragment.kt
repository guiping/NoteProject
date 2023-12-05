package com.example.myapplicationtest.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.PublishActivity
import com.example.myapplicationtest.R
import com.example.myapplicationtest.databinding.FragmentHomeBinding
import com.example.myapplicationtest.entity.BusEvent
import com.example.myapplicationtest.entity.NoteEntity
import com.example.myapplicationtest.utils.ClickUtils
import com.example.myapplicationtest.utils.CustomDialog
import com.example.myapplicationtest.utils.RxBus
import com.example.myapplicationtest.utils.TimeUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var ivTag: ImageView
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = ArrayList<NoteEntity>()
    private var isZhengXu = false
    private val homeViewModel:HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val ivPub: ImageView = binding.ivPublish
        ivTag = binding.ivTag
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        ivPub.setOnClickListener {
            if (ClickUtils.isClickable(it)) {
                Intent(activity,PublishActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        ivTag.setOnClickListener{ it ->
            if (ClickUtils.isClickable(it)) {
                isZhengXu = if(isZhengXu){
                    ivTag.setImageResource(R.mipmap.daoxu)
                    val tempNoteList =  noteList.sortedByDescending { it.currentTimeMillis }
                    noteList.clear()
                    noteList.addAll(tempNoteList)
                    noteAdapter.notifyDataSetChanged()
                    false
                }else{
                   val tempNoteList =  noteList.sortedBy { it.currentTimeMillis }
                    noteList.clear()
                    noteList.addAll(tempNoteList)
                    noteAdapter.notifyDataSetChanged()
                    ivTag.setImageResource(R.mipmap.zhengxu)
                    true
                }
            }
        }

        noteAdapter = NoteAdapter(noteList, object : NoteAdapter.OnEditClickListener {
            override fun onEditClick(note: NoteEntity) {
                // 处理编辑点击事件
            }
        }, object : NoteAdapter.OnDeleteClickListener {
            override fun onDeleteClick(note: NoteEntity) {
                showCustomDialog(note)
            }
        })
        recyclerView.adapter = noteAdapter
        initData()

        RxBus.toObservable(BusEvent::class.java)
            .subscribe({ message ->
                // 处理接收到的消息对象
                println("Received message: ${message.message}")
                if("UpdateDash" == message.message){
                    homeViewModel.getAllNoteData()
                }
            }, { error ->
                // 处理接收消息时发生的错误
                error.printStackTrace()
            }, {
                // 处理接收消息完成后的逻辑（可选）
            })
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun  initData(){
        homeViewModel.getAllNoteData()
        homeViewModel.nodeLiveData.observe(viewLifecycleOwner){
            noteList.clear()
            noteList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun showCustomDialog(note: NoteEntity) {
        context?.let {
            val dialog = CustomDialog(
                it,
                "Are you sure to delete the current note?",
                "After deletion, you can find it in the recycle bin。",
                object : CustomDialog.OnCancelClickListener{
                    override fun onCancelClick() {
                        // none need to do
                    }
                },
                object: CustomDialog.OnConfirmClickListener{
                    override fun onConfirmClick() {
                        homeViewModel.deleteNote(note)
                    }
                }
            )
            dialog.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NoteAdapter(
    private val noteEntityList: List<NoteEntity>,
    private val onEditClickListener: OnEditClickListener,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteEntityList[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.content
        holder.dateTextView.text = currentNote.currentTimeMillis?.toLong()
            ?.let { TimeUtils.convertTimestampToDateTime(it) }

        holder.editButton.setOnClickListener {
            onEditClickListener.onEditClick(currentNote)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener.onDeleteClick(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return noteEntityList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val editButton: TextView = itemView.findViewById(R.id.editButton)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
    }

    interface OnEditClickListener {
        fun onEditClick(noteEntity: NoteEntity)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(noteEntity: NoteEntity)
    }
}

