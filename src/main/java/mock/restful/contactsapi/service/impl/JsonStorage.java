package mock.restful.contactsapi.service.impl;

import mock.restful.contactsapi.config.Const;
import mock.restful.contactsapi.service.IDatabase;
import org.json.JSONObject;

import java.io.*;

public class JsonStorage implements IDatabase {

    @Override
    public JSONObject getDB() throws IllegalAccessException {
        JSONObject jsonObject = null;

        InputStream file = this.getClass()
                .getClassLoader()
                .getResourceAsStream(Const.jsonPath);
        if (file == null)
            throw new IllegalAccessException(Const.jsonPath + " not found");
//        File file = new File(Const.jsonPath);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            Reader r = new InputStreamReader(file, "UTF-8");
            reader = new BufferedReader(r);
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }

            jsonObject = new JSONObject(contents.toString());
        } catch (Exception e) {
            logger.error(e);
        }

        return jsonObject;
    }

    @Override
    public void saveDB(JSONObject jsonObject) {
        try {
            File file = new File(this.getClass().getClassLoader()
                    .getResource(".").getFile() + "/" + Const.jsonPath);
            PrintWriter writer = new PrintWriter(file);
            writer.write(jsonObject.toString(4));
            writer.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
