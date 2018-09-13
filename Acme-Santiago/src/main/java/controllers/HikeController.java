
package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdvertisementService;
import services.AgentService;
import services.HikeService;
import domain.Advertisement;
import domain.Hike;

@Controller
@RequestMapping("/hike")
public class HikeController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private HikeService				hikeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AgentService			agentService;

	@Autowired
	private AdvertisementService	advertisementService;


	//------------------------------------------- Constructor --------------------------------------------

	public HikeController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Hike> hikes;

		hikes = this.hikeService.findAll();

		result = new ModelAndView("hike/list");
		result.addObject("hikes", hikes);
		result.addObject("requestUri", "/hike/list.do");

		return result;
	}

	@RequestMapping(value = "/listAdvertisements")
	public ModelAndView listAdvertisements() {
		ModelAndView result;
		Collection<Hike> hikes;
		hikes = this.hikeService.getHikesFromAdvertisements(this.agentService.findByPrincipal().getAdvertisements());
		result = new ModelAndView("hike/list");
		result.addObject("hikes", hikes);
		result.addObject("requestUri", "/hike/list.do");

		return result;
	}
	@RequestMapping(value = "/listWithoutAdvertisements")
	public ModelAndView listWithoutAdvertisements() {
		ModelAndView result;
		Collection<Hike> hikes;

		hikes = this.hikeService.findAll();
		hikes.removeAll(this.hikeService.getHikesFromAdvertisements(this.agentService.findByPrincipal().getAdvertisements()));
		result = new ModelAndView("hike/list");
		result.addObject("hikes", hikes);
		result.addObject("requestUri", "/hike/list.do");

		return result;
	}

	@RequestMapping(value = "/showPictures", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int hikeId) {
		Assert.notNull(this.hikeService.findOne(hikeId));
		ModelAndView result;
		final Collection<Advertisement> adv = new HashSet<Advertisement>();
		final Hike hike = this.hikeService.findOne(hikeId);
		final Boolean empty = hike.getPictures().isEmpty();
		adv.addAll(hike.getAdvertisements());
		result = new ModelAndView("hike/showPictures");
		result.addObject("isEmpty", empty);
		result.addObject("hike", hike);
		if (adv.isEmpty())
			result.addObject("adv", null);
		else
			result.addObject("adv", this.advertisementService.getRandomAdvertisement(hike.getAdvertisements()).getBanner());
		return result;
	}
	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Hike hike;

		hike = this.hikeService.create();

		result = new ModelAndView("hike/edit");
		result.addObject("hike", hike);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int hikeId) {
		ModelAndView result;
		Hike hike;

		hike = this.hikeService.findOne(hikeId);
		result = this.createEditModelAndView(hike);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Hike hike, final BindingResult binding) {
		ModelAndView result;

		//hike = hikeService.reconstruct(hike, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(hike);
		else
			try {
				this.hikeService.save(hike);
				result = new ModelAndView("redirect:/hike/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(hike, "hike.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Hike hike, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			this.hikeService.delete(hike);
	//			result = new ModelAndView("redirect:/hike/list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(hike, "hike.commit.error");
	//		}
	//
	//		return result;
	//	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Hike hike) {
		ModelAndView result;
		result = this.createEditModelAndView(hike, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Hike hike, final String message) {
		ModelAndView result;

		result = new ModelAndView("hike/edit");
		result.addObject("hike", hike);
		result.addObject("message", message);

		return result;
	}

}
