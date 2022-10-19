package com.example.nativeandroidtask.views.fragments.todos

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.nativeandroidtask.MyApplication
import com.example.nativeandroidtask.adapter.UserAdapter
import com.example.nativeandroidtask.databinding.FragmentTodosBinding
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.utils.ProgressUtils
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TodosFragment : Fragment() {

    private lateinit var binding: FragmentTodosBinding

    @Inject
    lateinit var viewModel: TodosViewModel
    var userAdapter: UserAdapter? = null
    lateinit var userList: List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUser()
        observers()
        listener()
    }

    private fun listener() {
        binding.userDropDown.setOnItemClickListener { _, _, position, _ ->

            Log.d("tag",position.toString())
        }
    }

    private fun loadUser() {
        viewModel.getUser()
    }

    private fun observers() {

        viewModel.users.observe(viewLifecycleOwner) {
            if (it != null) {
                userList = it
                initMenuDropDown(userList)
            }
        }

        viewModel.eventShowMessage.observe(viewLifecycleOwner) {

            it?.run {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    this,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("OK") { }
                    .apply { show() }
            }
        }
        viewModel.eventShowLoading.observe(viewLifecycleOwner) {

            it?.run {
                if (this) {
                    ProgressUtils.showProgress(requireActivity())
                } else {
                    ProgressUtils.hideProgress(requireActivity())
                }
            }
        }
    }

    private fun initMenuDropDown(menus: List<User>?) {
        val menuList: MutableList<String> = ArrayList()
        if (menus != null) {
            menuList.add("Select User")
            for (i in menus) {
                menuList.add(i.name.toString())
            }
        }
        val adapter =
            ArrayAdapter<String>(this.requireContext(), R.layout.simple_spinner_dropdown_item, menuList)
        binding.userDropDown.setAdapter(adapter)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).mInject.inject(this)
    }


}



