package com.deepak.knote.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.deepak.knote.R
import com.deepak.knote.util.hideSoftKeyboard
import kotlinx.android.synthetic.main.activity_update_note.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

/**
 * Activity to update note
 */
class UpdateNoteActivity : AppCompatActivity() {
    private var id: Int = 0
    private var title: String? = null
    private var content: String? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        loadNoteInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save_note -> {
                updateNote()
                true
            }
            R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadNoteInfo() {
        id = intent.getIntExtra(NOTE_ID, 0)
        title = intent?.getStringExtra(NOTE_TITLE).toString()
        content = intent?.getStringExtra(NOTE_CONTENT).toString()
        position = intent.getIntExtra(POSITION, 0)

        update_note_title.setText(title)
        update_note_content.setText(content)
        update_note_title.selectionEnd
    }

    private fun updateNote() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()
        if (validateInput(title, content)) {
            hideSoftKeyboard()
            val intent = Intent()
            intent.putExtra(NOTE_ID, id)
            intent.putExtra(NOTE_TITLE, title)
            intent.putExtra(NOTE_CONTENT, content)
            intent.putExtra(POSITION, position)
            setResult(Activity.RESULT_OK, intent)
            supportFinishAfterTransition()
        } else {
            toast("Field(s) is Empty...")
        }
    }

    private fun validateInput(title: String, content: String): Boolean {
        return !(TextUtils.isEmpty(title.trim()) && TextUtils.isEmpty(content.trim()))
    }

    override fun onBackPressed() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()

        if (this.content == content) {
            finish()
        } else if (title.isNotEmpty() || content.isNotEmpty()) {
            alert(getString(R.string.alert_message)) {
                yesButton { supportFinishAfterTransition() }
                noButton { it.dismiss() }
            }.show()
        } else {
            supportFinishAfterTransition()
        }
    }
}

