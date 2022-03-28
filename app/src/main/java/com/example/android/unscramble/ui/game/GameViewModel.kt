package com.example.android.unscramble.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {

    val score=MutableLiveData(0)
    val currentScrambledWord=MutableLiveData("")
    lateinit var currentWord:String

    val wordNumber=MutableLiveData(0)
    var count=0

    val listOfWord= mutableListOf<String>()

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
        var str=allWordsList.random()
        if(str !in listOfWord)listOfWord.add(str)
        else getRandomWord()
    }

    fun nextWord() {
        currentWord=listOfWord[count++]
        wordNumber.value=count
        currentScrambledWord.value=shuffleCharactersOfAWord(currentWord)
    }

    fun increaseScore() {
        score.value=score.value?.plus(SCORE_INCREASE)
    }

    fun isCorrectWord(word:String) = word==currentWord

    fun reInit() {
        score.value=0
        wordNumber.value=1
        count=0
        listOfWord.clear()
        createRandomWordsList()
        nextWord()
    }

    fun shuffleCharactersOfAWord(word:String):String {
        var tempList =mutableListOf<Int>()
        var tempWord=""
        var tempInt:Int
        while(tempList.size<word.length)
        {
            tempInt=(0..(word.length-1)).random()
            if(tempInt !in tempList) {
                tempList.add(tempInt)
                tempWord+=word.get(tempInt)
            }
        }

        return tempWord
    }


}