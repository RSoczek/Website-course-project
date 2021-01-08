package project.Controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import project.Model.Entity.Opinion;
import project.Model.Entity.User;
import project.Model.Entity.Whisky;
import project.Model.Repository.OpinionRepo;
import project.Model.Repository.WhiskyRepo;
import project.Service.UserService;



@Controller
public class WhiskyController {
	
	@Autowired
	private WhiskyRepo whiskyRepo;
	
	@Autowired
	private UserService userService;
	
	Whisky whisky = new Whisky();
	
	@GetMapping("/whisky/{id}")
	public String ShowWhisky(@PathVariable (value = "id") long id, Model model) {
	whisky = whiskyRepo.getOne(id);
	model.addAttribute("whisky",whisky);
	model.addAttribute("newOpinion",new Opinion());
	return "whisky";
	
	}
	
	
	@PostMapping("/add-Opinion")
	public String addOpinion(@ModelAttribute Opinion opinion, Principal principal ) {
		

		User currentUser = userService.findByUsername(principal.getName());
		Date date = new Date();
		opinion.setUser(currentUser);
		opinion.setCreatedAt(date);
		opinion.setWhisky(whisky);
		whisky.getOpinions().add(opinion);
		whiskyRepo.save(whisky);	
	
		return "redirect:/whisky/" + whisky.getId().toString();
	}
}