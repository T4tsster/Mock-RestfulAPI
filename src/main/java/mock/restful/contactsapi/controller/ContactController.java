package mock.restful.contactsapi.controller;

import mock.restful.contactsapi.model.Contact;
import mock.restful.contactsapi.service.IContactService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/v1/contacts")
public class ContactController {
    private static final Logger logger = Logger.getLogger(ContactController.class);

    @Autowired
    private IContactService contactService;

    /**
     * Method that list all contact
     * @return ResponseEntity with a <code>List<Contact></code> object and the HTTP status
     *
     * HTTP Status:
     * 200 - OK: Everything worked as expected.
     * 404 - Not Found: The requested resource doesn't exist.
     */
    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        if (contactService.find().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(contactService.find());
    }

    /**
     *
     * @param companyId
     * @return ResponseEntity with a <code>List<Contact></code> object and HTTP status
     *
     * HTTP Status:
     * 200 - OK: Everything worked
     * 404 - Not found: The requested resource doesn't exist
     */
    @GetMapping(params = "companyId")
    public ResponseEntity<List<Contact>> getByCompanyId(@RequestParam(value = "companyId") String companyId) {
        if (contactService.find().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(contactService.findByCompanyId(companyId));
    }

    /**
     *  Methods that list contact with correct id
     * @param id
     * @return ResponseEntity with a <code>Contact</code> object and the HTTP status
     *
     * HTTP status:
     * 200 - OK: Everything worked
     * 404 - Not Found: The requested resource doesn't exist
     */
    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<Contact> get(@PathVariable("id") String id) {
        if (contactService.find().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(contactService.findById(id));
    }

    /**
     *
     * @param info
     * @return ResponseEntity with a <code>Contact</code> object and HTTP status
     *
     * HTTP Status:
     * 201 - Created
     * 400 - Bad request: Unable to parse JSON
     * 422 - Unprocessable entity: Contact ID is already existed in database, can't add new
     * 500 - Internal Server Error
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Contact> create(@RequestBody String info) {
        try {
            if (contactService.isJSONValid(info)){
                JSONObject contactInfo = new JSONObject(info);
                Contact contactCreated = contactService.create(contactInfo);
                var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(contactCreated.getId()).build().toUri();

                if (contactService.add(contactCreated)) {
                    return ResponseEntity.created(uri).body(null);
                } else {
                    logger.info("New contact ID is already existed");
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
                }

            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            logger.error(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     *
     * @param id
     * @return ResponseEntity with a <code>Boolean</code> object and HTTP status
     *
     * HTTP Status
     * 200 - OK: Delete successfully
     * 204 - No content: No contact matches with id
     * 404 - Not found: The requested resource doesn't exist.
     */
    @DeleteMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        if (contactService.find().isEmpty())
            return ResponseEntity.notFound().build();

        if (contactService.deleteById(id))
            return ResponseEntity.ok(null);
        else
            return ResponseEntity.noContent().build();
    }
}
