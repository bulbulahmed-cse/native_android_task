package com.example.nativeandroidtask.di



import com.example.nativeandroidtask.views.fragments.todos.TodosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTodosFragmentInjector(): TodosFragment

}