package com.example.myapplicationtest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.PublishActivity
import com.example.myapplicationtest.R
import com.example.myapplicationtest.databinding.FragmentHomeBinding
import com.example.myapplicationtest.entity.Note
import com.example.myapplicationtest.utils.ClickUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var ivTag: ImageView
    private lateinit var noteAdapter: NoteAdapter

    private var isZhengXu = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val ivPub: ImageView = binding.ivPublish
        ivTag = binding.ivTag
        recyclerView = binding.recyclerView
        ivPub.setOnClickListener {
            if (ClickUtils.isClickable(it)) {
                Intent(activity,PublishActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        ivTag.setOnClickListener{
            if (ClickUtils.isClickable(it)) {
               if(isZhengXu){
                   ivTag.setImageResource(R.mipmap.daoxu)
                   isZhengXu = false
               }else{
                   ivTag.setImageResource(R.mipmap.zhengxu)
                   isZhengXu = true
               }
            }
        }
        initData()
        return root
    }

    fun  initData(){
        val nodeList = ArrayList<Note>()
        noteAdapter = NoteAdapter(nodeList)
        recyclerView.adapter = noteAdapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NoteAdapter(
    private val noteList: List<Note>,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.content
        holder.dateTextView.text = currentNote.createDate

        holder.editButton.setOnClickListener {
           //
        }

        holder.deleteButton.setOnClickListener {
           //
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val editButton: TextView = itemView.findViewById(R.id.editButton)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
    }

    interface OnEditClickListener {
        fun onEditClick(note: Note)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(note: Note)
    }
}

