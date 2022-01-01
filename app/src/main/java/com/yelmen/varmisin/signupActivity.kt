package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yelmen.varmisin.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.*

class signupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        //kaydet butonuna tıklandığında veri al kontrol et
        binding.uyekaydet.setOnClickListener {
            var uyeadsoyad=binding.uyeadsoyad.text.toString()
            var uyeemail=binding.uyeemail.text.toString()
            var uyesifre=binding.uyesifre.text.toString()
            if (TextUtils.isEmpty(uyeadsoyad)){
                binding.uyeadsoyad.error="Lütfen Ad ve Soyad kısmını boş geçmeyiniz"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyesifre)){
                binding.uyesifre.error="Lütfen Şifre kısmını boş geçmeyiniz"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeemail)){
                binding.uyeemail.error="Lütfen e-mail kısmını boş geçmeyiniz"
                return@setOnClickListener
            }
                //kullanıcı bilgilerini veritabanına ekleme
            auth.createUserWithEmailAndPassword(binding.uyeemail.text.toString(),binding.uyesifre.text.toString())
                .addOnCompleteListener(this){task->
                    if (task.isSuccessful){
                        //güncel kullanıcı bilgilerini al
                        var currentuser=auth.currentUser
                        //kullanici idsini al aynı id de veri kaydet
                        var currentuserDb=currentuser?.let { it1-> databaseReference?.child(it1.uid)}
                        currentuserDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Hatalı Kayıt",Toast.LENGTH_LONG).show()
                    }

                }
        }
        //login sayfasına git

        binding.uyegirisyapbuton.setOnClickListener {

            intent=Intent(this,profilActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}