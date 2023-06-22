package com.vytautasdev.flowdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

//    private val _stateFlow = MutableStateFlow(0)
//    private val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 10, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val sharedFlow = _sharedFlow.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        lifecycleScope.launch {
//            stateFlow.collectLatest {
//                println("Counter = $it")
//            }
//        }

        lifecycleScope.launch {
            sharedFlow.collect {
                println("$it")
            }
        }
    }

    private fun myFlow(): Flow<Int> = flow {
        // Producer block
        var counter = 1
        while (counter < 6) {
            emit(counter)
            counter++
            delay(2000)
        }
    }

//    fun handleFlow(view: View) {
//        _stateFlow.value += 1
//    }

    fun handleFlow(v: View) {
        var counter = 1
        lifecycleScope.launch {
            while (counter < 6) {
                _sharedFlow.emit(counter)
                counter++
                delay(2000)
            }
        }
    }
}