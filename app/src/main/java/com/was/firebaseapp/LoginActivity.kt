package com.was.firebaseapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.was.firebaseapp.extensions.getText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password




class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btCriar.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(inputEmail.getText(),inputSenha.getText())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = mAuth.currentUser
                            Toast.makeText(this, "Usuario criado com sucesso...", Toast.LENGTH_SHORT).show()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, task.exception?.message,
                                    Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }

                        // ...
                    }
            btLogin.setOnClickListener {
                mAuth.signInWithEmailAndPassword(inputEmail.getText(), inputSenha.getText())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful && mAuth.currentUser?.isEmailVerified == true) {
                                val user = mAuth.currentUser
                                Toast.makeText(this, "Logado" + mAuth.currentUser?.isEmailVerified, Toast.LENGTH_SHORT).show()
                                //updateUI(user)
                            } else {
                                Toast.makeText(this, task.exception?.message,
                                        Toast.LENGTH_SHORT).show()
                                //updateUI(null)
                            }

                            // ...
                        }
            }

            btLogout.setOnClickListener {
                mAuth.signOut()
                Toast.makeText(this, "Deslogado", Toast.LENGTH_SHORT).show()
            }

            btSendEmail.setOnClickListener {
                val user : FirebaseUser? = mAuth.currentUser
                user?.sendEmailVerification()
                        ?.addOnCompleteListener(this) {
                            task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "sucesso", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "email nao enviado", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }
}
