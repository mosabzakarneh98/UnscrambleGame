package com.example.android.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {

    private val _score=MutableLiveData(0)
    val score:LiveData<Int>
        get() =_score
    private val _currentScrambledWord=MutableLiveData("")
    val currentScrambledWord:LiveData<String>
        get() = _currentScrambledWord
    private val _wordNumber=MutableLiveData(0)
    val wordNumber:LiveData<Int>
        get() = _wordNumber

    private lateinit var currentWord:String
    var count=0
    private val listOfWord= mutableListOf<String>()

    init {
        createRandomWordsList()
        nextWord()
    }

    fun createRandomWordsList() {
        for (i in 1..MAX_NO_OF_WORDS){
         getRandomWord()
        }
    }

    fun getRandomWord() {
        val str=allWordsList.random()
        if(str !in listOfWord)listOfWord.add(str)
        else getRandomWord()
    }

    fun nextWord() {
        currentWord=listOfWord[count++]
        _wordNumber.value=count
        _currentScrambledWord.value=shuffleCharactersOfAWord(currentWord)
    }

    fun increaseScore() {
        _score.value=score.value?.plus(SCORE_INCREASE)
    }

    fun isCorrectWord(word:String) = word==currentWord

    fun reInit() {
        _score.value=0
        _wordNumber.value=1
        count=0
        listOfWord.clear()
        createRandomWordsList()
        nextWord()
    }

    fun shuffleCharactersOfAWord(word:String):String {
        val tempList =mutableListOf<Int>()
        var tempWord=""
        var tempInt:Int
        while(tempList.size<word.length)
        {
            tempInt=(0 until word.length).random()
            if(tempInt !in tempList) {
                tempList.add(tempInt)
                tempWord+=word.get(tempInt)
            }
        }

        return tempWord
    }


}