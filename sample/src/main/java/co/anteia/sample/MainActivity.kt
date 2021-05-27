package co.anteia.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.anteia.anteiasdk.AnteiaSDK
import co.anteia.anteiasdk.ui.confirmemail.EmailActivity
import co.anteia.anteiasdk.ui.dataentry.DataEntryActivity
import co.anteia.anteiasdk.ui.aidetection.DocumentBackDetectionActivity
import co.anteia.anteiasdk.ui.aidetection.DocumentDetectionActivity
import co.anteia.anteiasdk.ui.aidetection.FaceDetectionActivity
import co.anteia.anteiasdk.ui.termsandconditions.TermsAndConditionsActivity
import co.anteia.sample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private val testProjectId = "60987eab7dc0b8596f75aa4c";
    private val testCode = "testingMobile"
    private val termsRequestCode= 200
    private val dataEntryRequestCode= 201
    private lateinit var binding: ActivityMainBinding
    private val userName = "5ffda817c683b046372d24b9"
    private val apiKey = "testApiKeyAnteia2020"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        val anteia = AnteiaSDK() // initialize variable
        anteia.setupKeys(testCode,testProjectId) //setup project keys
        anteia.setupUser(userName,apiKey) //setup user keys


        binding.dataEntryButton.setOnClickListener {
            launchDataEntry()
        }
        binding.terminosButton.setOnClickListener {
            launchTermsAndConditions()
        }
        binding.emailButton.setOnClickListener {
            launchConfirmEmail()
        }
        binding.frontDetectionButton.setOnClickListener {
            launchFrontML()
        }
        binding.backDetectionButton.setOnClickListener {
            launchBackML()
        }
        binding.faceDetectionButton.setOnClickListener {
            launchFaceML()
        }

    }

    private fun launchFaceML(){
        intent = Intent(this, FaceDetectionActivity::class.java)
        val b = Bundle()
        b.putString("registrationID","60ad1733b504814a06872e53" )
        intent.putExtras(b)
        startActivityForResult(intent, dataEntryRequestCode)
    }
    private fun launchFrontML() {
        intent = Intent(this, DocumentDetectionActivity::class.java)
        val b = Bundle()
        b.putString("registrationID","60ad1733b504814a06872e53" )
        intent.putExtras(b)
        startActivityForResult(intent, dataEntryRequestCode)
    }
    private fun launchBackML() {
        intent = Intent(this, DocumentBackDetectionActivity::class.java)
        val b = Bundle()
        b.putString("registrationID","60ad1733b504814a06872e53" )
        intent.putExtras(b)
        startActivityForResult(intent, dataEntryRequestCode)
    }

    private fun launchTermsAndConditions() {
        //como aqui es donde se genera el primer token, no necesitamos pasarle registrationId
        intent = Intent(this, TermsAndConditionsActivity::class.java)
        startActivityForResult(intent, termsRequestCode)

    }


    private fun launchDataEntry(){
        intent = Intent(this, DataEntryActivity::class.java)
        val b = Bundle()
        b.putString("registrationID","60ad1733b504814a06872e53" )
        intent.putExtras(b)
        startActivityForResult(intent, dataEntryRequestCode)
    }



    private fun launchConfirmEmail() {
        intent = Intent(this, EmailActivity::class.java)
        val b = Bundle()
        b.putString("registrationID","60ad1733b504814a06872e53" )
        intent.putExtras(b)
        startActivityForResult(intent, termsRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == termsRequestCode) {
            if (resultCode == RESULT_OK) {
                val result = data?.extras!!.getString("response")
                onSNACK(result.toString(),activity = this)
                Log.d("resultado", result.toString())
                val registrationId = data.extras!!.getString("registrationId")
                Log.d("registration ID", registrationId.toString())
            }
            else if (resultCode == RESULT_CANCELED){
                Log.d("resultado", "cancelado")
            }
        }
        else if (requestCode == dataEntryRequestCode){
            if (resultCode == RESULT_OK) {
                val result = data?.extras!!.getString("response")

                onSNACK(result.toString(),activity = this)
                Log.d("resultado", result.toString())
            }
            else if (resultCode == RESULT_CANCELED){
                val result = data?.extras?.getString("response")
                Log.d("resultado", "cancelado. ${result.toString()}")
            }
        }
    }

    private fun onSNACK(msg : String, activity : Activity){
        val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),
            msg, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }
}