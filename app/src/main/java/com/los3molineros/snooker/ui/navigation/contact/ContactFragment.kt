package com.los3molineros.snooker.ui.navigation.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.AppExecutors
import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.databinding.FragmentContactBinding
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ContactFragment : Fragment(R.layout.fragment_contact) {
    lateinit var binding: FragmentContactBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentContactBinding.bind(view)
        CommonFunctions.buttonEffect(binding.btnImprove)
        CommonFunctions.buttonEffect(binding.btnRate)

        binding.btnRate.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.los3molineros.snooker")
                )
            )
        }

        binding.btnImprove.setOnClickListener {
            sendEmail()
        }
    }


    private fun sendEmail() {
        val appExecutors = AppExecutors()

        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication("sergionunez90@gmail.com", "uuefepaxkvalnsho")
                    }
                })


            try {
                debugLog(description = "entro en try")
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                val emailId = binding.txtEmail.text.toString()
                //Setting sender address
                mm.setFrom(InternetAddress("sergionunez90@gmail.com"))
                //Adding receiver
                mm.addRecipient(
                    Message.RecipientType.TO,
                    InternetAddress("sergionunez90@gmail.com")
                )
                //Adding subject
                mm.subject = "Mail desde app snooker"
                //Adding message
                mm.setText("${binding.txtComment.text.toString()} ${binding.txtEmail.text.toString()}")

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    Snackbar.make(binding.root, "E-Mail sent! We will answer you shortly",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                }

            } catch (e: MessagingException) {
                debugLog(description = "entro en catch: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}