package com.pateljay012347.todolist
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {

    // Declare private properties to hold references to UI components.
    private lateinit var spinner: ListView
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the UI components by finding them by their IDs.
        spinner = findViewById(R.id.spinner)
        editText = findViewById(R.id.editTextTask)

        // Set up the "Add" button so that when it is clicked, it adds the task
        // entered in the EditText to the spinner.
        val addButton: Button = findViewById(R.id.button)
        addButton.setOnClickListener {
            // Get the value entered in the EditText as a String.
            val newValue: String = editText.text.toString()

            // Get the current items in the spinner as a MutableList of Strings.
            val currentItems: MutableList<String> = spinner.adapter?.let { adapter ->
                val items = mutableListOf<String>()
                for (i in 0 until adapter.count) {
                    items.add(adapter.getItem(i) as String)
                }
                items
            } ?: mutableListOf()

            // Adds new value if it contains some text init.
            if(newValue != "") {
                // Add the new value to the current items.
                currentItems.add(newValue)
            }

            // Create a new ArrayAdapter with the updated items and set it as
            // the adapter for the spinner.
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, currentItems)
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            spinner.adapter = adapter

            // Set the item click and long click listeners for the spinner.
            spinner.onItemClickListener = this
            spinner.onItemLongClickListener = this

            // Clear the EditText.
            editText.text.clear()
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     *
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     * will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // If the clicked item is a CheckedTextView, toggle its state.
        if(view is CheckedTextView){
            val checkBox = view
            checkBox.toggle()
        }
    }

    /**
     * Callback method to be invoked when an item in this view has been
     * clicked and held.
     *
     * Implementers can call getItemAtPosition(position) if they need to access
     * the data associated with the selected item.
     *
     * @param parent The AbsListView where the click happened
     * @param view The view within the AbsListView that was clicked
     * @param position The position of the view in the list
     * @param id The row id of the item that was clicked
     *
     * @return true if the callback consumed the long click, false otherwise
     */
    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ): Boolean {

        // Get the item that was long-clicked from the spinner
        val item: String = spinner.adapter.getItem(position) as String

        // Get a mutable list of all the items in the spinner
        val currentItems: MutableList<String> = spinner.adapter?.let { adapter ->
            val items = mutableListOf<String>()
            for (i in 0 until adapter.count) {
                items.add(adapter.getItem(i) as String)
            }
            items
        } ?: mutableListOf()

        // Remove the long-clicked item from the list
        currentItems.remove(item)

        // Create a new adapter with the updated list of items
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, currentItems)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Set the new adapter for the spinner and notify that the data set has changed
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        // Return true to indicate that the long click has been consumed
        return true
    }
}