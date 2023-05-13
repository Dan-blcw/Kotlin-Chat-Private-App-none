package com.dan.chatpapp

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

final class MemoryData {
    companion object {
        fun savaData(data: String, context: Context) {
            try {
                val fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
                fileOutputStream.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }

        fun getData(context: Context): String {
            var data = ""
            try {
                val fis = context.openFileInput("datata.txt")
                val isr = InputStreamReader(fis)
                val bufferedReader = BufferedReader(isr)
                var sb = StringBuilder()
                var line: String
//            for(String line=bufferedReader.readLine();line!=null;line=bufferedReader.readLine())
//            {
//                sb.append(line)
//            }
//            while((line = bufferedReader.readLine()) != null){
//                line = bufferedReader.readLine()
//                sb.append(line)
//            }
                bufferedReader.lines().forEachOrdered { line -> sb.append(line) }
                data = sb.toString()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return data
        }
    }
}