package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var etNum: EditText
    private lateinit var lvResult: ListView
    private lateinit var tvMsg: TextView
    private lateinit var radios: List<RadioButton>
    private lateinit var adapter: ArrayAdapter<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNum = findViewById(R.id.etNumber)
        lvResult = findViewById(R.id.listView)
        tvMsg = findViewById(R.id.tvMessage)

        radios = listOf(
            findViewById(R.id.rbLe),
            findViewById(R.id.rbChan),
            findViewById(R.id.rbNguyenTo),
            findViewById(R.id.rbChinhPhuong),
            findViewById(R.id.rbHoanHao),
            findViewById(R.id.rbFibo)
        )

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<Long>())
        lvResult.adapter = adapter

        etNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { update() }
            override fun beforeTextChanged(s: CharSequence?, st: Int, cnt: Int, aft: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, cnt: Int) {}
        })

        // Quản lý chọn/bỏ chọn RadioButton
        radios.forEach { rb ->
            rb.setOnClickListener {
                radios.forEach { it.isChecked = false }
                rb.isChecked = true
                update()
            }
        }

        showMsg("Nhập số nguyên để xem kết quả")
    }

    private fun update() {
        val txt = etNum.text.toString().trim()

        if (txt.isEmpty()) {
            showMsg("Nhập số nguyên để xem kết quả")
            return
        }

        val n = txt.toLongOrNull()
        if (n == null || n <= 0) {
            showMsg("Vui lòng nhập số nguyên dương")
            return
        }

        val res = when {
            radios[0].isChecked -> getOdd(n)
            radios[1].isChecked -> getEven(n)
            radios[2].isChecked -> getPrimes(n)
            radios[3].isChecked -> getSquares(n)
            radios[4].isChecked -> getPerfects(n)
            radios[5].isChecked -> getFibos(n)
            else -> emptyList()
        }

        if (res.isEmpty()) {
            showMsg("Không có số nào thỏa mãn")
        } else {
            tvMsg.visibility = TextView.GONE
            adapter.clear()
            adapter.addAll(res)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showMsg(msg: String) {
        adapter.clear()
        adapter.notifyDataSetChanged()
        tvMsg.text = msg
        tvMsg.visibility = TextView.VISIBLE
    }

    private fun getOdd(n: Long) = (1 until n step 2).toList()

    private fun getEven(n: Long) = (2 until n step 2).toList()

    private fun getPrimes(n: Long) = (2 until n).filter { isPrime(it) }

    private fun getSquares(n: Long): List<Long> {
        val res = mutableListOf<Long>()
        var i = 1L
        while (i * i < n) {
            res.add(i * i)
            i++
        }
        return res
    }

    private fun getPerfects(n: Long): List<Long> {
        val lim = minOf(n, 100000L)
        if (n > 100000L) {
            Toast.makeText(this, "Giới hạn tìm kiếm: 100,000", Toast.LENGTH_SHORT).show()
        }
        return (2 until lim).filter { isPerfect(it) }
    }

    private fun getFibos(n: Long): List<Long> {
        if (n <= 1) return emptyList()
        val res = mutableListOf<Long>()
        var a = 0L
        var b = 1L
        while (b < n) {
            res.add(b)
            val tmp = a + b
            a = b
            b = tmp
        }
        return res
    }

    private fun isPrime(num: Long): Boolean {
        if (num < 2) return false
        if (num == 2L) return true
        if (num % 2 == 0L) return false
        val r = sqrt(num.toDouble()).toLong()
        for (i in 3..r step 2) {
            if (num % i == 0L) return false
        }
        return true
    }

    private fun isPerfect(num: Long): Boolean {
        if (num <= 1) return false
        var sum = 1L
        val r = sqrt(num.toDouble()).toLong()
        for (i in 2..r) {
            if (num % i == 0L) {
                sum += i
                val other = num / i
                if (other != i) sum += other
            }
        }
        return sum == num
    }
}