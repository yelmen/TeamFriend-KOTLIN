package com.yelmen.varmisin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yelmen.varmisin.databinding.ActivityHesapAyarlariBinding
import android.view.View
import com.yelmen.varmisin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class hesapAyarlariActivity : AppCompatActivity() {
    var secilenGorsel: Uri? = null
    var secilenBitmap: Bitmap? = null
    lateinit var binding: ActivityHesapAyarlariBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityHesapAyarlariBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        var currentuser=auth.currentUser
        binding.ayarmaildegis.setText(currentuser?.email)
        //kullanici idsine erişip ad soyad al
        var userReference=databaseReference?.child(currentuser?.uid!!)
        userReference?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.ayarisimdegis.setText(snapshot.child("adisoyadi").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        //bilgileri değiştir
        binding.button.setOnClickListener {
            var ayarmaildegis=binding.ayarmaildegis.text.toString().trim()
            currentuser!!.updateEmail(ayarmaildegis)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"E-mail Başarılı",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"E-mail başarısız Başarısız",Toast.LENGTH_LONG).show()
                    }

                }

            //bio kaydetme
            //kullanici idsini al aynı id de veri kaydet
             var currentuserDb2=currentuser?.let { it1-> databaseReference?.child(it1.uid)}
            currentuserDb2?.child("biyografi")?.setValue(binding.ayarbio.text.toString())


            //parola güncelleme
            var guncelleparola=binding.ayarsifredegis.text.toString().trim()
            currentuser!!.updatePassword(guncelleparola)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Parola Başarılı",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Parola Başarısız",Toast.LENGTH_LONG).show()
                    }

                }
            //ad soyad güncelleme
            var currentuserDb=currentuser?.let { it1-> databaseReference?.child(it1.uid)}
            currentuserDb?.removeValue()
            currentuserDb?.child("adisoyadi")?.setValue(binding.ayarisimdegis.text.toString())
            Toast.makeText(this,"Güncelleme Başarılı",Toast.LENGTH_LONG).show()

        }

        binding.button.setOnClickListener {
            intent=Intent(this,profilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }




    //hesaptan çıkış yap
    fun cikisyap(view: View){
        auth.signOut()
        startActivity(Intent(this,loginActivity::class.java))
        finish()
    }
    fun gorselSec(view: View) {
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
            )
        } else {
            val galeriIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent, 2)

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            secilenGorsel = data.data
            if (secilenGorsel != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, secilenGorsel!!)
                    secilenBitmap = ImageDecoder.decodeBitmap(source)
                    profilresim.setImageBitmap(secilenBitmap)

                } else {
                    secilenBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, secilenGorsel)
                    profilresim.setImageBitmap(secilenBitmap)
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}