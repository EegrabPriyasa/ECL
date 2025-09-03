package com.example.ecl_project_file.HelperClasses

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

object SpinnerHelper {
  fun setupSpinner(
    context: Context,
    spinner: Spinner,
    items: List<String>,
    defaultText: String = "Select Option",
    onItemSelected: ((String) -> Unit)? = null
  ) {
    val spinnerItems = mutableListOf(defaultText)
    spinnerItems.addAll(items)

    // Use a custom ArrayAdapter to modify text size programmatically
    val adapter = object : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerItems) {
      override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).textSize = 12f  // Selected item text size
        return view
      }

      override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view as TextView).textSize = 12f  // Dropdown item text size
        return view
      }
    }

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if (position != 0) { // ignore default text
          val selectedItem = parent.getItemAtPosition(position).toString()
          onItemSelected?.invoke(selectedItem)
          Toast.makeText(context, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>) {
        // No action needed
      }
    }
  }
}