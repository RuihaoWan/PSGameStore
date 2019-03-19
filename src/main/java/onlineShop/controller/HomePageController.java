package onlineShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Product;
import onlineShop.service.ProductService;

@Controller
public class HomePageController {
	@Autowired
	private ProductService productService;
	//这个类mapping了用户get请求root/login,返回prefix+index+suffix的文件
	@RequestMapping(value = {"/index","/",""}, method = RequestMethod.GET)
	public ModelAndView returnHomePage(){ //return String original
		List<Product> products = productService.getAllProducts();
		return new ModelAndView("index","products",products);
		//return "index";
	}
	
	//这个类mappping root/login?error=..&logout=...,return 一个login的view,带上了error与logout的信息
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value="error",required=false)
			String error,
			@RequestParam(value="logout",required=false)
			String logout){
		ModelAndView modelAndView = new ModelAndView();
		if(error!=null){
			modelAndView.addObject("error","Invalid username and Password");
		}
		if(logout!=null){
			modelAndView.addObject("logout","You have logged out successfully");
		}
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping("/aboutus")
	public String sayAbout(){
		return "aboutUs";
	}
}
