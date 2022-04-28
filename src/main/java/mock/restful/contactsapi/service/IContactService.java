package mock.restful.contactsapi.service;

import mock.restful.contactsapi.model.Contact;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public interface IContactService {

    void createFactory();

    List<Contact> getContactList();

    void saveContact(List<Contact> contactList) throws IllegalAccessException;

    boolean isJSONValid(String jsonInString);

    long parseId(JSONObject contact);

    String parseFirstName(JSONObject contact);

    String parseLastName(JSONObject contact);

    String parseEmail(JSONObject contact);

    LocalDate parseDateAdded(JSONObject contact);

    String parseCompanyId(JSONObject contact);

    void setContactValues(JSONObject jsonContact, Contact contact);

    Contact create(JSONObject jsonContact);

    Contact update(JSONObject jsonContact, Contact contact);

    boolean add(Contact contact);

    boolean deleteById(String id);

    List<Contact> find();

    Contact findById(String id);

    List<Contact> findByCompanyId(String companyId);

    void clearObjects();
}
