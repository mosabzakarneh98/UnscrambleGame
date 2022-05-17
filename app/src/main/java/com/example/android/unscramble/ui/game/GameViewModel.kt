package com.example.android.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}*/


class GameViewModel : ViewModel() {

    private val _scoreLiveData = MutableLiveData(0)
    val scoreLiveData: LiveData<Int> = _scoreLiveData

    private val _currentScrambledWordLiveData = MutableLiveData("")
    val currentScrambledWordLiveData: LiveData<String> = _currentScrambledWordLiveData

    private val _wordNumberLiveData = MutableLiveData(0)
    val wordNumberLiveData: LiveData<Int> = _wordNumberLiveData

    private lateinit var currentWord: String

    private val _flagSingleLiveEvent = SingleLiveEvent(false)
    val flagSingleLiveEvent: LiveData<Boolean> = _flagSingleLiveEvent

    private var _count = 0
    var count: Int
        get() = _count
        set(value) {
            _count = value
        }

    private val listOfWord = mutableListOf<String>()

    init {
        createRandomWordsList()
        nextWord()
    }

    fun nextWord() {
        currentWord = listOfWord[count++]
        _wordNumberLiveData.value = count
        _currentScrambledWordLiveData.value = shuffleCharactersOfAWord(currentWord)
    }

    fun increaseScore() {
        _scoreLiveData.value = _scoreLiveData.value?.plus(SCORE_INCREASE)
        _flagSingleLiveEvent.value = true
    }

    fun isCorrectWord(word: String): Boolean {
        if (word == currentWord) {
            increaseScore()
            return true
        }
        return false
    }

    fun reInit() {
        _scoreLiveData.value = 0
        _wordNumberLiveData.value = 1
        count = 0
        listOfWord.clear()
        createRandomWordsList()
        nextWord()
    }

    private fun createRandomWordsList() {
        for (i in 1..MAX_NO_OF_WORDS) {
            getRandomWord()
        }
    }

    private fun getRandomWord() {
        val str = allWordsList.random()
        if (str !in listOfWord) listOfWord.add(str)
        else getRandomWord()
    }

    private fun shuffleCharactersOfAWord(word: String): String {
        val tempList = mutableListOf<Int>()
        var tempWord = ""
        var tempInt: Int
        while (tempList.size < word.length) {
            tempInt = (word.indices).random()
            if (tempInt !in tempList) {
                tempList.add(tempInt)
                tempWord += word[tempInt]
            }
        }

        return tempWord
    }
}