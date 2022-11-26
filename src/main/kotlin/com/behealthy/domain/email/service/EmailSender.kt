package com.behealthy.domain.email.service

import com.behealthy.domain.email.dto.SendEmailRequest
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSender(
    private val javaMailSender: JavaMailSender
) {

    fun send(request: SendEmailRequest) {
        javaMailSender.send(SimpleMailMessage().apply {
            setTo(request.receiverEmail)
            setSubject(request.title)
            setText(request.content)
        })
    }
}