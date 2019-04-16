package pl.edu.pw.elka.proz.bandaimbecyli;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class TestsService
{
    @GET
    public String getMsg()
    {
        return "Hello World !! - Jersey 2";
    }
}
