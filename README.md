# Coroutines Unstructured Concurrency

Contunuing with the coroutines example code where we had 2 buttons and 2 textviews namely DownloadUserData and Click here. Making the changes in the MainActivity class and adding a new class 
UserDataManager with a suspending function getTotalUserCount which returns the count of type Int.

![image](https://github.com/user-attachments/assets/83a43773-fc2b-4feb-85e7-e9df021b98ed)

![image](https://github.com/user-attachments/assets/ec059d60-f01a-4925-82f4-c50ba3b526cd)

If you execute the code now and click on the DownloadUserData, we will see the count displayed as 0 as shown below

![image](https://github.com/user-attachments/assets/4d5f7a5f-e6e0-4149-b643-962a12a34e6f)

Here this coroutine scope crates a new coroutine which behaves separately from this parent coroutine in the main activity. So this function reaches to the end and returns this count variable’s 
value before the completion of the coroutine. Because of that reason, instead of 50, we got 0 as the result. This is one of the weakness of unstructured concurrency. Unstructured Concurrency 
does not guarantee to complete all the tasks of the suspending function, before it returns.

Actually , the child coroutines can be still running, even after the completion of the parent coroutine. As a result of that, there can be unpredictable errors, specially if we use launch 
coroutine builder like we just did. But, if you use async builder and if you use await function call for the return value, you might be able to get the expected result of the async coroutine.
Now, lets lauch another coroutine with async builder add a delay of 3seconds. This time let’s return 70. Since we return from an async block, we use **return@async** here instead of return.
Let’s assign the returned value to a variable and then we can use await() to get the returned value.

![image](https://github.com/user-attachments/assets/b475ffbf-7ce3-4846-a0b7-a62f7026489e)

Now when we run the application, when we click teh DownloadUserDAta button, after a delay, we can see that value 70 instead of 0 like the above

![image](https://github.com/user-attachments/assets/ec1d1c9c-5c09-4daa-9b0e-2e7815cfbac9)

