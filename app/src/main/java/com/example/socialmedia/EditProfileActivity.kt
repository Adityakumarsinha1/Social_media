package com.example.socialmedia


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.socialmedia.databinding.ActivityEditProfileBinding
import com.example.socialmedia.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage



val firebaseUser=FirebaseAuth.getInstance().currentUser!!

class EditProfileActivity : AppCompatActivity() {
//    private lateinit var bio: EditText
//    private lateinit var fullname: EditText
//    private lateinit var username: EditText
//    private lateinit var deleteaccount: Button
//    private lateinit var changephoto: TextView
//    private lateinit var submitedit: ImageButton
//    private lateinit var dontedit: ImageButton
    private lateinit var binding: ActivityEditProfileBinding
//
//    private lateinit var sbio: String
//    private lateinit var sfullname: String
//    private lateinit var susername: String
//
//
//    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imageUri: Uri
    private lateinit var dialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this).setMessage("Updating Profile").setCancelable(false)

        database = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance()

        binding.editChangephoto.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.submitedit.setOnClickListener {
        if (binding.editUsername.text.isEmpty())
            Toast.makeText(this,"UserName can not be empty.",Toast.LENGTH_SHORT).show()
        else if (binding.editFullname.text.isEmpty())
           Toast.makeText(this,"FullName can not be empty.",Toast.LENGTH_SHORT).show()
        else if (imageUri == null)
           Toast.makeText(this,"Please Select an image",Toast.LENGTH_SHORT).show()
        else uploadData()
        }

        binding.dontedit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("Users").child(firebaseUser.uid)
        reference.putFile(imageUri).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener {  task ->
                    uploadinfo(task.toString())
                }
            }
            }
        }

    private fun uploadinfo(imageurl: String) {
    val user= Users(firebaseUser.uid.toString(),binding.editFullname.text.toString(),
        firebaseUser.email.toString(),binding.editUsername.text.toString(),binding.editBio.text.toString(),
        imageurl)

        database.reference.child("Users").child(firebaseUser.uid)
            .setValue(user).addOnSuccessListener {
                Toast.makeText(this,"Data sucessfully inserted",Toast.LENGTH_SHORT).show()
              startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null)
        {
            if (data.data !=null)
            {
                imageUri=data.data!!
                binding.editProfileImage.setImageURI(imageUri)
            }
        }
    }

}

