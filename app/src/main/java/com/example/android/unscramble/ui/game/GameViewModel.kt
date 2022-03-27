package com.example.android.unscramble.ui.game


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


class GameViewModel:ViewModel() {

    val score=MutableLiveData(0)
    val currentScrambledWord=MutableLiveData("")
    lateinit var currentWord:String

    val wordNumber=MutableLiveData(0)
    var count=0
    var inc=0

    val listOfWord= mutableListOf<String>()
    init {
        init()
    }

    fun init()
    {
        randomList()
        currentWord=listOfWord.get(count++)
        Log.d("mosab",currentWord)
        wordNumber.value=count
        currentScrambledWord.value=randomWord(currentWord)
        Log.d("mosab",currentScrambledWord.value.toString())
    }

    fun randomList ()
    {
        for (i in 1..10)
        {
          randomWord()
        }
    }

    fun randomWord()
    {
        var str=allWordsList.random()
        while(str !in listOfWord)listOfWord.add(str)

    }

    fun nextWord():Boolean
    {
        if(count==10)return false


             currentWord=listOfWord[count++]
             wordNumber.value=count
             currentScrambledWord.value=randomWord(currentWord)

       return true
    }

    fun score()
    {

        inc+=SCORE_INCREASE
        score.value=inc
    }
    fun reInit()
    {
        score.value=0
        wordNumber.value=1
        count=0
        listOfWord.clear()
        init()
    }
    fun randomWord(word:String):String
    {
        var temp =mutableListOf<Int>()
        var tempWord=""
        var tempInt=(0..(word.length-1)).random()
        while(temp.size<word.length)
        {
            tempInt=(0..(word.length-1)).random()
            if(tempInt !in temp) {
                temp.add(tempInt)
                tempWord+=word.get(tempInt)
            }
        }

        return tempWord
    }

}