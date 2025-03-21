# Coroutines Unstructured & Structured Concurrency

**Unstructured Concurrency**

Continuing with the coroutines example code where we had 2 buttons and 2 textviews namely DownloadUserData and Click here. Making the changes in the MainActivity class and adding a new class 
UserDataManager with a suspending function getTotalUserCount which returns the count of type Int.

![image](https://github.com/user-attachments/assets/83a43773-fc2b-4feb-85e7-e9df021b98ed)

![image](https://github.com/user-attachments/assets/ec059d60-f01a-4925-82f4-c50ba3b526cd)

If you execute the code now and click on the DownloadUserData, we will see the count displayed as 0 as shown below

![image](https://github.com/user-attachments/assets/4d5f7a5f-e6e0-4149-b643-962a12a34e6f)

Here the coroutine scope creates a new coroutine which behaves separately from the parent coroutine in the main activity. So this function reaches to the end and returns the count variable’s value before the completion of the coroutine. Because of that reason, instead of 50, we get 0 as the result. This is one of the weakness of unstructured concurrency. Unstructured Concurrency does not guarantee to complete all the tasks of the suspending function, before it returns. Actually, the child coroutines can be still running, even after the completion of the parent coroutine. As a result of that, there can be unpredictable errors, specially if we use launch coroutine builder like we just did. But, if we use async builder and if we use await function call for the return value, we might be able to get the expected result of the async coroutine.

Now, lets launch another coroutine with async builder and add a delay of 3seconds. This time let’s return 70. Since we return from an async block, we use **return@async** here instead of return. Also, let’s assign the returned value to a variable and then we can use await() to get the returned value.

![image](https://github.com/user-attachments/assets/b475ffbf-7ce3-4846-a0b7-a62f7026489e)

Now when we run the application, when we click teh DownloadUserDAta button, after a delay, we can see that value 70 instead of 0 like the above

![image](https://github.com/user-attachments/assets/ec1d1c9c-5c09-4daa-9b0e-2e7815cfbac9)

This doesn't mean unstructured concurrency is ok with async builders as still there are problems. As we know, in android, if there is an error occuring in a function, the function throws an exception. So we can catch the exception in the caller function and handle the situation. In unstructured concurrency, weather we use launch or async builders, there is no way to properly handle exceptions. So, even though it seems to be working well in some scenarios, it is not recommended to use unstructured concurrency.

**Structured Concurrency**

All the problems arised during the Unstructured Concurrency can be easily solved with the coroutine scope function. There are 2 Coroutine Scopes. 
1. **CoroutineScope** : This is the interface we used through out this course.
2. **coroutineScope** : This is a suspending function which allows us to create a child scope, within a given coroutine scope. This coroutine scope guarantees the completion of the tasks when the suspending function returns.

Let's understand it through code. Let's create 1 more class UserDataManager2. Defining the same method but using coroutineScope suspending function to provide the child scope instead of CoroutineScope interface. Instead of creating coroutines/lauching coroutines using this CoroutineScope interface, we will use coroutineScope suspending function inside this new suspending function to provide a child scope. Now we can use launch and async builders inside the child scope. Inside this child scope, we can directly use launch coroutine builder to launch a coroutine. If we don't use any dispatchers, then by default it will launch in dispatcher of parent scope. In our case, the parent dispatcher is the Dispatcher.Main. But we would like to use the Dispatcher.IO. 

![image](https://github.com/user-attachments/assets/117da108-3c0a-4d5b-8c75-bdb018cd29a3)

What happens here is, this coroutineScope Interface guarantees the completion of all the tasks within the child scope provided by it before the return of the function. So, once we call to this getTotalUserCount(), before it returns to the caller, all the coroutines launched within this scope will complete. Also making the required changes UserDataManager to UserDataManager2 in the MainActivity.

![image](https://github.com/user-attachments/assets/155f9b77-c714-446e-a935-3a6a1ee4fb3b)

In the unstructured concurrency, we were getting the result of 70. Now, in this case, we must be receiving the output as 120(70 + 50) as all the coroutines will get completed before it returns the result to the user as shown below. 

![image](https://github.com/user-attachments/assets/76b370c2-0216-4a54-a24f-aabb51ceb4b3)

Now because of this coroutineScope suspending function, the coroutineScope suspending function we can have a child scope, which is under the control of parent scope created in the main activity. So this is the recommended best practice. When you have more than one coroutines, you should always start with the Dispatchers.Main. You should always start with the CoroutineScope interface and inside suspending functions we should use **coroutineScope** function to provide a child scope. So this is structured concurrency.

Structured concurrency guarantees to complete all the work started by coroutines, within the child scope, before the return of the suspending function. Actually this coroutineScope waits for the child coroutines to complete. Not only that, there are other benefits of the structured concurrency. When errors happen, when exceptions thrown, structured concurrency guarantees to notify the caller so that we can handle exceptions easily and effectively. We can also use a structured concurrency to cancel tasks we started. If we cancel entire child scope, all the works happening inside that scope will be canceled. We can also cancel coroutines separately.
