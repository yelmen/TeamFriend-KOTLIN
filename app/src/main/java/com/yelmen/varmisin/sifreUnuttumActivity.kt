package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yelmen.varmisin.databinding.ActivitySifreUnuttumBinding
import kotlinx.android.synthetic.main.activity_sifre_unuttum.*

class sifreUnuttumActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySifreUnuttumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySifreUnuttumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.unuttumsifirla.setOnClickListener {
            var unuttumemail=unuttumemail.text.toString().trim()
            if (TextUtils.isEmpty(unuttumemail)){
                binding.unuttumemail.error="Lütfen e-mail giriniz"
            }else{
                auth.sendPasswordResetEmail(unuttumemail)
                    .addOnCompleteListener(this){task->
                        if (task.isSuccessful){
                            binding.sifirlasonuc.text="Parola sıfırlama bağlantınız e-mail adresinize gönderildi"
                        }else{
                            binding.sifirlasonuc.text="Parola sıfırlama işlemi başarısız"
                        }

                    }
            }
        }
        //logine gönder
        binding.unuttumgirisyap.setOnClickListener {
            startActivity(Intent(this,loginActivity::class.java))
            finish()
        }
    }
}