package com.example.semiprojectjpa.controller;

import com.example.semiprojectjpa.dto.MemberDto;
import com.example.semiprojectjpa.entity.Member;
import com.example.semiprojectjpa.form.LoginForm;
import com.example.semiprojectjpa.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("member/login");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }


    @GetMapping("/findpw")
    public ModelAndView findPasswordView (ModelAndView mav) {
        mav.setViewName("member/findpw");
        return mav;
    }

    @PostMapping("/findpw")
    public ModelAndView findPassword (@RequestParam(value = "username") String username, ModelAndView mav){
        String pw = memberService.findPw(username);
        mav = new ModelAndView("/member/checkpw");
        mav.addObject("password", pw);
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registerView (ModelAndView mav) {
        mav.setViewName("member/signup");
        mav.addObject("memberDto", new MemberDto());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView register (@ModelAttribute @Validated MemberDto dto, BindingResult result) {
        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView("member/signup");
            mav.addObject("memberDto", dto);
            return mav;
        }
        if(!dto.getPassword().equals(dto.getCheckpw())) {
            ModelAndView mav = new ModelAndView("member/signup");
            mav.addObject("message","비밀번호가 같지 않습니다.");
            mav.addObject("memberDto", dto);
            return mav;
        }
        if(!dto.isCheckid()) {
            System.out.println("---2. 아이디 boolean값은---");
            System.out.println(dto.isCheckid());
            ModelAndView mav = new ModelAndView("member/signup");
            mav.addObject("msg", "중복 아이디를 체크하십시오");
            mav.addObject("memberDto", dto);
            return mav;
        }
        memberService.join(dto);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/username_check_form")
    public ModelAndView idCheckFormView(@RequestParam(value = "username", defaultValue = "", required = false) String username, ModelAndView mav) {
        mav.addObject("username", username);
        boolean check = memberService.checkId(username);
        System.out.println(check);
        if(check) {
            mav.addObject("check", 0);
        } else {
            mav.addObject("check", 1);
        }
        mav.setViewName("member/idcheck");
        return mav;
    }

    @RequestMapping(value = "/checkid")
    public ModelAndView idCheck(@RequestParam(value = "username", defaultValue = "", required = false) String username, RedirectAttributes redirectAttributes) {
        System.out.println(username);
        MemberDto memberDto = new MemberDto();
        memberDto.setCheckid(true);
        memberDto.setUsername(username);
        System.out.println("---1. 아이디 boolean값은---");
        System.out.println(memberDto.isCheckid());
        ModelAndView mav = new ModelAndView("member/signup");
        mav.addObject("memberDto", memberDto);
        return mav;

    }

    @GetMapping("/member/update/{username}")
    public ModelAndView updateMemberView (@PathVariable("username") String username, ModelAndView mav) {
        Optional<Member> memberOptional = memberService.findByUsername(username);
        if(memberOptional.isPresent()) {
            MemberDto memberDto = memberOptional.get().toDto();
            mav = new ModelAndView("/member/update");
            mav.addObject("memberDto", memberDto);
        }
        return mav;
    }

    @PostMapping("/member/update")
    public ModelAndView updateMember (@ModelAttribute @Validated MemberDto dto, BindingResult result, ModelAndView mav) {
        if(result.hasErrors()) {
            mav.addObject("memberDto", dto);
            return mav;
        }
        if(!dto.getPassword().equals(dto.getCheckpw())) {
            mav.addObject("message","비밀번호가 같지 않습니다.");
            mav.addObject("memberDto", dto);
            return mav;
        }
        memberService.update(dto);
        return new ModelAndView("redirect:/logout");
    }

}
