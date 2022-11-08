package edu.uark.ahnelson.assignment3solution

import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddDescriptionActivity: AppCompatActivity() {
    private lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_description_activity)
        description = findViewById<EditText>(R.id.add_description)
        findViewById<Button>(R.id.submit_button).setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DESCRIPTION, description.text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_DESCRIPTION = "edu.uark.ahnelson.assignment3solution.TITLE"
    }
}