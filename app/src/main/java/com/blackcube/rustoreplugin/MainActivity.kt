package com.blackcube.rustoreplugin

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.uploadButton)

//        val publish = PublishAppTask()
//
//        button.setOnClickListener {
//            lifecycleScope.launch {
//                withContext(Dispatchers.IO) {
//                    publish.file = getApkFileFromAssets(this@MainActivity, "app-release.apk")
//                    publish.executeTask()
//                }
//            }
//        }
    }

    fun getApkFileFromAssets(context: Context, fileName: String): File {
        // Создаём временный файл в кэше приложения
        val tempFile = File(context.cacheDir, fileName)

        // Открываем APK файл из assets и копируем его в кэш
        context.assets.open(fileName).use { inputStream ->
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        // Возвращаем File объект временного файла
        return tempFile
    }

}