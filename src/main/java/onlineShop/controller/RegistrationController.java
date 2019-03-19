package onlineShop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

//**这个类为用户注册的controller
@Controller
public class RegistrationController {

	@Autowired
	private CustomerService customerService; //自动匹配一个bean中的customerService
	
	//这个函数map到get注册页面
	@RequestMapping(value = "/customer/registration",method=RequestMethod.GET)
	public ModelAndView getRegistrationForm(){
		return new ModelAndView("register","customer",new Customer()); //返回一个view(viewName,modelName(配置好的model),model的object)
	}
	//这个函数map到post注册信息页面
	@RequestMapping(value = "/customer/registration",method=RequestMethod.POST)//识别输入的customer格式是否符合
	public ModelAndView registerCustomer(@Valid @ModelAttribute(value="customer")Customer customer,BindingResult result){
		ModelAndView modelAndView = new ModelAndView();
		if(result.hasErrors()){
			modelAndView.setViewName("register"); //格式不合,转跳回去
			return modelAndView;
		}
		customerService.addCustomer(customer);
		
		modelAndView.addObject("registrationSuccess","Registered Successfully. Login using username and password");
		//注册完成,转入到登陆页面
		modelAndView.setViewName("login");
		return modelAndView;
	}
}
