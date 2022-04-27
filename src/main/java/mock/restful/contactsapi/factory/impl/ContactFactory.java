package mock.restful.contactsapi.factory.impl;

import mock.restful.contactsapi.factory.IContactFactory;
import mock.restful.contactsapi.model.Contact;

public class ContactFactory implements IContactFactory {
    @Override
    public Contact create(String id) {
        return new Contact(id);
    }
}
