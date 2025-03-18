package com.tsci.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsci.home.databinding.ItemPlantQuestionBinding
import com.tsci.home.model.QuestionUiModel

class PlantQuestionsAdapter(
    private val questions: List<QuestionUiModel>
) : RecyclerView.Adapter<PlantQuestionsAdapter.QuestionsViewHolder>() {

    inner class QuestionsViewHolder(
        private val binding: ItemPlantQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuestionUiModel) {
            binding.textViewPlantQuestionItemQuestion.text = item.question

            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.imageViewPlantQuestionItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        return QuestionsViewHolder(
            ItemPlantQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val item = questions[position]
        holder.bind(item)
    }
}