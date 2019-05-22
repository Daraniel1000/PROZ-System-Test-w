package pl.edu.pw.elka.proz.bandaimbecyli.webutil;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    public Response toResponse(ForbiddenException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
