package mock.restful.contactsapi.factory;

import mock.restful.contactsapi.model.Contact;

public interface IContactFactory {
    Contact create(String id);
}
