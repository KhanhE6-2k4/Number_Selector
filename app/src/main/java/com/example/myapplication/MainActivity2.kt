//
//package com.example.myapplication
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.myapplication.ui.theme.MyApplicationTheme
//import android.widget.Button
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var tvDisplay: TextView
//    private lateinit var tvHistory: TextView
//
//    // Accumulator lưu giá trị tạm thời của calculator
//    private var acc: Long? = null
//
//    // Operator chờ thực hiện: "+", "-", "*", "/"
//    private var pendingOP: String? = null
//
//    // Giá trị đang nhập (hiển thị)
//    private var currentEntry: String = "0"
//
//    private var pressEqualBefore: Boolean = false
//
//    // Flag cho biết người dùng đang nhập số (nếu false -> màn hình hiển thị kết quả hoặc 0)
//    private var userIsTyping: Boolean = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.calculator_layout_2)
//
//        tvDisplay = findViewById(R.id.tvDisplay)
//        tvHistory = findViewById(R.id.tvHistory)
//        tvDisplay.text = "0"
//
//        // Digit buttons
//        val digitIds = listOf(
//            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
//            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
//        )
//
//        for (id in digitIds) {
//            findViewById<Button>(id).setOnClickListener { digitPressed((it as Button).text.toString()) }
//        }
//
//        // Operators
//        findViewById<Button>(R.id.btnAdd).setOnClickListener { operatorPressed("+") }
//        findViewById<Button>(R.id.btnSub).setOnClickListener { operatorPressed("-") }
//        findViewById<Button>(R.id.btnMul).setOnClickListener { operatorPressed("*") }
//        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorPressed("/") }
//
//        findViewById<Button>(R.id.btnEq).setOnClickListener { equalsPressed() }
//
//        // Special buttons
//        findViewById<Button>(R.id.btnC).setOnClickListener { clearAll() } // C
//        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() } // CE
//        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() } // BS
//    }
//
//    private fun updateDisplay() {
//        tvDisplay.text = currentEntry
//        updateHistory()
//    }
//
//    private fun updateHistory() {
//        // Hiển thị phép toán đang chờ
//        if (acc != null && pendingOP != null) {
//            tvHistory.text = "$acc $pendingOP"
//        }
//        else {
//            tvHistory.text = ""
//        }
//    }
//
//    private fun digitPressed(d: String) {
//        // Integer only
//        if (!userIsTyping) {
//            // Nếu bắt đầu nhập mới
//            currentEntry = if (currentEntry == "-0") {
//                "-$d"
//            }
//            else {
//                d
//            }
//        }
//        else {
//            // Nếu đang nhập tiếp
//            if (currentEntry == "0" || currentEntry == "-0") {
//                // Thay thế 0 hoặc -0 bằng chữ số mới
//                currentEntry = if (currentEntry.startsWith("-")) "-$d" else d
//            }
//            else {
//                currentEntry += d
//            }
//        }
//        userIsTyping = true
//        pressEqualBefore = false
//        updateDisplay()
//    }
//
//    private fun backspace() {
//        if (!userIsTyping) {
//            // Nếu đang không nhập, đưa entry về 0
////            currentEntry = "0"
////            userIsTyping = false
////            updateDisplay()
//            if (pressEqualBefore)
//                tvHistory.text = ""
//            return
//        }
//
//
//        if (currentEntry.length <= 1 || (currentEntry.length == 2 && currentEntry.startsWith("-"))) {
//            currentEntry = "0"
//            userIsTyping = false
//        }
//        else {
//            currentEntry = if (currentEntry.length > 1) {
//                currentEntry.substring(0, currentEntry.length - 1)
//            } else {
//                "0"
//            }
//        }
//        pressEqualBefore = false
//        updateDisplay()
//    }
//
//    private fun clearEntry() {
//        // CE: xóa giá trị toán hạng hiện tại về 0, giữ acc và pendingOp
//        currentEntry = "0"
//        userIsTyping = false
//        pressEqualBefore = false
//        updateDisplay()
//    }
//
//    private fun clearAll() {
//        // C: xóa phép toán, nhập lại từ đầu
//        currentEntry = "0"
//        acc = null
//        pendingOP = null
//        userIsTyping = false
//        pressEqualBefore = false
//        tvHistory.text = ""
//        updateDisplay()
//    }
//
//    private fun operatorPressed(op: String) {
//        val value = currentEntry.toLongOrNull()
//        if (value == null) {
//            showErrorAndReset()
//            return
//        }
//
//        if (acc == null) {
//            acc = value
//        }
//        else {
//            if (userIsTyping) {
//                val result = compute(acc!!, value, pendingOP)
//                if (result == null) {
//                    showErrorAndReset();
//                    return;
//                }
//                else {
//                    acc = result
//                }
//            }
//        }
//        currentEntry = acc.toString()
//        userIsTyping = false
//        pendingOP = op
//        pressEqualBefore = false
//        updateDisplay()
//
//    }
//
//    private fun equalsPressed() {
//        val value = currentEntry.toLongOrNull()
//        if (value == null) {
//            showErrorAndReset()
//            return
//        }
//
//        if (acc != null && pendingOP != null) {
//            tvHistory.text = "$acc $pendingOP $value ="
//            val result = compute(acc!!, value, pendingOP)
//            if (result == null) {
//                showErrorAndReset()
//                return
//            }
//            currentEntry = result.toString()
//            tvDisplay.text = currentEntry
//            acc = null
//            pendingOP = null
//            userIsTyping = false
//        }
//        else {
//            // Không có operator, chỉ giữ value
//            currentEntry = value.toString()
//            updateDisplay()
//            userIsTyping = false
//        }
//        pressEqualBefore = true
//    }
//
//    private fun compute(a: Long, b: Long, op: String?): Long? {
//        // return 0 nếu lỗi -> overflow, chia cho 0
//        try {
//            return when (op) {
//                "+" -> safeAdd(a, b)
//                "-" -> safeSub(a, b)
//                "*" -> safeMul(a, b)
//                "/" -> {
//                    if (b == 0L)
//                        return null
//                    a / b
//                }
//                else -> null
//
//            }
//        } catch (e: Exception) {
//            return null
//        }
//    }
//
//    // Kiểm tra overflow cho phép tính integer -> trả về null nếu overflow
//    private fun safeAdd(a: Long, b: Long): Long? {
//        val res = a + b
//        if ((b > 0 && a > Long.MAX_VALUE - b) || (b < 0 && a < Long.MIN_VALUE - b)) {
//            return null
//        }
//        return res
//    }
//
//    private fun safeSub(a: Long, b: Long): Long? {
//        val res = a - b
//        if ((b < 0 && a > Long.MAX_VALUE+ b) || (b > 0 && a < Long.MIN_VALUE + b)) {
//            return null
//        }
//        return res
//    }
//
//    private fun safeMul(a: Long, b: Long): Long? {
//        if (a == 0L || b == 0L) {
//            return 0L;
//        }
//        if (a == Long.MIN_VALUE && b == -1L) {
//            return null // Overflow
//        }
//        val res = a * b
//        if (res / b != a) {
//            return null
//        }
//        return res
//    }
//
//    private fun showErrorAndReset() {
//        tvDisplay.text = "Math error"
//        // Reset trạng thái
//        tvHistory.text = ""
//        acc = null
//        pendingOP = null
//        currentEntry = "0"
//        userIsTyping = false
//    }
//}
//
////@Composable
////fun Greeting(name: String, modifier: Modifier = Modifier) {
////    Text(
////        text = "Hello $name!",
////        modifier = modifier
////    )
////}
////
////@Preview(showBackground = true)
////@Composable
////fun GreetingPreview() {
////    MyApplicationTheme {
////        Greeting("Android")
////    }
////}