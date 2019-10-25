package rentcar.carro.service;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Document(collection = "sequences")
public class DatabaseSequence {
 
    @Id
    private String id;
 
    private long seq;

}
