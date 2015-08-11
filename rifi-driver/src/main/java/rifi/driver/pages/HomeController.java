package rifi.driver.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

//    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap model) {
        model.addAttribute("user", request.getRemoteAddr());
        return "index";
    }

}
