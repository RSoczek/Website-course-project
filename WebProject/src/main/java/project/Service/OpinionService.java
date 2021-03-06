package project.Service;

import project.Model.Entity.Opinion;

public interface OpinionService {
	
	public Opinion findById(Long id);
	
	public Boolean haveAlreadyReviewed(long whiskyId, long userId);

}
