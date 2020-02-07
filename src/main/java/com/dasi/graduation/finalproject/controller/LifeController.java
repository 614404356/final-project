package com.dasi.graduation.finalproject.controller;

import com.dasi.graduation.finalproject.base.ResponseEnvelope;
import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.Life;
import com.dasi.graduation.finalproject.entity.User;
import com.dasi.graduation.finalproject.repository.LifeRepository;
import com.dasi.graduation.finalproject.repository.UserRepository;
import com.dasi.graduation.finalproject.service.LifeService;
import com.dasi.graduation.finalproject.vo.LifeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 15:44
 * @Description
 */
@RestController
@RequestMapping("life")
public class LifeController {

    @Autowired
    private LifeService lifeService;
    @Autowired
    private LifeRepository lifeRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("insert")
    public ResponseEntity<ResponseEnvelope<Life>> insert(Life life){
        User user = userRepository.findByPhone(life.getPhone());
        life.setName(user.getName());
        lifeRepository.save(life);
        ResponseEnvelope<Life> envelope = new ResponseEnvelope<>(life);
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("getPageLife")
    public ResponseEntity<ResponseEnvelope<Page<Life>>> getPageLife(@RequestParam String phone,
                                                                    @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        if (pageable == null){
            pageable = PageRequest.of(0, 11, new Sort(Sort.Direction.DESC, "id"));
        } else {
            if (pageable.getPageNumber() == 0) {
                pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            } else {
                pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(),
                        pageable.getSort());
            }
        }
        Page<Life> lifeList = lifeService.findByPhone(phone,pageable);
        ResponseEnvelope<Page<Life>> envelope = new ResponseEnvelope<>(lifeList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("deleteOne")
    public ResponseEntity<ResponseEnvelope<Void>> deleteOne(@RequestParam Long id)throws Exception{
        lifeService.deleteOne(id);
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("/getPageAll")
    public ResponseEntity<ResponseEnvelope<List<LifeVo>>> getPageAll(@PageableDefault( sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        if (pageable == null){
            pageable = PageRequest.of(0, 5, new Sort(Sort.Direction.DESC, "id"));
        } else {
            if (pageable.getPageNumber() == 0) {
                pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            } else {
                pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(),
                        pageable.getSort());
            }
        }
        Page<Life> page = lifeRepository.findAll(pageable);
        List<LifeVo> lifeVoList = new ArrayList<>();
        List<Life> lifeList = page.getContent();
        for (Life life : lifeList){
            LifeVo lifeVo = new LifeVo();
            User user = userRepository.findByPhone(life.getPhone());
            lifeVo.setUserPhoto(user.getPhoto());
            lifeVo.setName(life.getName());
            lifeVo.setBody(life.getBody());
            lifeVo.setAddress(life.getAddress());
            lifeVo.setDate(life.getDate());
            lifeVo.setId(life.getId());
            lifeVo.setPhoto(life.getPhoto());
            lifeVoList.add(lifeVo);
        }
        ResponseEnvelope<List<LifeVo>> envelope = new ResponseEnvelope<>(lifeVoList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("findByNameLike")
    public ResponseEntity<ResponseEnvelope<List<Life>>> findByNameLike(@RequestParam String name){
        List<Life> lifeList = lifeService.findByNameLike(name);
        ResponseEnvelope<List<Life>> envelope = new ResponseEnvelope<>(lifeList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("findByPhone")
    public ResponseEntity<ResponseEnvelope<List<LifeVo>>> findByPhone(@RequestParam String phone, @PageableDefault( sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        if (pageable == null){
            pageable = PageRequest.of(0, 5, new Sort(Sort.Direction.DESC, "id"));
        } else {
            if (pageable.getPageNumber() == 0) {
                pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            } else {
                pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(),
                        pageable.getSort());
            }
        }
        List<Life> lifeList = lifeService.findByPhone(phone,pageable).getContent();
        List<LifeVo> lifeVoList = new ArrayList<>();
        for (Life life : lifeList){
            LifeVo lifeVo = new LifeVo();
            User user = userRepository.findByPhone(life.getPhone());
            lifeVo.setUserPhoto(user.getPhoto());
            lifeVo.setName(life.getName());
            lifeVo.setBody(life.getBody());
            lifeVo.setAddress(life.getAddress());
            lifeVo.setDate(life.getDate());
            lifeVo.setId(life.getId());
            lifeVo.setPhoto(life.getPhoto());
            lifeVoList.add(lifeVo);
        }
        ResponseEnvelope<List<LifeVo>> envelope = new ResponseEnvelope<>(lifeVoList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("/updatePhoto")
    public ResponseEntity<ResponseEnvelope<String>> updatePhoto(MultipartFile file) throws IOException {
        String photo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        System.out.println(file.getOriginalFilename());
        String phone =  file.getOriginalFilename().substring(0,11);
        String date = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("_") + 1,file.getOriginalFilename().lastIndexOf(".")).replace("+",":");
        System.out.println(phone);
        System.out.println(date);
        File dir = new File("C:/upload/" + photo);
        if(!dir.getParentFile().exists()){
            dir.getParentFile().mkdirs();//创建父级文件路径
            dir.createNewFile();//创建文件
        }
        file.transferTo(dir);
        Life life = lifeRepository.findByDateAndPhone(date,phone);
        lifeRepository.updateOne(photo,life.getId());
        ResponseEnvelope<String> envelope = new ResponseEnvelope(phone);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("findByPhone1")
    public ResponseEntity<ResponseEnvelope<Life>> findByPhone1(@RequestParam String date,@RequestParam String phone){
        Life life = lifeRepository.findByDateAndPhone(date,phone);
        ResponseEnvelope<Life> envelope = new ResponseEnvelope<>(life);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }
}
