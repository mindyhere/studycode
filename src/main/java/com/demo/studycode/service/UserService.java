package com.demo.studycode.service;

import com.demo.studycode.dto.MailDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder pwEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public String findUserEmail(String name, String phone) {
        Optional<User> user = userRepository.findByNameAndPhone(name, phone);
        if (!user.isPresent()) return "fail";

        String email = user.get().getEmail();
        return email;
    }

    public String resetPasswd(Map<String, Object> map) {

        String email = (String) map.get("email");
        String name = map.get("name").toString();
        String phone = (String) map.get("phone");
        Optional<User> user = userRepository.findByEmailAndNameAndPhone(email, name, phone);

        if (!user.isPresent()) return "fail";

        String tempPasswd = MailService.getTempCode();
        String encodedPwd = pwEncoder.encode(tempPasswd);
        user.get().setPasswd(encodedPwd);
        userRepository.save(user.get());

        MailDTO mailDto = mailService.setTempCodeEmail(email, tempPasswd);
        String result = mailService.sendEmail(mailDto);

        return result;

    }
}
