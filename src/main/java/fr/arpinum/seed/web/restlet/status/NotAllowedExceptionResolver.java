package fr.arpinum.seed.web.restlet.status;

import fr.arpinum.seed.model.BusinessError;
import fr.arpinum.seed.model.NotAllowedException;
import org.json.*;
import org.restlet.data.*;
import org.restlet.ext.json.*;
import org.restlet.representation.*;

public class NotAllowedExceptionResolver implements ExceptionResolver {


    @Override
    public boolean canResolve(Throwable throwable) {
        return throwable != null && NotAllowedException.class.isAssignableFrom(throwable.getClass());
    }

    @Override
    public Status status(Throwable error) {
        return new Status(Status.CLIENT_ERROR_FORBIDDEN.getCode(), error);
    }

    @Override
    public Representation representation(Throwable throwable) {
        try {
            return new JsonRepresentation(toJson((BusinessError) throwable));
        } catch (JSONException e) {
            return new JsonRepresentation("{}");
        }
    }

    private JSONObject toJson(BusinessError error) throws JSONException {
        JSONObject result = new JSONObject();
        JSONArray errors = new JSONArray();
        result.put("errors", errors);
        JSONObject jsonObject = new JSONObject();
        putMessage(error.getMessage(), jsonObject);
        errors.put(jsonObject);
        return result;
    }

    private void putMessage(String message, JSONObject error) {
        try {
            error.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}