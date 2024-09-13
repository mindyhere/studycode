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

    public UserDTO findUserid(String email, String phone) {
        Optional<User> user = userRepository.findByEmailAndPhone(email, phone);
        if (!user.isPresent()) return null;

        UserDTO dto = new UserDTO();
        String userid = user.get().getUserid();
        String name = user.get().getName();
        dto.setUserid(userid);
        dto.setName(name);

        return dto;
    }

    public String resetPasswd(Map<String, Object> map) {

        String userid = (String) map.get("userid");
        String email = (String) map.get("email");
        String phone = (String) map.get("phone");
        Optional<User> user = userRepository.findByUseridAndEmailAndPhone(userid, email, phone);

        if (!user.isPresent()) return "not exist";

        String tempPasswd = MailService.getTempCode();
        String encodedPwd = pwEncoder.encode(tempPasswd);
        user.get().setPasswd(encodedPwd);
        userRepository.save(user.get());

        MailDTO mailDto = mailService.setTempCodeEmail(email, tempPasswd);
        String result = mailService.sendEmail(mailDto);

        return result;

    }
}
