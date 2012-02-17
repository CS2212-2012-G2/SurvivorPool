package client;

public class BonusQuestion {
private String question, type, answer;
private String[] multichoices;

public BonusQuestion(String question, String type, String[] multichoices, String answer){
	this.question = question;
	this.type = type;
	this.answer = answer;
	this.multichoices = multichoices;
}

public boolean hasAnswer(){
	if (answer.equals(""))
		return false;
	else
		return true;
}

public void setQuestion(String enteredQuestion){
	this.question = enteredQuestion;
}

public void setType(String enteredType){
	this.type = enteredType;
}

public void setMultiChoices(String[] enteredMultichoices){
	this.multichoices = enteredMultichoices;
}

public void setAnswer(String enteredAnswer){
	this.answer = enteredAnswer;
}

public String getQuestion(){
	return this.question;
}

public String getType(){
	return this.type;
}

public String[] getMultiChoices(){
	return this.multichoices;
}

public String getAnswer(){
	return this.answer;
}
}
