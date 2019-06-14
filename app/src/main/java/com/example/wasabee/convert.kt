/*package com.example.wasabee

import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.ArrayAdapter
import android.R
import android.widget.EditText
import android.os.Bundle


protected fun onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // получаем экземпляр элемента ListView
    val listView = findViewById(R.id.listView) as ListView
    val editText = findViewById(R.id.editText) as EditText

    // Создаём пустой массив для хранения имен котов
    val catNames = ArrayList()

    // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
    val adapter: ArrayAdapter<String>
    adapter = ArrayAdapter(
        this,
        android.R.layout.simple_list_item_1, catNames
    )
    // Привяжем массив через адаптер к ListView
    listView.setAdapter(adapter)

    // Прослушиваем нажатия клавиш
    editText.setOnKeyListener(object : View.OnKeyListener() {
        fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            if (event.getAction() === KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    catNames.add(0, editText.text.toString())
                    adapter.notifyDataSetChanged()
                    editText.setText("")
                    return true
                }
            return false
        }
    })
}*/