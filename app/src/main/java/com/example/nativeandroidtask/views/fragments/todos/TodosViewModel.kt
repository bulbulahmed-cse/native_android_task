package com.example.nativeandroidtask.views.fragments.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.network.ApiException
import com.example.nativeandroidtask.repositories.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodosViewModel  @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _users: MutableLiveData<
            List<User>> by lazy {
        MutableLiveData<
                List<User>>()
    }

    val users: LiveData<
            List<User>?>
        get() = _users

    //----------------------------------------------//

    //----------------------------------------------//

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    //----------------------------------------------//

    private val _eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventShowLoading: LiveData<Boolean?>
        get() = _eventShowLoading

    //----------------------------------------------//

    fun getUser() = viewModelScope.launch {

        try {

                _eventShowLoading.value = true

                val response = repository.getUsers()

                if (response.isNotEmpty()) {


                    _users.value = response

                } else {

                    throw ApiException(response.toString())

                }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }

}