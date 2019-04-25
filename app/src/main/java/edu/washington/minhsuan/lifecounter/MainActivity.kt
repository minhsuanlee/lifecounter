package edu.washington.minhsuan.lifecounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val numOfBtns = 16
    private val numOfPlayers = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBtns()
    }

    private fun getTextViews():Array<TextView?> {
        val lifeArr = arrayOfNulls<TextView>(numOfPlayers)
        for (i in 1..4) {
            var txtId = "txtLife$i"
            val resID = resources.getIdentifier(txtId, "id", getString(R.string.packageName))
            lifeArr[i - 1] = findViewById(resID)
        }
        return lifeArr
    }

    private fun setupBtns(): Array<Button?> {
        val clickListener = View.OnClickListener {
            when (it.id) {
                R.id.btn11, R.id.btn21, R.id.btn31, R.id.btn41 -> updateLife(-5, it.id)
                R.id.btn12, R.id.btn22, R.id.btn32, R.id.btn42 -> updateLife(-1, it.id)
                R.id.btn13, R.id.btn23, R.id.btn33, R.id.btn43 -> updateLife(1, it.id)
                else -> updateLife(5, it.id)
            }
        }

        val btnArr = getBtns()

        for (btn in btnArr) {
            btn?.setOnClickListener(clickListener)
        }

        return btnArr
    }

    private fun updateLife(diff: Int, btnId: Int) {
        val txtArr = getTextViews()
        when (btnId) {
            R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14 -> update(diff, txtArr[0], 1)
            R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24 -> update(diff, txtArr[1], 2)
            R.id.btn31, R.id.btn32, R.id.btn33, R.id.btn34 -> update(diff, txtArr[2], 3)
            else -> update(diff, txtArr[3], 4)
        }
    }

    private fun update(diff: Int, lifeId: TextView?, player: Int) {
        val text = getString(R.string.life_remain)
        var beforeLife = lifeId!!.text.substring(text.length + 1).toInt()
        var afterLife = beforeLife + diff
        if (afterLife > 0) {
            var newLife = getString(R.string.life_remain_x, afterLife)
            lifeId.setText(newLife)
        } else {
            disableBtns(player)
            var newLife = getString(R.string.life_remain_x, 0)
            lifeId.setText(newLife)
            val lostTxt = findViewById<TextView>(R.id.txtLost)
            lostTxt.setText(getString(R.string.lost, player))
        }
    }

    private fun getBtns(): Array<Button?> {
        val btnArr = arrayOfNulls<Button>(numOfBtns)

        for (i in 1..4) {
            for (j in 1..4) {
                var btnId = "btn$i$j"
                val resID = resources.getIdentifier(btnId, "id", getString(R.string.packageName))
                btnArr[(i - 1) * 4 + (j - 1)] = findViewById(resID)
            }
        }

        return btnArr
    }

    private fun disableBtns(player: Int) {
        val btnArr = getBtns()
        for (i in 1..4) {
            btnArr[(player - 1) * 4 + (i - 1)]!!.isEnabled = false
        }
    }
}
