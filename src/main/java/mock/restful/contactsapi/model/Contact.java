package mock.restful.contactsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateAdded;
    private String companyId;

    public Contact(String id) {
        this.id = id;
    }
}
