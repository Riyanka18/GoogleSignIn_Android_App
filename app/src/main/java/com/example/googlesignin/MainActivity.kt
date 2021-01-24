package com.example.googlesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var signInButton: SignInButton
    lateinit var signInClient: GoogleSignInClient
    var TAG: String = "MainActivity";
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var signoutButton: Button
    lateinit var signInOptions: GoogleSignInOptions
    val req: Int = 9001
    lateinit var n:TextView
    lateinit var e:TextView
    lateinit var u:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInButton = findViewById(R.id.signinbutton)
        signoutButton = findViewById(R.id.signout)
        firebaseAuth = FirebaseAuth.getInstance()

        n=findViewById(R.id.name)
        e=findViewById(R.id.email)
        u=findViewById(R.id.imag)

        signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        signInClient = GoogleSignIn.getClient(this, signInOptions)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signoutButton.setOnClickListener {
            signoutButton.visibility = View.INVISIBLE
        }
        signInButton.setOnClickListener {
            val i = signInClient.signInIntent
            startActivityForResult(i,req)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == req) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            Toast.makeText(this,"Signed in successful",Toast.LENGTH_SHORT).show()
            if(account!=null)
            {
                var name=account.displayName
                var email=account.email
                var url=account.photoUrl
                n.text=name
                e.text=email
                u.setImageURI(url)
            }
            signoutButton.visibility=View.VISIBLE
        } catch (e: ApiException) {
            Toast.makeText(this,"Sign in un-successful",Toast.LENGTH_SHORT).show()
        }
    }
}