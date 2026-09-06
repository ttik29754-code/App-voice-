package com.example.voiceopen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var commandInput: EditText
    private lateinit var statusText: TextView

    private val appPackages = mapOf(
        "whatsapp" to "com.whatsapp",
        "youtube" to "com.google.android.youtube",
        "camera" to "com.android.camera",
        "gmail" to "com.google.android.gm",
        "facebook" to "com.facebook.katana",
        "instagram" to "com.instagram.android",
        "chrome" to "com.android.chrome",
        "maps" to "com.google.android.apps.maps",
        "settings" to "com.android.settings",
        "gallery" to "com.google.android.apps.photos",
        "playstore" to "com.android.vending",
        "play store" to "com.android.vending"
    )

    private val speechLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""
            commandInput.setText(spokenText)
            handleCommand(spokenText)
        }
    }

    private val micPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startVoiceRecognition() else
            Toast.makeText(this, "Mic permission chahiye voice ke liye", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commandInput = findViewById(R.id.commandInput)
        statusText = findViewById(R.id.statusText)

        findViewById<Button>(R.id.openButton).setOnClickListener {
            val text = commandInput.text.toString()
            if (text.isBlank()) {
                statusText.text = "Pehle kuch likhein ya bolein"
            } else {
                handleCommand(text)
            }
        }

        findViewById<Button>(R.id.micButton).setOnClickListener {
            checkPermissionAndListen()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener { openApp("whatsapp") }
        findViewById<Button>(R.id.btnYoutube).setOnClickListener { openApp("youtube") }
        findViewById<Button>(R.id.btnCamera).setOnClickListener { openApp("camera") }
        findViewById<Button>(R.id.btnGmail).setOnClickListener { openApp("gmail") }
        findViewById<Button>(R.id.btnSettings).setOnClickListener { openApp("settings") }
        findViewById<Button>(R.id.btnFacebook).setOnClickListener { openApp("facebook") }
    }

    private fun checkPermissionAndListen() {
        val hasPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            startVoiceRecognition()
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur-PK")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Bolein, kaunsi app kholni hai?")
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Voice recognition available nahi hai is device par", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleCommand(text: String) {
        val lower = text.lowercase()
        statusText.text = "Command: \"$text\""

        val matchedApp = appPackages.keys.firstOrNull { lower.contains(it) }

        if (matchedApp != null) {
            openApp(matchedApp)
        } else {
            statusText.text = "Samajh nahi aaya: \"$text\". Try: \"whatsapp kholo\""
        }
    }

    private fun openApp(appKey: String) {
        val packageName = appPackages[appKey] ?: return
        val pm = packageManager
        val launchIntent = pm.getLaunchIntentForPackage(packageName)

        if (launchIntent != null) {
            statusText.text = "$appKey khol raha hoon..."
            startActivity(launchIntent)
        } else {
            statusText.text = "$appKey installed nahi hai. Play Store khol raha hoon..."
            try {
                val marketIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
                startActivity(marketIntent)
            } catch (e: Exception) {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
                startActivity(webIntent)
            }
        }
    }
}
