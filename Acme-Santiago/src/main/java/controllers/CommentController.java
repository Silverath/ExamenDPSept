
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private CommentService	commentService;


	//------------------------------------------- Constructor --------------------------------------------

	public CommentController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Comment> comments;

		comments = this.commentService.findAll();

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestUri", "/comment/list.do");

		return result;
	}

	@RequestMapping(value = "/showPictures", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		Assert.notNull(this.commentService.findOne(commentId));
		ModelAndView result;
		final Comment comment = this.commentService.findOne(commentId);
		final Boolean empty = comment.getPictures().isEmpty();
		result = new ModelAndView("comment/showPictures");
		result.addObject("isEmpty", empty);
		result.addObject("comment", comment);
		return result;
	}

	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int routeId) {
		ModelAndView result;
		Comment r;
		r = this.commentService.create(routeId);
		result = new ModelAndView("comment/edit");
		result.addObject("comment", r);
		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.findOne(commentId);
		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;

		//comment = commentService.reconstruct(comment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/comment/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			this.commentService.delete(commentId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("comment/list");
			result.addObject("message", "comment.commit.error");
		}
		result.addObject("routes", this.commentService.findAll());

		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;
		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

}
