package rentcar.carro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//    Ne pomnu zachen, no vrode Granocskiy govoril chto nujno!!!!
//    Mojem udalit
//TODO  understand if we need this
@EqualsAndHashCode
public class Response {
    private Object content;
    private Integer code;
    private String timestamp;
}
