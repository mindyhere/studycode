package com.demo.studycode.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailDTO {
    private String senderName;
    private String sender;
    private String recipient;
    private String subject;
    private String message;
}
