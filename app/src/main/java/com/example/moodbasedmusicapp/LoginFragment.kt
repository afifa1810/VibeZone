package com.example.moodbasedmusicapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var auth: FirebaseAuth

    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // SharedPreferences init
        sharedPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val isRemembered = sharedPreferences.getBoolean("rememberMe", false)

        // Auto-login if user chose "keep me logged in"
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (isRemembered && currentUser != null) {
            val intent = Intent(requireContext(), MoodActivity::class.java)
            startActivity(intent)
            activity?.finish()
            return view
        }

        // UI Components
        usernameEditText = view.findViewById(R.id.etEmail)
        passwordEditText = view.findViewById(R.id.etPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        signUpTextView = view.findViewById(R.id.tvSignup)
        rememberMeCheckBox = view.findViewById(R.id.cbRememberMe)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Save preference only if checkbox is checked
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("rememberMe", rememberMeCheckBox.isChecked)
                            editor.apply()

                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), MoodActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        signUpTextView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignupFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }


}
