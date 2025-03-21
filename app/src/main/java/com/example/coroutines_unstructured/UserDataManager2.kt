package com.example.coroutines_unstructured

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserDataManager2 {

    var count = 0
    lateinit var deferred : Deferred<Int>
        suspend fun getTotalUserCount() : Int {

        coroutineScope {
            // If we don't use any dispatchers, then by default it will launch in dispatcher of parent scope.
            // In our case, the parent dispatcher is the Dispatcher.Main.
            launch(IO) {
                delay(1000)
                count = 50
            }

            deferred = async(IO) {
                delay(3000)
                return@async 70
            }
        }

        return count + deferred.await()
    }
}