package mock.restful.contactsapi.service;


import org.apache.log4j.Logger;
import org.json.JSONObject;

public interface IDatabase {
    Logger logger = Logger.getLogger(IDatabase.class);

    JSONObject getDB() throws IllegalAccessException;

    void saveDB(JSONObject jsonObject);
}
