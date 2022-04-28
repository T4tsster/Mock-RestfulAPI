package mock.restful.contactsapi;

import mock.restful.contactsapi.model.Contact;
import mock.restful.contactsapi.service.IContactService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactsApiUnitTests {

	@Autowired
	private IContactService contactService;

	@BeforeAll
	void setUp() {
		contactService.createFactory();
	}

	@Test
	public void testReturnNotNullContactService() {
		Assertions.assertNotNull(contactService);
	}

	@Test
	public void testGetExistContactById() {
		String id = "22222";

		Contact contact = contactService.findById(id);
		Assertions.assertEquals(id, contact.getId());
	}

	@Test
	public void testGetNotExistContactById() {
		String id = "abcxyz";

		Contact contact = contactService.findById(id);
		Assertions.assertNull(contact);
	}

	@Test
	public void testGetExistedContactByCompanyIdWithSuccess() {
		String companyId = "123";

		List<Contact> contacts = contactService.findByCompanyId(companyId);
		Assertions.assertNotNull(contacts);
		Assertions.assertNotEquals(0, contacts.size());
	}

	@Test
	public void testGetNotExistedContactByCompanyIdWithSuccess() {
		String companyId = "notExist";

		List<Contact> contacts = contactService.findByCompanyId(companyId);
		Assertions.assertNotNull(contacts);
		Assertions.assertEquals(0, contacts.size());
	}

	@Test
	public void testGetContactWithSuccess() {
		List<Contact> contacts = contactService.find();
		Assertions.assertNotNull(contacts);
	}

	@Test
	public void testReturnContactCreatedWithSuccess() throws JSONException {
		String dateAdded = "2022-05-01";

		JSONObject jsonContact = new JSONObject();
		jsonContact.put("id", "4");
		jsonContact.put("firstName", "Pham");
		jsonContact.put("lastName", "Nhan");
		jsonContact.put("email", "nhan.phamr6@example.com");
		jsonContact.put("dateAdded", dateAdded);
		jsonContact.put("companyId", "5");

		Contact contact = contactService.create(jsonContact);

		Assertions.assertNotNull(contact);
		Assertions.assertEquals(contact.getId(), jsonContact.get("id"));
		Assertions.assertEquals(contact.getFirstName(), jsonContact.get("firstName"));
		Assertions.assertEquals(contact.getLastName(), jsonContact.get("lastName"));
		Assertions.assertEquals(contact.getEmail(), jsonContact.get("email"));
		Assertions.assertEquals(contact.getDateAdded(), LocalDate.parse(dateAdded));
		Assertions.assertEquals(contact.getCompanyId(), jsonContact.get("companyId"));
	}

	@Test
	public void testAddContactWithSuccess() throws JSONException {
		String dateAdded = "2022-05-01";
		String id = "4";

		JSONObject jsonContact = new JSONObject();
		jsonContact.put("id", id);
		jsonContact.put("firstName", "Pham");
		jsonContact.put("lastName", "Nhan");
		jsonContact.put("email", "nhan.phamr6@example.com");
		jsonContact.put("dateAdded", dateAdded);
		jsonContact.put("companyId", "5");

		Contact contact = contactService.create(jsonContact);
		boolean canAddContact = contactService.add(contact);

		Assertions.assertTrue(canAddContact);
	}

	@Test
	public void testAddContactExistId() throws JSONException {
		String dateAdded = "2022-05-01";
		String id = "55555";

		JSONObject jsonContact = new JSONObject();
		jsonContact.put("id", id);
		jsonContact.put("firstName", "Pham");
		jsonContact.put("lastName", "Nhan");
		jsonContact.put("email", "nhan.phamr6@example.com");
		jsonContact.put("dateAdded", dateAdded);
		jsonContact.put("companyId", "5");

		Contact contact = contactService.create(jsonContact);
		contactService.add(contact);

		// Add another contact with same id
		jsonContact.put("firstName", "James");
		jsonContact.put("lastName", "Blunt");
		jsonContact.put("email", "bluntJ@gmail.com");
		jsonContact.put("dateAdded", dateAdded);
		jsonContact.put("companyId", "234");

		contact = contactService.create(jsonContact);
		boolean canAddContact = contactService.add(contact);

		Assertions.assertFalse(canAddContact);
	}

	@Test
	public void testDeleteContactWithSuccess() throws JSONException {
		String dateAdded = "2022-05-01";
		String id = "6";

		JSONObject jsonContact = new JSONObject();
		jsonContact.put("id", id);
		jsonContact.put("firstName", "Pham");
		jsonContact.put("lastName", "Nhan");
		jsonContact.put("email", "nhan.phamr6@example.com");
		jsonContact.put("dateAdded", dateAdded);
		jsonContact.put("companyId", "999");

		Contact contact = contactService.create(jsonContact);
		boolean canAddContact = contactService.add(contact);
		Assertions.assertTrue(canAddContact);

		boolean canDelete = contactService.deleteById(id);
		Assertions.assertTrue(canDelete);
	}

	@Test
	public void testDeleteContactNotExist() {
		String id = "7";

		boolean canDelete = contactService.deleteById(id);
		Assertions.assertFalse(canDelete);
	}

	@AfterAll
	public void tearDown() {
		contactService.clearObjects();
	}
}
