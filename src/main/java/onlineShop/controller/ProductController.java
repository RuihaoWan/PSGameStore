package onlineShop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Product;
import onlineShop.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	//A controller work-flow
	//1. map which request
	//2. do what(service -> dao ->(hibernate model)-> db)
	//3. show which view
	@RequestMapping(value = "/getAllProducts",method=RequestMethod.GET)
	public ModelAndView getAllProducts(){
		List<Product> products = productService.getAllProducts();
		return new ModelAndView("productList","products",products);
	}
	//getAllProducts -> ProductService -> productDao -> get the product
	
	
	@RequestMapping(value = "/getProductById/{productId}",method=RequestMethod.GET)
	public ModelAndView getProductById(@PathVariable(value="productId") int productId){
		Product product = productService.getProductById(productId);
		return new ModelAndView("productPage","product",product);
	}
	
	@RequestMapping(value = "/admin/delete/{productId}",method=RequestMethod.GET)
	public String deleteProduct(@PathVariable(value = "productId") int productId){
		Path path = Paths.get("C:/Users/vanri/workspace/products/" + productId + ".jpg");
		if(Files.exists(path)){//first delete the picture file
			try {
				Files.delete(path);
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		productService.deleteProduct(productId);
		return "redirect:/getAllProducts";
	}
	
    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
    public String getProductForm(Model model) {
   	 Product product = new Product();
   	 model.addAttribute("productForm", product);
   	 return "addProduct";
    }

	
	@RequestMapping(value = "/admin/product/addProduct",method=RequestMethod.POST)
	public String addProduct(@Valid @ModelAttribute(value = "productForm") Product product,BindingResult result,
			HttpServletRequest request, HttpServletResponse response, HttpSession session){
		if(result.hasErrors()){
			return "addProduct";
		}
		productService.addProduct(product);
		MultipartFile image = product.getProductImage();
		System.out.println(image.getSize());
		if(image!=null&&!image.isEmpty()){
			String realPath = request.getSession().getServletContext().getRealPath("/");
			//"/products/" 
			//File file1 = new File("D:/AAA/BBB/CCC");
			File file = new File("C:/Users/vanri/workspace/products");
			file.mkdir();
			//realPath + "WEB-INF/resource/images/products/"
			Path path = Paths.get("C:/Users/vanri/workspace/products/" + product.getId() + ".jpg");
			try {
				image.transferTo(new File(path.toString()));
				System.out.print(path.toString());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/getAllProducts";
	}
	
	@RequestMapping(value = "/admin/product/editProduct/{productId}")
	public ModelAndView getEditForm(@PathVariable(value="productId")int productId){
		Product product = productService.getProductById(productId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("editProduct");
		modelAndView.addObject("editProductObj",product);
		modelAndView.addObject("productId",productId);
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/product/editProduct/{productId}",method=RequestMethod.POST)
	public String editProduct(@ModelAttribute(value = "editProductObj")Product product,@PathVariable(value="productId")int productId){
		product.setId(productId);
		productService.updateProduct(product);
		return "redirect:/getAllProducts";
	}
	
	@RequestMapping("/getProductsList")
	public @ResponseBody List<Product> getProductsListJson(){
		return productService.getAllProducts();
	}
}
