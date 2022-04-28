package mock.restful.contactsapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mock.restful.contactsapi.factory.IContactFactory;
import mock.restful.contactsapi.factory.impl.ContactFactory;
import mock.restful.contactsapi.model.Contact;
import mock.restful.contactsapi.service.IContactService;
import mock.restful.contactsapi.service.IDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {
    private IContactFactory factory;

    private IDatabase database;

    @Override
    public void createFactory() {
        if (factory == null)
            factory = new ContactFactory();
    }

    @Override
    public List<Contact> getContactList() {
        if (database == null)
            database = new JsonStorage();

        List<Contact> contactList = new ArrayList<>();
        JSONArray JsonContacts = null;
        try {
            JsonContacts = database.getDB().getJSONArray("contacts");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return contactList;
        }

        for (int i = 0; i < JsonContacts.length(); i++) {
            JSONObject contactObject = JsonContacts.getJSONObject(i);
            Contact contact = create(contactObject);
            contactList.add(contact);
        }
        return contactList;
    }

    @Override
    public void saveContact(List<Contact> contactList) throws IllegalAccessException {
        if (database == null)
            database = new JsonStorage();

        JSONObject data = database.getDB();
        data.put("contacts", contactList);
        database.saveDB(data);
    }

    @Override
    public boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public long parseId(JSONObject contact) {
        return Long.valueOf((int) contact.get("id"));
    }

    @Override
    public String parseFirstName(JSONObject contact) {
        return (String) contact.get("firstName");
    }

    @Override
    public String parseLastName(JSONObject contact) {
        return (String) contact.get("lastName");
    }

    @Override
    public String parseEmail(JSONObject contact) {
        return (String) contact.get("email");
    }

    @Override
    public LocalDate parseDateAdded(JSONObject contact) {
        var date = (String) contact.get("dateAdded");
        return LocalDate.parse(date);
    }

    @Override
    public String parseCompanyId(JSONObject contact) {
        return (String) contact.get("companyId");
    }

    @Override
    public void setContactValues(JSONObject jsonContact, Contact contact) {
        contact.setFirstName(jsonContact.get("firstName") != null ? parseFirstName(jsonContact) : contact.getFirstName());
        contact.setLastName(jsonContact.get("lastName") != null ? parseLastName(jsonContact) : contact.getLastName());
        contact.setEmail(jsonContact.get("email") != null ? parseEmail(jsonContact) : contact.getEmail());
        contact.setDateAdded(jsonContact.get("dateAdded") != null ? parseDateAdded(jsonContact) : contact.getDateAdded());
        contact.setCompanyId(jsonContact.get("companyId") != null ? parseCompanyId(jsonContact) : contact.getCompanyId());
    }

    @Override
    public Contact create(JSONObject jsonContact) {
        createFactory();

        Contact contact = factory.create((String) jsonContact.get("id"));
        setContactValues(jsonContact, contact);

        return contact;
    }

    @Override
    public Contact update(JSONObject jsonContact, Contact contact) {
        setContactValues(jsonContact, contact);
        return contact;
    }

    @Override
    public boolean add(Contact contact) {
        List<Contact> contactList = getContactList();
        if (contactList.stream().anyMatch(c -> c.getId().equals(contact.getId())))
            return false;

        contactList.add(contact);
        try {
            saveContact(contactList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        List<Contact> contactList = getContactList();
        Iterator<Contact> iterator = contactList.iterator();
        while (iterator.hasNext()) {
            String contactId = iterator.next().getId();
            if (id.equals(contactId)) {
                iterator.remove();
                try {
                    saveContact(contactList);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Contact> find() {
        return getContactList();
    }

    @Override
    public Contact findById(String id) {
        List<Contact> contactList = getContactList();
        return contactList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Contact> findByCompanyId(String companyId) {
        List<Contact> contactList = getContactList();
        return contactList.stream()
                .filter(c -> c.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public void clearObjects() {
        factory = null;
        database = null;
    }
}
