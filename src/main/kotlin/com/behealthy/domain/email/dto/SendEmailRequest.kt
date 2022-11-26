package com.behealthy.domain.email.dto

data class SendEmailRequest(
    val receiverEmail: String,
    /* 제목 */
    val title: String,
    /* 내용 */
    val content: String
)