package rentcar.carro.dto;



public interface RegExprForValidator {

    String NUMBER_AND_SPACE= "[-0-9]+";
    String NUMBERS_LETTERS = "[-0-9a-zA-Z]+";
    String LETTERS = "^[a-zA-Z]+";
    String BIG_LETTERS = "^[A-Z]+";


    String NUMBERS = "[0-9]+";
}
