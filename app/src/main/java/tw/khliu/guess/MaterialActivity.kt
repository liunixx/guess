package tw.khliu.guess

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    var secretNumber = SecretNumber()
    val TAG=MaterialActivity::class.java.simpleName
    val REQUEST_RECORD=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG,"The Secret Number is : ${secretNumber.secret}")
        fab.setOnClickListener { view ->
            replay()
        }
        tv_count.setText(secretNumber.count.toString())
        showSaved()
    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(R.string.replay_title)
            .setMessage(R.string.replay_confirm)
            .setPositiveButton(R.string.ok, { dialog, which ->
                secretNumber.reset()
                Log.d(TAG, "The Secret Number is : ${secretNumber.secret}")
                et_number.setText("")
                tv_count.setText(secretNumber.count.toString())
                showSaved()
            })
            .setNeutralButton(R.string.cancel, null)
            .show()
    }

    fun showSaved() {
        val count =getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER",-1)
        val nick =getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME","")
        Log.d(TAG, count.toString() + "/" + nick)
    }
    fun check(view: View) {
        val n:Int = et_number.text.toString().toInt()
        var msg = getString(R.string.yes_you_got_it)
        val diff = secretNumber.validate(n)
        if(diff<0) {
            msg=getString(R.string.bigger)
        } else if(diff>0) {
            msg=getString(R.string.smaller)
        } else if(diff==0) {
            if(secretNumber.count<3)
                msg=resources.getString(R.string.excellent_guess) + secretNumber.secret.toString()
        }
        tv_count.setText(secretNumber.count.toString())
        // Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(msg)
            .setPositiveButton(getString(R.string.ok),{dialog, which->
                  if(diff==0) {
                      val intent = Intent(this,RecordActivity::class.java)
                      intent.putExtra("COUNTER",secretNumber.count)
                      // startActivity(intent)
                      startActivityForResult(intent,REQUEST_RECORD)
                  }
            })
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_RECORD) {
            if(resultCode==RESULT_OK) {
                val nickname=data?.getStringExtra("REC_NICKNAME")
                Log.d(TAG,"onActivityResult : "+nickname)
               replay()
            }
        }
    }
}
