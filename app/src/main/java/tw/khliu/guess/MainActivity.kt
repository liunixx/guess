package tw.khliu.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   var secretNumber = SecretNumber()
   val TAG:String=MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Secret Number is : "+secretNumber.secret)
    }

   fun check(view: View) {
       val n:Int = et_number.text.toString().toInt()
       var msg = getString(R.string.yes_you_got_it)
       val diff = secretNumber.validate(n)
       if(diff<0) {
           msg=getString(R.string.bigger)
       } else if(diff>0) {
           msg=getString(R.string.smaller)
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
