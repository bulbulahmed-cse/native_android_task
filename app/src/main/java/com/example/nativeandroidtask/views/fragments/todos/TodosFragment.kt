package com.example.nativeandroidtask.views.fragments.todos

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nativeandroidtask.MyApplication
import com.example.nativeandroidtask.adapter.TodosAdapter
import com.example.nativeandroidtask.adapter.UserAdapter
import com.example.nativeandroidtask.databinding.FragmentTodosBinding
import com.example.nativeandroidtask.models.Todos
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.utils.ProgressUtils
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class TodosFragment : Fragment() {

    private lateinit var binding: FragmentTodosBinding

    @Inject
    lateinit var viewModel: TodosViewModel
    var userAdapter: UserAdapter? = null
    var todosAdapter: TodosAdapter? = null
    lateinit var userList: List<User>
    var todosList: List<Todos> = ArrayList()
    var page:Int = 1
    var userId:Int? = null
    var loading:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as AppCompatActivity).supportActionBar!!.title = "Todos List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView(todosList)
        loadUser()
        loadTodos(page)
        observers()
        listener()
    }

    private fun listener() {
        binding.userDropDown.setOnItemClickListener { _, _, position, _ ->
            page = 1
            if ( position == 0){
                userId = null
                loadTodos(page)
            }else{
                userId = userList[position - 1].id
                loadTodos(page)
            }
        }


    }

    private fun loadUser() {
        viewModel.getUser()
    }

    private fun loadTodos(page:Int) {
        viewModel.getTodos(page = page, userId = userId)
    }



    private fun observers() {

        viewModel.users.observe(viewLifecycleOwner) {
            if (it != null) {
                userList = it
                initMenuDropDown(userList)
            }
        }

        viewModel.todosList.observe(viewLifecycleOwner) {
            loading = true
            if (it != null) {
                todosList = it
                if (page>1){
                    todosAdapter?.setItem(it)
                    todosAdapter?.notifyDataSetChanged()
                }else{
                    todosAdapter = TodosAdapter(it)
                    binding.rvTodos.adapter = todosAdapter
                }
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

        viewModel.loadMoreLoading.observe(viewLifecycleOwner){
            it?.run {
                if (this){
                    binding.pbLoadMore.visibility = View.VISIBLE
                }else{
                    binding.pbLoadMore.visibility = View.GONE
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

    private fun initRecycleView(list: List<Todos>) {
        binding.rvTodos.layoutManager = LinearLayoutManager(activity)
        todosAdapter = TodosAdapter(list)
        binding.rvTodos.adapter = todosAdapter

        var pastItemsVisible: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.rvTodos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = (binding.rvTodos.layoutManager as LinearLayoutManager).childCount
                    totalItemCount = (binding.rvTodos.layoutManager as LinearLayoutManager).itemCount
                    pastItemsVisible = (binding.rvTodos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastItemsVisible >= totalItemCount) {

                            Log.v("...", "Last Item !")
                            page += 1
                            loadTodos(page)
                            loading = false
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).mInject.inject(this)
    }


}



