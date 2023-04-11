package com.example.room_sql


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(private val wordDao: WordDao):ViewModel() {

    val allWordsFromDataBase = this.wordDao.getWords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    //Очищаем всю таблицу
    fun onClearButton(context: Context){
        Toast.makeText(context,"База данных очищенна!",Toast.LENGTH_SHORT).show()
        viewModelScope.launch { wordDao.clearDataBase() }
    }

    //Метод принемает пользовательское слово из editText и проверяет его на соответсвие правилам
    //Если соответствует -> прокидываем это слово в метод  workWithDataBase()
    //Если не соответсвует -> Выводим тост
    fun userWordChecker(userWord:String,context: Context){
        val forbiddenSymbols = listOf(" ","&","?","!",".",",","@","#","$","%","+","=","(",")","*","\'","\"",":",";","_","/","<",">","1","2","3","4","5","6","7","8","9","0")
            .toSet()
            .toList()
        val userWordIsContainForbiddenSymbols = userWord.findAnyOf(forbiddenSymbols, startIndex = 0,ignoreCase = true) == null

        if (userWord.isNotBlank() && userWordIsContainForbiddenSymbols) workWithDataBase(userWord,context)
        else {
            Toast.makeText(context,"Введено недопустимое значение!",Toast.LENGTH_SHORT).show()
        }
    }

    //Данный метод принемает пользовательское слово которое прошло проверку на допустимость
    //и добавлет это слово в словарь если его там еще нет. Если слово уже было добавленно в словарь
    //ранее -> ищим это слово в БД и увеличиваем его счетчик повторений на 1
    private fun workWithDataBase(userWord:String, context: Context){
        viewModelScope.launch {
            //Проверяем слово на наличие/отсутсвие в словаре
            // наличие -> String / отсутсвие -> null
            val x = wordDao.tryToGetUserWord(userWord)

            //Добавляем слово в словарь
            if (x == null){
                wordDao.insertWord(
                    Word(
                        //id = 1,
                        userWord = userWord,
                        wordCounter = 0
                    )
                )
            }
            //Увеличиваем счетчик повторяемого слова
            else{
                Toast.makeText(context,"Данное слово уже присутствует в словаре!",Toast.LENGTH_SHORT).show()
                wordDao.increaseWordCounter(userWord)
            }
        }
    }

}