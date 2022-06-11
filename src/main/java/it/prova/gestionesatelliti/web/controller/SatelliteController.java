package it.prova.gestionesatelliti.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.SatelliteCustomValidator;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteservice;

	@Autowired
	private SatelliteCustomValidator satelliteValidator;

	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteservice.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteservice.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteValidator.validate(satellite, result);

		if (result.hasErrors())
			return "satellite/insert";

		satelliteservice.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteservice.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model) {

		model.addAttribute("remove_satellite_attr", satelliteservice.caricaSingoloElemento(idSatellite));

		return "satellite/delete";
	}

	@GetMapping("/remove/{idSatellite}")
	public String remove(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {
		
		if(satelliteValidator.validateRemove(satelliteservice.caricaSingoloElemento(idSatellite))) {
			
			satelliteservice.rimuoviPerId(idSatellite);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		}
		else {
			
			redirectAttrs.addFlashAttribute("errorMessage", "per eliminare il satellite bigogna che sia disattivato e atterrato oppure solo censito");
			
		}
		
		return "redirect:/satellite";

	}
	
	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("edit_satellite_attr", satelliteservice.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("edit_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors())
			return "satellite/edit";

		satelliteservice.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/cambioDataPiuLancio/{idSatellite}")
	public String cambioDataPiuLancio(@PathVariable(required = true) Long idSatellite, Model model, RedirectAttributes redirectAttrs) {
		
		Satellite satelliteDaLanciare = satelliteservice.caricaSingoloElemento(idSatellite);
		if(satelliteDaLanciare.getDataLancio() == null) {
			
			satelliteDaLanciare.setDataLancio(new Date());
			satelliteDaLanciare.setStato(StatoSatellite.IN_MOVIMENTO);
			satelliteservice.aggiorna(satelliteDaLanciare);
			
		}
		else {
			
			redirectAttrs.addFlashAttribute("errorMessage", "per lanciare il satellite bigogna che sia solo censito");
			
		}
		
		return "redirect:/satellite";
	}
	
	@GetMapping("/rientroPiuDisattivamento/{idSatellite}")
	public String rientroPiuDisattivamento(@PathVariable(required = true) Long idSatellite, Model model, RedirectAttributes redirectAttrs) {
		
		Satellite satelliteDaDisattivare = satelliteservice.caricaSingoloElemento(idSatellite);
		if(satelliteDaDisattivare.getDataRientro() == null) {
			
			satelliteDaDisattivare.setDataRientro(new Date());
			satelliteDaDisattivare.setStato(StatoSatellite.DISATTIVATO);
			satelliteservice.aggiorna(satelliteDaDisattivare);
			
		}
		else {
			
			redirectAttrs.addFlashAttribute("errorMessage", "per ritirare il satellite bigogna che sia in orbita");
			
		}
		
		return "redirect:/satellite";
	}

}
