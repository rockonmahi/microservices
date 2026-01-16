package com.escola.saml2.controller;

import com.escola.saml2.service.Car;
import com.escola.saml2.service.CarsOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class CarsOnlineController {
	
	@Autowired
	CarsOnlineService carsService;

	@GetMapping("/")
	public RedirectView redirectToCars() {
		return new RedirectView("/carsonline");
	}

	@GetMapping("/carsonline")
	public String showUsedCars(Authentication authentication, Model model) {
		
		List<Car> allCars = carsService.listCars();
		
		model.addAttribute("name", authentication.getName());
		model.addAttribute("cars", allCars);
		return "carsonline";
	}
	
	@GetMapping("/buy/{id}")
	public String buyCar(Authentication authentication, Model model, @PathVariable(name = "id") int id) {
		model.addAttribute("name", authentication.getName());
		
		Car car = null; 
		try {		
			car = carsService.buyCar(id);
		} catch (Exception e) {
			System.out.println("No Car available !");
		}
		
		model.addAttribute("car", car);
		return "buycar";
	}

	@GetMapping("/edit/{id}")
	public String editCar(Authentication authentication, Model model, @PathVariable(name = "id") int id) {
		model.addAttribute("name", authentication.getName());
		return "editcar";
	}

	@GetMapping("/user")
	public String showUser() {	
		return "user";
	}
}