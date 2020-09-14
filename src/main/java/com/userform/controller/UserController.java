package com.userform.controller;

import com.userform.dto.PasswordDto;
import com.userform.event.OnRegistrationCompleteEvent;
import com.userform.model.Role;
import com.userform.model.VerificationToken;
import com.userform.service.CaptchaService;
import com.userform.service.UserService;
import com.userform.validation.PasswordDtoValidator;
import com.userform.validation.UserValidator;
import com.userform.model.User;
import com.userform.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * This is controller for displaying pages
 *
 */
@Controller
@SessionAttributes(types = VerificationToken.class)
public class UserController {

    @Value("${google.recaptcha.key.site}")
    private  String siteV3;

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private CaptchaService captchaService;

  @Autowired
  private UserValidator userValidator;

  @Autowired
  private PasswordDtoValidator passwordValidator;

  @Autowired
  ApplicationEventPublisher eventPublisher;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private MessageSource messages;

  @Autowired
  private Environment env;


  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String registration(Model model) {
    model.addAttribute("userForm", new User());
      model.addAttribute("captchaSettings", "6Lez3rkZAAAAAPrqzJDtkYSOPdWgvfZgff4RQCh8");
    return "registration";
  }


  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
    userValidator.validate(userForm, bindingResult);

    String response = request.getParameter("g-recaptcha-response");
    captchaService.processResponse(response);

          if (bindingResult.hasErrors()) {
      return "registration";
    }

    userService.save(userForm);

   // securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

    try {
      String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();;
      eventPublisher.publishEvent(new OnRegistrationCompleteEvent
              (userForm, request.getLocale(), appUrl));
    } catch (Exception me) {
      return "redirect:/error";
    }

    return "redirect:/information";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {
    if (error != null)
      model.addAttribute("error", "Your username and password are invalid.");

    if (logout != null)
      model.addAttribute("message", "You have been logged out successfully.");

    model.addAttribute("newUser", new User());
    return "login";
  }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginInAccount(@Valid @ModelAttribute User newUser) {
        if (newUser != null)
            securityService.autologin(newUser.getUsername(), newUser.getPasswordConfirm());
        return "welcome";
    }

  @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
  public String confirmRegistration(HttpServletRequest request, Model model, HttpSession session, @RequestParam(name="token") String token) {
        session.setAttribute("token",token);
        Locale locale = request.getLocale();
        String result = userService.validateVerificationToken(token);
        if(result.equals("valid")) {
            User user = userService.getUser(token);
            authWithoutPassword(user);
            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
            return "registrationConfirm";
        }

        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return"registrationConfirm";
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.POST)
    public String resendRegistrationToken(HttpServletRequest request, @RequestParam(name="token") String token) {
        VerificationToken newToken = userService.generateNewVerificationToken(token);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return"information";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
  public String welcomePage(Model model) {
      return "welcome";
  }

    @RequestMapping(value = {"/information"}, method = RequestMethod.GET)
    public String informationPage(Model model) {
        return "information";
    }

    @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
    public String errorPage(Model model) {
        return "information";
    }

  @RequestMapping(value = {"/forgetPassword"}, method = RequestMethod.GET)
  public String getForgetPassword(Model model) {
      return "forgetPassword";
  }

  @RequestMapping(value = {"/changePassword"}, method = RequestMethod.GET)
  public String getChangePassword(Model model,  @RequestParam("token") String token, HttpSession session) {
      session.setAttribute("token",token);
      model.addAttribute("passwordDto", new PasswordDto());
      return "changePassword";
  }

    @RequestMapping(value = {"/forgetPassword"}, method = RequestMethod.POST)
    public String postForgetPassword(Model model, HttpServletRequest request, @RequestParam(required=false,name="email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
            model.addAttribute("message", messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        }
        return "forgetPassword";
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
    public String postChangePassword(Model model, HttpSession session, @Valid PasswordDto passwordDto,  BindingResult bindingResult, HttpServletRequest request) {
        passwordValidator.validate(passwordDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "changePassword";
        }
        User user = userService.getUserByPasswordResetToken(session.getAttribute("token").toString());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            model.addAttribute("message", messages.getMessage("message.updatePasswordError", null, request.getLocale()));
            return "changePassword";
        }
        model.addAttribute("message", messages.getMessage("message.updatePasswordSuc", null, request.getLocale()));
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return "changePassword";
    }

    /**
     * These are util methods to constract tokens
     *
     */
    private SimpleMailMessage constructResendVerificationTokenEmail( String contextPath,  Locale locale,  VerificationToken newToken,  User user) {
        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final String message = messages.getMessage("message.resendToken", null, locale);
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail( String contextPath,  Locale locale,  String token,  User user) {
        final String url = contextPath + "/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public void authWithoutPassword(User user) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

