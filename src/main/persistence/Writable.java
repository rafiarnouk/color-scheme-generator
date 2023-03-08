package persistence;

import org.json.JSONObject;

// represents an object that can be written as a JSON object
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
