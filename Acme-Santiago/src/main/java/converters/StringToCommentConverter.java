package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CommentRepository;
import domain.Comment;

@Component
@Transactional
public class StringToCommentConverter implements Converter<String, Comment>{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment convert(String text) {
		Comment result;
		int commentId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				commentId = Integer.valueOf(text);
				result = commentRepository.findOne(commentId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
