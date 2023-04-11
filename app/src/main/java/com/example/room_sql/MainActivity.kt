package com.example.room_sql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.room_sql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Инициализируем ViewModel и передаем в нее аргумент через фабрику
    private val viewModel:MainViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).dataBase.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.buttonAdd.setOnClickListener {
            val editTextValue = binding.editText.text.toString() //Получаем слово из EditText
            viewModel.userWordChecker(editTextValue,this) //Отправляем его на проверку в ViewModel
        }

        binding.buttonClear.setOnClickListener { viewModel.onClearButton(this) }

        lifecycleScope.launchWhenStarted {
            viewModel.allWordsFromDataBase.collect{ words->
                binding.textView.text = words.joinToString(separator = "\n\n")
            }
        }

    }
}