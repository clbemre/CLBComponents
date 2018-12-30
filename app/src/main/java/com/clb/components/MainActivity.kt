package com.clb.components

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import clb.com.clbview.views.clbdialog.*
import com.clb.components.adapter.AdapterSpinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clbEditText.setInfoMessage("Info", "Info Message")
        clbEditTextBF.setInfoMessage("Info BF", "Info Message BF")

        initCLBSpinner()

        initCLBDialogs()

    }

    private fun initCLBSpinner() {
        val list = ArrayList<String>()
        list.add("Please Select")
        clbSpinner.setCardText(list[0])
        for (i in 0..10) {
            list.add("item : $i")
        }
        val adapter = AdapterSpinner(this, list)
        clbSpinner.setDropDown(adapter, AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicked Position : $position", Toast.LENGTH_SHORT).show()
            clbSpinner.dismissPopup()
            clbSpinner.setCardText(list[position])
            clbSpinner.setError()
        })
        clbSpinner.setCardOnClickListener(View.OnClickListener {
            clbSpinner.showPopup()
        })

        clbSpinner.setCardOnLongCLickListener(View.OnLongClickListener {
            clbSpinner.showPopup()
            true
        })
    }

    private fun initCLBDialogs() {
        btnSuccessDialog.setOnClickListener {
            CLBDialog.Builder.clbSuccessDialog(this, "Success", "Message",
                "Ok", {
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                }, "Cancel", {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }, "Neutral", {
                    Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
                })
        }

        btnWarningDialog.setOnClickListener {
            CLBDialog.Builder.clbWarningDialog(this, "Warning", "Message",
                "Ok", {
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                }, "Cancel", {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }, "Neutral", {
                    Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
                })
        }

        btnQuestionDialog.setOnClickListener {
            CLBDialog.Builder.clbQuestionDialog(this, "Question", "Message",
                "Ok", {
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                }, "Cancel", {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }, "Neutral", {
                    Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
                })
        }

        btnErrorDialog.setOnClickListener {
            CLBDialog.Builder.clbErrorDialog(this, "Error", "Message",
                "Ok", {
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                }, "Cancel", {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }, "Neutral", {
                    Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
                })
        }

        btnCustomDialog.setOnClickListener {
            CLBDialog.Builder(this)
                .withTitle("Custom Dialog")
                .withMessage("Custom Dialog Style Message")
                .background(R.color.md_purple_50)
                .image(R.drawable.ic_android_black_24dp)
                .imageTint(R.color.md_purple_100)
                .titleColor(R.color.md_purple_500)
                .messageColor(R.color.md_purple_400)
                .positiveButtonColor(R.color.md_purple_700)
                .negativeButtonColor(R.color.md_purple_400)
                .neutralButtonColor(R.color.md_purple_300)
                .buttonType(CLBDialog.ButtonType.HORIZONTAL)
                .withPositiveButton("Positive Button") {
                    Toast.makeText(this, "Positive", Toast.LENGTH_SHORT).show()
                }.withNegativeButton("Negative Button") {
                    Toast.makeText(this, "Negative", Toast.LENGTH_SHORT).show()
                }.withNeutralButton("Neutral Button") {
                    Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
                }
                .show(CLBDialog.Type.CUSTOM, true)
        }
    }
}
