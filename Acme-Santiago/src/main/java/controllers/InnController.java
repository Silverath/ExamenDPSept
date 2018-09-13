
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InnService;
import domain.Inn;

@Controller
@RequestMapping("/inn")
public class InnController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private InnService	innService;


	//------------------------------------------- Constructor --------------------------------------------

	public InnController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Inn> inns;

		inns = this.innService.findAll();

		result = new ModelAndView("inn/list");
		result.addObject("inns", inns);
		result.addObject("requestUri", "/inn/list.do");

		return result;
	}

	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Inn inn;

		inn = this.innService.create();

		result = new ModelAndView("inn/edit");
		result.addObject("inn", inn);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int innId) {
		ModelAndView result;
		Inn inn;

		inn = this.innService.findOne(innId);
		result = this.createEditModelAndView(inn);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Inn inn, final BindingResult binding) {
		ModelAndView result;

		//inn = innService.reconstruct(inn, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(inn);
		else
			try {
				this.innService.save(inn);
				result = new ModelAndView("redirect:/inn/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(inn, "inn.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Inn inn, final BindingResult binding) {
		ModelAndView result;

		try {
			this.innService.delete(inn);
			result = new ModelAndView("redirect:/inn/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(inn, "inn.commit.error");
		}

		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Inn inn) {
		ModelAndView result;
		result = this.createEditModelAndView(inn, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Inn inn, final String message) {
		ModelAndView result;

		result = new ModelAndView("inn/edit");
		result.addObject("inn", inn);
		result.addObject("message", message);

		return result;
	}

}
