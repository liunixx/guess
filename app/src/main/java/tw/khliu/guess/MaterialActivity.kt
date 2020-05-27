package tw.khliu.guess

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG,"The Secret Number is : ${secretNumber.secret}")
        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Replay Game")
                .setMessage("Are You Sure ?")
                .setPositiveButton(R.string.ok,{dialog,which ->
                    secretNumber.reset()
                    Log.d(TAG,"The Secret Number is : ${secretNumber.secret}")
                    et_number.setText("")
                    tv_count.setText(secretNumber.count.toString())
                })
                .setNeutralButton("Cancel",null)
                .show()
        }
        tv_count.setText(secretNumber.count.toString())
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
            .setPositiveButton(getString(R.string.ok),null)
            .show()
    }

}
