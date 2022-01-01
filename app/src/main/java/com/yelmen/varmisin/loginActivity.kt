package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yelmen.varmisin.databinding.ActivityLoginBinding

class loginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        //kullanıcı oturum açtı mı açmadı mı kontrol et
        var currentuser = auth.currentUser
        if (currentuser != null) {
            startActivity(Intent(this, ilanlarActivity::class.java))
            finish()
        }
        //giriş yap butonu
        binding.girisyapbuton.setOnClickListener {
            var girisemail = binding.girisemail.text.toString()
            var girissifre = binding.girissifre.text.toString()
            if (TextUtils.isEmpty(girisemail)) {
                binding.girisemail.error = "Lüften E-mail adresinizi yazınız"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(girissifre)) {
                binding.girissifre.error = "Lüften parolanızı yazınız"
                return@setOnClickListener
            }
            //giris bilgilerini doğrula ve gir
            auth.signInWithEmailAndPassword(girisemail, girissifre)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        intent = Intent(this, ilanlarActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Hatalı giriş", Toast.LENGTH_LONG).show()
                    }
                }

        }
        //yeni üyelik sayfasına git
        binding.girisyeniuyelik.setOnClickListener {
            intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
            finish()
        }
        //parolamı unuttuma git
        binding.girissifreunuttum.setOnClickListener {
            intent = Intent(this, sifreUnuttumActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}