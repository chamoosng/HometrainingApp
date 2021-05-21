package com.example.healthmyself.Fragment

import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.healthmyself.Activity.LoginActivity
import com.example.healthmyself.Dialog.AboutDialog
import com.example.healthmyself.Dialog.AccountDeleteDialog
import com.example.healthmyself.Dialog.AlarmDialog
import com.example.healthmyself.R
import com.example.healthmyself.Service.AlarmReceiver
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FragmentMainSetting : Fragment() {
    var uid : String? = ""
    var alarminfo : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_main_setting, null)
        val text_uid = v.findViewById<TextView>(R.id.text_uid)
        val btn_logout = v.findViewById<TextView>(R.id.btn_logout)
        val text_alarminfo = v.findViewById<TextView>(R.id.alarminfo_txt)
        val text_about = v.findViewById<TextView>(R.id.txt_about)
        alarminfo = text_alarminfo
        val btn_account_delete = v.findViewById<TextView>(R.id.btn_account_delete)
        val check_alarm = v.findViewById<CheckBox>(R.id.alarm_check)
        btn_logout.setOnClickListener{ signoutAccount(v) }
        btn_account_delete.setOnClickListener{ showDeleteDialog() }

        val load = requireContext().getSharedPreferences("pref", 0)
        var existAlarm = load.getBoolean("alarm", false)
        Log.d("e", existAlarm.toString())
        if(existAlarm) check_alarm.toggle()

        check_alarm.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) moveToSetAlarm()
            else
            {
                Toast.makeText(requireContext(), "アラームをキャンセルします。", Toast.LENGTH_SHORT).show()
                cancelAlarm()
            }
        }
        text_about.setOnClickListener{ moveToAbout() }
        getUID();
        text_uid.setText("おはようございます。" + uid + "様")
        updateAlarminfo()

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                updateAlarminfo()
            }
        }
    }

    private fun signoutAccount(v: View?){
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    moveToMainActivity()
                    Toast.makeText(requireContext(), "ログアウトしました。", Toast.LENGTH_SHORT).show()
                }
    }
    private fun deleteAccount(){
        AuthUI.getInstance()
                .delete(requireContext())
                .addOnCompleteListener {
                    moveToMainActivity()
                    Toast.makeText(requireContext(), "アカウントの削除をしました。", Toast.LENGTH_SHORT).show()
                }
    }
    private fun showDeleteDialog() {
        AccountDeleteDialog().apply {
            addAccountDeleteDialogInterface(object :
                    AccountDeleteDialog.AccountDeleteDialogInterface {
                override fun delete() {
                    deleteAccount()
                }

                override fun cancelDelete() {
                }
            })
        }.show(requireFragmentManager(), "")
    }
    private fun moveToMainActivity(){
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun moveToSetAlarm(){
        val intent = Intent(requireContext(), AlarmDialog::class.java)
        startActivityForResult(intent, 1)
    }

    private fun cancelAlarm(){
        val am = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if(sender != null){
            am.cancel(sender)
            sender.cancel()
            val save: SharedPreferences = requireContext().getSharedPreferences("pref", 0)
            val edit = save.edit()
            edit.putBoolean("alarm", false)
            val save2: SharedPreferences = requireContext().getSharedPreferences("daily alarm", 0)
            val edit2 = save2.edit()
            edit2.remove("Alarminfo")
            edit.apply()
            edit2.apply()
            updateAlarminfo()
        }
    }

    private fun moveToAbout(){
        val intent = Intent(requireContext(), AboutDialog::class.java)
        startActivity(intent)
    }

    public fun updateAlarminfo()
    {
        val save: SharedPreferences = requireContext().getSharedPreferences("daily alarm", 0)
        val str = save.getString("Alarminfo", "")
        if(str == "")
            alarminfo?.setText("アラームがありません。")
        else
            alarminfo?.setText("次のアラームは"+str.toString()+"です。")
    }

    private fun getUID()
    {
        val user : FirebaseUser = FirebaseAuth.getInstance().currentUser;
        Log.d("e", "getUID: " + user.displayName)
        uid = user.displayName.toString();
    }
}