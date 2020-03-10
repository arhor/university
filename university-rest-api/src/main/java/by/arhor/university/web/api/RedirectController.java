//package by.arhor.university.web.api;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class RedirectController {
//
//  // Match everything without a suffix (so not a static resource)
//  @RequestMapping(value = "/**/{path:[^.]*}")
//  public String redirect() {
//    // Forward to home page so that route is preserved.
//    return "forward:/";
//  }
//}
