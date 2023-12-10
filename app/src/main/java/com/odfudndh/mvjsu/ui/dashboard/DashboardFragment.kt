package com.odfudndh.mvjsu.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.odfudndh.mvjsu.R
import com.odfudndh.mvjsu.databinding.FragmentDashboardBinding
import com.odfudndh.mvjsu.entity.BusEvent
import com.odfudndh.mvjsu.entity.NoteEntity
import com.odfudndh.mvjsu.utils.ClickUtils
import com.odfudndh.mvjsu.utils.CustomDialog
import com.odfudndh.mvjsu.utils.RxBus
import com.odfudndh.mvjsu.utils.TimeUtils

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var dashboardViewModel: DashboardViewModel

    private val binding get() = _binding!!

    private lateinit var noteAdapter: NoteAdapter
    private val noteList = ArrayList<NoteEntity>()
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager =   StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.ivClear.setOnClickListener { if(ClickUtils.isClickable( binding.ivClear)){
            dashboardViewModel.clearAllNote()
            initData()
        } }
        noteAdapter = NoteAdapter(
            noteList,
            object : NoteAdapter.OnRestoreClickListener {
                override fun onRestoreClick(note: NoteEntity) {
                    dashboardViewModel.restoreNote(note)
                }
            },
            object : NoteAdapter.OnDeleteClickListener {
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
                if("UpdateHome" == message.message){
                    dashboardViewModel.getAllNoteData()
                }
            }, { error ->
                // 处理接收消息时发生的错误
                error.printStackTrace()
            }, {
                // 处理接收消息完成后的逻辑（可选）
            })

        return root
    }

    private fun initData(){
        dashboardViewModel.getAllNoteData()
        dashboardViewModel.nodeLiveData.observe(viewLifecycleOwner){
            noteList.clear()
            noteList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        }
    }

    private fun showCustomDialog(note: NoteEntity) {
        context?.let {
            val dialog = CustomDialog(
                it,
                "Are you sure to delete the current note?",
                "After deletion, it cannot be restored.",
                object : CustomDialog.OnCancelClickListener{
                    override fun onCancelClick() {
                        // none need to do
                    }
                },
                object: CustomDialog.OnConfirmClickListener{
                    override fun onConfirmClick() {
                        dashboardViewModel.clearNote(note)
                        initData()
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
    private val onRestoreClickListener: OnRestoreClickListener,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_delte_item, parent, false)

        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteEntityList[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.content
        holder.dateTextView.text = currentNote.currentTimeMillis?.toLong()
            ?.let { TimeUtils.convertTimestampToDateTime(it) }

        holder.editButton.setOnClickListener {
            onRestoreClickListener.onRestoreClick(currentNote)
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

    interface OnRestoreClickListener {
        fun onRestoreClick(noteEntity: NoteEntity)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(noteEntity: NoteEntity)
    }
}
