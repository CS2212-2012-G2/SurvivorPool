package data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * MultipleChoice.java represents the case that a bonus question is of type
 * multiple choice.
 * 
 * @author Graem Littleton, Ramesh Raj, Justin McDonald, Jonathan Demelo, Kevin Brightwell
 *
 */
public class MultipleChoice extends BonusQuestion{

	private String[] options; // stores the choices that the user can make
	
	/**
	 * getAnswer returns the appropriate answer to a specific MC question.
	 * 
	 * @return answer   the answer to the question
	 */
    @Override
	public String getAnswer() 
    {
    	throw new NotImplementedException();
    }
    
    /**
     * getOptions returns the list of options to the caller.
     * 
     * @return this.options
     */
    public String[] getOptions()
    {
    	throw new NotImplementedException();
    }
}
