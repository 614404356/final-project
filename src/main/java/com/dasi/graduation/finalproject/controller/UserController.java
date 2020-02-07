package com.dasi.graduation.finalproject.controller;

import com.dasi.graduation.finalproject.base.ResponseEnvelope;
import com.dasi.graduation.finalproject.entity.Life;
import com.dasi.graduation.finalproject.entity.User;
import com.dasi.graduation.finalproject.repository.LifeRepository;
import com.dasi.graduation.finalproject.repository.UserRepository;
import com.dasi.graduation.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/8 0008 13:41
 * @Description
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LifeRepository lifeRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseEnvelope<Integer>> register(@RequestParam String name,
                                                              @RequestParam String phone,
                                                              @RequestParam String password){
        Integer body = 0;
        List<String> phoneList = userService.getAllPhone();
            if (phoneList.contains(phone)){
                body = -1;
                ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(body);
                return new ResponseEntity<>(envelope, HttpStatus.OK);
            }
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setPhone(phone);
            userRepository.save(user);
            ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(body);
            return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseEnvelope<Integer>> login(@RequestParam String phone,
                                                           @RequestParam String password){
        if (phone.equals("15858259401")){
            User user = userService.findByPhone(phone);
            if (password.equals(user.getPassword())){
                ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(2);
                return new ResponseEntity<>(envelope,HttpStatus.OK);
            }
        }
        //0登录成功，-1无该手机号，-2密码不正确,2管理员
        Integer body = 0;
        List<String> phoneList = userService.getAllPhone();
        if (!phoneList.contains(phone)){
            body = -1;
        }else {
            User user = userService.findByPhone(phone);
            if (!user.getPassword().equals(password)){
                body = -2;
            }
        }
        ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(body);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<ResponseEnvelope<Integer>> updatePassword(@RequestParam String phone,
                                                                @RequestParam(required = false) String password){
        User user = userService.findByPhone(phone);
        if (user == null){
            ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(-1);
            return new ResponseEntity<>(envelope,HttpStatus.OK);
        }
        User u = new User();
        u.setId(user.getId());
        u.setPhone(phone);
        u.setPassword(password);
        userRepository.saveAndFlush(u);
        ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(1);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("/findByPhone")
    public ResponseEntity<ResponseEnvelope<User>> findByPhone(@RequestParam String phone){
        User user = userService.findByPhone(phone);
        ResponseEnvelope<User> envelope = new ResponseEnvelope<>(user);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("/updatePhoto")
    public ResponseEntity<ResponseEnvelope<String>> updatePhoto(MultipartFile file) throws IOException {
        String photo = System.currentTimeMillis() + file.getOriginalFilename();
        System.out.println(file.getOriginalFilename());
        String phone =  file.getOriginalFilename().substring(0,11);
        File dir = new File("C:/upload/" + photo);
        if(!dir.getParentFile().exists()){
            dir.getParentFile().mkdirs();//创建父级文件路径
            dir.createNewFile();//创建文件
        }
        file.transferTo(dir);
        User user = userService.findByPhone(phone);
        User u = new User();
        u.setId(user.getId());
        u.setPhoto(photo);
        u.setPhone(phone);
        u.setName(user.getName());
        u.setPassword(user.getPassword());
        userRepository.saveAndFlush(u);
        ResponseEnvelope<String> envelope = new ResponseEnvelope(photo);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("/updateName")
    public ResponseEntity<ResponseEnvelope<Integer>> updateName(@RequestParam String phone,
                                                                @RequestParam String name){
        User user = userService.findByPhone(phone);
        User u = new User();
        u.setId(user.getId());
        u.setPhone(phone);
        u.setPhoto(user.getPhoto());
        u.setPassword(user.getPassword());
        u.setName(name);
        userRepository.saveAndFlush(u);
        ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(1);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<ResponseEnvelope<List<User>>> getUsers(@RequestParam String phone){
        List<User> userList = userRepository.findAll();
        ResponseEnvelope<List<User>> envelope = new ResponseEnvelope<>(userList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<ResponseEnvelope<Integer>> deleteUser(@RequestParam String phone){
        userRepository.deleteUser(phone);
        lifeRepository.deleteByPhone(phone);
        ResponseEnvelope<Integer> envelope = new ResponseEnvelope<>(1);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("/findUserLike")
    public ResponseEntity<ResponseEnvelope<List<User>>> findUserLike(@RequestParam String phone){
        List<User> userList = userService.findByPhoneLike(phone);
        ResponseEnvelope<List<User>> envelope = new ResponseEnvelope<>(userList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }
}
