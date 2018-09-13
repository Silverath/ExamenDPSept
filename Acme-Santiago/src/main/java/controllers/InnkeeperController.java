
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InnkeeperService;
import domain.Innkeeper;

@Controller
@RequestMapping("/innkeeper")
public class InnkeeperController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private InnkeeperService	innkeeperService;


	//------------------------------------------- Constructor --------------------------------------------

	public InnkeeperController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Innkeeper> innkeepers;

		innkeepers = this.innkeeperService.findAll();

		result = new ModelAndView("innkeeper/list");
		result.addObject("innkeepers", innkeepers);
		result.addObject("requestUri", "/innkeeper/list.do");

		return result;
	}

	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Innkeeper innkeeper;

		innkeeper = this.innkeeperService.create();

		result = new ModelAndView("innkeeper/edit");
		result.addObject("innkeeper", innkeeper);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int innkeeperId) {
		ModelAndView result;
		Innkeeper innkeeper;

		innkeeper = this.innkeeperService.findOne(innkeeperId);
		result = this.createEditModelAndView(innkeeper);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Innkeeper innkeeper, final BindingResult binding) {
		ModelAndView result;

		//innkeeper = innkeeperService.reconstruct(innkeeper, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(innkeeper);
		else
			try {
				this.innkeeperService.save(innkeeper);
				result = new ModelAndView("redirect:/innkeeper/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(innkeeper, "innkeeper.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Innkeeper innkeeper, final BindingResult binding) {
		ModelAndView result;

		try {
			this.innkeeperService.delete(innkeeper);
			result = new ModelAndView("redirect:/innkeeper/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(innkeeper, "innkeeper.commit.error");
		}

		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Innkeeper innkeeper) {
		ModelAndView result;
		result = this.createEditModelAndView(innkeeper, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Innkeeper innkeeper, final String message) {
		ModelAndView result;

		result = new ModelAndView("innkeeper/edit");
		result.addObject("innkeeper", innkeeper);
		result.addObject("message", message);

		return result;
	}

}
