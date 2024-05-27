package si.skrjanec.example.taxation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("taxation")
public class TaxationResource {

    @POST
    public Response ProcessTax() {
        return Response.noContent().build();
    }
}