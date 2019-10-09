package com.dianakarenms.dogslisttd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_dogs_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dianakarenms.models.Dog
import com.dianakarenms.utils.PictureTools
import com.dianakarenms.views.SquareImageView
import kotlinx.android.synthetic.main.activity_dogs_list.toolbar

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class DogsListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private val ADD_NEW_DOG = 101
    private lateinit var viewModel: SharedDogsViewModel
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        viewModel = ViewModelProviders.of(this@DogsListActivity).get(SharedDogsViewModel::class.java)

        setupRecyclerView(item_list)

        fab.setOnClickListener { view ->
            val intent = Intent(this@DogsListActivity, AddNewDogActivity::class.java)
            startActivityForResult(intent, ADD_NEW_DOG)
        }

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = SimpleItemRecyclerViewAdapter(this, twoPane)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@DogsListActivity)
        recyclerView.addItemDecoration(

            DividerItemDecoration(
                this@DogsListActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel?.dogsList.observe(this, Observer { dogs ->
            if (dogs.isEmpty()) {
                recyclerView.visibility = View.GONE
                empty_list.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                empty_list.visibility = View.GONE
            }
            adapter.setDogs(dogs!!)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NEW_DOG) {
            viewModel.addNew(data?.getSerializableExtra("dog") as Dog)
        }
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: DogsListActivity,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private var values: ArrayList<Dog> = arrayListOf()
        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Dog
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_DOG, item.name)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_DOG, item)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]

            if (item.imageUri.isNotEmpty()) {
                val bitmap = PictureTools.decodeSampledBitmapFromUri(item.imageUri, 500, 500)
                holder.imageView.setImageBitmap(bitmap)
            }

            holder.contentView.text = item.name

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount(): Int {
            return values.count()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val contentView: TextView = view.content
            val imageView: SquareImageView = view.imageView
        }

        internal fun setDogs(dogsList: ArrayList<Dog>) {
            this.values.clear()
            this.values.addAll(dogsList)
            notifyDataSetChanged()
        }
    }
}
