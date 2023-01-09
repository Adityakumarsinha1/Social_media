package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.socialmedia.databinding.ActivityMainBinding
import com.example.socialmedia.fragment.*
import com.example.socialmedia.util.UserUtil
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment as fragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Bottom Navigation Code
     * */
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var notificationFragment: NotificationFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var addpostFragment: AddpostFragment

    private val fragments: Array<fragment>
        get() = arrayOf(homeFragment,notificationFragment,searchFragment,profileFragment,addpostFragment)

    private var selectedIndex = 0
    private val selectedFragment get() = fragments[selectedIndex]

    /**
     * Method to change the fragments
     * */

    private fun selectFragment(selectedFragment: fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser==null)
        {
            //forwarding to login page
            startActivity(Intent(this, LoginActivity::class.java))
        }
        UserUtil.getCurrentUser()

        if (savedInstanceState == null) { // Not configuration change
            homeFragment = HomeFragment()
            searchFragment = SearchFragment()
            addpostFragment = AddpostFragment()
            notificationFragment = NotificationFragment()
            profileFragment = ProfileFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.flFragment, homeFragment , HOME)
                .add(R.id.flFragment, searchFragment , SEARCH)
                .add(R.id.flFragment, addpostFragment, ADDPOST )
                .add(R.id.flFragment, notificationFragment, NOTIFICATION)
                .add(R.id.flFragment, profileFragment, PROFILE)
                .commit()
        } else { // Configuration change happens  .....fragment_container
            homeFragment =
                supportFragmentManager.findFragmentByTag(HOME) as HomeFragment
            searchFragment=
                supportFragmentManager.findFragmentByTag(SEARCH) as SearchFragment
            notificationFragment =
                supportFragmentManager.findFragmentByTag(NOTIFICATION) as NotificationFragment
            addpostFragment=
                supportFragmentManager.findFragmentByTag(ADDPOST) as AddpostFragment
            profileFragment =
                supportFragmentManager.findFragmentByTag(PROFILE) as ProfileFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, selectedIndex)
        }
        selectFragment(selectedFragment)

        /**
         * Setting Listener for Bottom Navigation Change by the user.
         * */
        binding.navview.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_home -> homeFragment
                R.id.navigation_addpost -> addpostFragment
                R.id.navigation_profile -> profileFragment
                R.id.navigation_search -> searchFragment
                R.id.navigation_notifications -> notificationFragment
            else -> throw IllegalArgumentException("Unexpected itemId")

            }
            selectFragment(fragment)
            true
        }
    }

    override fun onBackPressed() {
        if (selectedIndex != 0) {
            binding.navview.selectedItemId = R.id.navigation_home
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }

}

private const val HOME = "HOME"
private const val SEARCH = "SEARCH"
private const val PROFILE = "PROFILE"
private const val ADDPOST = "ADDPOST"
private const val NOTIFICATION = "NOTIFICATION"
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
